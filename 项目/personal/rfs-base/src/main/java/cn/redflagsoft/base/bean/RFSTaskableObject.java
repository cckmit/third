/*
 * $Id: RFSTaskableObject.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.lang.reflect.Method;

import org.springframework.util.Assert;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class RFSTaskableObject extends RFSObject implements TaskableObject, Taskable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3901443409849206388L;
	private Task task;
	private int objectType;
	private String sn;
	
	public RFSTaskableObject(RFSObject object, Task task){
		Assert.notNull(object, "业务对象不能为空");
		this.setActiveDutyerID(object.getActiveDutyerID());
		this.setActiveMatter(object.getActiveMatter());
		this.setActiveTaskSN(object.getActiveTaskSN());
		this.setAttachmentCount(object.getAttachmentCount());
		this.setCreationTime(object.getCreationTime());
		this.setCreator(object.getCreator());
		this.setDutyClerkID(object.getDutyClerkID());
		this.setDutyClerkName(object.getDutyClerkName());
		this.setDutyDepartmentID(object.getDutyDepartmentID());
		this.setDutyDepartmentName(object.getDutyDepartmentName());
		this.setId(object.getId());
		this.setLifeStage(object.getLifeStage());
		this.setManager(object.getManager());
		this.setManagerType(object.getManagerType());
		this.setModificationTime(object.getModificationTime());
		this.setModifier(object.getModifier());
		this.setName(object.getName());
		this.setRemark(object.getRemark());
		this.setRiskEntryDescription(object.getRiskEntryDescription());
		this.setRiskGrade(object.getRiskGrade());
		this.setStatus(object.getStatus());
		this.setTag(object.getTag());
		this.setType(object.getType());
		this.objectType = object.getObjectType();
		this.sn = getSn(object);
		
		this.task = task;
	}
	
	public String getSn() {
		return sn;
	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	private String getSn(RFSObject object){
		try {
			Method method = object.getClass().getMethod("getSn");
			if(method != null){
				return (String) method.invoke(object);
			}
		} catch (Exception e) {
			//ignore exception
		} 	
		return null;
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Taskable#getSafeTaskSN()
	 */
	public long getSafeTaskSN() {
		return task != null ? task.getSn().longValue() : 0L;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Taskable#getTaskClerkId()
	 */
	public Long getTaskClerkId() {
		return task != null ? task.getClerkID() : null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Taskable#getTaskClerkName()
	 */
	public String getTaskClerkName() {
		return task != null ? task.getClerkName() : null;
	}
	

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Taskable#getTaskCode()
	 */
	public String getTaskCode() {
		return task != null ? task.getCode() : null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Taskable#getTaskName()
	 */
	public String getTaskName() {
		return task != null ? task.getName() : null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Taskable#getTaskSN()
	 */
	public Long getTaskSN() {
		return task != null ? task.getSn() : null;
	}
	
	public int getObjectType() {
		return objectType;
	}
}
