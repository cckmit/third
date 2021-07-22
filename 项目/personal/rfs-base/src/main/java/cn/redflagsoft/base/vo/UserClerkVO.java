/*
 * $Id: UserClerkVO.java 6445 2015-05-19 08:24:10Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.vo;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.Group;
import org.opoo.apps.security.GroupManager;
import org.opoo.apps.security.SecurityUtils;
import org.opoo.apps.security.User;
import org.opoo.apps.security.User.OnlineStatus;
import org.opoo.apps.security.UserManager;
import org.opoo.apps.security.bean.LiveUser;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.security.AuthUtils;
import cn.redflagsoft.base.security.BaseAuthority;
import cn.redflagsoft.base.security.PasswordProtectedUserWrapper;
import cn.redflagsoft.base.security.UserClerk;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * @author Alex Lin
 *
 */
public class UserClerkVO extends Clerk implements UserDetails{
	private static final long serialVersionUID = 6337706379162908554L;
	private static final Log log = LogFactory.getLog(UserClerkVO.class);
	private LiveUser user;
	private boolean manager;
	private Long groupID;
	private String groupName;
	private String groupNames;
	private String groupIds;
//	private transient boolean groupInfoLoaded = false; 
	
	public UserClerkVO(){
		user = new LiveUser();
		//通过该构造函数创建的对象，不加载器组信息（角色）
//		groupInfoLoaded = true;
		user.setAuthorities(new GrantedAuthority[0]);
	}

	public UserClerkVO(UserClerk uc){
		this(uc.getUser(), uc.getClerk());
	}
	
	public UserClerkVO(User user, Clerk clerk){
		user = new PasswordProtectedUserWrapper(user);
		this.user = new LiveUser(user);
		try {
			PropertyUtils.copyProperties(this, clerk);
		} catch (Exception e) {
			throw new IllegalArgumentException("Copy Clerk properties to UserClerkVO error.", e);
		}
		tryLoadGroupInfo();
		tryLookupManagerAuthority();
	}
	
	private void tryLoadGroupInfo(){
		if(/*!groupInfoLoaded &&*/ user != null && user.getUsername() != null){
			log.debug("查询用户的第一个角色信息：" + user.getUsername());
			UserManager userManager = Application.getContext().getUserManager();
			//设置用户的第一个角色信息
			List<Group> groupsOfUser = ((GroupManager) userManager).findGroupsOfUser(user.getUsername());
			if(groupsOfUser != null && !groupsOfUser.isEmpty()){
				Group group = groupsOfUser.iterator().next();
				setGroupID(group.getId());
				setGroupName(group.getName());
				if(log.isDebugEnabled()){
					log.debug("Group for User('" + user.getUsername() + "') found: " + group.getId() + " -> " + group.getName());
				}
				groupNames = "";
				groupIds = "";
				for(Group g: groupsOfUser){
					if(groupIds.length() > 0){
						groupIds += ",";
						groupNames += ",";
					}
					groupIds += g.getId();
					groupNames += g.getName();
				}
			}
//			groupInfoLoaded = true;
		}
	}
	
	private void tryLookupManagerAuthority(){
		if(user != null && user.getAuthorities() != null){
			this.manager = SecurityUtils.isGranted(user, AuthUtils.ROLE_MANAGER) 
				|| SecurityUtils.isGranted(user, BaseAuthority.MANAGER.name());
		}
		/*
		if(SecurityUtils.isGranted(user, AuthUtils.ROLE_MANAGER)
				|| SecurityUtils.isGranted(user, "MANAGER")){
			vo.setManager(true);
		}*/
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return user.getUsername();
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		user.setUsername(username);
	}

	
	private String password;
	/**
	 * @return the password
	 */
	public String getPassword() {
		//return user.getPassword();
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		//user.setPassword(password);
		this.password = password;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return user.isEnabled();
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		user.setEnabled(enabled);
	}

	/**
	 * @return the accountNonLocked
	 */
	public boolean isAccountNonLocked() {
		return user.isAccountNonLocked();
	}

