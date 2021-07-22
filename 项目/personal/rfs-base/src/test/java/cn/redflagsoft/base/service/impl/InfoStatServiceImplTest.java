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

import java.util.List;

import cn.redflagsoft.base.bean.InfoDetail;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.dao.ObjectDao;
import cn.redflagsoft.base.test.BaseTests;
import junit.framework.TestCase;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class InfoStatServiceImplTest extends BaseTests {
	protected InfoStatServiceImpl infoStatService;
	protected ObjectDao<RFSObject> objectDao;
	
	public void testPresent(){
		System.out.println(infoStatService);
		assertNotNull(infoStatService);
	}
	
	public void testStat(){
		RFSObject object = objectDao.get(9485L);
		System.out.println(object);
//		List<InfoDetail> list = infoStatService.stat(object);
//		for(InfoDetail d: list){
//			System.out.println(d);
//		}
		infoStatService.statAndSave(object);
		setComplete();
	}

}
