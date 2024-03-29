/*
 * $Id: RiskDao.java 5848 2012-06-07 07:44:16Z lf $
 * RiskDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.List;
import java.util.Map;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Risk;

/**
 * @author Administrator
 *
 */
public interface RiskDao extends Dao<Risk,Long> {

	/**
	 * 注意:本方法使用SQL聚合函数查询,元素中存储的是二维Object[],字段包含[ObjectId, Grade]
	 * @param objectType
	 * @return
	 */
	List<Object[]> findRiskByType(int objectType);
	
	List<RFSObject> findObjectWithRisk(int objectType, short lifeStage, byte grade, Long manager);
	
	List<Risk> findTimeRisks();
	
	void updateObjectAttr(Long objectId, int objectType, String objectAttr, Object value);
	
	List<Risk> findRiskByDutyerId(Long dutyerId);
	
	List<Map<String,Object>> findRiskByTaskTypesAndObjectId(Long objectID);
	
	List<Risk> findRiskByObjectId(final Long objectId, final int objectType) ;
	
	List<Risk> findTaskRiskByObjectIdAndTaskType(Long objectId, final int objectType,Integer... taskTypes);
	
	List<Object[]> findMatterCollect();
	
	List<Object[]> getValueAndScaleValueCountGroupByDutyerID();
	
	void deleteRiskByTaskSn(final Long taskSN); 
	
	List<Risk> findRiskByObjectAndObjectType(Long objectID,Integer objectType);
	
	void riskInvalid(RFSObject rfsObject);
}
