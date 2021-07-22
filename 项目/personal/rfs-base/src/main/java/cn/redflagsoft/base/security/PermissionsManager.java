/*
 * $Id: PermissionsManager.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.security;

import java.util.Map;

import org.opoo.apps.security.Group;
import org.opoo.apps.security.User;


public interface PermissionsManager {

	/**
	 * Add a permission of the specified PermissionType for the specified user.
	 * Only community or system administrators can assign permissions to a
	 * community, and only system admins can assign system level and community
	 * administration permissions. If this permissions context is at the system
	 * level, only system admins can call this method.
	 * 
	 * @param user  the User to add a permission to.
	 * @param aceId the identifier of access control entry.
	 * @param permissionType  the type of permission to add.
	 * @param permission  the permission to add.
	 */
	void addUserPermission(User user, String aceId, PermissionType permissionType, long permission);

	/**
	 * 
	 * @param aceId
	 * @param permissionType
	 * @param permission
	 */
	//void addAllUserPermission(String aceId, PermissionType permissionType, long permission);

	/**
	 * Removes a permission of the specified PermissionType for the specified
	 * user. Only community or system administrators can assign permissions to a
	 * community, and only system admins can assign community administration
	 * permissions. If this permissions context is at the system level, only
	 * system admins can call this method.
	 * 
	 * @param user the User to remove a permission from.
	 * @param aceId the identifier of access control entry.
	 * @param permissionType the type of permission to remove.
	 * @param permission the permission to remove
	 */
	void removeUserPermission(User user, String aceId, PermissionType permissionType, long permission);

	/**
	 * 
	 * @param aceId the identifier of access control entry.
	 * @param permissionType
	 */
	void removeAllUserPermissions(String aceId, PermissionType permissionType);
	
	/**
	 * 
	 * @param userId
	 * @param permissionType
	 */
	void removeAllUserPermissions(long userId, PermissionType permissionType);

	/**
	 * Grants a group a particular permission. Only community or system
	 * administrators can assign permissions to a community, and only system
	 * admins can assign community administration permissions. If this
	 * permissions context is at the system level, only system admins can call
	 * this method.
	 * 
	 * @param groupId the group to grant a permission to.
	 * @param aceId   the identifier of access control entry.
	 * @param permissionType  the type of permission to add.
	 * @param permission the permission to grant the group.
	 */
	void addGroupPermission(Group group, String aceId, PermissionType permissionType, long permission);

	
	/**
	 * 
	 * @param aceId
	 * @param permissionType
	 * @param permission
	 */
	//void addAllGroupPermission(String aceId, PermissionType permissionType, long permission);
	
	
	void removeGroupPermission(Group group, String aceId, PermissionType permissionType, long permission);
	
	/**
	 * Revokes all group permissions. Only system admins can call this method.
	 * 
	 * @param aceId   the identifier of access control entry.
	 * @param permissionType the type of permissions to remove.
	 */
	void removeAllGroupPermissions(String aceId, PermissionType permissionType);
	
	/**
	 * 
	 * @param groupId
	 * @param permissionType
	 */
	void removeAllGroupPermissions(long groupId, PermissionType permissionType);

	/**
	 * Returns the Permissions object that corresponds to the combined
     * permissions that a user has for a particular object:
     * <ul>
     *      <li> Permissions for groups that the user belongs to.
     *      <li> Specific permissions granted to the user.
     * </ul>
     * 
	 * @param userId
	 * @param aceId
	 * @param permissionType
	 * @return
	 */
	Permissions getFinalUserPerms(long userId, String aceId, PermissionType permissionType);

	/**
	 * Returns the Permissions object that corresponds to the
     * permissions that a group has for a particular object.
	 * 
	 * @param groupId
	 * @param aceId
	 * @param permissionType
	 * @return
	 */
	Permissions getFinalGroupPerms(long groupId, String aceId, PermissionType permissionType);

	/**
	 * Returns permissions for a given user on a given ace.
     * This is really a convenience to the code below which is common across
     * several usages.
     * 
	 * @param userId
	 * @param aceId
	 * @return
	 */
	Permissions getPermissions(long userId, String aceId);
	
	/**
	 * ��ȡָ���û���Ȩ���б�
	 * 
	 * @param user
	 * @return
	 */
	Map<String,Permissions> getPermissions(User user);
}
