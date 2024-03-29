/*
 * $Id: GroupAclScheme.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.acl;

import java.util.ArrayList;
import java.util.List;

import org.opoo.apps.security.Group;
import org.opoo.apps.security.GroupManager;
import org.springframework.util.Assert;

import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.security.Ace;
import cn.redflagsoft.base.security.AclManager;
import cn.redflagsoft.base.security.PermissionType;
import cn.redflagsoft.base.security.PermissionsManager;

public class GroupAclScheme extends AbstractScheme{
	private AclManager aclManager;
	private GroupManager groupManager;
	private PermissionsManager permissionsManager;
	
	private Long groupId;
	private List<AcePerm> perms;
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public List<AcePerm> getPerms() {
		return perms;
	}
	public void setPerms(List<AcePerm> perms) {
		this.perms = perms;
	}
	
	public AclManager getAclManager() {
		return aclManager;
	}
	public void setAclManager(AclManager aclManager) {
		this.aclManager = aclManager;
	}
	public GroupManager getGroupManager() {
		return groupManager;
	}
	public void setGroupManager(GroupManager groupManager) {
		this.groupManager = groupManager;
	}
	public PermissionsManager getPermissionsManager() {
		return permissionsManager;
	}
	public void setPermissionsManager(PermissionsManager permissionsManager) {
		this.permissionsManager = permissionsManager;
	}
	public Object doSaveAcl(){
		Group group = getGroup();
		if(perms == null){
			perms = new ArrayList<AcePerm>();
		}
		//clear all group's permissions
		permissionsManager.removeAllGroupPermissions(groupId, PermissionType.ADDITIVE);
		
		//add new group's permissions
		int n = 0;
		for (AcePerm p : perms) {
			if(p.getPerms() > 0){
				permissionsManager.addGroupPermission(group, p.getAceId(), PermissionType.ADDITIVE, p.getPerms());
				n++;
			}
		}
		return "为角色‘" + group.getName() + "’设置了 " + n + " 个权限。";
	}
	
	
	public List<Ace> viewFindAcl(){
		return aclManager.getGroupAcl(getGroup(), PermissionType.ADDITIVE);
	}
	
	private Group getGroup(){
		Assert.notNull(groupId, "必须指定角色");
		
		Group group = groupManager.getGroup(groupId);
		if(group == null){
			throw new SchemeException("角色不存在");
		}
		return group;
	}
}
