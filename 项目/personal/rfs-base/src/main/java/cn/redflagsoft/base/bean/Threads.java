/*
 * $Id: Threads.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * 
 * @author ymq
 *
 */
public class Threads extends VersionableBean implements org.opoo.ndao.Domain<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6065978095189853645L;
	private Long sn;
	private Long fstThread;
	private Long sndThread;

	public Threads() {
	}

	/**
	 * @return Long
	 */
	public Long getId() {
		return getSn();
	}

	/**
	 * @param Long
	 */
	public void setId(Long sn) {
		setSn(sn);
	}

	/**
	 * SN
	 * 
	 * 唯一标示
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
	 * 事件
	 * 
	 * @return Long
	 */
	public Long getFstThread() {
		return fstThread;
	}

	/**
	 * @param fstThread
	 */
	public void setFstThread(Long fstThread) {
		this.fstThread = fstThread;
	}

	/**
	 * 事件
	 * 
	 * @return Long
	 */
	public Long getSndThread() {
		return sndThread;
	}

	/**
	 * @param sndThread
	 */
	public void setSndThread(Long sndThread) {
		this.sndThread = sndThread;
	}
}
