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
	 * 要排除的通用查询Bean。
	 */
	private List<String> excludes = new ArrayList<String>();
	
	/**
	 * 注册一个查询。
	 * 如果重复，则覆盖之前注册的。
	 * 
	 * @param key 注册的关键字
	 * @param query 查询对象
	 */
	public void registerQuery(String key, Query<?> query){
		//boolean overlay = queries.containsKey(key);
		queries.put(key, query);
		//if(IS_DEBUG_ENABLED){
		//	log.debug((overlay ? "覆盖" : "注册") + "查询：" + key + " -> " + query);
		//}
	}
	
	/**
	 * 查找Query。
	 * @param key
	 * @return
	 */
	public Query<?> getQuery(String key) throws QueryNotFoundException{
		Query<?> query = queries.get(key);
		if(query == null){
			throw new QueryNotFoundException(key);
		}
		
		if(IS_DEBUG_ENABLED){
			log.debug("获取查询：" + key + " -> " + query);
		}
		
		if(statistics.isStatisticsEnabled()){
			query = new QueryWrapper(statistics, statisticsImplementor, key, query);
		}
		return query;
	}
	
	/**
	 * 注册Spring容器中所有的dao和标注了Queryable的函数的Bean为查询对象。
	 * 
	 * @param ctx
	 */
	private void registerQueryBeans(ApplicationContext ctx){
		if(!(ctx instanceof ConfigurableApplicationContext)){
			log.warn("Spring 容器不是 ConfigurableApplicationContext，不处理容器中的通用查询。");
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
			log.debug("根据配置排除：" + name);
			return;
		}
		
		if(!canQuery(c, name)){
			log.debug("该类不能用于通用查询(canQuery)：" + name);
			return;
		}
		
		BeanDefinition def = factory.getBeanDefinition(name);
		if(def.isAbstract() || !def.isSingleton()){
			//log.debug("排除抽象或者非单例：" + name);
			return;
		}
		
		Class<?> type = c.getType(name);
		if(type == null){
			//log.debug("Class of '" + name + "' is null.");
			return;
		}
		
		//如果被标注了不可用于通用查询，则跳过
		if(type.isAnnotationPresent(NotQueryable.class)){
			log.debug("该类不能用于通用查询：" + name);
			return;
		}
		
		//DAO
		if(Dao.class.isAssignableFrom(type)){
			String base = name.substring(0, name.length() - 3);
			registerQuery(base + ".list", new DaoListQuery(name, parametersBuilder, def));
			registerQuery(base + ".page", new DaoPageQuery(name, parametersBuilder, def));
			registerQuery(base + ".get", new DaoGetQuery(name, def));
			registerQuery(base + ".label", new LabelableQuery(name, parametersBuilder, def));
			
			//动态方法
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
							log.debug("方法定义为非公开、抽象或静态，不能作为通用查询使用：" + m);
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
							log.debug("方法定义为非公开、抽象或静态，不能作为通用查询使用：" + m);
						}
						return;
					}
					//return type
					if(void.class.equals(m.getReturnType())){
						if(IS_DEBUG_ENABLED){
							log.debug("方法定义返回值为void，不能作为通用查询使用：" + m);
						}
						return;
					}
					
					MethodQuery query = new MethodQuery(name, m, queryable.argNames(), parametersBuilder, def);
					registerQuery(name + "." + m.getName(), query);
					
					//如果有命名，则将命名查询也注册
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
	 * 根据一个SQL或者HQL定义的键值对集合来生成并注册查询。
	 * 尽量使用 {@link #setQueryMap(Map, Resource)}
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
			Assert.hasText(value, "查询服务配置不正确。");
			
			String[] array = value.split(";");
			Assert.hasText(array[0], "查询配置的查询语句不能为空。");
			
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
	 * 查询Spring容器中名称为 ”queries“ 的Map类型bean并创建和注册查询。
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
	
	//查找指定Bean的BeanDefinition中对应的Resource
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
	 * 查询Spring容器中名称为 ”queryProps“ 的Properties类型bean并创建和注册查询。
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
	 * 查找类路径中所有名称为 queries-*.properties的属性文件，并根据文件内容创建并注册查询。
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
			log.warn("没有注入statistics对象，创建默认对象，可能导致统计数据不正确。");
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
				throw new IllegalArgumentException("必须设置属性 ‘hibernateQuerySupport’");
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
		log.debug("查询是否启用统计：" + statistics.isStatisticsEnabled());
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
		//属性变化后重新设置
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
