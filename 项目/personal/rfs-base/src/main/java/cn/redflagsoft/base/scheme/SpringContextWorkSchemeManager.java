/*
 * $Id: SpringContextWorkSchemeManager.java 3996 2010-10-18 06:56:46Z lcj $
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 使用Spring容器特性实现的WorkScheme管理器。
 * 
 * @author Alex Lin
 * @deprecated since r1779, 2009-01-13, replaced by SpringContextSchemeManager.
 */
public class SpringContextWorkSchemeManager implements WorkSchemeManager, ApplicationContextAware {
	private static final Log log = LogFactory.getLog(SpringContextWorkSchemeManager.class);
	private ApplicationContext context;
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.WorkSchemeManager#getWorkScheme(java.lang.String)
	 */
	public WorkScheme getWorkScheme(String name) throws SchemeException {
		Object bean = null;
		try {
			bean = context.getBean(name);
		} catch (BeansException e) {
			log.error(e);
			throw new SchemeException("找不到WorkScheme: " + name);
		}
		if(bean != null && bean instanceof WorkScheme){
			return (WorkScheme) bean;
		}
		throw new SchemeException("找不到WorkScheme: " + name);
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}
}
