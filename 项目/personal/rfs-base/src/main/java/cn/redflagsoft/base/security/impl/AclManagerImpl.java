/*
 * $Id: AclManagerImpl.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opoo.apps.security.Group;
import org.opoo.apps.security.User;

import cn.redflagsoft.base.security.Ace;
import cn.redflagsoft.base.security.AceManager;
import cn.redflagsoft.base.security.AclManager;
import cn.redflagsoft.base.security.PermissionType;
import cn.redflagsoft.base.security.Permissions;
import cn.redflagsoft.base.security.PermissionsManager;


/**
 * 
 *
 */
public class AclManagerImpl implements AclManager {
	private AceManager aceManager;
	private PermissionsManager permissionsManager;
	
	
	public AceManager getAceManager() {
		return aceManager;
	}
	public void setAceManager(AceManager aceManager) {
		this.aceManager = aceManager;
	}
	public PermissionsManager getPermissionsManager() {
		return permissionsManager;
	}
	public void setPermissionsManager(PermissionsManager permissionsManager) {
		this.permissionsManager = permissionsManager;
	}

	
	public List<Ace> getUserAcl(User user, PermissionType permissionType) {
		long userId = user.getUserId();
		Set<Ace> set = aceManager.getAcl();
		List<Ace> list = new ArrayList<Ace>();
		for(Ace ace: set){
			Permissions permissions = permissionsManager.getFinalUserPerms(userId, ace.getId(), permissionType);
			for(Ace.Entry en: ace.getEntrySet()){
				if(permissions.hasPermission(en.getPermission())){
					en.setChecked(true);
				}else{
					en.setChecked(false);
				}
			}
			list.add(ace);
		}
		return list;
	}

	public List<Ace> getGroupAcl(Group group, PermissionType permissionType) {
		long groupId = group.getId();
		Set<Ace> set = aceManager.getAcl();
		List<Ace> list = new ArrayList<Ace>();
		for(Ace ace: set){
			Permissions permissions = permissionsManager.getFinalGroupPerms(groupId, ace.getId(), permissionType);
			for(Ace.Entry en: ace.getEntrySet()){
				if(permissions.hasPermission(en.getPermission())){
					en.setChecked(true);
				}else{
					en.setChecked(false);
				}
			}
			list.add(ace);
		}
		return list;
	}

	public Map<String, Long> getUserPermissions(User user) {
		Map<String,Long> perms = new LinkedHashMap<String,Long>();
//		long userId = user.getUserId();
//		Set<Ace> set = aceManager.getAcl();
//		for(Ace ace: set){
//			String aceId = ace.getId();
//			Permissions permissions = permissionsManager.getPermissions(userId, aceId);
//			long permissionsValue = permissions.value();
//			if(permissionsValue > 0){
//				perms.put(aceId, permissionsValue);
//			}
//		}
//		return perms;
		
		Map<String, Permissions> map = permissionsManager.getPermissions(user);
		for(Map.Entry<String, Permissions> en: map.entrySet()){
			perms.put(en.getKey(), en.getValue().value());
		}
		return perms;
	}
}
