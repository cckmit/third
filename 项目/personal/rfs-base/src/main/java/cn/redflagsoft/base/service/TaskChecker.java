/*
 * $Id: TaskChecker.java 5189 2011-12-12 04:52:47Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.Date;

import cn.redflagsoft.base.bean.Task;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface TaskChecker {
	/**
	 * 用指定时间计算task的timeUsed值，并保存更新。
	 * 
	 * @param task
	 * @param calculateTime
	 * @return 计算后的timeUsed的增量值，不变时为0
	 */
	int calculateTaskTimeUsed(Task task, Date calculateTime);
	
	/**
	 * 用指定时间计算所有正在运行的task的timeUsed值，并保存更新。
	 * 
	 * @param calculateTime
	 * @return 一共处理的task数量
	 */
	int calculateAllRunningTasksTimeUsed(Date calculateTime);
	
	/**
	 * 获取最后一次计算task的timeUsed值的时间。
	 * 
	 * @return 最后一次计算task的timeUsed的时间
	 * @see #calculateAllRunningTasksTimeUsed(Date)
	 */
	Date getLastCalculateAllRunningTasksTimeUsedTime();
}
