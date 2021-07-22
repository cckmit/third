/*
 * $Id$
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.util;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class DateUtilTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Calendar d1 = Calendar.getInstance();
		Calendar d2 = Calendar.getInstance();
		
		d1.set(Calendar.DAY_OF_MONTH, 12);
		d2.set(Calendar.DAY_OF_MONTH, 16);
		
		int diff = DateUtil.daysDiff(d1.getTime(), d2.getTime());
		System.out.println(diff);
		assertEquals(4, diff);
	}
}
