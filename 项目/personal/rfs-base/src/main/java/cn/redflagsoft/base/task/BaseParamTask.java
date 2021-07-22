/*
 * $Id: BaseParamTask.java 6377 2014-04-16 10:20:12Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.task;

import java.io.Serializable;

import org.opoo.apps.AppsContext;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface BaseParamTask <T extends Serializable> {

	/**
	 * ִ������
	 * @param context
	 * @param param
	 */
	void execute(AppsContext context, T param) throws Exception;
}
