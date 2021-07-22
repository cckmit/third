/*
 * $Id: WorkSchemeEvent.java 5136 2011-11-25 14:02:06Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.scheme.event;

import org.opoo.apps.event.v2.Event;

import cn.redflagsoft.base.scheme.WorkScheme;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class WorkSchemeEvent extends Event<WorkSchemeEvent.Type, WorkScheme> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5625920921286695291L;

	public static enum Type{
		TASK_CREATED,
		WORK_CREATED ,
		OBJECT_CREATED,
		DATUM_CREATED,
		BEFORE_EXECUTE,
		AFTER_EXECUTE,
		BEFORE_RETURN;
	}
	

	public WorkSchemeEvent(Type eventType, WorkScheme source) {
		super(eventType, source);
	};
	
}
