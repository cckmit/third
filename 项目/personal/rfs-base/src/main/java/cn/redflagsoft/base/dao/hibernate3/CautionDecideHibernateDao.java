/*
 * $Id: CautionDecideHibernateDao.java 6303 2013-08-15 01:19:45Z thh $
 * RiskHibernateDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.bean.CautionDecide;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.dao.CautionDecideDao;
import cn.redflagsoft.base.event2.ClerkNameChangeListener;
import cn.redflagsoft.base.event2.OrgAbbrChangeListener;
import cn.redflagsoft.base.event2.OrgBeforeDeleteListener;


public class CautionDecideHibernateDao extends
		AbstractBaseHibernateDao<CautionDecide, Long> implements
		CautionDecideDao, OrgAbbrChangeListener, ClerkNameChangeListener, OrgBeforeDeleteListener{
	private static final Log log = LogFactory
			.getLog(CautionDecideHibernateDao.class);

	public void clerkNameChange(Clerk clerk, String oldName, String newName) {
		if (log.isDebugEnabled()) {
			log.debug("人员信息发生了变化，同步更新CautionDecide的相关人员信息：" + oldName + " --> "
					+ newName);
		}
		
	}

	public void orgAbbrChange(Org org, String oldAbbr, String newAbbr) {
		if (log.isDebugEnabled()) {
			log.debug("单位信息发生了变化，同步更新CautionDecide中的相关单位：" + oldAbbr + " --> "
					+ newAbbr);
		}

		String qs1 = "update CautionDecide set decideOrgName=? where decideOrgId=?";

		Object[] values = new Object[] { newAbbr, org.getId() };
		int rows = executeBatchUpdate(new String[] { qs1}, values);

		// 清理缓存
		if (rows > 0 && getCache() != null) {
			getCache().clear();
			if (log.isDebugEnabled()) {
				log.debug("清理了CautionDecide缓存");
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("共更新CautionDecide中的相关单位 ‘" + rows + "’ 个");
		}
	}

	public void orgBeforeDelete(Org org) {
		doBeforeDeleteOrg(org, "decideOrgId");
	}
}