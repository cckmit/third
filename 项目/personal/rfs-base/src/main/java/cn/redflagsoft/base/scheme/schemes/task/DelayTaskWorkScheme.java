/*
 * $Id: DelayTaskWorkScheme.java 5686 2012-05-16 03:39:28Z thh $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.scheme.schemes.task;

import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Work;

/**
 * ҵ�����ڡ�
 * 
 * <pre>
 * /s/delayTask.jspa?objectServiceName=&taskType=&taskSN=&delayTime=&reasonCategory=&reason=&note=&matterIds[0]
 * 
 * delayTime ��������
 * reasonCategory ԭ�����
 * reason ԭ��
 * note ��ע
 * </pre>
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class DelayTaskWorkScheme extends AbstractTaskWorkScheme {
	private Short delayTime;//��������
//	private String note;//��ע
//	private String delayReasonCategory;//����ԭ�����
//	private String delayReason;//����ԭ��
	
	
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
		Assert.notNull(delayTime, "������������Ϊ�ա�");
	
		
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
		
		return "ҵ�����ڳɹ�";
	}
}
