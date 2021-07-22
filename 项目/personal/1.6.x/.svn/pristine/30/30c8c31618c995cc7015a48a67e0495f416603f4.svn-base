package org.opoo.apps.query.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.QueryParameters;
import org.opoo.apps.exception.QueryException;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.query.ParametersBuilder;
import org.opoo.apps.query.Query;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.security.SpringSecurityException;

public class MethodQuery implements Query<Object>, BeanDefinitionHolder {
	private static final Log log = LogFactory.getLog(MethodQuery.class);
	
	private final String name;
	private final String description;
	private final ParametersBuilder builder;
	private final Method m;
	private final String[] parameterNames;
	private final BeanDefinition beanDefinition;
	
	public MethodQuery(String name, Method m, String[] parameterNames, ParametersBuilder builder, BeanDefinition beanDefinition) {
		super();
		this.name = name;
		this.builder = builder;
		this.m = m;
		this.parameterNames = parameterNames;
		this.beanDefinition = beanDefinition;
		
		//description
		Class<?>[] parameterTypes = m.getParameterTypes();
		StringBuffer sb = new StringBuffer();
		sb.append(name).append("#").append(m.getName()).append("(");
		boolean showNames = parameterNames != null && parameterNames.length == parameterTypes.length;
		for(int i = 0 ; i < parameterTypes.length ; i++){
			if(i > 0){
				sb.append(", ");
			}
			sb.append(parameterTypes[i].getSimpleName());
			if(showNames){
				sb.append(" ").append(parameterNames[i]);
			}
		}
		sb.append(")");
		this.description = sb.toString();
	}

	public Object execute(QueryParameters parameters) throws QueryException {
		Object bean = Application.getContext().getBean(name);
		Class<?>[] parameterTypes = m.getParameterTypes();
		Object[] objects = builder.buildParameters(bean, parameters, parameterNames, parameterTypes);

		//执行方法
		try {
			return m.invoke(bean, objects);
		}catch(InvocationTargetException e){ 
			Throwable exception = e.getTargetException();
			if(exception instanceof SpringSecurityException){
				throw (SpringSecurityException) exception;
			}else{
				throw new QueryException("执行查询错误: " + m, e);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new QueryException("执行查询错误：" + m, e);
		}
	}
	
	public String toString(){
		return description;
//		return name + "#" + m.getName();
	}

	public String getName() {
		return name;
	}

	public BeanDefinition getBeanDefinition() {
		return beanDefinition;
	}
	
	public Method getMethod(){
		return m;
	}

	public String[] getParameterNames() {
		return parameterNames;
	}
}
