/*
 * $Id: SmsgManager.java 5291 2011-12-28 02:43:49Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;



/**
 * ��Ϣ��������
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface SmsgManager {

	/**
	 * ����ָ������Ϣ�����ڿ���ͬ�����͵���Ϣ�������ڲ���Ϣ����
	 * �������̷��ط��ͺ����Ϣ���󣬵������첽��Ϣ�������ֻ�
	 * ���ţ�����ֻ�ܷ�Ӧ����Ϣ�����ύ�ɹ����첽��Ϣ��������
	 * ÿ������׶�ͨ����Ϣ����֪ͨ��Ϣ������������Ϣ״̬��
	 * 
	 * @param smsgId ��Ϣ��ID
	 * @return ���ͻ����ύ����Ϣ����
	 */
	int sendMsg(long smsgId);	
	
	/**
	 * ���̷��Ϳ��Է��͵�������Ϣ��
	 * 
	 * @return �����Smsg������
	 */
	int sendAvailableMsgs();
}