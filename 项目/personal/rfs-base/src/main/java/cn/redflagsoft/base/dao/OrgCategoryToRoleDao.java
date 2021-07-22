package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.apps.security.Group;
import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.OrgCategoryToRole;

public interface OrgCategoryToRoleDao extends Dao<OrgCategoryToRole,Long>{
	public List<Group> findRolesNotOfOrgCategory(Long orgCategoryId);
}
