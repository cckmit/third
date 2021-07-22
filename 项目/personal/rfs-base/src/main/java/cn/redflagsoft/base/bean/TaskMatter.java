/*
 * $Id: TaskMatter.java 3996 2010-10-18 06:56:46Z lcj $
 * TaskMatter.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * @author Administrator
 *
 */
public class TaskMatter extends VersionableBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1699582767228555784L;
	private Long sn;
	private Long taskSN;
	private Long MatterID;
	private Integer bizRoute;
	private byte displayOrder;
	private byte result;
	private Integer createWork;
	private Integer modificationWork;
	private String matterCode;
	private String matterName;
	
	public String getMatterCode() {
		return matterCode;
	}

	public void setMatterCode(String matterCode) {
		this.matterCode = matterCode;
	}

	public String getMatterName() {
		return matterName;
	}

	public void setMatterName(String matterName) {
		this.matterName = matterName;
	}
	
	
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
	 * 
	 * 具体任务
	 * @param taskSN the taskSN to set
	 */
	public void setTaskSN(Long taskSN) {
		this.taskSN = taskSN;
	}
	/**
	 * @return the matterID
	 */
	public Long getMatterID() {
		return MatterID;
	}
	/**
	 * 事项
	 * 
	 * 具体事项ID，不可为0或空
	 * @param matterID the matterID to set
	 */
	public void setMatterID(Long matterID) {
		MatterID = matterID;
	}
	/**
	 * @return the bizRoute
	 */
	public Integer getBizRoute() {
		return bizRoute;
	}
	/**
	 * 流程
	 * 
	 * 运用流程，不同时期，运用的流程可能不同
	 * @param bizRoute the bizRoute to set
	 */
	public void setBizRoute(Integer bizRoute) {
		this.bizRoute = bizRoute;
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
	 * 显示顺序，默认为0，表示忽略
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(byte displayOrder) {
		this.displayOrder = displayOrder;
	}
	/**
	 * @return the result
	 */
	public byte getResult() {
		return result;
	}
	/**
	 * 结果
	 * 
	 * 0 在办，1 成功，2 失败，3 撤销
	 * @param result the result to set
	 */
	public void setResult(byte result) {
		this.result = result;
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
}
