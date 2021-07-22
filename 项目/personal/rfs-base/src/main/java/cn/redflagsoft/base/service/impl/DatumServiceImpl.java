/*
 * $Id: DatumServiceImpl.java 6012 2012-09-11 09:37:01Z lcj $
 * JobServiceImpl.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.lifecycle.Application;
import org.opoo.ndao.NonUniqueResultException;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Datum;
import cn.redflagsoft.base.bean.DatumAttachment;
import cn.redflagsoft.base.bean.DatumCategory;
import cn.redflagsoft.base.bean.DatumObject;
import cn.redflagsoft.base.bean.MatterDatum;
import cn.redflagsoft.base.bean.ObjectData;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.TaskData;
import cn.redflagsoft.base.bean.Work;
import cn.redflagsoft.base.codegenerator.CodeGeneratorProvider;
import cn.redflagsoft.base.dao.DatumCategoryDao;
import cn.redflagsoft.base.dao.DatumDao;
import cn.redflagsoft.base.dao.MatterDatumDao;
import cn.redflagsoft.base.dao.ObjectDataDao;
import cn.redflagsoft.base.dao.TaskDataDao;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.vo.DatumVO;
import cn.redflagsoft.base.vo.DatumVOList;
import cn.redflagsoft.base.vo.MatterVO;

/**
 * 
 * @author ymq
 * @deprecated
 */
public class DatumServiceImpl /*implements DatumService*/ {
	private static final Log log = LogFactory.getLog(DatumServiceImpl.class);
	public static final byte DATUM_CATEGORY = 0;
	private DatumCategoryDao datumCategoryDao;
	private CodeGeneratorProvider codeGeneratorProvider;
	private DatumDao datumDao;
	private ObjectDataDao objectDataDao;
	private TaskDataDao taskDataDao;
	private MatterDatumDao matterDatumDao;
	
	public void setTaskDataDao(TaskDataDao taskDataDao) {
		this.taskDataDao = taskDataDao;
	}

	public void setObjectDataDao(ObjectDataDao objectDataDao) {
		this.objectDataDao = objectDataDao;
	}

	public void setDatumDao(DatumDao datumDao) {
		this.datumDao = datumDao;
	}

	public void setCodeGeneratorProvider(CodeGeneratorProvider codeGeneratorProvider) {
		this.codeGeneratorProvider = codeGeneratorProvider;
	}

	public void setDatumCategoryDao(DatumCategoryDao datumCategoryDao) {
		this.datumCategoryDao = datumCategoryDao;
	}
	
	public void setMatterDatumDao(MatterDatumDao matterDatumDao) {
		this.matterDatumDao = matterDatumDao;
	}

	public List<Datum> processDatum_backup(byte category, Task task, Work work, int processType, RFSObject object, MatterVO matters, DatumVOList datumList, Clerk clerk) {
		/*
		if(datumList != null){
			//Long[] datumCategoryIds = datumVO.getDatumIds();
			//Long[] attachmentIds = datumVO.getAttachmentIds();
			Long[] matterIds = matters.getMatterIds();
			if(datumCategoryIds == null || (datumCategoryIds.length != attachmentIds.length)) {
				log.error("DatumVO����datumIds������attachmentIds���Ȳ�һ��,����Ϊ��!");
				throw new RuntimeException("DatumVO����datumIds������attachmentIds���Ȳ�һ��,����Ϊ��!");
			}
			for(int i=0; i<datumCategoryIds.length; i++) {
				//���� Datum
				Long datumId = handlerDatum(datumCategoryIds[i], attachmentIds[i], object.getId());
				//���� Matter
				handlerMatter(category, task, work, object, processType, datumId, datumCategoryIds[i], matterIds, i);
			}
		}else{
			log.warn("datumVOΪ�գ�processDatumδ����...");
		}*/
		List<Datum> ld = new ArrayList<Datum>();
		if(datumList == null){
			return null;
		}
		Long[] matterIds = matters.getMatterIds();
		
		for(int i = 0 ; i < datumList.size() ; i++){
			DatumVO datumVO = datumList.get(i);
			if(datumVO.getDatumCategoryId() == null || datumVO.getAttachmentId() == null) {
				continue;
			}
			Datum datum = saveDatum(datumVO, clerk, object == null ? null : object.getId());
			ld.add(datum);
			if(datum == null){
				throw new RuntimeException("�������ϳ���");
			}
			//��������֮һ��Ϊ�ռ�ִ��
			if((task != null && task.getSn() != null) || (object != null && object.getId() != null)){
				saveDatumForMatters(category, task, work, object, processType, datum, matterIds, i);
			}
		}
		return ld;
		
	}
	
	
	
	

