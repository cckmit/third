package cn.redflagsoft.base.test;

import org.opoo.apps.lifecycle.spring.AppsContextLoader;
import org.opoo.apps.test.SpringTests;

/**
 * 测试基础类。
 * 
 * 已隐含了启动应用程序（包含启动 Spring 容器）的操作。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class BaseTests extends SpringTests {

	/* (non-Javadoc)
	 * @see org.opoo.apps.test.SpringTests#createContextLoader()
	 */
	@Override
	protected AppsContextLoader createContextLoader() {
		return TestParams.createContextLoader();
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.test.SpringTests#getInstanceLocation()
	 */
	@Override
	protected String getInstanceLocation() {
		return TestParams.instanceLocation;
	}
}
