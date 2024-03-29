/*
 * $Id: RiskValueProvider.java 5189 2011-12-12 04:52:47Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskValue;

/**
 * Risk的value提供者。
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface RiskValueProvider {

	/**
	 * 是否支持获取次Risk的value。
	 * 
	 * @param risk
	 * @return
	 */
	boolean supportsGetRiskValue(Risk risk);
	/**
	 * 获取指定Risk所需的value值。
	 * 
	 * @param risk
	 * @return
	 */
	RiskValue getRiskValue(Risk risk);
}
