/*
 * $Id: ParametersInterceptor.java 4599 2011-08-18 07:14:12Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.aop.interceptor;

import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.aop.ParametersSetter;

import com.opensymphony.xwork2.interceptor.NoParameters;

/**
 * 参数设置拦截器。
 * @author Alex Lin
 *
 */
public class ParametersInterceptor extends ParametersSetter implements MethodInterceptor {
	/**
	 * 
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		//Object[] args = invocation.getArguments();
		Object object = invocation.getThis();
		Map<?, ?> parameters = getParameters(invocation);
		
		if(log.isDebugEnabled()){
			log = LogFactory.getLog(object.getClass());
		}
		
		//if(!(object instanceof NoParameters) && args.length >= 1 && args[0] != null && args[0] instanceof Map){
			//final Map parameters = (Map) args[0];

		if(parameters != null){
			
//            if (log.isDebugEnabled()) {
//                log.debug("ParametersInterceptor - [invoke]: Setting params " + getParameterLogMap(parameters));
//            }
            
            setParameters(object, parameters);
		}else{
			if(log.isDebugEnabled()){
				log.debug("ParametersInterceptor - [invoke]: 不符合条件，不设置参数。");
			}
		}
		return invocation.proceed();
	}
	
	/**
	 * 从invocation中查找相应的参数。
	 * 
	 * @param invocation
	 * @return
	 */
	protected Map<?, ?> getParameters(MethodInvocation invocation){
		Object[] args = invocation.getArguments();
		Object object = invocation.getThis();
		if(object instanceof NoParameters){
			return null;
		}
		if(object instanceof ParametersAware){
			return ((ParametersAware) object).getParameters();
		}
		if(args.length >= 1 && args[0] != null && args[0] instanceof Map){
			return (Map<?, ?>) args[0];
		}
		return null;
	}
}
