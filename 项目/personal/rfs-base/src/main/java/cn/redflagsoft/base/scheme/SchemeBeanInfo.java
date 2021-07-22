/*
 * $Id: SchemeBeanInfo.java 5136 2011-11-25 14:02:06Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;

/**
 * Scheme 在容器。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface SchemeBeanInfo {
	/**
	 * 设置Bean要调用的方法。
	 * 
	 * @return
	 */
	String getMethod();
	
	/**
	 * 获取Bean配置的方法。
	 * @param method
	 */
	void setMethod(String method);
	
	/**
	 * 获取Bean配置的名字。
	 * 
	 * @return
	 */
	String getBeanName();
	
	
	void setBeanName(String name);
}
