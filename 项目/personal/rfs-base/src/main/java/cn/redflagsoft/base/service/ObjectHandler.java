/*
 * $Id: ObjectHandler.java 5014 2011-11-04 06:26:16Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

/**
 * ����������
 * @author Alex Lin(lcql@msn.com)
 *
 * @param <T> ����
 * @param <R> ���
 */
public interface ObjectHandler<T,R> {
	/**
	 * ������
	 * @param object
	 * @return
	 */
	R handle(T object);
}