	public Datum saveDatum(Datum datum){
//		Assert.notNull(datum.getObjectID(), "Daum��objectId����Ϊ��");

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
		
		
		ResultFilter filter = new ResultFilter();
		Logic logic = Restrictions.logic(Restrictions.eq("objectID", datum.getWorkSN()));
		logic = logic.and(Restrictions.eq("type", datum.getType()));
		logic = logic.and(Restrictions.eq("content", datum.getContent()));
		logic = logic.and(Restrictions.eq("categoryID", datum.getCategoryID()));
		filter.setCriterion(logic);
		int count = datumDao.getCount(filter);
		if(count > 0){
			log.info("��ͬ�������Ѿ����ڣ����Բ��������ϣ�" + datum.toJSONString());
			return null;
		}
		
		datum = datumDao.save(datum);
		
		ObjectData od = new ObjectData();
		od.setCreateTask(0);
		od.setDatumCategoryID(datum.getCategoryID());
		od.setDatumID(datum.getId());
//		od.setObjectID(datum.getObjectID());;
		od.setStatus((byte) 0);
		od.setType(datum.getType());
		objectDataDao.save(od);
		return datum;
	}
	
	/**
	 * �������ϡ�
	 * 
	 * @param datumVO
	 * @param clerk
	 * @param objectID
	 * @return
	 */
	private Datum saveDatum(DatumVO datumVO, Clerk clerk, Long objectID){
		DatumCategory cat = datumCategoryDao.get(datumVO.getDatumCategoryId());
		if(cat == null) {
			log.warn("����datumCategoryIdΪ " + datumVO.getDatumCategoryId() + " ʱû���ҵ�DatumCategory");
			return null;
		}
		Datum datum = new Datum();
		datum.setAbbr(cat.getAbbr());
		datum.setBrief(cat.getAbbr());
		//����û���ϴ����ӵ�
		datum.setContent(datumVO.getAttachmentId() != null ? datumVO.getAttachmentId().toString() : null);//����ϵͳ
		datum.setName(cat.getName());
//		datum.setObjectID(objectID);
		datum.setOrigin((byte) 0);
		datum.setFormat((byte) 0);
		//datum.setType(cat.getId());//����Ҫ������	
		datum.setType( 0);
		datum.setCategoryID(cat.getId());
		datum.setPromulgator(clerk.getEntityID());
		datum.setPromulgatorAbbr(clerk.getEntityName());
		datum.setFileNo(datumVO.getFileNo());
		datum.setPublisherId(clerk.getId());
		datum.setPublisherName(clerk.getName());
		datum.setPublishOrgId(clerk.getEntityID());
		datum.setPublishOrgName(clerk.getEntityName());
		
		DatumObject object = new DatumObject(datum);
		String code = codeGeneratorProvider.generateCode(object);
		datum.setCode(code);
		
		return datumDao.save(datum);
		
		
//		ObjectData od = new ObjectData();
//		od.setCreateTask(0);
//		od.setDatumCategoryID(datum.getCategoryID());
//		od.setDatumID(datum.getId());
//		od.setObjectID(datum.getObjectID());;
//		od.setStatus((byte) 0);
//		od.setType(datum.getType());
//		return datum;
	}
	
	
	
	
	private void saveDatumForMatters(byte category, Task task, Work work, RFSObject superObject,
			int processType, Datum datum, Long[] matterIds, int index) {
		List<MatterDatum> list = null;
		//���� Matter
		for(Long matterId : matterIds) {
			//��ȡ MatterDatum
			//list = matterDatumDao.findMatterDatum(category, task.getType(), work.getType(), processType, matterId);
			list = matterDatumDao.findMatterDatum(category, task.getType(), work.getType(), processType, matterId,datum.getCategoryID());
			if(list == null || list.isEmpty()) {
				log.warn("����matterIdΪ "+matterId+" ʱû���ҵ�MatterDatum");
				continue;
			}
			for(MatterDatum element : list) {
				//���� TaskData
				if(task != null && task.getSn() != null) {
					handlerTaskData(element.getTaskAction(), datum, task.getSn(), work.getSn(), index);
				}
				//���� ObjectData
				if(superObject != null && superObject.getId() != null) {
					handlerObjectData(element.getObjectAction(), datum, task.getSn(), superObject.getId(), index);
				}
			}
		}
	}
	/**
	 * ���� TaskData
	 *  
	 * @param operation
	 * @param datumId
	 * @param datumCategoryId
	 * @param taskSN
	 * @param workSN
	 * @param index
	 */
	private void handlerTaskData(byte operation, Datum datum, Long taskSN, Long workSN, int index) {
		TaskData taskData = null;
		List<TaskData> list = null;
		switch(operation) {
		case MatterDatum.ACTION_ADDITION :
			taskData = new TaskData();
			taskData.setDatumID(datum.getId());
			taskData.setDatumCategoryID(datum.getCategoryID());
			taskData.setDisplayOrder((byte) index);
			taskData.setCreateWork(workSN == null ? null : workSN.intValue());
			taskData.setTaskSN(taskSN);
			taskData.setStatus((byte) 0);
			taskData.setType( 0);
			taskDataDao.save(taskData);
			break;
		case MatterDatum.ACTION_DELETE_REPLACE :
		case MatterDatum.ACTION_REPLACE :
			list = taskDataDao.findTaskDataByTaskSN(taskSN, datum.getId(), datum.getCategoryID());
			if(list == null || list.isEmpty()) return;			
			taskData = list.get(0);
			//�жϲ����Ƿ��Ӧ[ɾ���滻]
			if(operation == MatterDatum.ACTION_DELETE_REPLACE) {
				//ɾ��
				taskDataDao.delete(taskData);
				taskData.setDatumID(datum.getId());
				taskData.setDatumCategoryID(datum.getCategoryID());
				taskData.setDisplayOrder((byte) index);
				taskData.setCreateWork(workSN == null ? null : workSN.intValue());
				taskData.setTaskSN(taskSN);
				taskData.setStatus((byte) 0);
				taskData.setType( 0);
			}
			taskData.setDatumID(datum.getId());
			taskDataDao.update(taskData);
			break;
		case MatterDatum.ACTION_DELETE :
			list = taskDataDao.findTaskDataByTaskSN(taskSN, datum.getId(), datum.getCategoryID());
			if(list == null || list.isEmpty()) return;
			taskDataDao.delete(list.get(0));
			break;
		}
	}
	
