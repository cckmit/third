/*
 * $Id: SupervisionUser.java 6335 2013-12-11 06:30:56Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;

/**
 * 督查系统
 */
public class SupervisionUser extends InternalUser {
	private static final long serialVersionUID = 137271234891038340L;
	private static final SupervisionUser instance = new SupervisionUser();
	
	public static final String USERNAME = "$supervision";
	public static final long USER_ID = 14L;
	
	public SupervisionUser() {
		super(USERNAME, USER_ID, "电子督查");
	}

	public static SupervisionUser getInstance(){
		return instance;
	}
}
