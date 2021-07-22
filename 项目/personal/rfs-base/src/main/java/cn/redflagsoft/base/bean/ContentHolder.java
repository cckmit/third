/*
 * $Id: ContentHolder.java 4342 2011-04-22 02:17:01Z lcj $
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
 * ���ݶ�����Ϊ���ݴ洢����ĳ���ʹ�á�
 * 
 * @author Alex Lin(alex@opoo.org)
 */
public class ContentHolder extends VersionableBean {
	private static final long serialVersionUID = 7923038236990835043L;
	
	//��������ʹ��
	//private String objectId;		//��ض���I
	//private String objectType;		//��ض�������
	
	private String title;			//����
	private String body;			//����
	private String summary;			//ժҪ
	private Long publisherId;		//����ID
	private String publisherName;	//����������
	private Date publishTime;		//����ʱ��
	private Date effectingTime;		//��Чʱ��,�������Чʱ�䣬�����������Чʱ��ǰ�ǲ����õģ����ɼ������������Ϊ�գ���ʾ������Ч��
	private Date expirationTime;	//ʧЧʱ��,�����ʧЧʱ�䣬�ڸ�ʱ��֮���������Ч������ʾ�����������Ϊ�գ����ʾһֱ��Ч��
	private int imageCount;			//��ͼ����
	private String images;			//��ͼ��ͨ���Ǹ�ͼ��id���ϣ�ʹ�ö��ŷָ���
	private int attachmentCount;	//��������
	private String attachments;		//����.ͨ���Ǹ�����id���ϣ�ʹ�ö��ŷָ���
	private int version;			//�汾
	private int viewCount;			//�Ķ�����
	private Long reviewerId;		//�����ID
	private String reviewerName;	//���������
	private Byte reviewStatus;		//���״̬
	private Date reviewTime;		//���ʱ��
	private Long approverId;		//��׼��ID
	private String approverName;	//��׼������
	private Byte approvalStatus;	//��׼״̬
	private Date approvalTime;		//��׼ʱ��
	
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
