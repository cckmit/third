package cn.redflagsoft.base.menu;

import java.util.List;

/**
 * 菜单服务。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface MenuService {

	/**
	 * 获取指定用户的顶级菜单集合。
	 * 
	 * 多个角色的菜单将会合并。
	 * @param username
	 * @return
	 */
	List<RoleMenu> findMenusByUsername(String username);
	
	/**
	 * 获取指定角色的顶级菜单集合。
	 * 
	 * @param roleId
	 * @return
	 */
	List<RoleMenu> findMenusByRoleId(Long roleId);
	
	/**
	 * 获取指定 id 的菜单。
	 * 
	 * @param id
	 * @return 找不到指定菜单时返回null
	 */
	Menu getMenu(Long id);
}
