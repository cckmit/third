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
	 * 查询指定单位分组中的单位列表。
	 * @param groupId 单位分组ID
	 * @return 单位集合
	 */
	List<Org> findOrgsInGroup(Long groupId);
	
	/**
	 * 查询指定单位分组中的单位ID集合。
	 * 
	 * @param groupId 单位分组ID
	 * @return 单位ID集合
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
	 * 查找单位所属的组。
	 * 
	 * @param orgId
	 * @return
	 */
	List<OrgGroup> findGroupsOfOrg(Long orgId);
	
	
	/**
	 * 找出当前单位不属于的组。
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
	 * 更新指定组，指定Org的排序号。
	 * @param groupID
	 * @param entityID
	 * @param displayOrder
	 * @return
	 */
	int updateDisplayOrder(Long groupID, Long entityID, Short displayOrder);
}
