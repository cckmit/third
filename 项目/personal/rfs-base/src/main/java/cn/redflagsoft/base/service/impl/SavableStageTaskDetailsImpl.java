/*
 * $Id: SavableStageTaskDetailsImpl.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.Date;

import cn.redflagsoft.base.bean.StageTaskDetails;
import cn.redflagsoft.base.service.SavableStageTaskDetails;

/**
 * SavableStageTaskDetails的实现类。
 * 该类需要与{@link StageTaskDetailsServiceImpl}配合使用。
 * 
 * @see StageTaskDetailsServiceImpl
 * @author Alex Lin(alex@opoo.org)
 * 
 */
class SavableStageTaskDetailsImpl implements SavableStageTaskDetails {
	private final StageTaskDetailsServiceImpl stageTaskDetailsServiceImpl;
	private final StageTaskDetails details;
	private boolean isNew = false;

	SavableStageTaskDetailsImpl(StageTaskDetails details, StageTaskDetailsServiceImpl stageTaskDetailsServiceImpl) {
		this.stageTaskDetailsServiceImpl = stageTaskDetailsServiceImpl;
		if(details == null){
			details = new StageTaskDetails();
			isNew = true;
		}
		this.details = details;
	}

	SavableStageTaskDetailsImpl(StageTaskDetailsServiceImpl stageTaskDetailsServiceImpl) {
		this.stageTaskDetailsServiceImpl = stageTaskDetailsServiceImpl;
		this.details = new StageTaskDetails();
		isNew = true;
	}
	
