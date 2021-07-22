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
	 * 全称,专业名称.如:企业名称变更.
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
	 * 创建任务
	 * 
	 * 创建本事体的任务
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
	 * 结束任务
	 * 
	 * 创建本事体的任务.
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
	 * 关键任务
	 * 
	 * 创建本事体的任务.
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
	 * 流程
	 * 
	 * 流程,引用自事体.默认为0,表示忽略.
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
	 * 注释
	 * 
	 * 说明内容
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
