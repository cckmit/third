package org.opoo.apps.service.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import org.opoo.apps.service.QueryParametersResolver;
import org.opoo.apps.service.QueryService;
import org.opoo.ndao.Dao;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.AccessDeniedException;
import org.springframework.util.Assert;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated using QueryServiceImpl2
 */
public abstract class QueryServiceImpl implements QueryService, ApplicationContextAware {
	/**
	 * 
	 * @author Alex Lin(alex@opoo.org)
	 *
	 */
	public static interface DaoQueryCallback{
		Object execute(Dao<?,?> dao, ResultFilter resultFilter) throws QueryException;
	}
	/**
	 * 
	 * @author Alex Lin(alex@opoo.org)
	 *
	 */
	public static interface QueryCallback{
		Object execute(ResultFilter resultFilter) throws QueryException;
	}
	
	/**
	 * 
	 * @author Alex Lin(alex@opoo.org)
	 *
	 */
	public static class QueryString{
		
		public String selectQueryString;
		public String countQueryString;
		public QueryString(String queryString){
			this.selectQueryString = queryString;
		}
		public QueryString(String selectQueryString, String countQueryString){
			this.selectQueryString = selectQueryString;
			this.countQueryString = countQueryString;
		}		
	}
	
	
	
	
	public static final String QUERYSTRING_MAP_KEY = "queries";
	public static final String QUERYSTRING_PROPS_KEY = "queryProps";
	
	
	
	private static final Log log = LogFactory.getLog(QueryServiceImpl.class);
	
	
	private ApplicationContext applicationContext;
	//配置变量
	private List<String> excludeTargets; 
	private Map<String, QueryString> queryStrings = new HashMap<String, QueryString>();
	private QueryParametersResolver queryParametersResolver = new DefaultQueryParametersResolver();
	
	
	
	protected Criterion parametersToCriterion(List<QueryParameter> queryParameters, Dao<?,?> dao){
		return queryParametersResolver.resolve(queryParameters, dao);
	}
	protected ResultFilter parametersToResultFilter(QueryParameters parameters, Dao<?,?> dao){
		Criterion c = parametersToCriterion(parameters.getParameters(), dao);
		return new ResultFilter(c, parameters.getOrder(), parameters.getStart(), parameters.getMaxResults());
	}
	
	protected void beforeQuery(String target, QueryParameters parameters) throws QueryException{
		if(isExcudeTarget(target)){
			throw new AccessDeniedException("不允许使用通用查询访问此类对象：" + target);
		}
	}
	
