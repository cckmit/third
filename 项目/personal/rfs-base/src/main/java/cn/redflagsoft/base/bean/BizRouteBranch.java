/*
 * $Id: BizRouteBranch.java 3996 2010-10-18 06:56:46Z lcj $
 * BizRouteBranch.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * @author Administrator
 *
 */
public class BizRouteBranch extends VersionableBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6727801279706285314L;
	private Long id;
	private Long routeID;
	private Long nodeID;
	private Byte category;
	private int nextNode;
	private Byte defaultValue;
	private byte nextRole;
	private String note;
	
	/**
	 * ID
	 * 
	 * 唯一
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 流程
	 * 
	 * 不可空
	 * @return the routeID
	 */
	public Long getRouteID() {
		return routeID;
	}
	/**
	 * @param routeID the routeID to set
	 */
	public void setRouteID(Long routeID) {
		this.routeID = routeID;
	}
	/**
	 * 节点
	 * @return the nodeID
	 */
	public Long getNodeID() {
		return nodeID;
	}
	/**
	 * @param nodeID the nodeID to set
	 */
	public void setNodeID(Long nodeID) {
		this.nodeID = nodeID;
	}
	/**
	 * 分类
	 * 
	 * 0 强制，1 成功，2 失败，3 取消，4 其他。
	 * @return the category
	 */
	public Byte getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(Byte category) {
		this.category = category;
	}
	/**
	 * 下一节点
	 * 
	 * 默认为0，表示流程结束
	 * @return the nextNode
	 */
	public int getNextNode() {
		return nextNode;
	}
	/**
	 * @param nextNode the nextNode to set
	 */
	public void setNextNode(int nextNode) {
		this.nextNode = nextNode;
	}
	/**
	 * 默认值
	 * 
	 * 默认选项
	 * @return the defaultValue
	 */
	public Byte getDefaultValue() {
		return defaultValue;
	}
	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(Byte defaultValue) {
		this.defaultValue = defaultValue;
	}
	/**
	 * 下一角色
	 * 
	 * 默认为0，表示无。根据流程分类判断
	 * @return the nextRole
	 */
	public byte getNextRole() {
		return nextRole;
	}
	/**
	 * @param nextRole the nextRole to set
	 */
	public void setNextRole(byte nextRole) {
		this.nextRole = nextRole;
	}
	/**
	 * 注释
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	

}
