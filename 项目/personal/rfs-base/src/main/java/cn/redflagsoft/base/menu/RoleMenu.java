package cn.redflagsoft.base.menu;


/**
 * 角色的菜单。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface RoleMenu extends Menu {
	
	/**
	 * 备注的名称。
	 * @return
	 */
	String getRemarkName();
	
	/**
	 * 备注的显示名称。
	 * @return
	 */
	String getRemarkLabel();

	
	//Long getRoleId();
	//Group getRole();
}
