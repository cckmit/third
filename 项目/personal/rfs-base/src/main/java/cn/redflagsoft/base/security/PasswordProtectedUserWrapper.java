/*
 * $Id: PasswordProtectedUserWrapper.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;

import org.opoo.apps.security.User;
import org.opoo.apps.security.UserWrapper;

/**
 * 
 * 密码收到保护的用户包装类。
 * @author Alex Lin
 *
 */
public class PasswordProtectedUserWrapper extends UserWrapper implements User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7475393272673346740L;

	/**
	 * @param arg0
	 */
	public PasswordProtectedUserWrapper(User user) {
		super(user);
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.UserWrapper#getPassword()
	 */
	@Override
	public String getPassword() {
		return null;
	}

}
