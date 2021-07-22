/*
 * $Id: EntityObjectToWorkHibernateDao.java 5147 2011-11-30 09:38:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.EntityObjectToWork;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;
import cn.redflagsoft.base.dao.EntityObjectToWorkDao;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class EntityObjectToWorkHibernateDao extends
	AbstractBaseHibernateDao<EntityObjectToWork, Long> implements EntityObjectToWorkDao {

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.RFSEntityObjectWorkDao#findWorkSNs(long, short)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> findWorkSNs(long objectId, int objectType) {
		String qs = "select w.workSN from EntityObjectToWork w where w.objectId=? and w.objectType=?";
		return getQuerySupport().find(qs, new Object[]{objectId, objectType});
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.RFSEntityObjectWorkDao#findWorks(org.opoo.ndao.support.ResultFilter)
	 */
	@SuppressWarnings("unchecked")
	public List<Work> findWorks(ResultFilter filter) {
		String qs = "select w from EntityObjectToWork e, Work w where e.workSN=w.sn";
		return getQuerySupport().find(qs, filter);
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> findTaskSNs(long objectId, int objectType){
		String qs = "select distinct w.taskSN from EntityObjectToWork w where w.objectId=? and w.objectType=?";
		return getQuerySupport().find(qs, new Object[]{objectId, objectType});
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.EntityObjectWorkDao#findTasks(org.opoo.ndao.support.ResultFilter)
	 */
	@SuppressWarnings("unchecked")
	public List<Task> findTasks(ResultFilter filter) {
		String qs = "select distinct t from EntityObjectToWork e, Work w, Task t where e.workSN=w.sn and w.taskSN=t.sn";
		return getQuerySupport().find(qs, filter);
	}
}
