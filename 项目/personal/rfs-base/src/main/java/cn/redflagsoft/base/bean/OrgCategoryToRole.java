/*
 * $Id: OrgCategoryToRole.java 6249 2013-06-25 07:52:06Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.util.Date;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class OrgCategoryToRole extends VersionableBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3046025173848290159L;
	private Long orgCategoryId;
	private Long roleId;
	/**
	 * 保存，暂时无用。
	 */
	private Date deleteTime;
	private Long deleter;
	
	/**
	 * @return the orgCategoryId
	 */
	public Long getOrgCategoryId() {
		return orgCategoryId;
	}
	/**
	 * @param orgCategoryId the orgCategoryId to set
	 */
	public void setOrgCategoryId(Long orgCategoryId) {
		this.orgCategoryId = orgCategoryId;
	}
	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	/**
	 * @return the deleteTime
	 */
	public Date getDeleteTime() {
		return deleteTime;
	}
	/**
	 * @param deleteTime the deleteTime to set
	 */
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	/**
	 * @return the deleter
	 */
	public Long getDeleter() {
		return deleter;
	}
	/**
	 * @param deleter the deleter to set
	 */
	public void setDeleter(Long deleter) {
		this.deleter = deleter;
	}
}