	/**
	 * ���� ObjectData
	 * 
	 * @param operation
	 * @param datumId
	 * @param datumCategoryId
	 * @param taskSN
	 * @param objectId
	 * @param index
	 */
	private void handlerObjectData(byte operation, Datum datum, Long taskSN, Long objectId, int index) {
		ObjectData objectData = null;
		List<ObjectData> list = null;
		switch(operation) {
		case MatterDatum.ACTION_ADDITION :
			objectData = new ObjectData();
			objectData.setDatumID(datum.getId());
			objectData.setDatumCategoryID(datum.getCategoryID());
			objectData.setCreateTask(taskSN == null ? 0 : taskSN.intValue());
			objectData.setDisplayOrder((byte) index);
			objectData.setObjectID(objectId);
			objectData.setStatus((byte) 0);
			objectData.setType( 0);
			objectDataDao.save(objectData);
			break;
		case MatterDatum.ACTION_DELETE_REPLACE :
		case MatterDatum.ACTION_REPLACE :
			list = objectDataDao.findObjectDataByObjectId(objectId, datum.getId(), datum.getCategoryID());
			if(list == null || list.isEmpty()) return;
			objectData = list.get(0);
			//�жϲ����Ƿ��Ӧ[ɾ���滻]
			if(operation == MatterDatum.ACTION_DELETE_REPLACE) {
				//ɾ�� 
				objectDataDao.delete(objectData);
				//�ؽ�
				objectData = new ObjectData();
				objectData.setDatumCategoryID(datum.getCategoryID());
				objectData.setCreateTask(taskSN == null ? 0 : taskSN.intValue());
				objectData.setDisplayOrder((byte) index);
				objectData.setObjectID(objectId);
				objectData.setStatus((byte) 0);
				objectData.setType( 0);			
			}
			objectData.setDatumID(datum.getId());
			objectDataDao.saveOrUpdate(objectData);
			break;
		case MatterDatum.ACTION_DELETE :
			list = objectDataDao.findObjectDataByObjectId(objectId, datum.getId(), datum.getCategoryID());
			if(list == null || list.isEmpty()) return;
			objectDataDao.delete(list.get(0));
			break;
		}
	}
	
	
	
	
	/**
	 * ���� Matter
	 * 
	 * @param category
	 * @param task
	 * @param work
	 * @param superObject
	 * @param processType
	 * @param datumId
	 * @param datumCategoryId
	 * @param matterIds
	 * @param index
	 */
	protected void handlerMatter(byte category, Task task, Work work, RFSObject superObject,
			int processType, Long datumId, Long datumCategoryId, Long[] matterIds, int index) {
		List<MatterDatum> list = null;
		//���� Matter
		for(Long matterId : matterIds) {
			//��ȡ MatterDatum
			list = matterDatumDao.findMatterDatum(category, task.getType(), work.getType(), processType, matterId);
			if(list == null || list.isEmpty()) {
				log.warn("����matterIdΪ "+matterId+" ʱû���ҵ�MatterDatum");
				continue;
			}
			for(MatterDatum element : list) {
				//���� TaskData
				if(task != null && task.getSn() != null) {
					handlerTaskData(element.getTaskAction(), datumId, datumCategoryId, task.getSn(), work.getSn(), index);
				}
				//���� ObjectData
				if(superObject != null && superObject.getId() != null) {
					handlerObjectData(element.getObjectAction(), datumId, datumCategoryId, task.getSn(), superObject.getId(), index);
				}
			}
		}
	}
	
