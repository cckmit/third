/*
 * $Id: TimeUnit.java 5327 2012-02-15 03:52:13Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.util;

/**
 * 
 * ʱ�����ڵ�λ��1-�ꡢ2-�¡�3-�ܡ�4-��(������)��5-�죨��Ȼ�죩��6-ʱ��7-�֡�8-�롢9-����
 *
 */
public enum TimeUnit {
	YEAR(1), 
	MONTH(2), 
	WEEK(3), 
	WORKDAY(4), 
	DAY(5), 
	HOUR(6), 
	MINUTE(7), 
	SECOND(8), 
	MILLISECOND(9);
	
	
	private final int flag;
	private TimeUnit(int flag){
		this.flag = flag;
	}
	public int getFlag(){
		return flag;
	}
	
	public static TimeUnit valueOf(int flag){
		TimeUnit[] units = TimeUnit.values();
		for(TimeUnit unit: units){
			if(unit.flag == flag){
				return unit;
			}
		}
		throw new IllegalArgumentException("TimeUnit flag is invalid: " + flag);
	}
}
