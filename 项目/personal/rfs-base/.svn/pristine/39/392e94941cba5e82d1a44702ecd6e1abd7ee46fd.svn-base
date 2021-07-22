package cn.redflagsoft.base.menu.impl;

import org.opoo.cache.CacheSizes;
import org.opoo.cache.Cacheable;

/**
 * ²Ëµ¥Ïî¡£
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class MenuEntry implements Cacheable, Comparable<MenuEntry>{
	
	private static final long serialVersionUID = 1404140520605340699L;
	private long menuId;
	private int order;
	
	public MenuEntry(long menuId, int order) {
		super();
		this.menuId = menuId;
		this.order = order;
	}
	
	public int getCachedSize() {
		return CacheSizes.sizeOfObject() 
			+ CacheSizes.sizeOfInt()
			+ CacheSizes.sizeOfLong();
	}
	public boolean equals(Object o){
		if(o == null){
			return false;
		}
		if(!(o instanceof MenuEntry)){
			return false;
		}
		MenuEntry me = (MenuEntry) o;
		return me.menuId == menuId && me.order == order;
	}
	/**
	 * @return the menuId
	 */
	public long getMenuId() {
		return menuId;
	}
	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	public int compareTo(MenuEntry o) {
		return order - o.order;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	} 
}
