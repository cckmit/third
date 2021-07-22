/*
 * $Id: RiskHandler.java 6119 2012-11-15 07:23:17Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.math.BigDecimal;

import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskMonitorable;
import cn.redflagsoft.base.bean.RiskRule;

/**
 * Risk处理器。
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface RiskHandler {

	/**
	 * 在保存Risk之前要做的处理。
	 * 
	 * @param risk risk
	 * @param riskMonitorable 被监控对象，task，rfsObject等
	 * @param riskRule 监管规则
	 * @param object 相关的主对象
	 * @param scaleValue 要设置的值
	 */
	void beforeSaveRisk(Risk risk, RiskMonitorable riskMonitorable, RiskRule riskRule, RFSObject object, BigDecimal scaleValue);
	
	/**
	 * 在保存Risk之后要做的处理。
	 * @param risk 
	 * @param riskMonitorable 被监控对象，task，rfsObject等
	 * @param riskRule 监管规则
	 * @param object 相关的主对象
	 * @param scaleValue
	 */
	void afterSaveRisk(Risk risk, RiskMonitorable riskMonitorable, RiskRule riskRule, RFSObject object, BigDecimal scaleValue);
}
