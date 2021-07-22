/*
 * $Id: ObjectGroup.java 3996 2010-10-18 06:56:46Z lcj $
 * ObjetGroup.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import java.util.Date;

/**
 * @author mwx
 *
 */
public class ObjectGroup extends VersionableBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1986560711161441253L;
	private Long id;
	private short category;
	private Long groupID;
	private Long objectID;
	private Date insertTime;
	private Date deleteTime;
	private Short displayOrder;
	/**
	 * @return the displayOrder
	 */
	public Short getDisplayOrder() {
		return displayOrder;
	}
	/**
	 * 显示顺序
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(Short displayOrder) {
		this.displayOrder = displayOrder;
	}
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
	 * @return the category
	 */
	public short getCategory() {
		return category;
	}
	/**
	 * 分类
	 * 
	 * 对象的分类，默认为0，表示忽略
	 * @param category the category to set
	 */
	public void setCategory(short category) {
		this.category = category;
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
	 * @return the objectID
	 */
	public Long getObjectID() {
		return objectID;
	}
	/**
	 *对象群
	 * @param objectID the objectID to set
	 */
	public void setObjectID(Long objectID) {
		this.objectID = objectID;
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
}
