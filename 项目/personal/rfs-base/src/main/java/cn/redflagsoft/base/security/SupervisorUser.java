/*
 * $Id: SupervisorUser.java 6335 2013-12-11 06:30:56Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;


/**
 * 电子监察。
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class SupervisorUser extends InternalUser {
	private static final long serialVersionUID = 1504477165271308278L;
	private static final SupervisorUser instance = new SupervisorUser();
	
	public static final String USERNAME = "$supervisor";
	public static final long USER_ID = 12L;
	

	/**
	 * 
	 */
	public SupervisorUser() {
		super(USERNAME, USER_ID, "电子监察");
	}

	public static SupervisorUser getInstance(){
		return instance;
	}

	public static void main(String[] args){
		System.out.println(new SupervisorUser());
	}
}
