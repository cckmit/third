package cn.redflagsoft.base.dao.hibernate3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Issue;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.RFSObjectable;
import cn.redflagsoft.base.dao.IssueDao;
import cn.redflagsoft.base.event2.ClerkNameChangeListener;
import cn.redflagsoft.base.event2.ObjectNameChangeListener;
import cn.redflagsoft.base.event2.OrgAbbrChangeListener;
import cn.redflagsoft.base.event2.OrgBeforeDeleteListener;

public class IssueHibernateDao extends ObjectHibernateDao<Issue> implements
		IssueDao, OrgAbbrChangeListener, ClerkNameChangeListener,ObjectNameChangeListener , OrgBeforeDeleteListener{
	private static final Log log = LogFactory
			.getLog(IssueHibernateDao.class);

	public void clerkNameChange(Clerk clerk, String oldName, String newName) {
		if (log.isDebugEnabled()) {
			log.debug("人员信息发生了变化，同步更新Issue的相关人员信息：" + oldName + " --> "
					+ newName);
		}

		String qs1 = "update Issue set reporterName=? where reporterId=?";

		Object[] values = new Object[] { newName, clerk.getId() };
		int rows = executeBatchUpdate(new String[] { qs1 }, values);

		// 清理缓存
		if ((rows) > 0 && getCache() != null) {
			getCache().clear();
			if (log.isDebugEnabled()) {
				log.debug("清理了Issue缓存");
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("共更新Issue的相关人员信息 ‘" + rows + "’个");
		}
	}

	public void orgAbbrChange(Org org, String oldAbbr, String newAbbr) {
		if (log.isDebugEnabled()) {
			log.debug("单位信息发生了变化，同步更新Issue中的相关单位：" + oldAbbr
					+ " --> " + newAbbr);
		}

		String qs1 = "update Issue set orgName=? where orgId=?";
	
		Object[] values = new Object[] { newAbbr, org.getId() };
		int rows = executeBatchUpdate(new String[] { qs1 }, values);

		// 清理缓存
		if ((rows) > 0 && getCache() != null) {
			getCache().clear();
			if (log.isDebugEnabled()) {
				log.debug("清理了Issue缓存");
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("共更新Issue的相关人员信息 ‘" + rows + "’个");
		}
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.event2.ObjectNameChangeListener#objectNameChange(cn.redflagsoft.base.bean.RFSObjectable, java.lang.String, java.lang.String)
	 */
	public void objectNameChange(RFSObjectable object, String oldValue,	String newValue) {
		log.debug("对象名称发生了变化，同步更新Issue：" + oldValue + " --> " + newValue);
		
		String sql = "update Issue set REF_OBJ_NAME=? where REF_OBJ_ID=? and REF_OBJ_TYPE=?";
		int rows = executeSQLUpdate(sql, newValue, object.getId(), object.getObjectType());
		
		if(rows > 0 && getCache() != null){
			getCache().clear();
			log.debug("清理了 Issue 缓存");
		}
		log.debug("共更新Issue 的相关对象名称 ‘" + rows + "’个");
	}

	public void orgBeforeDelete(Org org) {
		doBeforeDeleteOrg(org, "orgId");
	}
}
