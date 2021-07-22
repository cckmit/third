/*
 * $Id: InternalUser.java 6335 2013-12-11 06:30:56Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;

import java.security.Principal;
import java.util.Date;
import java.util.Map;

import org.opoo.apps.security.User;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;

import com.google.common.collect.Maps;

/**
 * 系统内置用户。
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class InternalUser implements User, UserDetails, Principal {
	private static final long serialVersionUID = -3969979552253766865L;
	private final String username;
	private final long userId;
	private final String name;

	/**
	 * 
	 */
	InternalUser(String username, long userId) {
		this(username, userId, username);
	}
	
	InternalUser(String username, long userId, String name){
		this.userId = userId;
		this.username = username;
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#getAuthorities()
	 */
	public GrantedAuthority[] getAuthorities() {
		return new GrantedAuthority[0];
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#getPassword()
	 */
	public String getPassword() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#getUsername()
	 */
	public String getUsername() {
		return username;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#isAccountNonExpired()
	 */
	public boolean isAccountNonExpired() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#isAccountNonLocked()
	 */
	public boolean isAccountNonLocked() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#isEnabled()
	 */
	public boolean isEnabled() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.User#getAccountExpireTime()
	 */
	public Date getAccountExpireTime() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.User#getCreationTime()
	 */
	public Date getCreationTime() {
		return new Date(0);
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.User#getCreator()
	 */
	public String getCreator() {
		return "rfsa";
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.User#getCredentialsExpireTime()
	 */
	public Date getCredentialsExpireTime() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.User#getLastLoginTime()
	 */
	public Date getLastLoginTime() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.User#getLoginAddress()
	 */
	public String getLoginAddress() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.User#getLoginCount()
	 */
	public int getLoginCount() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.User#getLoginTime()
	 */
	public Date getLoginTime() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.User#getModificationTime()
	 */
	public Date getModificationTime() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.User#getModifier()
	 */
	public String getModifier() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.User#getOnlineStatus()
	 */
	public OnlineStatus getOnlineStatus() {
		return OnlineStatus.INVISIBLE;
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.User#getProperties()
	 */
	public Map<String, String> getProperties() {
		return Maps.newHashMap();
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.User#getTryLoginCount()
	 */
	public int getTryLoginCount() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.User#getUserId()
	 */
	public Long getUserId() {
		return userId;
	}
	
	/* (non-Javadoc)
	 * @see java.security.Principal#getName()
	 */
	public String getName() {
		return name;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return username.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		if(!(obj instanceof User)){
			return false;
		}
		User user = (User) obj;
		return username.equals(user.getUsername()) && userId == user.getUserId().longValue(); 
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return username + "{userId: " + userId + "}";
	}
	
	public void putIntoSecurityContext(){
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, "*"));
	}
}
