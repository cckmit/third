/*
 * $Id: ContentHolder.java 4342 2011-04-22 02:17:01Z lcj $
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
 * 内容对象，作为内容存储对象的超类使用。
 * 
 * @author Alex Lin(alex@opoo.org)
 */
public class ContentHolder extends VersionableBean {
	private static final long serialVersionUID = 7923038236990835043L;
	
	//留给子类使用
	//private String objectId;		//相关对象I
	//private String objectType;		//相关对象类型
	
	private String title;			//标题
	private String body;			//内容
	private String summary;			//摘要
	private Long publisherId;		//布者ID
	private String publisherName;	//发布者姓名
	private Date publishTime;		//发布时间
	private Date effectingTime;		//生效时间,如果有生效时间，则该内容在生效时间前是不可用的（不可见）。如果该项为空，表示立刻生效。
	private Date expirationTime;	//失效时间,如果有失效时间，在该时间之后该内容无效（不显示）。如果该项为空，则表示一直有效。
	private int imageCount;			//附图数量
	private String images;			//附图。通常是附图的id集合，使用逗号分隔。
	private int attachmentCount;	//附件数量
	private String attachments;		//附件.通常是附件的id集合，使用逗号分隔。
	private int version;			//版本
	private int viewCount;			//阅读次数
	private Long reviewerId;		//审查者ID
	private String reviewerName;	//审查者姓名
	private Byte reviewStatus;		//审查状态
	private Date reviewTime;		//审查时间
	private Long approverId;		//核准者ID
	private String approverName;	//核准者姓名
	private Byte approvalStatus;	//核准状态
	private Date approvalTime;		//核准时间
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Long getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Date getEffectingTime() {
		return effectingTime;
	}
	public void setEffectingTime(Date effectingTime) {
		this.effectingTime = effectingTime;
	}
	public Date getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}
	public int getImageCount() {
		return imageCount;
	}
	public void setImageCount(int imageCount) {
		this.imageCount = imageCount;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public int getAttachmentCount() {
		return attachmentCount;
	}
	public void setAttachmentCount(int attachmentCount) {
		this.attachmentCount = attachmentCount;
	}
	public String getAttachments() {
		return attachments;
	}
	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public Long getReviewerId() {
		return reviewerId;
	}
	public void setReviewerId(Long reviewerId) {
		this.reviewerId = reviewerId;
	}
	public String getReviewerName() {
		return reviewerName;
	}
	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}
	public Byte getReviewStatus() {
		return reviewStatus;
	}
	public void setReviewStatus(Byte reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	public Date getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}
	public Long getApproverId() {
		return approverId;
	}
	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public Byte getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(Byte approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public Date getApprovalTime() {
		return approvalTime;
	}
	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}
}
