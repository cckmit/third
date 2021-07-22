/*
 * $Id: SmsgForCautionService.java 5487 2012-04-09 04:05:21Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.bean.Smsg;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface SmsgForCautionService {
	
	List<Smsg> createSmsgForCaution(final Caution caution, Risk risk, RiskRule riskRule) throws Exception;
}
