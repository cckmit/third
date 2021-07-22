/*
 * $Id: BizStatisticsServiceImplTest.java 4507 2011-07-19 01:17:07Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import cn.redflagsoft.base.bean.BizStatisticsDefinition;
import cn.redflagsoft.base.bean.RFSEntityDescriptor;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.test.BaseTests;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class BizStatisticsServiceImplTest extends BaseTests {
	protected BizStatisticsServiceImpl bizStatisticsService;

	/**
	 * Test method for {@link cn.redflagsoft.base.service.impl.BizStatisticsServiceImpl#addStatistics(cn.redflagsoft.base.bean.BizStatisticsDefinition, java.math.BigDecimal, java.util.Date, cn.redflagsoft.base.bean.RFSEntityObject, cn.redflagsoft.base.bean.Task)}.
	 */
	public void testAddStatistics() {
		setComplete();
		BizStatisticsDefinition def = bizStatisticsService.getDefinition("AnPaiTouZi");
		RFSEntityObject descriptor = new RFSEntityDescriptor((short) 1002, 100L);
		int statistics = bizStatisticsService.addStatistics(def, BigDecimal.valueOf(10000L), new Date(), descriptor, null);
		System.out.println("Update row(s): " + statistics);
	}
}
