package cn.redflagsoft.base.menu.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.lifecycle.Application;
import org.opoo.cache.CacheSizes;
import org.opoo.cache.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.redflagsoft.base.bean.Action;
import cn.redflagsoft.base.bean.MenuItem;
import cn.redflagsoft.base.bean.MenuItemRelation;
import cn.redflagsoft.base.menu.Menu;
import cn.redflagsoft.base.menu.Submenu;

/**
 * 菜单实现类。 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class MenuImpl implements Menu, Cacheable{//, ExternalizableLite {

	private static final long serialVersionUID = -2055521425361866287L;
	private static final Log log = LogFactory.getLog(MenuImpl.class);
	
//	private transient MenuServiceImpl menuServiceImpl;
	private transient MenuManagerImpl menuManagerImpl;
	
	private Long actionId;
	private String name;
	private String label;
	private String logo;
	private String icon;
	private Long id;
	private int order;
	private Long parentId;
	private String remark;
	private Long creator;
	private Long modifier;
	private Date creationTime;
	private Date modificationTime;
	
	//可缓存
	private List<MenuEntry> submenuEntries;

	// cache objects
	private transient Menu parent;
	private transient Action action;
	private transient List<Submenu> submenus;
	

	public MenuImpl(MenuItem bean) {
		super();
		//this.menuServiceImpl = menuServiceImpl;
		this.actionId = bean.getActionId();
		this.icon = bean.getIcon();
		this.id = bean.getId();
		this.label = bean.getLabel();
		this.logo = bean.getLogo();
		this.name = bean.getName();
		this.order = 100;
		this.parentId = bean.getParentId();
		this.remark = bean.getRemark();
		this.creator = bean.getCreator();
		this.creationTime = bean.getCreationTime();
		this.modifier = bean.getModifier();
		this.modificationTime = bean.getModificationTime();
	}
	
	
//	private MenuServiceImpl getMenuServiceImpl(){
//		if(menuServiceImpl == null){
//			menuServiceImpl = Application.getContext().get("menuService", MenuServiceImpl.class);
//		}
//		return menuServiceImpl;
//	}
	
	private MenuManagerImpl getMenuManagerImpl(){
		if(menuManagerImpl == null){
			menuManagerImpl = Application.getContext().get("menuManagerV2", MenuManagerImpl.class);
		}
		return menuManagerImpl;
	}

	public Long getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public String getName() {
		return name;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return order;
	}

	public Menu getParent() {
		if (parentId != null && parent == null) {
			parent = getMenuManagerImpl().getMenuImpl(parentId);
		}
		return parent;
	}

	public List<MenuEntry> getSubmenuEntries(){
		if(submenuEntries == null){
			MenuManagerImpl impl = getMenuManagerImpl();
			submenuEntries = new ArrayList<MenuEntry>();
			List<MenuItemRelation> list = impl.getMenuItemRelationDao().findBySupId(id);
			for(MenuItemRelation mir: list){
				submenuEntries.add(new MenuEntry(mir.getSubId(), mir.getDisplayOrder()));
			}
			if(!submenuEntries.isEmpty()){
				Collections.sort(submenuEntries);
			}
			
			submenus = null;
			//重新缓存自己
			impl.getMenuCache().put(getId(), this);
		}
		return submenuEntries;
	}
	
	
	public List<Submenu> getSubmenus() {
		if(submenus != null){
			return submenus;
		}
		
		ArrayList<Submenu> list = new ArrayList<Submenu>();
		//处理自个儿的子菜单
		for(MenuEntry entry: getSubmenuEntries()){
			MenuImpl menu = getMenuManagerImpl().getMenuImpl(entry.getMenuId());
			menu.setOrder(entry.getOrder());
			Submenu sm = new SubmenuImpl(menu, this);
			list.add(sm);
		}
		//处理继承来的菜单
		Menu parent = getParent();
		if (parent != null) {
			List<Submenu> sms = parent.getSubmenus();
			if (!sms.isEmpty()) {
				for (Submenu sm : sms) {
					sm = new InheritedSubmenuImpl(sm, this);
					list.add(sm);
				}
			}
		}

		if (!list.isEmpty()) {
			Collections.sort(list, MenuOrderComparator.INSTANCE);
		}

		submenus = list;
		return submenus;
	}

	public Action getAction() {
		if (action == null && actionId != null) {
			action = getMenuManagerImpl().getActionManager().getAction(actionId);
		}
		return action;
	}

	public int getCachedSize() {
		int size = CacheSizes.sizeOfObject();
		size += CacheSizes.sizeOfString(name);
		size += CacheSizes.sizeOfString(label);
		size += CacheSizes.sizeOfInt();
		size += CacheSizes.sizeOfLong();
		if(submenuEntries != null){
			size += CacheSizes.sizeOfObject();
			size += submenuEntries.size() * (CacheSizes.sizeOfInt() 
					+ CacheSizes.sizeOfLong()
					+ CacheSizes.sizeOfObject());
		}
		size += CacheSizes.sizeOfString(icon);
		size += CacheSizes.sizeOfString(logo);
		size += CacheSizes.sizeOfLong();
		size += CacheSizes.sizeOfLong();
		size += CacheSizes.sizeOfString(remark);
		size += CacheSizes.sizeOfDate();
		size += CacheSizes.sizeOfDate();
		if (action != null) {
			size += action.getCachedSize();
		}
		return size;
	}

	public String getIcon() {
		if (icon == null && getAction() != null) {
			icon = getAction().getIcon();
		}
		return icon;
	}

	public String getLogo() {
		if (logo == null && getAction() != null) {
			logo = getAction().getLogo();
		}
		return logo;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @return the creator
	 */
	public Long getCreator() {
		return creator;
	}

	/**
	 * @return the modifier
	 */
	public Long getModifier() {
		return modifier;
	}

	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * @return the modificationTime
	 */
	public Date getModificationTime() {
		return modificationTime;
	}
	
	
	public String toString(){
		return new ToStringBuilder(this)
			.append("id", id)
			.append("name", name)
			.append("label", label)
			.toString();
	}

	/**
	 * 
	 */
	public void clear(){
		this.action = null;
		this.parent = null;
		this.submenuEntries = null;
	}


	@Transactional(propagation=Propagation.REQUIRED)
	public Submenu addSubmenu(Menu menu, int order) {
		
		MenuManagerImpl managerImpl = getMenuManagerImpl();
		
		MenuEntry entry = findSubmenuEntry(menu.getId());
		//增加到数据库
		managerImpl.addMenuToSuperior(this, menu, order);
		
		//重新设置缓存
		if(entry != null){
			entry.setOrder(order);
		}else{
			submenuEntries.add(new MenuEntry(menu.getId(), order));
		}
		Collections.sort(submenuEntries);
	
		//清空子菜单列表
		submenus = null;
		
		//刷新缓存
		managerImpl.getMenuCache().put(getId(), this);
		
		return new SubmenuImpl(menu, this);
	}
	
	private MenuEntry findSubmenuEntry(long menuId){
		for (MenuEntry entry : getSubmenuEntries()) {
			if(entry.getMenuId() == menuId){
				return entry;
			}
		}
		return null;
	}
	
	private int findSubmenuEntryIndex(long menuId){
		List<MenuEntry> list = getSubmenuEntries();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getMenuId() == menuId){
				return i;
			}
		}
		return -1;
	}


	public Submenu removeSubmenu(Menu menu) {
		MenuManagerImpl managerImpl = getMenuManagerImpl();
		
		int index = findSubmenuEntryIndex(menu.getId());
		if(index == -1){
			log.warn("但前菜单中不包含指定的子菜单，不能执行移除子菜单的操作。");
			return null;
		}	
		//从数据库中删除
		managerImpl.removeMenuFromSuperior(this, menu);
		
		
		//修改缓存
		submenuEntries.remove(index);
		if(!submenuEntries.isEmpty()){
			Collections.sort(submenuEntries);
		}
		submenus = null;
		
		managerImpl.getMenuCache().put(getId(), this);
		
		return new SubmenuImpl(menu, this);
	}

