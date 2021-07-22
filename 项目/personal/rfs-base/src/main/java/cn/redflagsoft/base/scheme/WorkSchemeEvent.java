/*
 * $Id: WorkSchemeEvent.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;

import org.opoo.apps.event.Event;

/**
 * WorkScheme事件。
 * 
 * @author Alex Lin
 * @deprecated
 */
public class WorkSchemeEvent extends Event<WorkScheme> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8421085144910398464L;
	/**
	 * 当任务创建时。
	 */
	public static final int TASK_CREATED = 1;
	/**
	 * 当工作创建时。
	 */
	public static final int WORK_CREATED = 2;
	/**
	 * 当业务对象创建时。
	 */
	public static final int OBJECT_CREATED = 3;
	/**
	 * 当资料创建时。
	 */
	public static final int DATUM_CREATED = 4;

	/**
	 * 
	 * @param eventType 事件类型
	 * @param source 事件源
	 */
	public WorkSchemeEvent(int eventType, WorkScheme source) {
		super(eventType, source);
	}

}
