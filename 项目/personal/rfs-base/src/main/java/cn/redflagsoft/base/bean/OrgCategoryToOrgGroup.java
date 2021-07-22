/*
 * $Id: OrgCategoryToOrgGroup.java 6249 2013-06-25 07:52:06Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bean;

import java.util.Date;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class OrgCategoryToOrgGroup extends VersionableBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8734384873274891378L;
	private Long orgCategoryId;
	private Long orgGroupId;
	/**
	 * ���棬��ʱ���á�
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
	 * @return the orgGroupId
	 */
	public Long getOrgGroupId() {
		return orgGroupId;
	}
	/**
	 * @param orgGroupId the orgGroupId to set
	 */
	public void setOrgGroupId(Long orgGroupId) {
		this.orgGroupId = orgGroupId;
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
