package cn.redflagsoft.base.menu.impl;

import java.util.ArrayList;
import java.util.List;

import cn.redflagsoft.base.menu.Menu;
import cn.redflagsoft.base.menu.Submenu;


/**
 * 继承的子菜单。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class InheritedSubmenuImpl extends SubmenuImpl implements Submenu {

	public InheritedSubmenuImpl(Menu menu, Menu superior) {
		super(menu, superior);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.menu.impl.SubmenuImpl#isInherited()
	 */
	@Override
	public boolean isInherited() {
		return true;
	}

	/* 
	 * 继承的子菜单的所有下级菜单都是继承性质的。
	 * (non-Javadoc)
	 * @see cn.redflagsoft.base.menu.impl.MenuWrapper#getSubmenus()
	 */
	@Override
	public List<Submenu> getSubmenus() {
		List<Submenu> list = super.getSubmenus();
		List<Submenu> submenus = new ArrayList<Submenu>();
		for(Submenu sm : list){
			if(!(sm instanceof InheritedSubmenuImpl)){
				sm = new InheritedSubmenuImpl(sm, this);
			}
			submenus.add(sm);
		}
		return submenus;
	}
}
