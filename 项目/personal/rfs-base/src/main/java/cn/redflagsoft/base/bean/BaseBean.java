/*
 * $Id: BaseBean.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;


import org.opoo.apps.bean.LongKeyBean;


/**
 * 基础应用的实体类的超类。
 * 此超类定义了
 * 
 * @author Alex Lin
 *
 */
public abstract class BaseBean extends LongKeyBean// implements Observable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7638192228138089130L;
	/**
	 * 备注。
	 */
	private String remark;
	/**
	 * 状态。
	 * 
	 */
	private byte status;
	/**
	 * 种类、类型。
	 */
	private int type;

	/**
	 * 备注。
	 * 
	 * @return String
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 状态。
	 * 
	 * @return String
	 */
	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	/**
	 * 类型、种类。
	 * 
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
}
