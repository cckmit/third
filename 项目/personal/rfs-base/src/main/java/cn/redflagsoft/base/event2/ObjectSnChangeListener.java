/*
 * $Id: ObjectSnChangeListener.java 5915 2012-06-28 08:54:20Z lcj $
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
public interface ObjectSnChangeListener {

	/**
	 * @param source
	 * @param oldValue
	 * @param newValue
	 */
	void objectSnChange(RFSObjectable object, String oldValue, String newValue);
}
