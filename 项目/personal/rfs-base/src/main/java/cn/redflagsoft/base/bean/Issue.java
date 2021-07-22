/*
 * $Id: Issue.java 6212 2013-05-17 08:33:04Z thh $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.util.Date;

import org.opoo.apps.util.BitFlag;

import com.googlecode.jsonplugin.annotations.JSON;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.annotation.ObjectType;
import cn.redflagsoft.base.util.CodeMapUtils;

/**
 * 问题。
 * 
 * @author Alex Lin(alex@opoo.org)
 * 
 */
@ObjectType(Issue.OBJECT_TYPE)
public class Issue extends LifeStageableObject {

	public static final short GRADE_一般 = 1;
	public static final short GRADE_紧急 = 2;
	public static final short GRADE_特别紧急 = 3;

	public static final int TYPE_安全 = 1;
	public static final int TYPE_质量 = 2;
	public static final int TYPE_进度 = 3;
	public static final int TYPE_成本 = 4;
	public static final int TYPE_效率 = 5;
	public static final int TYPE_其他 = 0;
	
	public static final int FLAG_项目问题 = 1;
	public static final int FLAG_协调问题 = 2;

	public static final byte STATUS_正常 = 0;
	public static final byte STATUS_待办 = 1;
	public static final byte STATUS_办结= 9;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4800574526675803377L;
	//base中用3位
	public static final short OBJECT_TYPE = ObjectTypes.ISSUE;
	// private Long id; //id
	private short grade; // 问题级别
	// private int type; // 类型
	private String sn; // 编号
	// private String name; // 名称
	private String description; // 描述
	private String progress; // 最新进度
	private Long refObjectId; // 相关对象ID
	private String refObjectName; // 相关对象名称
	private Integer refObjectType; // 相关对象类型
	private Long orgId; // 报告单位ID
	private String orgName; // 报告单位Name
	private Long reporterId; // 报告人ID
	private String reporterName; // 报告人姓名
	private Date reportTime; // 报告时间
	private BitFlag bitFlag = new BitFlag();
	
	private Date belongTime;	//所属月份/所属时间，用于统计。如果是所属月份，则取当月1号。
	
	@JSON(format="yyyy-MM-dd")
	public Date getBelongTime() {
		return belongTime;
	}

	public void setBelongTime(Date belongTime) {
		this.belongTime = belongTime;
	}
	
	// 级别（一般、紧急、加急）、类别（安全、质量、进度、成本、效率、其它）
	/**
	 * 
	 * @return the flags
	 */
	public int getFlags() {
		return bitFlag.intValue();
	}

	/**
	 * @param flags
	 *            the flags to set
	 */
	public void setFlags(int flags) {
		this.bitFlag = new BitFlag(flags);
	}

	// private byte status; // 状态
	// private String remark; // 备注信息
	// private Long creator; // 创建者
	// private Date creationTime; // 创建时间
	// private Long modifier; // 最后修改者
	// private Date modificationTime; // 最后修改时间

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the grade
	 */
	public short getGrade() {
		return grade;
	}

	/**
	 * @param grade
	 *            the grade to set
	 */
	public void setGrade(short grade) {
		this.grade = grade;
	}

	public String getGradeName() {
		return CodeMapUtils.getCodeName(Issue.class, "GRADE", getGrade());
	}

	public String getTypeName() {		
		return CodeMapUtils.getCodeName(Issue.class, "TYPE", getType());
	}

	/**
	 * @return the progress
	 */
	public String getProgress() {
		return progress;
	}

	/**
	 * @param progress
	 *            the progress to set
	 */
	public void setProgress(String progress) {
		this.progress = progress;
	}

	public Long getReporterId() {
		return reporterId;
	}

	public void setReporterId(Long reporterId) {
		this.reporterId = reporterId;
	}

	public String getReporterName() {
		return reporterName;
	}

	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	/**
	 * @return the refObjectId
	 */
	public Long getRefObjectId() {
		return refObjectId;
	}

	/**
	 * @param refObjectId the refObjectId to set
	 */
	public void setRefObjectId(Long refObjectId) {
		this.refObjectId = refObjectId;
	}

	/**
	 * @return the refObjectName
	 */
	public String getRefObjectName() {
		return refObjectName;
	}

	/**
	 * @param refObjectName the refObjectName to set
	 */
	public void setRefObjectName(String refObjectName) {
		this.refObjectName = refObjectName;
	}

	/**
	 * @return the refObjectType
	 */
	public Integer getRefObjectType() {
		return refObjectType;
	}

	/**
	 * @param refObjectType the refObjectType to set
	 */
	public void setRefObjectType(Integer refObjectType) {
		this.refObjectType = refObjectType;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.LifeStageObject#toLifeStage()
	 */
//	@Override
//	public LifeStage toLifeStage() {
//		LifeStage stage = super.toLifeStage();
//		stage.setTypeName(getTypeName());
//		stage.setType(getType());
//		stage.setString0(getGrade() + "");
//		stage.setString1(getGradeName());
//		stage.setRefObjectId(getRefObjectId());
//		stage.setRefObjectName(getRefObjectName());
//		stage.setRefObjectType(getRefObjectType());
//
//		if(description != null){
//			if(description.length() > 150){
//				stage.setName(description.substring(0, 150));
//			}else{
//				stage.setName(description);
//			}
//		}
//		return stage;
//	}
	
	

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.RFSObject#getObjectType()
	 */
	@Override
	public int getObjectType() {
		return OBJECT_TYPE;
	}
}
