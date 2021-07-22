package cn.redflagsoft.base.test;

import org.opoo.apps.test.StrutsTests;


/**
 * Struts2 测试基础类。
 * 
 * 已隐含了启动应用程序（包含启动 Spring 容器）的操作。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class BaseStrutsTests extends StrutsTests {

	/* (non-Javadoc)
	 * @see org.opoo.apps.test.StrutsTests#getContextLoaderClassName()
	 */
	@Override
	protected String getContextLoaderClassName() {
		return TestParams.contextLoaderClassName;
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.test.StrutsTests#getInstanceLocation()
	 */
	@Override
	protected String getInstanceLocation() {
		return TestParams.instanceLocation;
	}
	
}
