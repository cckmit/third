package cn.redflagsoft.base.scheme.schemes.menu.v2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.redflagsoft.base.bean.MenuItem;
import cn.redflagsoft.base.bean.MenuItemRelation;
import cn.redflagsoft.base.menu.MenuManager;
import cn.redflagsoft.base.menu.impl.MenuManagerImpl;
import cn.redflagsoft.base.scheme.AbstractScheme;

public class MenuScheme extends AbstractScheme{
	private final static Log log = LogFactory.getLog(MenuScheme.class);
	
	private MenuManager menuManager;
	private String file;
	private String menuData;
	
	private int added = 0;
	private int deleted = 0;
	private int updated = 0;
	

	
	
	/**
	 * 
	 * @return
	 * @throws DocumentException
	 * @throws FileNotFoundException
	 */
	public String doSave() throws DocumentException, FileNotFoundException{
		if(menuData == null && file == null){
			throw new IllegalArgumentException("XML 串或文件地址，必须指定其一。");
		}
		SAXReader saxReader = new SAXReader();
		saxReader.setEncoding("GBK");
		Document document = null;
		if(file != null){
			File f = new File(file);
			document = saxReader.read(f);
		}else if(menuData != null){
			StringReader reader = new StringReader(menuData);
			document = saxReader.read(reader);
		}
		
		Map<String, MenuItem> menus = new HashMap<String, MenuItem>();
		List<MenuItemRelation> relations = new ArrayList<MenuItemRelation>();
		
		Element rootElement = document.getRootElement();
		//List<Element> list = rootElement.elements();
		//解析XML文件
		populate(null, rootElement, 0, menus, relations);
		
		
		/////////////////////////////////////////////////////////////
		if(log.isDebugEnabled()){
			for(MenuItem item: menus.values()){
				log.debug(item.getId() + " : " + item.getLabel());
			}
			log.debug("---------------------------------------------------------------");
			for(MenuItemRelation r: relations){
				log.debug(r.getSupId() + " : " + r.getSubId() + " --> " + r.getDisplayOrder());
			}
		}
		///////////////////////////////////////////////////////////////
		
		
		
		//menuManager.removeAllMenuRelations();
		saveMenus(menus);
		saveRelations(relations);
		
		return String.format("菜单保存成功！共新增了 %s 项、更新了 %s 项、删除了 %s 项。", added, updated, deleted);
	}
	
	@SuppressWarnings("unchecked")
	private void populate(MenuItem superior, Element node, int index, Map<String, MenuItem> menus, List<MenuItemRelation> relations){
		MenuItem item = null;
		if(!node.isRootElement()){
			String id = node.attributeValue("id");
			String name = node.attributeValue("name");
			String label = node.attributeValue("label");
			String logo = node.attributeValue("logo");
			String icon = node.attributeValue("icon");
			String parentId = node.attributeValue("parentId");
			String actionId = node.attributeValue("actionId");
			String remark = node.attributeValue("remark");
			String isInherited = node.attributeValue("isInherited");
			//System.out.println(node.isRootElement());
			
			//继承节点，不予处理。
			if("true".equalsIgnoreCase(isInherited)){
				return;
			}
			
			if(superior != null){
				MenuItemRelation mir = new MenuItemRelation();
				mir.setDisplayOrder((index + 1) * 100);
				mir.setSupId(superior.getId());
				mir.setSubId(Long.parseLong(id));
				relations.add(mir);
			}
			
			MenuItem tmp = menus.get(id);
			if(tmp != null){
				//只处理关系
				return;
			}
		
			//创建自己	
			//MenuItem 
			item = new MenuItem();
			item.setId(Long.parseLong(id));
			item.setActionId(StringUtils.isNotBlank(actionId) ? Long.parseLong(actionId) : null);
			item.setIcon(icon);
			item.setLabel(label);
			item.setLogo(logo);
			item.setName(name);
			item.setParentId(StringUtils.isNotBlank(parentId) ? Long.parseLong(parentId) : null);
			item.setRemark(remark);
			item.setType(1);
			item.setStatus((byte) 1);
			menus.put(id, item);
		}
		
		//MenuManager mm;
		//mm.createMenu(id, name, label, icon, logo, parent, action, type, status, remark)
		//下级
		List<Element> list = node.elements();
		int i = 0;
		for(Element e: list){
			populate(item, e, i++, menus, relations);
		}
	}
	
	
	/**
	 * 
	 * @param menus
	 */
	protected void saveMenus(Map<String, MenuItem> menus){
		
		//已经处理过id的集合
		List<String> saved = new ArrayList<String>();
		
//		ArrayList<String> list = new ArrayList<String>(menus.keySet());
//		Collections.reverse(list);
//		for(String id: list){
//			saveMenu(id, menus, saved);
//		}
		
		//
		List<Long> oldMenuIds = menuManager.findAllMenuIds();


		//保存新的，可能是更新或者新增
		Iterator<String> it = menus.keySet().iterator();
		while(it.hasNext()){
			saveMenu(it.next(), menus, saved);
		}
		
		//删除原来有现在没有的
		for(Long id: oldMenuIds){
			if(saved.contains(id.toString())){
				continue;
			}else{
				menuManager.removeMenu(id);
				deleted++;
			}
		}
		for(String id: saved){
			if(oldMenuIds.contains(Long.parseLong(id))){
				updated++;
			}else{
				added++;
			}
		}
	}
	
	/**
	 * 
	 * @param id 当前操作要保存的对象的id
	 * @param menus
	 * @param saved
	 */
	protected void saveMenu(String id, Map<String, MenuItem> menus, List<String> saved){
		if(!saved.contains(id)){
			MenuItem item = menus.get(id);
			Long parentId = item.getParentId();

			
			if(parentId != null){
				String parentStringId = parentId.toString();
				//依赖的节点没有保存，必须先保存
				if(!saved.contains(parentStringId)){
					System.out.println(id + " 依赖于：" + parentStringId);
					saveMenu(parentStringId, menus, saved);
				}
			}
			
			//保存
			log.debug("正在保存：" + item.getId() + " : " + item.getLabel());
			((MenuManagerImpl)menuManager).saveOrUpdateMenuItem(item);
			saved.add(id);
		}else{
			//已有不处理
			log.debug("已经保存过了： " + id);
		}
	}
	
	protected void saveRelations(List<MenuItemRelation> relations){
		menuManager.removeAllMenuRelations();
		for(MenuItemRelation m: relations){
			((MenuManagerImpl)menuManager).getMenuItemRelationDao().save(m);
		}
	}
	
	

	/**
	 * @return the menuData
	 */
	public String getMenuData() {
		return menuData;
	}

	/**
	 * @param menuData the menuData to set
	 */
	public void setMenuData(String menuData) {
		//System.out.println("设置-----------------" + menuData);
		this.menuData = menuData;
	}

	/**
	 * @return the file
	 */
	public String getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(String file) {
		this.file = file;
	}
	
	/**
	 * @return the menuManager
	 */
	public MenuManager getMenuManager() {
		return menuManager;
	}

	/**
	 * @param menuManager the menuManager to set
	 */
	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}
}
