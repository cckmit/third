/*
 * $Id: RiskValue.java 5189 2011-12-12 04:52:47Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Risk的值。
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface RiskValue {

	/**
	 * risk的value
	 * @return
	 */
	BigDecimal getValue();
	
	/**
	 * risk的value值的生成时间
	 * @return
	 */
	Date getValueGeneratedTime();
	
	/**
	 * risk的value值的来源
	 * 
	 * @return
	 */
	Object getSource();
}
