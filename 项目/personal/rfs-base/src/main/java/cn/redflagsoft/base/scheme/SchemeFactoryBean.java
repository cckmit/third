/*
 * $Id: SchemeFactoryBean.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
