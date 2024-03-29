/*
 * $Id: OrgGroupService.java 6314 2013-11-06 09:15:38Z lf $
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

import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.OrgGroup;

/**
 * 单位分组管理，以及单位分组关联关系的管理。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface OrgGroupService {
	
	OrgGroup createGroup(OrgGroup grp);
	
	OrgGroup updateGroup(OrgGroup grp);
	
	int removeGroup(Long id);
	
	int removeGroups(List<Long> groupIds);
	
	OrgGroup getGroup(long id);
	
	//////////////////////////////////////

	List<OrgGroup> findGroups(ResultFilter filter);
	
	
	List<OrgGroup> findGroupsOfOrg(long orgId);
	
	List<OrgGroup> findGorupNotOfOrg(long orgId);
	
	List<Org> findOrgsInGroup(long groupId);
	
	/**
	 * 查询指定单位分组中的单位ID集合。
	 * @param groupId
	 * @return
	 */
	List<Long> findOrgIdsInGroup(long groupId);
	
	List<Org> findOrgsNotInGroup(long groupId);
	

	void addOrgToGroup(long orgId, long groupId);
	
	void addOrgToGroups(long orgId, List<Long> groupIds);
	

	void addOrgsToGroup(List<Long> orgIds, long groupId);
	
	
	/**
	 * 
	 * @param clerkId
	 * @param groupIds
	 * @return
	 */
	int removeOrgFromGroups(long orgId, List<Long> groupIds);
	
	/**
	 * 
	 * @param clerkId
	 * @param groupId
	 * @return
	 */
	int removeOrgsFromGroup(List<Long> orgIds, long groupId);
	
	
	/**
	 * 
	 * @param orgId
	 * @param groupId
	 * @return
	 */
	int removeOrgFromGroup(long orgId, long groupId);
	
	
	/**
	 * 对指定组的单位重新排序。
	 * 
	 * @param groupId
	 * @param sortedOrgIds
	 * @return
	 */
	int sortOrgsOfGroup(long groupId, List<Long> sortedOrgIds);
	
	
	int removeOrgGroup(long orgId);
}
