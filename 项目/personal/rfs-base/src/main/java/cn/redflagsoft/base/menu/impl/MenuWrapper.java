package cn.redflagsoft.base.menu.impl;

import java.util.Date;
import java.util.List;

import cn.redflagsoft.base.bean.Action;
import cn.redflagsoft.base.menu.Menu;
import cn.redflagsoft.base.menu.Submenu;


/**
 * 菜单包装类。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class MenuWrapper implements Menu, Submenu {
	private final Menu menu;
	public MenuWrapper(Menu menu) {
		super();
		this.menu = menu;
	}
	
	public Action getAction() {
		return menu.getAction();
	}

	public Date getCreationTime() {
		return menu.getCreationTime();
	}

	public Long getCreator() {
		return menu.getCreator();
	}

	public String getIcon() {
		return menu.getIcon();
	}

	public Long getId() {
		return menu.getId();
	}

	public String getLabel() {
		return menu.getLabel();
	}

	public String getLogo() {
		return menu.getLogo();
	}

	public Date getModificationTime() {
		return menu.getModificationTime();
	}

	public Long getModifier() {
		return menu.getModifier();
	}

	public String getName() {
		return menu.getName();
	}

	public int getOrder() {
		return menu.getOrder();
	}

	public Menu getParent() {
		return menu.getParent();
	}

	public String getRemark() {
		return menu.getRemark();
	}

	public List<Submenu> getSubmenus() {
		return menu.getSubmenus();
	}

	public Menu getSuperior() {
		if(menu instanceof Submenu){
			return ((Submenu)menu).getSuperior();
		}else{
			return null;
		}
	}

	public boolean isInherited() {
		if(menu instanceof Submenu){
			return ((Submenu)menu).isInherited();
		}else{
			return false;
		}
	}

	public Submenu addSubmenu(Menu menu, int order) {
		return this.menu.addSubmenu(menu, order);
	}

	public Submenu removeSubmenu(Menu menu) {
		return this.menu.removeSubmenu(menu);
	}
}
