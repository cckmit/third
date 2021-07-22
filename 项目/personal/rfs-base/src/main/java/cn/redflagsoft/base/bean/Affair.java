/*
 * $Id: Affair.java 3996 2010-10-18 06:56:46Z lcj $
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
public class Affair extends VersionableBean implements Domain<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 250620486412359359L;
	private Long id;
	private String code;
	private String name;
	private String abbr;
	private Long beginTask;
	private Long endTask;
	private Long keyTask;
	private Long bizRoute;
	private String note;
	
	public Affair() {
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
	 * ȫ��,רҵ����.��:��ҵ���Ʊ��.
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
	 * ��������
	 * 
	 * ���������������
	 * @return Long
	 */
	public Long getBeginTask() {
		return beginTask;
	}
	
	/**
	 * @param beginTask
	 */
	public void setBeginTask(Long beginTask) {
		this.beginTask = beginTask;
	}
	
	/**
	 * ��������
	 * 
	 * ���������������.
	 * @return Long
	 */
	public Long getEndTask() {
		return endTask;
	}
	
	/**
	 * @param endTask
	 */
	public void setEndTask(Long endTask) {
		this.endTask = endTask;
	}
	
	/**
	 * �ؼ�����
	 * 
	 * ���������������.
	 * @return Long
	 */
	public Long getKeyTask() {
		return keyTask;
	}
	
	/**
	 * @param keyTask
	 */
	public void setKeyTask(Long keyTask) {
		this.keyTask = keyTask;
	}
	
	/**
	 * ����
	 * 
	 * ����,����������.Ĭ��Ϊ0,��ʾ����.
	 * @return Long
	 */
	public Long getBizRoute() {
		return bizRoute;
	}
	/**
	 * @param bizRoute
	 */
	public void setBizRoute(Long bizRoute) {
		this.bizRoute = bizRoute;
	}
	
	/**
	 * ע��
	 * 
	 * ˵������
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
