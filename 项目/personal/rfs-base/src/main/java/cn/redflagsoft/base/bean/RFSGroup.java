/*
 * $Id: RFSGroup.java 4341 2011-04-22 02:12:39Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import org.springframework.core.Ordered;

/**
 * @author mwx
 *
 */
public class RFSGroup extends VersionableBean implements Ordered{
	
	public static final long ID_隐藏人员分组 = 0;
	public static final long ID_隐藏部门分组 = -1;
	public static final long ID_隐藏单位分组 = -2;
	
	public static final byte CATEGORY_单位分组 = 11;
	public static final byte CATEGORY_部门分组 = 12;
	public static final byte CATEGORY_人员分组 = 13;
	
	public static final Byte DELETE_ENABLED = 1;
	public static final Byte INSERT_ENABLED = 1;
	public static final Byte UPDATE_ENABLED = 1;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2316733783366035938L;
	private Long id;
	private byte category;
	private String name;
	private String abbr;
	private Byte canInsert = 0;
	private Byte canUpdate = 0;
	private Byte canDelete = 0;
	private Short memberNum = 0;
	private String note;
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
	 * 系统唯一
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the category
	 */
	public byte getCategory() {
		return category;
	}
	/**
	 * 分类
	 * 
	 * 默认为0，表示忽略
	 * @param category the category to set
	 */
	public void setCategory(byte category) {
		this.category = category;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 名称
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the abbr
	 */
	public String getAbbr() {
		return abbr;
	}
	/**
	 * 简称
	 * @param abbr the abbr to set
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	/**
	 * @return the canInsert
	 */
	public Byte getCanInsert() {
		return canInsert;
	}
	/**
	 * 添加权
	 * 
	 * 0 可，1 不可
	 * @param canInsert the canInsert to set
	 */
	public void setCanInsert(Byte canInsert) {
		this.canInsert = canInsert;
	}
	/**
	 * @return the canUpdate
	 */
	public Byte getCanUpdate() {
		return canUpdate;
	}
	/**
	 * 变更权
	 * 
	 *  0 可，1 不可
	 * @param canUpdate the canUpdate to set
	 */
	public void setCanUpdate(Byte canUpdate) {
		this.canUpdate = canUpdate;
	}
	/**
	 * @return the canDelete
	 */
	public Byte getCanDelete() {
		return canDelete;
	}
	/**
	 * 删除权
	 * 
	 *  0 可，1 不可
	 * @param canDelete the canDelete to set
	 */
	public void setCanDelete(Byte canDelete) {
		this.canDelete = canDelete;
	}
	/**
	 * @return the memberNum
	 */
	public Short getMemberNum() {
		return memberNum;
	}
	/**
	 * 成员数
	 * 
	 * 对应的成员数，对应关系见关系表
	 * @param memberNum the memberNum to set
	 */
	public void setMemberNum(Short memberNum) {
		this.memberNum = memberNum;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 注释
	 * 
	 * 说明内容
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
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
