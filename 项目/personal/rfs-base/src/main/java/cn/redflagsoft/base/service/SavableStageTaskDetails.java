/*
 * $Id: SavableStageTaskDetails.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.Date;

import cn.redflagsoft.base.bean.StageTaskDetails;

/**
 * 可以调用保存方法的StageTaskDetails。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface SavableStageTaskDetails {
	/**
	 * 保存当前的StageTaskDetails，实质是更新或者修改。
	 * 
	 * @return
	 */
	StageTaskDetails save();

	int getType();

	void setType(int type);

	int getTaskType();

	void setTaskType(int taskType);

	long getObjectId();

	void setObjectId(long objectId);

	String getObjectName();

	void setObjectName(String objectName);

	int getObjectType();

	void setObjectType(int objectType);

	String getTaskSNs();

	void setTaskSNs(String taskSNs);

	String getWorkSNs();

	void setWorkSNs(String workSNs);

	String getWorkItemName();

	void setWorkItemName(String workItemName);

	Long getSpbmId();

	void setSpbmId(Long spbmId);

	String getSpbmName();

	void setSpbmName(String spbmName);

	Long getBzdwId();

	void setBzdwId(Long bzdwId);

	String getBzdwName();

	void setBzdwName(String bzdwName);

	Date getSbTime();

	void setSbTime(Date sbTime);

	String getSbFileNo();

	void setSbFileNo(String sbFileNo);

	Long getSbFileId();

	void setSbFileId(Long sbFileId);

	Date getPfTime();

	void setPfTime(Date pfTime);

	String getPfFileNo();

	void setPfFileNo(String pfFileNo);

	Long getPfFileId();

	void setPfFileId(Long pfFileId);

	Long getPfdwId();

	void setPfdwId(Long pfdwId);

	String getPfdwName();

	void setPfdwName(String pfdwName);

	Date getExpectedFinishTime();

	void setExpectedFinishTime(Date expectedFinishTime);

	Date getActualFinishTime();

	void setActualFinishTime(Date actualFinishTime);

	Date getBzStartTime();

	void setBzStartTime(Date bzStartTime);

	Date getBzFinishTime();

	void setBzFinishTime(Date bzFinishTime);

	Date getExpectedStartTime();

	void setExpectedStartTime(Date expectedStartTime);

	Date getActualStartTime();

	void setActualStartTime(Date actualStartTime);

	Date getCreationTime();

	Long getCreator();

	Date getModificationTime();

	Long getModifier();

	void setCreationTime(Date creationTime);

	void setCreator(Long creator);

	void setModificationTime(Date modificationTime);

	void setModifier(Long modifier);

	String getRemark();

	byte getStatus();

	void setRemark(String remark);

	void setStatus(byte status);

	Long getId();

	Long getKey();

	void setId(Long id);

	void setKey(Long id);
	
	/**
	 * 预计编制开始时间
	 * @return
	 */
	Date getExpectedBzStartTime();
	
	void setExpectedBzStartTime(Date expectedBzStartTime);
	/**
	 * 预计编制结束时间
	 * @return
	 */
	Date getExpectedBzFinishTime();
	
	void setExpectedBzFinishTime(Date expectedBzFinishTime);
	
	Date getExpectedSbTime();
	/**
	 * 预计申报时间
	 * @param expectedSbTime
	 */
	void setExpectedSbTime(Date expectedSbTime);
	
	
	Date getExpectedPfTime();
	/**
	 * 预计批复时间
	 * @param expectedPfTime
	 */
	void setExpectedPfTime(Date expectedPfTime);
}
