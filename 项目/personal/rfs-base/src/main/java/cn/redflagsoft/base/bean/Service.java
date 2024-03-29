/*
 * $Id: Service.java 3996 2010-10-18 06:56:46Z lcj $
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
public class Service extends VersionableBean implements Domain<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5990586624651283959L;
	private Long id;
	private String code;
	private String name;
	private String abbr;
	private String xrl;
	private String note;
	
	public Service() {
	}
	
	public Service(String abbr, String code, Long id, String name, String note, String xrl) {
		this.abbr = abbr;
		this.code = code;
		this.id = id;
		this.name = name;
		this.note = note;
		this.xrl = xrl;
	}

	/**
	 * 唯一标示
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
	 * 名称
	 * 
	 * 全称,法律全称.
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
	 * 简称,注意不得重复.
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
	 * XRL
	 * 
	 * 服务地址.
	 * @return String
	 */
	public String getXrl() {
		return xrl;
	}

	/**
	 * @param xrl
	 */
	public void setXrl(String xrl) {
		this.xrl = xrl;
	}

	/**
	 * 注释
	 * 
	 * 说明内容.
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
