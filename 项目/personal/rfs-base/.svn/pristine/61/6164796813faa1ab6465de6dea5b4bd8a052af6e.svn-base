package cn.redflagsoft.base.dao.hibernate3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.redflagsoft.base.bean.RoleMenuRemark;
import cn.redflagsoft.base.dao.RoleMenuRemarkDao;

public class RoleMenuRemarkHibernateDao extends AbstractBaseHibernateDao<RoleMenuRemark, Long> implements
		RoleMenuRemarkDao {

	@SuppressWarnings("unchecked")
	public Map<Long, RoleMenuRemark> findByRoleId(long roleId) {
		String qs = "from RoleMenuRemark where roleId=?";
		List<RoleMenuRemark> list = getHibernateTemplate().find(qs, roleId);
		Map<Long,RoleMenuRemark> map = new HashMap<Long,RoleMenuRemark>();
		for(RoleMenuRemark rmr: list){
			map.put(rmr.getMenuId(), rmr);
		}
		return map;
	}

	public void remove(Long roleId, Long menuId) {
		String qs = "delete from RoleMenuRemark where roleId=? and menuId=?";
		getQuerySupport().executeUpdate(qs, new Object[]{roleId, menuId});
	}

	@SuppressWarnings("unchecked")
	public RoleMenuRemark get(Long roleId, Long menuId) {
		String qs = "from RoleMenuRemark where roleId=? and menuId=?";
		List<RoleMenuRemark> list = getHibernateTemplate().find(qs, new Object[]{roleId, menuId});
		if(list.isEmpty()){
			return null;
		}else if(list.size() == 1){
			return list.get(0);
		}else{
			throw new IllegalStateException("返回结果不唯一: " + list.size());
		}
	}
	
}
