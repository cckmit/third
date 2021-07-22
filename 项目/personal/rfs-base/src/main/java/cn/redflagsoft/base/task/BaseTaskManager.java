/*
 * $Id: BaseTaskManager.java 6383 2014-04-17 01:32:08Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.task;

import java.io.Serializable;


/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface BaseTaskManager {
	
	/**
	 * �ύһ�����񣬽������������ȥִ�к͵���
	 * @param clazz
	 * @param param
	 * @param failoverLimit ���ʧ�ܴ���
	 */
	//<T extends Serializable> void submit(Class<? extends BaseParamTask<T>> clazz, T param, int failoverLimit);
	
	/**
	 * �ύһ�����񣬽������������ȥִ�к͵���
	 * @param clazz
	 * @param param
	 */
	<T extends Serializable> void submit(Class<? extends BaseParamTask<T>> clazz, T param);
	
	
	/**
	 * �ύһ���޲�������
	 * @param clazz
	 * @param failoverLimit ���ʧ�ܴ���
	 */
	//void submit(Class<? extends BaseTask> clazz, int failoverLimit);
	
	/**
	 * �ύһ���޲�������
	 * @param clazz
	 */
	void submit(Class<? extends BaseTask> clazz);
	
	
	/**
	 * ���Ҳ��ύ������
	 * @param limit
	 * @return ������
	 */
	int findAndSubmitTasks(int limit);
}
