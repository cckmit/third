/*
 * $Id ReceiverHibernateDao.java$
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import cn.redflagsoft.base.bean.BizTrackNode;
import cn.redflagsoft.base.dao.BizTrackNodeDao;

/**
 * 
 * @author ymq
 *
 */
public class BizTrackNodeHibernateDao extends AbstractBaseHibernateDao<BizTrackNode, Long> implements BizTrackNodeDao{
	@SuppressWarnings("unchecked")
	public List<BizTrackNode> getBizTrackNode(Long sn, byte summary) {
		String hql = "select a from BizTrackNode a where a.sn=? and a.summary=?";
		return getHibernateTemplate().find(hql, new Object[]{sn, summary});
	}
}
