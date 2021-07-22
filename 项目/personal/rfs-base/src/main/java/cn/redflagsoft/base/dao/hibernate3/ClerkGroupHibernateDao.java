/*
 * $Id: ClerkGroupHibernateDao.java 4341 2011-04-22 02:12:39Z lcj $
 * ClerkGroupHibernateDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.ClerkGroup;
import cn.redflagsoft.base.bean.RFSGroup;
import cn.redflagsoft.base.dao.ClerkGroupDao;

/**
 * @author mwx
 *
 */
public class ClerkGroupHibernateDao extends AbstractBaseHibernateDao<ClerkGroup,Long> implements ClerkGroupDao{

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.ClerkGroupDao#findClerkIdsInGroup(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> findClerkIdsInGroup(Long groupId) {
		String qs = "select clerkID from ClerkGroup where groupID=? order by displayOrder asc";
		return getHibernateTemplate().find(qs, groupId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.ClerkGroupDao#findClerkIdsNotInGroup(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> findClerkIdsNotInGroup(Long groupId) {
		String qs = "select id from Clerk where id not in(select clerkID from ClerkGroup where groupID=?) order by displayOrder asc";
		return getHibernateTemplate().find(qs, groupId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.ClerkGroupDao#findGroupsNotOfClerk(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<RFSGroup> findGroupsOfClerk(Long clerkId) {
		String qs = "select a from RFSGroup a, ClerkGroup b where a.id=b.groupID and b.clerkID=?";
		return getHibernateTemplate().find(qs, clerkId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.ClerkGroupDao#findGroupsOfClerk(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<RFSGroup> findGroupsNotOfClerk(Long clerkId) {
		String qs = "from RFSGroup where category=? and id not in (select groupID from ClerkGroup where clerkID=?)";
		return getHibernateTemplate().find(qs, new Object[]{RFSGroup.CATEGORY_人员分组, clerkId});
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.ClerkGroupDao#removeByClerkId(java.lang.Long)
	 */
	public int removeByClerkId(Long clerkId) {
		String qs = "delete from ClerkGroup where clerkID=?";
		return getQuerySupport().executeUpdate(qs, clerkId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.ClerkGroupDao#removeByGroupId(java.lang.Long)
	 */
	public int removeByGroupId(Long groupId) {
		String qs = "delete from ClerkGroup where groupID=?";
		return getQuerySupport().executeUpdate(qs, groupId);
	}

	
	public int updateDisplayOrder(Long groupId, Long clerkId, Integer displayOrder){
		String qs = "update ClerkGroup set displayOrder=? where groupID=? and clerkID=?";
		return getQuerySupport().executeUpdate(qs, new Object[]{displayOrder, groupId, clerkId});
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Long> findClerkIds(ResultFilter filter){
		String qs = "select clerkID from ClerkGroup";
		return getQuerySupport().find(qs, filter);
	}
}
