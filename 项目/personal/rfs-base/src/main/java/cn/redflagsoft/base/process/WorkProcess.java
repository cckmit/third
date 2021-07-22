/*
 * $Id: WorkProcess.java 4599 2011-08-18 07:14:12Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.process;

import java.util.Map;


/**
 * ������С��ҵ��λ�������ٷֲ�ļ�ҵ���߼���
 * �����WorkProcess������������������Ҫ��WorkProcess���ó�prototype�ġ�
 * 
 * 
 * @author Alex Lin
 *
 */
public interface WorkProcess {
	/**
	 * ִ�о�������
	 * 
	 * @param parameters ������
	 * @return ���ض���
	 */
	Object execute(Map<?,?> parameters) throws WorkProcessException;
	/**
	 * ִ�о�������
	 * 
	 * @param parameters ������
	 * @param needLog �Ƿ���Ҫ��¼������־��
	 * @return
	 */
	Object execute(Map<?,?> parameters, boolean needLog) throws WorkProcessException;
}
