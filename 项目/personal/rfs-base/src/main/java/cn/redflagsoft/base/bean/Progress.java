/*
 * $Id: Progress.java 6156 2012-12-25 02:10:00Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.opoo.apps.util.BitFlag;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.annotation.DisplayName;
import cn.redflagsoft.base.util.CodeMapUtils;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * ����������ȡ�
 * 
 * @author Alex Lin(alex@opoo.org)
 * 
 */
public class Progress extends VersionableBean implements RFSEntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5614793178634133312L;

	public static final int HAS_ATTACHMENT = 1;// Math.pow(2, 0)
	public static final int HAS_IMAGE_FLAG = 2;// Math.pow(2, 1)
	public static final int HAS_VIDEO_FLAG = 4;// Math.pow(2, 2)

	
	@DisplayName("Ĭ��")
	public static final byte STATUS_DEFAULT = 0;
	@DisplayName("����")
	public static final byte STATUS_DRAFT = 1;
	@DisplayName("�ѱ�")
	public static final byte STATUS_OK = 9; 
	@DisplayName("����")
	public static final byte STATUS_INVALID = 11;//����
	
	
	// private Long id;

	private int refObjectType;//��ض�������

	// private Integer type;
	private Long refObjectId;// ��ض���ID
	private String refObjectName;// ��ض�������
	private String title;// ����
	private String description; // �����������
	private String comment; // ��ע
	private BigDecimal periodProgress;// �׶��Խ��ȣ��磺�������Ͷ�ʣ��������������
	private BigDecimal monthProgress;// �¶��ۼƽ���
	private BigDecimal quarterProgress;// �����ۼƽ���
	private BigDecimal yearProgress;// ����ۼƽ��ȣ�������ۼ����Ͷ��
	private BigDecimal progress; // ���ȣ��ۼƽ��ȣ��磺�ۼ����Ͷ�ʣ��ۼ����������
	private BigDecimal progress2; // ��������2����Ԫ��������ֵ
	private BigDecimal progress1; // ��������1����Ԫ��������ֵ(��ʱ������������Ͷ�ʣ�ppms�����ֶ�ʹ��)
	private BigDecimal progress3; // ��������3����Ԫ��������ֵ
	private BigDecimal progress4; // ��������4����Ԫ��������ֵ
	private BigDecimal progress5; // ��������5����Ԫ��������ֵ
	
	private Long orgId; // ���浥λID
	private String orgName; // ���浥λName
	private Long reporterId; // ������ID
	private String reporterName; // ����������
	private Date reportTime; // ����ʱ��
	private int reportYear;
	private int reportMonth;
	private Date belongTime;		//�����·�/����ʱ�䣬����ͳ�ơ�����������·ݣ���ȡ����1�š�
	private String issue1;
	private String issue2;
	
	
	@JSON(format="yyyy-MM-dd")
	public Date getBelongTime() {
		return belongTime;
	}

	public void setBelongTime(Date belongTime) {
		this.belongTime = belongTime;
	}

	@JSON(format="yyyy-MM-dd")
	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////
	// ע�⣬�������Բ���ֵ���ݣ���ʵ��ӳ���в���������Ӧ������
	// �������ݿ���û����Ӧ���ֶΣ���
	//////////////////////////////////////////////////////////////////////////////
