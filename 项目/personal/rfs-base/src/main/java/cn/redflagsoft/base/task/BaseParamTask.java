/*
 * $Id: BaseParamTask.java 6377 2014-04-16 10:20:12Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.task;

import java.io.Serializable;

import org.opoo.apps.AppsContext;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface BaseParamTask <T extends Serializable> {

	/**
	 * 执行任务。
	 * @param context
	 * @param param
	 */
	void execute(AppsContext context, T param) throws Exception;
}
