package cn.redflagsoft.base.menu.migration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.Group;
import org.opoo.apps.security.GroupManager;
import org.opoo.apps.security.UserManager;
import org.opoo.ndao.hibernate3.HibernateDao;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.redflagsoft.base.bean.Action;
import cn.redflagsoft.base.dao.MenuItemDao;
import cn.redflagsoft.base.dao.MenuItemRelationDao;
import cn.redflagsoft.base.menu.ActionManager;
import cn.redflagsoft.base.security.Menu;
import cn.redflagsoft.base.security.MenuGroup;
import cn.redflagsoft.base.security.MenuManagerImpl;
import cn.redflagsoft.base.security.dao.MenuDao;


/**
 * 必须在容器启动后运行。
 * 
 * 该迁移程序可将原菜单结构和已分配给用户组的菜单数据全部迁移。
 * 迁移后系统可正常运行在新的菜单系统中，新菜单系统中的中间结构
 * 表的数据将会非常大，原来每个用户组的菜单都将形成新的中间结果
 * 的一个根节点。
 * 
 * 
 * 顺序调用其public方法，或者调用run。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class MenuMigration implements Runnable {
	
	private MenuItemDao menuItemDao = Application.getContext().get("menuItemDao", MenuItemDao.class);
	private MenuItemRelationDao memuItemRelationDao = Application.getContext().get("memuItemRelationDao", MenuItemRelationDao.class);
	private UserManager userManager = Application.getContext().getUserManager();
	private MenuManagerImpl menuManager = Application.getContext().get("menuManager", MenuManagerImpl.class);
	private MenuDao menuDao = Application.getContext().get("menuDao", MenuDao.class);
	private cn.redflagsoft.base.menu.impl.MenuManagerImpl newMenuManager =
		Application.getContext().get("menuManagerV2", cn.redflagsoft.base.menu.impl.MenuManagerImpl.class);
	private ActionManager actionManager = Application.getContext().get("actionManager", ActionManager.class);
	
	
	
	
	private void execHQL(String hql){
		((HibernateDao)menuItemDao).getQuerySupport().executeUpdate(hql);
	}
	/**
	 * 清空
	 */
	public void clear(){
		String hql = "delete from RoleMenuItem";
		execHQL(hql);
		
		hql = "delete from RoleMenuRemark";
		execHQL(hql);
		
		hql = "delete from MenuItemRelation";
		execHQL(hql);
		
		hql = "update MenuItem set parentId=null";
		execHQL(hql);
		
		hql = "delete from MenuItem";
		execHQL(hql);
		
		hql = "delete from Action";
		execHQL(hql);
	}
	
	public void processAllMenus(){
		List<Menu> list = menuDao.find();
		processAllMenusToAction(list);
	}
	private void processAllMenusToAction(List<Menu> list){
		for(Menu menu: list){
			if(StringUtils.isNotBlank(menu.getApplication())){
				saveActionAndNewMenu(menu);
			}
		}
	}
	
	/**
	 * 
	 * @param m
	 * @param action
	 */
	private void saveActionAndNewMenu(Menu menu){
		Action action = new Action();
		action.setId(actionIdIndex++);
		//为了和原来的菜单的图标兼容，原来菜单的icon是根据菜单的id来定的。
		action.setIcon(menu.getId());//menu.getIcon());
		action.setLocation(menu.getApplication());
		action.setLogo(menu.getLogo());
		action.setName(menu.getName());
		action.setRemark(menu.getLabel());
		action.setStatus((byte) 1);
		action.setType(1);
		action.setUid("UID-" + (action.getId()));
		
		
		actionManager.saveAction(action);
		actions.put(menu.getAuthority(), action);
		
		
		cn.redflagsoft.base.menu.Menu mm = newMenuManager.createMenu(newMenuIdIndex++, menu.getName(), menu.getLabel2(),
				action.getIcon(), menu.getLogo(), null, action, 1, (byte)1, menu.getLabel());
		menus.put(menu.getAuthority(), mm);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * 将原有每个用户组（角色）的菜单作为新的菜单结构中的一个根节点，
	 * 以“角色 xxx 的菜单”来命名，其中xxx是用户组的名称。
	 * 经过这个方法处理后，不但可以转出新的菜单的结构，还可以按原有菜单分配
	 * 的情况自动生成RoleMenuItem信息。
	 * 方法处理完成后，即可使用新菜单，用户还是可以查询到和原来的一样的菜单
	 * 项和菜单结构。
	 * 
	 */
	public void processAllGroupMenus(){
		GroupManager gm = (GroupManager) userManager;
		List<Group> groups = gm.findGroups();
		for(Group g: groups){
			List<MenuGroup> list = menuManager.findMenusByUserGroupId(g.getId());
			System.out.println("角色：" + g.getName()  + "的菜单：" + list);
			processGroupMenu(g, list);
		}
	}
	
	private void processGroupMenu(Group group, List<MenuGroup> list){
		if(list.isEmpty()){
			return;
		}
		
		
		String name = "原" + group.getName() + "的菜单";
		cn.redflagsoft.base.menu.Menu superior = newMenuManager.createMenu(newMenuIdIndex++,
				name, name, null, null, null, null, 1, (byte)1, "角色顶级菜单");
		
		
		for (int i = 0; i < list.size(); i++) {
			processSingleGroupMenu(group, list.get(i), i, superior);
		}
	}
	
	
	private void processSingleGroupMenu(Group group, MenuGroup mg, int index, cn.redflagsoft.base.menu.Menu superior){
		//创建原菜单组对应的菜单项;
		cn.redflagsoft.base.menu.Menu menu = newMenuManager.createMenu(newMenuIdIndex++, mg.getLabel(),
				mg.getGroupName(), mg.getIcon(), mg.getLogo(), null, null, 1, (byte)1, "MGID-" + mg.getId());
		menus.put("MG" + mg.getId(), menu);
		
		
		
		
		//将上级菜单增加给当前角色
		newMenuManager.addMenuToRole(group.getId(), menu, (index + 1) * 100);
		
		
		
		//创建关联关系
		//newMenuManager.addMenuToSuperior(superior, menu, (index + 1) * 100);
		superior.addSubmenu(menu, (index + 1) * 100);
		
		
		//所有下级菜单的关联关系
		List<Menu> menus2 = mg.getMenus();
		for (int i = 0; i < menus2.size(); i++) {
			Menu oldMenu = menus2.get(i);
			cn.redflagsoft.base.menu.Menu newmenu = menus.get(oldMenu.getAuthority());
			System.out.println("找到：" + newmenu);
			menu.addSubmenu(newmenu, (i + 1) * 100);
		}
	}
	
	
	
	
	private long newMenuIdIndex = 1L;
	private long actionIdIndex = 1L;
	private Map<String,Action> actions = new LinkedHashMap<String, Action>();
	private Map<String, cn.redflagsoft.base.menu.Menu> menus = new LinkedHashMap<String, cn.redflagsoft.base.menu.Menu>();


	@Transactional(propagation=Propagation.REQUIRED)
	public void run() {
		clear();
		processAllMenus();
		processAllGroupMenus();
	}
}
