/*
 * $Id: LogoutService.java 6054 2012-10-09 06:45:08Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;

import org.springframework.security.ui.logout.LogoutHandler;



/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface LogoutService extends LogoutHandler{
	void logout();
}
