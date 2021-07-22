package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;

import com.google.common.collect.Lists;

import cn.redflagsoft.base.bean.OrgCategoryToOrgGroup;
import cn.redflagsoft.base.dao.OrgCategoryToOrgGroupDao;
import cn.redflagsoft.base.service.OrgCategoryToOrgGroupService;

public class OrgCategoryToOrgGroupServiceImpl implements
		OrgCategoryToOrgGroupService {

	private OrgCategoryToOrgGroupDao orgCategoryToOrgGroupDao;

	public OrgCategoryToOrgGroupDao getOrgCategoryToOrgGroupDao() {
		return orgCategoryToOrgGroupDao;
	}

	public void setOrgCategoryToOrgGroupDao(
			OrgCategoryToOrgGroupDao orgCategoryToOrgGroupDao) {
		this.orgCategoryToOrgGroupDao = orgCategoryToOrgGroupDao;
	}

	public void save(Long orgCategoryId, List<Long> groupIds) {
		if(groupIds != null && !groupIds.isEmpty()){
			for (Long groupId : groupIds) {
				boolean existOrgGroups = notExistOrgGroups(orgCategoryId,groupId);
				if(existOrgGroups){
					save(orgCategoryId,groupId);
				}
			}
		}
	}
	
	/*****
	 * 检查已选的分组在关联表中是否存在
	 * @param orgCategoryId
	 * @param groupId
	 * @return
	 */
	private boolean notExistOrgGroups(Long orgCategoryId,Long groupId){
			ResultFilter filter = ResultFilter.createEmptyResultFilter();
			SimpleExpression eq = Restrictions.eq("orgCategoryId", orgCategoryId);
			SimpleExpression eq2 = Restrictions.eq("orgGroupId", groupId);
			filter.setCriterion(Restrictions.logic(eq).and(eq2));
			List<OrgCategoryToOrgGroup> list = orgCategoryToOrgGroupDao.find(filter);
			if(list != null && !list.isEmpty()){
				return false;
			}
		return true;
	}

	public OrgCategoryToOrgGroup save(Long orgCategoryId, Long groupId) {
		OrgCategoryToOrgGroup group = new OrgCategoryToOrgGroup();
		group.setOrgCategoryId(orgCategoryId);
		group.setOrgGroupId(groupId);
		return orgCategoryToOrgGroupDao.save(group);
	}
	
	public List<OrgCategoryToOrgGroup> findOrgCategoryToOrgGroupByOrgCategoryId(Long orgCategoryId){
		//TODO 暂时没用到合适的字段来排序
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(Restrictions.eq("orgCategoryId", orgCategoryId));
		return orgCategoryToOrgGroupDao.find(filter);
	}

	public void removeOrgCategoryToOrgGroup(Long orgCategoryId, Long groupId) {
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		SimpleExpression eq = Restrictions.eq("orgCategoryId", orgCategoryId);
		SimpleExpression eq2 = Restrictions.eq("orgGroupId", groupId);
		filter.setCriterion(Restrictions.logic(eq).and(eq2));
		orgCategoryToOrgGroupDao.remove(filter.getCriterion());
	}

	public int delete(Long id) {
		return orgCategoryToOrgGroupDao.remove(id);
	}
}
