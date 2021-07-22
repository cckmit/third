package cn.redflagsoft.base.security;

import java.util.List;

/**
 * 
 * @author Alex Lin
 * @deprecated
 */
public interface MenuManager {
	
	/**
	 * 获取指定用户的菜单。
	 * 
	 * @param username
	 * @return
	 */
	List<MenuGroup> findMenusByUsername(String username);
	
	/**
	 * 添加菜单组
	 * @param menuGroup
	 * @return MenuGroup
	 */
	MenuGroup saveMenuGroup(MenuGroup menuGroup);
	
	/**
	 * 删除菜单组
	 * @param menuGroup
	 * @return int
	 */
	int deleteMenuGroup(MenuGroup menuGroup);
	
	/**
	 * 查询菜单组
	 * @param groupName
	 * @return MenuGroup
	 */
	MenuGroup getMenuGroupByGroupName(String groupName);
	
	MenuGroup getMenuGroup(Long id);
	
	/**
	 * 删除菜单组集合
	 * @param ids
	 * @return int
	 */
	int deleteMenuGroups(List<Long> ids);
	
	/**
	 * 修改菜单组
	 * @param menuGroup
	 * @return MenuGroup
	 */
	MenuGroup updateMenuGroup(MenuGroup menuGroup);
	
	/**
	 * 删除菜单
	 * @param menu
	 * @return int
	 */
	int deleteMenu(Menu menu);
	
	/**
	 * 删除菜单集合
	 * @param ids
	 * @return int
	 */
	int deleteMenus(List<String> ids);
	
	/**
	 * 添加菜单
	 * @param menu
	 * @return Menu
	 */
	Menu saveMenu(Menu menu);
	
	/**
	 * 修改菜单
	 * @param menu
	 * @return Menu
	 */
	Menu updateMenu(Menu menu);
	
	/**
	 * 查询按扭 
	 * @param id
	 * @return Menu
	 */
	Menu getMenu(String id);
	
	/**
	 * 删除菜单组所有菜单
	 * @param groupId
	 * @return int
	 */
	int deleteMenuByGroupId(Object[] groupId);
	
	/**
	 * 用户没拥有的权限
	 * 
	 * @return List<Menu>
	 */
	List<Menu> findNotOwnAuthorityMenusByUsername(String username);
	
	/**
	 * 查询菜单管理信息
	 * 
	 * @return List<Menu>
	 */
	List<Menu> findMenuManageInfo();
	
	List<Menu> findOwnAuthorityMenusByUsername(String username);
	
	
	List<MenuGroup> findAllMenus();
}
