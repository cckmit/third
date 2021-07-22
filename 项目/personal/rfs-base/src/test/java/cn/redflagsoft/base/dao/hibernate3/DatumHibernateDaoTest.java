/*
 * $Id: DatumHibernateDaoTest.java 4844 2011-09-29 09:41:23Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import cn.redflagsoft.base.bean.Datum;
import cn.redflagsoft.base.test.BaseTests;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class DatumHibernateDaoTest extends BaseTests {
	protected DatumHibernateDao datumDao;
	
	public void testPresent(){
		assertNotNull(datumDao);
	}

	/**
	 * Test method for {@link cn.redflagsoft.base.dao.hibernate3.DatumHibernateDao#findRFSObjectTaskDatum(long, short)}.
	 */
	public void testFindRFSObjectTaskDatum() {
		List<Datum> list = datumDao.findRFSObjectTaskDatum(1000L, (short)110);
		assertNotNull(list);
	}

	/**
	 * Test method for {@link cn.redflagsoft.base.dao.hibernate3.DatumHibernateDao#findEntityObjectTaskDatum(long, short, short)}.
	 */
	public void testFindEntityObjectTaskDatum() {
		List<Datum> list = datumDao.findEntityObjectTaskDatum(1000L, (short)1002, (short)110);
		assertNotNull(list);
	}

	/**
	 * Test method for {@link cn.redflagsoft.base.dao.hibernate3.DatumHibernateDao#findRFSObjectWorkDatum(long, short)}.
	 */
	public void testFindRFSObjectWorkDatum() {
		List<Datum> list = datumDao.findRFSObjectWorkDatum(1000L, (short)110);
		assertNotNull(list);
	}

	/**
	 * Test method for {@link cn.redflagsoft.base.dao.hibernate3.DatumHibernateDao#findEntityObjectWorkDatum(long, short, short)}.
	 */
	public void testFindEntityObjectWorkDatum() {
		List<Datum> list = datumDao.findEntityObjectWorkDatum(1000L, (short)1002, (short)110);
		assertNotNull(list);
	}

	/**
	 * Test method for {@link cn.redflagsoft.base.dao.hibernate3.DatumHibernateDao#findTaskDatum(long, short, short)}.
	 */
	public void testFindTaskDatum() {
		List<Datum> list = datumDao.findTaskDatum(1000L, (short)1002, (short)110);
		assertNotNull(list);
	}

	/**
	 * Test method for {@link cn.redflagsoft.base.dao.hibernate3.DatumHibernateDao#findWorkDatum(long, short, short)}.
	 */
	public void testFindWorkDatum() {
		List<Datum> list = datumDao.findWorkDatum(1000L, (short)1002, (short)110);
		assertNotNull(list);
	}

}
