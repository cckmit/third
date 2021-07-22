/*
 * $Id: DatumCategoryWorkProcessTest.java 4844 2011-09-29 09:41:23Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.process.impl;

import java.util.Map;

import com.google.common.collect.Maps;

import cn.redflagsoft.base.process.WorkProcess;
import cn.redflagsoft.base.process.WorkProcessManager;
import cn.redflagsoft.base.test.BaseTests;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class DatumCategoryWorkProcessTest extends BaseTests {
	protected WorkProcessManager workProcessManager;

	/**
	 * Test method for {@link cn.redflagsoft.base.process.impl.DatumCategoryWorkProcess#execute(java.util.Map, boolean)}.
	 */
	public void testExecuteMapBoolean() {
		assertNotNull(workProcessManager);
		WorkProcess process = workProcessManager.getProcess(DatumCategoryWorkProcess.TYPE);
		assertNotNull(process);
		
		//processType=6004&taskType=102&workType=1001&actualProcessType=2204&matterID=102
		Map<String,String> map = Maps.newHashMap();
		map.put("taskType", "102");
		map.put("workType", "1001");
		map.put("actualProcessType", "2204");
		map.put("matterID", "102");
		
		Object object = process.execute(map);
		System.out.println(object);
	}

}
