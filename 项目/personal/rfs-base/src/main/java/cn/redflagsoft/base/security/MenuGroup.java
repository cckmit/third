/*
 * $Id: MenuGroup.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.opoo.apps.bean.SerializableEntity;

import cn.redflagsoft.base.bean.LabelDataBean;

/**
 * 菜单组。
 * 
 * @author Alex Lin
 * @deprecated
 */
public class MenuGroup extends SerializableEntity<Long> implements LabelDataBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6402890444867301297L;
	private String groupName;
	private int displayOrder;
	private String logo;
	private String image;
	private String icon;
	private List<Menu> menus = new ArrayList<Menu>();
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
	
	public List<Menu> getMenus(){
		return menus;
	}
	/**
	 * @param menus the menus to set
	 */
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public void setKey(Long key) {
		setId(key);
	}
	
	public Long getKey() {
		return getId();
	}

	public Serializable getData() {
		return getKey();
	}
	
	public String getLabel() {
		return groupName + "(" + getKey() + ")";
	}
	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}
	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
}
