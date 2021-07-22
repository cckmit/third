package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import cn.redflagsoft.base.bean.Process;
import cn.redflagsoft.base.dao.ProcessDao;

public class ProcessHibernateDao extends AbstractBaseHibernateDao<Process, Long> implements ProcessDao{
	@SuppressWarnings("unchecked")
	public List<Process> getProcessByTaskSNAndType(Long taskSN,int type){
		String qs = "select a from Process a,Work w where a.workSN=w.sn and a.taskSN=? and w.type=?";
		return getHibernateTemplate().find(qs, new Object[]{taskSN,type});
	}
}
