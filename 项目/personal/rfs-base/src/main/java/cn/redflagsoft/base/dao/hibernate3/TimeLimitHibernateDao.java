/*
 * $Id ReceiverHibernateDao.java$
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Restrictions;

import cn.redflagsoft.base.bean.TimeLimit;
import cn.redflagsoft.base.dao.TimeLimitDao;

/**
 * 
 * @author ymq
 *
 */
public class TimeLimitHibernateDao extends AbstractBaseHibernateDao<TimeLimit, Long> implements TimeLimitDao{

	public TimeLimit getTimeLimt(byte category, int type, Long bizId) {
		byte status =1;
		Criterion c = Restrictions.logic(Restrictions.eq("category",category )).and(Restrictions.eq("type", type)).and(Restrictions.eq("bizID", bizId)).and(Restrictions.eq("status",status));
		TimeLimit t = get(c);
		return t;
	}

}
