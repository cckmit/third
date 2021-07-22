/*
 * $Id: DelayTaskWorkScheme.java 5686 2012-05-16 03:39:28Z thh $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.task;

import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Work;

/**
 * 业务延期。
 * 
 * <pre>
 * /s/delayTask.jspa?objectServiceName=&taskType=&taskSN=&delayTime=&reasonCategory=&reason=&note=&matterIds[0]
 * 
 * delayTime 延期天数
 * reasonCategory 原因类别
 * reason 原因
 * note 备注
 * </pre>
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class DelayTaskWorkScheme extends AbstractTaskWorkScheme {
	private Short delayTime;//延期天数
//	private String note;//备注
//	private String delayReasonCategory;//延期原因类别
//	private String delayReason;//延期原因
	
	
	/**
	 * @return the delayTime
	 */
	public Short getDelayTime() {
		return delayTime;
	}

	/**
	 * @param delayTime the delayTime to set
	 */
	public void setDelayTime(Short delayTime) {
		this.delayTime = delayTime;
	}

//	/**
//	 * @return the note
//	 */
//	public String getNote() {
//		return note;
//	}
//
//	/**
//	 * @param note the note to set
//	 */
//	public void setNote(String note) {
//		this.note = note;
//	}
//	
//	/**
//	 * @return the delayReasonCategory
//	 */
//	public String getDelayReasonCategory() {
//		return delayReasonCategory;
//	}
//
//	/**
//	 * @param delayReasonCategory the delayReasonCategory to set
//	 */
//	public void setDelayReasonCategory(String delayReasonCategory) {
//		this.delayReasonCategory = delayReasonCategory;
//	}
//
//
//	/**
//	 * @return the delayReason
//	 */
//	public String getDelayReason() {
//		return delayReason;
//	}
//
//	/**
//	 * @param delayReason the delayReason to set
//	 */
//	public void setDelayReason(String delayReason) {
//		this.delayReason = delayReason;
//	}

	public Object doScheme(){
		Assert.notNull(delayTime, "延期天数不能为空。");
	
		
		getTask().setNote(getNote());
		getTaskService().delayTask(getTask(), delayTime);
		
		Work work2 = getWork();
		work2.setString0(getReasonCategory());
		work2.setString1(getReason());
		work2.setString2(getClerkID() + "");
		work2.setString4(delayTime + "");
		//work2.setRemark(getNote());
		work2.setString3(getNote());
		getWorkService().updateWork(work2);
		
		return "业务延期成功";
	}
}
