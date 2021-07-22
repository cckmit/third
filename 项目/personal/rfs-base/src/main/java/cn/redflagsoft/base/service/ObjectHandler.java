/*
 * $Id: ObjectHandler.java 5014 2011-11-04 06:26:16Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

/**
 * 对象处理器。
 * @author Alex Lin(lcql@msn.com)
 *
 * @param <T> 输入
 * @param <R> 输出
 */
public interface ObjectHandler<T,R> {
	/**
	 * 对象处理
	 * @param object
	 * @return
	 */
	R handle(T object);
}
