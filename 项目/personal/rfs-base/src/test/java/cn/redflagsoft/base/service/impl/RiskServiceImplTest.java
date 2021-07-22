/*
 * $Id$
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service.impl;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskRule;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class RiskServiceImplTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		String remarkTemplate = "�涨ʱ�ޣ�${risk.scaleValue}${risk.valueUnitName}��ʣ��ʱ�䣺${risk.remainValue}${risk.valueUnitName}";
		Risk risk = new Risk();
		risk.setValue(new BigDecimal(10));
		risk.setScaleValue(new BigDecimal(30));
		risk.setValueUnit(RiskRule.VALUE_UNIT_WORKDAY);
		
		RiskRule rule = new RiskRule();
		String remark = RiskServiceImpl.parseRiskRemark(remarkTemplate, risk, rule);
		System.out.println(remark);
		assertEquals("�涨ʱ�ޣ�30�����գ�ʣ��ʱ�䣺20������", remark);
	}
}
