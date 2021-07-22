/*
 * $Id: UserClerk.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;

import java.io.Serializable;

import org.opoo.apps.security.User;

import cn.redflagsoft.base.bean.Clerk;

/**
 * 根据User和Clerk组装的对象。
 * 
 * @author Alex Lin
 * 
 */
public class UserClerk implements Serializable {
	private static final long serialVersionUID = -269150805098907424L;
	private User user;
	private Clerk clerk;
	
	public UserClerk(User user, Clerk clerk){
		this.user = new PasswordProtectedUserWrapper(user);
		this.clerk = clerk;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the clerk
	 */
	public Clerk getClerk() {
		return clerk;
	}

	/**
	 * @param clerk the clerk to set
	 */
	public void setClerk(Clerk clerk) {
		this.clerk = clerk;
	}
}
