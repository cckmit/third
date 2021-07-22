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

	/**
	 * Ψһ��ʾ
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
	 * ����
	 * 
	 * ����:1 ��ҵ,2 ����,3 ����.
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
	 * ҵ��
	 * 
	 * ����ID,Ĭ��Ϊ0,��ʾ����.
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
	 * ʱ�䵥λ
	 * 
	 * ʱ�䵥λ
	 * ����: 0 ��, 1 ��, 2 ��, 3 ��, 4 ��, 5 ʱ, 6 ��, 7 ��, 8 ����, 9 ��
	 * Ĭ��Ϊ0,��ʾ�޶�������.
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
	 * ʱ��
	 * 
	 * ��ŵ�����ʱ��.
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
	 * ��ͣʱ��
	 * 
	 * ����������ͣʱ��.
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
	 * �ӳ�ʱ��
	 * 
	 * ���������ӳ�ʱ��.
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
	 * ��ͣ����
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
	 * �ӳٴ���
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
	 * ע��
	 * 
	 * ˵������.
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
