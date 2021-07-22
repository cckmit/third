/*
 * $Id: RiskCalculator.java 5189 2011-12-12 04:52:47Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.math.BigDecimal;
import java.util.Date;

import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskValue;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface RiskCalculator {
	
	/**
	 * 从被监控对象的被监控属性中读取指定Risk的value值。如果risk的值来源是
	 * 定时增长，则可能取自计算结果。
	 * 
	 * 通常在计算risk的grade之前调用这个方法来获取risk的新值。
	 * 
	 * @param risk
	 * @return
	 */
	RiskValue getRiskValue(Risk risk);//是否定义参数为 objectId, objectType, objectAttr?

	
	/**
	 * 通过Risk的属性中计算Risk的新值。原则上该方法不改变risk的任何属性，如果是时间，可能会改变LastRunTime。
	 * 一般值是时间间隔且随时间变化时，每次计算可能都获取新的值。
	 * 
	 * <p>如果被监控对象能够自己定时计算自己的被监控属性值，则通常调用{@link #getValue(Risk)}方法，而不是本
	 * 方法（例如 Task可以定时计算自己的timeUsed）。
	 * 
	 * <p>该方法一般用于通用的时间增长性监察，并且各种时间时记录在risk对象中的情况。
	 * 
	 * @param risk - Risk 被计算的Risk
	 * @param calculateTime - Date 计算时间，允许为null，为null时取当前值
	 * @return 计算后的值
	 */
	BigDecimal calculateValue(Risk risk, Date calculateTime);
	
	/**
	 * 计算Risk的新级别。
	 * 
	 * @param risk 被计算的risk，计算前如果值有变化，要先设置
	 * @return 计算出的监察级别
	 */
	byte calculateGrade(Risk risk);
}
