/*
 * $Id: ObjectEntity.java 4615 2011-08-21 07:10:37Z lcj $
 * ObjectEinty.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * @author mwx
 *
 */
public class ObjectEntity extends VersionableBean implements Observable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2007435331993582026L;
	
	public static short KEY_PROJECT_TYPE=200;
	private Long sn;
	private int objectType;
	private Long objectID;
	private Long entityID;
	
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
	 * 编号
	 * 
	 * 唯一
	 * @param sn the sn to set
	 */
	public void setSn(Long sn) {
		this.sn = sn;
	}
	/**
	 * @return the objectID
	 */
	public Long getObjectID() {
		return objectID;
	}
	/**
	 * 对象
	 * @param objectID the objectID to set
	 */
	public void setObjectID(Long objectID) {
		this.objectID = objectID;
	}
	/**
	 * @return the entityID
	 */
	public Long getEntityID() {
		return entityID;
	}
	/**
	 * 单位
	 * @param entityID the entityID to set
	 */
	public void setEntityID(Long entityID) {
		this.entityID = entityID;
	}

	public int getObjectType() {
		return objectType;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}
	
	
}
