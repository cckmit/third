package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import cn.redflagsoft.base.bean.EventMsg;
import cn.redflagsoft.base.dao.EventMsgDao;

public class EventMsgHibernateDao  extends AbstractBaseHibernateDao<EventMsg, Long> implements EventMsgDao{
	@SuppressWarnings("unchecked")
	public List<EventMsg> findEventMsgCfg(int objectType,Long objectID,int jobType,int taskType,int workType,int processType,Long eventType){
			String hql = "select a from EventMsg a where a.objectType=? and (a.objectID=? or a.objectID=?) and a.jobType=? and a.taskType=? and (a.workType=? or a.workType=0) and a.processType=? and a.eventType=? and a.status=?";
			return getHibernateTemplate().find(hql, new Object[]{objectType,objectID,0L,jobType,taskType,workType,processType,eventType,(byte)1});
	  }
	
	 @SuppressWarnings("unchecked")
	public List<EventMsg> findEventMsgCfg(int objectType,Long objectID,int taskType,int workType,Long eventType){
			String hql = "select a from EventMsg a where a.objectType=? and (a.objectID=? or a.objectID=?) and a.taskType=? and (a.workType=? or a.workType=0) and a.eventType=? and a.status=?";
			return getHibernateTemplate().find(hql, new Object[]{objectType,objectID,0L,taskType,workType,eventType,(byte)1});
	  }
	  
	  @SuppressWarnings("unchecked")
	public List<EventMsg> findEventMsgCfg(int objectType,Long objectID,Long eventType){
		  	String hql = "select a from EventMsg a where a.objectType=? and (a.objectID=? or a.objectID=?) and a.eventType=? and a.status=?";
			return getHibernateTemplate().find(hql, new Object[]{objectType,objectID,0L,eventType,(byte)1});
	  }
}
