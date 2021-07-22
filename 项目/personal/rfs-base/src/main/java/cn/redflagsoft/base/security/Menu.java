/*
 * $Id: Menu.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;

import java.io.Serializable;

import org.opoo.apps.bean.StringKeyBean;

import cn.redflagsoft.base.bean.LabelDataBean;

/**
 * 菜单。
 * 
 * @author Alex Lin
 * @deprecated
 */
public class Menu extends StringKeyBean implements LabelDataBean, Comparable<Menu>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3741732975503686862L;
	
	//private String authority;
	private String name;
	private String application;
	private Long groupId;
	private int displayOrder;
	private String logo;
	private String image;
	private String icon;
	private String groupName;
	private String label2;
	private int checked = 0;

	
	public Menu() {
		super();
	}
	
	public Menu(String authority, String application, int displayOrder,String label2,Long groupId,
			String groupName, String icon, String image, String logo,
			String name) {
		this.setAuthority(authority);
		this.setApplication(application);
		this.setDisplayOrder(displayOrder);
		this.setLabel2(label2);
		this.setGroupId(groupId);
		this.setGroupName(groupName);
		this.setIcon(icon);
		this.setImage(image);
		this.setLogo(logo);
		this.setName(name);
	}
	/**
	 * @return the authority
	 */
	public String getAuthority() {
		return getId();
	}
	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(String authority) {
		this.setId(authority);
		//this.setKey(authority);
		//this.authority = authority;
	}
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
	 * @return the application
	 */
	public String getApplication() {
		return application;
	}
	/**
	 * @param application the application to set
	 */
	public void setApplication(String application) {
		this.application = application;
	}
	/**
	 * @return the groupId
	 */
	public Long getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Menu o) {
		return (groupId.intValue() * 1000 + displayOrder) - (o.groupId.intValue() * 1000 + o.displayOrder);
	}
	
	public Serializable getData() {
		return getId();
	}
	
	public String getLabel() {
		return  getId() + "(" + name + ")";
	}

	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the label2
	 */
	public String getLabel2() {
		return label2;
	}

	/**
	 * @param label2 the label2 to set
	 */
	public void setLabel2(String label2) {
		this.label2 = label2;
	}

	/**
	 * @return the checked
	 */
	public int getChecked() {
		return checked;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(int checked) {
		this.checked = checked;
	}
}
