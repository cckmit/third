/*
 * $Id: NetAddressTest.java 5980 2012-08-10 02:44:06Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.ws.rest.impl;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class NetAddressTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws UnknownHostException {
		InetAddress host = InetAddress.getLocalHost();
		System.out.println(host.getHostAddress());
		byte[] bytes = host.getAddress();
		for(byte b:bytes){
			System.out.println(b & 0xFF);
		}
	}
}
