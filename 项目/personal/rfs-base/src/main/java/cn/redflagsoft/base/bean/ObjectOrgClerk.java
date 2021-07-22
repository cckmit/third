/*
 * $Id: ObjectOrgClerk.java 5520 2012-04-16 07:55:15Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bean;

import java.util.Date;

/**
 * ����λ��Ա��ϵ��
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ObjectOrgClerk extends VersionableBean {
	private static final long serialVersionUID = 4242202773120152105L;
	
//	public static final int TYPE_�����ҵ����λ = 100;
//	public static final int TYPE_��������ε�λ = 101;
//	public static final int TYPE_����������� = 102;
	
	private int objectType;			//��������
	private long objectId;			//����ID
	private String objectName;		//��������
	private Long orgId;				//��λID
	private String orgName;			//��λ����
	private Long clerk1Id;			//��Ա1ID
	private String clerk1Name;		//��Ա1����
	private Long clerk2Id;			//��Ա2ID
	private String clerk2Name;		//��Ա2����
	private Long clerk3Id;			//��Ա3ID
	private String clerk3Name;		//��Ա3����
	private String accordingTo;		//�������
	private Date changeTime;		//���ʱ��
	private Long recorderId;		//������ID
	private String recorderName;	//����������
	private Long accordingToFileId;	//��������ļ�ID
	private String accordingToFileNo;//��������ĺ�
	//private int type;				//��ϵ���ͣ����磺���������ε�λ֮��Ĺ�ϵ��������ҵ����λ֮��Ĺ�ϵ

	public Long getAccordingToFileId() {
		return accordingToFileId;
	}
	public void setAccordingToFileId(Long accordingToFileId) {
		this.accordingToFileId = accordingToFileId;
	}
	public String getAccordingToFileNo() {
		return accordingToFileNo;
	}
	public void setAccordingToFileNo(String accordingToFileNo) {
		this.accordingToFileNo = accordingToFileNo;
	}
	/**
	 * @return the objectType
	 */
	public int getObjectType() {
		return objectType;
	}
	/**
	 * @param objectType the objectType to set
	 */
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}
	/**
	 * @return the objectId
	 */
	public long getObjectId() {
		return objectId;
	}
	/**
	 * @param objectId the objectId to set
	 */
	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}
	/**
	 * @return the objectName
	 */
	public String getObjectName() {
		return objectName;
	}
	/**
	 * @param objectName the objectName to set
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}
	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return the clerk1Id
	 */
	public Long getClerk1Id() {
		return clerk1Id;
	}
	/**
	 * @param clerk1Id the clerk1Id to set
	 */
	public void setClerk1Id(Long clerk1Id) {
		this.clerk1Id = clerk1Id;
	}
	/**
	 * @return the clerk1Name
	 */
	public String getClerk1Name() {
		return clerk1Name;
	}
	/**
	 * @param clerk1Name the clerk1Name to set
	 */
	public void setClerk1Name(String clerk1Name) {
		this.clerk1Name = clerk1Name;
	}
	/**
	 * @return the clerk2Id
	 */
	public Long getClerk2Id() {
		return clerk2Id;
	}
	/**
	 * @param clerk2Id the clerk2Id to set
	 */
	public void setClerk2Id(Long clerk2Id) {
		this.clerk2Id = clerk2Id;
	}
	/**
	 * @return the clerk2Name
	 */
	public String getClerk2Name() {
		return clerk2Name;
	}
	/**
	 * @param clerk2Name the clerk2Name to set
	 */
	public void setClerk2Name(String clerk2Name) {
		this.clerk2Name = clerk2Name;
	}
	/**
	 * @return the clerk3Id
	 */
	public Long getClerk3Id() {
		return clerk3Id;
	}
	/**
	 * @param clerk3Id the clerk3Id to set
	 */
	public void setClerk3Id(Long clerk3Id) {
		this.clerk3Id = clerk3Id;
	}
	/**
	 * @return the clerk3Name
	 */
	public String getClerk3Name() {
		return clerk3Name;
	}
	/**
	 * @param clerk3Name the clerk3Name to set
	 */
	public void setClerk3Name(String clerk3Name) {
		this.clerk3Name = clerk3Name;
	}
	/**
	 * @return the accordingTo
	 */
	public String getAccordingTo() {
		return accordingTo;
	}
	/**
	 * @param accordingTo the accordingTo to set
	 */
	public void setAccordingTo(String accordingTo) {
		this.accordingTo = accordingTo;
	}
	/**
	 * @return the changeTime
	 */
	public Date getChangeTime() {
		return changeTime;
	}
	/**
	 * @param changeTime the changeTime to set
	 */
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	/**
	 * @return the recorderId
	 */
	public Long getRecorderId() {
		return recorderId;
	}
	/**
	 * @param recorderId the recorderId to set
	 */
	public void setRecorderId(Long recorderId) {
		this.recorderId = recorderId;
	}
	/**
	 * @return the recorderName
	 */
	public String getRecorderName() {
		return recorderName;
	}
	/**
	 * @param recorderName the recorderName to set
	 */
	public void setRecorderName(String recorderName) {
		this.recorderName = recorderName;
	}
}
