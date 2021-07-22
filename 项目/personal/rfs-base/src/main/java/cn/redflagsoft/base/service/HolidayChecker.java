/*
 * $Id: HolidayChecker.java 5846 2012-06-07 04:41:36Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.Date;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface HolidayChecker {
	/**
	 * 判断指定的日期是否时节假日。
	 * @param date
	 * @return
	 */
	boolean isHoliday(Date date);
}
