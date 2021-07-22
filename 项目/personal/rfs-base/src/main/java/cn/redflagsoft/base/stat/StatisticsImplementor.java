/*
 * $Id: StatisticsImplementor.java 5136 2011-11-25 14:02:06Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.stat;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface StatisticsImplementor {
	/**
	 * 
	 * @param schemeName
	 * @param methodName
	 * @param time
	 */
	void schemeExecuted(String schemeName, String methodName, long time);
}
