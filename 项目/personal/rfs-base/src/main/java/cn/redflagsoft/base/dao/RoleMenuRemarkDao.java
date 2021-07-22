package cn.redflagsoft.base.dao;

import java.util.Map;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.RoleMenuRemark;

public interface RoleMenuRemarkDao extends Dao<RoleMenuRemark, Long> {
	
	/**
	 * 查询指定角色的所有菜单备注。
	 * 
	 * menuid, RoleMenuRemark
	 * @param roleId
	 * @return
	 */
	Map<Long,RoleMenuRemark> findByRoleId(long roleId);
	
	/**
	 * 
	 * @param roleId
	 * @param menuId
	 */
	void remove(Long roleId, Long menuId);
	
	/**
	 * 
	 * @param roleId
	 * @param menuId
	 * @return
	 */
	RoleMenuRemark get(Long roleId, Long menuId);
}
