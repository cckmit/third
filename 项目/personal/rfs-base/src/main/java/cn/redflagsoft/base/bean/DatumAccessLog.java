/*
 * $Id: DatumAccessLog.java 5378 2012-03-05 09:03:53Z lcj $
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
 * 资料访问日志。
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class DatumAccessLog extends VersionableBean {

	private static final long serialVersionUID = 2262625020453715770L;
	private Long datumId;
	private Date accessTime;
	private int accessType;
	private Long accessorId;
	private String accessorName;
	/**
	 * @return the datumId
	 */
	public Long getDatumId() {
		return datumId;
	}
	/**
	 * @param datumId the datumId to set
	 */
	public void setDatumId(Long datumId) {
		this.datumId = datumId;
	}
	/**
	 * @return the accessTime
	 */
	public Date getAccessTime() {
		return accessTime;
	}
	/**
	 * @param accessTime the accessTime to set
	 */
	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}
	/**
	 * @return the accessType
	 */
	public int getAccessType() {
		return accessType;
	}
	/**
	 * @param accessType the accessType to set
	 */
	public void setAccessType(int accessType) {
		this.accessType = accessType;
	}
	/**
	 * @return the accessorId
	 */
	public Long getAccessorId() {
		return accessorId;
	}
	/**
	 * @param accessorId the accessorId to set
	 */
	public void setAccessorId(Long accessorId) {
		this.accessorId = accessorId;
	}
	/**
	 * @return the accessorName
	 */
	public String getAccessorName() {
		return accessorName;
	}
	/**
	 * @param accessorName the accessorName to set
	 */
	public void setAccessorName(String accessorName) {
		this.accessorName = accessorName;
	}
}
