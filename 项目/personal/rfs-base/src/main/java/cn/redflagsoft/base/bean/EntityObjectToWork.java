/*
 * $Id: EntityObjectToWork.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

/**
 * RFSEntity与Work之间的关系
 * @author Alex Lin(alex@opoo.org)
 * @since 2.0.2
 */
public class EntityObjectToWork extends EntityObjectToBizLog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7912052019369639100L;
	private long workSN;
	
	/**
	 * 
	 */
	public EntityObjectToWork() {
		super();
	}

	/**
	 * @param objectId
	 * @param objectType
	 */
	public EntityObjectToWork(long workSN, long objectId, int objectType) {
		super(objectId, objectType);
		this.workSN = workSN;
	}

	/**
	 * @return the workSN
	 */
	public long getWorkSN() {
		return workSN;
	}

	/**
	 * @param workSN the workSN to set
	 */
	public void setWorkSN(long workSN) {
		this.workSN = workSN;
	}
	
	
}
