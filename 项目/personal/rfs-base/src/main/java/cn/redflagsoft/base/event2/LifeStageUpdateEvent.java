/*
 * $Id: LifeStageUpdateEvent.java 5161 2011-12-02 03:38:59Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.event2;

import org.opoo.apps.event.v2.Event;

import cn.redflagsoft.base.bean.ObjectTask;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.TaskDefinition;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class LifeStageUpdateEvent extends Event<LifeStageUpdateEvent.Type, LifeStageUpdateEvent.LifeStageInfo> {
	private static final long serialVersionUID = -1721035672350259724L;

	/**
	 * @param eventType
	 * @param source
	 */
	public LifeStageUpdateEvent(Type eventType, LifeStageInfo source) {
		super(eventType, source);
	}

	/**
	 * 
	 * @author Alex Lin(alex@opoo.org)
	 *
	 */
	public static enum Type{
		UPDATED;
	}
	
	public static class LifeStageInfo{
		private Task task;
		private TaskDefinition taskDefinition;
		private ObjectTask objectTask;
		
		public LifeStageInfo() {
			super();
		}

		public LifeStageInfo(Task task, TaskDefinition taskDefinition, ObjectTask objectTask) {
			this.task = task;
			this.taskDefinition = taskDefinition;
			this.objectTask = objectTask;
		}

		public Task getTask() {
			return task;
		}

		public void setTask(Task task) {
			this.task = task;
		}

		public TaskDefinition getTaskDefinition() {
			return taskDefinition;
		}

		public void setTaskDefinition(TaskDefinition taskDefinition) {
			this.taskDefinition = taskDefinition;
		}

		public ObjectTask getObjectTask() {
			return objectTask;
		}

		public void setObjectTask(ObjectTask objectTask) {
			this.objectTask = objectTask;
		}
	}
}
