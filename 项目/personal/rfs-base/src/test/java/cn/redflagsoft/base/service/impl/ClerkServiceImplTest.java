/*
 * $Id$
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import cn.redflagsoft.base.test.BaseTests;
import cn.redflagsoft.base.vo.UserClerkVO;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ClerkServiceImplTest extends BaseTests {

	protected ClerkServiceImpl clerkService;
	
	public void testFindPageableUserClerkVOs(){
		ResultFilter filter = new ResultFilter(null, Order.asc("b.displayOrder"), 100, 20);
		List<String> findUserClerkUsernames = clerkService.getClerkDao().findUserClerkUsernames(filter);
		for (String string : findUserClerkUsernames) {
			System.out.println(string);
		}
		System.out.println("-------------------------");
		filter = new ResultFilter(null, null, 120, 20);
		findUserClerkUsernames = clerkService.getClerkDao().findUserClerkUsernames(filter);
		for (String string : findUserClerkUsernames) {
			System.out.println(string);
		}
		System.out.println("-------------------------");
		filter = new ResultFilter(null, null, 140, 20);
		findUserClerkUsernames = clerkService.getClerkDao().findUserClerkUsernames(filter);
		for (String string : findUserClerkUsernames) {
			System.out.println(string);
		}
		
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("rfsa", "*", new GrantedAuthority[]{new GrantedAuthorityImpl("ROLE_ADMIN")}));
		
		filter = new ResultFilter(null, null, 100, 20);
		List<UserClerkVO> list1 = clerkService.findUserClerkVOs(filter);
		
		filter = new ResultFilter(null, null, 120, 20);
		List<UserClerkVO> list2 = clerkService.findUserClerkVOs(filter);
		
		filter = new ResultFilter(null, null, 140, 20);
		List<UserClerkVO> list3 = clerkService.findUserClerkVOs(filter);
		
		filter = new ResultFilter(null, null, 160, 20);
		List<UserClerkVO> list4 = clerkService.findUserClerkVOs(filter);
		
		for (UserClerkVO userClerkVO : list1) {
			System.out.println(userClerkVO.getUsername());
		}
		System.out.println("-------------------------");
		for (UserClerkVO userClerkVO : list2) {
			System.out.println(userClerkVO.getUsername());
		}
		System.out.println("-------------------------");
		for (UserClerkVO userClerkVO : list3) {
			System.out.println(userClerkVO.getUsername());
		}
		System.out.println("-------------------------");
		for (UserClerkVO userClerkVO : list4) {
			System.out.println(userClerkVO.getUsername());
		}
	}
}
