/*
 * $Id: PeopleEntity.java 3996 2010-10-18 06:56:46Z lcj $
 * PeopleEntity.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * @author Administrator
 *
 */
public class PeopleEntity extends VersionableBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9047047744263135847L;
	private Long sn;
	private Long peopleID;
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
