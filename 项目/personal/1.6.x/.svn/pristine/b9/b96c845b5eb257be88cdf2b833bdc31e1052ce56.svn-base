package org.opoo.apps.database;

import java.io.File;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.opoo.apps.AppsHome;


import junit.framework.TestCase;

public class DefaultDataSourceProviderTest extends TestCase {

	public void testDbcpDS(){
		BasicDataSourceFactory f = new BasicDataSourceFactory();
		 String serverURL = (new StringBuilder()).append("jdbc:hsqldb:").append(AppsHome.getAppsHome()).append(File.separator).append("database").append(File.separator).append("apps").toString();
	     System.out.println(serverURL); 
	     
	}
}
