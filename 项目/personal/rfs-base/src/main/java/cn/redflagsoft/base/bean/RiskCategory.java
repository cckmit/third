/*
 * $Id: RiskCategory.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import java.io.Serializable;

import org.opoo.apps.bean.SerializableEntity;

/**
 * 
 * @author Alex Lin
 *
 */
public class RiskCategory extends SerializableEntity<Integer> implements LabelDataBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 363438618311758830L;
	private String name;
	private String tableName;
	private byte status = 0;
	
	/**
	 * 将映射为数据库的ID字段。
	 * 
	 * @return the type
	 */
	public Integer getType() {
		return getId();
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		setId(type);
	}
	/**
	 * 类名
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 类表
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * 状态
	 * 至多256种，根据业务定义
	 * @return the status
	 */
	public byte getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(byte status) {
		this.status = status;
	}
	
	
	/* 
	 * @see cn.redflagsoft.base.bean.LabelDataBean#getData()
	 */
	public Serializable getData() {
		return getId();
	}
	/* 
	 * @see cn.redflagsoft.base.bean.LabelDataBean#getLabel()
	 */
	public String getLabel() {
		return getName();
	}
	
}
