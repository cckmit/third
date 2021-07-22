/*
 * $Id: ExCfgDatafilterRiskService.java 6010 2012-09-11 07:06:38Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.ExCfgDatafilterRisk;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface ExCfgDatafilterRiskService {
	/**
	 * 
	 * @param riskRuleID
	 * @param event
	 * @return
	 */
	List<ExCfgDatafilterRisk> findExCfgDatafilterRiskByRuleIDAndEvent(Long riskRuleID, String event);
}
