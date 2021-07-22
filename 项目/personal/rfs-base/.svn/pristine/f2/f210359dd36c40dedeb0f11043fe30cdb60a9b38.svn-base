/*
 * ClerkDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.security.UserClerk;
import cn.redflagsoft.base.vo.UserClerkVO;

/**
 * @author Administrator
 *
 */
public interface ClerkDao extends Dao<Clerk,Long>{
	/**
	 * 查询人员ID集合。
	 * 
	 * @param rf
	 * @return
	 */
	List<Long> findClerkIds(ResultFilter rf);
	/**
	 * 查询用户人员集合。
	 * @param rf
	 * @return
	 */
	List<UserClerk> findUserClerks(ResultFilter rf);
	/**
	 * 
	 * @param rf
	 * @return
	 */
	List<UserClerkVO> findUserClerkVOs(ResultFilter rf);
	
	/**
	 * 根据条件查询用户和职员，返回用户名列表。
	 * 
	 * @param rf
	 * @return
	 */
	List<String> findUserClerkUsernames(ResultFilter rf);
	
	/**
	 * 分页查询人员的ID集合。
	 * @param rf
	 * @return
	 */
	PageableList<Long> findPageableClerkIds(ResultFilter rf);
	
	/**
	 * 根据工作单位名称查询人员集合。
	 * @param entityName
	 * @return
	 */
	List<Clerk> findClerkByEntityName(String entityName);
	
	/**
	 * 批量更新人员的工作单位。
	 * @param entityID
	 * @param entityName
	 * @return
	 */
	int updateEntityName(Long entityID, String entityName);
	
	/**
	 * @param filter
	 * @return
	 * @see #findUserClerkUsernames(ResultFilter)
	 */
	int getUserClerkUsernameCount(ResultFilter filter);
}
