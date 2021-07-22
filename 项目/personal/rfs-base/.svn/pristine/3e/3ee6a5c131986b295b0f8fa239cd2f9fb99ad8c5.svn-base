package cn.redflagsoft.base.scheme.schemes.menu;

import java.util.List;

import org.springframework.util.Assert;

import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.security.MenuGroup;
import cn.redflagsoft.base.security.MenuManager;

public class MenuGroupScheme extends AbstractScheme {
	private MenuManager menuManager;
	private List<Long> ids;
	private MenuGroup menuGroup;
	private String flag;

	public MenuManager getMenuManager() {
		return menuManager;
	}

	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}

	public MenuGroup getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(MenuGroup menuGroup) {
		this.menuGroup = menuGroup;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Object doSaveOrUpdateMenuGroup() throws SchemeException {
		Assert.notNull(menuGroup);
		String result = null;
		if("create".equals(flag)){
			if(menuGroup.getKey()!=null){
				MenuGroup tmp = menuManager.getMenuGroup(menuGroup.getKey());
				if(tmp!=null){
					return "'添加菜单组信息失败!(id)已经存在!";
				}
			}
			menuManager.saveMenuGroup(menuGroup);		
			result ="添加菜单组信息成功!";
		}else if ("update".equals(flag))
		{
			if(menuGroup.getKey()!=null){
				MenuGroup tmp = menuManager.getMenuGroup(menuGroup.getKey());
				if(tmp!=null){
					if(Integer.MAX_VALUE >= menuGroup.getDisplayOrder() && Integer.MIN_VALUE <= menuGroup.getDisplayOrder()) {
						tmp.setDisplayOrder(menuGroup.getDisplayOrder());
					}
					if(menuGroup.getGroupName() != null) {
						tmp.setGroupName(menuGroup.getGroupName());
					}
					menuManager.updateMenuGroup(tmp);
					result = "修改菜单组信息成功!";
				}else{
					result = "修改菜单组信息失败,菜单组名不存在!";
				}
				
			}
			
		}else{
			result = "处理菜单组信息失败!";
		}
		return result;
	}
	
	public Object doDelete() throws SchemeException {
		return menuManager.deleteMenuGroup(menuGroup);
	}
	
	public Object doDeleteList() throws SchemeException {
		return menuManager.deleteMenuGroups(ids);
	}

	public Object doScheme() throws SchemeException {
		return this.doSaveOrUpdateMenuGroup();
	}
}
