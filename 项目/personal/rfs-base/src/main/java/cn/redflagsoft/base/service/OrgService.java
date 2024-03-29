/*
 * $Id: OrgService.java 6314 2013-11-06 09:15:38Z lf $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.apps.security.Group;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Org;

/**
 * 
 * @author ymq
 * @author Alex
 */
public interface OrgService {
	
	Org getOrg(Long id);
	
	Org getOrgByDutyEntity(Long dutyEntity);
	
	int deleteOrg(Org org);
	
	Org updateOrg(Org org);
	
	Org saveOrg(Org org);
	
	int deleteOrgs(List<Long> ids);
	
	List<Org> findOrgs();
	
	List<Org> findOrgs(ResultFilter filter);
	
	/**
	 * 查询指定部门的角色。
	 * 
	 * @param orgId
	 * @return
	 */
	List<Group> findRolesByOrgId(Long orgId);
	
	/**
	 * 重设指定部门的 角色。
	 * 该方法将删除当前部门的角色，并重新添加。
	 * 
	 * @param orgId
	 * @param roleIds
	 * @return
	 */
	List<Group> setOrgRoles(Long orgId, List<Long> roleIds);
	
	/**
	 * 向指定部门添加多个角色。
	 * 
	 * @param orgId
	 * @param roleIds
	 */
	void addOrgRoles(Long orgId, List<Long> roleIds);
	
	/**
	 * 向指定部门增加一个角色。
	 * 如果角色已经存在，则抛出异常。
	 * 
	 * @param orgId
	 * @param roleId
	 */
	void addOrgRole(Long orgId, Long roleId);
	
	/**
	 * 删除指定部门的指定角色。
	 * 
	 * @param orgId
	 * @param roleId
	 */
	void removeOrgRole(Long orgId, Long roleId);
	
	/**
	 * 查询直属下级单位数量（不包含直属下级的下级单位）。
	 * @param orgId
	 * @return
	 */
	int getSuborgsCount(Long orgId);
	
	/**
	 * 查询直属下级单位列表（不包含直属下级的下级单位）。
	 * @param orgId
	 * @return
	 */
	List<Org> findSuborgs(Long orgId);

	/**
	 * 查询直属下级单位ID列表（不包含直属下级的下级单位）。
	 * @param orgId
	 * @return
	 */
	List<Long> findSuborgIds(Long orgId);
	
	int removeOrgRole(Long orgId);
}
