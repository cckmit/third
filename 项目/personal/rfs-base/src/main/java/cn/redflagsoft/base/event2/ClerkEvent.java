/*
 * $Id: ClerkEvent.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.event2;

import org.opoo.apps.event.v2.Event;

import cn.redflagsoft.base.bean.Clerk;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ClerkEvent extends Event<ClerkEvent.Type, Clerk> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5184312888824787650L;

	/**
	 * @param eventType
	 * @param source
	 */
	public ClerkEvent(Type eventType, Clerk source) {
		super(eventType, source);
	}

	
	public static enum Type{
		CREATED, UPDATED, DELETED;
	}
}
