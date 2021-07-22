/*
 * $Id: DatumServiceImplV2.java 5397 2012-03-06 02:46:25Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.bean.core.Attachment;
import org.opoo.apps.lifecycle.Application;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Datum;
import cn.redflagsoft.base.bean.DatumCategory;
import cn.redflagsoft.base.bean.DatumObject;
import cn.redflagsoft.base.bean.DatumOrigin;
import cn.redflagsoft.base.bean.MatterDatum;
import cn.redflagsoft.base.bean.ObjectData;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.RFSObjectable;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.TaskData;
import cn.redflagsoft.base.bean.Work;
import cn.redflagsoft.base.codegenerator.CodeGeneratorProvider;
import cn.redflagsoft.base.dao.DatumCategoryDao;
import cn.redflagsoft.base.dao.DatumDao;
import cn.redflagsoft.base.dao.DatumOriginDao;
import cn.redflagsoft.base.dao.MatterDatumDao;
import cn.redflagsoft.base.dao.ObjectDataDao;
import cn.redflagsoft.base.dao.TaskDataDao;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.DatumService;
import cn.redflagsoft.base.util.EqualsUtils;
import cn.redflagsoft.base.vo.DatumInfoProvider;
import cn.redflagsoft.base.vo.DatumVO;
import cn.redflagsoft.base.vo.DatumVOList;
import cn.redflagsoft.base.vo.MatterVO;

/**
 * Datum Service.
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 2.0.2
 * @version 2
 */
public class DatumServiceImplV2 implements DatumService {
	private static final Log log = LogFactory.getLog(DatumServiceImplV2.class);
	public static final byte DATUM_CATEGORY = 0;
	private DatumCategoryDao datumCategoryDao;
	private CodeGeneratorProvider codeGeneratorProvider;
	private DatumDao datumDao;
	private ObjectDataDao objectDataDao;
	private TaskDataDao taskDataDao;
	private MatterDatumDao matterDatumDao;
	private DatumOriginDao datumOriginDao;

	/**
	 * @param datumOriginDao the datumOriginDao to set
	 */
	public void setDatumOriginDao(DatumOriginDao datumOriginDao) {
		this.datumOriginDao = datumOriginDao;
	}

	/**
	 * @param datumCategoryDao the datumCategoryDao to set
	 */
	public void setDatumCategoryDao(DatumCategoryDao datumCategoryDao) {
		this.datumCategoryDao = datumCategoryDao;
	}

	/**
	 * @param codeGeneratorProvider the codeGeneratorProvider to set
	 */
	public void setCodeGeneratorProvider(CodeGeneratorProvider codeGeneratorProvider) {
		this.codeGeneratorProvider = codeGeneratorProvider;
	}

	/**
	 * @param datumDao the datumDao to set
	 */
	public void setDatumDao(DatumDao datumDao) {
		this.datumDao = datumDao;
	}

	/**
	 * @param objectDataDao the objectDataDao to set
	 */
	public void setObjectDataDao(ObjectDataDao objectDataDao) {
		this.objectDataDao = objectDataDao;
	}

	/**
	 * @param taskDataDao the taskDataDao to set
	 */
	public void setTaskDataDao(TaskDataDao taskDataDao) {
		this.taskDataDao = taskDataDao;
	}

