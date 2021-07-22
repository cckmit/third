/*
 * $Id: BizDutyersConfig.java 6095 2012-11-06 01:07:54Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bean;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class BizDutyersConfig extends VersionableBean implements DutyersInfo{
	private static final long serialVersionUID = -4260291948663519426L;

	/**
	 *   ҵ����Ķ������ͣ�ֵȡ��ObjectTypes��
	 */
	private int defObjectType;
	
	/**
	 * ҵ�����ID��ʵ����taskType����workType��ֵ
	 */
	private int defId;
	
	/**
	 * ˳��
	 */
	private int displayOrder;
	
	/**
	 * ����
	 */
	private int type;

	private Long dutyEntityID;
	private String dutyEntityName;// ���ε�λ
	
	private Long dutyDepartmentID;
	private String dutyDepartmentName;//���β���
	
	private Long dutyerID;
	private String dutyerName;// ������
	
	private Long dutyerLeader1Id;
	private String dutyerLeader1Name;// ��������
	
	private Long dutyerLeader2Id;
	private String dutyerLeader2Name;// �ֹ��쵼
	
	
	/**
	 * @return the defObjectType
	 */
	public int getDefObjectType() {
		return defObjectType;
	}
	/**
	 * @param defObjectType the defObjectType to set
	 */
	public void setDefObjectType(int defObjectType) {
		this.defObjectType = defObjectType;
	}
	/**
	 * @return the defId
	 */
	public int getDefId() {
		return defId;
	}
	/**
	 * @param defId the defId to set
	 */
	public void setDefId(int defId) {
		this.defId = defId;
	}
	/**
	 * @return the displayOrder
	 */
	public int getDisplayOrder() {
		return displayOrder;
	}
	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the dutyEntityID
	 */
	public Long getDutyEntityID() {
		return dutyEntityID;
	}
	/**
	 * @param dutyEntityID the dutyEntityID to set
	 */
	public void setDutyEntityID(Long dutyEntityID) {
		this.dutyEntityID = dutyEntityID;
	}
	/**
	 * @return the dutyEntityName
	 */
	public String getDutyEntityName() {
		return dutyEntityName;
	}
	/**
	 * @param dutyEntityName the dutyEntityName to set
	 */
	public void setDutyEntityName(String dutyEntityName) {
		this.dutyEntityName = dutyEntityName;
	}
	/**
	 * @return the dutyDepartmentID
	 */
	public Long getDutyDepartmentID() {
		return dutyDepartmentID;
	}
	/**
	 * @param dutyDepartmentID the dutyDepartmentID to set
	 */
	public void setDutyDepartmentID(Long dutyDepartmentID) {
		this.dutyDepartmentID = dutyDepartmentID;
	}
	/**
	 * @return the dutyDepartmentName
	 */
	public String getDutyDepartmentName() {
		return dutyDepartmentName;
	}
	/**
	 * @param dutyDepartmentName the dutyDepartmentName to set
	 */
	public void setDutyDepartmentName(String dutyDepartmentName) {
		this.dutyDepartmentName = dutyDepartmentName;
	}
	/**
	 * @return the dutyerID
	 */
	public Long getDutyerID() {
		return dutyerID;
	}
	/**
	 * @param dutyerID the dutyerID to set
	 */
	public void setDutyerID(Long dutyerID) {
		this.dutyerID = dutyerID;
	}
	/**
	 * @return the dutyerName
	 */
	public String getDutyerName() {
		return dutyerName;
	}
	/**
	 * @param dutyerName the dutyerName to set
	 */
	public void setDutyerName(String dutyerName) {
		this.dutyerName = dutyerName;
	}
	/**
	 * @return the dutyerLeader1Id
	 */
	public Long getDutyerLeader1Id() {
		return dutyerLeader1Id;
	}
	/**
	 * @param dutyerLeader1Id the dutyerLeader1Id to set
	 */
	public void setDutyerLeader1Id(Long dutyerLeader1Id) {
		this.dutyerLeader1Id = dutyerLeader1Id;
	}
	/**
	 * @return the dutyerLeader1Name
	 */
	public String getDutyerLeader1Name() {
		return dutyerLeader1Name;
	}
	/**
	 * @param dutyerLeader1Name the dutyerLeader1Name to set
	 */
	public void setDutyerLeader1Name(String dutyerLeader1Name) {
		this.dutyerLeader1Name = dutyerLeader1Name;
	}
	/**
	 * @return the dutyerLeader2Id
	 */
	public Long getDutyerLeader2Id() {
		return dutyerLeader2Id;
	}
	/**
	 * @param dutyerLeader2Id the dutyerLeader2Id to set
	 */
	public void setDutyerLeader2Id(Long dutyerLeader2Id) {
		this.dutyerLeader2Id = dutyerLeader2Id;
	}
	/**
	 * @return the dutyerLeader2Name
	 */
	public String getDutyerLeader2Name() {
		return dutyerLeader2Name;
	}
	/**
	 * @param dutyerLeader2Name the dutyerLeader2Name to set
	 */
	public void setDutyerLeader2Name(String dutyerLeader2Name) {
		this.dutyerLeader2Name = dutyerLeader2Name;
	}
}
	