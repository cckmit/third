/*
 * $Id: ObjectOrgClerk.java 5520 2012-04-16 07:55:15Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.util.Date;

/**
 * 对象单位人员关系表。
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ObjectOrgClerk extends VersionableBean {
	private static final long serialVersionUID = 4242202773120152105L;
	
//	public static final int TYPE_对象跟业主单位 = 100;
//	public static final int TYPE_对象跟责任单位 = 101;
//	public static final int TYPE_对象跟责任人 = 102;
	
	private int objectType;			//对象类型
	private long objectId;			//对象ID
	private String objectName;		//对象名称
	private Long orgId;				//单位ID
	private String orgName;			//单位名称
	private Long clerk1Id;			//人员1ID
	private String clerk1Name;		//人员1名称
	private Long clerk2Id;			//人员2ID
	private String clerk2Name;		//人员2名称
	private Long clerk3Id;			//人员3ID
	private String clerk3Name;		//人员3名称
	private String accordingTo;		//变更依据
	private Date changeTime;		//变更时间
	private Long recorderId;		//备案人ID
	private String recorderName;	//备案人姓名
	private Long accordingToFileId;	//变更依据文件ID
	private String accordingToFileNo;//变更依据文号
	//private int type;				//关系类型，例如：对象与责任单位之间的关系、对象与业主单位之间的关系

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
