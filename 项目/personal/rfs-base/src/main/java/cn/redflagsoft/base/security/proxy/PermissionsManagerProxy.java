/*
 * $Id: PermissionsManagerProxy.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security.proxy;

import java.util.Map;

import org.opoo.apps.AppsGlobals;
import org.opoo.apps.security.Group;
import org.opoo.apps.security.User;

import cn.redflagsoft.base.security.PermissionType;
import cn.redflagsoft.base.security.Permissions;
import cn.redflagsoft.base.security.PermissionsManager;

public class PermissionsManagerProxy implements PermissionsManager{
	
	private PermissionsManager permissionsManagerBasedOnSidCache;
	private PermissionsManager permissionsManagerBasedOnAceCache;
	
	public void setPermissionsManagerBasedOnSidCache(PermissionsManager permissionsManagerBasedOnSidCache) {
		this.permissionsManagerBasedOnSidCache = permissionsManagerBasedOnSidCache;
	}

	public void setPermissionsManagerBasedOnAceCache(PermissionsManager permissionsManagerBasedOnAceCache) {
		this.permissionsManagerBasedOnAceCache = permissionsManagerBasedOnAceCache;
	}
	
	public PermissionsManager getAvailablePermissionsManager(){
		String baseOnCache = AppsGlobals.getProperty("PermissionsManager.baseOnCache", "sid");
		if("sid".equalsIgnoreCase(baseOnCache)){
			return permissionsManagerBasedOnSidCache;
		}else{
			return permissionsManagerBasedOnAceCache;
		}
	}
	
	public void addUserPermission(User user, String aceId, PermissionType permissionType, long permission) {
		getAvailablePermissionsManager().addUserPermission(user, aceId, permissionType, permission);
	}

	public void removeUserPermission(User user, String aceId, PermissionType permissionType, long permission) {
		getAvailablePermissionsManager().removeUserPermission(user, aceId, permissionType, permission);
	}

	public void removeAllUserPermissions(String aceId, PermissionType permissionType) {
		getAvailablePermissionsManager().removeAllUserPermissions(aceId, permissionType);
	}

	public void removeAllUserPermissions(long userId, PermissionType permissionType) {
		getAvailablePermissionsManager().removeAllUserPermissions(userId, permissionType);
	}

	public void addGroupPermission(Group group, String aceId, PermissionType permissionType, long permission) {
		getAvailablePermissionsManager().addGroupPermission(group, aceId, permissionType, permission);
	}

	public void removeGroupPermission(Group group, String aceId, PermissionType permissionType, long permission) {
		getAvailablePermissionsManager().removeGroupPermission(group, aceId, permissionType, permission);
	}

	public void removeAllGroupPermissions(String aceId, PermissionType permissionType) {
		getAvailablePermissionsManager().removeAllGroupPermissions(aceId, permissionType);
	}

	public void removeAllGroupPermissions(long groupId, PermissionType permissionType) {
		getAvailablePermissionsManager().removeAllGroupPermissions(groupId, permissionType);		
	}

	public Permissions getFinalUserPerms(long userId, String aceId, PermissionType permissionType) {
		return getAvailablePermissionsManager().getFinalUserPerms(userId, aceId, permissionType);
	}

	public Permissions getFinalGroupPerms(long groupId, String aceId, PermissionType permissionType) {
		return getAvailablePermissionsManager().getFinalGroupPerms(groupId, aceId, permissionType);
	}

	public Permissions getPermissions(long userId, String aceId) {
		return getAvailablePermissionsManager().getPermissions(userId, aceId);
	}

	public Map<String, Permissions> getPermissions(User user) {
		return getAvailablePermissionsManager().getPermissions(user);
	}
}
