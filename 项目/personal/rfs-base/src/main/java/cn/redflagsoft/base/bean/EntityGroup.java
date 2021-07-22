/*
 * $Id: EntityGroup.java 3966 2010-09-08 02:58:12Z lcj $
 * EntityGroup.java
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
public class EntityGroup extends VersionableBean implements Ordered{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4937490362343485018L;
	
	private Long id;
	private Long groupID;
	private Long entityID;
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
	 * ��ʾ˳��
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
	 * Ψһ
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
	 * Ⱥ��
	 * 
	 * 1 ����λ��2 ���ε�λ��3 �ල��λ��4 �쵼��λ�����������塣Ĭ��Ϊ0����ʾ����
	 * @param groupID the groupID to set
	 */
	public void setGroupID(Long groupID) {
		this.groupID = groupID;
	}
	/**
	 * @return the entityID
	 */
	public Long getEntityID() {
		return entityID;
	}
	/**
	 *��λȺ
	 * @param entityID the entityID to set
	 */
	public void setEntityID(Long entityID) {
		this.entityID = entityID;
	}
	/**
	 * @return the insertTime
	 */
	public Date getInsertTime() {
		return insertTime;
	}
	/**
	 *  ����ʱ��
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
	 * ɾ��ʱ��
	 * @param deleteTime the deleteTime to set
	 */
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	
	public int getOrder() {
		if(displayOrder != null){
			return displayOrder.intValue();
		}
		return 0;
	}
}
