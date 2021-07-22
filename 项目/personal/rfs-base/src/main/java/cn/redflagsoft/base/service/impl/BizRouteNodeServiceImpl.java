/*
 * $Id: BizRouteNodeServiceImpl.java 3996 2010-10-18 06:56:46Z lcj $
 * JobServiceImpl.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.BizRouteNode;
import cn.redflagsoft.base.dao.BizRouteNodeDao;
import cn.redflagsoft.base.service.BizRouteNodeService;

/**
 * 
 * @author ymq
 *
 */
public class BizRouteNodeServiceImpl implements BizRouteNodeService{
	public static final byte STATUS = 1;
	private BizRouteNodeDao bizRouteNodeDao;

	public void setBizRouteNodeDao(BizRouteNodeDao bizRouteNodeDao) {
		this.bizRouteNodeDao = bizRouteNodeDao;
	}

	public List<BizRouteNode> findBizRouteNodeList(Long routeId) {
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		Criterion c = Restrictions.logic(Restrictions.eq("routeID", routeId)).and(Restrictions.eq("status", STATUS));
		rf.setCriterion(c);
		return bizRouteNodeDao.find(rf);
	}
}
