/*
 * $Id: CautionNotifyHibernateDao.java 5971 2012-08-03 05:57:13Z lcj $
 * RiskHibernateDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.bean.CautionNotify;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.dao.CautionNotifyDao;
import cn.redflagsoft.base.event2.ClerkNameChangeListener;
import cn.redflagsoft.base.event2.OrgAbbrChangeListener;

/**
 *
 */
public class CautionNotifyHibernateDao extends
		AbstractBaseHibernateDao<CautionNotify, Long> implements
		CautionNotifyDao, OrgAbbrChangeListener, ClerkNameChangeListener {
	private static final Log log = LogFactory
			.getLog(CautionNotifyHibernateDao.class);

	public void clerkNameChange(Clerk clerk, String oldName, String newName) {
		if (log.isDebugEnabled()) {
			log.debug("人员信息发生了变化，同步更新CautionNotify的相关人员信息：" + oldName
					+ " --> " + newName);
		}
		String qs1 = "update CautionNotify set notifyClerkName=? where notifyClerkId=?";
		String qs2 = "update CautionNotify set notifyTargetName=? where notifyTargetId=?";

		Object[] values = new Object[] { newName, clerk.getId() };
		int rows = executeBatchUpdate(new String[] { qs1,qs2}, values);

		// 清理缓存
		if ((rows) > 0 && getCache() != null) {
			getCache().clear();
			if (log.isDebugEnabled()) {
				log.debug("清理了CautionNotify缓存");
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("共更新CautionNotify的相关人员信息 ‘" + rows + "’个");
		}

	}

	public void orgAbbrChange(Org org, String oldAbbr, String newAbbr) {
		if (log.isDebugEnabled()) {
			log.debug("单位信息发生了变化，同步更新CautionNotify中的相关单位：" + oldAbbr + " --> "
					+ newAbbr);
		}
	}
}