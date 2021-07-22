package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import cn.redflagsoft.base.bean.OrgCategoryToOrgGroup;
import cn.redflagsoft.base.bean.OrgGroup;
import cn.redflagsoft.base.dao.OrgCategoryToOrgGroupDao;

public class OrgCategoryToOrgGroupHibernateDao extends AbstractBaseHibernateDao<OrgCategoryToOrgGroup, Long> implements OrgCategoryToOrgGroupDao{
	
	@SuppressWarnings("unchecked")
	public List<OrgGroup> findGroupsNotOfOrgCategory(Long orgCategoryId) {
		String qs = "from OrgGroup where id not in(select orgGroupId from OrgCategoryToOrgGroup where orgCategoryId=?) order by displayOrder";
		return getHibernateTemplate().find(qs, orgCategoryId);
	}
}
