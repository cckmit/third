/*
 * $Id: AbstractRiskHandler.java 6119 2012-11-15 07:23:17Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service.impl;

import java.math.BigDecimal;

import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskMonitorable;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.service.RiskHandler;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public abstract class AbstractRiskHandler implements RiskHandler {
	
	protected abstract boolean supports(Risk risk, RiskMonitorable riskMonitorable,
			RiskRule riskRule, RFSObject object, BigDecimal scaleValue);
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.RiskHandler#beforeSaveRisk(cn.redflagsoft.base.bean.Risk, cn.redflagsoft.base.bean.RiskMonitorable, cn.redflagsoft.base.bean.RiskRule, cn.redflagsoft.base.bean.RFSObject, java.math.BigDecimal)
	 */
	public final void beforeSaveRisk(Risk risk, RiskMonitorable riskMonitorable,
			RiskRule riskRule, RFSObject object, BigDecimal scaleValue) {
		if(supports(risk, riskMonitorable, riskRule, object, scaleValue)){
			doBeforeSaveRisk(risk, riskMonitorable, riskRule, object, scaleValue);
		}
	}

	/**
	 * @param risk
	 * @param riskMonitorable
	 * @param riskRule
	 * @param object
	 * @param scaleValue
	 */
	protected void doBeforeSaveRisk(Risk risk, RiskMonitorable riskMonitorable,
			RiskRule riskRule, RFSObject object, BigDecimal scaleValue) {
		
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.RiskHandler#afterSaveRisk(cn.redflagsoft.base.bean.Risk, cn.redflagsoft.base.bean.RiskMonitorable, cn.redflagsoft.base.bean.RiskRule, cn.redflagsoft.base.bean.RFSObject, java.math.BigDecimal)
	 */
	public final void afterSaveRisk(Risk risk, RiskMonitorable riskMonitorable,
			RiskRule riskRule, RFSObject object, BigDecimal scaleValue) {
		if(supports(risk, riskMonitorable, riskRule, object, scaleValue)){
			doAfterSaveRisk(risk, riskMonitorable, riskRule, object, scaleValue);
		}
	}


	/**
	 * @param risk
	 * @param riskMonitorable
	 * @param riskRule
	 * @param object
	 * @param scaleValue
	 */
	protected void doAfterSaveRisk(Risk risk, RiskMonitorable riskMonitorable,
			RiskRule riskRule, RFSObject object, BigDecimal scaleValue) {
	}

}
