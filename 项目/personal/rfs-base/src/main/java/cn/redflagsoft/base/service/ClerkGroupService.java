/*
 * $Id: ClerkGroupService.java 4037 2010-11-04 08:11:49Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.RFSGroup;

/**
 * ��Ա�����Լ���Ա���������ϵ�Ĺ���
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
	 * ������Ա���顣
	 * @param group
	 * @return
	 */
	RFSGroup createGroup(RFSGroup group);
	
	/**
	 * �޸���Ա���顣
	 * @param group
	 * @return
	 */
	RFSGroup updateGroup(RFSGroup group);
	
	/**
	 * ɾ����Ա���顣
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
	 * ��ѯ��Ա���顣
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
	 * ���������ض����еĳ�Ա˳��
	 * 
	 * @param groupId
	 * @param sortedClerkIds
	 * @return
	 */
	int sortClerksOfGroup(long groupId, List<Long> sortedClerkIds);
}
