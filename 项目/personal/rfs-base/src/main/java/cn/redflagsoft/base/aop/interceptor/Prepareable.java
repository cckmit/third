/*
 * $Id: Prepareable.java 3825 2010-01-26 04:20:16Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.aop.interceptor;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface Prepareable {

	/**
	 * 数据准备。
	 * 
	 */
	void prepare();
}
