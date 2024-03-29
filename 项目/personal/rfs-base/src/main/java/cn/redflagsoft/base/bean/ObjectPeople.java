/*
 * $Id: ObjectPeople.java 4615 2011-08-21 07:10:37Z lcj $
 * ObjectPeople.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * 业务对象人员之间的关系。
 *  
 *
 */
public class ObjectPeople extends VersionableBean implements Observable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6383774613298962251L;
	
	/**
	 * 关注的对象。
	 */
	public static final int TYPE_FOCUS = 100;
	
	private Long sn;
	private int objectType;
	private Long objectID;
	private Long peopleID;
	
	/**
	 * 业务对象的类型。
	 * @return
	 */
	public int getObjectType() {
		return objectType;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

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
}
