/*
 * $Id: HolidayChecker.java 5846 2012-06-07 04:41:36Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import java.util.Date;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface HolidayChecker {
	/**
	 * �ж�ָ���������Ƿ�ʱ�ڼ��ա�
	 * @param date
	 * @return
	 */
	boolean isHoliday(Date date);
}
