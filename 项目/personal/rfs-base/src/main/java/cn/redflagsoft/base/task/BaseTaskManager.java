/*
 * $Id: BaseTaskManager.java 6383 2014-04-17 01:32:08Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.task;

import java.io.Serializable;


/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface BaseTaskManager {
	
	/**
	 * 提交一个任务，交给任务管理器去执行和调度
	 * @param clazz
	 * @param param
	 * @param failoverLimit 最大失败次数
	 */
	//<T extends Serializable> void submit(Class<? extends BaseParamTask<T>> clazz, T param, int failoverLimit);
	
	/**
	 * 提交一个任务，交给任务管理器去执行和调度
	 * @param clazz
	 * @param param
	 */
	<T extends Serializable> void submit(Class<? extends BaseParamTask<T>> clazz, T param);
	
	
	/**
	 * 提交一个无参数任务。
	 * @param clazz
	 * @param failoverLimit 最大失败次数
	 */
	//void submit(Class<? extends BaseTask> clazz, int failoverLimit);
	
	/**
	 * 提交一个无参数任务。
	 * @param clazz
	 */
	void submit(Class<? extends BaseTask> clazz);
	
	
	/**
	 * 查找并提交新任务。
	 * @param limit
	 * @return 任务数
	 */
	int findAndSubmitTasks(int limit);
}
