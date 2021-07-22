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
	
	public static final long ID_������Ա���� = 0;
	public static final long ID_���ز��ŷ��� = -1;
	public static final long ID_���ص�λ���� = -2;
	
	public static final byte CATEGORY_��λ���� = 11;
	public static final byte CATEGORY_���ŷ��� = 12;
	public static final byte CATEGORY_��Ա���� = 13;
	
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
	 * ϵͳΨһ
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
	 * ����
	 * 
	 * Ĭ��Ϊ0����ʾ����
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
	 * ����
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
	 * ���
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
	 * ���Ȩ
	 * 
	 * 0 �ɣ�1 ����
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
	 * ���Ȩ
	 * 
	 *  0 �ɣ�1 ����
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
	 * ɾ��Ȩ
	 * 
	 *  0 �ɣ�1 ����
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
	 * ��Ա��
	 * 
	 * ��Ӧ�ĳ�Ա������Ӧ��ϵ����ϵ��
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
	 * ע��
	 * 
	 * ˵������
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
