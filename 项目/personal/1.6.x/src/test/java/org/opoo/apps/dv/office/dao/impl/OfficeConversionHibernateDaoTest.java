package org.opoo.apps.dv.office.dao.impl;

import static org.junit.Assert.*;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opoo.apps.dv.office.model.OfficeMetaData;
import org.opoo.apps.id.TimestampIdGeneratorProvider;

public class OfficeConversionHibernateDaoTest {

	protected static SessionFactory factory;
	protected static OfficeConversionHibernateDao dao;
	
	@BeforeClass
	public static void init() throws Exception{
		Configuration cfg = new Configuration();
		cfg.addResource("org/opoo/apps/dv/office/model/OfficeConversion.hbm.xml");
		cfg.setProperty("hibernate.show_sql", "true");
		cfg.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
		cfg.setProperty("hibernate.connection.url", "jdbc:hsqldb:hsql://localhost:20100/");
		cfg.setProperty("hibernate.connection.username", "sa");
		cfg.setProperty("hibernate.dialect", org.hibernate.dialect.HSQLDialect.class.getName());
		factory = cfg.buildSessionFactory();
		
		dao = new OfficeConversionHibernateDao();
		dao.setSessionFactory(factory);
		dao.setIdGeneratorProvider(new TimestampIdGeneratorProvider());

		dao.afterPropertiesSet();
	}
	
	@AfterClass
	public static void destroy(){
		factory.close();
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
