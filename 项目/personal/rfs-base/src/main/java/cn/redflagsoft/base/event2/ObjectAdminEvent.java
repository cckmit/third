/*
 * $Id: ObjectAdminEvent.java 5610 2012-05-04 07:48:03Z thh $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.event2;

import org.opoo.apps.event.v2.Event;

import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.commons.ObjectAdmin;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ObjectAdminEvent extends Event<ObjectAdminEvent.Type, RFSObject> {
	private static final long serialVersionUID = 2658940076068880560L;
	private final ObjectAdmin objectAdmin;
	/**
	 * @param eventType
	 * @param source
	 */
	public ObjectAdminEvent(Type eventType, RFSObject source, ObjectAdmin objectAdmin) {
		super(eventType, source);
		this.objectAdmin = objectAdmin;
	}

	/**
	 * @return the objectAdmin
	 */
	public ObjectAdmin getObjectAdmin() {
		return objectAdmin;
	}

	public static enum Type{
		ARCHIVE, UNARCHIVE, INVALID, CANCEL, PUBLISH, PAUSE, WAKE, TRANS, SHELVE, FINISH;
	}
}