	/**
	 * ���� Datum
	 * 
	 * @param datumCategoryId
	 * @param attachmentId
	 * @param objectId
	 * @return Long
	 */
	protected Long handlerDatum(Long datumCategoryId, Long attachmentId, Long objectId) {
		DatumCategory cat = datumCategoryDao.get(datumCategoryId);
		if(cat == null) {
			log.warn("����datumCategoryIdΪ "+datumCategoryId+" ʱû���ҵ�DatumCategory");
			return null;
		}
		Datum datum = new Datum();
		datum.setAbbr(cat.getAbbr());
		datum.setBrief(cat.getAbbr());
		//String code = codeGeneratorProvider.generateCode(Datum.class, DATUM_CATEGORY, cat.getType());
		//datum.setCode(code);
		datum.setContent(attachmentId.toString());//����ϵͳ
		datum.setName(cat.getName());
//		datum.setObjectID(objectId);
		datum.setOrigin((byte) 0);
		datum.setFormat((byte) 0);
		//datum.setType(cat.getId());//����Ҫ������	
		
		DatumObject object = new DatumObject(datum);
		String code = codeGeneratorProvider.generateCode(object);
		datum.setCode(code);
		datumDao.save(datum);
		return datum.getId();
	}
	
	/**
	 * ���� TaskData
	 *  
	 * @param operation
	 * @param datumId
	 * @param datumCategoryId
	 * @param taskSN
	 * @param workSN
	 * @param index
	 */
	private void handlerTaskData(byte operation, Long datumId, Long datumCategoryId, Long taskSN, Long workSN, int index) {
		TaskData taskData = null;
		List<TaskData> list = null;
		switch(operation) {
		case MatterDatum.ACTION_ADDITION :
			taskData = new TaskData();
			taskData.setDatumID(datumId);
			taskData.setDatumCategoryID(datumCategoryId);
			taskData.setDisplayOrder((byte) index);
			taskData.setCreateWork(workSN == null ? null : workSN.intValue());
			taskData.setTaskSN(taskSN);
			taskData.setStatus((byte) 0);
			taskData.setType( 0);
			taskDataDao.save(taskData);
			break;
		case MatterDatum.ACTION_DELETE_REPLACE :
		case MatterDatum.ACTION_REPLACE :
			list = taskDataDao.findTaskDataByTaskSN(taskSN, datumId, datumCategoryId);
			if(list == null || list.isEmpty()) return;			
			taskData = list.get(0);
			//�жϲ����Ƿ��Ӧ[ɾ���滻]
			if(operation == MatterDatum.ACTION_DELETE_REPLACE) {
				//ɾ��
				taskDataDao.delete(taskData);
				taskData.setDatumID(datumId);
				taskData.setDatumCategoryID(datumCategoryId);
				taskData.setDisplayOrder((byte) index);
				taskData.setCreateWork(workSN == null ? null : workSN.intValue());
				taskData.setTaskSN(taskSN);
				taskData.setStatus((byte) 0);
				taskData.setType( 0);
			}
			taskData.setDatumID(datumId);
			taskDataDao.update(taskData);
			break;
		case MatterDatum.ACTION_DELETE :
			list = taskDataDao.findTaskDataByTaskSN(taskSN, datumId, datumCategoryId);
			if(list == null || list.isEmpty()) return;
			taskDataDao.delete(list.get(0));
			break;
		}
	}
	
