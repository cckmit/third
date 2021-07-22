/*
 * $Id: ShortMessageSenderFactory.java 6195 2013-04-02 10:04:31Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface ShortMessageSenderFactory {

	ShortMessageSender getAvailableSender();
}
