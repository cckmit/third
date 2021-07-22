/*
 * $Id: OrgGroup.java 4341 2011-04-22 02:12:39Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.io.Serializable;

import org.opoo.apps.util.BooleanMap;
import org.springframework.core.Ordered;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OrgGroup extends VersionableBean implements LabelDataBean, Ordered{
	private static final long serialVersionUID = 4937490362343485018L;
	
	public static final long ID_隐藏分组 = 0;
	
	private String name;
	private boolean userManaged = false;
	private BooleanMap booleanMap = new BooleanMap();
	private Integer displayOrder;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isUserManaged() {
		return userManaged;
	}

	public void setUserManaged(boolean userManaged) {
		this.userManaged = userManaged;
	}

	public long getFlags() {
		return booleanMap.longValue();
	}

	public void setFlags(long flags) {
		booleanMap = BooleanMap.valueOf(flags);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.LabelDataBean#getData()
	 */
	public Serializable getData() {
		return getId();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.LabelDataBean#getLabel()
	 */
	public String getLabel() {
		return name;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	public int getOrder() {
		if(displayOrder != null){
			return displayOrder.intValue();
		}
		return 0;
	}
}
