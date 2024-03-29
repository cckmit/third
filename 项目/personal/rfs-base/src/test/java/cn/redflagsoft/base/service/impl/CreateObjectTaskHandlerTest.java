/*
 * $Id: CreateObjectTaskHandlerTest.java 5014 2011-11-04 06:26:16Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.Date;

import cn.redflagsoft.base.support.TimeFrequency;
import junit.framework.TestCase;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class CreateObjectTaskHandlerTest extends TestCase {

	/**
	 * Test method for {@link cn.redflagsoft.base.service.impl.CreateObjectTaskHandler#createTask(cn.redflagsoft.base.bean.RFSObject)}.
	 */
	public void testCreateTask() {
		//CreateObjectTaskHandler handler = new CreateObjectTaskHandler();
		Date[] dates = CreateObjectTaskHandler.parseStartAndEndTime(TimeFrequency.Weekly);
		System.out.println(dates[0]);
		System.out.println(dates[1]);
	}

}
