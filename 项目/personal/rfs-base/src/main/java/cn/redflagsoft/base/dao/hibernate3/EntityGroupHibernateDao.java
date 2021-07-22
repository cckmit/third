/*
 * $Id: EntityGroupHibernateDao.java 5237 2011-12-21 01:44:52Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.opoo.apps.Labelable;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.EntityGroup;
import cn.redflagsoft.base.bean.LabelDataBean;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.OrgGroup;
import cn.redflagsoft.base.dao.EntityGroupDao;

/**
 * @author mwx
 *
 */
public class EntityGroupHibernateDao extends AbstractBaseHibernateDao<EntityGroup,Long> implements EntityGroupDao{
	public static final String QUERY_LABEL_DATA_ORG = "select new cn.redflagsoft.base.bean.DefaultLabelDataBean(a.abbr, a.id) from Org a, EntityGroup b where a.id=b.entityID";
	
	
	@SuppressWarnings("unchecked")
	public List<Org> findOrgsInGroup(Long id) {
		String qs = "select a from Org a, EntityGroup b where a.id=b.entityID and b.groupID=? order by b.displayOrder asc";
		return getHibernateTemplate().find(qs, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> findOrgIdsInGroup(Long groupId){
		String qs = "select entityID from EntityGroup where groupID=? order by displayOrder asc";
		return getHibernateTemplate().find(qs, groupId);
	}

	@SuppressWarnings("unchecked")
	public List<Org> findOrgsNotInGroup(Long groupId) {
		//String qs = "select a from Org a, EntityGroup b where a.id=b.entityID and b.groupID<>?";
		String qs = "from Org where id not in(select entityID from EntityGroup where groupID=?) order by displayOrder asc";
		return getHibernateTemplate().find(qs, groupId);
	}

	public List<LabelDataBean> findLabelDataBeans() {
		throw new UnsupportedOperationException("findLabelDataBeans");
		//return getHibernateTemplate().find(QUERY_LABEL_DATA_ORG);
	}

	@SuppressWarnings("unchecked")
	public List<LabelDataBean> findLabelDataBeans(ResultFilter rf) {
		//String sql = "select new cn.redflagsoft.base.bean.DefaultLabelDataBean(a.name, a.id) from Org a, EntityGroup b where a.id=b.entityID";
		return getQuerySupport().find(QUERY_LABEL_DATA_ORG, rf);
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.dao.LabelableDao#findLabelables(org.opoo.ndao.support.ResultFilter)
	 */
	@SuppressWarnings("unchecked")
	public List<Labelable> findLabelables(ResultFilter rf) {
		return getQuerySupport().find(QUERY_LABEL_DATA_ORG, rf);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.EntityGroupDao#findGroupByOrgId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<OrgGroup> findGroupsOfOrg(Long orgId) {
		String qs = "select a from OrgGroup a, EntityGroup b where a.id=b.groupID and b.entityID=?";
		return getHibernateTemplate().find(qs, orgId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.EntityGroupDao#findNotExistGroupByOrgId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<OrgGroup> findGroupsNotOfOrg(Long orgId) {
		//String qs = "select a from OrgGroup a, EntityGroup b where a.id=b.groupID and b.entityID<>?";
		String qs = "from OrgGroup where id not in(select groupID from EntityGroup where entityID=?) order by displayOrder";
		return getHibernateTemplate().find(qs, orgId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.EntityGroupDao#removeByGroupId(java.lang.Long)
	 */
	public int removeByGroupId(Long groupId) {
		String qs = "delete from EntityGroup where groupID=?";
		return getQuerySupport().executeUpdate(qs, groupId);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.EntityGroupDao#removeByOrgId(java.lang.Long)
	 */
	public int removeByOrgId(Long orgId) {
		String qs = "delete from EntityGroup where entityID=?";
		return getQuerySupport().executeUpdate(qs, orgId);
	}
	
	
	public int updateDisplayOrder(Long groupID, Long entityID, Short displayOrder){
		String qs = "update EntityGroup set displayOrder=? where groupID=? and entityID=?";
		return getQuerySupport().executeUpdate(qs, new Object[]{displayOrder, groupID, entityID});
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.EntityGroupDao#findClerksInOrgGroup(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Clerk> findClerksInOrgGroup(Long orgGroupId) {
		String qs = "select c from Clerk c where c.entityID in (select entityID from EntityGroup where groupID=?) order by c.displayOrder asc";
		return getHibernateTemplate().find(qs, orgGroupId);
	}
}
