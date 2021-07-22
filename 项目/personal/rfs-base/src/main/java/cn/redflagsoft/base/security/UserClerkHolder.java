/*
 * $Id: UserClerkHolder.java 5248 2011-12-22 03:11:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;

import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserHolder;
import org.springframework.security.AccessDeniedException;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.service.ClerkService;

/**
 * 当前登录用户的Clerk持有者。
 * 
 * @author Alex Lin
 *
 */
public abstract class UserClerkHolder {
	private static ClerkService clerkService;// = Application.getContext().get("clerkService", ClerkService.class);
	
	static ClerkService getClerkService(){
		if(clerkService == null){
			clerkService = Application.getContext().get("clerkService", ClerkService.class);
		}
		return clerkService;
	}
	
	/**
	 * 获取当前登录用户的clerk信息。
	 * @return
	 * @throws AccessDeniedException
	 */
	public static Clerk getClerk() throws AccessDeniedException{
		Clerk clerk = UserClerkHolder.getNullableClerk();
		if(clerk == null){
			throw new AccessDeniedException("找不到有效登录用户Clerk信息。");
		}
		return clerk;
	}
	
	/**
	 * 
	 * @return
	 * @throws AccessDeniedException
	 */
	public static UserClerk getUserClerk() throws AccessDeniedException{
		UserClerk clerk = UserClerkHolder.getNullableUserClerk();
		if(clerk == null){
			throw new AccessDeniedException("找不到有效登录用户Clerk信息。");
		}
		return clerk;
	}
	
	/**
	 * 获取当前登录用户的Clerk对象。
	 * 
	 * @return clerk对象
	 * @throws AccessDeniedException 如果用户没有登录
	 */
	public static Clerk getNullableClerk(){
		User user = UserHolder.getNullableUser();
		if(user != null){
			return getClerkService().getClerk(user.getUserId());
		}
		return null;
	}
	
	/**
	 * 获取当前登录用户的Clerk对象。
	 * 如果没有登录，返回空。
	 * 
	 * @return clerk对象
	 */
	public static UserClerk getNullableUserClerk(){
		User user = UserHolder.getNullableUser();
		Clerk clerk = UserClerkHolder.getNullableClerk();
		if(clerk != null && user != null){
			return new UserClerk(user, clerk);
		}
		return null;
	}
	
	
	public static Clerk getClerk(long userId){
		return getClerkService().getClerk(userId);
	}
}
