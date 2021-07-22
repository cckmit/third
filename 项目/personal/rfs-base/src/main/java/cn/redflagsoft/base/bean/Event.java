/*
 * $Id: Event.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import java.util.Date;

/**
 * 
 * @author ymq
 *
 */
public class Event extends VersionableBean implements org.opoo.ndao.Domain<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 366977957393295195L;
	private Long sn;
	private String code;
	private int affair;
	private String name;
	private String abbr;
	private Date eventTime;
	private int addr;
	private int object;
	private int people;
	private Long taskSN;
	private Long workSN;
	private byte objectNum;
	private byte peopleNum;
	private byte taskNum;
	private Long bizRoute;
	private String note;

	public Event() {
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
	public int getAffair() {
		return affair;
	}

	/**
	 * @param affair
	 */
	public void setAffair(int affair) {
		this.affair = affair;
	}

	/**
	 * 名称
	 * 
	 * 事件名称.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 简称
	 * 
	 * 事件简称.
	 * @return String
	 */
	public String getAbbr() {
		return abbr;
	}

	/**
	 * @param abbr
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	/**
	 * 时间
	 * 
	 * 默认为0,表示忽略.
	 * @return Date
	 */
	public Date getEventTime() {
		return eventTime;
	}

	/**
	 * @param time
	 */
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	/**
	 * 地点
	 * 
	 * 默认为0,表示忽略.
	 * @return int
	 */
	public int getAddr() {
		return addr;
	}

	/**
	 * @param addr
	 */
	public void setAddr(int addr) {
		this.addr = addr;
	}

	/**
	 * 对象
	 * 
	 * 数量为1时的对象.默认为0,表示忽略.
	 * @return int
	 */
	public int getObject() {
		return object;
	}

	/**
	 * @param object
	 */
	public void setObject(int object) {
		this.object = object;
	}

	/**
	 * 人员
	 * 
	 * 数量为1时的人员.默认为0,表示忽略.
	 * @return int
	 */
	public int getPeople() {
		return people;
	}

	/**
	 * @param people
	 */
	public void setPeople(int people) {
		this.people = people;
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
	 * 对象数
	 * 
	 * 涉及对象数量.默认为0,表示忽略.
	 * @return byte
	 */
	public byte getObjectNum() {
		return objectNum;
	}

	/**
	 * @param objectNum
	 */
	public void setObjectNum(byte objectNum) {
		this.objectNum = objectNum;
	}

	/**
	 * 人员数
	 * 
	 * 涉及人员数量.默认为0,表示忽略.
	 * @return byte
	 */
	public byte getPeopleNum() {
		return peopleNum;
	}

	/**
	 * @param peopleNum
	 */
	public void setPeopleNum(byte peopleNum) {
		this.peopleNum = peopleNum;
	}

	/**
	 * 任务数
	 * 
	 * 涉及任务数量.默认为0,表示忽略.
	 * @return byte
	 */
	public byte getTaskNum() {
		return taskNum;
	}

	/**
	 * @param taskNum
	 */
	public void setTaskNum(byte taskNum) {
		this.taskNum = taskNum;
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
}
