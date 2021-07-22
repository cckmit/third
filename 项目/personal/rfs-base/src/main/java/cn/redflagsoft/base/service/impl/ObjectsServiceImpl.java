/*
 * $Id: ObjectsServiceImpl.java 6128 2012-11-23 02:03:54Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.opoo.apps.annotation.Queryable;
import org.opoo.apps.bean.core.Attachment;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.util.Assert;

import cn.redflagsoft.base.bean.Objects;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;
import cn.redflagsoft.base.dao.ObjectsDao;
import cn.redflagsoft.base.service.ObjectsService;

/**
 * @author mwx
 *
 */
public class ObjectsServiceImpl implements ObjectsService{
	
	private ObjectsDao objectsDao;
	
	public void setObjectsDao(ObjectsDao objectsDao) {
		this.objectsDao = objectsDao;
	}

	public Objects createObjects(Objects objects) {
		return objectsDao.save(objects);
	}

	public int deleteObjects(Objects objects) {
		return objectsDao.delete(objects);
	}

	public Objects updateObjects(Objects objects) {
		return objectsDao.update(objects);
	}

	public Long getRelatedObjectID(Long objectID, int type) {
		return objectsDao.getRelatedObjectID(objectID, type);
	}

	/**
	 * 创建对象之间的关联关系。
	 */
	public Objects createObjects(Long firstObjectId, Long secondObjectId, int type, String remark) {
		ResultFilter filter = ResultFilter.createPageableResultFilter(0, 1);
		filter.setOrder(Order.desc("creationTime"));
		filter.setCriterion(Restrictions.logic(Restrictions.eq("fstObject", firstObjectId))
				.and(Restrictions.eq("sndObject", secondObjectId))
				.and(Restrictions.eq("type", type)));
		List<Objects> list = objectsDao.find(filter);
		if(list.size() > 0){
			Objects a = list.iterator().next();
			a.setRemark(remark);
			return objectsDao.update(a);
		}else{
			Objects a = new Objects();
			a.setFstObject(firstObjectId);
			a.setSndObject(secondObjectId);
			a.setStatus((byte) 1);
			a.setType(type);
			a.setRemark(remark);
			return objectsDao.save(a);
		}
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectsService#removeByFirstObjectID(java.lang.Long, short)
	 */
	public void removeByFirstObjectID(Long firstObjectID, int type) {
		Logic logic = Restrictions.logic(Restrictions.eq("fstObject", firstObjectID))
			.and(Restrictions.eq("type", type));
		objectsDao.remove(logic);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectsService#getObjectsByFirstObjectID(java.lang.Long, short)
	 */
	public Objects getObjectsByFirstObjectID(Long firstObjectID, int type) {
		ResultFilter filter = ResultFilter.createPageableResultFilter(0, 1);
		filter.setOrder(Order.desc("creationTime"));
		filter.setCriterion(Restrictions.logic(Restrictions.eq("fstObject", firstObjectID))
				.and(Restrictions.eq("type", type)));
		List<Objects> list = objectsDao.find(filter);
		if(list.size() > 0){
			return list.iterator().next();
		}
		return null;
	}

	@Queryable(argNames={"taskType", "workType", "objectID",  "objectType"})
	public List<Attachment> findAttachments(int taskType, int workType, Long objectID, Integer objectType) {
		Assert.notNull(objectID, "对象ID不能为空");
		long objectId2 = objectID.longValue();
		int objectType2 = 0;
		if(objectType != null){
			objectType2 = objectType;
		}
		return objectsDao.findAttachments(taskType, workType, objectId2, objectType2);
	}
	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectsService#findAttachments(long, java.lang.Short)
	 */
	@Queryable(argNames={"objectID",  "objectType"})
	public List<Attachment> findObjectAttachments(long objectID, Integer objectType) {
		return findAttachments(0, 0, objectID, objectType);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectsService#saveAttachments(cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, java.util.List)
	 */
	public void saveAttachments(Task task, Work work, List<Long> attachmentIds) {
		objectsDao.saveAttachments(task, work, attachmentIds);
	}

	public void deleteObjectsByAttachment(Long id) {
		objectsDao.removeByAttachment(id);
	}

	public List<Objects> findObjects(ResultFilter filter) {
		return objectsDao.find(filter);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectsService#saveOrUpdateObjects(long, long, int, java.lang.String)
	 */
	public Objects saveOrUpdateObjects(long fstObject, long sndObject,
			int type, String remark) {
		return objectsDao.saveOrUpdate(fstObject, sndObject, type, remark);
	}
}
