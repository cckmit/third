/*
 * $Id: TaskInfo.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;



/**
 * 任务附加信息接口。
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface TaskInfo extends Taskable {

	/**
	 * 获取当前对象关联的任务SN.
	 * @return
	 */
	Long getTaskSN();
	
	/**
	 * 设置当前对象关联的任务SN.
	 * @param taskSN
	 */
	void setTaskSN(Long taskSN);
}
