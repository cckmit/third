/*
 * $Id: BizTrackNodeInstance.java 3996 2010-10-18 06:56:46Z lcj $
 * BizRouteNodeHibernateDao.java
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
public class BizTrackNodeInstance extends SerializableEntity<Long> implements Domain<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 53737790677626311L;
	public static final byte CATEGORY_WORK = 3;
	public static final byte CATEGORY_TASK = 2;
	public static final byte CATEGORY_JOB = 1;
	public static final byte CATEGORY_DEFAULT = 3;
	public static final String MAP_QUERY_COLUMN_NODESN = "NODESN";
	public static final String MAP_QUERY_FUNCATION_COUNT = "COUNT";
	private Long sn;
	private Long trackSN;
	private Long nodeSN;
	private byte category;
	private Long bizSN;

	public BizTrackNodeInstance() {
	}

	public Long getId() {
		return getSn();
	}

	public void setId(Long id) {
		setSn(id);
	}

	/**
	 * Ψһ��ʾ
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
	 * �켣
	 * 
	 * ���ɿ�
	 * @return Long
	 */
	public Long getTrackSN() {
		return trackSN;
	}

	/**
	 * @param trackSN
	 */
	public void setTrackSN(Long trackSN) {
		this.trackSN = trackSN;
	}

	/**
	 * �ڵ�
	 * 
	 * ���ɿ�
	 * @return Long
	 */
	public Long getNodeSN() {
		return nodeSN;
	}

	/**
	 * @param nodeSN
	 */
	public void setNodeSN(Long nodeSN) {
		this.nodeSN = nodeSN;
	}

	/**
	 * ����
	 * 
	 * ���ࣺ1��ҵ��2����3����.Ĭ��Ϊ2.
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
	 * ҵ��
	 * 
	 * �켣�ڵ������ҵ��Ĺ������浽����һ���ط�.
	 * @return Long
	 */
	public Long getBizSN() {
		return bizSN;
	}

	/**
	 * @param bizSN
	 */
	public void setBizSN(Long bizSN) {
		this.bizSN = bizSN;
	}
}
