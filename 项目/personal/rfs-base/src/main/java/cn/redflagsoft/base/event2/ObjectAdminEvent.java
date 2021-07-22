/*
 * $Id: ObjectAdminEvent.java 5610 2012-05-04 07:48:03Z thh $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
