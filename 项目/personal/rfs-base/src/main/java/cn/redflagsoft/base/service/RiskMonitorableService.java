/*
 * $Id: RiskMonitorableService.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
	 * ����ָ������ض���ָ�����Զ�Ӧ��risk��
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
