package cn.redflagsoft.base.test;

import org.opoo.apps.lifecycle.Application;

public class SampleBaseTest extends BaseTests {
	
	public void testPresent(){
		assertNotNull(Application.getContext().getPresenceManager());
	}
}
