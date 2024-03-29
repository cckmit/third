/*
 * $Id: ObjectPeopleEntity.java 3996 2010-10-18 06:56:46Z lcj $
 * ObjectPeopleEntity.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * @author Administrator
 *
 */
public class ObjectPeopleEntity extends VersionableBean implements Observable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4450796757179946521L;
	private Long sn;
	private Long objectID;
	private Long peopleID;
	private Long entityID;
	
	public Long getId(){
		return getSn();
	}
	
	public void setId(Long id){
		setId(id);
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
	 * @return the peopleID
	 */
	public Long getPeopleID() {
		return peopleID;
	}
	/**
	 * 人员
	 * @param peopleID the peopleID to set
	 */
	public void setPeopleID(Long peopleID) {
		this.peopleID = peopleID;
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
}
