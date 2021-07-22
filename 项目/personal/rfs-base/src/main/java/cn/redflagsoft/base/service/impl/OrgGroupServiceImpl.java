/*
 * $Id: OrgGroupServiceImpl.java 6314 2013-11-06 09:15:38Z lf $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.EntityGroup;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.OrgGroup;
import cn.redflagsoft.base.dao.EntityGroupDao;
import cn.redflagsoft.base.dao.OrgGroupDao;
import cn.redflagsoft.base.service.OrgGroupService;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OrgGroupServiceImpl implements OrgGroupService {
	private static final Log log = LogFactory.getLog(OrgGroupServiceImpl.class);
	private EntityGroupDao entityGroupDao;
	private OrgGroupDao orgGroupDao;
	

	public EntityGroupDao getEntityGroupDao() {
		return entityGroupDao;
	}

	public void setEntityGroupDao(EntityGroupDao entityGroupDao) {
		this.entityGroupDao = entityGroupDao;
	}

	public OrgGroupDao getOrgGroupDao() {
		return orgGroupDao;
	}

	public void setOrgGroupDao(OrgGroupDao orgGroupDao) {
		this.orgGroupDao = orgGroupDao;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrgGroupService#createOrgGroup(cn.redflagsoft.base.bean.OrgGroup)
	 */
	public OrgGroup createGroup(OrgGroup grp) {
		grp.setUserManaged(true);
		grp.setStatus((byte) 1);
		grp.setType( 1);
		return orgGroupDao.save(grp);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrgGroupService#getOrgGroup(long)
	 */
	public OrgGroup getGroup(long id) {
		return orgGroupDao.get(id);
	}

	public int delete(OrgGroup grp){
		if(grp.isUserManaged()){
			entityGroupDao.remove(Restrictions.eq("groupID", grp.getId()));
			return orgGroupDao.remove(grp.getId());
		}else{
			log.warn(String.format("单位分组%s(id=%s)是内置的，不能被删除", 
					grp.getName(), grp.getId()));
			return 0;
		}
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrgGroupService#remove(java.lang.Long)
	 */
	public int removeGroup(Long id) {
		//entityGroupDao.remove(Restrictions.eq("groupID", id));
		//return orgGroupDao.remove(id);
		OrgGroup group = getGroup(id);
		return delete(group);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrgGroupService#remove(java.util.List)
	 */
	public int removeGroups(List<Long> groupIds) {
		int n = 0;
		for(Long id: groupIds){
			n += removeGroup(id);
		}
		return n;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrgGroupService#updateOrgGroup(cn.redflagsoft.base.bean.OrgGroup)
	 */
	public OrgGroup updateGroup(OrgGroup grp) {
		OrgGroup group = getGroup(grp.getId());
		if(group.isUserManaged()){
			group.setName(grp.getName());
			group.setUserManaged(true);
			group.setStatus((byte) 1);
			group.setType( 1);
			if(grp.getDisplayOrder() != null){
				group.setDisplayOrder(grp.getDisplayOrder());
			}
			return orgGroupDao.update(group);
		}else{
			throw new IllegalStateException(String.format("单位分组%s(id=%s)是内置的，不能被修改", 
					group.getName(), group.getId()));
		}
	}
	
	
	
	public void createEntityGroup(Long orgId, Long groupId) {
		EntityGroup eg = new EntityGroup();
		eg.setEntityID(orgId);
		eg.setGroupID(groupId);
		eg.setInsertTime(new Date());
		entityGroupDao.save(eg);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrgGroupService#addOrgToGroups(long, java.util.List)
	 */
	public void addOrgToGroups(long orgId, List<Long> groupIds) {
		for(Long groupId: groupIds){
			createEntityGroup(orgId, groupId);
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrgGroupService#addOrgsToGroup(java.util.List, long)
	 */
	public void addOrgsToGroup(List<Long> orgIds, long groupId) {
		for(Long orgId: orgIds){
			createEntityGroup(orgId, groupId);
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrgGroupService#findGroups(org.opoo.ndao.support.ResultFilter)
	 */
	public List<OrgGroup> findGroups(ResultFilter filter) {
		return orgGroupDao.find(filter);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrgGroupService#findGroupsOfOrg(long)
	 */
	public List<OrgGroup> findGroupsOfOrg(long orgId) {
		return entityGroupDao.findGroupsOfOrg(orgId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrgGroupService#findOrgsInGroup(long)
	 */
	public List<Org> findOrgsInGroup(long groupId) {
		return entityGroupDao.findOrgsInGroup(groupId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrgGroupService#removeOrgFromGroups(long, java.util.List)
	 */
	public int removeOrgFromGroups(long orgId, List<Long> groupIds) {
		Criterion c = Restrictions.logic(Restrictions.eq("entityID", orgId))
			.and(Restrictions.in("groupID", groupIds));
		return entityGroupDao.remove(c);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrgGroupService#removeOrgsFromGroup(java.util.List, long)
	 */
	public int removeOrgsFromGroup(List<Long> orgIds, long groupId) {
		Criterion c = Restrictions.logic(Restrictions.in("entityID", orgIds))
			.and(Restrictions.eq("groupID", groupId));
		return entityGroupDao.remove(c);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrgGroupService#addOrgToGroup(long, long)
	 */
	public void addOrgToGroup(long orgId, long groupId) {
		createEntityGroup(orgId, groupId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrgGroupService#removeOrgFromGroup(long, long)
	 */
	public int removeOrgFromGroup(long orgId, long groupId) {
		Criterion c = Restrictions.logic(Restrictions.eq("entityID", orgId))
		.and(Restrictions.eq("groupID", groupId));
		return entityGroupDao.remove(c);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrgGroupService#findGorupNotOfOrg(long)
	 */
	public List<OrgGroup> findGorupNotOfOrg(long orgId) {
		return entityGroupDao.findGroupsNotOfOrg(orgId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrgGroupService#findOrgsNotInGroup(long)
	 */
	public List<Org> findOrgsNotInGroup(long groupId) {
		List<Org> list = entityGroupDao.findOrgsNotInGroup(groupId);
		List<Org> result = new ArrayList<Org>();
		for(Org org: list){
			if(org.getId().longValue() > Org.MAX_SYS_ID){
				result.add(org);
			}else{
				//去掉系统管理单位
			}
		}
		return result;
	}

	
	
	/*
	 * (non-Javadoc)
	 * @see OrgGroupService#sortOrgsOfGroup(long, List)
	 */
	public int sortOrgsOfGroup(long groupId, List<Long> sortedOrgIds) {
		int cnt = 0;
		short order = 10;
		for (Long orgId : sortedOrgIds) {
			cnt += entityGroupDao.updateDisplayOrder(groupId, orgId, order);
			order += 10;
		}
		return cnt;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrgGroupService#findOrgIdsInGroup(long)
	 */
	public List<Long> findOrgIdsInGroup(long groupId) {
		return entityGroupDao.findOrgIdsInGroup(groupId);
	}

	public int removeOrgGroup(long orgId) { 
		return entityGroupDao.remove(Restrictions.eq("entityID", orgId));
	}
}
