package cn.redflagsoft.base.menu.migration;

import cn.redflagsoft.base.test.BaseTests;

public class MenuMigrationTest extends BaseTests {
	static{
		System.setProperty("apps.home", "e:\\work.home\\apps.dev");
	}
	
	public MenuMigrationTest(){
		super();
	}
	
	public void testProcess(){
		super.setComplete();
		
		MenuMigration mg = new MenuMigration();
		mg.run();
	}

}
