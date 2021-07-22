package cn.redflagsoft.base.menu.impl;

import cn.redflagsoft.base.menu.Menu;
import cn.redflagsoft.base.menu.Submenu;


/**
 * 子菜单实现类。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class SubmenuImpl extends MenuWrapper implements Submenu {
	private final Menu superior;
	
	public SubmenuImpl(Menu menu, Menu superior) {
		super(menu);
		this.superior = superior;
	}
	public Menu getSuperior() {
		return superior;
	}

	public boolean isInherited() {
		return false;
	}
}
