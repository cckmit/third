/*
 * $Id: OrderFinder.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

/**
 * 从对象中获取对象的order或者displayOrder。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface OrderFinder<T> {
	/**
	 * 查找指定对象的排序号。
	 * 
	 * @param t 指定对象。
	 * @return
	 */
	int getOrder(T t);
}
