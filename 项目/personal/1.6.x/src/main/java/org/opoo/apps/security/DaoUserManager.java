package org.opoo.apps.security;

import static org.opoo.apps.security.AuthorityUtils.dropRolePrefix;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.license.AppsLicenseManager;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.bean.LiveUser;
import org.opoo.apps.security.cache.AppsGroupCache;
import org.opoo.apps.security.cache.AppsUserCache;
import org.opoo.apps.security.cache.NullAppsUserCache;
import org.opoo.cache.Cache;
import org.opoo.cache.TimedExpirationMap;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.dao.SaltSource;
import org.springframework.security.providers.dao.cache.NullUserCache;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.providers.encoding.PlaintextPasswordEncoder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsManager;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.security.util.AuthorityUtils;
import org.springframework.util.Assert;

import com.jivesoftware.license.LicenseException;

/**
 * 用户管理。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class DaoUserManager extends DaoUserService implements UserManager, UserDetailsManager, GroupManager, InitializingBean {
	static class GroupList extends AbstractList<Group>{
		private final List<Long> ids;
		private final GroupManager groupManager;
		
		public GroupList(List<Long> ids, GroupManager groupManager) {
			this.ids = ids;
			this.groupManager = groupManager;
		}
		@Override
		public Group get(int index) {
			return groupManager.getGroup(ids.get(index));
		}
		@Override
		public int size() {
			return ids.size();
		}
	}
	static class UserList extends AbstractList<User>{
		private final List<String> usernames;
		private final UserManager userManager;
		
		public UserList(List<String> usernames, UserManager userManager) {
			this.usernames = usernames;
			this.userManager = userManager;
		}
		@Override
		public User get(int index) {
			String username = usernames.get(index);
			return (User) userManager.loadUserByUsername(username);
		}
		@Override
		public int size() {
			return usernames.size();
		}
	}
	protected static final Log logger = LogFactory.getLog(DaoUserManager.class);
	protected static final boolean IS_DEBUG_ENABLED = logger.isDebugEnabled();
	private AuthenticationManager authenticationManager;
	
	private PasswordEncoder passwordEncoder = new PlaintextPasswordEncoder();
	private SaltSource saltSource;

    /**
	 * 
	 */
	protected AppsUserCache userCache = new NullAppsUserCache();

