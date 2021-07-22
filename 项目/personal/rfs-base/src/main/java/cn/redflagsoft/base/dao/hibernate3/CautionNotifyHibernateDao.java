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
			log.debug("��Ա��Ϣ�����˱仯��ͬ������CautionNotify�������Ա��Ϣ��" + oldName
					+ " --> " + newName);
		}
		String qs1 = "update CautionNotify set notifyClerkName=? where notifyClerkId=?";
		String qs2 = "update CautionNotify set notifyTargetName=? where notifyTargetId=?";

		Object[] values = new Object[] { newName, clerk.getId() };
		int rows = executeBatchUpdate(new String[] { qs1,qs2}, values);

		// ������
		if ((rows) > 0 && getCache() != null) {
			getCache().clear();
			if (log.isDebugEnabled()) {
				log.debug("������CautionNotify����");
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("������CautionNotify�������Ա��Ϣ ��" + rows + "����");
		}

	}

	public void orgAbbrChange(Org org, String oldAbbr, String newAbbr) {
		if (log.isDebugEnabled()) {
			log.debug("��λ��Ϣ�����˱仯��ͬ������CautionNotify�е���ص�λ��" + oldAbbr + " --> "
					+ newAbbr);
		}
	}
}