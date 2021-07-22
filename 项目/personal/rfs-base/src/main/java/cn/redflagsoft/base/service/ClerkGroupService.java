/*
 * $Id: ClerkGroupService.java 4037 2010-11-04 08:11:49Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.RFSGroup;

/**
 * 人员分组以及人员分组关联关系的管理。
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface ClerkGroupService {

	/**
	 * 
	 * @param id
	 * @return
	 */
	RFSGroup getGroup(long id);
	/**
	 * 创建人员分组。
	 * @param group
	 * @return
	 */
	RFSGroup createGroup(RFSGroup group);
	
	/**
	 * 修改人员分组。
	 * @param group
	 * @return
	 */
	RFSGroup updateGroup(RFSGroup group);
	
	/**
	 * 删除人员分组。
	 * @param groupId
	 */
	int removeGroup(long groupId);
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	int removeGroups(List<Long> ids);
	
	/**
	 * 查询人员分组。
	 * @param filter
	 * @return
	 */
	List<RFSGroup> findGroups(ResultFilter filter);
	
	/**
	 * 
	 * @param clerkId
	 * @return
	 */
	List<RFSGroup> findGroupsOfClerk(long clerkId);
	
	/**
	 * @param id
	 * @return
	 */
	List<RFSGroup> findGroupsNotOfClerk(long clerkId);
	
	/**
	 * 
	 * @param groupId
	 * @return
	 */
	List<Clerk> findClerksInGroup(long groupId);
	
	/**
	 * 
	 * @param groupId
	 * @return
	 */
	List<Clerk> findClerksNotInGroup(long groupId);
	
//	/**
//	 * 
//	 * @param clerkId
//	 * @param groupId
//	 */
//	void addToGroup(long clerkId, long groupId);
	
	
	void addClerkToGroup(long clerkId, long groupId);
	
	/**
	 * 
	 * @param clerkId
	 * @param groupIds
	 */
	void addClerkToGroups(long clerkId, List<Long> groupIds);
	
	/**
	 * 
	 * @param clerkIds
	 * @param groupId
	 */
	void addClerksToGroup(List<Long> clerkIds, long groupId);
	
	
	/**
	 * 
	 * @param clerkId
	 * @param groupIds
	 * @return
	 */
	int removeClerkFromGroups(long clerkId, List<Long> groupIds);
	
	/**
	 * 
	 * @param clerkId
	 * @param groupId
	 * @return
	 */
	int removeClerksFromGroup(List<Long> clerkIds, long groupId);
	
	
	/**
	 * 
	 * @param clerkId
	 * @param groupId
	 * @return
	 */
	int removeClerkFromGroup(long clerkId, long groupId);
	
	
	/**
	 * 重新设置特定组中的成员顺序。
	 * 
	 * @param groupId
	 * @param sortedClerkIds
	 * @return
	 */
	int sortClerksOfGroup(long groupId, List<Long> sortedClerkIds);
}
