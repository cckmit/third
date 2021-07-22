/*
 * $Id: BizRouteMap.java 4615 2011-08-21 07:10:37Z lcj $
 * BizRouteNode.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import org.opoo.apps.bean.SerializableEntity;
import org.opoo.ndao.Domain;

/**
 * 
 * @author ymq
 *
 */
public class BizRouteMap extends SerializableEntity<Long> implements Domain<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2587124574241820603L;
	private Long sn;
	private byte category;
	private int bizType;
	private Long bizId;
	private byte status;
	private Long routeId;
	
	public BizRouteMap(){
	}

	/**
	 * @return Long
	 */
	public Long getId() {
		return getSn();
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		setSn(id);
	}

	/**
	 * 唯一标示
	 * 
	 * @return Long
	 */
	public Long getSn() {
		return sn;
	}

	/**
	 * @param sn
	 */
	public void setSn(Long sn) {
		this.sn = sn;
	}

	/**
	 * 分类
	 * 
	 * 分类：1 作业，2 任务， 3 工作。
	 * @return byte
	 */
	public byte getCategory() {
		return category;
	}

	/**
	 * @param category
	 */
	public void setCategory(byte category) {
		this.category = category;
	}

	/**
	 * 业务
	 * 
	 * 作业或任务工作本身的种类，作业时可以忽略。因为一个Affair对应一个Job.
	 * @return short
	 */
	public int getBizType() {
		return bizType;
	}

	/**
	 * @param bizType
	 */
	public void setBizType(int bizType) {
		this.bizType = bizType;
	}

	/**
	 * 事项
	 * 
	 * 事项ID或事别ID
	 * @return Long
	 */
	public Long getBizId() {
		return bizId;
	}

	/**
	 * @param bizId
	 */
	public void setBizId(Long bizId) {
		this.bizId = bizId;
	}

	/**
	 * 状态
	 * 
	 * @return byte
	 */
	public byte getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	/**
	 * 流程
	 * 
	 * @return Long
	 */
	public Long getRouteId() {
		return routeId;
	}

	/**
	 * @param routeId
	 */
	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
}
