/*
 * $Id: UserManagerTest.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.security;

import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.UserManager;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import cn.redflagsoft.base.test.BaseTests;


/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class UserManagerTest extends BaseTests {

	public void test00(){
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("rfsa", "123"));
		
		UserManager manager = Application.getContext().getUserManager();
		System.out.println(manager);
	}
}
