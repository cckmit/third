/*
 * $Id: ObjectCodeChangeEvent.java 5915 2012-06-28 08:54:20Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.event2;

import cn.redflagsoft.base.bean.RFSObjectable;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ObjectCodeChangeEvent extends PropertyChangeEvent<RFSObjectable, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 114507358020116763L;

	/**
	 * @param object
	 * @param propertyName
	 * @param oldValue
	 * @param newValue
	 */
	public ObjectCodeChangeEvent(RFSObjectable object, String oldValue, String newValue) {
		super(object, "code", oldValue, newValue);
	}
}
