/*
 * $Id: TimeBuilder.java 5014 2011-11-04 06:26:16Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.support;

import java.util.Calendar;
import java.util.Date;

import org.opoo.apps.util.DateUtils;

/**
 * @author Alex Lin(lcql@msn.com)
 * 
 */
public interface TimeBuilder {
	Date build();

	public static class NowTimeBuilder implements TimeBuilder {
		public Date build() {
			return new Date();
		}
	}

	public static class StartOfTodayTimeBuilder implements TimeBuilder {
		public Date build() {
			return DateUtils.toStartOfDay(new Date());
		}
	}

	public static class EndOfTodayTimeBuilder implements TimeBuilder {
		public Date build() {
			return DateUtils.toEndOfDay(new Date());
		}
	}

	public static class StartOfMonthTimeBuilder implements TimeBuilder {
		public Date build() {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			return DateUtils.toStartOfDay(calendar.getTime());
		}
	}

	public static class EndOfMonthTimeBuilder implements TimeBuilder {
		public Date build() {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 0);
			return DateUtils.toEndOfDay(calendar.getTime());
		}
	}

	public static class StartOfYearTimeBuilder implements TimeBuilder {
		public Date build() {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			return DateUtils.toStartOfDay(calendar.getTime());
		}
	}

	public static class EndOfYearTimeBuilder implements TimeBuilder {
		public Date build() {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MONTH, 11);
			calendar.set(Calendar.DAY_OF_MONTH, 31);
			return DateUtils.toEndOfDay(calendar.getTime());
		}
	}

	public static class StartOfWeekTimeBuilder implements TimeBuilder {
		public Date build() {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_WEEK, 1);
			calendar.add(Calendar.DATE, 1);
			return DateUtils.toStartOfDay(calendar.getTime());
		}
	}

	public static class EndOfWeekTimeBuilder implements TimeBuilder {
		public Date build() {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_WEEK, 0);
			calendar.add(Calendar.DATE, 1);
			return DateUtils.toEndOfDay(calendar.getTime());
		}
	}
}
