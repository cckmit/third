package cn.redflagsoft.base.menu.impl;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.opoo.ndao.criterion.Criterion;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.redflagsoft.base.bean.Action;
import cn.redflagsoft.base.bean.MenuItem;
import cn.redflagsoft.base.bean.MenuItemRelation;
import cn.redflagsoft.base.bean.RoleMenuItem;
import cn.redflagsoft.base.bean.RoleMenuRemark;
import cn.redflagsoft.base.menu.Menu;
import cn.redflagsoft.base.menu.MenuManager;
import cn.redflagsoft.base.menu.Submenu;


/**
 * 菜单管理实现类。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class MenuManagerImpl extends MenuServiceImpl implements MenuManager {
	@Transactional(propagation=Propagation.REQUIRED)
	public void addMenuRemark(RoleMenuRemark remark) {
		RoleMenuRemark old = getRoleMenuRemarkDao().get(remark.getRoleId(), remark.getMenuId());
		if(old != null){
			remark.setId(old.getId());
			getRoleMenuRemarkDao().update(remark);
		}else{
			getRoleMenuRemarkDao().save(remark);
		}
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public Menu createMenu(Long id, String name, String label, String icon, String logo, Menu parent, Action action,
			int type, byte status, String remark) {
		MenuItem mi = new MenuItem();
		mi.setActionId(action != null ? action.getId(): null);
		mi.setIcon(icon);
		mi.setId(id);
		mi.setLabel(label);
		mi.setLogo(logo);
		mi.setName(name);
		mi.setParentId(parent != null ? parent.getId() : null);
		mi.setRemark(remark);
		mi.setStatus(status);
		mi.setType(type);
		mi = getMenuItemDao().save(mi);
		missingMenu.clear();
		return getMenu(mi.getId());
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public MenuItem saveOrUpdateMenuItem(MenuItem item){
		Menu menu = getMenu(item.getId());
		if(menu != null){
			MenuItem old = buildMenuItem(menu);
			old.setActionId(item.getActionId());
			old.setIcon(item.getIcon());
			old.setLabel(item.getLabel());
			old.setLogo(item.getLogo());
			old.setName(item.getName());
			old.setParentId(item.getParentId());
			old.setRemark(item.getRemark());
			
			item = getMenuItemDao().update(old);
			getMenuCache().remove(item.getId());
		}else{ 
			item = getMenuItemDao().save(item);
			missingMenu.clear();
		}
		
		//MenuItem save = getMenuItemDao().save(item);
		return item;
	}
	
	public static MenuItem buildMenuItem(Menu menu){
		MenuItem item = new MenuItem();
		Action action = menu.getAction();
		Menu parent = menu.getParent();
		item.setActionId(action != null ? action.getId() : null);
		item.setCreationTime(menu.getCreationTime());
		item.setCreator(menu.getCreator());
		item.setIcon(menu.getIcon());
		item.setId(menu.getId());
		item.setLabel(menu.getLabel());
		item.setLogo(menu.getLogo());
		item.setModificationTime(menu.getModificationTime());
		item.setModifier(menu.getModifier());
		item.setName(menu.getName());
		item.setParentId(parent != null ? parent.getId() : null);
		item.setRemark(menu.getRemark());
		item.setStatus((byte) 1);
		item.setType(1);
		return item;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void addMenuToRole(Long roleId, Menu menu, int displayOrder) {
		
		RoleMenuItem item = getRoleMenuItemDao().get(roleId, menu.getId());
		if(item == null){
			item = new RoleMenuItem();
			item.setDisplayOrder(displayOrder);
			item.setMenuId(menu.getId());
			item.setRoleId(roleId);
			getRoleMenuItemDao().save(item);
		}else{
			log.debug("该菜单已经分配给指定角色,修改。");
			item.setDisplayOrder(displayOrder);
			getRoleMenuItemDao().update(item);
		}
	}

	/**
	 * 将指定菜单添加到指定的上级菜单中。
	 * @param superior 上级菜单
	 * @param menu 指定菜单
	 * @param displayOrder 顺序
	 */
	public void addMenuToSuperior(Menu superior, Menu menu, int displayOrder) {
		MenuItemRelation mir = getMenuItemRelationDao().get(superior.getId(), menu.getId());
		if(mir == null){
			mir = new MenuItemRelation();
			mir.setDisplayOrder(displayOrder);
			mir.setSupId(superior.getId());
			mir.setSubId(menu.getId());
			getMenuItemRelationDao().save(mir);
		}else{
			log.debug("菜单关联关系已经存在,修改。");
			mir.setDisplayOrder(displayOrder);
			getMenuItemRelationDao().update(mir);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void removeMenu(Long id) {
		getMenuItemDao().remove(id);
		getMenuCache().remove(id);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void removeMenuFromRole(Long roleId, Menu menu) {
		getRoleMenuItemDao().remove(roleId, menu.getId());
		
	}

	/**
	 * 从指定的上级菜单中删除指定的菜单。
	 * 只删除了关联管理。
	 * @param superior
	 * @param menu
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeMenuFromSuperior(Menu superior, Menu menu) {
		getMenuItemRelationDao().remove(superior.getId(), menu.getId());
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void removeMenuRemark(Long roleId, Menu menu) {
		getRoleMenuRemarkDao().remove(roleId, menu.getId());
	}

	public List<Menu> findMenus() {
		final List<Long> ids = getMenuItemDao().findIds();
		return new AbstractList<Menu>(){
			@Override
			public Menu get(int index) {
				return getMenuImpl(ids.get(index));
			}

			@Override
			public int size() {
				return ids.size();
			}
		};
	}
	
	public List<Menu> findSuperMenus(){
		final List<Long> ids = getMenuItemDao().findSuperIds();
		return new AbstractList<Menu>(){
			@Override
			public Menu get(int index) {
				return getMenuImpl(ids.get(index));
			}

			@Override
			public int size() {
				return ids.size();
			}
		};
	}
	
	public List<Menu> findSuperMenus2(){
		List<Menu> list = findMenus();
		final Map<Long, Menu> map = new HashMap<Long,Menu>();
		for(Menu menu: list){
			map.put(menu.getId(), menu);
		}
		
		Iterator<Menu> iterator = list.iterator();
		while(iterator.hasNext()){
			Menu menu = iterator.next();
			removeSubmenus(map, menu);
		}
		
		return new ArrayList<Menu>(map.values());
	}
	
	
	private void removeSubmenus(Map<Long, Menu> map, Menu menu){
		List<Submenu> submenus = menu.getSubmenus();
		if(!submenus.isEmpty()){
			for(Submenu sm: submenus){
				removeSubmenus(map, sm);
				map.remove(sm.getId());
			}
		}
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeAllMenuRelations() {
		//getMenuCache().clear();
		missingMenu.clear();
		
		getMenuItemRelationDao().remove((Criterion)null);
		//getMenuItemDao().removeAll();
	}
	
	public List<Long> findAllMenuIds(){
		return getMenuItemDao().findIds();
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeRoleMenus(Long roleId) {
		getRoleMenuItemDao().removeByRoleId(roleId);
	}

	/**
	 * 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void addMenuRemark(Long roleId, Menu menu, String remarkName, String remarkLabel, String remark1,
			String remark2) {
		RoleMenuRemark remark = getRoleMenuRemarkDao().get(roleId, menu.getId());
		boolean isNew = remark == null;
		if(isNew){
			remark = new RoleMenuRemark();
			remark.setMenuId(menu.getId());
			remark.setRoleId(roleId);
		}
		remark.setLabel(remarkLabel);
		remark.setName(remarkName);
		remark.setRemark1(remark1);
		remark.setRemark2(remark2);
		
		if(isNew){
			getRoleMenuRemarkDao().save(remark);
		}else{
			getRoleMenuRemarkDao().update(remark);
		}
	}
}
