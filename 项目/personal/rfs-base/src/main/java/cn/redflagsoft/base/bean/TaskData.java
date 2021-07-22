/*
 * TaskData.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * @author Administrator
 *
 */
public class TaskData extends VersionableBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3879736929564021184L;
	private Long sn;
	private Long taskSN;
	private Long datumID;
	private Long datumCategoryID;
	private byte displayOrder;
	private Integer createWork;
	private Integer modificationWork;
	
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
	 * @return the datumID
	 */
	public Long getDatumID() {
		return datumID;
	}
	/**
	 * 资料
	 * @param datumID the datumID to set
	 */
	public void setDatumID(Long datumID) {
		this.datumID = datumID;
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
	 * @return the createWork
	 */
	public Integer getCreateWork() {
		return createWork;
	}
	/**
	 * 创建工作
	 * @param createWork the createWork to set
	 */
	public void setCreateWork(Integer createWork) {
		this.createWork = createWork;
	}
	/**
	 * @return the modificationWork
	 */
	public Integer getModificationWork() {
		return modificationWork;
	}
	/**
	 * 修订工作
	 * @param modificationWork the modificationWork to set
	 */
	public void setModificationWork(Integer modificationWork) {
		this.modificationWork = modificationWork;
	}

	/**
	 * @return the datumCategoryID
	 */
	public Long getDatumCategoryID() {
		return datumCategoryID;
	}

	/**
	 * @param datumCategoryID the datumCategoryID to set
	 */
	public void setDatumCategoryID(Long datumCategoryID) {
		this.datumCategoryID = datumCategoryID;
	}
}
