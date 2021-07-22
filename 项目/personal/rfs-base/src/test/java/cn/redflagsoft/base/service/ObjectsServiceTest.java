package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.apps.bean.core.Attachment;
import org.opoo.apps.util.SerializableUtils;

import cn.redflagsoft.base.dao.hibernate3.ObjectsHibernateDao;
import cn.redflagsoft.base.test.BaseTests;


public class ObjectsServiceTest extends BaseTests {
	protected ObjectsService objectsService;
	protected ObjectsHibernateDao objectsDao;
	
	public void etestFindAttachments() throws Exception{
		List<Attachment> list = objectsService.findAttachments(2003, 2003, 2952L, 0);
		String serialize = SerializableUtils.toJSON(list);
		System.out.println(serialize);
		System.out.println(list);
	}
	
	
	public void testFindAttachments3() throws Exception{
		List<Attachment> list = objectsDao.findAttachments3((short)2003, (short)2003, 2952L);
		String serialize = SerializableUtils.toJSON(list);
		System.out.println(serialize);
		System.out.println(list);
	}
}
