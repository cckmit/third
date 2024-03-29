/*
 * $Id: MethodTest.java 4446 2011-06-30 03:23:10Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import org.junit.Test;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class MethodTest {

	public void saySomething(String... msg){
		System.out.println(msg == null);
		if(msg != null){
			System.out.println(msg.length);
			for(String m:msg){
				System.out.println(m);
			}
		}
	}
	
	@Test
	public void testSay(){
		MethodTest test = new MethodTest();
		test.saySomething();
		test.saySomething("a","b");
	}
}
