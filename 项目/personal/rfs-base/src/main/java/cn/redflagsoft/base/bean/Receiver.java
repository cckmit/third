/*
 * $Id: Receiver.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import org.opoo.ndao.Domain;

/**
 * @author ymq
 */
public class Receiver extends VersionableBean implements Domain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2898447717386996690L;
	
	public static byte	 CATEGORY_CLERK=1;
	public static byte  CATEGORY_DEPART=3;
	public static byte  CATEGORY_ENTITY=4;
	private Long id;
	private byte category;
	private int taskType;
	private int workType;
	private int processType;
	private Long clerkID;
	private Long receiver;
	private byte selected;

	public Receiver() {
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
	 * 分类
	 * 
	 * 1 个人,2 岗位, 3 部门, 4 单位, 5 系统
	 * 1：clerkid；2：postid；3：departid；4：entityid；5：systemid。
	 * @return byte
	 */
	public byte getCategory() {
		return category;
	}

	/**
	 * @param category
	 */
	public void setCategory(byte category) {
		this.category = category;
	}
	
	

	/**
	 * @return the taskType
	 */
	public int getTaskType() {
		return taskType;
	}

	/**
	 * @param taskType the taskType to set
	 */
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	/**
	 * @return the workType
	 */
	public int getWorkType() {
		return workType;
	}

	/**
	 * @param workType the workType to set
	 */
	public void setWorkType(int workType) {
		this.workType = workType;
	}

	/**
	 * 事务
	 * 
	 * 默认为0,表示忽略.
	 * @return short
	 */
	public int getProcessType() {
		return processType;
	}

	/**
	 * @param processClass
	 */
	public void setProcessType(int processType) {
		this.processType = processType;
	}

	/**
	 * 职位
	 * 
	 * 默认为0,表示公用.
	 * @return short
	 */
	public Long getClerkID() {
		return clerkID;
	}

	/**
	 * @param clerkID
	 */
	public void setClerkID(Long clerkID) {
		this.clerkID = clerkID;
	}

	/**
	 * 接收人
	 * 
	 * 具体内容.
	 * @return short
	 */
	public Long getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver
	 */
	public void setReceiver(Long receiver) {
		this.receiver = receiver;
	}

	/**
	 * 默选
	 * 
	 * 1 选择,0 未选
	 * @return byte
	 */
	public byte getSelected() {
		return selected;
	}

	public void setSelected(byte selected) {
		this.selected = selected;
	}
}
