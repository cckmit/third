/*
 * $Id: Objects.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import org.opoo.ndao.Domain;

/**
 * @author ymq
 */
public class Objects extends VersionableBean implements Domain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8958304981963256829L;
	
	
	public static final int TYPE_业务对象与附件之间关系 = 10001;
	public static final int TYPE_任务与附件之间关系 = 10002;
	public static final int TYPE_工作与附件之间关系 = 10003;
	
	
	private Long sn;
	private Long fstObject;
	private Long sndObject;

	public Objects() {
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
	 * 对象
	 * @return Long
	 */
	public Long getFstObject() {
		return fstObject;
	}

	/**
	 * @param fstObject
	 */
	public void setFstObject(Long fstObject) {
		this.fstObject = fstObject;
	}

	/**
	 * 对象
	 * @return Long
	 */
	public Long getSndObject() {
		return sndObject;
	}

	/**
	 * @param sndObject
	 */
	public void setSndObject(Long sndObject) {
		this.sndObject = sndObject;
	}
}
