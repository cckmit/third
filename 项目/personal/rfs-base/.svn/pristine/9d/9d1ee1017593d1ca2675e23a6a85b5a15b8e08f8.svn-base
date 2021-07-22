/*
 * RFSObjectDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.ObjectRiskGrade;
import cn.redflagsoft.base.bean.RFSObject;

/**
 * @author Alex Lin
 *
 */
public interface ObjectDao<T extends RFSObject> extends Dao<T, Long> {
	
	List<T> findObjectByRisk(Integer objectType, Byte grade, Short lifeStage, Byte managerType, Long manager, Byte valueType, Short year,int key);
	
	/**
	 * 根据objectType查询分组归类的对象。
	 * @param objectType
	 * @return
	 */
	List<ObjectRiskGrade> findObjectGroupByRiskGrade(Integer objectType, Byte valueType);
	/**
	 * 
	 * @param objectType
	 * @param riskType
	 * @param objectStatus
	 * @return
	 */
	List<RFSObject> findObjectGroupByRiskGrade(Integer objectType, Integer riskType, Short objectStatus);
	
	/**
	 * 
	 * @param objectType
	 * @param riskType
	 * @param objectStatus
	 * @param year
	 * @return
	 */
	List<RFSObject> findObjectGroupByRiskGrade(Integer objectType, Integer riskType, Short objectStatus, Short year,int key,Long entityID);

	@Deprecated
	void updateObjectsDutyClerkIdAndNameByClerkID(List<Long> ids,Long clerkID,String clerkName);
}
