/*
 * $Id$
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
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
		String remarkTemplate = "规定时限：${risk.scaleValue}${risk.valueUnitName}，剩余时间：${risk.remainValue}${risk.valueUnitName}";
		Risk risk = new Risk();
		risk.setValue(new BigDecimal(10));
		risk.setScaleValue(new BigDecimal(30));
		risk.setValueUnit(RiskRule.VALUE_UNIT_WORKDAY);
		
		RiskRule rule = new RiskRule();
		String remark = RiskServiceImpl.parseRiskRemark(remarkTemplate, risk, rule);
		System.out.println(remark);
		assertEquals("规定时限：30工作日，剩余时间：20工作日", remark);
	}
}
