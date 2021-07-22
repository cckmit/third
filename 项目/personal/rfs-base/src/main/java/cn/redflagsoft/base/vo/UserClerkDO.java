/*
 * $Id: UserClerkDO.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.vo;

import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.opoo.apps.security.User;
import org.opoo.apps.security.User.OnlineStatus;

import cn.redflagsoft.base.bean.Clerk;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class UserClerkDO extends Clerk {
	private static final long serialVersionUID = 5918405653089794819L;
	
	private String username;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private Date accountExpireTime;
	private Date credentialsExpireTime;
	private Date lastLoginTime;
	private String loginAddress;
	private int loginCount;
	private Date loginTime;
	private OnlineStatus onlineStatus;
	private int tryLoginCount;
	
	public UserClerkDO() {
	}
	
	public UserClerkDO(User user, Clerk clerk) {
		try {
			PropertyUtils.copyProperties(this, user);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		try {
			PropertyUtils.copyProperties(this, clerk);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Date getAccountExpireTime() {
		return accountExpireTime;
	}
	public void setAccountExpireTime(Date accountExpireTime) {
		this.accountExpireTime = accountExpireTime;
	}
	public Date getCredentialsExpireTime() {
		return credentialsExpireTime;
	}
	public void setCredentialsExpireTime(Date credentialsExpireTime) {
		this.credentialsExpireTime = credentialsExpireTime;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLoginAddress() {
		return loginAddress;
	}
	public void setLoginAddress(String loginAddress) {
		this.loginAddress = loginAddress;
	}
	public int getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public OnlineStatus getOnlineStatus() {
		return onlineStatus;
	}
	public void setOnlineStatus(OnlineStatus onlineStatus) {
		this.onlineStatus = onlineStatus;
	}
	public int getTryLoginCount() {
		return tryLoginCount;
	}
	public void setTryLoginCount(int tryLoginCount) {
		this.tryLoginCount = tryLoginCount;
	}
}
