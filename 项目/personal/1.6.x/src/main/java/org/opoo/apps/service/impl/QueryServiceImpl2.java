package org.opoo.apps.service.impl;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.Labelable;
import org.opoo.apps.LabeledBean;
import org.opoo.apps.QueryParameter;
import org.opoo.apps.QueryParameters;
import org.opoo.apps.annotation.Queryable;
import org.opoo.apps.dao.LabelableDao;
import org.opoo.apps.exception.QueryException;
import org.opoo.apps.service.QueryService;
import org.opoo.cache.TimedExpirationMap;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.SpringSecurityException;
import org.springframework.util.Assert;


/**
 * 通用查询实现类。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class QueryServiceImpl2 implements QueryService, ApplicationContextAware, InitializingBean {
	protected final Log log = LogFactory.getLog(getClass());


	private ApplicationContext applicationContext;
	
	/**
	 * 对象是否使用 prototype 来定义。
	 */
	private boolean prototype = false;
	
	/**
	 * 存在的对象的名称，在 usePrototype = true 时，使用这个缓存。
	 */
	private Map<String,String> beanNames = new HashMap<String,String>();
	/**
	 * 查询目标对象的缓存，在 usePrototype = false 时，使用这个缓存。
	 */
	private Map<String,Object> beans = new HashMap<String,Object>();
	/**
	 * 不存在的对象的缓存，为了快捷查找。
	 */
	private Map<String,Integer> notExistsBeans = new TimedExpirationMap<String,Integer>("query_notExistsBeans", 30 * 60 * 1000, 20 * 60 * 1000);
	
	/**
	 * 查询调用方法的缓存。
	 */
	private Map<String,Method> methods = new HashMap<String,Method>();
	
	/**
	 * 不存在的方法，为了快捷查找。
	 */
	private Map<String,Integer> notExistsMethods = new TimedExpirationMap<String,Integer>("query_notExistsMethods", 30 * 60 * 1000, 20 * 60 * 1000);
	
	
	private ParametersBuilder parametersBuilder;
	
	

	/**
	 * @return the prototype
	 */
	public boolean isPrototype() {
		return prototype;
	}

	/**
	 * @param prototype the prototype to set
	 */
	public void setPrototype(boolean prototype) {
		this.prototype = prototype;
	}

	/**
	 * @return the parametersBuilder
	 */
	public ParametersBuilder getParametersBuilder() {
		return parametersBuilder;
	}

	/**
	 * @param parametersBuilder the parametersBuilder to set
	 */
	public void setParametersBuilder(ParametersBuilder parametersBuilder) {
		this.parametersBuilder = parametersBuilder;
	}

	/**
	 * @return the applicationContext
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	
	
	
	public Object invoke(String target, String methodName, QueryParameters parameters){
		beforeQuery(target, parameters);
		
		Object bean = getBean(target);
		Method method = getMethod(target, methodName, bean);
		return invoke(bean, method, parameters);
	}
	
	/**
	 * 
	 * @param bean
	 * @param method
	 * @param parameters
	 * @return
	 */
	protected Object invoke(Object bean, Method method, QueryParameters parameters){
		//执行方法前先剔除无效的查询参数
//		parameters = new ValidQueryParameters(parameters);
//		ValidQueryParametersUtils.removeInvalidParamerter(parameters.getParameters());
//		if(log.isDebugEnabled()){
//			log.debug("剔除后参数：" + parameters.getParameters());
//		}
		
		Class<?>[] parameterTypes = method.getParameterTypes();
		Object[] args = new Object[parameterTypes.length];
		Queryable queryable = method.getAnnotation(Queryable.class);
		
		
		//参数是 ResultFilter 类型的
		if(parameterTypes.length == 1 && parameterTypes[0].equals(ResultFilter.class)){
			args[0] = buildResultFilter(bean, method, parameters);
		}else if(queryable != null){//其他类型，有 Queryable 的注释。
			List<QueryParameter> list = parameters.getParameters();
			ValidQueryParametersUtils.removeInvalidParamerter(list);
			String[] names = queryable.argNames();
			if(names.length != parameterTypes.length){
				throw new IllegalArgumentException("查询参数不正确，个数不匹配：" + method);
			}
			
			for (int i = 0; i < names.length; i++) {
				String name = names[i];
				if("start".equals(name)){
					args[i] = parameters.getStart();
				}else if("limit".equals(name)){
					args[i] = parameters.getMaxResults();
				}else if("sort".equals(name)){
					args[i] = parameters.getSort();
				}else if("dir".equals(name)){
					args[i] = parameters.getDir();
				}else if("order".equalsIgnoreCase(name)){
					args[i] = parameters.getOrder();
				}else{
					QueryParameter parameter = getParameter(list, name);
					if(parameter == null){
						args[i] = null;
					}else{
						Class<?> parameterType = parameterTypes[i];
						Class<?> argType = parameterType;// String.class;
						if(parameterType.isArray()){
							argType = parameterType.getComponentType();
						}
//						if(argType == null && parameter.getTypeClass() != null){
//							argType = parameter.getTypeClass();
//						}
						log.debug("::Arg type: " + argType);
						
						QueryParameter arg = new QueryParameter(parameter.getN(), parameter.getV(), parameter.getO(), argType);
						
						//args[i] = extractValue(bean, method, parameter);
						args[i] = extractValue(bean, method, arg);
					}
				}
			}
		}else{//其他，不支持
			log.error("不支持调用该查询方法：" + method + "，既不是 Dao 类也没有标注 Queryable 。");
			throw new QueryException("无法执行查询：" + method);
		}
		
		//执行方法
		try {
			return method.invoke(bean, args);
		}catch(InvocationTargetException e){ 
			Throwable exception = e.getTargetException();
			if(exception instanceof SpringSecurityException){
				throw (SpringSecurityException) exception;
			}else{
				throw new QueryException("执行查询错误: " + method, e);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new QueryException("执行查询错误：" + method, e);
		}
	}
		
	protected ResultFilter buildResultFilter(Object bean, Method method, QueryParameters parameters){
		ResultFilter rf = parametersBuilder.buildResultFilter(bean, parameters);
		//process in parametersBuilder.buildResultFilter
		//rf.setRawParameters(parameters.getRawParameters());
		return rf;
	}
	
	protected Object extractValue(Object bean, Method method, QueryParameter parameter){
		ResolvedParameter rp = parametersBuilder.resolveParameter(bean, parameter);
		Object[] values = rp.getValues();
		if(values == null || values.length == 0){
			log.warn("查询参数为空，可能导致不正确的查询。");
			return null;
		}
		return values[0];
	}
	
	protected Object convertValue(String value, Class<?> clazz){
		return parametersBuilder.convertValue(value, clazz);
	}
	
	protected Object getBean(String target){
		return prototype ? getPrototypeBean(target) : getNonPrototypeBean(target);
	}

	/**
	 * 这个方法缓存的是 target 在 Spring 容器中对应的名称，而不是具体对象。
	 * @param target
	 * @return
	 */
	protected Object getPrototypeBean(String target){
		//如果‘不存在’缓存中发现该bean是找不到的，则不必再查找，直接报错
		if(notExistsBeans.containsKey(target)){
			log.error("无法执行查询, 找不到目标对象: " + target);
			throw new QueryException("无法执行查询");// + beanName + "." + methodName);
		}
		String name = beanNames.get(target);
		Object bean = null;
		if(name != null){
			bean = getSpringBean(name);
			if(bean != null){
				return bean;
			}
		}else{
			bean = getSpringBean(target + "Dao");
			if(bean != null){
				beanNames.put(target, target + "Dao");
				return bean;
			}
			bean = getSpringBean(target);
			if(bean != null){
				beanNames.put(target, target);
				return bean;
			}
		}
		
		//缓存不存在的
		notExistsBeans.put(target, 1);
		log.error("无法执行查询, 找不到目标对象: " + name);
		throw new QueryException("无法执行查询, 找不到目标对象。");
	}
	/**
	 * 
	 * @param target
	 * @return
	 * @throws QueryException
	 */
	protected Object getNonPrototypeBean(String target) throws QueryException{
		//如果‘不存在’缓存中发现该bean是找不到的，则不必再查找，直接报错
		if(notExistsBeans.containsKey(target)){
			log.error("无法执行查询, 找不到目标对象: " + target);
			throw new QueryException("无法执行查询");// + beanName + "." + methodName);
		}
		Object bean = beans.get(target);
		//若果‘存在’缓存中找不到，则执行查找
		if(bean == null){
			bean = getSpringBean(target + "Dao");
			if(bean == null){
				bean = getSpringBean(target);
			}
			if(bean == null){
				notExistsBeans.put(target, 1);
				log.error("无法执行查询, 找不到目标对象: " + target);
				throw new QueryException("无法执行查询, 找不到目标对象。");
			}else{			
				beans.put(target, bean);
			}
		}
		return bean;
	}
	
	private Object getSpringBean(String name){
		try {
			return applicationContext.getBean(name);
		} catch (BeansException e) {
			if(log.isDebugEnabled()){
				log.debug("查找对象：" + e.getMessage());
			}
			return null;
		}
	}
	

	
	protected Method getMethod(String beanName, String methodName, Object bean) {
		String key = beanName + "." + methodName;
		if(notExistsMethods.containsKey(key)){
			log.error("无法执行查询，找不到方法: " + key);
			throw new QueryException("无法执行查询，找不到方法。");
		}
		
		Method m = methods.get(key);
		if(m == null){
			m = findMethod(bean.getClass(), methodName);
			if(m != null){
				methods.put(key, m);
			}else{
				notExistsMethods.put(key, 1);
				log.error("无法执行查询，找不到方法: " + key);
				throw new QueryException("无法执行查询，找不到方法。");
			}
		}
		
		return m;
	}
	
	private Method findMethod(Class<?> clazz, String methodName){
		try {
			return clazz.getMethod(methodName, ResultFilter.class);
		} catch (SecurityException e) {
			log.error("无法获取指定方法：" + clazz.getName() + "." + methodName, e);
			throw new QueryException("无法执行查询", e);
		} catch (NoSuchMethodException e) {
			log.debug(e.toString());
		}
		
		try {
			Method[] methods = clazz.getDeclaredMethods();
			for (Method m : methods) {
				if(Modifier.isPublic(m.getModifiers()) && m.getName().equals(methodName)){
					return m;
				}
			}
		} catch (SecurityException e) {
			log.error("无法获取指定方法：" + clazz.getName() + "." + methodName, e);
			throw new QueryException("无法执行查询", e);
		}
		
		return null;
	}
	
	public void clear(){
		this.beanNames.clear();
		this.beans.clear();
		this.notExistsBeans.clear();
		this.notExistsMethods.clear();
		this.methods.clear();
	}
	
	private QueryParameter getParameter(List<QueryParameter> parameters, String name){
		for (QueryParameter p : parameters) {
			if(name.equals(p.getN())){
				return p;
			}
		}
		return null;
	}
	
	
	
//	
//	/**
//	 * 
//	 * @author Alex Lin(alex@opoo.org)
//	 *
//	 */
//	public static interface DaoQueryCallback{
//		Object execute(Dao<?,?> dao, ResultFilter resultFilter) throws Exception;
//	}
//	/**
//	 * 
//	 * @author Alex Lin(alex@opoo.org)
//	 *
//	 */
//	public static interface QueryCallback{
//		Object execute(ResultFilter resultFilter) throws Exception;
//	}
//	
	/**
	 * 
	 * @author Alex Lin(alex@opoo.org)
	 *
	 */
	public static class QueryString{
		
		public String selectQueryString;
		public String countQueryString;
		public boolean isSQLQuery = false;
		public QueryString(String selectQueryString){
			this.selectQueryString = selectQueryString;
		}
		public QueryString(String selectQueryString, String countQueryString){
			this.selectQueryString = selectQueryString;
			this.countQueryString = countQueryString;
		}
		public QueryString(String selectQueryString, String countQueryString, boolean isSQLQuery){
			this.selectQueryString = selectQueryString;
			this.countQueryString = countQueryString;
			this.isSQLQuery = isSQLQuery;
		}		
	}
	
	
	
	
	public static final String QUERYSTRING_MAP_KEY = "queries";
	public static final String QUERYSTRING_PROPS_KEY = "queryProps";
	
	
	

	//配置变量
	private List<String> excludeTargets; 
	private Map<String, QueryString> queryStrings = new HashMap<String, QueryString>();
//	private QueryParametersResolver queryParametersResolver = new DefaultQueryParametersResolver();
//	
//	
//	
//	protected Criterion parametersToCriterion(List<QueryParameter> queryParameters, Dao<?,?> dao){
//		return queryParametersResolver.resolve(queryParameters, dao);
//	}
//	protected ResultFilter parametersToResultFilter(QueryParameters parameters, Dao<?,?> dao){
//		Criterion c = parametersToCriterion(parameters.getParameters(), dao);
//		return new ResultFilter(c, parameters.getOrder(), parameters.getStart(), parameters.getMaxResults());
//	}
//	
	protected void beforeQuery(String target, QueryParameters parameters) throws QueryException{
		if(isExcudeTarget(target)){
			throw new AccessDeniedException("不允许使用通用查询访问此类对象：" + target);
		}
	}
//	
//	protected Object handleResult(String target, QueryParameters parameters, Object result) throws Exception{
//		return result;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	@SuppressWarnings("unchecked")
//	protected Dao<?,?> lookupDao(String target) throws Exception{
//		String daoName = target + "Dao";
//		Object bean = applicationContext.getBean(daoName);
//		
//		if(bean instanceof Dao){
//			return (Dao<?, ?>) bean;
//		}
//		//is null or other type
//		throw new ObjectNotFoundException("找不到处理数据的DAO: " + daoName);
//	}
//	
//	protected Object execute(String target, QueryParameters parameters, DaoQueryCallback callback) throws Exception{
//		beforeQuery(target, parameters);
//		
//		Dao<?, ?> dao = lookupDao(target);
//		ResultFilter filter = parametersToResultFilter(parameters, dao);
//		
//		Object result = callback.execute(dao, filter);
//		
//		return handleResult(target, parameters, result);
//	}
//	
//	protected Object execute(String target, QueryParameters parameters, QueryCallback callback) throws Exception{
//		beforeQuery(target, parameters);
//		
//		ResultFilter filter = parametersToResultFilter(parameters, null);
//		
//		Object result = callback.execute(filter);
//		
//		return handleResult(target, parameters, result);
//	}
//	
//
//	
//
	
	@SuppressWarnings("unchecked")
	public <L extends List<?>> L query(String target, final String methodName, QueryParameters parameters, Class<L> expectedResultType) throws QueryException {
		if(methodName == null){
			throw new IllegalArgumentException("没有指定方法名");
		}
		Object result = invoke(target, methodName, parameters);
		
		//判断执行结果的类型是不是正确的。
		if(expectedResultType.isInstance(result)){
			return (L) result;
		}
		throw new QueryException("方法执行返回结果不正确");
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Labelable> queryLabelables(String target, QueryParameters parameters) throws QueryException{
		beforeQuery(target, parameters);
		Object bean = getBean(target);
		if(bean instanceof LabelableDao){
			Method m = getMethod(target, "findLabelables", bean);
			return (List<Labelable>) invoke(bean, m, parameters);
		}else{
			Method m = getMethod(target, "find", bean);
			List<?> list = (List<?>) invoke(bean, m, parameters);
			return listItemToLabeledDomain(list);
		}
	}
	
	private List<Labelable> listItemToLabeledDomain(List<?> list){
		List<Labelable> beans = new ArrayList<Labelable>();
		if(list.size() > 0){
			for(int i = 0 ; i < list.size() ; i++){
				Object o = list.get(i);
				if(o instanceof Labelable){
					Labelable bean = (Labelable) o;
					beans.add(new LabeledBean(bean.getLabel(), bean.getData()));
				}else{
					log.warn("对象不是有效的LabeledBean: " + o);
				}
			}
		}
		return beans;
	}
		
	
	
	protected QueryString getQueryString(String name){
		QueryString string = queryStrings.get(name);
		if(string == null){
			throw new IllegalArgumentException("找不到查询的定义：" + name);
		}
		return string;
	}
	
	/**
	 * @return the queryStrings
	 */
	public Map<String, QueryString> getQueryStrings() {
		return queryStrings;
	}
	/**
	 * @param queryStrings the queryStrings to set
	 */
	public void setQueryStrings(Map<String, QueryString> queryStrings) {
		this.queryStrings = queryStrings;
	}
	
	
	
	public void setQueries(Map<String, String> queries) {
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

			boolean overlay = queryStrings.containsKey(key);
			if(array.length >=2 && StringUtils.isNotBlank(array[1])){
				queryStrings.put(key, new QueryString(array[0], array[1], isSQLQuery));
			}else{
				queryStrings.put(key, new QueryString(array[0], null, isSQLQuery));
			}
			
			if(log.isDebugEnabled()){
				log.debug((overlay ? "覆盖" : "") + (isSQLQuery ? "SQL" : "") + "查询[" + key + "]: " + value);
			}
		}
	}
	
	



	@SuppressWarnings("unchecked")
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
		try {
			Map<String, String> map = (Map<String, String>) applicationContext.getBean(QUERYSTRING_MAP_KEY);
			if(map != null){
				setQueries(map);
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		
		try {
			Properties props = (Properties) applicationContext.getBean(QUERYSTRING_PROPS_KEY);
			if(props != null){
				@SuppressWarnings("rawtypes")
				Map<String,String> map = new HashMap(props);
				setQueries(map);
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void afterPropertiesSet() throws Exception {
		log.debug("查询配置");

		//TODO
		DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
		ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
		Resource[] resources = resolver.getResources("classpath*:queries-*.properties");
		if(resources != null && resources.length > 0){
			for (Resource resource : resources) {
				//System.out.println(resource);
				Properties props = PropertiesLoaderUtils.loadProperties(resource);
				if(props != null){
					setQueries(new HashMap(props));
					log.debug("Overlay queries.properties by " + resource);
				}
			}
		}else{
			log.debug("No overlays found.");
//			try {
//				System.out.println(getClass().getResourceAsStream("queries.properties"));
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			try {
//				System.out.println(this.getClass().getResourceAsStream("queries-ppms.properties"));
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
	
	/**
	 * 
	 * @param target
	 * @return
	 */
	protected boolean isExcudeTarget(String target){
		return excludeTargets != null && excludeTargets.contains(target);
	}
	
	/**
	 *  @return the excludeTargets
	 */
	public List<String> getExcludeTargets() {
		return excludeTargets;
	}

	/**
	 * @param excludeTargets the excludeTargets to set
	 */
	public void setExcludeTargets(List<String> excludeTargets) {
		this.excludeTargets = excludeTargets;
	}
	

//	
//	
//	
//	/* (non-Javadoc)
//	 * @see org.opoo.apps.service.QueryService#invoke(java.lang.String, java.lang.String, org.opoo.apps.QueryParameters)
//	 */
//	public Object invoke2(String beanName, String methodName, QueryParameters parameters) {
//		Object bean = applicationContext.getBean(beanName);
//		if(bean == null){
//			throw new QueryException(beanName, methodName);
//		}
//		//查找方法
//		Method method = lookupMethod(bean.getClass(), methodName);
//		if(method == null){
//			throw new QueryException(beanName, methodName);
//		}
//		//查找方法注释
//		Queryable queryable = method.getAnnotation(Queryable.class);
//		if(queryable == null){
//			throw new QueryException("查询服务无法访问指定的方法");
//		}
//		
//		//组装方法参数
//		Class<?>[] parameterTypes = method.getParameterTypes();
//		Object[] args = new Object[parameterTypes.length];
//		if(parameterTypes.length == 1 && parameterTypes[0].equals(ResultFilter.class)){
//			Criterion criterion = queryParametersResolver.resolve(parameters.getParameters(), queryable);
//			ResultFilter filter = new ResultFilter(criterion, parameters.getOrder(), parameters.getStart(), parameters.getMaxResults());
//			args[0] = filter;
//		}else{
//			List<QueryParameter> list = parameters.getParameters();
//			String[] names = queryable.argNames();
//			if(names.length != parameterTypes.length){
//				throw new IllegalArgumentException("查询参数不正确，不匹配");
//			}
//			for (int i = 0; i < names.length; i++) {
//				String name = names[i];
//				if("start".equals(name)){
//					args[i] = parameters.getStart();
//				}else if("limit".equals(name)){
//					args[i] = parameters.getMaxResults();
//				}else if("sort".equals(name)){
//					args[i] = parameters.getSort();
//				}else if("dir".equals(name)){
//					args[i] = parameters.getDir();
//				}else if("order".equalsIgnoreCase(name)){
//					args[i] = parameters.getOrder();
//				}else{
//					String value = getValue(names[i], list);
//					if(value == null){
//						args[i] = null;
//					}else{
//						args[i] = queryParametersResolver.convert(value, parameterTypes[i]);
//					}
//				}
//			}
//		}
//		
//		
//		//执行方法
//		try {
//			return method.invoke(bean, args);
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//			throw new QueryException(beanName, methodName, e);
//		} 
//	}
//	
//	/**
//	 * 查找指定方法
//	 * 
//	 * @param clazz
//	 * @param methodName
//	 * @return
//	 */
//	private Method lookupMethod(Class<?> clazz, String methodName){
//		Method method = null;
//		boolean noFilterMethod = false;
//		try {
//			method =  clazz.getMethod(methodName, ResultFilter.class);//clazz.getDeclaredMethod(methodName, ResultFilter.class);
//		} catch (SecurityException e) {
//			log.error(e.getMessage(), e);
//			throw new QueryException(clazz.getName(), methodName, e);
//		} catch (NoSuchMethodException e) {
//			//System.out.println(e);
//			noFilterMethod = true;
//		}
//		
//		if(noFilterMethod){
//			try {
//				Method[] methods = clazz.getDeclaredMethods();
//				for (Method m : methods) {
//					//System.out.println(m.getName() + ":" + m.getModifiers());
//					if(m.getModifiers() == 1 && m.getName().equals(methodName)){
//						method = m;
//						break;
//					}
//				}
//			} catch (SecurityException e) {
//				log.error(e.getMessage(), e);
//				throw new QueryException(clazz.getName(), methodName, e);
//			}
//		}
//		
//		return method;
//	}
//	
//	/**
//	 * 查找参数值
//	 * 
//	 * @param name
//	 * @param parameters
//	 * @return
//	 */
//	private String getValue(String name, List<QueryParameter> parameters){
//		for (QueryParameter p : parameters) {
//			if(p.getN().equals(name)){
//				return p.getV();
//			}
//		}
//		return null;
//	}
	

	
	
	
	public static interface ParametersBuilder{
		/**
		 * 创建过滤器。
		 * 
		 * @param bean
		 * @param parameters
		 * @return
		 */
		ResultFilter buildResultFilter(Object bean, QueryParameters parameters);
//		/**
//		 * 获取参数值。
//		 * 
//		 * @param bean
//		 * @param parameter
//		 * @return
//		 */
//		Object extractValue(Object bean, QueryParameter parameter);
		
		
		/**
		 * 
		 * @param value
		 * @param clazz
		 * @return
		 */
		Object convertValue(String value, Class<?> clazz);
		
		
		/**
		 * 解析查询参数。
		 * 
		 * @param bean
		 * @param parameter
		 * @return
		 */
		ResolvedParameter resolveParameter(Object bean, QueryParameter parameter);
		
	}
	
	
	public static class ResolvedParameter implements Serializable{
		private static final long serialVersionUID = -5501747751755556587L;
		private final QueryParameter queryParameter;
		private final String name;
		private String operator;
		private Object[] values;
		private Class<?> typeClass;
		
		/**
		 * @return the operator
		 */
		public String getOperator() {
			return operator;
		}

		/**
		 * @param operator the operator to set
		 */
		public ResolvedParameter setOperator(String operator) {
			this.operator = operator;
			return this;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		public ResolvedParameter(QueryParameter param){
			this.queryParameter = param;
			this.name = param.getN();
			this.operator = param.getO();
			this.typeClass = param.getTypeClass();
		}
		
		public ResolvedParameter(QueryParameter param, Object[] values){
			this(param);
			this.values = values;
		}
		
		public ResolvedParameter(QueryParameter param, Object[] values, Class<?> typeClass){
			this(param, values);
			this.typeClass = typeClass;
		}
		
		
		/**
		 * @return the queryParameter
		 */
		public QueryParameter getQueryParameter() {
			return queryParameter;
		}

		/**
		 * @return the typeClass
		 */
		public Class<?> getTypeClass() {
			return typeClass;
		}
		/**
		 * @param typeClass the typeClass to set
		 */
		public ResolvedParameter setTypeClass(Class<?> typeClass) {
			this.typeClass = typeClass;
			return this;
		}


		/**
		 * @return the values
		 */
		public Object[] getValues() {
			return values;
		}

		/**
		 * @param values the values to set
		 */
		public ResolvedParameter setValues(Object[] values) {
			this.values = values;
			return this;
		}
		
		
	}
	
	
	
	
	public static void main(String[] args) throws IOException{
		int[] a = {1,2,3,4};
		Integer aa = 0;
		System.out.println(aa.getClass().getComponentType());
		System.out.println(a.getClass().isArray());
		DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
		ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
		Resource[] resources = resolver.getResources("classpath:spring-*.xml");
		for (Resource resource : resources) {
			System.out.println(resource);
		}
//		Properties properties = PropertiesLoaderUtils.loadProperties(null);
		
	}
}
