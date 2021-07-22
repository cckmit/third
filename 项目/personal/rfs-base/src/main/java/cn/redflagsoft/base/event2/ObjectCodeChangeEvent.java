/*
 * $Id: ObjectCodeChangeEvent.java 5915 2012-06-28 08:54:20Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.event2;

import cn.redflagsoft.base.bean.RFSObjectable;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ObjectCodeChangeEvent extends PropertyChangeEvent<RFSObjectable, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 114507358020116763L;

	/**
	 * @param object
	 * @param propertyName
	 * @param oldValue
	 * @param newValue
	 */
	public ObjectCodeChangeEvent(RFSObjectable object, String oldValue, String newValue) {
		super(object, "code", oldValue, newValue);
	}
}