/*
	public BigDecimal getProgress1Temp(){
		return getProgress1().multiply(new BigDecimal(10000));
	}
	public void setProgress1Temp(BigDecimal progress1Temp)
	{
		this.setProgress1(progress1Temp.divide(new BigDecimal(10000)));
	}
*/

	
	public int getReportYear() {
		return reportYear;
	}

	public void setReportYear(int reportYear) {
		this.reportYear = reportYear;
	}

	public int getReportMonth() {
		return reportMonth;
	}

	public void setReportMonth(int reportMonth) {
		this.reportMonth = reportMonth;
	}

	//����1��ԪΪ��λ��������
	public BigDecimal getProgress1InY(){
		if(getProgress1() != null){
			return getProgress1().multiply(new BigDecimal(10000));
		}
		return null;
	}

	//����1��ԪΪ��λ��������
	public void setProgress1InY(BigDecimal progress1InY){
		if(progress1InY != null){
			this.setProgress1(progress1InY.divide(new BigDecimal(10000)));
		}
	}

	
	

	// ��bit���洢���boolean��־�����ݽṹ�������Դ洢31��Boolean���ݣ�ÿ��BITλһ����
	// ӳ��ʱʹ��int���͡�
	private BitFlag bitFlag = new BitFlag();

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

	public void setHasAttachment(boolean hasAttachment) {
		if (hasAttachment) {
			bitFlag.set(HAS_ATTACHMENT);
		} else {
			bitFlag.clear(HAS_ATTACHMENT);
		}
	}

	public boolean isHasAttachment() {
		return bitFlag.has(HAS_ATTACHMENT);
	}

	public void setHasVideo(boolean hasVideo) {
		if (hasVideo) {
			bitFlag.set(HAS_VIDEO_FLAG);
		} else {
			bitFlag.clear(HAS_VIDEO_FLAG);
		}
	}

	public boolean isHasVedio() {
		return bitFlag.has(HAS_VIDEO_FLAG);
	}

	public void setHasImage(boolean hasImage) {
		if (hasImage) {
			bitFlag.set(HAS_IMAGE_FLAG);
		} else {
			bitFlag.clear(HAS_IMAGE_FLAG);
		}
	}

	public boolean isHasImage() {
		return bitFlag.has(HAS_IMAGE_FLAG);
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the periodProgress
	 */
	public BigDecimal getPeriodProgress() {
		return periodProgress;
	}

	/**
	 * @param periodProgress
	 *            the periodProgress to set
	 */
	public void setPeriodProgress(BigDecimal periodProgress) {
		this.periodProgress = periodProgress;
	}

	/**
	 * @return the monthProgress
	 */
	public BigDecimal getMonthProgress() {
		return monthProgress;
	}

	/**
	 * @param monthProgress
	 *            the monthProgress to set
	 */
	public void setMonthProgress(BigDecimal monthProgress) {
		this.monthProgress = monthProgress;
	}

	/**
	 * @return the quarterProgress
	 */
	public BigDecimal getQuarterProgress() {
		return quarterProgress;
	}

	/**
	 * @param quarterProgress
	 *            the quarterProgress to set
	 */
	public void setQuarterProgress(BigDecimal quarterProgress) {
		this.quarterProgress = quarterProgress;
	}

	/**
	 * @return the yearProgress
	 */
	public BigDecimal getYearProgress() {
		return yearProgress;
	}

	/**
	 * @param yearProgress
	 *            the yearProgress to set
	 */
	public void setYearProgress(BigDecimal yearProgress) {
		this.yearProgress = yearProgress;
	}

	/**
	 * @return the progress
	 */
	public BigDecimal getProgress() {
		return progress;
	}

	/**
	 * @param progress
	 *            the progress to set
	 */
	public void setProgress(BigDecimal progress) {
		this.progress = progress;
	}

	/**
	 * @return the progress2
	 */
	public BigDecimal getProgress2() {
		return progress2;
	}

	/**
	 * @param progress2
	 *            the progress2 to set
	 */
	public void setProgress2(BigDecimal progress2) {
		this.progress2 = progress2;
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

	/**
	 * @return the refObjectType
	 */
	public int getRefObjectType() {
		return refObjectType;
	}

	/**
	 * @param refObjectType the refObjectType to set
	 */
	public void setRefObjectType(int refObjectType) {
		this.refObjectType = refObjectType;
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

	public BigDecimal getProgress1() {
		return progress1;
	}

	public void setProgress1(BigDecimal progress1) {
		this.progress1 = progress1;
	}

	public BigDecimal getProgress3() {
		return progress3;
	}

	public void setProgress3(BigDecimal progress3) {
		this.progress3 = progress3;
	}

	public BigDecimal getProgress4() {
		return progress4;
	}

	public void setProgress4(BigDecimal progress4) {
		this.progress4 = progress4;
	}

	public BigDecimal getProgress5() {
		return progress5;
	}

	public void setProgress5(BigDecimal progress5) {
		this.progress5 = progress5;
	}
	
	/**
	 * @return the issue1
	 */
	public String getIssue1() {
		return issue1;
	}

	/**
	 * @param issue1 the issue1 to set
	 */
	public void setIssue1(String issue1) {
		this.issue1 = issue1;
	}

	/**
	 * @return the issue2
	 */
	public String getIssue2() {
		return issue2;
	}

	/**
	 * @param issue2 the issue2 to set
	 */
	public void setIssue2(String issue2) {
		this.issue2 = issue2;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.RFSEntityObject#getObjectType()
	 */
	public int getObjectType() {
		return ObjectTypes.PROGRESS;
	}
	
	public String getStatusName(){
		return CodeMapUtils.getCodeName(Progress.class, "STATUS", getStatus());
	}
}
