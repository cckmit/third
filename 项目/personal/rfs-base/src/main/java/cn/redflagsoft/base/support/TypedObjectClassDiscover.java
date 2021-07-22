/*
 * $Id: TypedObjectClassDiscover.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.support;

/**
 * 类发现者。
 * 
 * @author Alex Lin
 *
 */
public interface TypedObjectClassDiscover {
	
	/**
	 * 根据对象的objectType查找对象的类型。
	 * @param objectType
	 * @return
	 */
	Class<?> findClass(int objectType);
	
	/**
	 * 根据对象的objectType查找对象的实体名称或者类名。
	 * 
	 * @param objectType
	 * @return
	 */
	String findName(int objectType);
}