	/**
	 * ���� ObjectData
	 * 
	 * @param operation
	 * @param datumId
	 * @param datumCategoryId
	 * @param taskSN
	 * @param objectId
	 * @param index
	 */
	private void handlerObjectData(byte operation, Long datumId, Long datumCategoryId, Long taskSN, Long objectId, int index) {
		ObjectData objectData = null;
		List<ObjectData> list = null;
		switch(operation) {
		case MatterDatum.ACTION_ADDITION :
			objectData = new ObjectData();
			objectData.setDatumID(datumId);
			objectData.setDatumCategoryID(datumCategoryId);
			objectData.setCreateTask(taskSN == null ? 0 : taskSN.intValue());
			objectData.setDisplayOrder((byte) index);
			objectData.setObjectID(objectId);
			objectData.setStatus((byte) 0);
			objectData.setType( 0);
			objectDataDao.save(objectData);
			break;
		case MatterDatum.ACTION_DELETE_REPLACE :
		case MatterDatum.ACTION_REPLACE :
			list = objectDataDao.findObjectDataByObjectId(objectId, datumId, datumCategoryId);
			if(list == null || list.isEmpty()) return;
			objectData = list.get(0);
			//�жϲ����Ƿ��Ӧ[ɾ���滻]
			if(operation == MatterDatum.ACTION_DELETE_REPLACE) {
				//ɾ�� 
				objectDataDao.delete(objectData);
				//�ؽ�
				objectData = new ObjectData();
				objectData.setDatumCategoryID(datumCategoryId);
				objectData.setCreateTask(taskSN == null ? 0 : taskSN.intValue());
				objectData.setDisplayOrder((byte) index);
				objectData.setObjectID(objectId);
				objectData.setStatus((byte) 0);
				objectData.setType( 0);			
			}
			objectData.setDatumID(datumId);
			objectDataDao.saveOrUpdate(objectData);
			break;
		case MatterDatum.ACTION_DELETE :
			list = objectDataDao.findObjectDataByObjectId(objectId, datumId, datumCategoryId);
			if(list == null || list.isEmpty()) return;
			objectDataDao.delete(list.get(0));
			break;
		}
	}
	
	public List<Datum> findDatum(Long objectId) {
		ResultFilter resultFilter = ResultFilter.createEmptyResultFilter();
		resultFilter.setCriterion(Restrictions.eq("objectID", objectId));
		List<Datum> list = datumDao.find(resultFilter);
		if(list == null || list.isEmpty()) {	
			log.warn("û�в�ѯ������,���ؿռ���!����û���ҵ�project����superobject");
		}
		return list;
	}

	public Datum getDatum(Long id) {
		return datumDao.get(id);
	}

	/**
	 * ɾ�����ϼ�������صĸ�����
	 */
	public int deleteDatum(Datum datum) {
		if(datum.getContent() != null){
			try {
				long attachmentId = Long.parseLong(datum.getContent());
				Application.getContext().getAttachmentManager().removeAttachment(attachmentId);
				if(log.isDebugEnabled()){
					log.debug("ɾ������" + datum.getName() + ":" + datum.getId() 
							+ "��صĸ�����" + attachmentId);
				}
			} catch (Exception e) {
				// ignore
			}
		}
		return datumDao.delete(datum);
	}
	
	/**
	 * 
	 * @param objectId
	 * @param categoryId
	 * @return
	 */
	public Datum getCurrentDatum(Long objectId, Long categoryId){
		ResultFilter resultFilter = ResultFilter.createEmptyResultFilter();
		Logic logic = Restrictions.logic(Restrictions.eq("objectID", objectId))
			.and(Restrictions.eq("categoryID", categoryId))
			.and(Restrictions.eq("type", Datum.TYPE_DATUM))
			.and(Restrictions.eq("status", Datum.STATUS_��ǰ����));
		resultFilter.setCriterion(logic);
		List<Datum> list = datumDao.find(resultFilter);
		int size = list.size();
		if(size > 1){
			throw new NonUniqueResultException(size);
		}
		if(size == 1){
			return list.iterator().next();
		}
		return null;
	}

