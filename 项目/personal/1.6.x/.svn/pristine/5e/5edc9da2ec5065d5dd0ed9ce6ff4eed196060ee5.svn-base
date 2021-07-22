package org.opoo.apps.security.dao;

import java.util.List;

import org.opoo.apps.security.bean.UserAuthority;
import org.opoo.apps.security.bean.LiveUser;
import org.opoo.ndao.Dao;

public interface UserDao extends Dao<LiveUser, String> {
	/**
	 * 获取指定用户的用户权限。
	 * 
	 * @param username
	 * @return
	 */
	List<UserAuthority> findUserAuthoritiesByUsername(String username);
	/**
	 * 检查用户是否存在。
	 * 
	 * @param username
	 * @return
	 */
	boolean isUserExists(String username);
	/**
	 * 删除用户。
	 * 
	 * @param username
	 */
	void deleteUser(String username);
	/**
	 * 创建新用户。
	 * @param user
	 */
	void createUser(LiveUser user);
	/**
	 * 更新用户信息。
	 * 包括基本信息、权限、密码、属性。
	 * 
	 * @param user
	 */
	void updateUser(LiveUser user);
//	/**
//	 * 更新用户基本信息。
//	 * 
//	 * @param user
//	 */
//	void updateBaseInfo(UserTemplate user);
	/**
	 * 更新用户密码。
	 * 
	 * @param username
	 * @param password
	 * @param modifier
	 */
	void updatePassword(String username, String password, String modifier);
	
	
	/////////////////////////////////////////////////////////////////////////
	//
	/////////////////////////////////////////////////////////////////////////
	
	void loginSuccess(LiveUser user);
	
	void loginFailure(String username);
	
	void logout(LiveUser user);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	String getUsernameByUserId(Long userId);
}
