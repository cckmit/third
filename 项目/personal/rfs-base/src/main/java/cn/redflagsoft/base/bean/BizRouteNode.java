/*
 * $Id: BizRouteNode.java 4615 2011-08-21 07:10:37Z lcj $
 * BizRouteNode.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * @author Administrator
 *
 */
public class BizRouteNode extends VersionableBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4910300172440925136L;
	//����
	public static final byte STATUS_DEFAULT = 0;
	//�ڰ�
	public static final byte STATUS_DOING = 1;
	//
	public static final byte STATUS_PAUSE = 2;
	
	public static final byte STATUS_FINISH = 9;
	
	
	
	private Long id;
	private Long routeID;
	private byte sectNo;
	private byte stepNo;
	private byte seatNo = 1;
	private String dutyer;
	private Long dutyerID;
	private short timeLimit;
	private Integer bizType;
	private String bizName;
	private int nextNode;
	private int lastNode;
	private byte timeUnit;
	/**
	 * @return the timeUnit
	 */
	public byte getTimeUnit() {
		return timeUnit;
	}

	/**
	 * ʱ�䵥λ
	 * 
	 * ���壺0 �ޣ�1 �꣬2 �£�3 �ܣ�4 �� ��5ʱ��6 �֣�7�룬8���롣Ĭ��Ϊ0����ʾ�޶������ԡ�
	 * @param timeUnit the timeUnit to set
	 */
	public void setTimeUnit(byte timeUnit) {
		this.timeUnit = timeUnit;
	}

	
	/**
	 * ID
	 * 
	 * Ψһ
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
	 * ����
	 * 
	 * ���ɿ�
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
	 * ����
	 * 
	 * ���ɿ�
	 * @return the sectNo
	 */
	public byte getSectNo() {
		return sectNo;
	}
	/**
	 * @param sectNo the sectNo to set
	 */
	public void setSectNo(byte sectNo) {
		this.sectNo = sectNo;
	}
	/**
	 * ����
	 * 
	 * ���ɿ�
	 * @return the stepNo
	 */
	public byte getStepNo() {
		return stepNo;
	}
	/**
	 * @param stepNo the stepNo to set
	 */
	public void setStepNo(byte stepNo) {
		this.stepNo = stepNo;
	}
	/**
	 * @return the seatNo
	 */
	public byte getSeatNo() {
		return seatNo;
	}
	/**
	 * λ��
	 * 
	 * Ĭ��Ϊ1
	 * @param seatNo the seatNo to set
	 */
	public void setSeatNo(byte seatNo) {
		this.seatNo = seatNo;
	}
	/**
	 * ҵ��
	 * 
	 * ҵ������
	 * @return the bizType
	 */
	public Integer getBizType() {
		return bizType;
	}
	/**
	 * @param bizType the bizType to set
	 */
	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}
	
	/**
	 * ҵ������
	 * 
	 * @return String
	 */
	public String getBizName() {
		return bizName;
	}
	/**
	 * @param bizName
	 */
	public void setBizName(String bizName) {
		this.bizName = bizName;
	}
	/**
	 * @return the dutyer
	 */
	public String getDutyer() {
		return dutyer;
	}
	/**
	 * ������
	 * @param dutyer the dutyer to set
	 */
	public void setDutyer(String dutyer) {
		this.dutyer = dutyer;
	}	
	/**
	 * ʱ��
	 * @return
	 */
	public short getTimeLimit() {
		return timeLimit;
	}
	/**
	 * @param timeLimit
	 */
	public void setTimeLimit(short timeLimit) {
		this.timeLimit = timeLimit;
	}
	/**
	 * ��һ�ڵ�
	 * 
	 * Ĭ��Ϊ0����ʾ���̽���
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
	 * ��һ�ڵ�
	 * 
	 *  Ĭ��Ϊ0����ʾ���̿�ʼ
	 * @return the lastNode
	 */
	public int getLastNode() {
		return lastNode;
	}
	/**
	 * @param lastNode the lastNode to set
	 */
	public void setLastNode(int lastNode) {
		this.lastNode = lastNode;
	}
	
	public Long getDutyerID() {
		return dutyerID;
	}

	public void setDutyerID(Long dutyerID) {
		this.dutyerID = dutyerID;
	}
}