	protected Object handleResult(String target, QueryParameters parameters, Object result) throws QueryException{
		return result;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected Dao<?,Serializable> lookupDao(String target) throws QueryException{
		String daoName = target + "Dao";
		Object bean = applicationContext.getBean(daoName);
		
		if(bean instanceof Dao){
			return (Dao<?, Serializable>) bean;
		}
		//is null or other type
		throw new QueryException("找不到处理数据的DAO: " + daoName);
	}
	
	protected Object execute(String target, QueryParameters parameters, DaoQueryCallback callback) throws QueryException{
		beforeQuery(target, parameters);
		
		Dao<?, Serializable> dao = lookupDao(target);
		ResultFilter filter = parametersToResultFilter(parameters, dao);
		
		Object result = callback.execute(dao, filter);
		
		return handleResult(target, parameters, result);
	}
	
	protected Object execute(String target, QueryParameters parameters, QueryCallback callback) throws QueryException{
		beforeQuery(target, parameters);
		
		ResultFilter filter = parametersToResultFilter(parameters, null);
		
		Object result = callback.execute(filter);
		
		return handleResult(target, parameters, result);
	}
	

	

	
	@SuppressWarnings("unchecked")
	public <L extends List<?>> L query(String target, final String methodName, QueryParameters parameters, Class<L> expectedResultType) throws QueryException {
		if(methodName == null){
			throw new IllegalArgumentException("没有指定方法名");
		}
		Object result = execute(target, parameters, new DaoQueryCallback(){
			public Object execute(Dao<?, ?> dao, ResultFilter filter)  throws QueryException{
				if("find".equals(methodName)){
					return dao.find(filter);
				}
				
				if("findPageableList".equals(methodName)){
					return dao.findPageableList(filter);
				}
				
				
				try{
					Method m = dao.getClass().getMethod(methodName, ResultFilter.class);
					return m.invoke(dao, filter);
				}catch(InvocationTargetException e){
					Throwable t = e.getTargetException();
					if(t instanceof Exception){
						throw new QueryException(t);
					}else{
						throw new QueryException(e);
					}
				} catch (SecurityException e) {
					throw new QueryException(e);
				} catch (NoSuchMethodException e) {
					throw new QueryException(e);
				} catch (IllegalArgumentException e) {
					throw new QueryException(e);
				} catch (IllegalAccessException e) {
					throw new QueryException(e);
				}
			}
		});
		
		//判断执行结果的类型是不是正确的。
		if(expectedResultType.isInstance(result)){
			return (L) result;
		}
		throw new IllegalArgumentException("方法执行返回结果不正确");
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Labelable> queryLabelables(String target, QueryParameters parameters) throws QueryException{
		return (List<Labelable>) execute(target, parameters, new DaoQueryCallback(){
			public Object execute(Dao<?, ?> dao, ResultFilter resultFilter)	throws QueryException {
				if(dao instanceof LabelableDao){
					return ((LabelableDao) dao).findLabelables(resultFilter);
				}else{
					List<?> list = dao.find(resultFilter);
					return listItemToLabeledDomain(list);
				}
			}
		});
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
	
	
	public abstract List<?> namedQueryList(String name, QueryParameters parameters) throws QueryException;
	
	public abstract PageableList<?> namedQueryPageableList(String name, QueryParameters parameters) throws QueryException;
	
	public abstract Serializable get(String target, String id)throws QueryException;

	
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
			if(array.length >=2 && StringUtils.isNotBlank(array[1])){
				queryStrings.put(key, new QueryString(array[0], array[1]));
			}else{
				queryStrings.put(key, new QueryString(array[0]));
			}

			log.debug("查询[" + key + "]: " + value);
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
	/**
	 * @return the queryParametersResolver
	 */
	public QueryParametersResolver getQueryParametersResolver() {
		return queryParametersResolver;
	}
	/**
	 * @param queryParametersResolver the queryParametersResolver to set
	 */
	public void setQueryParametersResolver(
			QueryParametersResolver queryParametersResolver) {
		this.queryParametersResolver = queryParametersResolver;
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.opoo.apps.service.QueryService#invoke(java.lang.String, java.lang.String, org.opoo.apps.QueryParameters)
	 */
	public Object invoke(String beanName, String methodName, QueryParameters parameters) {
		Object bean = applicationContext.getBean(beanName);
		if(bean == null){
			throw new QueryException(beanName, methodName);
		}
		//查找方法
		Method method = lookupMethod(bean.getClass(), methodName);
		if(method == null){
			throw new QueryException(beanName, methodName);
		}
		//查找方法注释
		Queryable queryable = method.getAnnotation(Queryable.class);
		if(queryable == null){
			throw new QueryException("查询服务无法访问指定的方法");
		}
		
		//组装方法参数
		Class<?>[] parameterTypes = method.getParameterTypes();
		Object[] args = new Object[parameterTypes.length];
		if(parameterTypes.length == 1 && parameterTypes[0].equals(ResultFilter.class)){
			Criterion criterion = queryParametersResolver.resolve(parameters.getParameters(), queryable);
			ResultFilter filter = new ResultFilter(criterion, parameters.getOrder(), parameters.getStart(), parameters.getMaxResults());
			args[0] = filter;
		}else{
			List<QueryParameter> list = parameters.getParameters();
			String[] names = queryable.argNames();
			if(names.length != parameterTypes.length){
				throw new IllegalArgumentException("查询参数不正确，不匹配");
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
					String value = getValue(names[i], list);
					if(value == null){
						args[i] = null;
					}else{
						args[i] = queryParametersResolver.convert(value, parameterTypes[i]);
					}
				}
			}
		}
		
		
		//执行方法
		try {
			return method.invoke(bean, args);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new QueryException(beanName, methodName, e);
		} 
	}
	
	/**
	 * 查找指定方法
	 * 
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	private Method lookupMethod(Class<?> clazz, String methodName){
		Method method = null;
		boolean noFilterMethod = false;
		try {
			method =  clazz.getDeclaredMethod(methodName, ResultFilter.class);
		} catch (SecurityException e) {
			log.error(e.getMessage(), e);
			throw new QueryException(clazz.getName(), methodName, e);
		} catch (NoSuchMethodException e) {
			//System.out.println(e);
			noFilterMethod = true;
		}
		
		if(noFilterMethod){
			try {
				Method[] methods = clazz.getDeclaredMethods();
				for (Method m : methods) {
					//System.out.println(m.getName() + ":" + m.getModifiers());
					if(m.getModifiers() == 1 && m.getName().equals(methodName)){
						method = m;
						break;
					}
				}
			} catch (SecurityException e) {
				log.error(e.getMessage(), e);
				throw new QueryException(clazz.getName(), methodName, e);
			}
		}
		
		return method;
	}
	
	/**
	 * 查找参数值
	 * 
	 * @param name
	 * @param parameters
	 * @return
	 */
	private String getValue(String name, List<QueryParameter> parameters){
		for (QueryParameter p : parameters) {
			if(p.getN().equals(name)){
				return p.getV();
			}
		}
		return null;
	}
	
}
