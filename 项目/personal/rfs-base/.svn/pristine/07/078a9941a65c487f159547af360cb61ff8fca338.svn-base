package cn.redflagsoft.base.menu.impl;

import java.util.List;

import cn.redflagsoft.base.bean.Action;
import cn.redflagsoft.base.menu.Menu;
import cn.redflagsoft.base.menu.MenuManager;
import cn.redflagsoft.base.menu.Submenu;
import cn.redflagsoft.base.test.BaseTests;

public class MenuManagerImplTest extends BaseTests {
	protected MenuManager menuManagerV2;
	
	
	public void etestSetUp(){
		assertNotNull(menuManagerV2);
		System.out.println(menuManagerV2);
	}
	
	public void etestCreateMenu(){
		Menu menu = menuManagerV2.createMenu((Long)null, "项目查询", "项目查询", null, null, 
				(Menu)null, (Action)null, (short)1, (byte)1, null);
		System.out.println(menu);
		//super.setComplete();
	}
	
	public void testFindMenus(){
		List<Menu> list = ((MenuManagerImpl)menuManagerV2).findSuperMenus();
		for(Menu menu: list){
			System.out.println(menu);
		}
		
		
		Menu menu = menuManagerV2.getMenu(7L);
		StringBuffer sb = new StringBuffer();
		makeXML(menu, sb, 0);
		System.out.println(sb.toString());
	}
	
	private void makeXML(Menu menu, StringBuffer sb, int level){
		String blanks = blanks(level);
		String s = blanks + "<node id='%s' name='%s' label='%s' icon='%s' loc='%s' inherited='%s'>\n";
		String loc = "";
		if(menu.getAction() != null){
			loc = menu.getAction().getLocation();
		}
		String inherited = "false";
		if(menu instanceof Submenu && ((Submenu)menu).isInherited()){
			inherited = "true";
		}
		
		sb.append(String.format(s, menu.getId(), menu.getName(), menu.getLabel(), menu.getIcon(), loc, inherited));
		
		List<Submenu> list = menu.getSubmenus();
		if(!list.isEmpty()){
			for(Submenu smenu: list){
				makeXML(smenu, sb, level + 1);
			}
		}
		
		sb.append(blanks + "</node>\n");
	}
	
	private String blanks(int level){
		String s = "";
		for(int i = 0 ; i < level ; i++){
			s += "    ";
		}
		return s;
	}
}
