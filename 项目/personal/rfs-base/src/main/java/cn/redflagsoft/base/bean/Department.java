/*
 * $Id: Department.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import java.io.Serializable;

/**
 * @author Alex Lin
 *
 */
public class Department extends VersionableBean implements LabelDataBean, Observable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6921934325861075878L;
	private Long id = 0L;
	private Long objectID;
	private String code;
	private String name;
	private String abbr;
	private String departmentSN;
	private Long managerID;
	private byte rank =1;
	private short parents;
	private short children;
	private String telNo;
	private String faxNo;
	private String workAddr;
	
	/**
	 * ID
	 * 默认为0，表示本部门。系统唯一
	 * @return the iD
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the iD to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 对象
	 * 对应在Object表中的ID
	 * @return the objectID
	 */
	public Long getObjectID() {
		return objectID;
	}

	/**
	 * @param objectID the objectID to set
	 */
	public void setObjectID(Long objectID) {
		this.objectID = objectID;
	}

	/**
	 * 代码
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param l the long to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 名称
	 * 一般名称在50个汉字之内
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 简称
	 * 一般简称在8个汉字之内
	 * @return the abbr
	 */
	public String getAbbr() {
		return abbr;
	}

	/**
	 * @param abbr the abbr to set
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	/**
	 * 编号
	 * 默认为空
	 * @return the departmentSN
	 */
	public String getDepartmentSN() {
		return departmentSN;
	}

	/**
	 * @param departmentSN the departmentSN to set
	 */
	public void setDepartmentSN(String departmentSN) {
		this.departmentSN = departmentSN;
	}

	/**
	 * 负责人
	 * 职员ID
	 * @return the managerID
	 */
	public Long getManagerID() {
		return managerID;
	}

	/**
	 * @param managerID the managerID to set
	 */
	public void setManagerID(Long managerID) {
		this.managerID = managerID;
	}

	/**
	 * 部门级别
	 * 部门级别，默认为1
	 * @return the rank
	 */
	public byte getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(byte rank) {
		this.rank = rank;
	}

	/**
	 * 上级部门
	 * 上级单位数量，默认为0，表示忽略
	 * @return the parents
	 */
	public short getParents() {
		return parents;
	}

	/**
	 * @param parents the parents to set
	 */
	public void setParents(short parents) {
		this.parents = parents;
	}

	/**
	 * 下级部门
	 * 下级单位数量，默认为0，表示忽略
	 * @return the children
	 */
	public short getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(short children) {
		this.children = children;
	}

	/**
	 * 电话号码
	 * @return the telNo
	 */
	public String getTelNo() {
		return telNo;
	}

	/**
	 * @param telNo the telNo to set
	 */
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	/**
	 * 传真号码
	 * @return the faxNo
	 */
	public String getFaxNo() {
		return faxNo;
	}

	/**
	 * @param faxNo the faxNo to set
	 */
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	/**
	 * 办公地址
	 * @return the workAddr
	 */
	public String getWorkAddr() {
		return workAddr;
	}

	/**
	 * @param workAddr the workAddr to set
	 */
	public void setWorkAddr(String workAddr) {
		this.workAddr = workAddr;
	}

	
	

	public Serializable getData() {
		return getId();
	}

	public String getLabel() {
		return getName();
	}

}
