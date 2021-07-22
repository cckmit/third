/*
 * $Id: BizTrackNodeServiceImpl.java 4615 2011-08-21 07:10:37Z lcj $
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
import cn.redflagsoft.base.bean.BizTrackNode;
import cn.redflagsoft.base.dao.BizRouteNodeDao;
import cn.redflagsoft.base.dao.BizTrackNodeDao;
import cn.redflagsoft.base.dao.BizTrackNodeInstanceDao;
import cn.redflagsoft.base.service.BizTrackNodeInstanceService;
import cn.redflagsoft.base.service.BizTrackNodeService;

/**
 * 
 * @author ymq
 *
 */
public class BizTrackNodeServiceImpl implements BizTrackNodeService{
	private BizRouteNodeDao bizRouteNodeDao;
	private BizTrackNodeDao bizTrackNodeDao;
	private BizTrackNodeInstanceService bizTrackNodeInstanceService;

	public void setBizRouteNodeDao(BizRouteNodeDao bizRouteNodeDao) {
		this.bizRouteNodeDao = bizRouteNodeDao;
	}

	public void setBizTrackNodeDao(BizTrackNodeDao bizTrackNodeDao) {
		this.bizTrackNodeDao = bizTrackNodeDao;
	}

	public void setBizTrackNodeInstanceDao(BizTrackNodeInstanceDao bizTrackNodeInstanceDao) {
	}

	public void setBizTrackNodeInstanceService(BizTrackNodeInstanceService bizTrackNodeInstanceService) {
		this.bizTrackNodeInstanceService = bizTrackNodeInstanceService;
	}

	public boolean createBizTrackNode(Long bizTrackSN, Long routeId) {
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		rf.setCriterion(Restrictions.logic(
				Restrictions.eq("status", Byte.parseByte("1"))).and(
				Restrictions.eq("routeID", routeId)));
		List<BizRouteNode> bizRouteNodeList = bizRouteNodeDao.find(rf);
		for(BizRouteNode bizRouteNode : bizRouteNodeList){
			BizTrackNode bizTrackNode = new BizTrackNode();
			bizTrackNode.setTrackSN(bizTrackSN);
			bizTrackNode.setSectNo(bizRouteNode.getSectNo());
			bizTrackNode.setStepNo(bizRouteNode.getStepNo());
			bizTrackNode.setSeatNo(bizRouteNode.getSeatNo());
			bizTrackNode.setBizType(bizRouteNode.getBizType());
			bizTrackNode.setBizName(bizRouteNode.getBizName());
			bizTrackNode.setDutyer(bizRouteNode.getDutyer());
			bizTrackNode.setTimeLimit(bizRouteNode.getTimeLimit());
			bizTrackNode.setTimeUnit(bizRouteNode.getTimeUnit());
			bizTrackNode.setDutyerID(bizRouteNode.getDutyerID());
			bizTrackNodeDao.save(bizTrackNode);
		}
		return true;
	}

	public boolean deriveBizTrackNode(byte bizCategory, int bizType, Long bizSN, Long trackSN) {
		Criterion c = Restrictions.logic(Restrictions.eq("trackSN", trackSN)).and(Restrictions.eq("bizType", bizType)).and(Restrictions.lt("summary", (byte)1));
		BizTrackNode bizTrackNode = bizTrackNodeDao.get(c);
		if(bizTrackNode != null)
		{
			BizTrackNode entity = new BizTrackNode();
			entity.setTrackSN(bizTrackNode.getTrackSN());
			entity.setSectNo(bizTrackNode.getSectNo());
			entity.setStepNo(bizTrackNode.getStepNo());
			entity.setSeatNo(bizTrackNode.getSeatNo());
			entity.setBizType(bizTrackNode.getBizType());
			entity.setBizName(bizTrackNode.getBizName());
			entity.setDutyer(bizTrackNode.getDutyer());
			entity.setTimeLimit(bizTrackNode.getTimeLimit());
			entity.setTimeUnit(bizTrackNode.getTimeUnit());
			entity.setResult(bizTrackNode.getResult());
			entity.setSummary((byte)1);
			BizTrackNode node = bizTrackNodeDao.save(entity);
			bizTrackNode.setSummary((byte)(bizTrackNode.getSummary() - 1));
			bizTrackNodeDao.update(bizTrackNode);
			bizTrackNodeInstanceService.createBizTrackNodeInstance(bizCategory, bizType, bizSN,trackSN, node.getSn());
			return true;
		}
		return false;
	}
}
