package org.opoo.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.orm.hibernate3.HibernateCallback;


public class HibernateUtils {
	
	public static Object execute(Configuration cfg, HibernateCallback action){
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Object result = action.doInHibernate(session);
			tx.commit();
			return result;
		} catch (Exception e) {
			tx.rollback();
			throw new HibernateException(e);
		}finally{
			session.close();
			factory.close();
		}
	}
	
	public static Configuration createAnnotationConfig(){
		Configuration cfg = new Configuration();
		//cfg.addResource("org/opoo/apps/bean/core/SysId.hbm.xml");
		//cfg.addResource("org/opoo/apps/bean/core/Attachment.hbm.xml");
		//cfg.addResource("org/opoo/apps/security/Security.hbm.xml");
		//cfg.addResource("org/opoo/apps/conversion/Conversion.hbm.xml");
		cfg.addResource("org/opoo/apps/dv/office/model/OfficeConversion.hbm.xml");
		//org/opoo/apps/conversion/Conversion.hbm.xml
		cfg.setProperty("hibernate.show_sql", "true");
		cfg.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
//		cfg.setProperty("hibernate.connection.url", "jdbc:hsqldb:hsql://localhost:20100/");
//		cfg.setProperty("hibernate.connection.username", "sa");
		cfg.setProperty("hibernate.dialect", org.hibernate.dialect.HSQLDialect.class.getName());
		return cfg;
	}
	
	public static void buildSchema(){
		Configuration config = createAnnotationConfig();
		//config.setProperty("hibernate.dialect", Oracle10gDialect.class.getName());
		SchemaExport export = new SchemaExport(config);
		export.create(true, false);
	}
	
	public static void main(String[] args){
		buildSchema();
	}
}
