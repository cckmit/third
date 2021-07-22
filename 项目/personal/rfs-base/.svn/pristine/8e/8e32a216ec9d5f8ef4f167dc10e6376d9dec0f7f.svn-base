package cn.redflagsoft.base.security;

import org.opoo.apps.web.context.ConfigurationProviderImpl;

import cn.redflagsoft.base.test.BaseTests;

public class WeavingTest extends BaseTests {
	static{
		System.setProperty(ConfigurationProviderImpl.ENV_KEY_SPRING_CONTEXT_CONFIG_LOCATIONS, 
				"E:\\java-works\\rfs-base-1.5.x\\src\\main\\webapp\\WEB-INF\\applicationContext-security.xml");
	}
	
	
	public void testAnnotationSecuredMethod(){
		AnnotationObject ao = new AnnotationObject();
		ao.doSomething();
	}
}
