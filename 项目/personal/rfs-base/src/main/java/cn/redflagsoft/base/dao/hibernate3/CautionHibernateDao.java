/*
 * $Id: CautionHibernateDao.java 6303 2013-08-15 01:19:45Z thh $
 * RiskHibernateDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.RFSObjectable;
import cn.redflagsoft.base.dao.CautionDao;
import cn.redflagsoft.base.event2.ClerkNameChangeListener;
import cn.redflagsoft.base.event2.ObjectNameChangeListener;
import cn.redflagsoft.base.event2.OrgAbbrChangeListener;
import cn.redflagsoft.base.event2.OrgBeforeDeleteListener;

/**
 *
 */
public class CautionHibernateDao extends ObjectHibernateDao<Caution> 
	implements CautionDao, OrgAbbrChangeListener, ClerkNameChangeListener,ObjectNameChangeListener , OrgBeforeDeleteListener{
	private static final Log log = LogFactory.getLog(CautionHibernateDao.class);

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.event2.ClerkNameChangeListener#clerkNameChange(cn.redflagsoft.base.bean.Clerk, java.lang.String, java.lang.String)
	 */
	public void clerkNameChange(Clerk clerk, String oldName, String newName) {
		super.clerkNameChange(clerk, oldName, newName, Caution.OBJECT_TYPE);
		
		String qs1 = "update CAUTION set SUPERVISE_CLERKNAME=? where SUPERVISE_CLERKID=?";
		String qs2 = "update CAUTION set DUTYER_LEADERNAME=? where DUTYER_LEADERID=?";
		String qs3 = "update CAUTION set DUTYER_MANAGERNAME=? where DUTYER_MANAGERID=?";
		String qs4 = "update CAUTION set DUTYERNAME=? where DUTYERID=?";
		
		int rows = executeBatchSQLUpdate(new String[]{qs1,qs2,qs3,qs4}, newName, clerk.getId());
		if(rows > 0 && getCache() != null){
			getCache().clear();
			if(log.isDebugEnabled()){
				log.debug("清理了警示缓存");
			}
		}
		if(log.isDebugEnabled()){
			log.debug("共更新警示中人员相关数据‘" + rows + "’ 个");
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.event2.OrgAbbrChangeListener#orgAbbrChange(cn.redflagsoft.base.bean.Org, java.lang.String, java.lang.String)
	 */
	public void orgAbbrChange(Org org, String oldAbbr, String newAbbr) {
		super.orgAbbrChange(org, oldAbbr, newAbbr, Caution.OBJECT_TYPE);
		String qs1 = "update CAUTION set SUPERVISE_ORGABBR=? where SUPERVISE_ORGID=?";
		String qs2 = "update CAUTION set DUTYER_ORGNAME=? where DUTYER_ORGID=?";
		String qs3 = "update CAUTION set RECTIFICATION_ORGNAME=? where RECTIFICATION_ORGID=?";
		
		int rows = executeBatchSQLUpdate(new String[]{qs1,qs2}, newAbbr, org.getId());
		if(rows > 0 && getCache() != null){
			getCache().clear();
			if(log.isDebugEnabled()){
				log.debug("清理了警示缓存");
			}
		}
		if(log.isDebugEnabled()){
			log.debug("共更新警示中单位相关数据‘" + rows + "’ 个");
		}
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.event2.ObjectNameChangeListener#objectNameChange(cn.redflagsoft.base.bean.RFSObjectable, java.lang.String, java.lang.String)
	 */
	public void objectNameChange(RFSObjectable object, String oldValue,	String newValue) {
		log.debug("对象名称发生了变化，同步更新CAUTION：" + oldValue + " --> " + newValue);
		
		String sql = "update CAUTION set REF_OBJECT_NAME=? where REF_OBJECTID=? and REF_OBJECT_TYPE=?";
		int rows = executeSQLUpdate(sql, newValue, object.getId(), object.getObjectType());
		
		if(rows > 0 && getCache() != null){
			getCache().clear();
			log.debug("清理了 CAUTION 缓存");
		}
		log.debug("共更新CAUTION 的相关对象名称 ‘" + rows + "’个");
	}

	public void orgBeforeDelete(Org org) {
		doBeforeDeleteOrg(org, "superviseOrgId", "dutyerOrgId", "rectificationOrgId");
	}
}