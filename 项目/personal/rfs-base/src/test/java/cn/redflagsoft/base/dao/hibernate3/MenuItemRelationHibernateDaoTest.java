package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import cn.redflagsoft.base.bean.MenuItemRelation;
import cn.redflagsoft.base.dao.MenuItemRelationDao;

public class MenuItemRelationHibernateDaoTest extends AbstractDaoSpringTests {
	protected MenuItemRelationDao menuItemRelationDao;
	
	public void testSetUp(){
		assertNotNull(menuItemRelationDao);
	}
	
	public void testFind(){
		List<MenuItemRelation> list = menuItemRelationDao.find();
		System.out.println(list);
		assertNotNull(list);
		
		MenuItemRelation m = new MenuItemRelation();
		m.setDisplayOrder(200);
		m.setSubId(300L);
		m.setSupId(400L);
		menuItemRelationDao.save(m);
		
		list = menuItemRelationDao.find();
		System.out.println(list);
		assertNotNull(list);
	}
}
