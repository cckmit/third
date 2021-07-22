/*
 * $Id: BizTrackNodeInstanceDao.java 3996 2010-10-18 06:56:46Z lcj $
 * BizRouteNodeHibernateDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.List;
import java.util.Map;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.BizTrackNodeInstance;

/**
 * 
 * @author ymq
 *
 */
public interface BizTrackNodeInstanceDao extends Dao<BizTrackNodeInstance, Long>{
	List<BizTrackNodeInstance> getBizTrackNodeInstance(Long nodeSN);
	
	List<Map<String, Long>> findBizTrackNodeCount(Long nodeSN, byte category, Long managerId);
}
