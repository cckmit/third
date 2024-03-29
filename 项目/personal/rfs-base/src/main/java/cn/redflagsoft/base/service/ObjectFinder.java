/*
 * $Id: ObjectFinder.java 5189 2011-12-12 04:52:47Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

/**
 * 对象查询器。
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 * @param <T> 对象
 * @param <K> 对象主键
 */
public interface ObjectFinder<T> extends ObjectCountQuery{
	
//	/**
//	 * 读取对象。
//	 * @param id
//	 * @return
//	 */
//	T getObject(K id);
	
	/**
	 * 查询对象列表。
	 * @param rf
	 * @return
	 */
	List<T> findObjects(ResultFilter rf);
	
	
//	/**
//	 * 
//	 * @param rf
//	 * @return
//	 */
//	List<K> findObjectIds(ResultFilter rf);
	
	
//	/**
//	 * 获取对象数量。
//	 * @param rf
//	 * @return
//	 */
//	int getObjectCount(ResultFilter rf);
}