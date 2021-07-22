package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import cn.redflagsoft.base.bean.RoleMenuItem;
import cn.redflagsoft.base.dao.RoleMenuItemDao;

@SuppressWarnings("unchecked")
public class RoleMenuItemHibernateDao extends AbstractBaseHibernateDao<RoleMenuItem, Long> implements
		RoleMenuItemDao {

	public List<RoleMenuItem> findByRoleId(Long roleId) {
		String qs = "from RoleMenuItem where roleId=? order by displayOrder";
		return getHibernateTemplate().find(qs, roleId);
		
	}

	public void remove(Long roleId, Long menuId) {
		String qs = "delete from RoleMenuItem where roleId=? and menuId=?";
		getQuerySupport().executeUpdate(qs, new Object[]{roleId, menuId});		
	}

	public RoleMenuItem get(Long roleId, Long menuId) {
		String qs = "from RoleMenuItem where roleId=? and menuId=?";
		List<RoleMenuItem> list = getHibernateTemplate().find(qs, new Object[]{roleId, menuId});
		if(list.isEmpty()){
			return null;
		}else if(list.size() == 1){
			return list.get(0);
		}else{
			throw new IllegalStateException("返回结果不唯一: " + list.size());
		}
	}

	
	public List<Long> findMenuIdsByRoleId(Long roleId) {
		String qs = "select menuId from RoleMenuItem where roleId=? order by displayOrder";
		return getHibernateTemplate().find(qs, roleId);
	}

	/**
	 * 
	 */
	public int removeByRoleId(Long roleId) {
		String qs = "delete from RoleMenuItem where roleId=?";
		return getQuerySupport().executeUpdate(qs, roleId);
	}

	public List<Long> findMenuIdsByRoleIds(Long[] roleIds) {
		String qs = "select menuId from RoleMenuItem where roleId in (:roleIds) order by displayOrder";
		return getQuerySupport().find(qs, "roleIds", roleIds);
	}
}
