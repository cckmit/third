/*
 * $Id: ClerkGroupServiceImpl.java 5065 2011-11-15 06:06:10Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.annotation.Queryable;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.ClerkGroup;
import cn.redflagsoft.base.bean.RFSGroup;
import cn.redflagsoft.base.dao.ClerkGroupDao;
import cn.redflagsoft.base.dao.GroupDao;
import cn.redflagsoft.base.service.ClerkGroupService;
import cn.redflagsoft.base.service.ClerkService;

/**
 * 人员分组以及人员分组关联关系的管理。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ClerkGroupServiceImpl implements ClerkGroupService {
	private static final Log log = LogFactory.getLog(ClerkGroupServiceImpl.class);
	
	
	private GroupDao groupDao;
	private ClerkGroupDao clerkGroupDao;
	private ClerkService clerkService;
	

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public ClerkGroupDao getClerkGroupDao() {
		return clerkGroupDao;
	}

	public void setClerkGroupDao(ClerkGroupDao clerkGroupDao) {
		this.clerkGroupDao = clerkGroupDao;
	}

	public ClerkService getClerkService() {
		return clerkService;
	}

	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ClerkGroupService#addClerkToGroups(long, java.util.List)
	 */
	public void addClerkToGroups(long clerkId, List<Long> groupIds) {
		for(Long groupId: groupIds){
			createClerkGroup(clerkId, groupId);
		}
	}
	
	private void createClerkGroup(long clerkId, long groupId){
		ClerkGroup cg = new ClerkGroup();
		cg.setClerkID(clerkId);
		cg.setGroupID(groupId);
		cg.setInsertTime(new Date());
		cg.setStatus((byte) 1);
		cg.setType( 1);
		clerkGroupDao.save(cg);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ClerkGroupService#addClerksToGroup(java.util.List, long)
	 */
	public void addClerksToGroup(List<Long> clerkIds, long groupId) {
		for(long clerkId: clerkIds){
			createClerkGroup(clerkId, groupId);
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ClerkGroupService#createGroup(cn.redflagsoft.base.bean.RFSGroup)
	 */
	public RFSGroup createGroup(RFSGroup group) {
		group.setCategory(RFSGroup.CATEGORY_人员分组);
		group.setCanDelete(RFSGroup.DELETE_ENABLED);
		group.setCanInsert(RFSGroup.INSERT_ENABLED);
		group.setCanUpdate(RFSGroup.UPDATE_ENABLED);
		group.setStatus((byte) 1);
		group.setType( 1);
		return groupDao.save(group);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ClerkGroupService#findClerksInGroup(long)
	 */
	public List<Clerk> findClerksInGroup(long groupId) {
		final List<Long> ids = clerkGroupDao.findClerkIdsInGroup(groupId);
		
		//内置对象，不返回给前台
//		ids.remove(new Long(Clerk.CLERK_ID_ADMIN));
//		ids.remove(new Long(Clerk.CLERK_ID_RFSA));
		ClerkServiceImpl.removeSystemClerks(ids);
		
		if(ids.isEmpty()){
			return Collections.<Clerk>emptyList();
		}
		
		return makeClerkList(ids);
//		return new AbstractList<Clerk>(){
//			@Override
//			public Clerk get(int index) {
//				Long id = ids.get(index);
//				return clerkService.getClerk(id);
//			}
//			@Override
//			public int size() {
//				return ids.size();
//			}
//			/* (non-Javadoc)
//			 * @see java.util.AbstractList#remove(int)
//			 */
//			@Override
//			public Clerk remove(int index) {
//				Clerk clerk = get(index);
//				ids.remove(index);
//				return clerk;
//			}
//		};
	}
	
	private List<Clerk> makeClerkList(List<Long> ids){
		List<Clerk> list = new ArrayList<Clerk>();
		for (Long id : ids) {
			Clerk clerk = clerkService.getClerk(id);
			if(clerk != null){
				list.add(clerk);
			}else{
				log.warn("Clerk不存在：" + id);
			}
		}
		return list;
	}
	

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ClerkGroupService#findGroups(org.opoo.ndao.support.ResultFilter)
	 */
	@Queryable
	public List<RFSGroup> findGroups(ResultFilter filter) {
		if(filter == null){
			filter = ResultFilter.createEmptyResultFilter();
		}
		Criterion c = filter.getCriterion();
		//只能查人员分组的。
		Criterion categoryFilter = Restrictions.eq("category", RFSGroup.CATEGORY_人员分组);
		if(c == null){
			c = categoryFilter;
		}else{
			c = Restrictions.logic(c).and(categoryFilter);
		}
		filter.setCriterion(c);
		
		return groupDao.find(filter);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ClerkGroupService#findGroupsOfClerk(long)
	 */
	public List<RFSGroup> findGroupsOfClerk(long clerkId) {
		return clerkGroupDao.findGroupsOfClerk(clerkId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ClerkGroupService#removeClerkFromGroups(long, java.util.List)
	 */
	public int removeClerkFromGroups(long clerkId, List<Long> groupIds) {
		Criterion c = Restrictions.logic(Restrictions.eq("clerkID", clerkId))
						.and(Restrictions.in("groupID", groupIds));
		return clerkGroupDao.remove(c);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ClerkGroupService#removeClerksFromGroup(java.util.List, long)
	 */
	public int removeClerksFromGroup(List<Long> clerkIds, long groupId) {
		Criterion c = Restrictions.logic(Restrictions.in("clerkID", clerkIds))
			.and(Restrictions.eq("groupID", groupId));
		return clerkGroupDao.remove(c);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ClerkGroupService#removeGroup(long)
	 */
	public int removeGroup(long groupId) {
		RFSGroup group = getGroup(groupId);
		return deleteGroup(group);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ClerkGroupService#updateGroup(cn.redflagsoft.base.bean.RFSGroup)
	 */
	public RFSGroup updateGroup(RFSGroup group) {
		RFSGroup old = groupDao.get(group.getId());
		if(old == null){
			throw new IllegalArgumentException("找不到被修改的分组：" + group.getId());
		}
		
		if(RFSGroup.UPDATE_ENABLED.equals(old.getCanUpdate())){
			old.setCategory(RFSGroup.CATEGORY_人员分组);
			old.setCanDelete(RFSGroup.DELETE_ENABLED);
			old.setCanInsert(RFSGroup.INSERT_ENABLED);
			old.setCanUpdate(RFSGroup.UPDATE_ENABLED);
			old.setAbbr(group.getAbbr());
			old.setName(group.getName());
			if(group.getDisplayOrder() != null){
				old.setDisplayOrder(group.getDisplayOrder());
			}
			return groupDao.update(old);
		}else{
//			log.warn(String.format("人员分组%s(id=%s)是内置的，不能被修改", 
//					group.getName(), group.getId()));
//			return null;
			throw new IllegalStateException(String.format("人员分组%s(id=%s)是内置的，不能被修改", 
					old.getName(), old.getId()));
		}
//		return groupDao.update(group);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ClerkGroupService#removeGroups(java.util.List)
	 */
	public int removeGroups(List<Long> ids) {
		int n = 0;
		for(Long id: ids){
			n += removeGroup(id);
		}
		return n;
	}
	
	public int deleteGroup(RFSGroup group){
		if(RFSGroup.DELETE_ENABLED.equals(group.getCanDelete())){
			return groupDao.delete(group);
		}else{
			log.warn(String.format("人员分组%s(id=%s)是内置的，不能被删除", 
					group.getName(), group.getId()));
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ClerkGroupService#getGroup(long)
	 */
	public RFSGroup getGroup(long id) {
		return groupDao.get(id);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ClerkGroupService#addClerkToGroup(long, long)
	 */
	public void addClerkToGroup(long clerkId, long groupId) {
		createClerkGroup(clerkId, groupId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ClerkGroupService#removeClerkFromGroup(long, long)
	 */
	public int removeClerkFromGroup(long clerkId, long groupId) {
		Criterion c = Restrictions.logic(Restrictions.eq("clerkID", clerkId))
			.and(Restrictions.eq("groupID", groupId));
		return clerkGroupDao.remove(c);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ClerkGroupService#findClerksNotInGroup(long)
	 */
	public List<Clerk> findClerksNotInGroup(long groupId) {
		final List<Long> ids = clerkGroupDao.findClerkIdsNotInGroup(groupId);
		
		//内置对象，不返回给前台
		ids.remove(new Long(Clerk.CLERK_ID_ADMIN));
		ids.remove(new Long(Clerk.CLERK_ID_RFSA));
		
		if(ids.isEmpty()){
			return Collections.<Clerk>emptyList();
		}
		
		return makeClerkList(ids);
		
//		return new AbstractList<Clerk>(){
//			@Override
//			public Clerk get(int index) {
//				Long id = ids.get(index);
//				return clerkService.getClerk(id);
//			}
//			@Override
//			public int size() {
//				return ids.size();
//			}
//			/* (non-Javadoc)
//			 * @see java.util.AbstractList#remove(int)
//			 */
//			@Override
//			public Clerk remove(int index) {
//				Clerk clerk = get(index);
//				ids.remove(index);
//				return clerk;
//			}
//		};
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ClerkGroupService#findGroupsNotOfClerk(long)
	 */
	public List<RFSGroup> findGroupsNotOfClerk(long clerkId) {
		return clerkGroupDao.findGroupsNotOfClerk(clerkId);
	}

	/*
	 * (non-Javadoc)
	 * @see ClerkGroupService#sortClerksOfGroup(long, List)
	 */
	public int sortClerksOfGroup(long groupId, List<Long> sortedClerkIds) {
		int cnt = 0;
		int order = 10;
		for (Long id : sortedClerkIds) {
			cnt += clerkGroupDao.updateDisplayOrder(groupId, id, order);
			order += 10;
		}
		return cnt;
	}
}
