/*
 * $Id: Tasks.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import org.opoo.ndao.Domain;

/**
 * @author ymq
 */
public class Tasks extends VersionableBean implements Domain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6224875346062965165L;
	private Long sn;
	private Long fstTask;
	private Long sndTask;

	public Tasks() {
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
	 * 业务
	 * @return Long
	 */
	public Long getFstTask() {
		return this.fstTask;
	}

	/**
	 * @param fstTask
	 */
	public void setFstTask(Long fstTask) {
		this.fstTask = fstTask;
	}

	/**
	 * 业务
	 * @return Long
	 */
	public Long getSndTask() {
		return this.sndTask;
	}

	/**
	 * @param sndTask
	 */
	public void setSndTask(Long sndTask) {
		this.sndTask = sndTask;
	}
}
