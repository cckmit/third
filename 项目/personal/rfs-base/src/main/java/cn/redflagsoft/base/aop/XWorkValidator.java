/*
 * $Id: XWorkValidator.java 3993 2010-10-18 06:54:56Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.Model;

import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.validator.ActionValidatorManagerFactory;

/**
 * XWork验证器。可用于WorkScheme、WorkProcess等.
 * 
 * @author Alex Lin
 * @since 1.5
 */
public class XWorkValidator {
	
	static{
		if(ObjectFactory.getObjectFactory() == null){
			ObjectFactory.setObjectFactory(new ObjectFactory());
		}
	}
	
	protected Log log = LogFactory.getLog(XWorkValidator.class);
	
	
	@SuppressWarnings("unchecked")
	public Object validate(Callback action, Object object, String method) throws Throwable {
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
		
		return action.doInCallback();
	}
	
	
}
