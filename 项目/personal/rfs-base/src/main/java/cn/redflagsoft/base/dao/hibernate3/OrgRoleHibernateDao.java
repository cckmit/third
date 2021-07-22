package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.opoo.apps.security.Group;

import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.OrgRole;
import cn.redflagsoft.base.dao.OrgRoleDao;

public class OrgRoleHibernateDao extends AbstractBaseHibernateDao<OrgRole, Long> implements OrgRoleDao {

	@SuppressWarnings("unchecked")
	public List<Org> findOrgsByRoleId(Long roleId) {
		String qs = "select a from Org a, OrgRole b where a.id=b.orgId and b.roleId=?";
		return getHibernateTemplate().find(qs, roleId);
	}

	@SuppressWarnings("unchecked")
	public List<Group> findRolesByOrgId(Long orgId) {
		String qs = "select a from Group a, OrgRole b where a.id=b.roleId and b.orgId=?";
		return getHibernateTemplate().find(qs, orgId);
	}

	public void removeByUnique(Long orgId, Long roleId) {
		String qs = "delete from OrgRole where orgId=? and roleId=?";
		getQuerySupport().executeUpdate(qs, new Object[]{orgId, roleId});
	}
}
