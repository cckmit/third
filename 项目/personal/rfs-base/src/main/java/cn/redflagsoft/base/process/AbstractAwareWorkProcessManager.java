/*
 * $Id: AbstractAwareWorkProcessManager.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.process;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author Alex Lin
 *
 */
public abstract class AbstractAwareWorkProcessManager extends SimpleWorkProcessManager implements BeanPostProcessor{
	protected final Log log = LogFactory.getLog(getClass());
	private Map<String, WorkProcess> processes = new HashMap<String, WorkProcess>();
	
	/**
	 * 
	 * @param beanName
	 * @param workProcesses
	 */
	protected void addWorkProcess(String beanName, WorkProcess workProcesses){
		processes.put(beanName, workProcesses);
	}
	
	protected abstract Integer getType(Object bean);
	
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {		
		//System.out.println(bean);
		if(bean != null && bean instanceof WorkProcess){
			
			Integer type = getType(bean);
			if(type != null){
				addWorkProcess(type, (WorkProcess)bean);
				addWorkProcess(beanName, (WorkProcess)bean);
				log.debug("Set workprocess: type=" + type + ", name=" + beanName + ", class=" + bean);
			}
		}
		return bean;
	}
	
	/**
	 * 
	 */
	public Object postProcessBeforeInitialization(Object bean, String beanName)	throws BeansException {
		//System.out.println("postProcessBeforeInitialization => " + bean);
		return bean;
	}

	/**
	 * 
	 * @param processName
	 * @return
	 */
	public WorkProcess getProcess(String processName){
		return processes.get(processName);
	}

	/**
	 * 
	 * @param processName
	 * @param parameters
	 * @param needLog
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object execute(String processName, Map parameters, boolean needLog) {
		return getProcess(processName).execute(parameters, needLog);
	}
	
}
