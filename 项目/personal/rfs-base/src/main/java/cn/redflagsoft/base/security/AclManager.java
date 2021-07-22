/*
 * $Id: AclManager.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;

import java.util.List;
import java.util.Map;

import org.opoo.apps.security.Group;
import org.opoo.apps.security.User;

/**
 * ACL服务。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface AclManager {

	/**
	 * 查找指定用户的访问列表。该列表以所有可分配的权限项为模板。
	 * 
	 * 
	 * @param user 用户
	 * @param permissionType 授权类型
	 * @return
	 */
	List<Ace> getUserAcl(User user, PermissionType permissionType);
	
	/**
	 * 查找指定组的访问列表。该列表以所有可分配的权限项为模板。
	 * 
	 * @param group 用户组
	 * @param permissionType 授权类型
	 * @return
	 */
	List<Ace> getGroupAcl(Group group, PermissionType permissionType);
	
	/**
	 * 查询用户最终的可以访问的授权项ID和权限值。
	 * 只查询已分配的权限，未分配的不出现在列表中，即该查询不以可分配权限为模板。
	 * 
	 * @param user
	 * @return map结构，其中key为授权项ID，Value为权限值
	 */
	Map<String, Long> getUserPermissions(User user);
}
