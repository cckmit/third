package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import cn.redflagsoft.base.bean.MenuItem;
import cn.redflagsoft.base.dao.MenuItemDao;

public class MenuItemHibernateDaoTest extends AbstractDaoSpringTests {

	protected MenuItemDao menuItemDao;
	
	
	public void testFind() {
		//fail("Not yet implemented");
		System.out.println(menuItemDao);
		assertNotNull(menuItemDao);
		List<MenuItem> list = menuItemDao.find();
		System.out.println(list);
		
	}
	
	public void testSave(){
		MenuItem menu = new MenuItem();
		menu.setActionId(1L);
		menu.setLabel("阿拉斯加绿卡涉及到");
		menu.setName("aaaaaaaaa");
		menuItemDao.save(menu);
		System.out.println(menuItemDao.find());
	}

}
