/*
 * $Id: BizRouteServiceImpl.java 4615 2011-08-21 07:10:37Z lcj $
 * JobServiceImpl.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service.impl;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Restrictions;

import cn.redflagsoft.base.bean.BizRoute;
import cn.redflagsoft.base.bean.BizRouteMap;
import cn.redflagsoft.base.dao.BizRouteDao;
import cn.redflagsoft.base.dao.BizRouteMapDao;
import cn.redflagsoft.base.service.BizRouteService;

/**
 * 
 * @author ymq
 *
 */
public class BizRouteServiceImpl implements BizRouteService {
	public static final byte STATUS = 1;
	private BizRouteDao bizRouteDao;
	private BizRouteMapDao bizRouteMapDao;

	public void setBizRouteDao(BizRouteDao bizRouteDao) {
		this.bizRouteDao = bizRouteDao;
	}

	public void setBizRouteMapDao(BizRouteMapDao bizRouteMapDao) {
		this.bizRouteMapDao = bizRouteMapDao;
	}

	public BizRoute getBizRoute(byte category, int bizType, Long bizId) {
		Criterion c = Restrictions.logic(Restrictions.eq("category", category))
				.and(Restrictions.eq("bizType", bizType)).and(
						Restrictions.eq("bizID", bizId)).and(
						Restrictions.eq("status", STATUS));
		BizRouteMap bizRouteMap = bizRouteMapDao.get(c);
		return bizRouteMap != null ? bizRouteDao.get(bizRouteMap.getRouteId()) : null;
	}
}
