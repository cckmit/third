/*
 * $Id: MultiUserDetailsService.java 5142 2011-11-29 04:15:06Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

/**
 * @author Alex Lin(lcql@msn.com)
 */
public class MultiUserDetailsService implements UserDetailsService {
	
	private UserDetailsService userDetailsService;
	/**
	 * 
	 */
	public MultiUserDetailsService() {
	}

	/**
	 * @return the userDetailsService
	 */
	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	/**
	 * @param userDetailsService the userDetailsService to set
	 */
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		if(WorkflowUser.USERNAME.equalsIgnoreCase(username)){
			return WorkflowUser.getInstance();
		}else if(SupervisorUser.USERNAME.equalsIgnoreCase(username)){
			return SupervisorUser.getInstance();
		}else if(SchedulerUser.USERNAME.equals(username)){
			return SchedulerUser.getInstance();
		}else{
			return userDetailsService.loadUserByUsername(username);
		}
	}
}
