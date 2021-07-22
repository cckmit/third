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
			log.debug("��λ��Ϣ�����˱仯��ͬ������SignCard�е���ص�λ��" + oldAbbr + " --> " + newAbbr);
		}
		
		String qs1 = "update SignCard set rvDutyerOrgName=? where rvDutyerOrgId=?";
		
		Object[] values = new Object[]{newAbbr, org.getId()};
		int rows = executeBatchUpdate(new String[]{qs1}, values);
		
		//������
		if((rows) > 0 && getCache() != null){
			getCache().clear();
			if(log.isDebugEnabled()){
				log.debug("������SignCard����");
			}
		}
		if(log.isDebugEnabled()){
			log.debug("������SignCard����ص�λ��Ϣ ��" + rows + "����"); 
		}
		
	}

	public void clerkNameChange(Clerk clerk, String oldName, String newName) {
		if(log.isDebugEnabled()){
			log.debug("��Ա��Ϣ�����˱仯��ͬ������SignCard�������Ա��Ϣ��" + oldName + " --> " + newName);
		}
		String qs1 = "update SignCard set rvDutyerName=? where rvDutyerID=?";
		
		Object[] values = new Object[]{newName, clerk.getId()};
		int rows = executeBatchUpdate(new String[]{qs1}, values);
		
		//������
		if((rows) > 0 && getCache() != null){
			getCache().clear();
			if(log.isDebugEnabled()){
				log.debug("������SignCard����");
			}
		}
		if(log.isDebugEnabled()){
			log.debug("������SignCard�������Ա��Ϣ ��" + rows + "����"); 
		}
	}

	public void orgBeforeDelete(Org org) {
		doBeforeDeleteOrg(org, "rvDutyerOrgId");
	}
}