	SavableStageTaskDetailsImpl(){
		this.stageTaskDetailsServiceImpl = null;
		this.details = new StageTaskDetails();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#save()
	 */
	public StageTaskDetails save() {
		if(stageTaskDetailsServiceImpl == null){
			StageTaskDetailsServiceImpl.log.debug("StageTask 不保存");
			return null;
		}
		
		if(isNew){
			return stageTaskDetailsServiceImpl.saveStageTaskDetails(details);
		}else{
			return stageTaskDetailsServiceImpl.updateStageTaskDetails(details);
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getType()
	 */
	public int getType() {
		return details.getType();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setType(short)
	 */
	public void setType(int type) {
		details.setType(type);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getTaskType()
	 */
	public int getTaskType() {
		return details.getTaskType();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setTaskType(short)
	 */
	public void setTaskType(int taskType) {
		details.setTaskType(taskType);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getObjectId()
	 */
	public long getObjectId() {
		return details.getObjectId();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setObjectId(long)
	 */
	public void setObjectId(long objectId) {
		details.setObjectId(objectId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getObjectName()
	 */
	public String getObjectName() {
		return details.getObjectName();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setObjectName(java.lang.String)
	 */
	public void setObjectName(String objectName) {
		details.setObjectName(objectName);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getObjectType()
	 */
	public int getObjectType() {
		return details.getObjectType();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setObjectType(short)
	 */
	public void setObjectType(int objectType) {
		details.setObjectType(objectType);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getTaskSNs()
	 */
	public String getTaskSNs() {
		return details.getTaskSNs();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setTaskSNs(java.lang.String)
	 */
	public void setTaskSNs(String taskSNs) {
		details.setTaskSNs(taskSNs);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getWorkSNs()
	 */
	public String getWorkSNs() {
		return details.getWorkSNs();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setWorkSNs(java.lang.String)
	 */
	public void setWorkSNs(String workSNs) {
		details.setWorkSNs(workSNs);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getWorkItemName()
	 */
	public String getWorkItemName() {
		return details.getWorkItemName();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setWorkItemName(java.lang.String)
	 */
	public void setWorkItemName(String workItemName) {
		details.setWorkItemName(workItemName);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getSpbmId()
	 */
	public Long getSpbmId() {
		return details.getSpbmId();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setSpbmId(java.lang.Long)
	 */
	public void setSpbmId(Long spbmId) {
		details.setSpbmId(spbmId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getSpbmName()
	 */
	public String getSpbmName() {
		return details.getSpbmName();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setSpbmName(java.lang.String)
	 */
	public void setSpbmName(String spbmName) {
		details.setSpbmName(spbmName);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getBzdwId()
	 */
	public Long getBzdwId() {
		return details.getBzdwId();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setBzdwId(java.lang.Long)
	 */
	public void setBzdwId(Long bzdwId) {
		details.setBzdwId(bzdwId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getBzdwName()
	 */
	public String getBzdwName() {
		return details.getBzdwName();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setBzdwName(java.lang.String)
	 */
	public void setBzdwName(String bzdwName) {
		details.setBzdwName(bzdwName);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getSbTime()
	 */
	public Date getSbTime() {
		return details.getSbTime();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setSbTime(java.util.Date)
	 */
	public void setSbTime(Date sbTime) {
		details.setSbTime(sbTime);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getSbFileNo()
	 */
	public String getSbFileNo() {
		return details.getSbFileNo();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setSbFileNo(java.lang.String)
	 */
	public void setSbFileNo(String sbFileNo) {
		details.setSbFileNo(sbFileNo);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getSbFileId()
	 */
	public Long getSbFileId() {
		return details.getSbFileId();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setSbFileId(java.lang.Long)
	 */
	public void setSbFileId(Long sbFileId) {
		details.setSbFileId(sbFileId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getPfTime()
	 */
	public Date getPfTime() {
		return details.getPfTime();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setPfTime(java.util.Date)
	 */
	public void setPfTime(Date pfTime) {
		details.setPfTime(pfTime);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getPfFileNo()
	 */
	public String getPfFileNo() {
		return details.getPfFileNo();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setPfFileNo(java.lang.String)
	 */
	public void setPfFileNo(String pfFileNo) {
		details.setPfFileNo(pfFileNo);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getPfFileId()
	 */
	public Long getPfFileId() {
		return details.getPfFileId();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setPfFileId(java.lang.Long)
	 */
	public void setPfFileId(Long pfFileId) {
		details.setPfFileId(pfFileId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getPfdwId()
	 */
	public Long getPfdwId() {
		return details.getPfdwId();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setPfdwId(java.lang.Long)
	 */
	public void setPfdwId(Long pfdwId) {
		details.setPfdwId(pfdwId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getPfdwName()
	 */
	public String getPfdwName() {
		return details.getPfdwName();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setPfdwName(java.lang.String)
	 */
	public void setPfdwName(String pfdwName) {
		details.setPfdwName(pfdwName);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getExpectedFinishTime()
	 */
	public Date getExpectedFinishTime() {
		return details.getExpectedFinishTime();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setExpectedFinishTime(java.util.Date)
	 */
	public void setExpectedFinishTime(Date expectedFinishTime) {
		details.setExpectedFinishTime(expectedFinishTime);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getActualFinishTime()
	 */
	public Date getActualFinishTime() {
		return details.getActualFinishTime();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setActualFinishTime(java.util.Date)
	 */
	public void setActualFinishTime(Date actualFinishTime) {
		details.setActualFinishTime(actualFinishTime);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getBzStartTime()
	 */
	public Date getBzStartTime() {
		return details.getBzStartTime();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setBzStartTime(java.util.Date)
	 */
	public void setBzStartTime(Date bzStartTime) {
		details.setBzStartTime(bzStartTime);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getBzFinishTime()
	 */
	public Date getBzFinishTime() {
		return details.getBzFinishTime();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setBzFinishTime(java.util.Date)
	 */
	public void setBzFinishTime(Date bzFinishTime) {
		details.setBzFinishTime(bzFinishTime);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getExpectedStartTime()
	 */
	public Date getExpectedStartTime() {
		return details.getExpectedStartTime();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setExpectedStartTime(java.util.Date)
	 */
	public void setExpectedStartTime(Date expectedStartTime) {
		details.setExpectedStartTime(expectedStartTime);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getActualStartTime()
	 */
	public Date getActualStartTime() {

		return details.getActualStartTime();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setActualStartTime(java.util.Date)
	 */
	public void setActualStartTime(Date actualStartTime) {

		details.setActualStartTime(actualStartTime);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getCreationTime()
	 */
	public Date getCreationTime() {

		return details.getCreationTime();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getCreator()
	 */
	public Long getCreator() {

		return details.getCreator();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getModificationTime()
	 */
	public Date getModificationTime() {

		return details.getModificationTime();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getModifier()
	 */
	public Long getModifier() {

		return details.getModifier();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setCreationTime(java.util.Date)
	 */
	public void setCreationTime(Date creationTime) {

		details.setCreationTime(creationTime);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setCreator(java.lang.Long)
	 */
	public void setCreator(Long creator) {

		details.setCreator(creator);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setModificationTime(java.util.Date)
	 */
	public void setModificationTime(Date modificationTime) {

		details.setModificationTime(modificationTime);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setModifier(java.lang.Long)
	 */
	public void setModifier(Long modifier) {

		details.setModifier(modifier);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getRemark()
	 */
	public String getRemark() {

		return details.getRemark();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getStatus()
	 */
	public byte getStatus() {

		return details.getStatus();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setRemark(java.lang.String)
	 */
	public void setRemark(String remark) {

		details.setRemark(remark);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setStatus(byte)
	 */
	public void setStatus(byte status) {

		details.setStatus(status);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getId()
	 */
	public Long getId() {

		return details.getId();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#getKey()
	 */
	public Long getKey() {
		return details.getKey();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		details.setId(id);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.util.SavableStageTaskDetails#setKey(java.lang.Long)
	 */
	public void setKey(Long id) {
		details.setKey(id);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.SavableStageTaskDetails#getExpectedBzStartTime()
	 */
	public Date getExpectedBzStartTime() {
		return details.getExpectedBzStartTime();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.SavableStageTaskDetails#setExpectedBzStartTime(java.util.Date)
	 */
	public void setExpectedBzStartTime(Date expectedBzStartTime) {
		details.setExpectedBzStartTime(expectedBzStartTime);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.SavableStageTaskDetails#getExpectedBzFinishTime()
	 */
	public Date getExpectedBzFinishTime() {
		return details.getExpectedBzFinishTime();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.SavableStageTaskDetails#setExpectedBzFinishTime(java.util.Date)
	 */
	public void setExpectedBzFinishTime(Date expectedBzFinishTime) {
		details.setExpectedBzFinishTime(expectedBzFinishTime);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.SavableStageTaskDetails#getExpectedSbTime()
	 */
	public Date getExpectedSbTime() {
		return details.getExpectedSbTime();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.SavableStageTaskDetails#setExpectedSbTime(java.util.Date)
	 */
	public void setExpectedSbTime(Date expectedSbTime) {
		details.setExpectedSbTime(expectedSbTime);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.SavableStageTaskDetails#getExpectedPfTime()
	 */
	public Date getExpectedPfTime() {
		return details.getExpectedPfTime();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.SavableStageTaskDetails#setExpectedPfTime(java.util.Date)
	 */
	public void setExpectedPfTime(Date expectedPfTime) {
		details.setExpectedPfTime(expectedPfTime);		
	}
}