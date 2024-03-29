/*
 * $Id: PeopleGroup.java 3996 2010-10-18 06:56:46Z lcj $
 * PeopleGroup.java
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
public class PeopleGroup extends VersionableBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4945069080094496093L;
	private Long id;
	private Long groupID;
	private Long peopleID;
	private Short displayOrder;
	
	private Date insertTime;
	private Date deleteTime;
	
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
	 * @return the groupID
	 */
	public Long getGroupID() {
		return groupID;
	}
	/**
	 * 群名
	 * 
	 * 1 申请人；2 经办人；3 代理人；4 承办人；其他见定义，默认为0，表示忽略
	 * @param groupID the groupID to set
	 */
	public void setGroupID(Long groupID) {
		this.groupID = groupID;
	}
	/**
	 * @return the peopleID
	 */
	public Long getPeopleID() {
		return peopleID;
	}
	/**
	 *人员群
	 * @param peopleID the peopleID to set
	 */
	public void setPeopleID(Long peopleID) {
		this.peopleID = peopleID;
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
