/*
 * $Id: RiskHandler.java 6119 2012-11-15 07:23:17Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import java.math.BigDecimal;

import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskMonitorable;
import cn.redflagsoft.base.bean.RiskRule;

/**
 * Risk��������
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface RiskHandler {

	/**
	 * �ڱ���Risk֮ǰҪ���Ĵ���
	 * 
	 * @param risk risk
	 * @param riskMonitorable ����ض���task��rfsObject��
	 * @param riskRule ��ܹ���
	 * @param object ��ص�������
	 * @param scaleValue Ҫ���õ�ֵ
	 */
	void beforeSaveRisk(Risk risk, RiskMonitorable riskMonitorable, RiskRule riskRule, RFSObject object, BigDecimal scaleValue);
	
	/**
	 * �ڱ���Risk֮��Ҫ���Ĵ���
	 * @param risk 
	 * @param riskMonitorable ����ض���task��rfsObject��
	 * @param riskRule ��ܹ���
	 * @param object ��ص�������
	 * @param scaleValue
	 */
	void afterSaveRisk(Risk risk, RiskMonitorable riskMonitorable, RiskRule riskRule, RFSObject object, BigDecimal scaleValue);
}
