package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.OrgGroup;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated
 */
public interface EntityGroupService {
	/**
	 * 
	 * @param entityGroup
	 * @return EntityGroup
	 */
	boolean saveEntityGroup(Long groupId, List<Long> entityIds);
	
	/**
	 * 
	 * @param entityGroup
	 * @return int
	 */
	//int deleteEntityGroup(EntityGroup entityGroup);
	
	/**
	 * 
	 * @param entityId
	 * @return int
	 */
	//int deleteEntityGroupByEntityId(List<Long> entityIds);
	
	/**
	 * 
	 * @param groupIds
	 * @return int
	 */
	//int deleteEntityGroupByGroupId(List<Long> groupIds);
	
	/**
	 * 查询存在的 org
	 * @param groupId
	 * @return List<Org>
	 */
	List<Org> findExistOrgByGroupId(Long groupId);
	
	/**
	 * 查询不存在的 org
	 * @param groupId
	 * @return List<Org>
	 */
	List<Org> findNotExistOrgByGroupId(Long groupId);
	
	
	
	List<OrgGroup> findExistGroupByOrgId(Long orgId);
	
	
	List<OrgGroup> findNotExistGroupByOrgId(Long orgId);
	
	
	/**
	 * 
	 * @param groupId
	 * @param orgId
	 */
	void addOrgToGroup(Long orgId, Long groupId);
	
	
	void addOrgToGroups(Long orgId, List<Long> groupIds);
	
	/**
	 * 
	 * @param groupId
	 * @param orgId
	 */
	void deleteOrgOfGroup(Long orgId, Long groupId);
	
	
}
