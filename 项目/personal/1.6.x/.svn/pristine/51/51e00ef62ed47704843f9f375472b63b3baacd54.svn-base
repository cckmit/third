package org.opoo.apps.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.opoo.apps.security.bean.GroupAuthority;
import org.opoo.apps.security.bean.LiveUser;
import org.opoo.apps.security.bean.UserAuthority;
import org.opoo.apps.security.dao.GroupDao;
import org.opoo.apps.security.dao.UserDao;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

public class DaoUserService implements UserService, UserDetailsService {
	private UserDao userDao;
	private GroupDao groupDao;
    private boolean enableAuthorities = true;
    private boolean enableGroups;
    private boolean enableProperties = true;
    private String rolePrefix = "";
    private boolean usernameBasedPrimaryKey = true;
    
    /**
     * 根据用户名加载用户。
     * 
     * 
     * @param username 用户名
     * @return 用户对象
     */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        //LiveUser user = loadLiveUserByUsername(username);//no group auths,props
        LiveUser user = userDao.get(username);
        if(user == null){
        	throw new UsernameNotFoundException("没有找到用户 " + username + "。");
        }
        
        Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();
        Map<String, String> propertiesMap = new TreeMap<String, String>();
        
        //用户的
        if (enableAuthorities) {
        	//GrantedAuthority[] uas = user.getAuthorities();
        	//uas = AuthorityUtils.addRolePrefix(uas, getRolePrefix());
        	//Collection<GrantedAuthority> uasCollection = Arrays.asList(uas);
            //dbAuthsSet.addAll(uasCollection);
        	dbAuthsSet.addAll(loadUserAuthorities(user.getUsername()));
        }
        
        if(enableProperties && user.getProperties() != null){
        	propertiesMap.putAll(user.getProperties());
        }

        //组的
        if (enableGroups) {
            dbAuthsSet.addAll(loadGroupAuthorities(user.getUsername()));
            if(enableProperties){
            	propertiesMap.putAll(loadGroupProperties(user.getUsername()));
            }
        }

        List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);

        addCustomAuthorities(user.getUsername(), dbAuths);

        if (dbAuths.size() == 0) {
            throw new UsernameNotFoundException("用户 " + username + " 没有授权！");
        }
        
        
        addCustomProperties(user.getUsername(), propertiesMap);
    
        //GrantedAuthority[] arrayAuths = (GrantedAuthority[]) dbAuths.toArray(new GrantedAuthority[dbAuths.size()]);

        return createUserDetails(username, user, dbAuths, propertiesMap);
	}
	


	/**
     * Allows subclasses to add their own granted authorities to the list to be returned in the
     * <code>User</code>.
     *
     * @param username the username, for use by finder methods
     * @param authorities the current granted authorities, as populated from the <code>authoritiesByUsername</code>
     *        mapping
     */
	protected void addCustomAuthorities(String username, List<GrantedAuthority> dbAuths) {		
	}
	
	
	/**
	 * Allows subclasses to add their own properties to the map to be returned in the
     * <code>User</code>.
     * 
	 * @param username
	 * @param properties
	 */
	protected void addCustomProperties(String username, Map<String, String> properties){
		
	}

	/**
	 * 加载用户所属组的权限。
	 * 
	 * @param username
	 * @return
	 */
	protected Collection<GrantedAuthority> loadGroupAuthorities(String username) {
		List<GroupAuthority> groupAuths = groupDao.findGroupAuthoritiesByUsername(username);
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(GroupAuthority ga: groupAuths){
			String roleName = rolePrefix + ga.getAuthority();
            GrantedAuthorityImpl authority = new GrantedAuthorityImpl(roleName);
            authorities.add(authority);
		}
		return authorities;
	}
	/**
	 * 加载用户权限。
	 * 
	 * 
	 * @param username
	 * @return
	 */
	protected Collection<GrantedAuthority> loadUserAuthorities(String username) {
		List<UserAuthority> userAuths = userDao.findUserAuthoritiesByUsername(username);
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(UserAuthority ua: userAuths){
			String roleName = rolePrefix + ua.getAuthority();
            GrantedAuthorityImpl authority = new GrantedAuthorityImpl(roleName);
            authorities.add(authority);
		}
		return authorities;
	}
	
	protected GrantedAuthority[] loadUserAuthoritiesNoRolePrefix(String username) {
		List<UserAuthority> userAuths = userDao.findUserAuthoritiesByUsername(username);
		GrantedAuthority[] authorities = new GrantedAuthority[userAuths.size()];
		for(int i = 0 ; i < userAuths.size() ; i++){
			UserAuthority ua = userAuths.get(i);
			String roleName = ua.getAuthority();
            GrantedAuthorityImpl authority = new GrantedAuthorityImpl(roleName);
            authorities[i] = authority;
		}
		return authorities;
	}

