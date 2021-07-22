package cn.redflagsoft.base.dao.hibernate3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.SignCardCheck;
import cn.redflagsoft.base.dao.SignCardCheckDao;
import cn.redflagsoft.base.event2.ClerkNameChangeListener;

public class SignCardCheckHibernateDao extends AbstractBaseHibernateDao<SignCardCheck,Long> implements 
		SignCardCheckDao ,ClerkNameChangeListener {
	
	private static final Log log = LogFactory.getLog(SignCardCheckHibernateDao.class);
	
	public void clerkNameChange(Clerk clerk, String oldName, String newName) {
		if(log.isDebugEnabled()){
			log.debug("人员信息发生了变化，同步更新SignCardCheck的相关人员信息：" + oldName + " --> " + newName);
		}
		String qs1 = "update SignCardCheck set checkDutyerName=? where checkDutyerId=?";
		
		Object[] values = new Object[]{newName, clerk.getId()};
		int rows = executeBatchUpdate(new String[]{qs1}, values);
		
		//清理缓存
		if((rows) > 0 && getCache() != null){
			getCache().clear();
			if(log.isDebugEnabled()){
				log.debug("清理了SignCardCheck缓存");
			}
		}
		if(log.isDebugEnabled()){
			log.debug("共更新SignCardCheck的相关人员信息 ‘" + rows + "’个"); 
		}
	}
}
