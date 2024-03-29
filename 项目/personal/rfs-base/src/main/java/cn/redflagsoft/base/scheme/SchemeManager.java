/*
 * $Id: SchemeManager.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;

/**
 * @author Alex Lin
 *
 */
public interface SchemeManager {
	/**
	 * 查找指定名称的Scheme。
	 * 
	 * @param name
	 * @return
	 * @throws SchemeException
	 */
	Scheme getScheme(String name) throws SchemeException;
	
	/**
	 * 根据指定的taskType和workType查找指定的WorkScheme。
	 * 
	 * @param taskType 
	 * @param workType
	 * @return
	 * @throws SchemeException
	 */
	WorkScheme getWorkScheme(int taskType, int workType) throws SchemeException;
	
//	/**
//	 * 获取指定task对应的默认WorkSchem实例。
//	 * 
//	 * @param taskType
//	 * @return
//	 * @throws SchemeException
//	 */
//	WorkScheme getDefaultWorkScheme(int taskType) throws SchemeException;
}
