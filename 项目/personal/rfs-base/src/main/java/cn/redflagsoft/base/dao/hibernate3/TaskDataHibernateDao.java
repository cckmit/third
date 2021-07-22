/*
 * $Id: TaskDataHibernateDao.java 4444 2011-06-30 03:18:49Z lcj $
 * TaskDataHibernateDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import cn.redflagsoft.base.bean.Datum;
import cn.redflagsoft.base.bean.TaskData;
import cn.redflagsoft.base.dao.TaskDataDao;

/**
 * @author Administrator
 *
 */
public class TaskDataHibernateDao extends AbstractBaseHibernateDao<TaskData,Long> implements TaskDataDao{
	
	@SuppressWarnings("unchecked")
	public List<TaskData> findTaskDataByTaskSN(Long taskSN, Long datumId, Long datumCategoryId) {
		String hql = "select a from TaskData a where a.taskSN=? and a.datumID=? and a.datumCategoryID=?";
		return getHibernateTemplate().find(hql, new Object[]{taskSN, datumId, datumCategoryId});
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.TaskDataDao#findDatum(long)
	 */
	@SuppressWarnings("unchecked")
	public List<Datum> findDatum(long taskSN) {
		String qs = "select distinct d from Datum d, TaskData td where d.id=td.datumID and td.taskSN=? order by d.orderNo";
		return getHibernateTemplate().find(qs, taskSN);
	}
}
