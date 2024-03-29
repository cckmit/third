/*
 * $Id: ClerkNameChangeEvent.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.event2;

import cn.redflagsoft.base.bean.Clerk;


/**
 * 人员姓名变更时发出的事件。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ClerkNameChangeEvent extends PropertyChangeEvent<Clerk, String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7027117277930416228L;

	/**
	 * 
	 * @param clerk
	 * @param oldValue
	 * @param newValue
	 */
	public ClerkNameChangeEvent(Clerk clerk, String oldValue, String newValue) {
		super(clerk, "name", oldValue, newValue);
	}
}
