package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.opoo.apps.security.Group;

import cn.redflagsoft.base.bean.OrgCategoryToRole;
import cn.redflagsoft.base.dao.OrgCategoryToRoleDao;

public class OrgCategoryToRoleHibernateDao extends AbstractBaseHibernateDao<OrgCategoryToRole, Long> implements OrgCategoryToRoleDao{

	@SuppressWarnings("unchecked")
	public List<Group> findRolesNotOfOrgCategory(Long orgCategoryId) {
		String qs = "from Group where id not in(select roleId from OrgCategoryToRole where orgCategoryId=?)";
		return getHibernateTemplate().find(qs, orgCategoryId);
	}

}
