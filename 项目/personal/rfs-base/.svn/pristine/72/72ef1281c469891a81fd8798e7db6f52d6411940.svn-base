package cn.redflagsoft.base.menu.migration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.UserManager;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.hibernate3.HibernateDao;
import org.opoo.ndao.support.ResultFilter;
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
 * ****
 * 该迁移程序运行后，只生成了与原菜单项一致的菜单项，于原菜单组——菜单项对应一致
 * 的菜单结构，但不能迁移已分配给用户组的菜单。
 * 运行该迁移程序后，必须手动通过工具/界面等将新菜单项重新分配给用户组。
 * ***
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class MenuMigration2 implements Runnable{
	
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
	/////////////////////////////////////////////////
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
	
	////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 处理原来菜单的MenuGroup以及与Menu之间的关系。
	 */
	@SuppressWarnings("deprecation")
	public void processAllMenuGroups(){
		Order order = Order.asc("displayOrder");
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setOrder(order);
		List<MenuGroup> list = menuManager.getMenuGroupDao().find(filter);
		for (MenuGroup mg : list) {
			cn.redflagsoft.base.menu.Menu menu = newMenuManager.createMenu(newMenuIdIndex++, mg.getLabel(),
					mg.getGroupName(), mg.getIcon(), mg.getLogo(), null, null, 1, (byte)1, "MG-" + mg.getId());
			menus.put("MG" + mg.getId(), menu);
		}
	}
	
	
	////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 */
	public void processAllMenusAndRelations(){
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
	@SuppressWarnings("deprecation")
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
		action.setType( 1);
		action.setUid("UID-" + (action.getId()));
		
		
		actionManager.saveAction(action);
		actions.put(menu.getAuthority(), action);
		
		
		cn.redflagsoft.base.menu.Menu mm = newMenuManager.createMenu(newMenuIdIndex++, menu.getName(), menu.getLabel2(),
				action.getIcon(), menu.getLogo(), null, action, 1, (byte)1, menu.getLabel());
		menus.put(menu.getAuthority(), mm);
		
		Long gid = menu.getGroupId();
		cn.redflagsoft.base.menu.Menu superior = menus.get("MG" + gid);
		if(superior == null){
			throw new IllegalStateException("之前保存的原菜单组信息不存在，数据不完整，数据迁移不能完成。");
		}
		
		
		//newMenuManager.addMenuToSuperior(superior, mm, menu.getDisplayOrder());
		superior.addSubmenu(mm, menu.getDisplayOrder());
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	
	
	private long newMenuIdIndex = 1L;
	private long actionIdIndex = 1L;
	private Map<String,Action> actions = new LinkedHashMap<String, Action>();
	private Map<String, cn.redflagsoft.base.menu.Menu> menus = new LinkedHashMap<String, cn.redflagsoft.base.menu.Menu>();




	@Transactional(propagation=Propagation.REQUIRED)
	public void run() {
		this.clear();
		this.processAllMenuGroups();
		this.processAllMenusAndRelations();
	}
}
