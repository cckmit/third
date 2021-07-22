/*
 * $Id ReceiverHibernateDao.java$
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.opoo.apps.bean.core.Attachment;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.orm.hibernate3.HibernateCallback;

import cn.redflagsoft.base.bean.Objects;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;
import cn.redflagsoft.base.dao.ObjectsDao;

/**
 * 
 * @author ymq
 *
 */
public class ObjectsHibernateDao extends AbstractBaseHibernateDao<Objects, Long> implements ObjectsDao{
	private static final Log log = LogFactory.getLog(ObjectsHibernateDao.class);

	/**
	 * 根据FstObjectId查找sndObjectID。可能查询出多条，只取一条。
	 */
	public Long getRelatedObjectID(Long objectID, int type) {
		Criterion c = Restrictions.logic(Restrictions.eq("fstObject",objectID )).and(Restrictions.eq("type", type));
		Objects objects = get(c);
		return objects == null ? null : objects.getSndObject();
		
		//也可以使用一下查询语句
		//String ql = "select sndObject from Objects where fstObject=? and type=?";
	}
	
	@SuppressWarnings("unchecked")
	public List<Attachment> findAttachments3(int taskType, int workType, final Long objectID){
		String qs1 = "select distinct a.sndObject from Objects a, Task b where a.fstObject=b.sn and a.type=? and b.type=?";
		List<Long> list1 = getHibernateTemplate().find(qs1, new Object[]{Objects.TYPE_任务与附件之间关系, taskType});
		
		String qs2 = "select distinct a.sndObject from Objects a, Work b where a.fstObject=b.sn and a.type=? and b.type=?";
		List<Long> list2 = getHibernateTemplate().find(qs2, new Object[]{Objects.TYPE_工作与附件之间关系, workType});
		
		final List<Long> ids = new ArrayList<Long>();
		ids.addAll(list1);
		ids.addAll(list2);
		
		if(ids.isEmpty()){
			return Collections.emptyList();
		}
		
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String qs3 = "select att from Attachment att, Objects a where a.sndObject=att.id " +
						"and a.type=:x and a.fstObject=:y and a.sndObject in (:z)";
				return session.createQuery(qs3)
					.setInteger("x", Objects.TYPE_业务对象与附件之间关系)
					.setLong("y", objectID)
					.setParameterList("z", ids)
					.list();
			}});

		
		
		
