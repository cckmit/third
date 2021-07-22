/*
 * $Id: EntityObjectToBizLog.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

/**
 * 记录对象与业务日志（Job，Task，Work）之间的关系。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 2.0.2
 */
public abstract class EntityObjectToBizLog extends VersionableBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 843973349037889435L;
	private long objectId;
	private int objectType;
	
	/**
	 * @param objectId
	 * @param objectType
	 */
	public EntityObjectToBizLog(long objectId, int objectType) {
		super();
		this.objectId = objectId;
		this.objectType = objectType;
	}
	/**
	 * 
	 */
	public EntityObjectToBizLog() {
		super();
	}
	/**
	 * @return the objectId
	 */
	public long getObjectId() {
		return objectId;
	}
	/**
	 * @param objectId the objectId to set
	 */
	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}
	/**
	 * @return the objectType
	 */
	public int getObjectType() {
		return objectType;
	}
	/**
	 * @param objectType the objectType to set
	 */
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}
}
