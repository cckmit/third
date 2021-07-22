/*
 * $Id: CautionCheckHibernateDao.java 6304 2013-08-15 09:03:33Z thh $
 * RiskHibernateDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.bean.CautionCheck;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.dao.CautionCheckDao;
import cn.redflagsoft.base.event2.ClerkNameChangeListener;
import cn.redflagsoft.base.event2.OrgAbbrChangeListener;
import cn.redflagsoft.base.event2.OrgBeforeDeleteListener;


public class CautionCheckHibernateDao extends
		AbstractBaseHibernateDao<CautionCheck, Long> implements
		CautionCheckDao, OrgAbbrChangeListener, ClerkNameChangeListener, OrgBeforeDeleteListener {
	private static final Log log = LogFactory
			.getLog(CautionCheckHibernateDao.class);

	public void clerkNameChange(Clerk clerk, String oldName, String newName) {
		if (log.isDebugEnabled()) {
			log.debug("��Ա��Ϣ�����˱仯��ͬ������CautionCheck�������Ա��Ϣ��" + oldName
					+ " --> " + newName);
		}
		String qs1 = "update CautionCheck set checkerName=? where checkerId=?";

		Object[] values = new Object[] { newName, clerk.getId() };
		int rows = executeBatchUpdate(new String[] { qs1}, values);

		// ������
		if ((rows) > 0 && getCache() != null) {
			getCache().clear();
			if (log.isDebugEnabled()) {
				log.debug("������CautionCheck����");
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("������CautionCheck�������Ա��Ϣ ��" + rows + "����");
		}

	}

	public void orgAbbrChange(Org org, String oldAbbr, String newAbbr) {
		if (log.isDebugEnabled()) {
			log.debug("��λ��Ϣ�����˱仯��ͬ������CautionCheck�е���ص�λ��" + oldAbbr + " --> "
					+ newAbbr);
		}

		String qs1 = "update CautionCheck set checkOrgName=? where checkOrgId=?";

		Object[] values = new Object[] { newAbbr, org.getId() };
		int rows = executeBatchUpdate(new String[] { qs1 }, values);

		// ������
		if (rows > 0 && getCache() != null) {
			getCache().clear();
			if (log.isDebugEnabled()) {
				log.debug("������CautionCheck����");
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("������CautionCheck�е���ص�λ ��" + rows + "�� ��");
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.event2.OrgBeforeDeleteListener#orgBeforeDelete(cn.redflagsoft.base.bean.Org)
	 */
	public void orgBeforeDelete(Org org) {
		doBeforeDeleteOrg(org, "checkOrgId");
	}
}