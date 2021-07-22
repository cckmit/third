/*
 * $Id: ObjectData.java 4615 2011-08-21 07:10:37Z lcj $
 * ObjectData.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * @author mwx
 *
 */
public class ObjectData extends VersionableBean implements Observable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6458913708531329443L;
	private Long sn;
	private Long objectID;
	private Long datumID;
	private Long datumCategoryID;//datumCategoryID
	private byte displayOrder;
	private Integer createTask;
	private Integer modificationTask;
	private Integer objectType = 0;//0ʱ����
	
	public Long getId(){
		return getSn();
	}
	
	public void setId(Long id){
		setSn(id);
	}
	
	/**
	 * @return the sn
	 */
	public Long getSn() {
		return sn;
	}

	/**
	 * ���
	 * 
	 * Ψһ
	 * @param sn the sn to set
	 */
	public void setSn(Long sn) {
		this.sn = sn;
	}

	/**
	 * @return the datumID
	 */
	public Long getDatumID() {
		return datumID;
	}

	/**
	 * ����
	 * @param datumID the datumID to set
	 */
	public void setDatumID(Long datumID) {
		this.datumID = datumID;
	}

	


	/**
	 * @return the displayOrder
	 */
	public byte getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * ˳��
	 * 
	 * Ĭ��Ϊ0����ʾ����
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(byte displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * @return the createTask
	 */
	public Integer getCreateTask() {
		return createTask;
	}

	/**
	 * ��������
	 * @param createTask the createTask to set
	 */
	public void setCreateTask(Integer createTask) {
		this.createTask = createTask;
	}

	/**
	 * @return the modificationTask
	 */
	public Integer getModificationTask() {
		return modificationTask;
	}

	/**
	 * �޶�����
	 * @param modificationTask the modificationTask to set
	 */
	public void setModificationTask(Integer modificationTask) {
		this.modificationTask = modificationTask;
	}

	public Long getObjectID() {
		return objectID;
	}

	public void setObjectID(Long objectID) {
		this.objectID = objectID; 
	}

	/**
	 * @return the datumCategoryID
	 */
	public Long getDatumCategoryID() {
		return datumCategoryID;
	}

	/**
	 * @param datumCategoryID the datumCategoryID to set
	 */
	public void setDatumCategoryID(Long datumCategoryID) {
		this.datumCategoryID = datumCategoryID;
	}

	/**
	 * @return the objectType
	 */
	public Integer getObjectType() {
		return objectType;
	}

	/**
	 * @param objectType the objectType to set
	 */
	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}
}
