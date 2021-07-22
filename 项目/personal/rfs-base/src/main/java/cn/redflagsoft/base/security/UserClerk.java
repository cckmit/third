/*
 * $Id: UserClerk.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.security;

import java.io.Serializable;

import org.opoo.apps.security.User;

import cn.redflagsoft.base.bean.Clerk;

/**
 * ����User��Clerk��װ�Ķ���
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
