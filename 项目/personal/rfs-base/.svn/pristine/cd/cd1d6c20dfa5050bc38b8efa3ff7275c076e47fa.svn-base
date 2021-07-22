/*
 * JobHibernateDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import cn.redflagsoft.base.bean.Job;
import cn.redflagsoft.base.dao.JobDao;

/**
 * @author Administrator
 *
 */
public class JobHibernateDao extends AbstractBaseHibernateDao<Job,Long> implements JobDao{

	@SuppressWarnings("unchecked")
	public List<Job> findJobByNodeSn(byte category, Long nodeSn) {
		String hql = "select a from Job a, BizTrackNodeInstance b where a.sn=b.bizSN and b.category=? and nodeSN=?";
		return getHibernateTemplate().find(hql, new Object[]{category, nodeSn});
	}
}
