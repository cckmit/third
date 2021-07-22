package org.opoo.apps.dv.office.dao.impl;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opoo.apps.dv.office.dao.impl.OfficeConversionJdbcDao;

public class OfficeConversionDaoImplTest {

	static BasicDataSource dataSource;
	static OfficeConversionJdbcDao dao;
	
	@BeforeClass
	public static void init() throws Exception{
		dataSource = new BasicDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@192.168.18.5:1521:ORCL");
        dataSource.setUsername("test_015");
        dataSource.setPassword("bestbnf");
        
		dao = new OfficeConversionJdbcDao();
		dao.setDataSource(dataSource);
		dao.afterPropertiesSet();
	}
	@AfterClass
	public static void destroy() throws SQLException{
		dataSource.close();
	}
	
	@Test
	public void test() {
		int metaDataCount = dao.getMetaDataCount();
		System.out.println(metaDataCount);
		assertNotNull(dao);
	}
}
