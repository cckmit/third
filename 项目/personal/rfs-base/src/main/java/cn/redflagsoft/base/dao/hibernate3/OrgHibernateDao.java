/*
 * $Id: OrgHibernateDao.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.dao.OrgDao;

/**
 * 
 * @author ymq
 *
 */
public class OrgHibernateDao extends AbstractBaseHibernateDao<Org, Long> implements OrgDao{

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.hibernate3.AbstractHibernateDao#getDataProperty()
	 */
	@Override
	protected String getDataProperty() {
		return "id";
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.hibernate3.AbstractHibernateDao#getLabelProperty()
	 */
	@Override
	protected String getLabelProperty() {
		return "abbr";
	}
}
