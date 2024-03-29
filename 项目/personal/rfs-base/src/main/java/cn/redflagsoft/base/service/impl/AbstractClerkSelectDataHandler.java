/*
 * $Id: AbstractClerkSelectDataHandler.java 5894 2012-06-20 02:11:23Z thh $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserManager;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.RFSGroup;
import cn.redflagsoft.base.bean.SelectDataSource;
import cn.redflagsoft.base.dao.ClerkGroupDao;
import cn.redflagsoft.base.vo.UserClerkDO;

import com.google.common.collect.Lists;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class AbstractClerkSelectDataHandler implements SelectDataHandler<Clerk> {
	private UserManager userManager; 
	private ClerkGroupDao clerkGroupDao;
	
	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public ClerkGroupDao getClerkGroupDao() {
		return clerkGroupDao;
	}

	public void setClerkGroupDao(ClerkGroupDao clerkGroupDao) {
		this.clerkGroupDao = clerkGroupDao;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.impl.SelectDataHandler#findSelectData(cn.redflagsoft.base.bean.SelectDataSource, org.opoo.ndao.support.ResultFilter)
	 */
	public final List<Clerk> findSelectData(SelectDataSource dataSource, ResultFilter filter) {
		List<Clerk> list = findClerks(dataSource, filter);
		List<Long> ids = clerkGroupDao.findClerkIdsInGroup(RFSGroup.ID_隐藏人员分组);
		
		if(list == null || list.isEmpty()){
			return Collections.emptyList();
		}
		
		String remark = dataSource.getRemark();
		boolean requireUser = StringUtils.isNotBlank(remark) && remark.indexOf("IS_USER") != -1;
		
		List<Clerk> result = Lists.newArrayList();
		
		for(Clerk clerk: list){
			Clerk clerk2 = transform(clerk, ids, requireUser);
			if(clerk2 != null){
				result.add(clerk2);
			}
		}
		
		return result;
	}
	
	
	/**
	 * 
	 * @param clerk
	 * @param hiddenClerkIds
	 * @param requireUser 要求返回的结果类型是否是用户
	 * @return
	 */
	private Clerk transform(Clerk clerk, List<Long> hiddenClerkIds, boolean requireUser){
		if(isInHiddenClerkGroup(clerk, hiddenClerkIds)){
			return null;
		}
		User user = (User) userManager.loadUserByUserId(clerk.getId());
		if(user == null){
			if(requireUser){
				return null;
			}else{
				return clerk;
			}
		}
		
		if(user.isEnabled()){
			return requireUser ? new UserClerkDO(user, clerk) : clerk;
		}
		
		return null;
	}
	
	/**
	 * 判断指定Clerk是否在隐藏人员分组中
	 * @param clerk 指定的clerk对象
	 * @param hiddenClerkIds 隐藏分组中的人员ID集合。
	 * @return 如果指定的clerk对象的ID在隐藏人员分组的集合中，返回true，否则返回false。
	 */
	private boolean isInHiddenClerkGroup(Clerk clerk, List<Long> hiddenClerkIds){
		return hiddenClerkIds.contains(clerk.getId());
	}
	
	protected abstract List<Clerk> findClerks(SelectDataSource dataSource, ResultFilter filter);
}
