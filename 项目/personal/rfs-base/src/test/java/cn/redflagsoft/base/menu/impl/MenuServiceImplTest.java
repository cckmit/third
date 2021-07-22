package cn.redflagsoft.base.menu.impl;

import java.util.List;

import cn.redflagsoft.base.menu.Menu;
import cn.redflagsoft.base.menu.MenuService;
import cn.redflagsoft.base.menu.RoleMenu;
import cn.redflagsoft.base.menu.Submenu;
import cn.redflagsoft.base.test.BaseTests;


/**
 * 执行这个测试必须准备业务数据。
 * 
 <pre>
INSERT INTO ACTION(ID, UID, NAME, LOCATION, TP, STATUS) VALUES(1, '1001', '业务受理', '/shouli.jsp', 1, 1);
INSERT INTO ACTION(ID, UID, NAME, LOCATION, TP, STATUS) VALUES(2, '1002', '业务承办', '/chengban.jsp', 1, 1);
INSERT INTO ACTION(ID, UID, NAME, LOCATION, TP, STATUS) VALUES(3, '1003', '业务审核', '/shenhe.jsp', 1, 1);
INSERT INTO ACTION(ID, UID, NAME, LOCATION, TP, STATUS) VALUES(4, '1004', '业务批准', '/pizhun.jsp', 1, 1);
INSERT INTO ACTION(ID, UID, NAME, LOCATION, TP, STATUS) VALUES(5, '1005', '业务办结', '/banjie.jsp', 1, 1);

INSERT INTO MENU_ITEM(ID, NAME, LABEL, PARENTID, ACTIONID, TP, STATUS) VALUES(1, '受理', '受理', null, 1, 1, 1);
INSERT INTO MENU_ITEM(ID, NAME, LABEL, PARENTID, ACTIONID, TP, STATUS) VALUES(2, '承办', '承办', null, 2, 1, 1);
INSERT INTO MENU_ITEM(ID, NAME, LABEL, PARENTID, ACTIONID, TP, STATUS) VALUES(3, '审核', '审核', null, 3, 1, 1);
INSERT INTO MENU_ITEM(ID, NAME, LABEL, PARENTID, ACTIONID, TP, STATUS) VALUES(4, '批准', '批准', null, 4, 1, 1);
INSERT INTO MENU_ITEM(ID, NAME, LABEL, PARENTID, ACTIONID, TP, STATUS) VALUES(5, '办结', '办结', null, 5, 1, 1);
INSERT INTO MENU_ITEM(ID, NAME, LABEL, PARENTID, ACTIONID, TP, STATUS) VALUES(6, '业务办理', '业务办理', null, null, 1, 1);
INSERT INTO MENU_ITEM(ID, NAME, LABEL, PARENTID, ACTIONID, TP, STATUS) VALUES(7, '综合业务', '综合业务', null, null, 1, 1);
INSERT INTO MENU_ITEM(ID, NAME, LABEL, PARENTID, ACTIONID, TP, STATUS) VALUES(8, '业务办理副本', '业务办理副本', 6, null, 1, 1);

INSERT INTO MENU_ITEM_RELATION(ID, SUPID, SUBID, DISPLAY_ORDER) VALUES(1, 6, 1, 10);
INSERT INTO MENU_ITEM_RELATION(ID, SUPID, SUBID, DISPLAY_ORDER) VALUES(2, 6, 2, 20);
INSERT INTO MENU_ITEM_RELATION(ID, SUPID, SUBID, DISPLAY_ORDER) VALUES(3, 6, 4, 40);
INSERT INTO MENU_ITEM_RELATION(ID, SUPID, SUBID, DISPLAY_ORDER) VALUES(4, 6, 5, 50);
INSERT INTO MENU_ITEM_RELATION(ID, SUPID, SUBID, DISPLAY_ORDER) VALUES(5, 8, 3, 30);
INSERT INTO MENU_ITEM_RELATION(ID, SUPID, SUBID, DISPLAY_ORDER) VALUES(6, 7, 6, 10);
INSERT INTO MENU_ITEM_RELATION(ID, SUPID, SUBID, DISPLAY_ORDER) VALUES(7, 7, 8, 10);


INSERT INTO ROLE_MENU_ITEM(ID, ROLEID, MENUID, DISPLAY_ORDER) VALUES(1, 0, 6, 10);
INSERT INTO ROLE_MENU_ITEM(ID, ROLEID, MENUID, DISPLAY_ORDER) VALUES(2, 0, 8, 5);

INSERT INTO ROLE_MENU_REMARK(ID, ROLEID, MENUID, NAME, LABEL, REMARK1, REMARK2) VALUES(1, 0, 2, '我的承办', '我的承办', null, null);
INSERT INTO ROLE_MENU_REMARK(ID, ROLEID, MENUID, NAME, LABEL, REMARK1, REMARK2) VALUES(2, 0, 8, '我的业务', null, null, null);
 </pre>
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class MenuServiceImplTest extends BaseTests {
	protected MenuService menuService;

	
	public void etestSetUp(){
		Menu menu = menuService.getMenu(7L);
		System.out.println(menu.getName());
		for(Submenu smenu: menu.getSubmenus()){
			System.out.println("子菜单："  + smenu.getName() + " - " + smenu.getOrder() + " : " + smenu.isInherited());
		}
		
		//assertEquals(5, menu.getSubmenus().size());
		
		StringBuffer sb = new StringBuffer();
		makeXML(menu, sb);
		System.out.println(sb.toString());
		
		
		System.out.println(menu.getSubmenus());
	}
	
	private void makeXML(Menu menu, StringBuffer sb){
		String s = "<node id='%s' name='%s' label='%s' icon='%s' loc='%s'>\n";
		String loc = "";
		if(menu.getAction() != null){
			loc = menu.getAction().getLocation();
		}
		sb.append(String.format(s, menu.getId(), menu.getName(), menu.getLabel(), menu.getIcon(), loc));
		List<Submenu> list = menu.getSubmenus();
		if(!list.isEmpty()){
			for(Submenu smenu: list){
				makeXML(smenu, sb);
			}
		}
		
		sb.append("</node>\n");
	}
	
	public void etestFindRoleMenus(){
		List<RoleMenu> list = menuService.findMenusByRoleId(0L);
		System.out.println(list);
		
		//if(true) return;
		
		StringBuffer sb = new StringBuffer();
		for(Menu m: list){
			makeXML(m, sb);
		}
		
		System.out.println(sb.toString());
	}
	
	public void testPresent(){
		assertNotNull(menuService);
	}
}
