/*
 * $Id: SchedulerUser.java 6335 2013-12-11 06:30:56Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;


/**
 * 工作流。
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class SchedulerUser extends InternalUser{
	private static final long serialVersionUID = 2977400421947701154L;
	private static final SchedulerUser instance = new SchedulerUser();
	
	public static final String USERNAME = "$scheduler";
	public static final long USER_ID = 13L;
	/**
	 * @param username
	 * @param userId
	 */
	public SchedulerUser() {
		super(USERNAME, USER_ID, "定时任务");
	}
	
	public static SchedulerUser getInstance(){
		return instance;
	}
}
