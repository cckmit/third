package cn.redflagsoft.base.menu;

import java.util.List;

import cn.redflagsoft.base.bean.Action;


/**
 * 菜单相关的管理类。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface MenuManager extends MenuService {
	/**
	 * 创建菜单。
	 * @param id
	 * @param name
	 * @param label
	 * @param icon
	 * @param logo
	 * @param parent
	 * @return
	 */
	Menu createMenu(Long id, String name, String label, String icon, String logo, Menu parent, Action action,
			int type, byte status, String remark);
	
	/**
	 * 删除Menu，MenuRelation，RoleMenuItem，RoleMenuRemark等。
	 * 级联删除，很重要。但不级联删除继承关系。
	 * 级联删除主要采取数据库自身的关联关系。
	 * @param id
	 */
	void removeMenu(Long id);
	
	
	/**
	 * 
	 * @return
	 */
	List<Menu> findMenus();
	
	/**
	 * 
	 * @return
	 */
	List<Menu> findSuperMenus();
	
//	/**
//	 * 将指定菜单添加到指定的上级菜单中。
//	 * @param superior 上级菜单
//	 * @param menu 指定菜单
//	 * @param displayOrder 顺序
//	 */
//	void addMenuToSuperior(Menu superior, Menu menu, int displayOrder);
	
	
//	/**
//	 * 从指定的上级菜单中删除指定的菜单。
//	 * 只删除了关联管理。
//	 * @param superior
//	 * @param menu
//	 */
//	void removeMenuFromSuperior(Menu superior, Menu menu);
	
	
	/**
	 * 向指定的角色增加顶级菜单。
	 * 
	 * @param roleId 角色id
	 * @param menu 菜单
	 * @param displayOrder 顺序
	 */
	void addMenuToRole(Long roleId, Menu menu, int displayOrder);
	
	
	/**
	 * 删除指定角色的指定菜单。
	 * 
	 * @param roleId
	 * @param menu
	 */
	void removeMenuFromRole(Long roleId, Menu menu);
	
	
//	/**
//	 * 给角色增加菜单的备注（如对菜单的label的备注）。
//	 * @param remark
//	 */
//	//void addMenuRemark(RoleMenuRemark remark);
	
	/**
	 * 给角色增加菜单的备注（如对菜单的label的备注）。
	 * 这个方法更符合封装性原则。
	 * @param roleId
	 * @param menu
	 * @param remarkName
	 * @param remarkLabel
	 * @param remark1
	 * @param remark2
	 */
	void addMenuRemark(Long roleId, Menu menu, String remarkName, String remarkLabel, String remark1, String remark2);
	
	
	//void removeMenuRemark(RoleMenuRemark remark);
	
	/**
	 * 删除指定角色指定菜单的备注。
	 */
	void removeMenuRemark(Long roleId, Menu menu);
	
	
	/**
	 * 清空Menu和Relation。
	 */
	void removeAllMenuRelations();
	
	
	
	/**
	 * 
	 * @param roleId
	 */
	void removeRoleMenus(Long roleId);
	
//	/**
//	 * 
//	 * @param supId
//	 * @param subId
//	 * @param displayOrder
//	 */
//	void createMenuRelation(long supId, long subId, int displayOrder);
	
	List<Long> findAllMenuIds();
}
