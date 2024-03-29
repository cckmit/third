/*
 * $Id: SchemeTaskManager.java 4471 2011-07-07 10:02:03Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;

import java.util.Collection;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface SchemeTaskManager {
	
	/**
	 * Add scheme task.
	 * @param type
	 * @param schemeTask
	 */
	void addSchemeTask(int type, Runnable schemeTask);
	
	/**
	 * Add default scheme task. 
	 */
	void addSchemeTask(Runnable schemeTask);
	
	/**
	 * Return scheme task specified by type.
	 * @param type
	 * @return
	 */
	Collection<SchemeTask> getSchemeTasks(int type);
	
	/**
	 * Return all scheme tasks.
	 * @return
	 */
	Collection<SchemeTask> getAllSchemeTasks();
	
	/**
	 * 
	 * @param type 类型
	 * @return 返回已经执行scheme task的数量
	 */
	int executeSchemeTasks(int type);
	
	/**
	 * 
	 * @return 返回已经执行scheme task的数量
	 */
	int executeAllSchemeTasks();
	
	/**
	 * 获取指定类型的task的数量。
	 * 
	 * @param type
	 * @return
	 */
	int getNumberOfSchemeTasks(int type);
}
