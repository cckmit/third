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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class FileNoManagerImplTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		String fileNo = FileNoManagerImpl.generateFileNo("深南发改[${year}]第${value}号", 12, 1);
		System.out.println(fileNo);
		//assertEquals("深南发改[2012]第012号", fileNo);
		
		org.springframework.transaction.aspectj.AnnotationTransactionAspect s;
	}

}
