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


import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.dao.EntityObjectToWorkDao;
import cn.redflagsoft.base.test.BaseTests;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class WorkServiceImplTest extends BaseTests{
	protected EntityObjectToWorkDao entityObjectToWorkDao;

	public void testGetEntityToWorkCount(){
		//workSN=41153 and objectId=18574 and objectType=121
		//workSN=41151 and objectId=18572 and objectType=121
		Logic logic = Restrictions.logic(Restrictions.eq("workSN", 41153L))
				.and(Restrictions.eq("objectId", 18574L))
				.and(Restrictions.eq("objectType", 121));
		ResultFilter filter = new ResultFilter(logic, null);
		int count = entityObjectToWorkDao.getCount(filter);
		System.out.println(count);
	}
}
