package cn.redflagsoft.base.scheme.schemes.menu;

import java.util.List;

import org.springframework.util.Assert;

import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.security.Menu;
import cn.redflagsoft.base.security.MenuManager;

public class MenuScheme extends AbstractScheme {
	private MenuManager menuManager;
	private List<String> ids;
	private Long id;
	private Menu menu;
	private String username;
	private String flag;
	
	public MenuManager getMenuManager() {
		return menuManager;
	}

	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Object doSaveOrUpdateMenu() throws SchemeException {
		Assert.notNull(menu);
		String result = null;

			if ("create".equals(flag)) {
				if(menu.getKey() != null){	
					Menu tmp = menuManager.getMenu(menu.getKey());
					if (tmp != null) {
						result = "添加菜单信息失败,编号重复!";
					}
				}
					menu.setAuthority(menu.getKey());
					menuManager.saveMenu(menu);
					result = "添加菜单信息成功!";
				} else if ("update".equals(flag))
				{
						Menu tmp = menuManager.getMenu(menu.getKey());
						if (tmp != null) {
						if(menu.getApplication() != null) {
							tmp.setApplication(menu.getApplication());
						}
						if(Integer.MAX_VALUE >= menu.getDisplayOrder() && Integer.MIN_VALUE <= menu.getDisplayOrder()) {
							tmp.setDisplayOrder(menu.getDisplayOrder());
						}
						if(menu.getGroupId() != null) {
							tmp.setGroupId(menu.getGroupId());
						}
						if(menu.getName() != null) {
							tmp.setName(menu.getName());
						}
						if(menu.getLabel2()!=null){
							tmp.setLabel2(menu.getLabel2());
						}
						menu.setAuthority(menu.getKey());
						menuManager.updateMenu(tmp);
						result = "修改菜单信息成功!";
						} else {
							result = "修改菜单信息失败,菜单编号不存在!";
						}
			}else {
			result = "处理菜单信息失败!";
		}
		return result;
	}
	
	/**
	 * 删除菜单
	 * 
	 * @return String
	 * @throws SchemeException
	 */
	public String doDelete() throws SchemeException {
		int line = menuManager.deleteMenu(menu);
		return line == 0 ? "删除菜单信息失败!" : "删除菜单信息成功!";
	}
	
	/**
	 * 删除指定组所有菜单
	 * @return String
	 * @throws SchemeException
	 */
	public String doDeleteByGroupId() throws SchemeException {
		int line = menuManager.deleteMenuByGroupId(ids.toArray(new Long[0]));
		return line == 0 ? "删除菜单信息失败!" : "删除菜单信息成功!";
	}
	
	/**
	 * 删除指定菜单列表
	 * @return String
	 * @throws SchemeException
	 */
	public String doDeleteList() throws SchemeException {
		int line = menuManager.deleteMenus(ids);
		return line == 0 ? "删除菜单信息失败!" : "删除菜单信息成功!";		
	}
	
	/**
	 * 用户没有拥有权限
	 * 
	 * @return List<Menu>
	 * @throws SchemeException
	 */
	public List<Menu> doFindNotOwnAuthorityMenusByUsername() throws SchemeException {
		if(username != null && !username.trim().equals("")) {
			return menuManager.findNotOwnAuthorityMenusByUsername(username);
		}
		return null;
	}
	
	/**
	 * 查询菜单管理信息
	 * 
	 * @return List<Menu>
	 * @throws SchemeException
	 */
	public List<Menu> doFindMenuManageInfo() throws SchemeException {
		return menuManager.findMenuManageInfo();
	}

	public Object doScheme() throws SchemeException {
		return this.doSaveOrUpdateMenu();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * 用户拥有权限
	 * 
	 * @return List<Menu>
	 * @throws SchemeException
	 */
	public List<Menu> doFindMenusByUsername() throws SchemeException {
		if(username != null && !username.trim().equals("")) {
			return menuManager.findOwnAuthorityMenusByUsername(username);
		}
		return null;
	}
}
