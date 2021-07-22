/*
 * $Id: Clerk.java 5228 2011-12-19 03:53:53Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import org.springframework.core.Ordered;



/**
 * 使用继承映射，name，peopleid属性不再有用。
 * 
 * @author Alex Lin
 *
 */
public class Clerk extends People implements LabelDataBean, Ordered{
	private static final long serialVersionUID = -1136769162742774622L;
	
	public static final long CLERK_ID_RFSA = 1L;
	public static final long CLERK_ID_ADMIN = 10L;
	public static final long CLERK_ID_WORKFLOW = 11L;//工作流
	public static final long CLERK_ID_SUPERVISOR = 12L;//电子监察
	public static final long CLERK_ID_SCHEDULER = 13L;//定时任务
	
	
//	private Long id;
	//private Long peopleID;
	private String code;
	private String clerkNo;
	private Long entityID;
	private String entityName;
	private Long departmentID;
	private String departmentName;
	private byte departmentNum;
	private byte postNum;
	private short workNum;
	private short taskNum;
	private int processNum;
	//private String name;
	private String position;
	private int displayOrder = 0;
	private int displayLevel = 0;//显示级别
	
//	/**
//	 * ID
//	 * 
//	 * @return the id
//	 */
//	public Long getId() {
//		return id;
//	}
//
//	/**
//	 * @param id the iD to set
//	 */
//	public void setId(Long id) {
//		this.id = id;
//	}

	/**
	 * 人员
	 * 对应人员的ID，默认为0，表示忽略
	 * @return the peopleID
	 */
	public Long getPeopleID() {
		return getId();
	}

	/**
	 * @param peopleID the peopleID to set
	 */
	public void setPeopleID(Long peopleID) {
		//this.peopleID = peopleID;
		setId(peopleID);
	}

	/**
	 * 代码
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 工号
	 * 在单位的编号
	 * @return the clerkNo
	 */
	public String getClerkNo() {
		return clerkNo;
	}

	/**
	 * @param clerkNo the clerkNo to set
	 */
	public void setClerkNo(String clerkNo) {
		this.clerkNo = clerkNo;
	}

	/**
	 * 直属单位
	 * @return the entityID
	 */
	public Long getEntityID() {
		return entityID;
	}

	/**
	 * @param entityID the entityID to set
	 */
	public void setEntityID(Long entityID) {
		this.entityID = entityID;
	}

	/**
	 * 直属单位
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * @param entityName the entityName to set
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	
	public void setEntityIDLabel(String entityName){
		setEntityName(entityName);
	}

	/**
	 * 直属部门
	 * 所有部门中直属的。默认为0
	 * @return the departmentID
	 */
	public Long getDepartmentID() {
		return departmentID;
	}

	/**
	 * @param departmentID the departmentID to set
	 */
	public void setDepartmentID(Long departmentID) {
		this.departmentID = departmentID;
	}

	/**
	 * 直属部门
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * 部门数
	 * 当前供职的部门，默认为0
	 * @return the departmentNum
	 */
	public byte getDepartmentNum() {
		return departmentNum;
	}

	/**
	 * @param departmentNum the departmentNum to set
	 */
	public void setDepartmentNum(byte departmentNum) {
		this.departmentNum = departmentNum;
	}

	/**
	 * 岗位数
	 * 当前有授权的岗位数量，默认为0
	 * @return the postNum
	 */
	public byte getPostNum() {
		return postNum;
	}

	/**
	 * @param postNum the postNum to set
	 */
	public void setPostNum(byte postNum) {
		this.postNum = postNum;
	}

	/**
	 * 工作数
	 * 相关的工作数量，默认为0
	 * @return the workNum
	 */
	public short getWorkNum() {
		return workNum;
	}

	/**
	 * @param workNum the workNum to set
	 */
	public void setWorkNum(short workNum) {
		this.workNum = workNum;
	}

	/**
	 * 任务数
	 * 相关的任务数量，默认为0
	 * @return the taskNum
	 */
	public short getTaskNum() {
		return taskNum;
	}

	/**
	 * @param taskNum the taskNum to set
	 */
	public void setTaskNum(short taskNum) {
		this.taskNum = taskNum;
	}

	/**
	 * 事物数
	 * 相关的实物数量，默认为0
	 * @return the processNum
	 */
	public int getProcessNum() {
		return processNum;
	}

	/**
	 * @param processNum the processNum to set
	 */
	public void setProcessNum(int processNum) {
		this.processNum = processNum;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	

//	/* (non-Javadoc)
//	 * @see cn.redflagsoft.base.bean.LabelDataBean#getData()
//	 */
//	public Serializable getData() {
//		return getKey();
//	}
//
//	/* (non-Javadoc)
//	 * @see cn.redflagsoft.base.bean.LabelDataBean#getLabel()
//	 */
//	public String getLabel() {
//		return getName();
//	}

//	/**
//	 * @return the name
//	 */
//	public String getName() {
//		return name;
//	}
//
//	/**
//	 * @param name the name to set
//	 */
//	public void setName(String name) {
//		this.name = name;
//	}

//	public Long getKey() {
//		return getId();
//	}
//
//	public void setKey(Long id) {
//		setId(id);
//	} 
	
	public int getOrder() {
		return displayOrder;
	}

	/**
	 * @return the displayLevel
	 */
	public int getDisplayLevel() {
		return displayLevel;
	}

	/**
	 * @param displayLevel the displayLevel to set
	 */
	public void setDisplayLevel(int displayLevel) {
		this.displayLevel = displayLevel;
	}
}
