/*
 * $Id: RFSSystem.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import org.opoo.ndao.Domain;

/**
 * @author Alex Lin
 *
 */
public class RFSSystem extends VersionableBean implements Observable, Domain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3272745708529604460L;
	private Long id;
	private Long objectID;
	private String code;
	private String name;
	private String abbr;
	private byte rank;
	private Long entityID;
	private String manager;
	private String contact;
	private String telNo;
	private String faxNo;
	private String systemAddr;
	private short parents;
	private short children;
	
	public RFSSystem() {
	}

	/**
	 * 唯一标示
	 * 
	 * @return Long
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 对象
	 * 
	 * @return Long
	 */
	public Long getObjectID() {
		return objectID;
	}

	/**
	 * @param id
	 */
	public void setObjectID(Long objectID) {
		this.objectID = objectID;
	}

	/**
	 * 代码
	 * 
	 * @return String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 名称
	 * 
	 * 一般名称在50个汉字之内.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 简称
	 * 
	 * 一般简称在8个汉字之内.
	 * @return String
	 */
	public String getAbbr() {
		return abbr;
	}

	/**
	 * @param abbr
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	/**
	 * 系统级别
	 * 
	 * 系统级别,默认为1.
	 * @return byte
	 */
	public byte getRank() {
		return rank;
	}

	/**
	 * @param rank
	 */
	public void setRank(byte rank) {
		this.rank = rank;
	}

	/**
	 * 所有单位
	 * 
	 * @return Long
	 */
	public Long getEntityID() {
		return entityID;
	}

	/**
	 * @param entityID
	 */
	public void setEntityID(Long entityID) {
		this.entityID = entityID;
	}

	/**
	 * 负责人
	 * 
	 * 一般法人名字在15个汉字之内.
	 * @return String
	 */
	public String getManager() {
		return manager;
	}

	/**
	 * @param managerID
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}

	/**
	 * 联系人
	 * 
	 * 一般法人名字在15个汉字之内.
	 * @return String
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contactID
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * 电话号码
	 * 
	 * @return String
	 */
	public String getTelNo() {
		return telNo;
	}

	/**
	 * @param telNo
	 */
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	/**
	 * 传真号码
	 * 
	 * @return String
	 */
	public String getFaxNo() {
		return faxNo;
	}

	/**
	 * @param faxNo
	 */
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	/**
	 * 网络地址
	 * 
	 * 系统IP或其它地址.
	 * @return String
	 */
	public String getSystemAddr() {
		return systemAddr;
	}

	/**
	 * @param systemAddr
	 */
	public void setSystemAddr(String systemAddr) {
		this.systemAddr = systemAddr;
	}

	/**
	 * 上级系统
	 * 
	 * 上级系统数量,默认为0,表示忽略.
	 * @return short
	 */
	public short getParents() {
		return parents;
	}

	/**
	 * @param parents
	 */
	public void setParents(short parents) {
		this.parents = parents;
	}

	/**
	 * 下级系统
	 * 
	 * 下级系统数量,默认为0,表示忽略.
	 * @return short
	 */
	public short getChildren() {
		return children;
	}

	/**
	 * @param children
	 */
	public void setChildren(short children) {
		this.children = children;
	}
}