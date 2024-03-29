/*
 * $Id: DistrictDaoTest.java 4352 2011-04-26 05:56:19Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import cn.redflagsoft.base.bean.DistrictBean;
import cn.redflagsoft.base.dao.hibernate3.DistrictHibernateDao;
import cn.redflagsoft.base.test.BaseTests;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class DistrictDaoTest extends BaseTests {
	protected DistrictHibernateDao districtDao;
	
	public void etestSave(){
		DistrictBean d = new DistrictBean();
		d.setCode("510000");
		d.setName("广东省");
		d.setDisplayOrder(20);
		
		d = districtDao.save(d);
		
		DistrictBean d2 = new DistrictBean();
		d2.setCode("518000");
		d2.setName("深圳");
		d2.setDisplayOrder(755);
		d2.setParentCode(d.getCode());
		districtDao.save(d2);
		
		super.setComplete();
	}
	
	public void testFind(){
		List<DistrictBean> list = districtDao.find();
		for (DistrictBean district : list) {
			System.out.println(district);
		}
	}
}
