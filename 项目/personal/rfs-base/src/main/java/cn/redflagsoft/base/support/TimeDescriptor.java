/*
 * $Id: TimeDescriptor.java 5014 2011-11-04 06:26:16Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.support;

import java.util.Date;

import cn.redflagsoft.base.support.TimeBuilder.EndOfMonthTimeBuilder;
import cn.redflagsoft.base.support.TimeBuilder.EndOfTodayTimeBuilder;
import cn.redflagsoft.base.support.TimeBuilder.EndOfWeekTimeBuilder;
import cn.redflagsoft.base.support.TimeBuilder.EndOfYearTimeBuilder;
import cn.redflagsoft.base.support.TimeBuilder.NowTimeBuilder;
import cn.redflagsoft.base.support.TimeBuilder.StartOfMonthTimeBuilder;
import cn.redflagsoft.base.support.TimeBuilder.StartOfTodayTimeBuilder;
import cn.redflagsoft.base.support.TimeBuilder.StartOfWeekTimeBuilder;
import cn.redflagsoft.base.support.TimeBuilder.StartOfYearTimeBuilder;




/**
 * ʱ��������
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public enum TimeDescriptor {
	Now(new NowTimeBuilder()),
	StartOfToday(new StartOfTodayTimeBuilder()),
	EndOfToday(new EndOfTodayTimeBuilder()), 
	StartOfMonth(new StartOfMonthTimeBuilder()), 
	EndOfMonth(new EndOfMonthTimeBuilder()), 
	StartOfYear(new StartOfYearTimeBuilder()), 
	EndOfYear(new EndOfYearTimeBuilder()), 
	StartOfWeek(new StartOfWeekTimeBuilder()),
	EndOfWeek(new EndOfWeekTimeBuilder());
	
	private final TimeBuilder builder;
	private TimeDescriptor(TimeBuilder builder){
		this.builder = builder;
	}
	public Date getTime(){
		return this.builder.build();
	}
	
	
	public static void main(String[] args){
		System.out.println(new NowTimeBuilder().build());
		System.out.println(new StartOfTodayTimeBuilder().build());
		System.out.println(new EndOfTodayTimeBuilder().build());
		System.out.println(new StartOfMonthTimeBuilder().build());
		System.out.println(new EndOfMonthTimeBuilder().build());
		System.out.println(new StartOfYearTimeBuilder().build());
		System.out.println(new EndOfYearTimeBuilder().build());
		System.out.println(new StartOfWeekTimeBuilder().build());
		System.out.println(new EndOfWeekTimeBuilder().build());
		TimeDescriptor desc = TimeDescriptor.valueOf("Now");
		System.out.println(desc.getTime());
	}
}
