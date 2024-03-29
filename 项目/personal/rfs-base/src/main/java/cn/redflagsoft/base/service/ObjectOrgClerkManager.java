/*
 * $Id: ObjectOrgClerkManager.java 5520 2012-04-16 07:55:15Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.List;

/**
 * 对象单位人员关系管理器。
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface ObjectOrgClerkManager {
	
	/**
	 * 注册一种关系类型。在使用关系类型之前必须注册，否则无法保存关联对象。
	 * 
	 * @param type 关系类型
	 * @param description 描述
	 * @throws IllegalArgumentException 如果该类型已经被注册，则抛出异常，同种类型只能被注册一次；
	 * 	关系类型必须大于100，否则也抛出错误
	 */
	void registerType(int type, String description) throws IllegalArgumentException;	
	
	/**
	 * 判断指定的类型是否已经注册。
	 * 
	 * @param type
	 * @return 如果已经注册返回true，其它返回false。
	 */
	boolean hasRegistered(int type);

	/**
	 * 返回当前系统中已经注册的关系类型集合。
	 *
	 */
	List<Integer> getRegisteredTypes();
	
	/**
	 * 返回当前系统注册的关系指定关系的描述。
	 * @param type 关系类型
	 * @return 关系类型描述
	 * @throws IllegalArgumentException 如果该类型未注册，则抛出异常；关系类型必须大于100，否则也抛出错误
	 */
	String getRegisteredTypeDescription(int type) throws IllegalArgumentException;
}
