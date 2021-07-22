/*
 * $Id: Entities.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import org.opoo.ndao.Domain;

/**
 * @author ymq
 */
public class Entities extends VersionableBean implements Domain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2783414843225689874L;
	private Long sn;
	private Long fstOrg;
	private Long sndOrg;

	public Entities() {
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
	 * 单位
	 * 
	 * @return Long
	 */
	public Long getFstOrg() {
		return fstOrg;
	}

	/**
	 * @param fstOrg
	 */
	public void setFstOrg(Long fstOrg) {
		this.fstOrg = fstOrg;
	}

	/**
	 * 单位
	 * 
	 * @return Long
	 */
	public Long getSndOrg() {
		return sndOrg;
	}

	/**
	 * @param sndOrg
	 */
	public void setSndOrg(Long sndOrg) {
		this.sndOrg = sndOrg;
	}
}