	/**
	 * @param matterDatumDao the matterDatumDao to set
	 */
	public void setMatterDatumDao(MatterDatumDao matterDatumDao) {
		this.matterDatumDao = matterDatumDao;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DatumService#saveDatum(cn.redflagsoft.base.bean.Datum)
	 */
	public Datum saveDatum(Datum datum) {
		Assert.notNull(datum.getWorkSN(), "Datum相关的Work不能为空");
		
		SimpleExpression c1 = Restrictions.eq("workSN", datum.getWorkSN());
		SimpleExpression c2 = Restrictions.eq("categoryID", datum.getCategoryID());
		SimpleExpression c3 = Restrictions.eq("status", Datum.STATUS_当前资料);
		Logic logic = Restrictions.logic(c1).and(c2).and(c3);
		Datum oldDatum = datumDao.get(logic);
		if(oldDatum != null){
			if(EqualsUtils.equals(oldDatum.getContent(), datum.getContent())){
				log.info("相同的资料已经存在，忽略插入新资料：" + datum.toJSONString());
				oldDatum.setFlag(1);
				return oldDatum;
			}else{
//				datum.setStatus(Datum.STATUS_历史资料);
				updateToHistory(oldDatum);
			}
		}
		

		Clerk clerk = UserClerkHolder.getClerk();
		if(datum.getPromulgator() == null || datum.getPromulgatorAbbr() == null){
			datum.setPromulgator(clerk.getEntityID());
			datum.setPromulgatorAbbr(clerk.getEntityName());
		}
		if(datum.getPublisherId() == null || datum.getPublisherName() == null){
			datum.setPublisherId(clerk.getId());
			datum.setPublisherName(clerk.getName());
		}
		if(datum.getPublishOrgId() == null || datum.getPublishOrgName() == null){
			datum.setPublishOrgId(clerk.getEntityID());
			datum.setPublishOrgName(clerk.getEntityName());
		}
		
		if(datum.getCategoryID() == null){
			datum.setCategoryID(0L);
		}
		if(datum.getCode() == null){
			//String code = codeGeneratorProvider.generateCode(Datum.class, DATUM_CATEGORY, null);
			datum.setCode("--");
		}
		
		datum = datumDao.save(datum);
		return datum;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DatumService#getDatum(java.lang.Long)
	 */
	public Datum getDatum(Long id) {
		return datumDao.get(id);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DatumService#deleteDatum(cn.redflagsoft.base.bean.Datum)
	 */
	public int deleteDatum(Datum datum) {
		if(datum.getContent() != null){
			try {
				long attachmentId = Long.parseLong(datum.getContent());
				Application.getContext().getAttachmentManager().removeAttachment(attachmentId);
				if(log.isDebugEnabled()){
					log.debug("删除资料 " + datum.getName() + ":" + datum.getId() 
							+ "相关的附件：" + attachmentId);
				}
			} catch (Exception e) {
				// ignore
			}
		}
		int objectDataRows = objectDataDao.remove(Restrictions.eq("datumID", datum.getId()));
		int taskDataRows = taskDataDao.remove(Restrictions.eq("datumID", datum.getId()));
		if(log.isDebugEnabled()){
			log.debug(String.format("同步删除资料相关的ObjectData %s 条，TaskData %s 条。", objectDataRows, taskDataRows));
		}
		return datumDao.delete(datum);
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DatumService#updateToHistory(cn.redflagsoft.base.bean.Datum)
	 */
	public Datum updateToHistory(Datum datum) {
		datum.setStatus(Datum.STATUS_历史资料);
		return datumDao.update(datum);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DatumService#processDatum(byte, cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work, short, cn.redflagsoft.base.bean.RFSObject, cn.redflagsoft.base.vo.MatterVO, cn.redflagsoft.base.vo.DatumVOList, cn.redflagsoft.base.bean.Clerk, cn.redflagsoft.base.bean.RFSEntityObject[])
	 */
	@Transactional(propagation=Propagation.MANDATORY)
	public List<Datum> processDatum(byte category, Task task, Work work,
			int processType, MatterVO matters,
			DatumVOList datumList, Clerk clerk, RFSObject object, RFSEntityObject... objects) {
		if(datumList == null || datumList.isEmpty()){
			throw new IllegalArgumentException("datumList is required.");
		}
		List<Datum> result = new ArrayList<Datum>();
		//Long[] matterIds = matters.getMatterIds();
		for(DatumVO datumVO: datumList){
			if(datumVO == null){
				continue;
			}
			//判断DatumCategory
			if(datumVO.getDatumCategoryId() == null) {
				log.debug("datum category id is null, skip process");
				continue;
			}
			DatumCategory cat = datumCategoryDao.get(datumVO.getDatumCategoryId());
			if(cat == null) {
				log.warn("处理datumCategoryId为 " + datumVO.getDatumCategoryId() + " 时没有找到DatumCategory");
				continue;
			}
			
			if(datumVO.getAttachmentId() == null || datumVO.getAttachmentId().longValue() == 0L){
				if(AppsGlobals.getProperty("datum.skipSavingIfFileIdIsNull", true)){
					continue;
				}
			}
			
			//判断资料是否已经存在
			Datum datum = saveOrUpdateDatum(datumVO, cat, task, work, clerk, datumList);
			
			if((task != null && task.getSn() != null) || (object != null && object.getId() != null) || objects.length > 0){
				processMattersDatum(category, task, work, processType, matters, datumVO, clerk, object, objects, cat, datum);
			}
			
			result.add(datum);
		}
		
		return result;
	}

	/**
	 * @param category
	 * @param task
	 * @param work
	 * @param processType
	 * @param matters
	 * @param datumVO
	 * @param clerk
	 * @param object
	 * @param objects
	 * @param cat
	 * @param datum
	 */
	private void processMattersDatum(byte category, Task task, Work work,
			int processType, MatterVO matters, DatumVO datumVO, Clerk clerk,
			RFSObject object, RFSEntityObject[] objects, DatumCategory cat,
			Datum datum) {
		
		Set<RFSEntityObject> objectsSet = new HashSet<RFSEntityObject>();
		if(object != null){
			objectsSet.add(object);
		}
		for(RFSEntityObject o: objects){
			objectsSet.add(o);
		}
		
		int index = 0;
		//遍历 Matter
		for(Long matterId : matters.getMatterIds()) {
			List<MatterDatum> list = matterDatumDao.findMatterDatum(category, task.getType(), work.getType(), processType, matterId, cat.getId());
			if(list == null || list.isEmpty()) {
				String s = String.format("没有matterId=%s, taskType=%s, workType=%s, category=%s(%s)的MatterDatum，" +
						" 不处理Task及Object相关的Datum。", 
						matterId, task.getType(), work.getType(), cat.getName(), cat.getId());
				log.warn(s);
				continue;
			}
			
			for(MatterDatum md : list) {
				//处理 TaskData
				if(task != null && task.getSn() != null) {
					procecessTaskData(md, task, work, processType, matters, datumVO, clerk, object, objects, cat, datum, index);
				}
				
				//处理ObjectData
				if(objectsSet.size() > 0){
					procecessObjectData(md, task, work, processType, matters, datumVO, clerk, objectsSet, cat, datum, index);
				}
			}
			index++;
		}
	}

	/**
	 * @param md 资料配置
	 * @param task
	 * @param work
	 * @param processType
	 * @param matters
	 * @param datumVO
	 * @param clerk
	 * @param set
	 * @param cat
	 * @param datum
	 * @param index
	 */
	private void procecessObjectData(MatterDatum md, Task task, Work work,
			int processType, MatterVO matters, DatumVO datumVO, Clerk clerk,
			Set<RFSEntityObject> objectsSet, DatumCategory cat, Datum datum, int index) {
		for(RFSEntityObject object: objectsSet){
			switch(md.getObjectAction()) {
			//新增：将原来的设为历史，新增新的
			case MatterDatum.ACTION_ADDITION :
				createObjectData(task, work, datum, object, index);
				break;
			case MatterDatum.ACTION_DELETE_REPLACE :
			case MatterDatum.ACTION_REPLACE :
				deleteObjectDataByObjectAndDatumCategory(object, cat.getId());
				createObjectData(task, work, datum, object, index);
				break;
			case MatterDatum.ACTION_DELETE :
				deleteObjectDataByObjectAndDatumCategory(object, cat.getId());
				break;
			}
		}
	}

	/**
	 * @param id
	 * @param objectType
	 * @param id2
	 */
	private void deleteObjectDataByObjectAndDatumCategory(RFSEntityObject object, Long datumCategoryID) {
		Integer objectType = object.getObjectType();
		if(object instanceof RFSObjectable){
			objectType = 0;
		}
		Logic logic = Restrictions.logic(Restrictions.eq("objectID", object.getId()))
			.and(Restrictions.eq("objectType", objectType))
			.and(Restrictions.eq("datumCategoryID", datumCategoryID));
		objectDataDao.remove(logic);
	}

	private ObjectData createObjectData(Task task, Work work, Datum datum, RFSEntityObject object, int index) {
		ObjectData objectData = new ObjectData();
		objectData.setDatumID(datum.getId());
		objectData.setDatumCategoryID(datum.getCategoryID());
		objectData.setCreateTask(task.getSn() != null ? task.getSn().intValue() : 0);
		objectData.setDisplayOrder((byte) index);
		objectData.setObjectID(object.getId());
		objectData.setStatus(Datum.STATUS_当前资料);
		objectData.setType(datum.getType());
		if(object instanceof RFSObjectable){
			objectData.setObjectType(0);
		}else{
			objectData.setObjectType(object.getObjectType());
		}
		return objectDataDao.save(objectData);
	}

	/**
	 * @param md
	 * @param task
	 * @param work
	 * @param processType
	 * @param matters
	 * @param datumVO
	 * @param clerk
	 * @param object
	 * @param objects
	 * @param cat
	 * @param datum
	 * @param index
	 */
	private void procecessTaskData(MatterDatum md, Task task, Work work,
			int processType, MatterVO matters, DatumVO datumVO, Clerk clerk,
			RFSObject object, RFSEntityObject[] objects, DatumCategory cat,
			Datum datum, int index) {

		switch (md.getTaskAction()) {
		case MatterDatum.ACTION_ADDITION:
			createTaskData(task, work, datum, index);
			break;
		case MatterDatum.ACTION_DELETE_REPLACE:
		case MatterDatum.ACTION_REPLACE:
			deleteTaskDataByTaskAndDatumCategory(task.getSn(), cat.getId());
			createTaskData(task, work, datum, index);
			break;
		case MatterDatum.ACTION_DELETE:
			deleteTaskDataByTaskAndDatumCategory(task.getSn(), cat.getId());
			break;
		}
	}

	private void deleteTaskDataByTaskAndDatumCategory(Long taskSN, Long datumCategoryID) {
		SimpleExpression c1 = Restrictions.eq("taskSN", taskSN);
		SimpleExpression c2 = Restrictions.eq("datumCategoryID", datumCategoryID);
		Logic logic = Restrictions.logic(c1).and(c2);
		taskDataDao.remove(logic);
	}

	private TaskData createTaskData(Task task, Work work, Datum datum, int index) {
		TaskData taskData = new TaskData();
		taskData.setDatumID(datum.getId());
		taskData.setDatumCategoryID(datum.getCategoryID());
		taskData.setDisplayOrder((byte) index);
		taskData.setCreateWork(work.getSn() != null ? work.getSn().intValue() : 0);
		taskData.setTaskSN(task.getSn());
		taskData.setStatus(Datum.STATUS_当前资料);
		taskData.setType(datum.getType());
		return taskDataDao.save(taskData);
	}

	/**
	 * @param datumVO
	 * @param cat
	 * @param work 
	 * @param task 
	 * @return
	 */
	private Datum saveOrUpdateDatum(DatumVO datumVO, DatumCategory cat, Task task, Work work, Clerk clerk, DatumInfoProvider datumInfo) {
		SimpleExpression c1 = Restrictions.eq("workSN", work.getSn());
		SimpleExpression c2 = Restrictions.eq("categoryID", cat.getId());
		SimpleExpression c3 = Restrictions.eq("status", Datum.STATUS_当前资料);
		Logic logic = Restrictions.logic(c1).and(c2).and(c3);
		Datum datum = datumDao.get(logic);
		if(datum != null){
			String content = datum.getContent();
			Long attachmentId = datumVO.getAttachmentId();
			String fileId = attachmentId == null ? null : attachmentId.toString();
			if(EqualsUtils.equals(fileId, content)){
				return datum;
			}else{
//				datum.setStatus(Datum.STATUS_历史资料);
				updateToHistory(datum);
			}
		}
		
		datum = new Datum();
		datum.setAbbr(cat.getAbbr());
		datum.setBrief(cat.getAbbr());
//		String code = codeGeneratorProvider.generateCode(Datum.class, DATUM_CATEGORY, cat.getType());
//		datum.setCode(code);
		//可能没有上传电子档
		datum.setContent(datumVO.getAttachmentId() != null ? datumVO.getAttachmentId().toString() : null);//附件系统
		datum.setName(cat.getName());
		//datum.setObjectID(object.getId());
		datum.setWorkSN(work.getSn());
		datum.setOrigin((byte) 0);
		datum.setFormat((byte) 0);
		//datum.setType(cat.getId());//很重要，关联	
		datum.setType(Datum.TYPE_DATUM);
		datum.setStatus(Datum.STATUS_当前资料);
		datum.setCategoryID(cat.getId());
		datum.setPromulgator(clerk.getEntityID());
		datum.setPromulgatorAbbr(clerk.getEntityName());
		datum.setFileNo(datumVO.getFileNo());
		datum.setPublisherId(clerk.getId());
		datum.setPublisherName(clerk.getName());
		datum.setPublishOrgId(clerk.getEntityID());
		datum.setPublishOrgName(clerk.getEntityName());
		//发布时间
		datum.setPublishTime(work.getBeginTime());
		//发文单位和时间
		if(datumInfo != null){
			datum.setDispatchOrgId(datumInfo.getDatumDispatchOrgId());
			datum.setDispatchOrgName(datumInfo.getDatumDispatchOrgName());
			datum.setDispatchTime(datumInfo.getDatumDispatchTime());
		}
		
		DatumObject datumObject = new DatumObject(datum);
		String code = codeGeneratorProvider.generateCode(datumObject);
		datum.setCode(code);
		
		//保存资料信息
		datum = datumDao.save(datum);

		//有文件才有原文信息
		if(datumVO.getAttachmentId() != null){
			Attachment attachment = Application.getContext().getAttachmentManager().getAttachment(datumVO.getAttachmentId());

			//保存资料原文信息
			DatumOrigin origin = new DatumOrigin();
			origin.setDatumId(datum.getId());
			origin.setFileName(attachment.getFileName());//?
			origin.setOperateTime(work.getBeginTime());
			origin.setPublishTime(work.getBeginTime());
			origin.setFileType(attachment.getFileType());//?
			datumOriginDao.save(origin);
		}
		
		return datum;
	}
	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DatumService#findDatum(java.lang.Long)
	 */
	public List<Datum> findDatum(Long objectId) {
		return findDatum(objectId,  0);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DatumService#findDatum(cn.redflagsoft.base.bean.RFSEntityObject)
	 */
	public List<Datum> findDatum(RFSEntityObject object) {
		int objectType = object.getObjectType();
		if(object instanceof RFSObjectable){
			objectType = 0;
		}
		return findDatum(object.getId(), objectType);
	}
	
	public List<Datum> findDatum(long objectId, int objectType){
		return objectDataDao.findDatum(objectId, objectType);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DatumService#findDatumByTaskSN(long)
	 */
	public List<Datum> findDatumByTaskSN(long taskSN) {
		return taskDataDao.findDatum(taskSN);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DatumService#findDatumByWorkSN(long)
	 */
	public List<Datum> findDatumByWorkSN(long workSN) {
		SimpleExpression c = Restrictions.eq("workSN", workSN);
		ResultFilter filter = new ResultFilter(c, Order.asc("orderNo"));
		return datumDao.find(filter);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DatumService#findTaskDatum(cn.redflagsoft.base.bean.RFSEntityObject, short)
	 */
	public List<Datum> findTaskDatum(RFSEntityObject object, int taskType) {
		if(object instanceof RFSObjectable){
			return datumDao.findTaskDatum(object.getId(), 0, taskType);
		}else{
			return datumDao.findTaskDatum(object.getId(), object.getObjectType(), taskType);
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DatumService#findWorkDatum(cn.redflagsoft.base.bean.RFSEntityObject, short)
	 */
	public List<Datum> findWorkDatum(RFSEntityObject object, int workType) {
		if(object instanceof RFSObjectable){
			return datumDao.findWorkDatum(object.getId(), 0, workType);
		}else{
			return datumDao.findWorkDatum(object.getId(), object.getObjectType(), workType);
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DatumService#saveDatum(cn.redflagsoft.base.bean.Datum, cn.redflagsoft.base.bean.RFSEntityObject, cn.redflagsoft.base.bean.Task, cn.redflagsoft.base.bean.Work)
	 */
	public Datum saveDatum(Datum datum, RFSEntityObject object, Task task, Work work) {
		Datum datum2 = saveDatum(datum);
		//flag 1 表示该对象是已经存在的，没有新建datum对象
		if(datum2.getFlag() != 1){
			createObjectData(task, work, datum2, object, 0);
			createTaskData(task, work, datum2, 0);
		}
		return datum2;
	}
}
