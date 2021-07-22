/*
 * $Id: MethodTest.java 4446 2011-06-30 03:23:10Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
