/*
 * $Id: SavableStageTaskDetails.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import java.util.Date;

import cn.redflagsoft.base.bean.StageTaskDetails;

/**
 * ���Ե��ñ��淽����StageTaskDetails��
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface SavableStageTaskDetails {
	/**
	 * ���浱ǰ��StageTaskDetails��ʵ���Ǹ��»����޸ġ�
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
	 * Ԥ�Ʊ��ƿ�ʼʱ��
	 * @return
	 */
	Date getExpectedBzStartTime();
	
	void setExpectedBzStartTime(Date expectedBzStartTime);
	/**
	 * Ԥ�Ʊ��ƽ���ʱ��
	 * @return
	 */
	Date getExpectedBzFinishTime();
	
	void setExpectedBzFinishTime(Date expectedBzFinishTime);
	
	Date getExpectedSbTime();
	/**
	 * Ԥ���걨ʱ��
	 * @param expectedSbTime
	 */
	void setExpectedSbTime(Date expectedSbTime);
	
	
	Date getExpectedPfTime();
	/**
	 * Ԥ������ʱ��
	 * @param expectedPfTime
	 */
	void setExpectedPfTime(Date expectedPfTime);
}
