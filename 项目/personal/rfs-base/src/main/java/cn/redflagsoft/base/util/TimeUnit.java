/*
 * $Id: TimeUnit.java 5327 2012-02-15 03:52:13Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.util;

/**
 * 
 * 时间周期单位：1-年、2-月、3-周、4-日(工作日)、5-天（自然天）、6-时、7-分、8-秒、9-毫秒
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
