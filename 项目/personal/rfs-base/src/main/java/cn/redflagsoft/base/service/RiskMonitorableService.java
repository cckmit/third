/*
 * $Id: RiskMonitorableService.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.math.BigDecimal;

import cn.redflagsoft.base.bean.RiskMonitorable;

/**
 * @author Alex Lin
 *
 */
public interface RiskMonitorableService {
	
	/**
	 * 更新指定被监控对象指定属性对应的risk。
	 * 
	 * @param rm
	 * @param objectAttr
	 * @param dutyId
	 */
	void updateRiskDutyer(RiskMonitorable rm, String objectAttr, Long dutyerID);
	

	void setRiskScale(RiskMonitorable rm, String objectAttr,BigDecimal scaleValue, byte scaleId);
	
	Byte getRiskValueUnit(RiskMonitorable rm, String objectAttr);
	
	BigDecimal getRiskScale(RiskMonitorable rm, String objectAttr, byte scaleId);

}
