package org.opoo.apps.test;

import org.opoo.apps.lifecycle.spring.AppsContextLoader;

abstract class TestConstants {
	static String INSTANCE_LOCATION = "classpath:apps_build.xml";
	static Class<? extends AppsContextLoader> CONTEXT_LOADER_CLASS = AppsContextLoader.class;
	static String CONTEXT_LOADER_CLASS_NAME = CONTEXT_LOADER_CLASS.getName();
	
	static AppsContextLoader createContextLoader(){
		return new AppsContextLoader();
	}
}
