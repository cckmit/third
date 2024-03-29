/*
 * $Id: ValidationInterceptor.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.aop.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.aop.Callback;
import cn.redflagsoft.base.aop.XWorkValidator;

import com.opensymphony.xwork2.ValidationAware;

/**
 * 验证拦截器。
 * 
 * @author Alex Lin
 */
public class ValidationInterceptor extends XWorkValidator implements MethodInterceptor  {

	public Object invoke(final MethodInvocation invocation) throws Throwable {
		Object object = invocation.getThis();
		String method = invocation.getMethod().getName();
		
		if(object instanceof ValidationAware){
			if(log.isDebugEnabled()){
				log = LogFactory.getLog(object.getClass());
				log.debug("Validating " + object.getClass().getName() + " method " + method);
			}
			
			Callback action = new Callback.MethodCallback(invocation);
			return validate(action, object, method);
		}
		return invocation.proceed();
	}
	/*
	static{
		if(ObjectFactory.getObjectFactory() == null){
			ObjectFactory.setObjectFactory(new ObjectFactory());
		}
	}
	
	private Log log = LogFactory.getLog(ValidationInterceptor.class);
	
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object object = invocation.getThis();
		String method = invocation.getMethod().getName();
		
		if(object instanceof ValidationAware){
			if(log.isDebugEnabled()){
				log = LogFactory.getLog(object.getClass());
				log.debug("Validating " + object.getClass().getName() + " method " + method);
			}
		
			boolean validationNonException = true;
			try {
				ActionValidatorManagerFactory.getInstance().validate(object, method);
			} catch (Exception e) {
				if(log.isDebugEnabled()){
					log.debug("验证出错", e);
				}
				validationNonException = false;
			}
			
			if(validationNonException){
				ValidationAware v = (ValidationAware) object;
				if(v.hasErrors()){
					Model m = new Model();
					m.setSuccess(false);
					if(v.hasActionErrors()){
						m.setMessage(v.getActionErrors().toString());
					}
					if(v.hasFieldErrors()){
						m.setErrors(v.getFieldErrors());
					}
					return m;
				}
			}
		}
		
		return invocation.proceed();
	}
	
	*/
	
	
	
}
