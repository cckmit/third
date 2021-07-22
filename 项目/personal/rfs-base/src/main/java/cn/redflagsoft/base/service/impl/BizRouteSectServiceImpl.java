/*
 * $Id: BizRouteSectServiceImpl.java 3996 2010-10-18 06:56:46Z lcj $
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

import cn.redflagsoft.base.bean.BizRouteSect;
import cn.redflagsoft.base.dao.BizRouteSectDao;
import cn.redflagsoft.base.service.BizRouteSectService;

/**
 * 
 * @author ymq
 *
 */
public class BizRouteSectServiceImpl implements BizRouteSectService{
	public static final byte STATUS = 1;
	private BizRouteSectDao bizRouteSectDao;
	
	public void setBizRouteSectDao(BizRouteSectDao bizRouteSectDao) {
		this.bizRouteSectDao = bizRouteSectDao;
	}

	public List<BizRouteSect> findBizRouteSectList(Long routeId) {
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		Criterion c = Restrictions.logic(Restrictions.eq("routeID", routeId)).and(Restrictions.eq("status", STATUS));
		rf.setCriterion(c);
		return bizRouteSectDao.find(rf);
	}
}
