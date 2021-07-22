/*
 * $Id: BizAffair.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */

package cn.redflagsoft.base.bean;

import org.opoo.ndao.Domain;


/**
 * @author ymq
 */
public class BizAffair extends VersionableBean implements Domain<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 668462829490404076L;
	private Long sn;
	private Long jobSN;
	private Long taskSN;
	private Long workSN;
	private Long processSN;
	private Long affairID;
	private byte displayOrder;

	public BizAffair() {
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
	 * @param id
	 */
	public void setId(Long id) {
		setSn(id);
	}

	public Long getSn() {
		return sn;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

	/**
	 * 作业
	 * 
	 * 默认为0,表示忽略.
	 * @return
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
	 * 不能为0
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
	 * 事别ID
	 * 
	 * 具体事别ID,不可为0或空.
	 * @return Long
	 */
	public Long getAffairID() {
		return affairID;
	}

	/**
	 * @param affairid
	 */
	public void setAffairID(Long affairID) {
		this.affairID = affairID;
	}

	/**
	 * 顺序
	 * 
	 * 显示顺序
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
