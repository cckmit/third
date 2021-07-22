/*
 * $Id: ShortMessageSender.java 6195 2013-04-02 10:04:31Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import org.springframework.core.Ordered;

/**
 * ���ŷ�������
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface ShortMessageSender extends Ordered {
	/**
	 * �жϷ������Ƿ������С�
	 * @return
	 */
	boolean isRunning();
	
	/**
	 * ���Ͷ��š�
	 * @param receiverNumber
	 * @param message
	 * @return
	 * @throws Exception
	 */
	int send(String receiverNumber, String message) throws Exception;
}
