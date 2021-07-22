/*
 * $Id: Scheme.java 5136 2011-11-25 14:02:06Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;



/**
 * Scheme类。
 * 
 * @author Alex Lin
 *
 */
public interface Scheme/* extends Validateable*/ extends SchemeBeanInfo{
	/**
	 * Scheme默认的方法。
	 * 所有动态方法以do开头，方便加AOP。
	 * @return
	 */
	Object doScheme() throws SchemeException;
	
	
	/**
	 * 用于管理 Scheme 时显示其名称。
	 * @return
	 */
	String getDisplayName();
}
