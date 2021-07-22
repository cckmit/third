package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.opoo.cache.TimedExpirationMap;

import cn.redflagsoft.base.security.PermissionType;
import cn.redflagsoft.base.security.Permissions;
import cn.redflagsoft.base.security.impl.PermissionsManagerImpl2;
import cn.redflagsoft.base.security.impl.dao.Perms;
import cn.redflagsoft.base.security.impl.dao.hibernate3.PermsHibernateDao;

public class SchemaBuilder {
	
	
	public static Configuration createAnnotationConfig(){
		Configuration cfg = new Configuration();
		//cfg.addResource("cn/redflagsoft/base/bean/AuditLog.hbm.xml");
		cfg.addResource("cn/redflagsoft/base/security/impl/dao/hibernate3/Perms.hbm.xml");
		cfg.addResource("org/opoo/apps/security/Security.hbm.xml");
		cfg.addResource("cn/redflagsoft/base/bean/ContentHolder.hbm.xml");
		cfg.addResource("cn/redflagsoft/base/bean/Comment.hbm.xml");
		cfg.addResource("cn/redflagsoft/base/bean/Amounts.hbm.xml");
		cfg.setProperty("hibernate.show_sql", "true");
		
		//ORACLE
		cfg.setProperty("hibernate.connection.driver_class", "oracle.jdbc.driver.OracleDriver"/*"org.hsqldb.jdbcDriver"*/);
		cfg.setProperty("hibernate.connection.url", "jdbc:oracle:thin:@192.168.18.15:1521:ORCL"/*"jdbc:hsqldb:hsql://localhost:20100/"*/);
		cfg.setProperty("hibernate.connection.username", "dev_012");
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
	
	
	public static void main2(String[] args){
		buildSchema();
		
		SessionFactory factory = createAnnotationConfig().buildSessionFactory();
		System.out.println(factory);
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		session.createQuery("from UserPerms a where a.id.sid=12").list();
		session.createQuery("from GroupPerms").list();
		
		PermsHibernateDao dao = new PermsHibernateDao();
		dao.setSessionFactory(factory);
		dao.afterPropertiesSet();
		
		List<Perms> list = dao.findGroupPerms(100L);
		System.out.println(list);
		dao.addUserPermission(100L, "project", PermissionType.ADDITIVE.getId(), 16L);
		dao.addUserPermission(102L, "project", PermissionType.ADDITIVE.getId(), 17L);
		
		List<Perms> perms = dao.findUserPerms("project");
		for (Perms p : perms) {
			System.out.println(p.getSid() + ":" + p.getPermissions());
		}
		
		
		PermissionsManagerImpl2 pm = new PermissionsManagerImpl2();
		pm.setPermsCache(new TimedExpirationMap<String, PermissionsManagerImpl2.PermissionsCacheEntry>("PermsCache", 20000, 10000));
		pm.setPermsDao(dao);
		
		Permissions permissions = pm.getFinalUserPerms(100L, "project", PermissionType.ADDITIVE);
		System.out.println(permissions.value());
		
		tx.commit();
		factory.close();
	}
	
	public static void main3(String[] args){
//		String qs = "from GroupPerms where id.sid in " +
//				"(select distinct gm.groupId from GroupMember gm, LiveUser u where gm.username=u.username and u.userId=?)";
		String qs = "select distinct(a) from GroupPerms a, GroupMember gm, LiveUser u " +
				"where a.id.sid=gm.groupId and gm.username=u.username and u.userId=?";

//		String qs = "from GroupPerms where id.sid in (select distinct gm.groupId from GroupMember gm where gm.username=?)";
		String qs2 = "select distinct(a) from GroupPerms a, GroupMember b where a.id.sid=b.groupId and b.username=?";
			
		SessionFactory factory = createAnnotationConfig().buildSessionFactory();
		System.out.println(factory);
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		session.createQuery(qs).setLong(0, 1000L).list();
		session.createQuery(qs2).setString(0, "a").list();
		
		tx.commit();
		factory.close();
	}
	
	public static void main(String[] args){
		buildSchema();
		SessionFactory factory = createAnnotationConfig().buildSessionFactory();
		System.out.println(factory);
		Session session = factory.openSession();
		session.createQuery("from Amounts").list();
		session.close();
		factory.close();
	}
}
