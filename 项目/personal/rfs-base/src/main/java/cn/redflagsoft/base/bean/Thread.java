/*
 * $Id: Thread.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;


/**
 * 
 * @author ymq
 *
 */
public class Thread extends VersionableBean implements org.opoo.ndao.Domain<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5591866029459241735L;
	private Long sn;
	private String code;
	private Long affairID;
	private Long objectID;
	private Long taskSN;
	private Long workSN;
	private Long bizRoute;
	private String note;
	private Long jobSN;
	private Long bizTrack;

	public Long getBizTrack() {
		return bizTrack;
	}

	public void setBizTrack(Long bizTrack) {
		this.bizTrack = bizTrack;
	}

	public Thread() {
	}

	/**
	 * @return Long
	 */
	public Long getId() {
		return getSn();
	}

	/**
	 * @param Long
	 */
	public void setId(Long sn) {
		setSn(sn);
	}

	/**
	 * SN
	 * 
	 * Ψһ��ʾ
	 * @return Long
	 */
	public Long getSn() {
		return sn;
	}

	/**
	 * @param sn
	 */
	public void setSn(Long sn) {
		this.sn = sn;
	}

	/**
	 * ����
	 * 
	 * @return String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * ����
	 * 
	 * Ĭ��Ϊ0,��ʾ����.
	 * @return Long
	 */
	public Long getAffairID() {
		return affairID;
	}

	/**
	 * @param affair
	 */
	public void setAffairID(Long affairID) {
		this.affairID = affairID;
	}

	

	

	/**
	 * ����
	 * 
	 * ����Ϊ1ʱ�Ķ���.Ĭ��Ϊ0,��ʾ����.
	 * @return Long
	 */
	public Long getObjectID() {
		return objectID;
	}

	/**
	 * @param object
	 */
	public void setObjectID(Long objectID) {
		this.objectID = objectID;
	}

	

	/**
	 * ����
	 * 
	 * ���������������.Ĭ��Ϊ0,��ʾ����.
	 * @return Long
	 */
	public Long getTaskSN() {
		return taskSN;
	}

	/**
	 * @param taskSN
	 */
	public void setTaskSN(Long taskSN) {
		this.taskSN = taskSN;
	}

	/**
	 * ����
	 * 
	 * �������¼��Ĺ���.Ĭ��Ϊ0,��ʾ����.
	 * @return Long
	 */
	public Long getWorkSN() {
		return workSN;
	}

	/**
	 * @param workSN
	 */
	public void setWorkSN(Long workSN) {
		this.workSN = workSN;
	}

	
	/**
	 * ����
	 * 
	 * ע��,����������.Ĭ��Ϊ0,��ʾ����.
	 * @return Long
	 */
	public Long getBizRoute() {
		return bizRoute;
	}

	/**
	 * @param busRoute
	 */
	public void setBizRoute(Long bizRoute) {
		this.bizRoute = bizRoute;
	}

	/**
	 * ע��
	 * 
	 * ���ڱ��¼���������˵��.
	 * @return String
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the jobSN
	 */
	public Long getJobSN() {
		return jobSN;
	}

	/**
	 * @param jobSN the jobSN to set
	 */
	public void setJobSN(Long jobSN) {
		this.jobSN = jobSN;
	}
}
