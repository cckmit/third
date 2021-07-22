/*
 * $Id$
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.config;

import java.util.List;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class BeanA extends AbstractBean {
	private String desc;
	private List<Long> matterIds;
	
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the matterIds
	 */
	public List<Long> getMatterIds() {
		return matterIds;
	}

	/**
	 * @param matterIds the matterIds to set
	 */
	public void setMatterIds(List<Long> matterIds) {
		this.matterIds = matterIds;
	}
}
