package org.opoo.apps.security;

import java.util.List;
import java.util.Map;

import org.springframework.security.GrantedAuthority;


/**
 * �û������
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface GroupManager extends org.springframework.security.userdetails.GroupManager {
	/**
	 * ��ѯȫ���顣
	 * @return
	 */
	List<Group> findGroups();
	
	/**
	 * �����û��������顣
	 * 
	 * @param username
	 * @return
	 */
	List<Group> findGroupsOfUser(String username);
	
	
	//List<UserDetails> findUsersInGroup(Long groupId);
	
	/**
	 * ɾ���û��顣
	 */
	void deleteGroup(Long groupId);
	
	/**
	 * �����û��顣
	 * @param id
	 * @return
	 */
	Group getGroup(Long id);
	
	/**
	 * �����顣
	 * 
	 * @param id
	 * @param newName
	 */
	void renameGroup(Long id, String newName);

	/**
	 * ��ָ��������û���
	 * 
	 * @param username
	 * @param groupId
	 */
    void addUserToGroup(String username, Long groupId);

    /**
     * ��ָ�������Ƴ�ָ�����û���
     * 
     * @param username
     * @param groupId
     */
    void removeUserFromGroup(String username, Long groupId);
    
    /**
     * ��ѯ��Ȩ�ޡ�
     * 
     * @param groupId
     * @return
     */
    List<GrantedAuthority> findGroupAuthorities(Long groupId);

    /**
     * ��ָ��������Ȩ�ޡ�
     * 
     * @param groupId
     * @param authority
     */
    void addGroupAuthority(Long groupId, GrantedAuthority authority);


    /**
     * �Ƴ���Ȩ�ޡ�
     * 
     * @param groupId
     * @param authority
     */
    void removeGroupAuthority(Long groupId, GrantedAuthority authority);
    
    
    /**
	 * ���������û�������ơ�
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
