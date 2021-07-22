package cn.redflagsoft.base.dao.hibernate3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.SignCardAccept;
import cn.redflagsoft.base.dao.SignCardAcceptDao;
import cn.redflagsoft.base.event2.ClerkNameChangeListener;

public class SignCardAcceptHibernateDao extends AbstractBaseHibernateDao<SignCardAccept,Long> implements 
		SignCardAcceptDao, ClerkNameChangeListener{
	
	private static final Log log = LogFactory.getLog(SignCardAcceptHibernateDao.class);
	
	public void clerkNameChange(Clerk clerk, String oldName, String newName) {
		if(log.isDebugEnabled()){
			log.debug("��Ա��Ϣ�����˱仯��ͬ������SignCardAccept�������Ա��Ϣ��" + oldName + " --> " + newName);
		}
		String qs1 = "update SignCardAccept set acceptDutyerName=? where acceptDutyerId=?";
		
		Object[] values = new Object[]{newName, clerk.getId()};
		int rows = executeBatchUpdate(new String[]{qs1}, values);
		
		//������
		if((rows) > 0 && getCache() != null){
			getCache().clear();
			if(log.isDebugEnabled()){
				log.debug("������SignCardAccept����");
			}
		}
		if(log.isDebugEnabled()){
			log.debug("������SignCardAccept�������Ա��Ϣ ��" + rows + "����"); 
		}
	}
}	
