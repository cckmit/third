package cn.redflagsoft.base.menu.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.Group;
import org.opoo.apps.security.GroupManager;
import org.opoo.apps.security.UserManager;
import org.opoo.cache.Cache;
import org.opoo.cache.TimedExpirationMap;

import cn.redflagsoft.base.bean.MenuItem;
import cn.redflagsoft.base.bean.RoleMenuRemark;
import cn.redflagsoft.base.dao.MenuItemDao;
import cn.redflagsoft.base.dao.MenuItemRelationDao;
import cn.redflagsoft.base.dao.RoleMenuItemDao;
import cn.redflagsoft.base.dao.RoleMenuRemarkDao;
import cn.redflagsoft.base.menu.ActionManager;
import cn.redflagsoft.base.menu.Menu;
import cn.redflagsoft.base.menu.MenuNotFoundException;
import cn.redflagsoft.base.menu.MenuService;
import cn.redflagsoft.base.menu.RoleMenu;


/**
 * 菜单服务实现类。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class MenuServiceImpl implements MenuService {

	protected final Log log = LogFactory.getLog(getClass());
	private Cache<Long,MenuImpl> menuCache;
	protected Cache<Long,String> missingMenu = new TimedExpirationMap<Long, String>("missingMenu", 600 * 1000, 300 * 1000);
	private MenuItemDao menuItemDao;
	private MenuItemRelationDao menuItemRelationDao;
	private RoleMenuItemDao roleMenuItemDao;
	private RoleMenuRemarkDao roleMenuRemarkDao;
	private ActionManager actionManager;
	
	/**
	 * 获取指定用户的一级菜单。
	 * 多个角色的菜单将会合并。
	 * 
	 * @param username
	 * @return
	 */
	public List<RoleMenu> findMenusByUsername(String username){
		UserManager userManager = Application.getContext().getUserManager();
		//用户的所有组（角色）
		List<Group> list = ((GroupManager) userManager).findGroupsOfUser(username);
		
		List<RoleMenu> roleMenus = new ArrayList<RoleMenu>();
		List<Long> roleMenuIds = new ArrayList<Long>();
		
		Group group;
		for(int i = 0 ; i < list.size() ; i++){
			group = list.get(i);
			List<RoleMenu> tmp = findMenusByRoleId(group.getId());
			if(!tmp.isEmpty()){
				for(RoleMenu rm: tmp){
					if(!roleMenuIds.contains(rm.getId())){
						roleMenuIds.add(rm.getId());
						roleMenus.add(rm);
					}else{
						String msg = String.format("用户 '%s' 拥有的角色  '%s' 中有与其它角色重复的菜单项  '%s'，这个菜单在合并时被忽略。", 
								username, group.getName(), rm.getName());
						log.warn(msg);
					}
				}
			}
		}
		//
		return roleMenus;
	}
	
	public List<RoleMenu> findMenusByRoleId(Long roleId) {
		//final List<RoleMenuItem> list = roleMenuItemDao.findByRoleId(roleId);
		//只有顶级菜单的ID
		List<Long> ids = roleMenuItemDao.findMenuIdsByRoleId(roleId);
		//当前角色所有菜单（包括下级菜单）的备注
		Map<Long, RoleMenuRemark> map = roleMenuRemarkDao.findByRoleId(roleId);
		List<RoleMenu> roleMenus = new ArrayList<RoleMenu>(ids.size());
		for(Long id: ids){
			RoleMenu rm = wrapMenu(id, map);
			roleMenus.add(rm);
		}
		return roleMenus;
	}
	
	private RoleMenu wrapMenu(Long id, Map<Long,RoleMenuRemark> remarks) {
		MenuImpl impl = getMenuImpl(id);
		return new RoleMenuWrapper(impl, remarks);
	}

	/**
	 * 用角色备注里的名称和label替换菜单本身的名称和label。
	 * 
	 * @param menu
	 * @param rmr
	 * @return
	 */
	protected Menu wrapMenu(final Menu menu, final RoleMenuRemark rmr){
		if(rmr == null){
			return menu;
		}else{
			return new MenuWrapper(menu){

				/* (non-Javadoc)
				 * @see cn.redflagsoft.base.menu.impl.MenuWrapper#getLabel()
				 */
				@Override
				public String getLabel() {
					if(StringUtils.isNotBlank(rmr.getLabel())){
						return rmr.getLabel();
					}
					return super.getLabel();
				}

				/* (non-Javadoc)
				 * @see cn.redflagsoft.base.menu.impl.MenuWrapper#getName()
				 */
				@Override
				public String getName() {
					if(StringUtils.isNotBlank(rmr.getName())){
						return rmr.getName();
					}
					return super.getName();
				}
			};
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws MenuNotFoundException
	 */
	public MenuImpl getMenuImpl(Long id) throws MenuNotFoundException{
		MenuImpl menu = menuCache.get(id);
		if(menu == null){
			String missing = missingMenu.get(id);
			if(missing != null){
				throw new MenuNotFoundException(id);
			}
			
			MenuItem bean = menuItemDao.get(id);
			if(bean == null){
				missingMenu.put(id, "Y");
				throw new MenuNotFoundException(id);
			}
			/*
			//查询子菜单
			List<MenuItemRelation> list = menuItemRelationDao.findBySupId(id);
			//Action
			Action action = null;
			if(bean.getActionId() != null){
				action = getAction(bean.getActionId());
			}
			*/
			
			//因为Menu可能要序列化，所以不能把不能序列化的manager传入。
			menu = new MenuImpl(bean);
			menuCache.put(id, menu);
		}
		return menu;
	}
	

	
//	public List<MenuItemRelation> findMenuItemRelationsBySupId(long supId){
//		return menuItemRelationDao.findBySupId(supId);
//	}
	
	

	public Menu getMenu(Long id){
		try {
			return getMenuImpl(id);
		} catch (MenuNotFoundException e) {
			log.debug(e.getMessage());
			return null;
		}
	}
	
	

	/**
	 * @return the menuCache
	 */
	public Cache<Long, MenuImpl> getMenuCache() {
		return menuCache;
	}

	/**
	 * @param menuCache the menuCache to set
	 */
	public void setMenuCache(Cache<Long, MenuImpl> menuCache) {
		this.menuCache = menuCache;
	}


	/**
	 * @return the menuItemDao
	 */
	public MenuItemDao getMenuItemDao() {
		return menuItemDao;
	}

	/**
	 * @param menuItemDao the menuItemDao to set
	 */
	public void setMenuItemDao(MenuItemDao menuItemDao) {
		this.menuItemDao = menuItemDao;
	}

	/**
	 * @return the menuItemRelationsDao
	 */
	public MenuItemRelationDao getMenuItemRelationDao() {
		return menuItemRelationDao;
	}

	/**
	 * @param menuItemRelationsDao the menuItemRelationsDao to set
	 */
	public void setMenuItemRelationDao(MenuItemRelationDao menuItemRelationsDao) {
		this.menuItemRelationDao = menuItemRelationsDao;
	}


	/**
	 * @return the roleMenuItemDao
	 */
	public RoleMenuItemDao getRoleMenuItemDao() {
		return roleMenuItemDao;
	}

	/**
	 * @param roleMenuItemDao the roleMenuItemDao to set
	 */
	public void setRoleMenuItemDao(RoleMenuItemDao roleMenuItemDao) {
		this.roleMenuItemDao = roleMenuItemDao;
	}

	/**
	 * @return the roleMenuRemarkDao
	 */
	public RoleMenuRemarkDao getRoleMenuRemarkDao() {
		return roleMenuRemarkDao;
	}

	/**
	 * @param roleMenuRemarkDao the roleMenuRemarkDao to set
	 */
	public void setRoleMenuRemarkDao(RoleMenuRemarkDao roleMenuRemarkDao) {
		this.roleMenuRemarkDao = roleMenuRemarkDao;
	}


	/**
	 * @return the actionManager
	 */
	public ActionManager getActionManager() {
		return actionManager;
	}

	/**
	 * @param actionManager the actionManager to set
	 */
	public void setActionManager(ActionManager actionManager) {
		this.actionManager = actionManager;
	}
}
