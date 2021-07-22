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
			log.debug("��Ա��Ϣ�����˱仯��ͬ������CautionDecide�������Ա��Ϣ��" + oldName + " --> "
					+ newName);
		}
		
	}

	public void orgAbbrChange(Org org, String oldAbbr, String newAbbr) {
		if (log.isDebugEnabled()) {
			log.debug("��λ��Ϣ�����˱仯��ͬ������CautionDecide�е���ص�λ��" + oldAbbr + " --> "
					+ newAbbr);
		}

		String qs1 = "update CautionDecide set decideOrgName=? where decideOrgId=?";

		Object[] values = new Object[] { newAbbr, org.getId() };
		int rows = executeBatchUpdate(new String[] { qs1}, values);

		// ������
		if (rows > 0 && getCache() != null) {
			getCache().clear();
			if (log.isDebugEnabled()) {
				log.debug("������CautionDecide����");
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("������CautionDecide�е���ص�λ ��" + rows + "�� ��");
		}
	}

	public void orgBeforeDelete(Org org) {
		doBeforeDeleteOrg(org, "decideOrgId");
	}
}