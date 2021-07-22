package cn.redflagsoft.base.dao;

import java.util.Map;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.RoleMenuRemark;

public interface RoleMenuRemarkDao extends Dao<RoleMenuRemark, Long> {
	
	/**
	 * ��ѯָ����ɫ�����в˵���ע��
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
