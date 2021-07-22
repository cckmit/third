/*
 * $Id: DatumHibernateDao.java 6303 2013-08-15 01:19:45Z thh $
 * DatumHibernateDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.bean.core.Attachment;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Datum;
import cn.redflagsoft.base.bean.DatumAttachment;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.dao.DatumDao;
import cn.redflagsoft.base.event2.ClerkNameChangeListener;
import cn.redflagsoft.base.event2.OrgAbbrChangeListener;
import cn.redflagsoft.base.event2.OrgBeforeDeleteListener;

/**
 * @author Administrator
 *
 */
public class DatumHibernateDao extends AbstractBaseHibernateDao<Datum,Long> implements DatumDao,OrgAbbrChangeListener, ClerkNameChangeListener, OrgBeforeDeleteListener {

	private static final Log log = LogFactory.getLog(DatumHibernateDao.class);
	@SuppressWarnings("unchecked")
	public Datum getDatum(String name) {
		List<Datum> list = getHibernateTemplate().find("from Datum where name=?", name);
		if(list!=null && !list.isEmpty()){
			if(list.size() > 1){
				log.warn("Get Datum by name(" + name + ") 返回结果不唯一：" + list.size());
			}
			return list.get(0);
		}
		return null;
	}

	public List<DatumAttachment> findDatumAttachments(ResultFilter rf) {
//		String qs = "select new cn.redflagsoft.base.bean.DatumAttachment(a,b)" +
//				" from Datum a, Attachment b where a.content=b.id";
//		return getQuerySupport().find(qs, rf);
		if(rf == null){
			rf = new ResultFilter();
		}
		if(rf.getOrder() == null){
			rf.setOrder(Order.desc("a.publishTime"));
		}
		
		String qs = "select a,b from Datum a, Attachment b, ObjectData o where a.content=b.id and o.datumID=a.id";
		List<Object[]> list = getQuerySupport().find(qs, rf);
		List<DatumAttachment> result = new ArrayList<DatumAttachment>();
		for(Object[] objects: list){
			result.add(new DatumAttachment((Datum)objects[0], (Attachment)objects[1]));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<DatumAttachment> findDatumAttachmentByType(Long objectId, int type) {
		String qs = "select new cn.redflagsoft.base.bean.DatumAttachment(a,b)" +
		" from Datum a, Attachment b where a.content=b.id and a.objectID=? and a.type=? order by a.publishTime desc";
		return getHibernateTemplate().find(qs, new Object[]{objectId, type});
	}
	
	@SuppressWarnings("unchecked")
	public List<DatumAttachment> findDatumAttachmentByTypeAndCategoryID(Long objectId, int type,Long categoryID) {
//		String qs = "select new cn.redflagsoft.base.bean.DatumAttachment(a,b)" +
//		" from Datum a, Attachment b where a.content=b.id and a.objectID=? and a.type=? and a.categoryID=?";
//		return getHibernateTemplate().find(qs, new Object[]{objectId, type,categoryID});
		String qs = "select a, b from Datum a, Attachment b where a.content=b.id " +
				"and a.objectID=? and a.type=? and a.categoryID=? order by b.storeTime desc, a.creationTime desc";
		List<Object[]> list = getHibernateTemplate().find(qs, new Object[]{objectId, type, categoryID});
		List<DatumAttachment> result = new ArrayList<DatumAttachment>();
		for(Object[] objects: list){
			result.add(new DatumAttachment((Datum)objects[0], (Attachment)objects[1]));
		}
		return result;
	}

	public void clerkNameChange(Clerk clerk, String oldName, String newName) {
		if(log.isDebugEnabled()){
			log.debug("人员信息发生了变化，同步更新资料文件中的相关人员：" + oldName + " --> " + newName);
		}
		String qs1 = "update Datum set publisherName=? where publisherId=?";
		Object[] values = new Object[]{newName, clerk.getId()};
		int rows1 = getQuerySupport().executeUpdate(qs1, values);
		
		//清理缓存
		if((rows1) > 0 && getCache() != null){
			getCache().clear();
			if(log.isDebugEnabled()){
				log.debug("清理了资料文件缓存");
			}
		}
		if(log.isDebugEnabled()){
			log.debug("共更新资料发布者(publisherName) ‘" + rows1 + "’个"); 
		}
	}

	public void orgAbbrChange(Org org, String oldAbbr, String newAbbr) {
		if(log.isDebugEnabled()){
			log.debug("单位信息发生了变化，同步更新资料文件中的相关单位：" + oldAbbr + " --> " + newAbbr);
		}
		
		String qs1 = "update Datum set promulgatorAbbr=? where promulgator=?";
		
		String qs2 = "update Datum set publishOrgName=? where publishOrgId=?";
		
		Object[] values = new Object[]{newAbbr, org.getId()};
		int rows1 = getQuerySupport().executeUpdate(qs1, values);
		int rows2 = getQuerySupport().executeUpdate(qs2, values);
		
		//清理缓存
		if((rows1 + rows2) > 0 && isEnableCache() && getCache() != null){
			getCache().clear();
			if(log.isDebugEnabled()){
				log.debug("清理了资料文件缓存");
			}
		}
		
		if(log.isDebugEnabled()){
			log.debug("共更新上传单位(promulgatorAbbr) ‘" + rows1 
					+ "’ 个，发布单位(publishOrgName) ‘" 
					+ rows2 + "’ 个");
		}
	}

//	public int removeDatumByTypeAndCategoryID(Long objectID, int type, Long categoryID) {
//		String qs = "select a.content from Datum a where a.objectID=? and a.type=? and a.categoryID=?";
//		List<String> list = getHibernateTemplate().find(qs, new Object[]{objectID, type, categoryID});
//		if(list.size() > 0){
//			List<Long> ids = new ArrayList<Long>();
//			for(String c : list){
//				if(StringUtils.isNotBlank(c)){
//					ids.add(Long.parseLong(c));
//				}
//			}
//			if(ids.size() > 0){
//				Application.getContext().getAttachmentManager()
//					.removeAttachments(ids.toArray(new Long[ids.size()]));
//			}
//		}
//		qs = "delete from Datum a where a.objectID=? and a.type=? and a.categoryID=?";
//		getQuerySupport().executeUpdate(qs, new Object[]{objectID, type, categoryID});
//		return list.size();
//	}
	
	
//	
//	public List<Datum> findDatums(long objectId, int objectType, Integer taskType, Integer workType){
//		String qs = "select d from Datum d, EntityObjectToWork e " +
//				" , Work w, WorkDef wd " +
//				" where d.workSN=e.workSN" +
//				" and w.sn=d.workSN and w.type=wd.workType and wd.taskType=?" + 
//				" and e.objectId=? and e.objectType=?" +
//				" and w.type=?" +
//				" order by d.displayOrder";
//
//		return null;
//	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.DatumDao#findRFSObjectTaskDatum(long, short)
	 */
	@SuppressWarnings("unchecked")
	public List<Datum> findRFSObjectTaskDatum(long objectID, int taskType) {
		String qs = "select distinct d from Datum d, TaskData td, Task t" +
				" where d.id=td.datumID and td.taskSN=t.sn" +
				" and t.refObjectId=? and t.type=? order by d.publishTime desc, d.creationTime desc";
		return getHibernateTemplate().find(qs, new Object[]{objectID, taskType});
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.DatumDao#findEntityObjectTaskDatum(long, short, short)
	 */
	@SuppressWarnings("unchecked")
	public List<Datum> findEntityObjectTaskDatum(long objectID,	int objectType, int taskType) {
		String qs = "select distinct d from Datum d, TaskData td, EntityObjectToTask e, Task t" +
		" where d.id=td.datumID and td.taskSN=e.taskSN and t.sn=e.taskSN" +
		" and e.objectId=? and e.objectType=?" +
		" and t.type=? order by d.publishTime desc, d.creationTime desc";
		return getHibernateTemplate().find(qs, new Object[]{objectID, objectType, taskType});
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.DatumDao#findRFSObjectWorkDatum(long, short)
	 */
	@SuppressWarnings("unchecked")
	public List<Datum> findRFSObjectWorkDatum(long objectID, int workType) {
		String qs = "select distinct d from Datum d, Work w" +
			" where d.workSN=w.sn" +
			" and w.refObjectId=? and w.type=? order by d.publishTime desc, d.creationTime desc";
		return getHibernateTemplate().find(qs, new Object[]{objectID, workType});
		
		/**
		 * 如果tasktype和worktype是多对多关系时，可以关联task查询，语句类似
		 * 
		 * select d from Datum d, Work w, Task t
		 * where d.workSN=w.sn and w.taskSN=t.sn
		 * w.refObjectId=? 
		 * and t.type=? and w.type=?
		 */
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.DatumDao#findEntityObjectWorkDatum(long, short, short)
	 */
	@SuppressWarnings("unchecked")
	public List<Datum> findEntityObjectWorkDatum(long objectID,	int objectType, int workType) {
		String qs = "select distinct d from Datum d, Work w, EntityObjectToWork e" +
		" where d.workSN=w.sn and d.workSN=e.workSN" +
		" and e.objectId=? and e.objectType=?" +
		" and w.type=? order by d.publishTime desc, d.creationTime desc";
		return getHibernateTemplate().find(qs, new Object[]{objectID, objectType, workType});
		
		/**
		 * 如果tasktype和worktype是多对多关系时，可以关联task查询，语句类似
		 * 
		 * select d from Datum d, Work w, EntityObjectToWork e, Task t
		 * where d.workSN=w.sn and w.sn=e.workSN and w.taskSN=t.sn
		 * and e.objectId=? and e.objectType=? 
		 * and t.type=? and w.type=?
		 */
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.DatumDao#findTaskDatum(long, short, short)
	 */
	public List<Datum> findTaskDatum(long objectID, int objectType, int taskType) {
		if(objectType == 0){
			return findRFSObjectTaskDatum(objectID, taskType);
		}else{
			return findEntityObjectTaskDatum(objectID, objectType, taskType);
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.DatumDao#findWorkDatum(long, short, short)
	 */
	public List<Datum> findWorkDatum(long objectID, int objectType,int workType) {
		if(objectType == 0){
			return findRFSObjectWorkDatum(objectID, workType);
		}else{
			return findEntityObjectWorkDatum(objectID, objectType, workType);
		}
	}

	public void orgBeforeDelete(Org org) {
		doBeforeDeleteOrg(org, "promulgator", "publishOrgId");
	}
}
