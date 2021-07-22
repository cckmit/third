package org.opoo.apps.database;

import org.opoo.apps.database.spring.DatabaseProperties;
import org.opoo.apps.test.SpringTests;

public class DatabasePropertiesTest2 extends SpringTests {
	static{
		System.setProperty("apps.home", "C:\\apps");
	}
	
	
	public void testPut(){
			
		System.setProperty("apps.home", "C:\\apps");
		
		DatabaseProperties properties = DatabaseProperties.getInstance();
		properties.put("doCusterTest", "test-" + System.currentTimeMillis());
	}
}
