/*
 * $Id: BizMatter.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import org.opoo.ndao.Domain;

/**
 * @author ymq
 */
public class BizMatter extends VersionableBean implements Domain<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8930668880531922036L;
	private Long sn;
	private Long jobSN;
	private Long taskSN;
	private Long workSN;
	private Long processSN;
	private Long matterID;
	private byte displayOrder;

	public BizMatter() {
	}
	
	/**
	 * 唯一标示
	 * 
	 * @return Long
	 */
	public Long getId() {
		return getSn();
	}

	/**
	 * @param ID
	 */
	public void setId(Long id) {
		setSn(id);
	}

	/**
	 * 唯一标示
	 * 
	 * @return Long
	 */
	public Long getSn() {
		return sn;
	}

	/**
	 * @param SN
	 */
	public void setSn(Long sn) {
		this.sn = sn;
	}
	
	/**
	 * 作业
	 * 
	 * 默认为0,表示忽略.
	 * @return Long
	 */	
	public Long getJobSN() {
		return jobSN;
	}

	/**
	 * @param jobSN
	 */
	public void setJobSN(Long jobSN) {
		this.jobSN = jobSN;
	}

	/**
	 * 任务
	 * 
	 * 不能为0.
	 * @return Long
	 */
	public Long getTaskSN() {
		return taskSN;
	}

	/**
	 * @param taskSN
	 */
	public void setTaskSN(Long taskSN) {
		this.taskSN = taskSN;
	}

	/**
	 * 工作
	 * 
	 * 默认为0,表示忽略.
	 * @return Long
	 */
	public Long getWorkSN() {
		return workSN;
	}

	/**
	 * @param workSN
	 */
	public void setWorkSN(Long workSN) {
		this.workSN = workSN;
	}

	/**
	 * 事务
	 * 
	 * 默认为0,表示忽略.
	 * @return Long
	 */
	public Long getProcessSN() {
		return processSN;
	}

	/**
	 * @param processSN
	 */
	public void setProcessSN(Long processSN) {
		this.processSN = processSN;
	}

	/**
	 * 事项ID
	 * 
	 * 具体事项ID,不可为0或空.
	 * @return Long
	 */
	public Long getMatterID() {
		return matterID;
	}

	/**
	 * @param matterID
	 */
	public void setMatterID(Long matterID) {
		this.matterID = matterID;
	}
	
	/**
	 * 顺序
	 * 
	 * 显示顺序.
	 * @return byte
	 */
	public byte getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param displayOrder
	 */
	public void setDisplayOrder(byte displayOrder) {
		this.displayOrder = displayOrder;
	}
}