	/**
	 * @param accountNonLocked the accountNonLocked to set
	 */
	public void setAccountNonLocked(boolean accountNonLocked) {
		user.setAccountNonLocked(accountNonLocked);
	}

	/**
	 * @return the accountExpireTime
	 */
	public Date getAccountExpireTime() {
		return user.getAccountExpireTime();
	}

	/**
	 * @param accountExpireTime the accountExpireTime to set
	 */
	public void setAccountExpireTime(Date accountExpireTime) {
		user.setAccountExpireTime(accountExpireTime);
	}

	/**
	 * @return the credentialsExpireTime
	 */
	public Date getCredentialsExpireTime() {
		return user.getCredentialsExpireTime();
	}

	/**
	 * @param credentialsExpireTime the credentialsExpireTime to set
	 */
	public void setCredentialsExpireTime(Date credentialsExpireTime) {
		user.setCredentialsExpireTime(credentialsExpireTime);
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getLastLoginTime() {
		return user.getLastLoginTime();
	}

	public int getLoginCount() {
		return user.getLoginCount();
	}


	public int getTryLoginCount() {
		return user.getTryLoginCount();
	}


	public GrantedAuthority[] getAuthorities() {
		//throw new UnsupportedOperationException("getAuthorities()");
		return new GrantedAuthority[0];
	}


	public boolean isAccountNonExpired() {
		return user.isAccountNonExpired();
	}

	public boolean isCredentialsNonExpired() {
		return user.isAccountNonExpired();
	}

	/**
	 * @param intEnabled the intEnabled to set
	 */
	public void setIntEnabled(int intEnabled) {
		this.setEnabled(intEnabled == 1);
	}

	/**
	 * @return the intEnabled
	 */
	public int getIntEnabled() {
		return isEnabled() ? 1 : 0;
	}
	
	
	public String getEntityIDLabel(){
		return getEntityName();
	}
	
	public void setEntityIDLabel(String value){
		setEntityName(value);
	}
	
	
	/**
	 * @return the manager
	 * @ deprecated 使用角色，不再单独使用用户的权限控制
	 * 再次启用 2013-06-26
	 */
	public boolean isManager() {
//		return SecurityUtils.isGranted(user, AuthUtils.ROLE_MANAGER) 
//			|| SecurityUtils.isGranted(user, BaseAuthority.MANAGER.name());
		return manager;
	}

	/**
	 * @param manager the manager to set
	 * @ deprecated 使用角色，不再单独使用用户的权限控制
	 * 再次启用 2013-06-26
	 */
	public void setManager(boolean manager) {
		this.manager = manager;
	}
	
	public void setEnabledName(String value){
		
	}
	
	public String getEnabledName(){
		return isEnabled() ? "在用" : "禁用";
	}
	
	public Long getUserId(){
		return user.getUserId();
	}
	
	public void setUserId(Long userId){
		user.setUserId(userId);
	}
	
	public String getOnlineStatus(){
		if(user.getOnlineStatus().equals(OnlineStatus.ONLINE)){
			return "在线";
		}else if(user.getOnlineStatus().equals(OnlineStatus.IDLE)){
			return "离开";
		}else if(user.getOnlineStatus().equals(OnlineStatus.INVISIBLE)){
			return "隐身";
		}else{
			return "离线";
		}
	}

	/**
	 * 第一个角色的id
	 * @return the groupID
	 */
	public Long getGroupID() {
//		tryLoadGroupInfo();
		return groupID;
	}

	/**
	 * @param groupID the groupID to set
	 */
	public void setGroupID(Long groupID) {
		this.groupID = groupID;
	}

	/**
	 * 第一个角色的名称。
	 * @return the groupName
	 */
	public String getGroupName() {
//		tryLoadGroupInfo();
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public void setGroupIDLabel(String groupName){
		setGroupName(groupName);
	}

	public String getGroupNames() {
		return groupNames;
	}

	public void setGroupNames(String groupNames) {
		this.groupNames = groupNames;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}
	
}
