package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.OrgCategoryToOrgGroup;
import cn.redflagsoft.base.bean.OrgCategoryToRole;
import cn.redflagsoft.base.dao.OrgCategoryToRoleDao;
import cn.redflagsoft.base.service.OrgCategoryToRoleService;

public class OrgCategoryToRoleServiceImpl implements OrgCategoryToRoleService {
	private OrgCategoryToRoleDao orgCategoryToRoleDao;

	public OrgCategoryToRoleDao getOrgCategoryToRoleDao() {
		return orgCategoryToRoleDao;
	}

	public void setOrgCategoryToRoleDao(OrgCategoryToRoleDao orgCategoryToRoleDao) {
		this.orgCategoryToRoleDao = orgCategoryToRoleDao;
	}

	public void save(Long orgCategoryId, List<Long> roleIds) {
		if(roleIds != null && !roleIds.isEmpty()){
			for (Long roleId : roleIds) {
				if(notExistRoleId(orgCategoryId,roleId)){
					save(orgCategoryId,roleId);
				}
			}
		}
	}

	public void save(Long orgCategoryId, Long roleId) {
		OrgCategoryToRole role = new OrgCategoryToRole();
		role.setOrgCategoryId(orgCategoryId);
		role.setRoleId(roleId);
		orgCategoryToRoleDao.save(role);
	}
	
	/*****
	 * 检查已选的角色在关联表中是否存在
	 * @param orgCategoryId
	 * @param groupId
	 * @return
	 */
	private boolean notExistRoleId(Long orgCategoryId,Long roleId){
			ResultFilter filter = ResultFilter.createEmptyResultFilter();
			SimpleExpression eq = Restrictions.eq("orgCategoryId", orgCategoryId);
			SimpleExpression eq2 = Restrictions.eq("roleId", roleId);
			filter.setCriterion(Restrictions.logic(eq).and(eq2));
			List<OrgCategoryToRole> list = orgCategoryToRoleDao.find(filter);
			if(list != null && !list.isEmpty()){
				return false;
			}
		return true;
	}

	public List<OrgCategoryToRole> findOrgCategoryToRoleByOrgCategoryId(
			Long orgCategoryId) {
		//TODO 暂时没用到合适的字段来排序
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(Restrictions.eq("orgCategoryId", orgCategoryId));
		return orgCategoryToRoleDao.find(filter);
	}

	public void removeOrgCategoryToRole(Long orgCategoryId, Long roleId) {
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		SimpleExpression eq = Restrictions.eq("orgCategoryId", orgCategoryId);
		SimpleExpression eq2 = Restrictions.eq("roleId", roleId);
		filter.setCriterion(Restrictions.logic(eq).and(eq2));
		orgCategoryToRoleDao.remove(filter.getCriterion());
	}

	public int delete(Long id) {
		int remove = orgCategoryToRoleDao.remove(id);
		return remove;
	}
	
}
