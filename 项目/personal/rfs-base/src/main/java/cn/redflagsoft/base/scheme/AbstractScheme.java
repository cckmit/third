/*
 * $Id: AbstractScheme.java 5136 2011-11-25 14:02:06Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.BeanNameAware;

import cn.redflagsoft.base.annotation.DisplayNameUtils;
import cn.redflagsoft.base.aop.annotation.Parameters;
import cn.redflagsoft.base.aop.interceptor.ParametersAware;

import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.ValidationAwareSupport;

/**
 * Scheme 抽象超类。
 * 
 * @author Alex Lin
 *
 */
public abstract class AbstractScheme implements Scheme, ParametersAware, ValidationAware, BeanNameAware {
	private ValidationAware validation = new ValidationAwareSupport();
	
	//参数总集合
	@SuppressWarnings("rawtypes")
	private Map parameters;
	private String displayName = DisplayNameUtils.getDisplayName(getClass());
	private String beanName;
	private String method = "scheme";
	
	@Parameters
	public Object doScheme() throws SchemeException {
		return null;
	}


	/**
	 * @return the parameters
	 */
	@SuppressWarnings("rawtypes")
	public Map getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(@SuppressWarnings("rawtypes") Map parameters) {
		this.parameters = parameters;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#addActionError(java.lang.String)
	 */
	public void addActionError(String anErrorMessage) {
		validation.addActionError(anErrorMessage);		
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#addActionMessage(java.lang.String)
	 */
	public void addActionMessage(String message) {
		validation.addActionMessage(message);
	}




	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#addFieldError(java.lang.String, java.lang.String)
	 */
	public void addFieldError(String fieldName, String errorMessage) {
		validation.addFieldError(fieldName, errorMessage);
	}




	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#getActionErrors()
	 */
	public Collection<?> getActionErrors() {
		return validation.getActionErrors();
	}




	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#getActionMessages()
	 */
	public Collection<?> getActionMessages() {
		return validation.getActionMessages();
	}




	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#getFieldErrors()
	 */
	public Map<?, ?> getFieldErrors() {
		return validation.getFieldErrors();
	}




	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#hasActionErrors()
	 */
	public boolean hasActionErrors() {
		return validation.hasActionErrors();
	}




	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#hasActionMessages()
	 */
	public boolean hasActionMessages() {
		return validation.hasActionMessages();
	}




	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#hasErrors()
	 */
	public boolean hasErrors() {
		return validation.hasErrors();
	}




	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#hasFieldErrors()
	 */
	public boolean hasFieldErrors() {
		return validation.hasFieldErrors();
	}




	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#setActionErrors(java.util.Collection)
	 */
	public void setActionErrors(@SuppressWarnings("rawtypes") Collection errorMessages) {
		validation.setActionErrors(errorMessages);
	}




	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#setActionMessages(java.util.Collection)
	 */
	public void setActionMessages(@SuppressWarnings("rawtypes") Collection messages) {
		validation.setActionMessages(messages);
	}




	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ValidationAware#setFieldErrors(java.util.Map)
	 */
	public void setFieldErrors(@SuppressWarnings("rawtypes") Map errorMap) {
		validation.setFieldErrors(errorMap);
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	/**
	 * @return the beanName
	 */
	public String getBeanName() {
		return beanName;
	}


	/**
	 * @param beanName the beanName to set
	 */
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}


	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}


	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
}
