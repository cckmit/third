package org.opoo.apps.dv.office.dao.impl;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opoo.apps.dv.office.model.OfficeMetaData;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class OfficeConversionHibernateDaoTest2 {

	static ClassPathXmlApplicationContext ctx;
	static OfficeConversionHibernateDao dao;
	
	@BeforeClass
	public static void init() throws Exception{
		ctx = new ClassPathXmlApplicationContext("dv/spring-dv.xml");
		dao = (OfficeConversionHibernateDao) ctx.getBean("officeConversionDao");
	}
	
	@AfterClass
	public static void destroy(){
		ctx.close();
	}
	
	@Test
	public void test() {
		System.out.println(dao);
		OfficeMetaData data = new OfficeMetaData(102, 1000L, "test.doc", 1238376L, 2, 1, null);
		dao.saveMetaData(data);
		System.out.println(data);
		assertTrue(data.getId() > 0L);
	}
}
