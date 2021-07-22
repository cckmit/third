/*
 * $Id: EntityGroupDao.java 4341 2011-04-22 02:12:39Z lcj $
 * EntityGroupDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.EntityGroup;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.OrgGroup;

/**
 * @author mwx
 *
 */
public interface EntityGroupDao extends Dao<EntityGroup,Long>, LabelDataBeanDao{

	/**
	 * ��ѯָ����λ�����еĵ�λ�б�
	 * @param groupId ��λ����ID
	 * @return ��λ����
	 */
	List<Org> findOrgsInGroup(Long groupId);
	
	/**
	 * ��ѯָ����λ�����еĵ�λID���ϡ�
	 * 
	 * @param groupId ��λ����ID
	 * @return ��λID����
	 */
	List<Long> findOrgIdsInGroup(Long groupId);
	
	/**
	 * 
	 * @param orgGroupId
	 * @return
	 */
	List<Clerk> findClerksInOrgGroup(Long orgGroupId);
	
	/**
	 * 
	 * @param groupId
	 * @return
	 */
	List<Org> findOrgsNotInGroup(Long groupId);
	
	
	/**
	 * ���ҵ�λ�������顣
	 * 
	 * @param orgId
	 * @return
	 */
	List<OrgGroup> findGroupsOfOrg(Long orgId);
	
	
	/**
	 * �ҳ���ǰ��λ�����ڵ��顣
	 * 
	 * @param orgId
	 * @return
	 */
	List<OrgGroup> findGroupsNotOfOrg(Long orgId);
	
	/**
	 * 
	 * @param groupId
	 * @return
	 */
	int removeByGroupId(Long groupId);
	
	/**
	 * 
	 * @param orgId
	 * @return
	 */
	int removeByOrgId(Long orgId);
	
	
	/**
	 * ����ָ���飬ָ��Org������š�
	 * @param groupID
	 * @param entityID
	 * @param displayOrder
	 * @return
	 */
	int updateDisplayOrder(Long groupID, Long entityID, Short displayOrder);
}
