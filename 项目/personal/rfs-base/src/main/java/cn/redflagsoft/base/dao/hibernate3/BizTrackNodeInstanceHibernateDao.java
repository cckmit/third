/*
 * $Id: BizTrackNodeInstanceHibernateDao.java 3996 2010-10-18 06:56:46Z lcj $
 * BizRouteNodeHibernateDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;
import java.util.Map;

import cn.redflagsoft.base.bean.BizTrackNodeInstance;
import cn.redflagsoft.base.dao.BizTrackNodeInstanceDao;

/**
 * 
 * @author ymq
 *
 */
public class BizTrackNodeInstanceHibernateDao extends
		AbstractBaseHibernateDao<BizTrackNodeInstance, Long> implements BizTrackNodeInstanceDao {

	@SuppressWarnings("unchecked")
	public List<BizTrackNodeInstance> getBizTrackNodeInstance(Long nodeSN){
		String hql = "select a from BizTrackNodeInstance a where a.nodeSN=?";
		return getHibernateTemplate().find(hql, nodeSN);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Long>> findBizTrackNodeCount(Long nodeSN, byte category, Long managerId) {
		String hql = "select new map(a.nodeSN as " + BizTrackNodeInstance.MAP_QUERY_COLUMN_NODESN + ",count(*) as " +
				BizTrackNodeInstance.MAP_QUERY_FUNCATION_COUNT + ") from BizTrackNodeInstance a " +
				"where a.trackSN=? and a.category=? " + 
				(managerId == null ? "" : " and a.bizSN in(select id from RFSObject where manager="+managerId+")") + 
				" group by nodeSN";
		return getHibernateTemplate().find(hql, new Object[]{nodeSN, category});
	}
}
