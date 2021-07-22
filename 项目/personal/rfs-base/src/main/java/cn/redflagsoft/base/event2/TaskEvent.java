/*
 * $Id: TaskEvent.java 4576 2011-08-11 01:41:38Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.event2;

import org.opoo.apps.event.v2.Event;

import cn.redflagsoft.base.bean.Task;

/**
 * 任务事件。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class TaskEvent extends Event<TaskEvent.Type, Task> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2145317557018117836L;
	private Task oldTask;
	/**
	 * @param eventType
	 * @param source
	 */
	public TaskEvent(Type eventType, Task source) {
		super(eventType, source);
	}
	
	public TaskEvent(Type eventType, Task oldTask, Task newTask) {
		super(eventType, newTask);
		this.oldTask = oldTask;
	}
	
	public Task getOldTask(){
		return oldTask;
	}
	
	public Task getNewTask(){
		return getSource();
	}

	public static enum Type{
		CREATED, UPDATED, DELETED, HANG, WAKE, TERMINATE, DELAY, STOP, AVOID, WITHDRAW, REJECT, UNDO, TRANSFER;
	} 
}
