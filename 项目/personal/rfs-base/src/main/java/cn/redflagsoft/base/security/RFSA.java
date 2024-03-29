/*
 * $Id: RFSA.java 6335 2013-12-11 06:30:56Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;

import java.util.Date;
import java.util.Map;

import org.opoo.apps.AppsGlobals;
import org.opoo.apps.security.MutableUser;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;

import com.google.common.collect.Maps;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class RFSA extends InternalUser implements MutableUser{
	private static final long serialVersionUID = -6986046056557392660L;
	public static final String USERNAME = "rfsa";
	public static final long USER_ID = 1L;
	
	
	public static final String PROP_SYSTEM_PASSWD = "app.system.password";
	public static final String PROP_SYSTEM_LAST_LOGIN = "app.system.lastLogin";
	public static final String PROP_SYSTEM_LOGIN_ADDR = "app.system.loginAddr";
	public static final String PROP_SYSTEM_LOGIN_COUNT = "app.system.loginCount";
	public static final String PROP_SYSTEM_TRY_LOGIN_COUNT = "app.system.tryLoginCount";
	public static final String PROP_SYSTEM_MTIME = "app.system.mtime";
	
	private Date loginTime = new Date();
	private OnlineStatus onlineStatus = OnlineStatus.INVISIBLE;
	
	/**S
	 * @param username
	 * @param userId
	 */
	public RFSA() {
		super(USERNAME, USER_ID);
	}
	
	
	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#getAuthorities()
	 */
	public GrantedAuthority[] getAuthorities() {
		return new GrantedAuthority[]{
				new GrantedAuthorityImpl("ROLE_ADMIN"),
				new GrantedAuthorityImpl("ROLE_USER")//,
				//new GrantedAuthorityImpl("ADMIN"),
				//new GrantedAuthorityImpl("USER")
			};
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#getPassword()
	 */
	public String getPassword() {
		//System.out.println("获取密码：" + this);
		return AppsGlobals.getProperty(PROP_SYSTEM_PASSWD);
	}

	public void setPassword(String password){
		AppsGlobals.setProperty(PROP_SYSTEM_PASSWD, password);
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
		return true;
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
		return "system";
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
		long time = AppsGlobals.getProperty(PROP_SYSTEM_LAST_LOGIN, 0L);
		return new Date(time);
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.User#getLoginAddress()
	 */
	public String getLoginAddress() {
		return AppsGlobals.getProperty(PROP_SYSTEM_LOGIN_ADDR);
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.User#getLoginCount()
	 */
	public int getLoginCount() {
		return AppsGlobals.getProperty(PROP_SYSTEM_LOGIN_COUNT, 0);
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.User#getLoginTime()
	 */
	public Date getLoginTime() {
		return loginTime;
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.User#getModificationTime()
	 */
	public Date getModificationTime() {
		return new Date(AppsGlobals.getProperty(PROP_SYSTEM_MTIME, 0L));
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
		//return OnlineStatus.INVISIBLE;
		return onlineStatus;
	}
	
	public void setOnlineStatus(OnlineStatus status){
		this.onlineStatus = status;
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
		return AppsGlobals.getProperty(PROP_SYSTEM_TRY_LOGIN_COUNT, 0);
	}

	/* (non-Javadoc)
	 * @see java.security.Principal#getName()
	 */
	public String getName() {
		return "RFSA";
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.MutableUser#setLastLoginTime(java.util.Date)
	 */
	public void setLastLoginTime(Date date) {
		if(date != null){
			AppsGlobals.setProperty(PROP_SYSTEM_LAST_LOGIN, date.getTime()+"");
		}else{
			AppsGlobals.deleteProperty(PROP_SYSTEM_LAST_LOGIN);
		}
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.MutableUser#setLoginAddress(java.lang.String)
	 */
	public void setLoginAddress(String addr) {
		if(addr != null){
			AppsGlobals.setProperty(PROP_SYSTEM_LOGIN_ADDR, addr);
		}else{
			AppsGlobals.deleteProperty(PROP_SYSTEM_LOGIN_ADDR);
		}
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.MutableUser#setLoginCount(int)
	 */
	public void setLoginCount(int count) {
		AppsGlobals.setProperty(PROP_SYSTEM_LOGIN_COUNT, String.valueOf(count));
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.MutableUser#setLoginTime(java.util.Date)
	 */
	public void setLoginTime(Date date) {
		this.loginTime = date;
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.security.MutableUser#setTryLoginCount(int)
	 */
	public void setTryLoginCount(int count) {
		AppsGlobals.setProperty(PROP_SYSTEM_TRY_LOGIN_COUNT, String.valueOf(count));
	}
}
