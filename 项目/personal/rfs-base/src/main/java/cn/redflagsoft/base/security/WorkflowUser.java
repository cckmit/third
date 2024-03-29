/*
 * $Id: WorkflowUser.java 6335 2013-12-11 06:30:56Z lcj $
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
public class WorkflowUser extends InternalUser{
	private static final long serialVersionUID = 2977400421947701154L;
	private static final WorkflowUser instance = new WorkflowUser();
	
	public static final String USERNAME = "$workflow";
	public static final long USER_ID = 11L;
	/**
	 * @param username
	 * @param userId
	 */
	public WorkflowUser() {
		super(USERNAME, USER_ID, "工作流");
	}
	
	public static WorkflowUser getInstance(){
		return instance;
	}
}
