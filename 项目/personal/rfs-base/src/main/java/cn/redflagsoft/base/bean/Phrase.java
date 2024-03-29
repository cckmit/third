/*
 * $Id: Phrase.java 4681 2011-09-14 02:42:15Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import java.io.Serializable;

import org.opoo.ndao.Domain;

/**
 * @author ymq
 */
public class Phrase extends VersionableBean implements Domain<Long>,LabelDataBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3654699652395442349L;
	private Long sn;
	private byte category;
	private int taskType;
	private int workType;
	private int processType;
	private short clerkID;
	private String title;
	private String content;
	private byte selected;
	private byte displayOrder;
	private short tag;
	//业务操作，定义见MatterAffair
	private byte bizAction = MatterAffair.ACTION_UNKOWN;

	public Phrase() {
	}

	/**
	 * 唯一标示
	 * 
	 * @return Long
	 */
	public Long getId() {
		return getSn();
	}
	
	/**
	 * @param id
	 */
	public void setId(Long id) {
		setSn(id);
	}

	/**
	 * 唯一标示
	 * 
	 * @return Long
	 */
	public Long getSn() {
		return sn;
	}

	/**
	 * @param sn
	 */
	public void setSn(Long sn) {
		this.sn = sn;
	}

	/**
	 * @return the category
	 */
	public byte getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
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
	public short getClerkID() {
		return clerkID;
	}

	/**
	 * @param clerkID
	 */
	public void setClerkID(short clerkID) {
		this.clerkID = clerkID;
	}

	
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 内容
	 * 
	 * 具体内容.
	 * @return String
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 默选
	 * 
	 * 1 选用,0 未选.
	 * @return byte
	 */
	public byte getSelected() {
		return selected;
	}

	/**
	 * @param selected
	 */
	public void setSelected(byte selected) {
		this.selected = selected;
	}

	/**
	 * 顺序
	 * 
	 * 现实顺序,默认为0,表示忽略.
	 * @return byte
	 */
	public byte getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param displayOrder
	 */
	public void setDisplayOrder(byte displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Serializable getData() {
		return getId();
	}

	public String getLabel() {
		return getTitle();
	}

	public short getTag() {
		return tag;
	}

	public void setTag(short tag) {
		this.tag = tag;
	}

	/**
	 * @return the bizAction
	 */
	public byte getBizAction() {
		return bizAction;
	}

	/**
	 * @param bizAction the bizAction to set
	 */
	public void setBizAction(byte bizAction) {
		this.bizAction = bizAction;
	}
	
}
