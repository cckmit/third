package org.opoo.apps.security.dao;

import java.util.List;
import java.util.Map;

import org.opoo.apps.security.Group;
import org.opoo.apps.security.User;
import org.opoo.apps.security.bean.GroupAuthority;
import org.opoo.ndao.Dao;

/**
 * 用户组dao。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface GroupDao extends Dao<Group, Long> {
	/**
	 * 根据用户名查询用户在所有用户组中的权限。
	 * 
	 * @param username 用户名
	 * @return 组权限对象集合
	 */
	List<GroupAuthority> findGroupAuthoritiesByUsername(String username);
	
	/**
	 * 查询全部组。
	 * 
	 * @return 组对象集合
	 */
	List<Group> findGroups();
	
	/**
	 * 查找所有用户组的组名。
	 * 
	 * @return 组名称集合
	 */
	List<String> findGroupNames();
	
	/**
	 * 删除组。
	 */
	void deleteGroup(Long groupId);
	
	/**
	 * 删除组。
	 * @param groupName 组名称
	 */
	void deleteGroup(String groupName);
	
	/**
	 * 查找指定组。
	 * 
	 * @param id 组id
	 * @return null if not exists
	 */
	Group getGroup(Long id);
	
	/**
	 * 修改指定组的组名称。
	 * 如果组不存在，则退出。
	 * 
	 * @param id 组id
	 * @param newName 新名称
	 */
	void renameGroup(Long id, String newName);
	
	/**
	 * 修改指定组的组名。
	 * 
	 * @param oldName 旧名称
	 * @param newName 新名称
	 */
	void renameGroup(String oldName, String newName);

	/**
	 * 向指定组中添加用户。
	 * 
	 * @param username 用户名
	 * @param groupId 组id
	 */
    void addUserToGroup(String username, Long groupId);

    /**
     * 从指定组中删除指定成员。
     * 
     * @param username 用户名
     * @param groupId 组id
     */
    void removeUserFromGroup(String username, Long groupId);
    
    /**
     * 从指定组中删除指定成员。
     * 
     * @param username 用户名
     * @param groupName 组名称
     */
    void removeUserFromGroup(String username, String groupName);
    /**
     * 查找指定组的组权限。
     * 
     * @param groupId 组id
     * @return 组权限集合
     */
    List<String> findGroupAuthorities(Long groupId);
    
    /**
     * 查找指定组的组权限。
     * 
     * @param groupName 组名称
     * @return 组权限集合
     */
    List<String> findGroupAuthorities(String groupName);

    /**
     * 向指定组中添加权限。
     * 
     * @param groupId 组id
     * @param authority 权限
     */
    void addGroupAuthority(Long groupId, String authority);


    /**
     * 向指定组中添加权限。
     * 
     * @param groupName 组名称
     * @param authority 权限
     */
    void addGroupAuthority(String groupName, String authority);
    /**
     * 删除指定组中指定的权限。
     * 
     * @param groupId 组id
     * @param authority 权限
     */
    void removeGroupAuthority(Long groupId, String authority);
    
    /**
     *  删除指定组中指定的权限。
     * @param groupName 组名称
     * @param authority 权限
     */
    void removeGroupAuthority(String groupName,	String authority);
    
    /**
     * 查找指定组中所有成员。
     * 
     * @param groupName 组名称
     * @return 成员用户名集合
     */
    List<String> findUsernamesInGroup(String groupName);
    
    /**
     * 
     * @param groupId
     * @return
     */
    List<String> findUsernamesInGroup(Long groupId);
    
    /**
     * 创建组
     * 
     * @param group 组对象
     * @param authorities 权限集合
     */
    void createGroup(Group group, String[] authorities);

    /**
     * 向指定组中添加用户。
     * 
     * @param username 用户名
     * @param groupName 组名称
     */
	void addUserToGroup(String username, String groupName);
	
	/**
	 * 查询组成员对象集合。
	 * 
	 * @param groupId
	 * @return
	 */
	List<User> findUsersInGroup(Long groupId);

	/**
	 * 
	 * @param username
	 * @return
	 */
	Map<String, String> findGroupPropertiesByUsername(String username);

	/**
	 * 
	 * @param username
	 * @return
	 */
	List<Group> findGroupsByUsername(String username);
	

	Group getGroupByName(String groupName);

	List<Long> findGroupIdsByUsername(String username);
}
