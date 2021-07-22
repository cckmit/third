package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.apps.security.Group;
import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.OrgRole;

public interface OrgRoleDao extends Dao<OrgRole, Long> {
	
	/**
	 * 查询指定部门的所有角色。
	 * 
	 * @param orgId 
	 * @return
	 */
	List<Group> findRolesByOrgId(Long orgId);
	
	
	/**
	 * 查询指定角色被分配到的部门。
	 * 
	 * @param roleId
	 * @return
	 */
	List<Org> findOrgsByRoleId(Long roleId);
	
	/**
	 * 删除。
	 * 
	 * @param orgId
	 * @param roleId
	 */
	void removeByUnique(Long orgId, Long roleId);
	
}
