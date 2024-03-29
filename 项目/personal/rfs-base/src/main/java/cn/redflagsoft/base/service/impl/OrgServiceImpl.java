/*
 * $Id: OrgServiceImpl.java 6314 2013-11-06 09:15:38Z lf $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.Labelable;
import org.opoo.apps.annotation.Queryable;
import org.opoo.apps.security.Group;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.security.annotation.Secured;

import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.OrgRole;
import cn.redflagsoft.base.dao.EntityGroupDao;
import cn.redflagsoft.base.dao.OrgDao;
import cn.redflagsoft.base.dao.OrgRoleDao;
import cn.redflagsoft.base.event2.EventSource;
import cn.redflagsoft.base.event2.OrgEvent;
import cn.redflagsoft.base.security.AuthUtils;
import cn.redflagsoft.base.service.OrgService;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OrgServiceImpl extends EventSource implements OrgService{
	public static final Log log = LogFactory.getLog(OrgServiceImpl.class);
	
	private OrgDao orgDao;
	private OrgRoleDao orgRoleDao;
	private EntityGroupDao entityGroupDao;
	
	public EntityGroupDao getEntityGroupDao() {
		return entityGroupDao;
	}

	public void setEntityGroupDao(EntityGroupDao entityGroupDao) {
		this.entityGroupDao = entityGroupDao;
	}

	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}

	@Secured({"ROLE_SYS", "ROLE_ADMIN", "ROLE_ADMINISTRATOR", "ROLE_DELETE_ORG"})
	public int deleteOrg(Org org) {
		if(org == null) {
			log.warn("deleteOrg 时 org = null,return delete line = 0 ... ");
			return 0;
		}
		if(org.getId().longValue() <= Org.MAX_SYS_ID){
			log.warn("不能删除系统内置部门: " + org.getId());
			return 0;
		}
		//删除前
		dispatchEvent(new OrgEvent(OrgEvent.Type.BEFORE_DELETE, org));
		
		entityGroupDao.remove(Restrictions.eq("entityID", org.getId()));
//		entityGroupDao.removeByOrgId(org.getId());
		int row = orgDao.delete(org);
		dispatchEvent(new OrgEvent(OrgEvent.Type.DELETED, org));
		return row;
	}
	
	
	@Secured({"ROLE_SYS", "ROLE_ADMIN", "ROLE_ADMINISTRATOR", "ROLE_DELETE_ORG"})
	public int deleteOrgs(List<Long> ids) {
		if(ids == null || ids.isEmpty()) {
			log.warn("deleteOrgs 时 ids = null,return delete line = 0 ... ");
			return 0;
		}
		
		int n = 0;
		for(Long id: ids){
			Org org = orgDao.get(id);
			n += deleteOrg(org);
		}
		return n;
	}
	
	/**
	 * 批量删除效率高，但不好控制。
	 * @param ids
	 * @return
	 */
	public int deleteOrgs_BAK(List<Long> ids) {
		if(ids == null || ids.isEmpty()) {
			log.warn("deleteOrgs 时 ids = null,return delete line = 0 ... ");
			return 0;
		}
		List<Long> actualIds = new ArrayList<Long>();
		for(Long id: ids){
			if(id.longValue() > Org.MAX_SYS_ID){
				actualIds.add(id);
			}else{
				log.warn("不能删除系统内置部门: " + id);
			}
		}
		if(actualIds.size() == 0){
			return 0;
		}
		
		Long[] theIds = actualIds.toArray(new Long[actualIds.size()]);
		
		//删除分组关联关系
		entityGroupDao.remove(Restrictions.in("entityID", theIds));
		
		int rows = orgDao.remove(theIds/*actualIds.toArray(new Long[actualIds.size()])*/);
		
		//处理事件
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deletedRows", rows);
		map.put("deletingIds", theIds);
		dispatchEvent(new OrgEvent(OrgEvent.Type.BATCH_DELETED, new Org(), map));
		
		return rows;
	}

	public Org getOrgByDutyEntity(Long dutyEntity) {
		return orgDao.get(dutyEntity);
	}
	
	public Org getOrg(Long id){
		return orgDao.get(id);
	}

	@Secured({"ROLE_ADMIN", "ROLE_SYS", "ROLE_ADMINISTRATOR"})
	public Org updateOrg(Org org) {
		if(org == null) {
			log.warn("updateOrg 时 org = null,修改失败 return org = null ... ");
			return org;
		}		
//		Org old = orgDao.get(org.getId());
//		if (log.isDebugEnabled()) {
//			log.debug("Old,New,OldValue,NewValue: " + old + ", " + org + ", " + old.getAbbr() + ", "
//					+ org.getAbbr());
//		}
		
		Org org2 = orgDao.update(org);
		
//		if (log.isDebugEnabled()) {
//			log.debug("Old,New,OldValue,NewValue: " + old + ", " + org + ", " + old.getAbbr() + ", "
//					+ org.getAbbr());
//		}
//		
//		if(!EqualsUtils.equals(old.getAbbr(), org2.getAbbr())){
//			dispatchEvent(new OrgAbbrChangeEvent(org2, old.getAbbr(), org2.getAbbr()));
//		}
//		if(!EqualsUtils.equals(old.getName(), org2.getName())){
//			dispatchEvent(new OrgNameChangeEvent(org2, old.getName(), org2.getName()));
//		}
		dispatchEvent(new OrgEvent(OrgEvent.Type.UPDATED, org2));
		return org2;
	}

	//@Secured({"ROLE_ADMIN", "ROLE_SYS", "ROLE_ADMINISTRATOR"})
	public Org saveOrg(Org org) {
//		if(org == null) {
//			log.warn("saveOrg 时 org = null,保存失败 return org = null ... ");
//			return org;
//		}
		Org org2 = orgDao.save(org);
		
		dispatchEvent(new OrgEvent(OrgEvent.Type.CREATED, org2));
		return org2;
	}

	public List<Org> findOrgs() {
		return orgDao.find();
	}
	
	/**
	 * 
	 * @param rf
	 * @return
	 */
	@Queryable()
	@Secured({"ROLE_ADMIN", "ROLE_SYS", "ROLE_ADMINISTRATOR"})
	public List<Org> findOrgs(ResultFilter rf){
		List<Org> list = orgDao.find(rf);
		
		//仅仅是系统管理员？由于方法只能有所注释的3个权限访问，所以可以
		//不做以下的前一个条件的判断
		if(/*AuthUtils.isSupervisor() && */!AuthUtils.isAdministrator()){
			return list;
		}
		
		List<Org> result = new ArrayList<Org>();
		for(Org org:list){
			if(org.getId().longValue() > Org.MAX_SYS_ID){
				result.add(org);
			}
		}
		
		return result;
	}

	/**
	 * @return the orgRoleDao
	 */
	public OrgRoleDao getOrgRoleDao() {
		return orgRoleDao;
	}

	/**
	 * @param orgRoleDao the orgRoleDao to set
	 */
	public void setOrgRoleDao(OrgRoleDao orgRoleDao) {
		this.orgRoleDao = orgRoleDao;
	}

	public List<Group> findRolesByOrgId(Long orgId) {
		return orgRoleDao.findRolesByOrgId(orgId);
	}

	public List<Group> setOrgRoles(Long orgId, List<Long> roleIds) {
		orgRoleDao.remove(Restrictions.eq("orgId", orgId));
//		for(Long roleId: roleIds){
//			OrgRole or = new OrgRole(orgId, roleId);
//			orgRoleDao.save(or);
//		}
		addOrgRoles(orgId, roleIds);
		return findRolesByOrgId(orgId);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ADMINISTRATOR"})
	public void addOrgRoles(Long orgId, List<Long> roleIds) {
		for(Long roleId: roleIds){
			OrgRole or = new OrgRole(orgId, roleId);
			orgRoleDao.save(or);
		}
	}

	@Secured({"ROLE_ADMIN", "ROLE_ADMINISTRATOR"})
	public void addOrgRole(Long orgId, Long roleId) {
		OrgRole or = new OrgRole(orgId, roleId);
		orgRoleDao.save(or);
	}

	@Secured({"ROLE_ADMIN", "ROLE_ADMINISTRATOR"})
	public void removeOrgRole(Long orgId, Long roleId) {
		orgRoleDao.removeByUnique(orgId, roleId);
	}
	
	
	@Queryable
	public List<Labelable> findLabelableOrgs(ResultFilter rf){
		List<Labelable> list = orgDao.findLabelables(rf);
		
		//是管理员，但不是超级管理员
		if(/*AuthUtils.isAdministrator() && !AuthUtils.isSupervisor()*/ !AuthUtils.isAdmin()){
			List<Labelable> result = new ArrayList<Labelable>();
			for(Labelable l: list){
				if(((Long)l.getData()).longValue() >= 100L){
					result.add(l);
				}else{
					log.debug("Administrator不能查看系统内置部门：" + l.getLabel());
				}
			}
			return result;
		}else{
			return list;
		}
	}

	public int getSuborgsCount(Long orgId) {
		Criterion c = Restrictions.eq("parentOrgId", orgId);
		return orgDao.getCount(new ResultFilter(c, null));
	}

	public List<Org> findSuborgs(Long orgId) {
		Criterion c = Restrictions.eq("parentOrgId", orgId);
		Order o = Order.asc("displayOrder");
		return orgDao.find(new ResultFilter(c, o));
	}

	public List<Long> findSuborgIds(Long orgId) {
		Criterion c = Restrictions.eq("parentOrgId", orgId);
		Order o = Order.asc("displayOrder");
		return orgDao.findIds(new ResultFilter(c, o));
	}

	@Secured({"ROLE_ADMIN", "ROLE_ADMINISTRATOR"})
	public int removeOrgRole(Long orgId) {
		return orgRoleDao.remove(Restrictions.eq("orgId", orgId));
	}
}
