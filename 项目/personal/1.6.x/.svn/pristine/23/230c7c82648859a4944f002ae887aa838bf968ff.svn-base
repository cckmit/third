package org.opoo.apps.security;

import java.util.List;
import java.util.Map;

import org.springframework.security.GrantedAuthority;


/**
 * 用户组管理。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface GroupManager extends org.springframework.security.userdetails.GroupManager {
	/**
	 * 查询全部组。
	 * @return
	 */
	List<Group> findGroups();
	
	/**
	 * 查找用户所属的组。
	 * 
	 * @param username
	 * @return
	 */
	List<Group> findGroupsOfUser(String username);
	
	
	//List<UserDetails> findUsersInGroup(Long groupId);
	
	/**
	 * 删除用户组。
	 */
	void deleteGroup(Long groupId);
	
	/**
	 * 查找用户组。
	 * @param id
	 * @return
	 */
	Group getGroup(Long id);
	
	/**
	 * 更名组。
	 * 
	 * @param id
	 * @param newName
	 */
	void renameGroup(Long id, String newName);

	/**
	 * 向指定组添加用户。
	 * 
	 * @param username
	 * @param groupId
	 */
    void addUserToGroup(String username, Long groupId);

    /**
     * 从指定组中移除指定的用户。
     * 
     * @param username
     * @param groupId
     */
    void removeUserFromGroup(String username, Long groupId);
    
    /**
     * 查询组权限。
     * 
     * @param groupId
     * @return
     */
    List<GrantedAuthority> findGroupAuthorities(Long groupId);

    /**
     * 向指定组增加权限。
     * 
     * @param groupId
     * @param authority
     */
    void addGroupAuthority(Long groupId, GrantedAuthority authority);


    /**
     * 移除组权限。
     * 
     * @param groupId
     * @param authority
     */
    void removeGroupAuthority(Long groupId, GrantedAuthority authority);
    
    
    /**
	 * 返回所有用户组的名称。
	 */
	String[] findAllGroups();
	
	String[] findUsersInGroup(String groupName);

	void addGroupAuthority(String groupName, GrantedAuthority authority);

	void addUserToGroup(String username, String groupName);

	void deleteGroup(String groupName);

	GrantedAuthority[] findGroupAuthorities(String groupName);

	void removeGroupAuthority(String groupName,	GrantedAuthority authority);
	
	void removeUserFromGroup(String username, String groupName);

	void renameGroup(String oldName, String newName);

	List<User> findUsersInGroup(Long groupId);

	void createGroup(String groupName, List<GrantedAuthority> authorities);

	void createGroup(String groupName, GrantedAuthority[] authorities);
	
	/**
	 * 
	 * @param id
	 * @param properties
	 * @return
	 */
	Group updateGroupProperties(Long id, Map<String, String> properties);
}