//		String qs = "select att from Attachment att where id in(" +
//				"select a1.sndObject from Objects a1, Task b1 where a1.fstObject=b1.sn and a1.type=? and b1.type=?" +
//				" union select a2.sndObject from Objects a2, Work b2 where a2.fstObject=b2.sn and a2.type=? and b2.type=?" +
//				" union select a3.sndObject from Objects a3 where a3.type=? and a3.fstObject=?)";
//		return getHibernateTemplate().find(qs, new Object[]{Objects.TYPE_任务与附件之间关系, taskType,
//				 Objects.TYPE_工作与附件之间关系, workType,
//				 Objects.TYPE_业务对象与附件之间关系, objectID});
	}
	
	@SuppressWarnings("unchecked")
	public List<Attachment> findAttachments2(int taskType, int workType, final Long objectID){
		//使用这种方法需要保证Task的refObjectId存储的是object的id
		String qs1 = "select distinct a.sndObject from Objects a, Task b where a.fstObject=b.sn and a.type=? and b.type=? and b.refObjectId=?";
		List<Long> list1 = getHibernateTemplate().find(qs1, new Object[]{Objects.TYPE_任务与附件之间关系, taskType, objectID});
		
		String qs2 = "select distinct a.sndObject from Objects a, Work b where a.fstObject=b.sn and a.type=? and b.type=? and b.refObjectId=?";
		List<Long> list2 = getHibernateTemplate().find(qs2, new Object[]{Objects.TYPE_工作与附件之间关系, workType, objectID});
		
		final List<Long> ids = new ArrayList<Long>();
		ids.addAll(list1);
		ids.addAll(list2);
		
		if(ids.isEmpty()){
			return Collections.emptyList();
		}
		
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String qs3 = "select att from Attachment att, Objects a where a.sndObject=att.id " +
						"and a.type=:x and a.fstObject=:y and a.sndObject in (:z)";
				return session.createQuery(qs3)
					.setInteger("x", Objects.TYPE_业务对象与附件之间关系)
					.setLong("y", objectID)
					.setParameterList("z", ids)
					.list();
			}});
	}

	
	@SuppressWarnings("unchecked")
	public List<Attachment> findAttachments(int taskType, int workType, final Long objectID){
		//使用这种方法需要保证Task的refObjectId存储的是object的id
		String qs = "select a from Attachment a, Objects b where a.id=b.sndObject and b.type=? and b.fstObject=? and a.id in " +
				"(select distinct a.sndObject from Objects a, Task b where a.fstObject=b.sn and a.type=? and b.type=? and b.refObjectId=?)" +
				" and a.id in " +
				"(select distinct a.sndObject from Objects a, Work b where a.fstObject=b.sn and a.type=? and b.type=? and b.refObjectId=?)";
		Object[] values = new Object[]{Objects.TYPE_业务对象与附件之间关系, objectID, 
				Objects.TYPE_任务与附件之间关系, taskType, objectID,
				Objects.TYPE_工作与附件之间关系, workType, objectID};
		
		return getHibernateTemplate().find(qs, values);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.ObjectsDao#findAttachments(short, short, long, short)
	 */
	@SuppressWarnings("unchecked")
	public List<Attachment> findAttachments(int taskType, int workType,	long objectId, int objectType) {
		List<Object> values = new ArrayList<Object>();
		String hql = "select distinct a from Attachment a, Objects ta, Objects wa, Task t, Work w";
		if(objectType != 0){
			hql += ", EntityObjectToTask et, EntityObjectToWork ew";
		}
		hql += " where a.id=ta.sndObject and ta.type=? and ta.fstObject=t.sn";
		values.add(Objects.TYPE_任务与附件之间关系);
		if(taskType != 0){
			hql += " and t.type=?"; 
			values.add(taskType);
		}
		
		hql += " and a.id=wa.sndObject and wa.type=? and wa.fstObject=w.sn";
		values.add(Objects.TYPE_工作与附件之间关系);
		if(workType != 0){
			hql += " and w.type=?";
			values.add(workType);
		}
		
		if(objectType == 0){
			hql += " and t.refObjectId=w.refObjectId and t.refObjectId=?";
			values.add(objectId);
		}else{
			hql += " and et.taskSN=t.sn and ew.workSN=w.sn and et.objectId=ew.objectId" +
					" and et.objectType=ew.objectType and et.objectId=? and et.objectType=?";
			values.add(objectId);
			values.add(objectType);
		}
		if(log.isDebugEnabled()){
			log.debug("findAttachments 原始查询HQL：" + hql);
			log.debug("findAttachments 参数：" + values);
		}
		return getHibernateTemplate().find(hql, values.toArray());
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.ObjectsDao#saveAttachments(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, java.util.List)
	 */
	public void saveAttachments(Task task, Work work, List<Long> attachmentIds) {
		long taskSN = task.getSn();
		long workSN = work.getSn();
		for(long fileId: attachmentIds){
			saveOrUpdate(taskSN, fileId, Objects.TYPE_任务与附件之间关系, "任务与附件之间关系");
			saveOrUpdate(workSN, fileId, Objects.TYPE_工作与附件之间关系, "工作与附件之间关系");
		}
	}
	
	/**
	 * 保存关联对象。
	 * 如果关联关系已经存在，则直接返回，否则创建并返回。
	 * 
	 * @param fstObject 第一个对象ID
	 * @param sndObject 第二个对象ID
	 * @param type 关系类型
	 * @param remark 备注
	 * @return Objects 保存或者已经保存的关联对象
	 */
	public Objects saveOrUpdate(long fstObject, long sndObject, int type, String remark){
		Logic logic = Restrictions
				.logic(Restrictions.eq("fstObject", fstObject))
				.and(Restrictions.eq("sndObject", sndObject))
				.and(Restrictions.eq("type", type));
		List<Objects> list = find(new ResultFilter(logic, null));
		if(list != null && !list.isEmpty()){
			return list.iterator().next();
		}else{
			Objects a = new Objects();
			a.setFstObject(fstObject);
			a.setSndObject(sndObject);
			a.setStatus((byte) 1);
			a.setType(type);
			a.setRemark(remark);
			return save(a);
		}
	}


	public void removeByAttachment(Long id) {
		String hql = "delete from Objects o where o.sndObject=? and (o.type=? or o.type=?)";
		getQuerySupport().executeUpdate(hql, new Object[]{id, Objects.TYPE_任务与附件之间关系, Objects.TYPE_工作与附件之间关系});
	}
}
