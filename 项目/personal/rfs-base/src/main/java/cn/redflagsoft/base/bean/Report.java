/*
 * $Id: Report.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import org.opoo.ndao.Domain;

/**
 * @author Alex Lin
 *
 */
public class Report extends VersionableBean implements Domain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5802758008185785475L;
	private Long id;
	private String code;
	private String name;
	private String abbr;
	private Long serviceID;
	private String note;

	public Report() {
	}
	
	/**
	 * Ψһ��ʾ
	 * 
	 * @return Long
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * ȫ��,����ȫ��.
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
	 * ���,ע�ⲻ���ظ�.
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
	 * ����
	 * 
	 * @return Long
	 */
	public Long getServiceID() {
		return serviceID;
	}

	/**
	 * @param serviceID
	 */
	public void setServiceID(Long serviceID) {
		this.serviceID = serviceID;
	}

	/**
	 * ע��
	 * 
	 * ����˵��.
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
