/*
 * $Id: ObjectPeopleSchemeTest.java 4844 2011-09-29 09:41:23Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
