/*
 * $Id: ObjectServiceImpl.java 6372 2014-04-14 10:52:55Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.opoo.apps.id.IdGeneratable;
import org.opoo.apps.id.IdGenerator;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.ndao.support.ResultFilterUtils;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.ArchivingStatus;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.dao.ObjectDao;
import cn.redflagsoft.base.service.ObjectService;
import cn.redflagsoft.base.service.PlanMonitorService;
import cn.redflagsoft.base.vo.statistics.PlanMonitorResult;

/**
 * 业务对象管理类。
 * 
 * <p>
 * 注意，在1.5.2以后版本中，查询业务对象集合类的方法中已经默认带了条件，
 * 只查询状态字段有效的记录。
 * 
 * @author Alex Lin
 *
 */
public class ObjectServiceImpl<T extends RFSObject> extends RiskMonitorableServiceImpl implements ObjectService<T> {
	private ObjectDao<T> objectDao;
	private IdGenerator<Long> idGenerator;
	private PlanMonitorService planMonitorService;
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		Assert.notNull(objectDao, "objectDao 不能为空");
		Assert.notNull(idGenerator, "idGenerator 不能为空");
		Assert.notNull(planMonitorService, "planMonitorService 不能为空");
	}
	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectService#deleteObject(cn.redflagsoft.base.bean.RFSObject)
	 */
	public void deleteObject(T object) {
		objectDao.delete(object);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectService#findObjects(org.opoo.ndao.support.ResultFilter)
	 */
	public List<T> findObjects(ResultFilter rf) {
		rf = ResultFilterUtils.append(rf, ArchivingStatus.VALID_STATUS_CRITERION);
		return objectDao.find(rf);
	}
	
	public List<T> findObjectsOrigin(ResultFilter rf) {
		//rf = ResultFilterUtils.append(rf, ArchivingStatus.VALID_STATUS_FOR_ORG);
		return objectDao.find(rf);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectService#findPageableObjects(org.opoo.ndao.support.ResultFilter)
	 */
	public PageableList<T> findPageableObjects(ResultFilter rf) {
		rf = ResultFilterUtils.append(rf, ArchivingStatus.VALID_STATUS_CRITERION);
		return objectDao.findPageableList(rf);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectService#getObject(java.lang.Long)
	 */
	public T getObject(Long id) {
		return objectDao.get(id);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectService#getObjectCount(org.opoo.ndao.criterion.Criterion)
	 */
	public int getObjectCount(ResultFilter rf) {
		rf = ResultFilterUtils.append(rf, ArchivingStatus.VALID_STATUS_CRITERION);
		return objectDao.getCount(rf);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectService#removeObject(java.lang.Long)
	 */
	public void removeObject(Long id) {
		objectDao.remove(id);
	}

	public Long generateId(){
		return idGenerator.getNext();
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectService#saveObject(cn.redflagsoft.base.bean.RFSObject)
	 */
	public T saveObject(T object) {
		if(object.getId() == null){
			object.setId(generateId());
		}
		object = objectDao.save(object);
		createRisksForRiskMonitorable(object, null);
		return object;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectService#updateObject(cn.redflagsoft.base.bean.RFSObject)
	 */
	public T updateObject(T object) {
		T t = objectDao.get(object.getId());
		object = objectDao.update(object);
		updateRiskValueIfNeeded(t, object);
		return object;
	}
	
	public T updateObject(T oldObject, T newObject){
		newObject = objectDao.update(newObject);
		updateRiskValueIfNeeded(oldObject, newObject);
		return newObject;//objectDao.update(newObject);
	}
	

	//public List<T> findObjectByGrade(Integer objectType, Short lifeStage, Byte grade, Byte managerType, Long manager) {
	//	return objectDao.findObjectByRisk(objectType, lifeStage, grade, managerType, manager);
	//}

	/**
	 * @return the objectDao
	 */
	public ObjectDao<T> getObjectDao() {
		return objectDao;
	}

	/**
	 * @param objectDao the objectDao to set
	 */
	@SuppressWarnings("unchecked")
	public void setObjectDao(ObjectDao<T> objectDao) {
		this.objectDao = objectDao;
		this.idGenerator = ((IdGeneratable<Long>) objectDao).getIdGenerator();
	}

	public void setPlanMonitorService(PlanMonitorService planMonitorService) {
		this.planMonitorService = planMonitorService;
	}


	public PlanMonitorResult getObjectRiskSummaryByManager(Integer objectType, Integer riskType, Short objectStatus, Short year,int key,Long entityID) {
		return planMonitorService.getObjectRiskSummaryByManager(objectType, riskType, objectStatus, year,key,entityID);
	}

	@Deprecated
	public void updateObjectsDutyClerkIdAndNameByClerkID(List<Long> ids,Long clerkID,String clerkName){
		objectDao.updateObjectsDutyClerkIdAndNameByClerkID(ids, clerkID, clerkName);
	}


	public List<Long> findObjectIds(ResultFilter rf) {
		rf = ResultFilterUtils.append(rf, ArchivingStatus.VALID_STATUS_CRITERION);
		return objectDao.findIds(rf);
	}
}
