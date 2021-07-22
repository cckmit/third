/*
 * $Id: ObjectTask.java 3996 2010-10-18 06:56:46Z lcj $
 * ObjectTask.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * @author Administrator
 *
 */
public class ObjectTask extends VersionableBean implements Observable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6673342038339993795L;
	private Long sn;
	private Long objectID;
	private Long taskSN;
	private byte displayOrder;
	private Integer createTask;
	private Integer modificationTask;
	
	public Long getId(){
		return getSn();
	}
	
	public void setId(Long id){
		setSn(id);
	}
	
	/**
	 * @return the sn
	 */
	public Long getSn() {
		return sn;
	}

	/**
	 * 编号
	 * 
	 * 唯一
	 * @param sn the sn to set
	 */
	public void setSn(Long sn) {
		this.sn = sn;
	}

	/**
	 * @return the taskSN
	 */
	public Long getTaskSN() {
		return taskSN;
	}

	/**
	 * 任务
	 * @param taskSN the taskSN to set
	 */
	public void setTaskSN(Long taskSN) {
		this.taskSN = taskSN;
	}

	/**
	 * @return the displayOrder
	 */
	public byte getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * 顺序
	 * 
	 * 默认为0，表示忽略
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(byte displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * @return the createTask
	 */
	public Integer getCreateTask() {
		return createTask;
	}

	/**
	 * 创建任务
	 * @param createTask the createTask to set
	 */
	public void setCreateTask(Integer createTask) {
		this.createTask = createTask;
	}

	/**
	 * @return the modificationTask
	 */
	public Integer getModificationTask() {
		return modificationTask;
	}

	/**
	 * 修订任务
	 * @param modificationTask the modificationTask to set
	 */
	public void setModificationTask(Integer modificationTask) {
		this.modificationTask = modificationTask;
	}

	public Long getObjectID() {
		return objectID;
	}

	public void setObjectID(Long objectID) {
		this.objectID = objectID; 
	}
	 

}
