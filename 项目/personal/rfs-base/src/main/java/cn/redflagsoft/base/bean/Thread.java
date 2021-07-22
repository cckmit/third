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
	 * 唯一标示
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
	 * 代码
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
	 * 事体
	 * 
	 * 默认为0,表示忽略.
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
	 * 对象
	 * 
	 * 数量为1时的对象.默认为0,表示忽略.
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
	 * 任务
	 * 
	 * 创建本事体的任务.默认为0,表示忽略.
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
	 * 工作
	 * 
	 * 创建本事件的工作.默认为0,表示忽略.
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
	 * 流程
	 * 
	 * 注程,引用自事体.默认为0,表示忽略.
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
	 * 注释
	 * 
	 * 关于本事件的文字性说明.
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
