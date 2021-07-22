/*
 * $Id: ObjectByIDFinder.java 5277 2011-12-26 09:07:11Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

/**
 * @author Alex Lin(lcql@msn.com)
 * @deprecated using {@link ObjectFinder}
 */
public interface ObjectByIDFinder<T, K> extends ObjectCountQuery {
	/**
	 * 读取对象。
	 * @param id
	 * @return
	 */
	T getObject(K id);
	
	/**
	 * 
	 * @param rf
	 * @return
	 */
	List<K> findObjectIds(ResultFilter rf);
}
