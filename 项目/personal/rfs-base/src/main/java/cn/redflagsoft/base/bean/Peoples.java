/*
 * $Id: Peoples.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import org.opoo.ndao.Domain;

/**
 * @author ymq
 */
public class Peoples extends VersionableBean implements Domain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6105632904649012465L;
	public static final int TYPE_RISK_WHITE=101;
	public static final int TYPE_RISK_BLUE =102;
	public static final int TYPE_RISK_YELLOW =103;
	public static final int TYPE_RISK_ORANGE =104;
	public static final int TYPE_RISK_RED = 105;
	public static final int TYPE_RISK_BLACK = 106;

	private Long sn;
	private Long fstPeople;
	private Long sndPeople;
		
	public Peoples() {
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
	 * 人员
	 * @return Long
	 */
	public Long getFstPeople() {
		return fstPeople;
	}

	/**
	 * @param fstPeople
	 */
	public void setFstPeople(Long fstPeople) {
		this.fstPeople = fstPeople;
	}

	/**
	 * 人员
	 * @return Long
	 */
	public Long getSndPeople() {
		return sndPeople;
	}

	/**
	 * @param sndPeople
	 */
	public void setSndPeople(Long sndPeople) {
		this.sndPeople = sndPeople;
	}
}
