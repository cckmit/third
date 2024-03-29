/*
 * $Id: MenuManagerImpl.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.util.Assert;

import cn.redflagsoft.base.security.dao.MenuDao;
import cn.redflagsoft.base.security.dao.MenuGroupDao;

/**
 * 
 * 
 * @author Alex Lin
 * @deprecated
 */
public class MenuManagerImpl implements MenuManager, InitializingBean {

	private UserDetailsService userDetailsService;
	private MenuDao menuDao;
	private MenuGroupDao menuGroupDao;
	
	
	public List<MenuGroup> findAllMenus(){
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setOrder(Order.asc("displayOrder"));
		List<Menu> menus = getMenuDao().find(filter);
		List<MenuGroup> groups = menuGroupDao.find(filter);
		addMenusToGroup(groups, menus);
		
		return groups;
	}
	
	private void addMenusToGroup(List<MenuGroup> groups, List<Menu> menus ){
		Map<Long, MenuGroup> mapGroups = new HashMap<Long, MenuGroup>();
		for(MenuGroup group: groups){
			mapGroups.put(group.getId(), group);
		}
		
		//将菜单放置在对应的菜单组中。
		for(Menu menu: menus){
			MenuGroup group = mapGroups.get(menu.getGroupId());
			group.getMenus().add(menu);
		}
		
		
//		for (Iterator<MenuGroup> it = new ArrayList<MenuGroup>(groups).iterator(); it.hasNext();) {
//			MenuGroup mg = it.next();
//			if(mg.getMenus().isEmpty()){
//				groups.remove(mg);
//			}
//		}
		for (Iterator<MenuGroup> it = groups.iterator(); it.hasNext();) {
			MenuGroup mg = it.next();
			if(mg.getMenus().isEmpty()){
				it.remove();
			}
		}
	}
	
	
	public List<MenuGroup> findMenusByUserGroupId(long groupId){
		//使用这种方式查询只能查询出使用组权限配置的菜单。
		List<Menu> menus = menuDao.findMenusByUserGroupId(groupId);
		
		
		//查询菜单组。
		ResultFilter filter = new ResultFilter();
		filter.setOrder(Order.asc("displayOrder"));
		List<MenuGroup> groups = menuGroupDao.find(filter);
		
		addMenusToGroup(groups, menus);
		return groups;
	}
	
	
	/**
	 * 
	 * @see cn.redflagsoft.base.auth.MenuManager#findMenusByUsername(java.lang.String)
	 * @see cn.redflagsoft.base.security.dao.findMenusByUsername
	 */
	public List<MenuGroup> findMenusByUsername(String username) {
		
		
		/*暂时不用这中方式查询。
		 * 
		UserDetails user = userDetailsService.loadUserByUsername(username);
		GrantedAuthority[] authorities = user.getAuthorities();
		String[] auths = new String[authorities.length];
		for(int i = 0 ; i < authorities.length ; i++){
			auths[i] = authorities[i].getAuthority();
		}
		//查询当前用户的所有菜单项。
		List<Menu> menus = menuDao.findMenusByAuthorities(auths);
		
		*/
		
		//使用这种方式查询只能查询出使用组权限配置的菜单。
		List<Menu> menus = menuDao.findMenusByUsername(username);
		
		
		//查询菜单组。
		ResultFilter filter = new ResultFilter();
		filter.setOrder(Order.asc("displayOrder"));
		List<MenuGroup> groups = menuGroupDao.find(filter);
		
		addMenusToGroup(groups, menus);
		return groups;
		
//		Map<Long, MenuGroup> mapGroups = new HashMap<Long, MenuGroup>();
//		for(MenuGroup group: groups){
//			mapGroups.put(group.getId(), group);
//		}
//		
//		//将菜单放置在对应的菜单组中。
//		for(Menu menu: menus){
//			MenuGroup group = mapGroups.get(menu.getGroupId());
//			group.getMenus().add(menu);
//		}
//		
//		//处理没有菜单的组。
//		List<MenuGroup> result = new ArrayList<MenuGroup>();
//		for(MenuGroup group: groups){
//			if(group.getMenus().size() > 0){
//				result.add(group);
//			}
//		}
//		
//		return result;
	}

	/**
	 * @return the userDetailsService
	 */
	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	/**
	 * @param userDetailsService the userDetailsService to set
	 */
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	/**
	 * @return the menuDao
	 */
	public MenuDao getMenuDao() {
		return menuDao;
	}

	/**
	 * @param menuDao the menuDao to set
	 */
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	/**
	 * @return the menuGroupDao
	 */
	public MenuGroupDao getMenuGroupDao() {
		return menuGroupDao;
	}

	/**
	 * @param menuGroupDao the menuGroupDao to set
	 */
	public void setMenuGroupDao(MenuGroupDao menuGroupDao) {
		this.menuGroupDao = menuGroupDao;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.menuDao);
		Assert.notNull(this.menuGroupDao);
	}

	public MenuGroup saveMenuGroup(MenuGroup menuGroup) {
		if(menuGroup != null) {
			return menuGroupDao.save(menuGroup);
		}
		return menuGroup;
	}

	public int deleteMenuGroup(MenuGroup menuGroup) {
		if(menuGroup != null && menuGroup.getId() != null) {
			menuGroupDao.delete(menuGroup);
			return deleteMenuByGroupId(new Object[] { menuGroup.getId() });
		}
		return 0;
	}
	
	public int deleteMenuGroups(List<Long> ids) {
		if(ids != null && !ids.isEmpty()) {
			Long[] array = ids.toArray(new Long[0]);
			menuGroupDao.remove(array);
			deleteMenuByGroupId(array);
			return ids.size();
		}
		return 0;
	}
	
	public int deleteMenuByGroupId(Object[] groupIds) {
		if(groupIds != null && groupIds.length > 0) {
			return menuDao.remove(Restrictions.in("groupId", groupIds));
		}
		return 0;
	}

	public MenuGroup updateMenuGroup(MenuGroup menuGroup) {
		if(menuGroup != null) {
			return menuGroupDao.update(menuGroup);
		}
		return menuGroup;
	}

	public int deleteMenu(Menu menu) {
		if(menu != null) {
			return menuDao.delete(menu);
		}
		return 0;
	}

	public Menu saveMenu(Menu menu) {
		if(menu != null) {
			return menuDao.save(menu);
		}
		return menu;
	}

	public int deleteMenus(List<String> ids) {
		if(ids != null && !ids.isEmpty()) {
			return menuDao.remove(ids.toArray(new String[0]));
		}
		return 0;
	}

	public Menu updateMenu(Menu menu) {
		if(menu != null) {
			return menuDao.update(menu);
		}
		return menu;
	}

	public Menu getMenu(String id) {
		if(id != null && !id.trim().equals("")) {
			return menuDao.get(id);
		}
		return null;
	}

	public MenuGroup getMenuGroupByGroupName(String groupName) {
		return menuGroupDao.get(Restrictions.eq("groupName", groupName));
	}

	public MenuGroup getMenuGroup(Long id) {
		return menuGroupDao.get(id);
	}

	public List<Menu> findNotOwnAuthorityMenusByUsername(String username) {
		return menuDao.findNotOwnAuthorityMenusByUsername(username);
	}

	public List<Menu> findMenuManageInfo() {
		return menuDao.findMenuManageInfo();
	}

	public List<Menu> findOwnAuthorityMenusByUsername(String username) {	
		return menuDao.findMenusByUsername(username);
	}
	
}
