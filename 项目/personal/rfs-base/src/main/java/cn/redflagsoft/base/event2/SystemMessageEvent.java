/*
 * $Id: SystemMessageEvent.java 4387 2011-05-09 06:02:02Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.event2;

import org.opoo.apps.event.v2.Event;

import cn.redflagsoft.base.service.SystemMessage;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class SystemMessageEvent extends Event<SystemMessageEvent.Type, SystemMessage> {
	private static final long serialVersionUID = -1050917671862241931L;

	/**
	 * @param eventType
	 * @param source
	 */
	public SystemMessageEvent(Type eventType, SystemMessage source) {
		super(eventType, source);
	}

	public static enum Type{
		CREATED, UPDATED, DELETED, PUBLISH, PUBLISH_STATUS_CHANGED;
	}
}