//    /**
//     * A cache for group objects. This cache is not instantiated until after this factory
//     * is initialized.
//     */
//    protected Cache<Long, Group> groupCache;
//    
//    /**
//     * A cache that maps group names to ID's. This cache is not instantiated until after this
//     * factory is initialized.
//     */
//    protected Cache<String, Long> groupIdCache;
//	
//	/**
//     * A cache for the list of members in each group. This cache is not instantiated until after
//     * this factory is initialized.
//     */
//    protected Cache<String, List<Long>> groupMemberCache;
//	
//	/**
//     * 
//     */
//    protected Cache<Long, List<String>> groupAuthoritiesCache;
	
	protected AppsGroupCache groupCache;
    
	/**
	 * A cache for UsernameNotFoundException.
	 */
	private Cache<String, UsernameNotFoundException> notFoundUserCache = new TimedExpirationMap<String, UsernameNotFoundException>("notExistsUser", 600 * 1000, 300 * 1000);
	
	public void setNotFoundUserCache(
			Cache<String, UsernameNotFoundException> notFoundUserCache) {
		this.notFoundUserCache = notFoundUserCache;
	}

	public void addGroupAuthority(Long groupId, GrantedAuthority authority) {
        logger.debug("Adding authority '" + authority + "' to group '" + groupId + "'");
        Assert.notNull(groupId);
        Assert.notNull(authority);

        getGroupDao().addGroupAuthority(groupId, authority.getAuthority());
//        groupAuthoritiesCache.remove(groupId);
        groupCache.removeGroupAuthoritiesFromCache(groupId);
	}
	
    public void addGroupAuthority(String groupName, GrantedAuthority authority) {
        logger.debug("Adding authority '" + authority + "' to group '" + groupName + "'");
        Assert.hasText(groupName);
        Assert.notNull(authority);

        getGroupDao().addGroupAuthority(groupName, authority.getAuthority());
//        groupAuthoritiesCache.clear();
        groupCache.clearGroupAuthoritiesCache();
	}

    
	
	public void addUserToGroup(String username, Long groupId) {
        logger.debug("Adding user '" + username + "' to group '" + groupId + "'");
        Assert.hasText(username);
        Assert.notNull(groupId);
        
        getGroupDao().addUserToGroup(username, groupId);
        userCache.removeUserFromCache(username);
//        groupMemberCache.remove(username);
        groupCache.removeGroupIdsFromCache(username);
	}
	
	public void addUserToGroup(String username, String groupName) {
        logger.debug("Adding user '" + username + "' to group '" + groupName + "'");
        Assert.hasText(username);
        Assert.hasText(groupName);
        
        getGroupDao().addUserToGroup(username, groupName);
        userCache.removeUserFromCache(username);		
//        groupMemberCache.remove(username);
        groupCache.removeGroupIdsFromCache(username);
	}

	
	public void afterPropertiesSet() throws Exception {
        if (authenticationManager == null) {
            logger.info("No authentication manager set. Reauthentication of users when changing passwords will " +
                    "not be performed.");
        }
//		Assert.notNull(groupAuthoritiesCache);
		Assert.notNull(groupCache);
//		Assert.notNull(groupIdCache);
//		Assert.notNull(groupMemberCache);
	}
	
	
	
	public void changePassword(String oldPassword, String newPassword) {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		if (currentUser == null) {
		    // This would indicate bad coding somewhere
		    throw new AccessDeniedException("Can't change password as no Authentication object found in context " +
		            "for current user.");
		}
		
		String username = currentUser.getName();
		
		// If an authentication manager has been set, try find default one from global beans holder.
		if(authenticationManager == null){
			authenticationManager = Application.getContext().get("_authenticationManager", AuthenticationManager.class);
		}
		
		// If an authentication manager has been set, re-authenticate the user with the supplied password.
		if (authenticationManager != null) {
		    logger.debug("Reauthenticating user '"+ username + "' for password change request.");
		
		    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
		} else {
		    logger.debug("No authentication manager set. Password won't be re-checked.");
		}
		
		logger.debug("Changing password for user '"+ username + "'");
		
		
		/*
		UserDetails user = loadUserByUsername(username);
		
		Object salt = null;
        if (saltSource != null) {
            salt = saltSource.getSalt(user);
        }
		newPassword = passwordEncoder.encodePassword(newPassword, salt);
		
		getUserDao().updatePassword(username, newPassword);
		*/
		
		UserDetails user = updatePassword(username, newPassword);
		
		SecurityContextHolder.getContext().setAuthentication(buildNewAuthentication(currentUser, user, newPassword));
		
		//userCache.removeUserFromCache(username);
	}
	
	public void createGroup(String groupName, GrantedAuthority[] authorities) {
        Assert.hasText(groupName);
        Assert.notNull(authorities);
        Group g = getGroup(groupName);
        if(g != null){
        	throw new IllegalArgumentException("Group already exists: " + groupName);
        }
        
        if(logger.isDebugEnabled()){
        	logger.debug("Creating new group '" + groupName + "' with authorities " +
                    AuthorityUtils.authorityArrayToSet(authorities));
        }
        Group group = new Group(groupName);
        String[] as = new String[authorities.length];
        for(int i = 0, n = authorities.length ; i < n ; i++){
        	as[i] = authorities[i].getAuthority();
        }
        getGroupDao().createGroup(group, as);
	}
	
	public void createGroup(String groupName, List<GrantedAuthority> authorities) {
		createGroup(groupName, authorities.toArray(new GrantedAuthority[authorities.size()]));
	}
	
	
	
	protected LiveUser buildLiveUser(UserDetails details, String encodedPassword){
		LiveUser user = new LiveUser();
		user.setAccountNonLocked(details.isAccountNonExpired());
		user.setAuthorities(dropRolePrefix(details.getAuthorities(), getRolePrefix()));
		user.setEnabled(details.isEnabled());
		user.setPassword(encodedPassword /* details.getPassword() */);
		user.setUsername(details.getUsername());
		
		if(details instanceof User){
			User ud = (User) details;
			user.setExternalUserId(ud.getExternalUserId());
			user.setAccountExpireTime(ud.getAccountExpireTime());
			user.setCreationTime(ud.getCreationTime());
			user.setCreator(ud.getCreator());
			user.setCredentialsExpireTime(ud.getCredentialsExpireTime());
			user.setLastLoginTime(ud.getLastLoginTime());
			user.setLoginAddress(ud.getLoginAddress());
			user.setLoginCount(ud.getLoginCount());
			user.setModificationTime(ud.getModificationTime());
			user.setModifier(ud.getModifier());
			user.setOnlineStatus(ud.getOnlineStatus());
			user.setProperties(ud.getProperties());
			user.setTryLoginCount(ud.getTryLoginCount());
			user.setUserId(ud.getUserId());
		}
		return user;
	}
	
	protected Authentication buildNewAuthentication(Authentication currentAuth, UserDetails user, String newPassword) {
        //UserDetails user = loadUserByUsername(currentAuth.getName());

        UsernamePasswordAuthenticationToken newAuthentication =
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        newAuthentication.setDetails(currentAuth.getDetails());

        return newAuthentication;
    }
	
	
	
	
	
	public void createUser(UserDetails user) {
		AppsLicenseManager lm = Application.getContext().getLicenseManager();
		int allowedUserCount = lm.getAppsLicense().getAllowedUserCount();
		if(allowedUserCount != -1){
			int count = getUserDao().getCount();
			if(count >= allowedUserCount){
				String msg = "已经达到License许可的最大用户数， 无法创建新用户。";
				logger.warn(msg);
				throw new LicenseException(msg);
			}
		}else{
			logger.info("无限制用户数量");
		}
		
		//判断是否有重复的UserId
		if (user instanceof User) {
			User u = (User) user;
			UserDetails details = loadUserByUserId(u.getUserId());
			if (details != null) {
				throw new IllegalArgumentException("User id already exists: " + u.getUserId());
			}
		}
		
		validateUserDetails(user);
		
		
		/*
		//((User) user).setPassword(passwordEncoder.encodePassword(user.getPassword(), saltSource));
		Object salt = null;
		if(saltSource != null){
			salt = saltSource.getSalt(user);
		}
		String password = passwordEncoder.encodePassword(user.getPassword(), salt);
		*/
		
		
		

//		if(user instanceof User){
//			((User) user).setPassword(password);
//		}else{
//			List<GrantedAuthority> authorities = Arrays.asList(user.getAuthorities());
//			user = new User(user.getUsername(), password, user.isEnabled(), null, null, user.isAccountNonExpired(), null, authorities);
//		}
//		getUserDao().createUser((User) user);
		
		String encodedPassword = encodePassword(user, user.getPassword());
		LiveUser ut = buildLiveUser(user, encodedPassword);
		ut.setCreator(getLoginUsername());
		getUserDao().createUser(ut);
		
		//创建用户成功时，清理cache中该用户的记录
		if(notFoundUserCache.containsKey(ut.getUsername())){
			notFoundUserCache.remove(ut.getUsername());
		}
	}
	
	public void deleteGroup(Long groupId) {
		logger.debug("Deleting group '" + groupId + "'");
		Assert.notNull(groupId);
		getGroupDao().deleteGroup(groupId);
		
		groupCache.removeGroupFromCache(groupId);
//		groupCache.remove(groupId);
//		groupIdCache.clear();
//		groupMemberCache.clear();
//		groupAuthoritiesCache.remove(groupId);
	}
	

	public void deleteGroup(String groupName) {
		logger.debug("Deleting group '" + groupName + "'");
		Assert.hasText(groupName);
		getGroupDao().deleteGroup(groupName);
		groupCache.removeGroupFromCache(groupName);
//		groupCache.clear();
//		groupIdCache.remove(groupName);
//		groupMemberCache.clear();
//		groupAuthoritiesCache.clear();
	}

	
	
	public void deleteUser(String username) {
		getUserDao().remove(username);
		userCache.removeUserFromCache(username);
//		groupMemberCache.remove(username);
		groupCache.removeGroupIdsFromCache(username);
	}


	/**
	 * 
	 * @param user
	 * @return
	 */
	protected String encodePassword(UserDetails user, String originPassword){
		Object salt = null;
		if(saltSource != null){
			salt = saltSource.getSalt(user);
		}
		return passwordEncoder.encodePassword(originPassword, salt);
	}

	/**
	 * 返回所有用户组的名称。
	 */
	public String[] findAllGroups() {
		List<String> names = getGroupDao().findGroupNames();
		return names.toArray(new String[names.size()]);
	}
	
	public List<GrantedAuthority> findGroupAuthorities(Long groupId) {
        logger.debug("Loading authorities for group '" + groupId + "'");
        Assert.notNull(groupId);
        
        List<String> gas = groupCache.getGroupAuthoritiesFromCache(groupId);
        if(gas == null){
        	gas = getGroupDao().findGroupAuthorities(groupId);
//        	groupAuthoritiesCache.put(groupId, gas);
        	groupCache.putGroupAuthoritiesInCache(groupId, gas);
        }
        ///List<String> gas = getGroupDao().findGroupAuthorities(groupId);
        
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        //GrantedAuthority[] authorities = new GrantedAuthority[gas.size()];
        for(int i = 0 , n = gas.size() ; i < n ; i++){
        	String roleName = getRolePrefix() + gas.get(i);
        	GrantedAuthorityImpl authority = new GrantedAuthorityImpl(roleName);
        	authorities.add(authority);
        }

        return authorities;
        //return (GrantedAuthority[]) authorities.toArray(new GrantedAuthority[0]);
	}


	public GrantedAuthority[] findGroupAuthorities(String groupName) {
        logger.debug("Loading authorities for group '" + groupName + "'");
        Assert.hasText(groupName);
        
        Group group = getGroup(groupName);
        List<GrantedAuthority> list = findGroupAuthorities(group.getId());
        return list.toArray(new GrantedAuthority[list.size()]);
	}

	public GrantedAuthority[] findGroupAuthorities_BAK(String groupName) {
        logger.debug("Loading authorities for group '" + groupName + "'");
        Assert.hasText(groupName);
        
        List<String> gas = getGroupDao().findGroupAuthorities(groupName);
        GrantedAuthority[] authorities = new GrantedAuthority[gas.size()];
        for(int i = 0 , n = gas.size() ; i < n ; i++){
        	String roleName = getRolePrefix() + gas.get(i);
        	authorities[i] = new GrantedAuthorityImpl(roleName);
        }

        return authorities;
        //return (GrantedAuthority[]) authorities.toArray(new GrantedAuthority[0]);
	}

	public List<Group> findGroups() {
		//return getGroupDao().find();
		List<Long> ids = getGroupDao().findIds(ResultFilter.createEmptyResultFilter());
		return new GroupList(ids, this);
	}

	public List<Group> findGroupsOfUser(String username){
		//return getGroupDao().findGroupsByUsername(username);
		List<Long> ids = groupCache.getGroupIdsFromCache(username);
		if(ids == null){
			ids = getGroupDao().findGroupIdsByUsername(username);
			groupCache.putGroupIdsInCache(username, ids);
		}else{
			if(logger.isDebugEnabled()){
				logger.debug("Load group ids from cache: " + username);
			}
		}
		return new GroupList(ids, this);
	}

	public List<User> findUserDetailsInGroup(Long groupId){
		List<String> usernames = getGroupDao().findUsernamesInGroup(groupId);
		return new UserList(usernames, this);
	}

	/**
	 * User不包含权限信息。
	 * 
	 */
	public List<User> findUsersInGroup(Long groupId) {
		return getGroupDao().findUsersInGroup(groupId);
	}
	
	public String[] findUsersInGroup(String groupName) {
		List<String> users = getGroupDao().findUsernamesInGroup(groupName);
		return users.toArray(new String[users.size()]);
	}
	
	/**
	 * @return the authenticationManager
	 */
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public Group getGroup(Long id) {
		Group group = groupCache.getGroupFromCache(id);
		if(group == null){
			group = getGroupDao().get(id);
			if(group != null){
				groupCache.putGroupInCache(group);
			}
		}
		return group;
	}

	public Group getGroup(String groupName){
		Group group = groupCache.getGroupFromCache(groupName);
		if(group == null){
			group = getGroupDao().getGroupByName(groupName);
			if(group != null){
				groupCache.putGroupInCache(group);
			}
		}
		return group;
	}

	private String getLoginUsername(){
		User user = UserHolder.getNullableUser();
		return user != null ? user.getUsername() : null;
	}

	/**
	 * @return the passwordEncoder
	 */
	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}
	
	/**
	 * @return the saltSource
	 */
	public SaltSource getSaltSource() {
		return saltSource;
	}

	public UserDetails loadUserByUserId(Long userId){
		String username = userCache.getUsernameFromCache(userId);
		if(username == null){
			username = getUserDao().getUsernameByUserId(userId);
		}
		//String username = getUserDao().getUsernameByUserId(userId);
		if(username == null){
			return null;
		}
		return loadUserByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		UsernameNotFoundException unfe = notFoundUserCache.get(username);
		if(unfe != null){
			throw unfe; 
		}
		
		UserDetails user = userCache.getUserFromCache(username);
		if(user == null){
			try {
				user = super.loadUserByUsername(username);
				userCache.putUserInCache(user);
				logger.debug("loadUserByUsername from database: " + username);
			} catch (UsernameNotFoundException e) {
				notFoundUserCache.put(username, e);
				throw e;
			}
		}else{
			logger.debug("loadUserByUsername from cache: " + username);
		}
		
		return user;
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.UserManager#loginFailure(org.springframework.security.userdetails.UserDetails)
	 */
	public void loginFailure(String username) {
		boolean userExists = false;
		if(!(userCache instanceof NullUserCache)){
			userExists = userCache.getUserFromCache(username) != null;
		}else{
			userExists = userExists(username);
		}
		if(userExists){
			getUserDao().loginFailure(username);
			userCache.removeUserFromCache(username);
		}
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.UserManager#loginSuccess(org.springframework.security.userdetails.UserDetails)
	 */
	public void loginSuccess(UserDetails user) {
		LiveUser ut = new LiveUser(user.getUsername());
		if(user instanceof org.opoo.apps.security.User){
			org.opoo.apps.security.User ud = (org.opoo.apps.security.User) user;
			ut.setLoginAddress(ud.getLoginAddress());
			if(logger.isDebugEnabled()){
				logger.debug(user.getUsername() + " 已登录次数：" + ud.getLoginCount());
			}
		}
		
//		
//		u.setLoginCount(u.getLoginCount() + 1);
//		u.setLastLoginTime(new Date());
//		u.setTryLoginCount(0);
//		
//		//必须设置loginAddress？
//		//u.setLoginAddress(loginAddress);
//		u.setOnlineStatus(OnlineStatus.ONLINE);
		getUserDao().loginSuccess(ut);
		//userCache.putUserInCache(user);
		userCache.removeUserFromCache(user.getUsername());
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.security.UserManager#logout(org.springframework.security.userdetails.UserDetails)
	 */
	public void logout(UserDetails user) {
		LiveUser ut = new LiveUser(user.getUsername());
		getUserDao().logout(ut);
		userCache.removeUserFromCache(ut.getUsername());
	}
	
	
	public void removeGroupAuthority(Long groupId, GrantedAuthority authority) {
        logger.debug("Removing authority '" + authority + "' from group '" + groupId + "'");
        Assert.notNull(groupId);
        Assert.notNull(authority);

        getGroupDao().removeGroupAuthority(groupId, authority.getAuthority());
//		groupAuthoritiesCache.remove(groupId);
        groupCache.removeGroupAuthoritiesFromCache(groupId);
	}
	

//	/**
//	 * @return the userCache
//	 */
//	public AppsUserCache getUserCache() {
//		return userCache;
//	}

	public void removeGroupAuthority(String groupName,	GrantedAuthority authority) {
        logger.debug("Removing authority '" + authority + "' from group '" + groupName + "'");
        Assert.hasText(groupName);
        Assert.notNull(authority);

        getGroupDao().removeGroupAuthority(groupName, authority.getAuthority());
//        groupAuthoritiesCache.clear();
        groupCache.clearGroupAuthoritiesCache();
	}

    public void removeUserFromGroup(String username, Long groupId) {
        logger.debug("Removing user '" + username + "' to group '" + groupId + "'");
        Assert.hasText(username);
        Assert.notNull(groupId);
        
        getGroupDao().removeUserFromGroup(username, groupId);
        //userCache.removeUserFromCache(username);
//        groupMemberCache.remove(username);
        groupCache.removeGroupIdsFromCache(username);
	}

    public void removeUserFromGroup(String username, String groupName) {
        logger.debug("Removing user '" + username + "' to group '" + groupName + "'");
        Assert.hasText(username);
        Assert.hasText(groupName);
        
        getGroupDao().removeUserFromGroup(username, groupName);
        //userCache.removeUserFromCache(username);
//        groupMemberCache.clear();
        groupCache.clearGroupIdsCache();
	}

	public void renameGroup(Long id, String newName) {
        logger.debug("Changing group name from '" + id + "' to '" + newName + "'");
        Assert.notNull(id);
        Assert.hasText(newName);
		if(getGroup(newName) != null){
			throw new IllegalArgumentException("new group name already exists: " + newName);
		}
        getGroupDao().renameGroup(id, newName);
//        groupCache.remove(id);
//        groupIdCache.clear();
        groupCache.removeGroupFromCache(id);
	}

	public void renameGroup(String oldName, String newName) {
        logger.debug("Changing group name from '" + oldName + "' to '" + newName + "'");
        Assert.hasText(oldName);
        Assert.hasText(newName);
        if(getGroup(newName) != null){
			throw new IllegalArgumentException("new group name already exists: " + newName);
		}
        
        getGroupDao().renameGroup(oldName, newName);
//        groupCache.clear();
//        groupIdCache.remove(oldName);
        groupCache.removeGroupFromCache(oldName);
	}

	/**
	 * @param authenticationManager the authenticationManager to set
	 */
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/**
	 * @param passwordEncoder the passwordEncoder to set
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * @param saltSource the saltSource to set
	 */
	public void setSaltSource(SaltSource saltSource) {
		this.saltSource = saltSource;
	}

	/**
	 * @param userCache the userCache to set
	 */
	public void setUserCache(AppsUserCache userCache) {
		this.userCache = userCache;
	}

	public Group updateGroupProperties(Long id, Map<String, String> properties){
		Assert.notNull(id, "用户组ID不能为空。");
		if(properties == null){
			properties = new HashMap<String, String>();
		}
		
		Group g = getGroup(id);
		g.setProperties(properties);
		
		groupCache.removeGroupFromCache(id);
		return getGroupDao().update(g);
	}

	/**
	 * 不修改权限、属性、密码。
	 */
	public void updateLiveUser(LiveUser user){
		//validateUserDetails(user);
		Assert.hasText(user.getUsername(), "Username may not be empty or null");
		
		
		//取被修改用户
		UserDetails details = loadUserByUsername(user.getUsername());
		//密码不能被修改
		LiveUser ut = buildLiveUser(user, details.getPassword());
		ut.setModifier(getLoginUsername());
		getUserDao().update(ut);
		userCache.removeUserFromCache(user.getUsername());
	}

	public UserDetails updatePassword(String username, String newPassword){
		
		UserDetails user = loadUserByUsername(username);
		
		/*
		Object salt = null;
        if (saltSource != null) {
            salt = saltSource.getSalt(user);
        }
		newPassword = passwordEncoder.encodePassword(newPassword, salt);
		*/
		newPassword = encodePassword(user, newPassword);
		
		getUserDao().updatePassword(username, newPassword, getLoginUsername());
		
		userCache.removeUserFromCache(username);
		
		return user;
	}

	public void updateUser(UserDetails user) {
		validateUserDetails(user);
		//取被修改用户
		UserDetails details = loadUserByUsername(user.getUsername());
		//密码不能被修改
		LiveUser ut = buildLiveUser(user, details.getPassword());
		ut.setModifier(getLoginUsername());
		getUserDao().updateUser(ut);
		userCache.removeUserFromCache(user.getUsername());
	}

	/**
	 * 是应用数据库当前数据判断，不使用缓存，保证准确性。
	 */
	public boolean userExists(String username) {
		return getUserDao().isUserExists(username);
	}
	
	private void validateAuthorities(GrantedAuthority[] authorities) {
        Assert.notNull(authorities, "Authorities list must not be null");

        for (int i=0; i < authorities.length; i++) {
            Assert.notNull(authorities[i], "Authorities list contains a null entry");
            Assert.hasText(authorities[i].getAuthority(), "getAuthority() method must return a non-empty string");
        }
    }
	
	private void validateUserDetails(UserDetails user) {
        Assert.hasText(user.getUsername(), "Username may not be empty or null");
        validateAuthorities(user.getAuthorities());
    }

	public void setGroupCache(AppsGroupCache groupCache) {
		this.groupCache = groupCache;
	}
}
