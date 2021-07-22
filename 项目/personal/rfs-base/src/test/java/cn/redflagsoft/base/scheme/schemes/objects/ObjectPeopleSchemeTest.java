/*
 * $Id: ObjectPeopleSchemeTest.java 4844 2011-09-29 09:41:23Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.objects;

import java.util.List;

import cn.redflagsoft.base.service.ObjectPeopleService;
import cn.redflagsoft.base.test.BaseTests;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ObjectPeopleSchemeTest extends BaseTests {

//	protected SchemeManager schemeManager;
	protected ObjectPeopleService objectPeopleService;
	
	public void testFindObjectsNotIn(){
//		Scheme scheme = schemeManager.getScheme("objectPeopleScheme");
		List<Long> list = objectPeopleService.findObjectIdsByPeopleId(1010L, (short) 110, (short)1002);
		System.out.println(list);
	}

}
