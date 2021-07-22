/*
 * $Id: WorkItem.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 值对象，做参数传递使用。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class WorkItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Long clerkId;
	private String clerkIdLabel;
	
	private Long dutyerId;
	private String dutyerIdLabel;
	
	private Short timeLimit;
	
	private String content;
	private String remark;
	
	private Date beginTime;
	private Date endTime;
	
	private String name;
	private String workItemName;
	private long taskSN;
	private long workSN;
	private int taskType;
	private int workType;
	
	
	public Long getClerkId() {
		return clerkId;
	}
	public void setClerkId(Long clerkId) {
		this.clerkId = clerkId;
	}
	public String getClerkIdLabel() {
		return clerkIdLabel;
	}
	public void setClerkIdLabel(String clerkIdLabel) {
		this.clerkIdLabel = clerkIdLabel;
	}
	public Long getDutyerId() {
		return dutyerId;
	}
	public void setDutyerId(Long dutyerId) {
		this.dutyerId = dutyerId;
	}
	public String getDutyerIdLabel() {
		return dutyerIdLabel;
	}
	public void setDutyerIdLabel(String dutyerIdLabel) {
		this.dutyerIdLabel = dutyerIdLabel;
	}
	public Short getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(Short timeLimit) {
		this.timeLimit = timeLimit;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWorkItemName() {
		return workItemName;
	}
	public void setWorkItemName(String workItemName) {
		this.workItemName = workItemName;
	}
	public long getTaskSN() {
		return taskSN;
	}
	public void setTaskSN(long taskSN) {
		this.taskSN = taskSN;
	}
	public long getWorkSN() {
		return workSN;
	}
	public void setWorkSN(long workSN) {
		this.workSN = workSN;
	}
	public int getTaskType() {
		return taskType;
	}
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	public int getWorkType() {
		return workType;
	}
	public void setWorkType(int workType) {
		this.workType = workType;
	}
	
}
