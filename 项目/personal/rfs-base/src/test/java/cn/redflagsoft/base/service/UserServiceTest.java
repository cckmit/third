/*
 * $Id: UserServiceTest.java 4352 2011-04-26 05:56:19Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import org.opoo.apps.security.SecurityUtils;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserHolder;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import cn.redflagsoft.base.test.BaseTests;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class UserServiceTest extends BaseTests {

	protected UserService adminUserService;

	public void test0(){
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("rfsa", "123"));
		System.out.println(adminUserService);
		
		User user = UserHolder.getUser();
		boolean granted = SecurityUtils.isGranted(user, "ROLE_ADMIN");
		System.out.println(granted);
		
		adminUserService.findAllAdminUser(null);
	}
}
