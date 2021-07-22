package cn.redflagsoft.base.dao.hibernate3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.Progress;
import cn.redflagsoft.base.dao.ProgressDao;
import cn.redflagsoft.base.event2.ClerkNameChangeListener;
import cn.redflagsoft.base.event2.OrgAbbrChangeListener;
import cn.redflagsoft.base.event2.OrgBeforeDeleteListener;

public class ProgressHibernateDao extends
		AbstractBaseHibernateDao<Progress, Long> implements ProgressDao,
		OrgAbbrChangeListener, ClerkNameChangeListener, OrgBeforeDeleteListener{
	private static final Log log = LogFactory.getLog(ProgressHibernateDao.class);

	public void clerkNameChange(Clerk clerk, String oldName, String newName) {
		if (log.isDebugEnabled()) {
			log.debug("人员信息发生了变化，同步更新Progress的相关人员信息：" + oldName + " --> "
					+ newName);
		}

		String qs1 = "update Progress set reporterName=? where reporterId=?";

		Object[] values = new Object[] { newName, clerk.getId() };
		int rows = executeBatchUpdate(new String[] { qs1 }, values);

		// 清理缓存
		if ((rows) > 0 && getCache() != null) {
			getCache().clear();
			if (log.isDebugEnabled()) {
				log.debug("清理了Progress缓存");
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("共更新Progress的相关人员信息 ‘" + rows + "’个");
		}
	}

	public void orgAbbrChange(Org org, String oldAbbr, String newAbbr) {
		if (log.isDebugEnabled()) {
			log.debug("单位信息发生了变化，同步更新Progress中的相关单位：" + oldAbbr + " --> "
					+ newAbbr);
		}

		String qs1 = "update Progress set orgName=? where orgId=?";

		Object[] values = new Object[] { newAbbr, org.getId() };
		int rows = executeBatchUpdate(new String[] { qs1 }, values);

		// 清理缓存
		if ((rows) > 0 && getCache() != null) {
			getCache().clear();
			if (log.isDebugEnabled()) {
				log.debug("清理了Progress缓存");
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("共更新Progress的相关人员信息 ‘" + rows + "’个");
		}
	}

	public void orgBeforeDelete(Org org) {
		doBeforeDeleteOrg(org, "orgId");
	}
}
