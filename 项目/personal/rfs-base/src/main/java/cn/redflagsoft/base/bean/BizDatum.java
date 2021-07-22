/*
 * $Id: BizDatum.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import org.opoo.ndao.Domain;

/**
 * @author ymq
 */
public class BizDatum extends VersionableBean implements Domain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4497560907121185278L;
	private Long sn;
	private Long jobSN;
	private Long taskSN;
	private Long workSN;
	private Long processSN;
	private Long datumID;
	private short count;
	private byte displayOrder;
	private byte orgNum;
	private byte copyNum;
	
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

	/**
	 * 唯一标示
	 * 
	 * @return Long
	 */
	public Long getSn() {
		return sn;
	}

	/**
	 * @param sn
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
	 * 资料
	 * 
	 * 具体到某个资料实例.
	 * @return Long
	 */
	public Long getDatumID() {
		return datumID;
	}
	
	/**
	 * @param datumID
	 */
	public void setDatumID(Long datumID) {
		this.datumID = datumID;
	}
	
	/**
	 * 数据
	 * 
	 * 默认为1.
	 * @return short
	 */
	public short getCount() {
		return count;
	}
	
	/**
	 * @param count
	 */
	public void setCount(short count) {
		this.count = count;
	}
	
	/**
	 * 顺序
	 * 
	 * 默认为0,表示忽略.
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
	
	/**
	 * 正本数
	 * 
	 * 默认为0.
	 * @return byte
	 */
	public byte getOrgNum() {
		return orgNum;
	}
	
	/**
	 * @param orgNum
	 */
	public void setOrgNum(byte orgNum) {
		this.orgNum = orgNum;
	}
	
	/**
	 * 副本数
	 * 
	 * 默认为0.
	 * @return byte
	 */
	public byte getCopyNum() {
		return copyNum;
	}
	
	/**
	 * @param copyNum
	 */
	public void setCopyNum(byte copyNum) {
		this.copyNum = copyNum;
	}
}