/*
	public void readExternal(DataInput input) throws IOException {
		this.actionId = input.readLong();
		this.name = input.readUTF();
		this.label = input.readUTF();
		logo = ExternalizableHelper.readSafeUTF(input);
		icon = ExternalizableHelper.readSafeUTF(input);
		this.id = input.readLong();
		this.order = input.readInt();
		this.parentId = input.readLong();
		this.remark = ExternalizableHelper.readSafeUTF(input);
		this.creator = input.readLong();
		this.modifier = input.readLong();
		
		if(actionId == -1){
			actionId = null;
		}
		if(parentId == -1){
			parentId = null;
		}
		if(creator == -1){
			creator = null;
		}
		if(modifier == -1){
			modifier = null;
		}
		
		long ctime = input.readLong();
		if(ctime != -1){
			this.creationTime = new Date(ctime);
		}
		long mtime = input.readLong();
		if(mtime != -1){
			this.modificationTime = new Date(mtime);
		}
		
		submenuEntries = new ArrayList<SubmenuEntry>();
		int size = input.readInt();
		if(size > 0){
			for(int i = 0 ; i < size ; i++){
				SubmenuEntry se = new SubmenuEntry(input.readLong(), input.readInt());
				submenuEntries.add(se);
			}
		}
	}


	public void writeExternal(DataOutput output) throws IOException {
		output.writeLong(actionId != null ? actionId.longValue() : -1L);
		output.writeUTF(name);
		output.writeUTF(label);
		ExternalizableHelper.writeSafeUTF(output, logo);
		ExternalizableHelper.writeSafeUTF(output, icon);
		output.writeLong(id);
		output.writeInt(order);
		output.writeLong(parentId != null ? parentId.longValue() : -1L);
		ExternalizableHelper.writeSafeUTF(output, remark);
		output.writeLong(creator != null ? creator.longValue() : -1L);
		output.writeLong(modifier != null ? modifier.longValue() : -1L);
		output.writeLong(creationTime != null ? creationTime.getTime() : -1L);
		output.writeLong(modificationTime != null ? modificationTime.getTime() : -1L);
		
		if(submenuEntries == null){
			output.writeInt(0);
		}else{
			output.writeInt(submenuEntries.size());
			for (SubmenuEntry se : submenuEntries) {
				output.writeLong(se.menuId);
				output.writeInt(se.order);
			}
		}
	}
	
	*/
}
