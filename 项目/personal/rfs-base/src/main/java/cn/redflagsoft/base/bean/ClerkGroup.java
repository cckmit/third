/*
 * $Id: ClerkGroup.java 3966 2010-09-08 02:58:12Z lcj $
 * clerkGroup.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import java.util.Date;

import org.springframework.core.Ordered;

/**
 * @author mwx
 *
 */
public class ClerkGroup extends VersionableBean implements Ordered{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2505998455500558436L;
	private Long id;
	private Long groupID;
	private Long clerkID;
	private Date insertTime;
	private Date deleteTime;
	private Integer displayOrder;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * ID
	 * 
	 * 唯一
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the groupID
	 */
	public Long getGroupID() {
		return groupID;
	}
	/**
	 * 群名
	 * 
	 * 默认为0，表示忽略
	 * @param groupID the groupID to set
	 */
	public void setGroupID(Long groupID) {
		this.groupID = groupID;
	}
	/**
	 * @return the clerkID
	 */
	public Long getClerkID() {
		return clerkID;
	}
	/**
	 *职员
	 * @param clerkID the clerkID to set
	 */
	public void setClerkID(Long clerkID) {
		this.clerkID = clerkID;
	}
	/**
	 * @return the insertTime
	 */
	public Date getInsertTime() {
		return insertTime;
	}
	/**
	 *  加入时间
	 * @param insertTime the insertTime to set
	 */
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	/**
	 * @return the deleteTime
	 */
	public Date getDeleteTime() {
		return deleteTime;
	}
	/**
	 * 删除时间
	 * @param deleteTime the deleteTime to set
	 */
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public int getOrder() {
		if(displayOrder != null){
			return displayOrder.intValue();
		}
		return 0;
	}
}