	/**
	 * ��ѯָ���������͵����ϵ����°汾��
	 * 
	 * <p>ע�⣬����Ϊ0�����ϲ���ͨ������������ã����򱨴�
	 */
	public DatumAttachment getCurrentDatumAttachment(Long objectId, Long categoryId) {
		ResultFilter resultFilter = ResultFilter.createEmptyResultFilter();
		Logic logic = Restrictions.logic(Restrictions.eq("a.objectID", objectId))
			.and(Restrictions.eq("a.categoryID", categoryId))
			.and(Restrictions.eq("a.type", Datum.TYPE_DATUM))
			.and(Restrictions.eq("a.status", Datum.STATUS_��ǰ����));
		resultFilter.setCriterion(logic);
		List<DatumAttachment> list = datumDao.findDatumAttachments(resultFilter);
		int size = list.size();
		if(size > 1){
			throw new NonUniqueResultException(size);
		}
		if(size == 1){
			return list.iterator().next();
		}
		return null;
	}
	
	
	
	public ObjectData getCurrentObjectData(Long objectId, Long datumId){
		ResultFilter resultFilter = ResultFilter.createEmptyResultFilter();
		Logic logic = Restrictions.logic(Restrictions.eq("objectID", objectId))
//			.and(Restrictions.eq("datumCategoryID", categoryId))
			.and(Restrictions.eq("datumID",datumId))
			.and(Restrictions.eq("status", Datum.STATUS_��ǰ����));
		resultFilter.setCriterion(logic);
		List<ObjectData> list = objectDataDao.find(resultFilter);
		int size = list.size();
		if(size > 1){
			throw new NonUniqueResultException(size);
		}
		if(size == 1){
			return list.iterator().next();
		}
		return null;
	}
	
	/**
	 * ��ѯָ��task�����ϵĵ�ǰ���Ϲ�����ϵ��
	 * 
	 * @param taskSN
	 * @param datumId
	 * @return
	 */
	public TaskData getCurrentTaskData(Long taskSN, Long datumId){
		ResultFilter resultFilter = ResultFilter.createEmptyResultFilter();
		Logic logic = Restrictions.logic(Restrictions.eq("taskSN", taskSN))
			//.and(Restrictions.eq("datumCategoryID", categoryId))
			.and(Restrictions.eq("datumID",datumId))
			.and(Restrictions.eq("status", Datum.STATUS_��ǰ����));
		resultFilter.setCriterion(logic);
		List<TaskData> list = taskDataDao.find(resultFilter);
		int size = list.size();
		if(size > 1){
			throw new NonUniqueResultException(size);
		}
		if(size == 1){
			return list.iterator().next();
		}
		return null;
	}
	
	/**
	 * ��ָ����¼����Ϊ��ʷ���ϡ�
	 * 
	 * @param datum
	 * @return
	 */
	public Datum updateToHistory(Datum datum){
		datum.setStatus(Datum.STATUS_��ʷ����);
		return datumDao.update(datum);
	}
	
	/**
	 * Ϊ�ƶ����󴴽���ǰ���ϡ�ִ��ǰӦ�ý�ԭ���ĵ�ǰ���ϸ���Ϊ��ʷ���ϣ�����
	 * ����ֶ����ǰ���ϣ����²�ѯ����
	 * @param object
	 * @param datumVO
	 * @param cat
	 * @param clerk
	 * @return
	 */
	public Datum createCurrentDatum(RFSObject object, DatumVO datumVO, DatumCategory cat, Clerk clerk){
		Datum datum = new Datum();
		datum.setAbbr(cat.getAbbr());
		datum.setBrief(cat.getAbbr());
		
		//����û���ϴ����ӵ�
		datum.setContent(datumVO.getAttachmentId() != null ? datumVO.getAttachmentId().toString() : null);//����ϵͳ
		datum.setName(cat.getName());
//		datum.setObjectID(object.getId());
		datum.setOrigin((byte) 0);
		datum.setFormat((byte) 0);
		//datum.setType(cat.getId());//����Ҫ������	
		datum.setType(Datum.TYPE_DATUM);
		datum.setStatus(Datum.STATUS_��ǰ����);
		datum.setCategoryID(cat.getId());
		datum.setPromulgator(clerk.getEntityID());
		datum.setPromulgatorAbbr(clerk.getEntityName());
		datum.setFileNo(datumVO.getFileNo());
		datum.setPublisherId(clerk.getId());
		datum.setPublisherName(clerk.getName());
		datum.setPublishOrgId(clerk.getEntityID());
		datum.setPublishOrgName(clerk.getEntityName());
		
		DatumObject datumObject = new DatumObject(datum);
		String code = codeGeneratorProvider.generateCode(datumObject);
		datum.setCode(code);
		
		return datumDao.save(datum);
	}
	
