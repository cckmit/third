package cn.redflagsoft.base.menu.impl;

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.redflagsoft.base.bean.RoleMenuRemark;
import cn.redflagsoft.base.menu.Menu;
import cn.redflagsoft.base.menu.RoleMenu;
import cn.redflagsoft.base.menu.Submenu;


/**
 * ½ÇÉ«µÄ²Ëµ¥¡£
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class RoleMenuWrapper extends MenuWrapper implements RoleMenu {
	private final Map<Long,RoleMenuRemark> remarks;
	private final RoleMenuRemark remark;
	public RoleMenuWrapper(Menu menu, Map<Long,RoleMenuRemark> remarks) {
		super(menu);
		this.remarks = remarks;
		this.remark = remarks.get(menu.getId());
	}
	
	public RoleMenuRemark getRoleMenuRemark(){
		return remark;
	}
	
	public String getRemarkName(){
		return remark == null ? null : remark.getName();
	}
	
	public String getRemarkLabel(){
		return remark == null ? null : remark.getLabel();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.menu.impl.MenuWrapper#getParent()
	 */
	@Override
	public Menu getParent() {
		Menu parent = super.getParent();
		if(parent == null){
			return null;
		}else{
			return new RoleMenuWrapper(parent, remarks);
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.menu.impl.MenuWrapper#getSubmenus()
	 */
	@Override
	public List<Submenu> getSubmenus() {
		final List<Submenu> list = super.getSubmenus();
		return Collections.unmodifiableList(new AbstractList<Submenu>(){
			@Override
			public Submenu get(int index) {
				Submenu submenu = list.get(index);
				return new RoleMenuWrapper(submenu, remarks);
			}

			@Override
			public int size() {
				return list.size();
			}});
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.menu.impl.MenuWrapper#getSuperior()
	 */
	@Override
	public Menu getSuperior() {
		Menu superior = super.getSuperior();
		if(superior == null){
			return null;
		}else{
			return new RoleMenuWrapper(superior, remarks);
		}
	}
	
//	public String getRemarkLogo(){
//		return remark == null ? null : remark.getRemark1();
//	}
	
}
