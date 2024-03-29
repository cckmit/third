/*
 * $Id: PeopleGroupDao.java 3996 2010-10-18 06:56:46Z lcj $
 * PeopleGroup.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.PeopleGroup;
import cn.redflagsoft.base.bean.RFSGroup;

/**
 * @author mwx
 *
 */
public interface PeopleGroupDao  extends Dao<PeopleGroup,Long>{

	/**
	 * 查找组中人员。
	 * 
	 * @param groupId
	 * @return
	 */
	List<Clerk> getClerkInGroup(Long groupId);
	
	/**
	 * 查找不在组中的成员。
	 * 
	 * @param groupId
	 * @return
	 */
	List<Clerk> getClerkNotInGroup(Long groupId);
	
	/**
	 * 查找人员所属的组。
	 * 
	 * @param clerkId
	 * @return
	 */
	List<RFSGroup> getGroupOfClerk(Long clerkId);
}