	/**
	 * �������ϡ�
	 * 
	 * ÿ�β�����Work����Ӧ��Datum������Datum����
	 */
	public List<Datum> processDatum(byte category, Task task, Work work, int processType, RFSObject object,
			MatterVO matters, DatumVOList datumList, Clerk clerk) {
		if(datumList == null || datumList.isEmpty()){
			throw new IllegalArgumentException("datumList is required.");
		}
		List<Datum> result = new ArrayList<Datum>();
		//Long[] matterIds = matters.getMatterIds();
		for(DatumVO datumVO: datumList){
			//�ж�DatumCategory
			if(datumVO.getDatumCategoryId() == null) {
				log.debug("datum category id is null, skip process");
				continue;
			}
			DatumCategory cat = datumCategoryDao.get(datumVO.getDatumCategoryId());
			if(cat == null) {
				log.warn("����datumCategoryIdΪ " + datumVO.getDatumCategoryId() + " ʱû���ҵ�DatumCategory");
				continue;
			}
			
			Datum datum = getCurrentDatum(object.getId(), datumVO.getDatumCategoryId());
			Datum newDatum = null;
			
			if(datumVO.getAttachmentId() == null){//���β��������ϣ�������ɾ����
				if(log.isDebugEnabled()){
					log.debug("maybe attachment need delete for category '"
							+ cat.getName()
							+ "' and object '" + object.getId() + "'");
				}
				//ԭ���У�����û�У���ɾ�����������޸�Ϊ��ʷ����
				if(datum != null){
					datum = updateToHistory(datum);
					//������ĵ�ǰ��������Ϊnull
					//��Task�ĵ�ǰ��������Ϊnull
				}
			}else{
				String currentFileId = String.valueOf(datumVO.getAttachmentId());
				//������������ϴ����ļ���ԭ������ͬ
				if(datum != null && currentFileId.equals(datum.getContent())){
					newDatum = datum;
					continue;
				}else{//��ͬ
					if(datum != null){
						datum = updateToHistory(datum);
					}
					newDatum = createCurrentDatum(object, datumVO, cat, clerk);
				}
			}
			
			if(newDatum != null){
				result.add(newDatum);
			}
			if((task != null && task.getSn() != null) || (object != null && object.getId() != null)){
				processDatumForMatters(category, task, work, processType, object, matters, datumVO, clerk, cat, datum, newDatum);
			}
		}
		return result;
	}
	
	private void processDatumForMatters(byte category, Task task, Work work, int processType, RFSObject object,
			MatterVO matters, DatumVO datumVO, Clerk clerk, DatumCategory cat, Datum oldCurrentDatum, Datum newCurrentDatum) {
		int index = 0;
		//���� Matter
		for(Long matterId : matters.getMatterIds()) {
			List<MatterDatum> list = matterDatumDao.findMatterDatum(category, task.getType(), work.getType(), processType, matterId, cat.getId());
			if(list == null || list.isEmpty()) {
				String s = String.format("û��matterId=%s, taskType=%s, workType=%s, category=%s(%s)��MatterDatum��" +
						" ������Task��Object��ص�Datum��", 
						matterId, task.getType(), work.getType(), cat.getName(), cat.getId());
				log.warn(s);
				continue;
			}
			
			for(MatterDatum md : list) {
				//���� TaskData
				if(task != null && task.getSn() != null) {
//					handlerTaskData(md.getTaskAction(), datum, task.getSn(), work.getSn(), index);
					processTaskData(md, task, work, processType, object, datumVO, clerk, cat, oldCurrentDatum, newCurrentDatum, index);
				}
				//���� ObjectData
				if(object != null && object.getId() != null) {
//					handlerObjectData(md.getObjectAction(), datum, task.getSn(), object.getId(), index);
					processObjectData(md, task, work, processType, object, datumVO, clerk, cat, oldCurrentDatum, newCurrentDatum, index);
				}
			}
			index++;
		}
	}

