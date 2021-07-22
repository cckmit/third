/*
 * $Id: TimeLimit.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import org.opoo.ndao.Domain;

/**
 * @author ymq
 */
public class TimeLimit extends VersionableBean implements Domain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4317452602767692774L;
	public static final byte CATEGORY_JOB = 1;
	public static final byte CATEGORY_TASK = 2;
	public static final byte CATEGORY_WORK = 3;
	
	
	private Long sn;
	private byte category;
	private Long bizID;
	private byte timeUnit;
	private short timeLimit;
	private short hangLimit;
	private short delayLimit;
	private byte hangTimes;
	private byte delayTimes;
	private String note;

	public TimeLimit() {
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
	 * 分类
	 * 
	 * 分类:1 作业,2 任务,3 工作.
	 * @return byte
	 */
	public byte getCategory() {
		return this.category;
	}

	/**
	 * @param category
	 */
	public void setCategory(byte category) {
		this.category = category;
	}

	/**
	 * 业务
	 * 
	 * 事项ID,默认为0,表示忽略.
	 * @return
	 */
	public Long getBizID() {
		return this.bizID;
	}

	/**
	 * @param bizID
	 */
	public void setBizID(Long bizID) {
		this.bizID = bizID;
	}

	/**
	 * 时间单位
	 * 
	 * 时间单位
	 * 定交: 0 无, 1 年, 2 月, 3 周, 4 日, 5 时, 6 分, 7 称, 8 毫秒, 9 天
	 * 默认为0,表示无定义或忽略.
	 * @return byte
	 */
	public byte getTimeUnit() {
		return this.timeUnit;
	}

	/**
	 * @param timeUnit
	 */
	public void setTimeUnit(byte timeUnit) {
		this.timeUnit = timeUnit;
	}

	/**
	 * 时限
	 * 
	 * 承诺的完成时限.
	 * @return short
	 */
	public short getTimeLimit() {
		return this.timeLimit;
	}

	/**
	 * @param timeLimit
	 */
	public void setTimeLimit(short timeLimit) {
		this.timeLimit = timeLimit;
	}

	/**
	 * 暂停时限
	 * 
	 * 允许最大的暂停时限.
	 * @return short
	 */
	public short getHangLimit() {
		return this.hangLimit;
	}

	/**
	 * @param hangLimit
	 */
	public void setHangLimit(short hangLimit) {
		this.hangLimit = hangLimit;
	}

	/**
	 * 延迟时限
	 * 
	 * 允许最大的延迟时限.
	 * @return short
	 */
	public short getDelayLimit() {
		return this.delayLimit;
	}

	/**
	 * @param delayLimit
	 */
	public void setDelayLimit(short delayLimit) {
		this.delayLimit = delayLimit;
	}

	/**
	 * 暂停次限
	 * 
	 * @return byte
	 */
	public byte getHangTimes() {
		return this.hangTimes;
	}

	/**
	 * @param hangTimes
	 */
	public void setHangTimes(byte hangTimes) {
		this.hangTimes = hangTimes;
	}

	/**
	 * 延迟次限
	 * 
	 * @return byte
	 */
	public byte getDelayTimes() {
		return this.delayTimes;
	}

	/**
	 * @param delayTimes
	 */
	public void setDelayTimes(byte delayTimes) {
		this.delayTimes = delayTimes;
	}

	/**
	 * 注释
	 * 
	 * 说明内容.
	 * @return
	 */
	public String getNote() {
		return this.note;
	}

	/**
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}
}
