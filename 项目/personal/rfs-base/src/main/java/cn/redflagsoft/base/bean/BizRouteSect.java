/*
 * $Id: BizRouteSect.java 3996 2010-10-18 06:56:46Z lcj $
 * BizRouteNode.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import org.opoo.ndao.Domain;

/**
 * 
 * @author ymq
 *
 */
public class BizRouteSect extends VersionableBean implements Domain<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8911797153460813561L;
	public static final Long ROUTEID_PARENT = 1L;
	public static final Long ROUTEID_CHILD = 2L;
	private Long id;
	private Long routeID;
	private byte sectNo;
	private String sectName;
	private byte status;
	
	public BizRouteSect() {
	}

	/**
	 * 唯一标示
	 * 
	 * @return Long
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 流程
	 * 
	 * 不可空
	 * @return Long
	 */
	public Long getRouteID() {
		return routeID;
	}
	
	/**
	 * @param routeID
	 */
	public void setRouteID(Long routeID) {
		this.routeID = routeID;
	}
	
	/**
	 * 段号
	 * 
	 * 不可空
	 * @return byte
	 */
	public byte getSectNo() {
		return sectNo;
	}
	
	/**
	 * @param sectNo
	 */
	public void setSectNo(byte sectNo) {
		this.sectNo = sectNo;
	}
	
	/**
	 * 名称
	 * 
	 * 25个汉字，不能为空.
	 * @return String
	 */
	public String getSectName() {
		return sectName;
	}
	
	/**
	 * @param sectName
	 */
	public void setSectName(String sectName) {
		this.sectName = sectName;
	}
	
	/**
	 * 状态
	 * 
	 * @return byte
	 */
	public byte getStatus() {
		return status;
	}
	
	/**
	 * @param status
	 */
	public void setStatus(byte status) {
		this.status = status;
	}
}