//	/**
//	 * 查找对象，查找出的用户对象不包含权限信息。
//	 * 
//	 * @param username
//	 * @return
//	 */
//	protected UserDetails loadUserDetailsByUsername(String username){
//		return userDao.get(username);
//	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public LiveUser loadLiveUserByUsername(String username){
		LiveUser ut = userDao.get(username);
		if(ut == null){
			return null;
		}
		if (enableAuthorities/* && ut != null*/) {
			ut.setAuthorities(loadUserAuthoritiesNoRolePrefix(username));
		}
		return new LiveUser(ut);
	}
	
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	protected Map<String, String> loadGroupProperties(String username) {
		return groupDao.findGroupPropertiesByUsername(username);
	}
	
	
	
	
	/**
	 * 创建用户对象。
	 * 
	 * @param username 用户名
	 * @param userFromDB 不包含权限的用户对象
	 * @param combinedAuthorities 权限
	 * @return
	 */
	protected UserDetails createUserDetails(String username, LiveUser ut, List<GrantedAuthority> combinedAuthorities, Map<String, String> combinedProperties) {
		String returnUsername = ut.getUsername();

		if (!usernameBasedPrimaryKey) {
			returnUsername = username;
		}
		
		GrantedAuthority[] array = new GrantedAuthority[combinedAuthorities.size()];
		array = combinedAuthorities.toArray(array);
		ut.setUsername(returnUsername);
		return new SecurityUser(ut, array, combinedProperties);
	}

	/**
	 * @return the userDao
	 */
	public UserDao getUserDao() {
		return userDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * @return the groupDao
	 */
	public GroupDao getGroupDao() {
		return groupDao;
	}

	/**
	 * @param groupDao the groupDao to set
	 */
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	/**
	 * @return the enableAuthorities
	 */
	public boolean isEnableAuthorities() {
		return enableAuthorities;
	}

	/**
	 * @param enableAuthorities the enableAuthorities to set
	 */
	public void setEnableAuthorities(boolean enableAuthorities) {
		this.enableAuthorities = enableAuthorities;
	}

	/**
	 * @return the enableGroups
	 */
	public boolean isEnableGroups() {
		return enableGroups;
	}

	/**
	 * @param enableGroups the enableGroups to set
	 */
	public void setEnableGroups(boolean enableGroups) {
		this.enableGroups = enableGroups;
	}

	/**
	 * @return the rolePrefix
	 */
	public String getRolePrefix() {
		return rolePrefix;
	}

	/**
	 * @param rolePrefix the rolePrefix to set
	 */
	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

	/**
	 * @return the usernameBasedPrimaryKey
	 */
	public boolean isUsernameBasedPrimaryKey() {
		return usernameBasedPrimaryKey;
	}

	/**
	 * @param usernameBasedPrimaryKey the usernameBasedPrimaryKey to set
	 */
	public void setUsernameBasedPrimaryKey(boolean usernameBasedPrimaryKey) {
		this.usernameBasedPrimaryKey = usernameBasedPrimaryKey;
	}

	/**
	 * @return the enableProperties
	 */
	public boolean isEnableProperties() {
		return enableProperties;
	}

	/**
	 * @param enableProperties the enableProperties to set
	 */
	public void setEnableProperties(boolean enableProperties) {
		this.enableProperties = enableProperties;
	}
}
