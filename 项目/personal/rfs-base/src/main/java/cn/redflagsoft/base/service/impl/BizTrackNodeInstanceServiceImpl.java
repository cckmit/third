/*
 * $Id: BizTrackNodeInstanceServiceImpl.java 5998 2012-08-21 00:42:50Z lcj $
 * JobServiceImpl.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.BizTrackNode;
import cn.redflagsoft.base.bean.BizTrackNodeInstance;
import cn.redflagsoft.base.dao.BizTrackNodeDao;
import cn.redflagsoft.base.dao.BizTrackNodeInstanceDao;
import cn.redflagsoft.base.service.BizTrackNodeInstanceService;

/**
 * 
 * @author ymq
 *
 */
public class BizTrackNodeInstanceServiceImpl implements BizTrackNodeInstanceService {
	private static final Log log = LogFactory.getLog(BizTrackNodeInstanceServiceImpl.class);
	private BizTrackNodeInstanceDao bizTrackNodeInstanceDao;
	private BizTrackNodeDao bizTrackNodeDao;
	
	public void setBizTrackNodeInstanceDao(BizTrackNodeInstanceDao bizTrackNodeInstanceDao) {
		this.bizTrackNodeInstanceDao = bizTrackNodeInstanceDao;
	}

	public void setBizTrackNodeDao(BizTrackNodeDao bizTrackNodeDao) {
		this.bizTrackNodeDao = bizTrackNodeDao;
	}

	public boolean createBizTrackNodeInstance(byte bizCategory, int bizType, Long bizSN, Long trackSN, Long nodeSN) {
		if(trackSN == null || bizSN == null){
			log.debug("trackSN, bizSN 参数不全，createBizTrackNodeInstance方法不执行！trackSN=" + trackSN + ", bizSN=" + bizSN);
			return false;
		}
		
		BizTrackNode bizTrackNode = null;
		if(nodeSN == null){
			Criterion c = Restrictions.logic(Restrictions.eq("trackSN", trackSN)).and(Restrictions.eq("bizType", bizType));
			bizTrackNode = bizTrackNodeDao.get(c);
		}else{
			Criterion c = Restrictions.logic(Restrictions.eq("sn", nodeSN));
			bizTrackNode = bizTrackNodeDao.get(c);
		}
		if(bizTrackNode != null){
			BizTrackNodeInstance entity = new BizTrackNodeInstance();
			trackSN=bizTrackNode.getTrackSN();
			nodeSN=bizTrackNode.getSn();
			entity.setTrackSN(trackSN);
			entity.setNodeSN(nodeSN);
			entity.setCategory(bizCategory);
			entity.setBizSN(bizSN);
			bizTrackNode.setStatus((byte)1);
			bizTrackNodeDao.update(bizTrackNode);
			bizTrackNodeInstanceDao.save(entity);
			return true;
		}
		else
			return false;
	}

	public boolean createBizTrackNodeInstance(byte bizCategory, int bizType, Long bizSN, Long trackSN) {
		return createBizTrackNodeInstance(bizCategory, bizType, bizSN, trackSN, null);
	}

	public boolean updateBizTrackNodeStatus(byte bizCategory, Long bizSN, byte status) {
		if(bizSN == null){
			log.debug("bizSN为空，updateBizTrackNodeStatus方法不执行");
			return false;
		}
		
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		rf.setCriterion(Restrictions.logic(Restrictions.eq("category", bizCategory)).and(Restrictions.eq("bizSN", bizSN)));
		List<BizTrackNodeInstance> elements = bizTrackNodeInstanceDao.find(rf);
		if(elements != null && !elements.isEmpty()) {
			BizTrackNode tmp;
			for (BizTrackNodeInstance btni : elements) {
				tmp = bizTrackNodeDao.get(btni.getNodeSN());
				tmp.setStatus(status);
				bizTrackNodeDao.update(tmp);
			}
			return true;
		} else {
			log.debug("updateBizTrackNodeInstanceStatus 时，获取BizTrackNodeInstance列表为空 ... return false");
			return false;
		}
	}

	public boolean moveBizTrackNodeInstance(byte bizCategory, int bizType, Long bizSN, Long trackSN) {
		if(trackSN == null || bizSN == null){
			log.debug("trackSN, bizSN 参数不全，moveBizTrackNodeInstance方法不执行！trackSN=" + trackSN + ", bizSN=" + bizSN);
			return false;
		}

		Criterion c = Restrictions.logic(Restrictions.eq("trackSN", trackSN)).and(Restrictions.eq("bizType", bizType));
		BizTrackNode bizTrackNode = bizTrackNodeDao.get(c);
		if (bizTrackNode == null) {
			return false;
		} else {
			c = Restrictions.logic(Restrictions.eq("trackSN", trackSN)).and(
					Restrictions.eq("category", bizCategory)).and(
					Restrictions.eq("bizSN", bizSN));
			BizTrackNodeInstance bizTrackNodeInstance = bizTrackNodeInstanceDao.get(c);
			if(bizTrackNodeInstance != null) {
				bizTrackNodeInstance.setNodeSN(bizTrackNode.getSn());
				bizTrackNodeInstanceDao.update(bizTrackNodeInstance);
			} else {
				createBizTrackNodeInstance(bizCategory, bizType, bizSN, trackSN, bizTrackNode.getSn());
			}
			return true;
		}
	}
}
