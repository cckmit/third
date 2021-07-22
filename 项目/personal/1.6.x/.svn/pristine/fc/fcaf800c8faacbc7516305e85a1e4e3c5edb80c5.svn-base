package org.opoo.apps.security;

import org.opoo.apps.security.bean.LiveUser;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsManager;
import org.springframework.security.userdetails.UsernameNotFoundException;

/**
 * 用户管理。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface UserManager extends UserService, UserDetailsManager {

	/**
	 * 修改指定用户的密码，一般是系统管理员的功能。
	 * 不需要输入原密码，直接修改。
	 * 
	 * @param username
	 * @param newPassword
	 * @return
	 */
	UserDetails updatePassword(String username, String newPassword);
	
	/**
	 * 修改当前用户的密码。
	 */
	void changePassword(String oldPassword, String newPassword);
	
	/**
	 * 创建用户。
	 */
	void createUser(UserDetails user);
	
	/**
	 * 更新用户信息。
	 * 不修改密码。修改权限。
	 */
	void updateUser(UserDetails user);
	
	/**
	 * 删除指定用户。
	 */
	void deleteUser(String username);
	
	/**
	 * 用户是否存在？
	 */
	boolean userExists(String username);

	/**
	 * 根据用户名加载用户。
	 */
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException;
	
	/**
	 * 
	 * @param userId
	 * @return null if userId not exists
	 */
	UserDetails loadUserByUserId(Long userId);
	
	/////////////////////////////////////////
	//
	////////////////////////////////////////
	
	
	/**
	 * 更新用户信息，不包含权限、密码信息。
	 * 
	 * @param user
	 */
	void updateLiveUser(LiveUser user);
	
	/**
	 * 
	 * @param user
	 */
	void loginSuccess(UserDetails user);
	
	/**
	 * 
	 * @param user
	 */
	void loginFailure(String username);
	
	/**
	 * 
	 * @param user
	 */
	void logout(UserDetails user);
}
