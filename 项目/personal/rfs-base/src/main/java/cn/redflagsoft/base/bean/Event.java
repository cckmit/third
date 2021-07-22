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
	 * ����
	 * 
	 * �¼�����.
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
	 * ���
	 * 
	 * �¼����.
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
	 * ʱ��
	 * 
	 * Ĭ��Ϊ0,��ʾ����.
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
	 * �ص�
	 * 
	 * Ĭ��Ϊ0,��ʾ����.
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
	 * ����
	 * 
	 * ����Ϊ1ʱ�Ķ���.Ĭ��Ϊ0,��ʾ����.
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
	 * ��Ա
	 * 
	 * ����Ϊ1ʱ����Ա.Ĭ��Ϊ0,��ʾ����.
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
	 * ������
	 * 
	 * �漰��������.Ĭ��Ϊ0,��ʾ����.
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
	 * ��Ա��
	 * 
	 * �漰��Ա����.Ĭ��Ϊ0,��ʾ����.
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
	 * ������
	 * 
	 * �漰��������.Ĭ��Ϊ0,��ʾ����.
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
}
