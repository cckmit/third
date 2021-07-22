/*
 * $Id: PeopleGroupHibernateDao.java 3996 2010-10-18 06:56:46Z lcj $
 * PeopleGroupHibernateDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.PeopleGroup;
import cn.redflagsoft.base.bean.RFSGroup;
import cn.redflagsoft.base.dao.PeopleGroupDao;

/**
 * @author mwx
 *
 */
public class PeopleGroupHibernateDao extends AbstractBaseHibernateDao<PeopleGroup,Long> implements PeopleGroupDao{

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.PeopleGroupDao#getClerkInGroup(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Clerk> getClerkInGroup(Long groupId) {
		String qs = "select a from Clerk a, PeopleGroup b where a.id=b.peopleID and b.groupID=?";
		return getHibernateTemplate().find(qs, groupId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.PeopleGroupDao#getClerkNotInGroup(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Clerk> getClerkNotInGroup(Long groupId) {
		String qs = "select a from Clerk a where a.id not in(select peopleID from PeopleGroup" +
				" where groupID=?)";
		return getHibernateTemplate().find(qs, groupId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.PeopleGroupDao#getGroupOfClerk(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<RFSGroup> getGroupOfClerk(Long clerkId) {
		String qs = "select a from RFSGroup a, PeopleGroup b where a.id=b.groupID and b.peopleID=?";
		return getHibernateTemplate().find(qs, clerkId);
	}

}