	private void processTaskData(MatterDatum md, Task task, Work work, int processType, RFSObject object,
			DatumVO datumVO, Clerk clerk, DatumCategory cat, Datum oldCurrentDatum, Datum newCurrentDatum, int index) {
		TaskData td = null;
		if(oldCurrentDatum != null){
			td = getCurrentTaskData(task.getSn(), /*cat.getId(), */oldCurrentDatum.getId());
		}
		int workSN = work != null ? work.getSn().intValue() : 0;
		
		switch(md.getTaskAction()) {
		case MatterDatum.ACTION_ADDITION :
			if(td != null){
				td.setStatus(Datum.STATUS_��ʷ����);
				td.setModificationWork(workSN);
				taskDataDao.update(td);
			}
			if(newCurrentDatum != null){
				createCurrentTaskData(task, newCurrentDatum, workSN, index);
			}
			break;
		case MatterDatum.ACTION_DELETE_REPLACE :
		case MatterDatum.ACTION_REPLACE :
			if(td != null){
				taskDataDao.delete(td);
			}
			if(newCurrentDatum != null){
				createCurrentTaskData(task, newCurrentDatum, workSN, index);
			}
			break;
		case MatterDatum.ACTION_DELETE :
			if(td != null){
				taskDataDao.delete(td);
			}
			break;
		}
	}
	
	private TaskData createCurrentTaskData(Task task, Datum currentDatum, int workSN, int index){
		TaskData taskData = new TaskData();
		taskData.setDatumID(currentDatum.getId());
		taskData.setDatumCategoryID(currentDatum.getCategoryID());
		taskData.setDisplayOrder((byte) index);
		taskData.setCreateWork(workSN);
		taskData.setTaskSN(task.getSn());
		taskData.setStatus(Datum.STATUS_��ǰ����);
		taskData.setType(Datum.TYPE_DATUM);
		return taskDataDao.save(taskData);
	}

	private void processObjectData(MatterDatum md, Task task, Work work, int processType, RFSObject object,
			DatumVO datumVO, Clerk clerk, DatumCategory cat, Datum oldCurrentDatum, Datum newCurrentDatum, int index) {
		ObjectData od = null;
		if(oldCurrentDatum != null){
			od = getCurrentObjectData(object.getId(), /*cat.getId(), */oldCurrentDatum.getId());
		}
		int taskSN = task != null ? task.getSn().intValue() : 0;
		
		switch(md.getObjectAction()) {
		//��������ԭ������Ϊ��ʷ�������µ�
		case MatterDatum.ACTION_ADDITION :
			if(od != null){
				od.setStatus(Datum.STATUS_��ʷ����);
				od.setModificationTask(taskSN);
				objectDataDao.update(od);
			}
			if(newCurrentDatum != null){
				createCurrentObjectData(object, newCurrentDatum, taskSN, index);
			}
			break;
		case MatterDatum.ACTION_DELETE_REPLACE :
		case MatterDatum.ACTION_REPLACE :
			if(od != null){
				objectDataDao.delete(od);
			}
			if(newCurrentDatum != null){
				createCurrentObjectData(object, newCurrentDatum, taskSN, index);
			}
			break;
		case MatterDatum.ACTION_DELETE :
			if(od != null){
				objectDataDao.delete(od);
			}
			break;
		}
	}
	
	private ObjectData createCurrentObjectData(RFSObject object, Datum currentDatum, int createTaskSN, int index){
		ObjectData objectData = new ObjectData();
		objectData.setDatumID(currentDatum.getId());
		objectData.setDatumCategoryID(currentDatum.getCategoryID());
		objectData.setCreateTask(createTaskSN);
		objectData.setDisplayOrder((byte) index);
		objectData.setObjectID(object.getId());
		objectData.setStatus(Datum.STATUS_��ǰ����);
		objectData.setType(Datum.TYPE_DATUM);
		return objectDataDao.save(objectData);
	}

}
