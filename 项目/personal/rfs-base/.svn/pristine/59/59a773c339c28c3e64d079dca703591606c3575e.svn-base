package cn.redflagsoft.base.scheme.schemes.menu.v2;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.opoo.util.Assert;

import cn.redflagsoft.base.menu.Menu;
import cn.redflagsoft.base.menu.MenuManager;
import cn.redflagsoft.base.scheme.AbstractScheme;


/**
 * 角色菜单管理。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class RoleMenuScheme extends AbstractScheme {
	private MenuManager menuManager;
	private String file;
	private String menuData;
	private Long roleId;
	
	private Long menuId;
	private String remarkLabel;
	private String remarkName;
	private String remark1;
	private String remark2;
	
	private boolean remoteRemark = true;
	
//	private int added = 0;
//	private int deleted = 0;
//	private int updated = 0;
	
	/**
	 * 保存角色菜单。
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String doSave() throws Exception{
		Assert.notNull(roleId, "没有指定角色");
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
		
//		Map<String, MenuItem> menus = new HashMap<String, MenuItem>();
//		List<MenuItemRelation> relations = new ArrayList<MenuItemRelation>();
		
		Element rootElement = document.getRootElement();
		List<Element> list = rootElement.elements();
		List<Long> menuIds = new ArrayList<Long>();
		for(Element el: list){
			String id = el.attributeValue("id");
			//System.out.println(el.attributeValue("id"));
			menuIds.add(Long.parseLong(id));
		}
			
		saveRoleMenus(menuIds);
		
		
		//如果菜单的备注没有实时更新，则需要在这里整体保存。
		if(!remoteRemark){
			saveAllRoleMenuRemarks(rootElement);
		}
		
		return "当前角色的菜单保存成功。";
	}
	
	private void saveRoleMenus(List<Long> menuIds){
		//清理之前的
		menuManager.removeRoleMenus(roleId);
		for(int i = 0 ; i < menuIds.size() ; i++){
			Long menuId = menuIds.get(i);
			//主要为了确保Menu是存在的
			Menu menu = menuManager.getMenu(menuId);
			menuManager.addMenuToRole(roleId, menu, (i + 1) * 100);
		}
	}
	
	private void saveAllRoleMenuRemarks(Element rootElement){
		List<Long> done = new ArrayList<Long>();
		saveAllRoleMenuRemarksForElement(rootElement, done);
	}

	@SuppressWarnings("unchecked")
	private void saveAllRoleMenuRemarksForElement(Element element, List<Long> done){
		List<Element> list = element.elements();
		for(Element el: list){
			String id = el.attributeValue("id");
			long menuId = Long.parseLong(id);
			
			//如果之前没有处理
			if(!done.contains(menuId)){
				Menu menu = menuManager.getMenu(menuId);
				String remarkLabel = el.attributeValue("remarkLabel");
				if(StringUtils.isBlank(remarkLabel)){
					menuManager.removeMenuRemark(roleId, menu);
				}else{
					menuManager.addMenuRemark(roleId, menu, remarkName, remarkLabel, remark1, remark2);
				}
				done.add(menuId);
			}
			
			//处理下级
			saveAllRoleMenuRemarksForElement(el, done);
		}
	}
	
	
	
	
	
	
	
	/**
	 * 添加或者修改菜单备注。
	 * 
	 * @return
	 */
	public String doSaveRemark(){
		Assert.notNull(roleId, "必须指定角色");
		Assert.notNull(menuId, "必须指定菜单ID");
		Assert.notNull(remarkLabel, "必须指定备注的显示名称");
		Menu menu = menuManager.getMenu(menuId);
		Assert.notNull(menu, "必须指定正确的菜单ID。");
		menuManager.addMenuRemark(roleId, menu, remarkName, remarkLabel, remark1, remark2);
		
		return "保存成功。";
	}
	
	
	/**
	 * 删除菜单备注。
	 * 
	 * @return
	 */
	public String doDeleteRemark(){
		Assert.notNull(roleId, "必须指定角色");
		Assert.notNull(menuId, "必须指定菜单ID");
		Menu menu = menuManager.getMenu(menuId);
		Assert.notNull(menu, "必须指定正确的菜单ID。");
		menuManager.removeMenuRemark(roleId, menu);
		
		return "删除成功。";
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
	 * @return the menuData
	 */
	public String getMenuData() {
		return menuData;
	}


	/**
	 * @param menuData the menuData to set
	 */
	public void setMenuData(String menuData) {
		this.menuData = menuData;
	}


	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}


	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the menuId
	 */
	public Long getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the remarkLabel
	 */
	public String getRemarkLabel() {
		return remarkLabel;
	}

	/**
	 * @param remarkLabel the remarkLabel to set
	 */
	public void setRemarkLabel(String remarkLabel) {
		this.remarkLabel = remarkLabel;
	}

	/**
	 * @return the remarkName
	 */
	public String getRemarkName() {
		return remarkName;
	}

	/**
	 * @param remarkName the remarkName to set
	 */
	public void setRemarkName(String remarkName) {
		this.remarkName = remarkName;
	}

	/**
	 * @return the remark1
	 */
	public String getRemark1() {
		return remark1;
	}

	/**
	 * @param remark1 the remark1 to set
	 */
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	/**
	 * @return the remark2
	 */
	public String getRemark2() {
		return remark2;
	}

	/**
	 * @param remark2 the remark2 to set
	 */
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	/**
	 * @return the remoteRemark
	 */
	public boolean isRemoteRemark() {
		return remoteRemark;
	}

	/**
	 * @param remoteRemark the remoteRemark to set
	 */
	public void setRemoteRemark(boolean remoteRemark) {
		this.remoteRemark = remoteRemark;
	}
}
