/*
 * $Id: ParametersAware.java 4602 2011-08-18 07:38:59Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.aop.interceptor;

import java.util.Map;

/**
 * 参数接口。
 * 
 * @author Alex Lin
 *
 */
public interface ParametersAware {
	/**
	 * 设置参数。
	 * 
	 * @param parameters
	 */
	void setParameters(@SuppressWarnings("rawtypes") Map parameters);
	
	/**
	 * 获取参数。
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Map getParameters();
}
