/*
 * $Id: SchemaBuilder.java 4446 2011-06-30 03:23:10Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import cn.redflagsoft.base.bean.Issue;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class SchemaBuilder {
	public static Configuration createAnnotationConfig(){
		Configuration cfg = new Configuration();
		//cfg.addResource("cn/redflagsoft/base/bean/AuditLog.hbm.xml");
//		cfg.addResource("cn/redflagsoft/base/bean/ContentHolder.hbm.xml");
//		cfg.addResource("cn/redflagsoft/milestone/bean/ProjectAdvice.hbm.xml");
		cfg.addResource("cn/redflagsoft/base/bean/RFSEntityObjectToBiz.hbm.xml");
		cfg.setProperty("hibernate.show_sql", "true");
		
		//ORACLE
		cfg.setProperty("hibernate.connection.driver_class", "oracle.jdbc.driver.OracleDriver"/*"org.hsqldb.jdbcDriver"*/);
		cfg.setProperty("hibernate.connection.url", "jdbc:oracle:thin:@192.168.18.15:1521:ORCL"/*"jdbc:hsqldb:hsql://localhost:20100/"*/);
		cfg.setProperty("hibernate.connection.username", "dev_015");
		cfg.setProperty("hibernate.connection.password", "bestbnf");
		cfg.setProperty("hibernate.dialect", org.hibernate.dialect.Oracle10gDialect.class.getName());
		
		//HSQLDB
//		cfg.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
//		cfg.setProperty("hibernate.connection.url", "jdbc:hsqldb:hsql://localhost:20100/");
//		cfg.setProperty("hibernate.connection.username", "sa");
//		cfg.setProperty("hibernate.dialect", org.hibernate.dialect.HSQLDialect.class.getName());
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
//		SessionFactory factory = createAnnotationConfig().buildSessionFactory();
//		Session session = factory.openSession();
//		Transaction tx = session.beginTransaction();
//		List<?> list = session.createQuery("from ProjectAdvice").list();
//		System.out.println(list);
//		
//		Issue pa = new Issue();
//		pa.setId(1L);
//		session.save(pa);
//		
//		tx.commit();
//		session.close();
	}
}
