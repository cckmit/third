package cn.redflagsoft.base.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;

import cn.redflagsoft.base.bean.EntityGroup;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.OrgGroup;
import cn.redflagsoft.base.dao.EntityGroupDao;
import cn.redflagsoft.base.service.EntityGroupService;


@Deprecated
public class EntityGroupServiceImpl implements EntityGroupService {
	public static final Log log = LogFactory.getLog(EntityGroupServiceImpl.class);
	private EntityGroupDao entityGroupDao;

	public EntityGroupDao getEntityGroupDao() {
		return entityGroupDao;
	}

	public void setEntityGroupDao(EntityGroupDao entityGroupDao) {
		this.entityGroupDao = entityGroupDao;
	}

	public boolean saveEntityGroup(Long groupId, List<Long> entityIds) {
		if(groupId == null || entityIds == null || entityIds.size() < 1) {
			log.warn("saveEntityGroup 时参数输入不完整,操作忽略 ... ");
			return false;
		} else {
			deleteEntityGroupByGroupId(Arrays.asList(new Long[] { groupId }));
			EntityGroup entityGroup;
			for(int i = 0; i < entityIds.size(); i++) {
				entityGroup = new EntityGroup();
				entityGroup.setGroupID(groupId);
				entityGroup.setEntityID(entityIds.get(i));
				entityGroup.setCreationTime(new Date());
				entityGroup.setDisplayOrder((short)(i + 1));
				entityGroupDao.save(entityGroup);
			}
			return true;
		}
	}

	public int deleteEntityGroup(EntityGroup entityGroup) {
		if(entityGroup != null) {
			return entityGroupDao.delete(entityGroup);
		}
		return 0;
	}

	/**
	 * 删除单位时，应调用。
	 * @param entityIds
	 * @return
	 */
	public int deleteEntityGroupByEntityId(List<Long> entityIds) {
		if(entityIds != null && !entityIds.isEmpty()) {
			return entityGroupDao.remove(Restrictions.in("entityID", entityIds.toArray()));
		}
		return 0;
	}

	/**
	 * 删除单位分组时应调用。
	 * @param groupIds
	 * @return
	 */
	public int deleteEntityGroupByGroupId(List<Long> groupIds) {
		if(groupIds != null && !groupIds.isEmpty()) {
			return entityGroupDao.remove(Restrictions.in("groupID", groupIds.toArray()));
		}
		return 0;
	}

	public List<Org> findExistOrgByGroupId(Long groupId) {
//		if(groupId != null) {
			return entityGroupDao.findOrgsInGroup(groupId);
//		} else {
//			log.warn("findExistOrgByGroupId 时 groupId = null,返回 List<Org> = null ... ");
//			return null;
//		}
	}

	public List<Org> findNotExistOrgByGroupId(Long groupId) {
//		if(groupId != null) {
			return entityGroupDao.findOrgsNotInGroup(groupId);
//		} else {
//			log.warn("findNotExistOrgByGroupId 时 groupId = null,返回 List<Org> = null ... ");
//			return null;
//		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.EntityGroupService#addOrgToGroup(java.lang.Long, java.lang.Long)
	 */
	public void addOrgToGroup(Long orgId, Long groupId) {
		EntityGroup eg = new EntityGroup();
		eg.setEntityID(orgId);
		eg.setGroupID(groupId);
		eg.setInsertTime(new Date());
		entityGroupDao.save(eg);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.EntityGroupService#deleteOrgOfGroup(java.lang.Long, java.lang.Long)
	 */
	public void deleteOrgOfGroup(Long orgId, Long groupId) {
		Logic logic = Restrictions.logic(Restrictions.eq("groupID", groupId)).and(Restrictions.eq("entityID", orgId));
		entityGroupDao.remove(logic);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.EntityGroupService#findExistGroupByOrgId(java.lang.Long)
	 */
	public List<OrgGroup> findExistGroupByOrgId(Long orgId) {
		return entityGroupDao.findGroupsOfOrg(orgId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.EntityGroupService#fingNotExistGroupByOrgId(java.lang.Long)
	 */
	public List<OrgGroup> findNotExistGroupByOrgId(Long orgId) {
		return entityGroupDao.findGroupsNotOfOrg(orgId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.EntityGroupService#addOrgToGroups(java.lang.Long, java.util.List)
	 */
	public void addOrgToGroups(Long orgId, List<Long> groupIds) {
		for(Long groupId: groupIds){
			addOrgToGroup(orgId, groupId);
		}
	}
}
