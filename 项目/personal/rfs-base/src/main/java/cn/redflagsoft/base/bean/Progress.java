/*
 * $Id: Progress.java 6156 2012-12-25 02:10:00Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
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
 * 对象形象进度。
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

	
	@DisplayName("默认")
	public static final byte STATUS_DEFAULT = 0;
	@DisplayName("待报")
	public static final byte STATUS_DRAFT = 1;
	@DisplayName("已报")
	public static final byte STATUS_OK = 9; 
	@DisplayName("作废")
	public static final byte STATUS_INVALID = 11;//作废
	
	
	// private Long id;

	private int refObjectType;//相关对象类型

	// private Integer type;
	private Long refObjectId;// 相关对象ID
	private String refObjectName;// 相关对象名称
	private String title;// 标题
	private String description; // 形象进度描述
	private String comment; // 备注
	private BigDecimal periodProgress;// 阶段性进度，如：本期完成投资，本期完成数量等
	private BigDecimal monthProgress;// 月度累计进度
	private BigDecimal quarterProgress;// 季度累计进度
	private BigDecimal yearProgress;// 年度累计进度：如年度累计完成投资
	private BigDecimal progress; // 进度，累计进度，如：累计完成投资，累计完成数量等
	private BigDecimal progress2; // 其他进度2，万元或其它数值
	private BigDecimal progress1; // 其他进度1，万元或其它数值(暂时给‘本年度完成投资（ppms）’字段使用)
	private BigDecimal progress3; // 其他进度3，万元或其它数值
	private BigDecimal progress4; // 其他进度4，万元或其它数值
	private BigDecimal progress5; // 其他进度5，万元或其它数值
	
	private Long orgId; // 报告单位ID
	private String orgName; // 报告单位Name
	private Long reporterId; // 报告人ID
	private String reporterName; // 报告人姓名
	private Date reportTime; // 报告时间
	private int reportYear;
	private int reportMonth;
	private Date belongTime;		//所属月份/所属时间，用于统计。如果是所属月份，则取当月1号。
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
	// 注意，以下属性参与值传递，在实体映射中并不存在相应的属性
	// （即数据库中没有相应的字段）。
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

	//进度1的元为单位附加属性
	public BigDecimal getProgress1InY(){
		if(getProgress1() != null){
			return getProgress1().multiply(new BigDecimal(10000));
		}
		return null;
	}

	//进度1的元为单位附加属性
	public void setProgress1InY(BigDecimal progress1InY){
		if(progress1InY != null){
			this.setProgress1(progress1InY.divide(new BigDecimal(10000)));
		}
	}

	
	

	// 用bit来存储多个boolean标志的数据结构，最多可以存储31个Boolean数据，每个BIT位一个。
	// 映射时使用int类型。
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
