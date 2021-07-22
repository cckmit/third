package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.OrgCategoryToOrgGroup;

public interface OrgCategoryToOrgGroupService {
	void save(Long orgCategoryId,List<Long> groupIds);
	
	OrgCategoryToOrgGroup save(Long orgCategoryId,Long groupId);
	
	List<OrgCategoryToOrgGroup> findOrgCategoryToOrgGroupByOrgCategoryId(Long orgCategoryId);
	
	void removeOrgCategoryToOrgGroup(Long orgCategoryId,Long groupId);
	
	int delete(Long id);
}
