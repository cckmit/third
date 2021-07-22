package org.opoo.apps.database;

import java.lang.annotation.Annotation;
import java.util.List;

import org.junit.Test;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.AppsHome;
import org.opoo.apps.cache.CacheFactory;
import org.opoo.apps.cache.coherence.CoherenceClusteredCacheFactory;
import org.opoo.apps.cache.coherence.CoherenceLocalCacheFactory;
import org.opoo.apps.database.spring.DatabaseProperties;



public class DatabasePropertiesTest {
	
	public void testInit() throws Exception{
		System.setProperty("apps.home", "C:\\apps");
		System.out.println(AppsHome.getAppsHomePath());
		System.out.println(AppsGlobals.getSetupProperty("dataSourceProvider.className"));
		
		//DatabaseProperties.getInstance().init();
		DatabaseProperties properties = DatabaseProperties.getInstance();
		System.out.println(properties);
		System.out.println(properties.get("apps.sample"));
		properties.put("apps." + System.currentTimeMillis(), "a" + System.currentTimeMillis());
		System.out.println(properties);
		
//		properties.put("apps.sample2", "ÖÐÎÄ");
//		System.out.println(properties);
//		
//		properties.remove("apps.sample2");
//		System.out.println(properties);
		
		properties.remove("apps");
		
		String string = properties.get("appsInstanceId");
		System.out.println(string);
		
		
		
		Annotation[] annotations = AppsGlobals.class.getAnnotations();
		for(Annotation a: annotations){
			System.out.println(a);
		}
		
		Annotation[] annotations2 = AppsGlobals.class.getMethod("setLocalProperty", String.class, String.class).getAnnotations();
		for(Annotation a: annotations2){
			System.out.println(a);
		}
		
		
		AppsGlobals.class.getDeclaredMethods();
		
	}
	
}
