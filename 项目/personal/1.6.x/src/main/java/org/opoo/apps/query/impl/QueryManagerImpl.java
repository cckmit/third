package org.opoo.apps.query.impl;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.annotation.NotQueryable;
import org.opoo.apps.annotation.Queryable;
import org.opoo.apps.event.v2.EventListener;
import org.opoo.apps.event.v2.core.PropertyEvent;
import org.opoo.apps.exception.QueryNotFoundException;
import org.opoo.apps.query.ParametersBuilder;
import org.opoo.apps.query.Query;
import org.opoo.apps.query.QueryManager;
import org.opoo.apps.query.stat.QueryWrapper;
import org.opoo.apps.query.stat.Statistics;
import org.opoo.apps.query.stat.StatisticsImplementor;
import org.opoo.ndao.Dao;
import org.opoo.ndao.hibernate3.HibernateQuerySupport;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.Assert;

public class QueryManagerImpl implements QueryManager, ApplicationContextAware, InitializingBean, EventListener<PropertyEvent>{
	private static final Log log = LogFactory.getLog(QueryManagerImpl.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	public static final String QUERYSTRING_MAP_KEY = "queries";
	public static final String QUERYSTRING_PROPS_KEY = "queryProps";
	public static final String QUERY_STATISTICS_ENABLED_KEY = "query.statistics.enabled";
	
	private Map<String, Query<?>> queries = new HashMap<String, Query<?>>();
	
	private ParametersBuilder parametersBuilder;
	private HibernateQuerySupport hibernateQuerySupport;
	private ApplicationContext applicationContext;
	private Statistics statistics;// = new StatisticsImpl();
	private StatisticsImplementor statisticsImplementor;
	
	/**
	 * Ҫ�ų���ͨ�ò�ѯBean��
	 */
	private List<String> excludes = new ArrayList<String>();
	
	/**
	 * ע��һ����ѯ��
	 * ����ظ����򸲸�֮ǰע��ġ�
	 * 
	 * @param key ע��Ĺؼ���
	 * @param query ��ѯ����
	 */
	public void registerQuery(String key, Query<?> query){
		//boolean overlay = queries.containsKey(key);
		queries.put(key, query);
		//if(IS_DEBUG_ENABLED){
		//	log.debug((overlay ? "����" : "ע��") + "��ѯ��" + key + " -> " + query);
		//}
	}
	
	/**
	 * ����Query��
	 * @param key
	 * @return
	 */
	public Query<?> getQuery(String key) throws QueryNotFoundException{
		Query<?> query = queries.get(key);
		if(query == null){
			throw new QueryNotFoundException(key);
		}
		
		if(IS_DEBUG_ENABLED){
			log.debug("��ȡ��ѯ��" + key + " -> " + query);
		}
		
		if(statistics.isStatisticsEnabled()){
			query = new QueryWrapper(statistics, statisticsImplementor, key, query);
		}
		return query;
	}
	
	/**
	 * ע��Spring���������е�dao�ͱ�ע��Queryable�ĺ�����BeanΪ��ѯ����
	 * 
	 * @param ctx
	 */
	private void registerQueryBeans(ApplicationContext ctx){
		if(!(ctx instanceof ConfigurableApplicationContext)){
			log.warn("Spring �������� ConfigurableApplicationContext�������������е�ͨ�ò�ѯ��");
			return;
		}
		
		ConfigurableApplicationContext c = (ConfigurableApplicationContext) ctx;
		ConfigurableListableBeanFactory factory = c.getBeanFactory();
		String[] names = ctx.getBeanDefinitionNames();
		for(String name: names){
			registerQueryBean(c, factory, name);
		}
	}
	
	private void registerQueryBean(ConfigurableApplicationContext c, ConfigurableListableBeanFactory factory, final String name){
		if(excludes.contains(name)){
			log.debug("���������ų���" + name);
			return;
		}
		
		if(!canQuery(c, name)){
			log.debug("���಻������ͨ�ò�ѯ(canQuery)��" + name);
			return;
		}
		
		BeanDefinition def = factory.getBeanDefinition(name);
		if(def.isAbstract() || !def.isSingleton()){
			//log.debug("�ų�������߷ǵ�����" + name);
			return;
		}
		
		Class<?> type = c.getType(name);
		if(type == null){
			//log.debug("Class of '" + name + "' is null.");
			return;
		}
		
		//�������ע�˲�������ͨ�ò�ѯ��������
		if(type.isAnnotationPresent(NotQueryable.class)){
			log.debug("���಻������ͨ�ò�ѯ��" + name);
			return;
		}
		
		//DAO
		if(Dao.class.isAssignableFrom(type)){
			String base = name.substring(0, name.length() - 3);
			registerQuery(base + ".list", new DaoListQuery(name, parametersBuilder, def));
			registerQuery(base + ".page", new DaoPageQuery(name, parametersBuilder, def));
			registerQuery(base + ".get", new DaoGetQuery(name, def));
			registerQuery(base + ".label", new LabelableQuery(name, parametersBuilder, def));
			
			//��̬����
			Method[] methods = type.getDeclaredMethods();
			for(Method m: methods){
				Class<?>[] parameterTypes = m.getParameterTypes();
				Class<?> returnType = m.getReturnType();
				if(parameterTypes.length == 1 && ResultFilter.class.isAssignableFrom(parameterTypes[0])
						&& List.class.isAssignableFrom(returnType)){
					//modifiers
					int modifiers = m.getModifiers();
					if(!Modifier.isPublic(modifiers) || Modifier.isAbstract(modifiers) || Modifier.isStatic(modifiers)){
						if(IS_DEBUG_ENABLED){
							log.debug("��������Ϊ�ǹ����������̬��������Ϊͨ�ò�ѯʹ�ã�" + m);
						}
						return;
					}
					
					String[] argNames = {"filter"};
					registerQuery(base + "." + m.getName(), new MethodQuery(name, m, argNames, parametersBuilder, def));
//					if(PageableList.class.isAssignableFrom(returnType)){
//						registerQuery(key + ".dynpage", new MethodCallQuery(name, m, argNames, parametersBuilder));
//					}else if(List.class.isAssignableFrom(returnType)){
//						registerQuery(key + ".dynlist", new MethodCallQuery(name, m, argNames, parametersBuilder));
//					}
				}
			}
		}else{
			Method[] methods = type.getDeclaredMethods();
			for(Method m: methods){
				Queryable queryable = m.getAnnotation(Queryable.class);
				if(queryable != null){
					//modifiers
					int modifiers = m.getModifiers();
					if(!Modifier.isPublic(modifiers) || Modifier.isAbstract(modifiers) || Modifier.isStatic(modifiers)){
						if(IS_DEBUG_ENABLED){
							log.debug("��������Ϊ�ǹ����������̬��������Ϊͨ�ò�ѯʹ�ã�" + m);
						}
						return;
					}
					//return type
					if(void.class.equals(m.getReturnType())){
						if(IS_DEBUG_ENABLED){
							log.debug("�������巵��ֵΪvoid��������Ϊͨ�ò�ѯʹ�ã�" + m);
						}
						return;
					}
					
					MethodQuery query = new MethodQuery(name, m, queryable.argNames(), parametersBuilder, def);
					registerQuery(name + "." + m.getName(), query);
					
					//�������������������ѯҲע��
					String queryName = queryable.name();
					if(StringUtils.isNotBlank(queryName)){
						registerQuery(queryName, query);
					}
				}
			}
		}
	}
	
	
	protected boolean canQuery(ConfigurableApplicationContext ctx, String name){
		return true;
	}
	
	/**
	 * ����һ��SQL����HQL����ļ�ֵ�Լ��������ɲ�ע���ѯ��
	 * ����ʹ�� {@link #setQueryMap(Map, Resource)}
	 * @param queries
	 */
	public void setQueryMap(Map<String, String> queries) {
		setQueryMap(queries, null);
	}

	/**
	 * 
	 * @param queries
	 * @param resource
	 */
	public void setQueryMap(Map<String, String> queries, Resource resource) {
		for(Map.Entry<String, String> en: queries.entrySet()){
			String key = en.getKey();
			String value = en.getValue();
			Assert.hasText(value, "��ѯ�������ò���ȷ��");
			
			String[] array = value.split(";");
			Assert.hasText(array[0], "��ѯ���õĲ�ѯ��䲻��Ϊ�ա�");
			
			boolean isSQLQuery = false;
			if(key.toUpperCase().endsWith("|SQL")){
				isSQLQuery = true;
			}
			
			//list2
			NamedListQuery list = new NamedListQuery(hibernateQuerySupport, array[0], isSQLQuery, parametersBuilder, resource);
			registerQuery(key + ".list2", list);
			
			//page2
			if(array.length >=2 && StringUtils.isNotBlank(array[1])){
				NamedPageQuery page = new NamedPageQuery(hibernateQuerySupport, array[0], array[1], isSQLQuery, parametersBuilder, resource);
				registerQuery(key + ".page2", page);
			}
		}
	}
	
	/**
	 * ��ѯSpring����������Ϊ ��queries�� ��Map����bean��������ע���ѯ��
	 * @param applicationContext
	 */
	private void registerQueryMapBean(ApplicationContext applicationContext){
		try {
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) applicationContext.getBean(QUERYSTRING_MAP_KEY);
			if(map != null){
				Resource resource = getBeanResource(applicationContext, QUERYSTRING_MAP_KEY);
				setQueryMap(map, resource);
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
	}
	
	//����ָ��Bean��BeanDefinition�ж�Ӧ��Resource
	private Resource getBeanResource(ApplicationContext applicationContext, String beanName){
		BeanDefinition def = null;
		if(applicationContext instanceof ConfigurableListableBeanFactory){
			ConfigurableListableBeanFactory ctx = (ConfigurableListableBeanFactory) applicationContext;
			def = ctx.getBeanDefinition(beanName);
		}else if(applicationContext instanceof ConfigurableApplicationContext){
			ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) applicationContext;
			ConfigurableListableBeanFactory beanFactory = ctx.getBeanFactory();
			if(beanFactory != null){
				def = beanFactory.getBeanDefinition(beanName);
			}
		}
		if(def instanceof AbstractBeanDefinition){
			return ((AbstractBeanDefinition)def).getResource();
		}
		return null;
	}
	
	/**
	 * ��ѯSpring����������Ϊ ��queryProps�� ��Properties����bean��������ע���ѯ��
	 * @param applicationContext
	 */
	private void registerQueryPropertiesBean(ApplicationContext applicationContext){
		try {
			Properties props = (Properties) applicationContext.getBean(QUERYSTRING_PROPS_KEY);
			if(props != null){
				@SuppressWarnings({ "unchecked", "rawtypes" })
				Map<String,String> map = new HashMap(props);
				Resource resource = getBeanResource(applicationContext, QUERYSTRING_PROPS_KEY);
				setQueryMap(map, resource);
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
	}
	
	/**
	 * ������·������������Ϊ queries-*.properties�������ļ����������ļ����ݴ�����ע���ѯ��
	 * @param applicationContext
	 * @throws IOException
	 */
	private void registerOverlayProperties(ApplicationContext applicationContext) throws IOException{
		DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
		ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
		Resource[] resources = resolver.getResources("classpath*:queries-*.properties");
		if(resources != null && resources.length > 0){
			for (Resource resource : resources) {
				//System.out.println(resource);
				Properties props = PropertiesLoaderUtils.loadProperties(resource);
				if(props != null){
					@SuppressWarnings({ "unchecked", "rawtypes" })
					Map<String, String> map = new HashMap(props);
					setQueryMap(map, resource);
					log.debug("Overlay queries.properties by " + resource);
				}
			}
		}else{
			log.debug("No overlays found.");
		}
	}

	public void afterPropertiesSet() throws Exception {
		/*
		if(statistics == null){
			log.warn("û��ע��statistics���󣬴���Ĭ�϶��󣬿��ܵ���ͳ�����ݲ���ȷ��");
			statistics = new StatisticsImpl();
		}
		if(statisticsImplementor == null){
			if(statistics instanceof StatisticsImplementor){
				statisticsImplementor = (StatisticsImplementor)statistics;
			}else{
				statisticsImplementor = new StatisticsImpl();
			}
		}*/
		Assert.notNull(statistics, "statistics is required.");
		Assert.notNull(statisticsImplementor, "statisticsImplementor is required.");
		
		if(hibernateQuerySupport == null){
			@SuppressWarnings("unchecked")
			Map<String,HibernateQuerySupport> map = applicationContext.getBeansOfType(HibernateQuerySupport.class);
			if(map.size() > 1){
				hibernateQuerySupport = map.values().iterator().next();
			}else{
				throw new IllegalArgumentException("������������ ��hibernateQuerySupport��");
			}
		}

		if(excludes == null){
			excludes = Collections.emptyList();
		}
		registerQueryBeans(applicationContext);
		registerQueryMapBean(applicationContext);
		registerQueryPropertiesBean(applicationContext);
		registerOverlayProperties(applicationContext);
		
		resetQueryStatisticsEnabled();
	}
	private void resetQueryStatisticsEnabled(){
		statistics.setStatisticsEnabled(AppsGlobals.getProperty(QUERY_STATISTICS_ENABLED_KEY, false));
		log.debug("��ѯ�Ƿ�����ͳ�ƣ�" + statistics.isStatisticsEnabled());
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	@Required
	public void setParametersBuilder(ParametersBuilder parametersBuilder) {
		this.parametersBuilder = parametersBuilder;
	}
	
	public void setHibernateQuerySupport(HibernateQuerySupport hibernateQuerySupport) {
		this.hibernateQuerySupport = hibernateQuerySupport;
	}

	public Map<String, Query<?>> getQueries() {
		return queries;
	}

	public void setQueries(Map<String, Query<?>> queries) {
		this.queries = queries;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

	public void setStatisticsImplementor(StatisticsImplementor statisticsImplementor) {
		this.statisticsImplementor = statisticsImplementor;
	}

	public void handle(PropertyEvent event) {
		//���Ա仯����������
		if(QUERY_STATISTICS_ENABLED_KEY.equals(event.getName())){
			resetQueryStatisticsEnabled();
		}
	}
	
//	static boolean isValidQueryMethod(Method m){
//		int modifiers = m.getModifiers();
//		if(void.class.equals(m.getReturnType())){
//			if(IS_DEBUG_ENABLED){
//				log.debug("Method return type is void: " + m);
//			}
//			return false;
//		}
//		if(Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)){
//			return true;
//		}
//		return false;
//	}
}
