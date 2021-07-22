/*
 * $Id: TaskableObject.java 4666 2011-09-06 08:08:18Z zf $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.util.Date;

/**
 * @author Alex Lin(alex@opoo.org)
 * 
 */
public interface TaskableObject extends Taskable {
	
	Long getTaskSN();
	
	long getSafeTaskSN();
	
	String getTaskName();
	
	String getTaskCode();
	
	Long getTaskClerkId();
	
	String getTaskClerkName();

	Long getActiveDutyerID();

	Long getActiveMatter();

	Long getActiveTaskSN();

	int getAttachmentCount();

	Date getCreationTime();

	Long getCreator();

	Long getDutyClerkID();

	String getDutyClerkName();

	Long getDutyDepartmentID();

	String getDutyDepartmentName();

	Long getId();

	int getLifeStage();

	Long getManager();

	byte getManagerType();

	Date getModificationTime();

	Long getModifier();

	String getName();

	String getRemark();

	// String getRiskEntryDescription();
	Byte getRiskGrade();

	byte getStatus();

	short getTag();

	int getType();

	int getObjectType();
		
}
