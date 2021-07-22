/*
 * $Id: TaskableObject.java 4666 2011-09-06 08:08:18Z zf $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
