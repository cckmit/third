/*
 * $Id: SchemeBeanPostProcessor.java 4568 2011-08-05 05:45:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class SchemeBeanPostProcessor implements BeanPostProcessor {
	private static final Log log = LogFactory.getLog(SchemeBeanPostProcessor.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	private SchemeManager schemeManager;
	
	
	public SchemeManager getSchemeManager() {
		return schemeManager;
	}

	public void setSchemeManager(SchemeManager schemeManager) {
		if(IS_DEBUG_ENABLED){
			log.debug("Set schemeManager: " + schemeManager);
		}
		this.schemeManager = schemeManager;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof SchemeManagerAware){
			((SchemeManagerAware) bean).setSchemeManager(getSchemeManager());
			log.info("Registering bean " + beanName + " as an SchemeManagerAware.");
		}
		return bean;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
}
