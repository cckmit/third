/*
 * $Id: OrgBeforeDeleteListener.java 6299 2013-08-13 01:56:24Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.event2;

import cn.redflagsoft.base.bean.Org;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface OrgBeforeDeleteListener {
	void orgBeforeDelete(Org org);
}
