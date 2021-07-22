/*
 * $Id: UserManagerTest.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
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
