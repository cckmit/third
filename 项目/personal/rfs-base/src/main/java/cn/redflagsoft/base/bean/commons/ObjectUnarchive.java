/*
 * $Id: ObjectUnarchive.java 5550 2012-04-23 04:54:03Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean.commons;

import cn.redflagsoft.base.ObjectTypes;

/**
 * 对象退档日志。
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ObjectUnarchive extends ObjectAdmin {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3896700118696276140L;

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.RFSEntityObject#getObjectType()
	 */
	public int getObjectType() {
		return ObjectTypes.OBJECT_UNARCHIVE;
	}

}
