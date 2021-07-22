/*
 * $Id: ClerkGroupDao.java 4341 2011-04-22 02:12:39Z lcj $
 * 
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.ClerkGroup;
import cn.redflagsoft.base.bean.RFSGroup;

/**
 * @author mwx
 *
 */
public interface ClerkGroupDao extends Dao<ClerkGroup,Long>{

	/**
	 * 查找指定组中的人员。
	 * 
	 * @param groupId
	 * @return
	 */
	List<Long> findClerkIdsInGroup(Long groupId);
	
	/**
	 * 查找不在指定组中的人员。
	 * 
	 * @param groupId
	 * @return
	 */
	List<Long> findClerkIdsNotInGroup(Long groupId);
	
	/**
	 * 查找指定人员所属的组。
	 * 
	 * @param clerkId
	 * @return
	 */
	List<RFSGroup> findGroupsOfClerk(Long clerkId);
	
	/**
	 * 查找指定人员所不属的组。
	 * 
	 * @param clerkId
	 * @return
	 */
	List<RFSGroup> findGroupsNotOfClerk(Long clerkId);
	
	
	/**
	 * 
	 * @param clerkId
	 * @return
	 */
	int removeByClerkId(Long clerkId);
	
	
	/**
	 * 
	 * @param groupId
	 * @return
	 */
	int removeByGroupId(Long groupId);
	
	
	/**
	 * 
	 * @param groupId
	 * @param clerkId
	 * @param displayOrder
	 * @return
	 */
	int updateDisplayOrder(Long groupId, Long clerkId, Integer displayOrder);
	
	/**
	 * 根据查询条件查询人员id集合。
	 * 
	 * @param filter
	 * @return
	 */
	List<Long> findClerkIds(ResultFilter filter);
}
