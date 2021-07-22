package cn.redflagsoft.base.dao.hibernate3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.SignCard;
import cn.redflagsoft.base.dao.SignCardDao;
import cn.redflagsoft.base.event2.ClerkNameChangeListener;
import cn.redflagsoft.base.event2.OrgAbbrChangeListener;
import cn.redflagsoft.base.event2.OrgBeforeDeleteListener;

public class SignCardHibernateDao extends ObjectHibernateDao<SignCard> implements 
		SignCardDao, OrgAbbrChangeListener,ClerkNameChangeListener, OrgBeforeDeleteListener{

	private static final Log log = LogFactory.getLog(SignCardHibernateDao.class);
	
	public void orgAbbrChange(Org org, String oldAbbr, String newAbbr) {
		if(log.isDebugEnabled()){
			log.debug("单位信息发生了变化，同步更新SignCard中的相关单位：" + oldAbbr + " --> " + newAbbr);
		}
		
		String qs1 = "update SignCard set rvDutyerOrgName=? where rvDutyerOrgId=?";
		
		Object[] values = new Object[]{newAbbr, org.getId()};
		int rows = executeBatchUpdate(new String[]{qs1}, values);
		
		//清理缓存
		if((rows) > 0 && getCache() != null){
			getCache().clear();
			if(log.isDebugEnabled()){
				log.debug("清理了SignCard缓存");
			}
		}
		if(log.isDebugEnabled()){
			log.debug("共更新SignCard的相关单位信息 ‘" + rows + "’个"); 
		}
		
	}

	public void clerkNameChange(Clerk clerk, String oldName, String newName) {
		if(log.isDebugEnabled()){
			log.debug("人员信息发生了变化，同步更新SignCard的相关人员信息：" + oldName + " --> " + newName);
		}
		String qs1 = "update SignCard set rvDutyerName=? where rvDutyerID=?";
		
		Object[] values = new Object[]{newName, clerk.getId()};
		int rows = executeBatchUpdate(new String[]{qs1}, values);
		
		//清理缓存
		if((rows) > 0 && getCache() != null){
			getCache().clear();
			if(log.isDebugEnabled()){
				log.debug("清理了SignCard缓存");
			}
		}
		if(log.isDebugEnabled()){
			log.debug("共更新SignCard的相关人员信息 ‘" + rows + "’个"); 
		}
	}

	public void orgBeforeDelete(Org org) {
		doBeforeDeleteOrg(org, "rvDutyerOrgId");
	}
}
