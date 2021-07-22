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
	 * Ψһ��ʾ
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
	 * ��ҵ
	 * 
	 * Ĭ��Ϊ0,��ʾ����.
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
	 * ����
	 * 
	 * ����Ϊ0
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
	 * ����
	 * 
	 * Ĭ��Ϊ0,��ʾ����.
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
	 * ����
	 * 
	 * Ĭ��Ϊ0,��ʾ����.
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
	 * �±�ID
	 * 
	 * �����±�ID,����Ϊ0���.
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
	 * ˳��
	 * 
	 * ��ʾ˳��
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
