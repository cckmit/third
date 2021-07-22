/*
 * $Id: SchemeFactoryBean.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class SchemeFactoryBean implements FactoryBean, SchemeManagerAware, InitializingBean {
	private SchemeManager schemeManager;
	private String name;

	public Object getObject() throws Exception {
		return schemeManager.getScheme(name);
	}

	public Class<?> getObjectType() {
		return Scheme.class;
	}

	public boolean isSingleton() {
		return false;
	}

	public void setSchemeManager(SchemeManager schemeManager) {
		this.schemeManager = schemeManager;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(schemeManager);
		Assert.notNull(name);
	}
}
