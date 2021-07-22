/*
 * $Id: Issue.java 6212 2013-05-17 08:33:04Z thh $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bean;

import java.util.Date;

import org.opoo.apps.util.BitFlag;

import com.googlecode.jsonplugin.annotations.JSON;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.annotation.ObjectType;
import cn.redflagsoft.base.util.CodeMapUtils;

/**
 * ���⡣
 * 
 * @author Alex Lin(alex@opoo.org)
 * 
 */
@ObjectType(Issue.OBJECT_TYPE)
public class Issue extends LifeStageableObject {

	public static final short GRADE_һ�� = 1;
	public static final short GRADE_���� = 2;
	public static final short GRADE_�ر���� = 3;

	public static final int TYPE_��ȫ = 1;
	public static final int TYPE_���� = 2;
	public static final int TYPE_���� = 3;
	public static final int TYPE_�ɱ� = 4;
	public static final int TYPE_Ч�� = 5;
	public static final int TYPE_���� = 0;
	
	public static final int FLAG_��Ŀ���� = 1;
	public static final int FLAG_Э������ = 2;

	public static final byte STATUS_���� = 0;
	public static final byte STATUS_���� = 1;
	public static final byte STATUS_���= 9;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4800574526675803377L;
	//base����3λ
	public static final short OBJECT_TYPE = ObjectTypes.ISSUE;
	// private Long id; //id
	private short grade; // ���⼶��
	// private int type; // ����
	private String sn; // ���
	// private String name; // ����
	private String description; // ����
	private String progress; // ���½���
	private Long refObjectId; // ��ض���ID
	private String refObjectName; // ��ض�������
	private Integer refObjectType; // ��ض�������
	private Long orgId; // ���浥λID
	private String orgName; // ���浥λName
	private Long reporterId; // ������ID
	private String reporterName; // ����������
	private Date reportTime; // ����ʱ��
	private BitFlag bitFlag = new BitFlag();
	
	private Date belongTime;	//�����·�/����ʱ�䣬����ͳ�ơ�����������·ݣ���ȡ����1�š�
	
	@JSON(format="yyyy-MM-dd")
	public Date getBelongTime() {
		return belongTime;
	}

	public void setBelongTime(Date belongTime) {
		this.belongTime = belongTime;
	}
	
	// ����һ�㡢�������Ӽ�������𣨰�ȫ�����������ȡ��ɱ���Ч�ʡ�������
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

	// private byte status; // ״̬
	// private String remark; // ��ע��Ϣ
	// private Long creator; // ������
	// private Date creationTime; // ����ʱ��
	// private Long modifier; // ����޸���
	// private Date modificationTime; // ����޸�ʱ��

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
