package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.RoleMenuItem;

public interface RoleMenuItemDao extends Dao<RoleMenuItem, Long> {

	List<Long> findMenuIdsByRoleIds(Long[] roleIds);
	
	List<Long> findMenuIdsByRoleId(Long roleId);
	
	List<RoleMenuItem> findByRoleId(Long roleId);
	
	void remove(Long roleId, Long menuId);
	
	RoleMenuItem get(Long roleId, Long menuId);
	
	int removeByRoleId(Long roleId);
}
