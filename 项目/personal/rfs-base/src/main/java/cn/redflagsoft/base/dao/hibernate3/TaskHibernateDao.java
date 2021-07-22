package cn.redflagsoft.base.dao.hibernate3;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.springframework.orm.hibernate3.HibernateCallback;

import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.RFSObjectable;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.dao.TaskDao;
import cn.redflagsoft.base.event2.ObjectNameChangeListener;
import cn.redflagsoft.base.event2.ObjectSnChangeListener;

public class TaskHibernateDao extends AbstractBaseHibernateDao<Task, Long> 
		implements TaskDao,ObjectNameChangeListener,ObjectSnChangeListener {
	private static final Log log = LogFactory.getLog(TaskHibernateDao.class);

   @SuppressWarnings("unchecked")
    public List<Task> findTaskAllVisibilityByObjectID(Long objectID){  
        String hql = "select a from Task a, ObjectTask b where a.sn=b.taskSN and b.objectID=? order by a.beginTime ASC";
        return getHibernateTemplate().find(hql, new Object[]{objectID}); 
    }
   	
   	public List<Task> findTaskByObjectID(Long objectId) {	
		return findTaskByObjectIDandVisibility(objectId,Task.VISIBILITY_GENERAL);
	}
   
	@SuppressWarnings("unchecked")
	public List<Task> findTaskByObjectIDandVisibility(Long objectId,int visibility) {
		String hql = "select a from Task a, ObjectTask b where a.sn=b.taskSN and b.objectID=? and (a.visibility=? or a.visibility=0) order by a.beginTime ASC";
		return getHibernateTemplate().find(hql,new Object[] {objectId,visibility});
	}

	/**
	 * @deprecated using {@link #find(org.opoo.ndao.support.ResultFilter)}
	 */
	public List<Task> findTaskByDutyerID(Long dutyerID) {	
		return findTaskByDutyerIDandVisibility(dutyerID,Task.VISIBILITY_GENERAL);
	}
	

	/**
	 * @deprecated using {@link #find(org.opoo.ndao.support.ResultFilter)}
	 */
	@SuppressWarnings("unchecked")
	public List<Task> findTaskByDutyerIDandVisibility(Long dutyerID,int visibility) {
		String hql = "from Task where dutyerID=? and (visibility=? or visibility=0) and status<>? order by type ASC";
		return getHibernateTemplate().find(hql, new Object[]{dutyerID,visibility,Task.TASK_STATUS_TERMINATE});
	}
	

	/**
	 * ʹ�ñ����ӵķ�ʽ��ѯĳ���ŵ�ҵ�����ݡ�
	 * @deprecated δʵ�ֵĲ�ѯ
	 * @param dutyerID
	 * @return Object[]{Task, RFSObject}
	 */
	public List<Object[]> findTaskInfoByDuterID(Long dutyerID){
		String qs = "select a, b from Task a, RFSObject b, ObjectTask c where a.sn=c.taskSN and b.id=c.objectID" +
				" and a.dutyerID=? order by a.type asc";
		qs = "select a, b, d.term from Task a, RFSObject b, ObjectTask c, Glossary d where a.sn=c.taskSN and b.id=c.objectID and d.category=112 and d.code=a.type" +
				" and a.dutyerID=? order by a.type asc";
		System.out.println(qs);
		return null;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Task> findTaskByNodeSn(byte category, Long nodeSn) {
		String hql = "select a from Task a, BizTrackNodeInstance b where a.sn=b.bizSN and b.category=? and b.nodeSN=?";
		return getHibernateTemplate().find(hql, new Object[]{category, nodeSn});
	}
	
	public List<Task> findTasks(Long objectID, int taskType){	
		return findTaskByVisibility(objectID, taskType,Task.VISIBILITY_GENERAL);
	}


	@SuppressWarnings("unchecked")
	public List<Task> findTaskByVisibility(Long objectID, int taskType, int visibility){
		String hql = "select a from Task a, ObjectTask b where a.sn=b.taskSN and b.objectID=? and a.type=? and (a.visibility=? or a.visibility=0) order by a.beginTime ASC";
		return getHibernateTemplate().find(hql, new Object[]{objectID, taskType,visibility});
	}
	

	/**
	 * @deprecated using {@link #update(Task)}
	 */
	public void updateActiveWorkSN(Long sn, Long activeWorkSN) {
		String qs = "update Task set activeWorkSN=? where sn=?";
		getQuerySupport().executeUpdate(qs, new Object[]{activeWorkSN, sn});
	}
	
	/**
	 * @deprecated using {@link #find(org.opoo.ndao.support.ResultFilter)}
	 */
	public List<Task> findTaskByClerkID(Long dutyerID) {	
		return findTaskByClerkIDandVisibility(dutyerID,Task.VISIBILITY_GENERAL);
	}


	/**
	 * @deprecated using {@link #find(org.opoo.ndao.support.ResultFilter)}
	 */
	@SuppressWarnings("unchecked")
	public List<Task> findTaskByClerkIDandVisibility(Long dutyerID,int visibility) {
		String hql = "from Task where clerkID=? and (visibility=? or visibility=0) and status<? order by type ASC";
		return getHibernateTemplate().find(hql, new Object[]{dutyerID,visibility, Task.TASK_STATUS_TERMINATE});
	}
	
	public List<Object[]> getBizCountGroupByDutyerID() {
		String hql = "select dutyerID,count(*) as countF,sum(timeused) as value,sum(timeLimit) as timeLimit from Task group by dutyerID order by dutyerID Asc";
		return getQuerySupport().find(hql);
	}
	
	/**
	 * ��������task��clerkID,��δ����task��clerkName��
	 */
	public void updateTaskClerkIdByTaskIds(final List<Long> ids,final Long clerkID){
		final String qs = "update Task set clerkID=:clerkID where sn in(:ids)";
	    getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery(qs)
				.setLong("clerkID", clerkID)
				.setParameterList("ids", ids)
				.executeUpdate();
				return null;
			}
	    });
	}


	@SuppressWarnings("unchecked")
	public List<Task> findTaskByObjectIDandVisibilitys(Long objectId,int visibility) {
		String hql = "select a from Task a, ObjectTask b where a.sn=b.taskSN and b.objectID=? and (a.visibility=0 or a.visibility=? or a.visibility=?) order by a.beginTime ASC";
		return getHibernateTemplate().find(hql,new Object[] {objectId,Task.VISIBILITY_GENERAL,visibility});
	}
	

	@SuppressWarnings("unchecked")
	public List<Task> findTaskAllVisibility(Long objectID, int taskType){	
		String hql = "select a from Task a, ObjectTask b where a.sn=b.taskSN and b.objectID=? and a.type=? order by a.beginTime ASC";
		return getHibernateTemplate().find(hql, new Object[]{objectID, taskType}); 
	}

	public void taskInvalid(RFSObject rfsObject) {
		final int objectType = rfsObject.getObjectType();
		final Long objectId = rfsObject.getId();
		
		final String sql= "insert into task_invalid (select * from task t where t.ref_obj_type=? and t.ref_obj_id=?)";
		
		int execute = (Integer)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query q = session.createSQLQuery(sql).setInteger(0, objectType).setLong(1, objectId);
				return q.executeUpdate();
			}
		});
		
		SimpleExpression eq = Restrictions.eq("refObjectId", objectId);
		SimpleExpression eq2 = Restrictions.eq("refObjectType", objectType);
		int rows2 = remove(Restrictions.logic(eq).and(eq2));
		
		String s = String.format("����(type=%s, id=%s)���ϣ�ת��Task����%s�task_invalid��ɾ��%s�", objectType, objectId, execute, rows2);
		log.info(s);
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.event2.ObjectNameChangeListener#objectNameChange(cn.redflagsoft.base.bean.RFSObjectable, java.lang.String, java.lang.String)
	 */
	public void objectNameChange(RFSObjectable object, String oldValue,	String newValue) {
		log.debug("�������Ʒ����˱仯��ͬ������TASK��" + oldValue + " --> " + newValue);
		
		String sql = "update TASK set REF_OBJ_NAME=? where REF_OBJ_ID=? and REF_OBJ_TYPE=?";
		int rows = executeSQLUpdate(sql, newValue, object.getId(), object.getObjectType());
		
		if(rows > 0 && getCache() != null){
			getCache().clear();
			log.debug("������TASK ����");
		}
		log.debug("������TASK����ض������� ��" + rows + "����");
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.event2.ObjectNameChangeListener#objectNameChange(cn.redflagsoft.base.bean.RFSObjectable, java.lang.String, java.lang.String)
	 */
	public void objectSnChange(RFSObjectable object, String oldValue,	String newValue) {
		log.debug("�������Ʒ����˱仯��ͬ������TASK��" + oldValue + " --> " + newValue);
		
		String sql = "update TASK set S4=? where REF_OBJ_ID=? and REF_OBJ_TYPE=?";
		int rows = executeSQLUpdate(sql, newValue, object.getId(), object.getObjectType());
		
		if(rows > 0 && getCache() != null){
			getCache().clear();
			log.debug("������TASK ����");
		}
		log.debug("������TASK����ض������� ��" + rows + "����");
	}

}
