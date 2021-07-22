package cn.redflagsoft.base.menu.migration;

import cn.redflagsoft.base.test.BaseTests;

public class MenuMigration2Test extends BaseTests {
	static{
		System.setProperty("apps.home", "e:\\work.home\\apps.dev");
	}
	
	public MenuMigration2Test() {
		super();
	}

	
	public void testRun() {
		super.setComplete();
		
		MenuMigration2 mm2 = new MenuMigration2();
		mm2.run();
	}
}
