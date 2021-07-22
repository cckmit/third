/*
 * $Id: UserClerkHolder.java 5248 2011-12-22 03:11:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.security;

import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserHolder;
import org.springframework.security.AccessDeniedException;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.service.ClerkService;

/**
 * ��ǰ��¼�û���Clerk�����ߡ�
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
	 * ��ȡ��ǰ��¼�û���clerk��Ϣ��
	 * @return
	 * @throws AccessDeniedException
	 */
	public static Clerk getClerk() throws AccessDeniedException{
		Clerk clerk = UserClerkHolder.getNullableClerk();
		if(clerk == null){
			throw new AccessDeniedException("�Ҳ�����Ч��¼�û�Clerk��Ϣ��");
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
			throw new AccessDeniedException("�Ҳ�����Ч��¼�û�Clerk��Ϣ��");
		}
		return clerk;
	}
	
	/**
	 * ��ȡ��ǰ��¼�û���Clerk����
	 * 
	 * @return clerk����
	 * @throws AccessDeniedException ����û�û�е�¼
	 */
	public static Clerk getNullableClerk(){
		User user = UserHolder.getNullableUser();
		if(user != null){
			return getClerkService().getClerk(user.getUserId());
		}
		return null;
	}
	
	/**
	 * ��ȡ��ǰ��¼�û���Clerk����
	 * ���û�е�¼�����ؿա�
	 * 
	 * @return clerk����
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
