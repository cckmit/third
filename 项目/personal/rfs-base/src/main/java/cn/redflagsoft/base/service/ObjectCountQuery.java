/*
 * $Id: ObjectCountQuery.java 5051 2011-11-09 03:44:58Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import org.opoo.ndao.support.ResultFilter;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface ObjectCountQuery {
	/**
	 * 获取对象数量。
	 * @param rf
	 * @return
	 */
	int getObjectCount(ResultFilter rf);
}
