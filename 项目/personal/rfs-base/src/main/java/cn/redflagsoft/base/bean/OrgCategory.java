/*
 * $Id: OrgCategory.java 6284 2013-07-18 02:36:10Z lf $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.io.Serializable;

import org.springframework.core.Ordered;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class OrgCategory extends VersionableBean implements LabelDataBean, Ordered{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6694143250000479703L;
	private String name;
	private String abbr;
	private String code;
	private String sn;
	private String description;
	private int displayOrder = 0;
	
	
	/**
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
	 * @return the abbr
	 */
	public String getAbbr() {
		return abbr;
	}

	/**
	 * @param abbr the abbr to set
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the sn
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * @param sn the sn to set
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the displayOrder
	 */
	public int getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.LabelDataBean#getLabel()
	 */
	public String getLabel() {
		return getName();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.LabelDataBean#getData()
	 */
	public Serializable getData() {
		return getId();
	}

	/* (non-Javadoc)
	 * @see org.springframework.core.Ordered#getOrder()
	 */
	public int getOrder() {
		return getDisplayOrder();
	}
	
	public String getOrgCategoryName(){
		return getName();
	}

	
	public Long getCategoryId(){
		return this.getId();
	}
}
