/*
 * $Id: OrgScheme.java 6413 2014-07-02 06:53:56Z lf $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.Model;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.event.v2.EventDispatcherAware;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.Group;
import org.opoo.apps.security.GroupManager;
import org.opoo.apps.security.SecurityUtils;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserHolder;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.security.annotation.Secured;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.OrgCategory;
import cn.redflagsoft.base.bean.OrgCategoryToOrgGroup;
import cn.redflagsoft.base.bean.OrgCategoryToRole;
import cn.redflagsoft.base.bean.OrgGroup;
import cn.redflagsoft.base.dao.OrgCategoryDao;
import cn.redflagsoft.base.dao.OrgCategoryToOrgGroupDao;
import cn.redflagsoft.base.dao.OrgCategoryToRoleDao;
import cn.redflagsoft.base.event2.OrgAbbrChangeEvent;
import cn.redflagsoft.base.event2.OrgNameChangeEvent;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.security.AuthUtils;
import cn.redflagsoft.base.service.OrgGroupService;
import cn.redflagsoft.base.service.OrgService;
import cn.redflagsoft.base.util.EqualsUtils;

import com.google.common.collect.Lists;



/**
 * 单位管理。
 * 
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OrgScheme extends AbstractScheme implements EventDispatcherAware{
	public static final Log log = LogFactory.getLog(OrgScheme.class);
	public static final TreeMap<Long, String> group = new TreeMap<Long, String>();
	private OrgService orgService;
	//private EntityGroupService entityGroupService;
	private OrgGroupService orgGroupService;
	
	private OrgCategoryToRoleDao  orgCategoryToRoleDao;
	private OrgCategoryToOrgGroupDao orgCategoryToOrgGroupDao;
	private OrgCategoryDao orgCategoryDao;
	
	private Org org;
	private List<Long> ids;
	private String flag;
	private Long id;
	private Long roleId;
	private Long groupId;
	
	
	private boolean roleOrderByName = true;

	private EventDispatcher eventDispatcher;
	
	/* (non-Javadoc)
	 * @see org.opoo.apps.event.v2.EventDispatcherAware#setEventDispatcher(org.opoo.apps.event.v2.EventDispatcher)
	 */
	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
		if(log.isDebugEnabled()){
			log.debug("Set EventDispatcher: " + eventDispatcher);
		}
	}

	public EventDispatcher getEventDispatcher() {
		return eventDispatcher;
	}

	
	public OrgCategoryToRoleDao getOrgCategoryToRoleDao() {
		return orgCategoryToRoleDao;
	}

	public void setOrgCategoryToRoleDao(OrgCategoryToRoleDao orgCategoryToRoleDao) {
		this.orgCategoryToRoleDao = orgCategoryToRoleDao;
	}

	public OrgCategoryToOrgGroupDao getOrgCategoryToOrgGroupDao() {
		return orgCategoryToOrgGroupDao;
	}

	public void setOrgCategoryToOrgGroupDao(
			OrgCategoryToOrgGroupDao orgCategoryToOrgGroupDao) {
		this.orgCategoryToOrgGroupDao = orgCategoryToOrgGroupDao;
	}

	public OrgGroupService getOrgGroupService() {
		return orgGroupService;
	}

	public void setOrgGroupService(OrgGroupService orgGroupService) {
		this.orgGroupService = orgGroupService;
	}
	
	public OrgCategoryDao getOrgCategoryDao() {
		return orgCategoryDao;
	}

	public void setOrgCategoryDao(OrgCategoryDao orgCategoryDao) {
		this.orgCategoryDao = orgCategoryDao;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the roleOrderByName
	 */
	public boolean isRoleOrderByName() {
		return roleOrderByName;
	}

	/**
	 * @param roleOrderByName the roleOrderByName to set
	 */
	public void setRoleOrderByName(boolean roleOrderByName) {
		this.roleOrderByName = roleOrderByName;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public OrgService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}
	
	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	
	static {
		group.put(1L, "管理单位");
		group.put(2L, "责任单位");
		group.put(3L, "监督单位");
		group.put(4L, "领导单位");
	}
	
	public static TreeMap<Long, String> getGroup() {
		return group;
	}	
	
//	public Object doSaveOrUpdateOrg2() throws SchemeException {
//		String result;
//		if(org!=null){
//		  if(org.getId()!=null){
//			  Org tmp = orgService.getOrgByDutyEntity(org.getId());
//				if(tmp != null) {
//					if(org.getTelNo() != null) {
//						tmp.setTelNo(org.getTelNo());
//					}
//					if(org.getFaxNo() != null) {
//						tmp.setFaxNo(org.getFaxNo());
//					}
//					if(org.getLegalAddr() != null) {
//						tmp.setLegalAddr(org.getLegalAddr());
//					}
//					if(org.getWorkAddr() != null) {
//						tmp.setWorkAddr(org.getWorkAddr());
//					}
//					if(Short.MAX_VALUE >= org.getParents() && Short.MIN_VALUE <= org.getParents()) {
//						tmp.setParents(org.getParents());
//					}
//					if(Short.MAX_VALUE >= org.getChildren() && Short.MIN_VALUE <= org.getChildren()) {
//						tmp.setChildren(org.getChildren());
//					}
//					if(org.getModifier() != null) {
//						tmp.setModifier(org.getModifier());
//					}
//					if(org.getAbbr()!=null){
//						tmp.setAbbr(org.getAbbr());
//					}
//					if(org.getName()!=null){
//						tmp.setName(org.getName());
//					}
//					tmp.setModificationTime(new Date());
//					orgService.updateOrg(tmp);
//					result = "修改单位信息成功!"; 
//					} else {
//						log.warn("doSaveOrUpdateOrg 时获取 id = " + org.getId() + " 的 Org 对象不存在,修改单位信息失败 ... ");
//						result = "修改单位组信息失败!";
//					}
//		  }	else {
//			  	orgService.saveOrg(org);
//				result = "添加单位信息成功!";
//			  
//		  }
//		}else{
//			result = "表单提交失败！";
//		}
//		return result;
//	}
//	
	
	public Object doSaveOrUpdateOrg() throws SchemeException {
		if ("update".equalsIgnoreCase(flag)) {
			if (org != null && org.getId() != null) {
				Org tmp = orgService.getOrgByDutyEntity(org.getId());
				if(tmp == null){
					throw new IllegalArgumentException("被修改对象不能为空。");
				}
				String oldAbbr = tmp.getAbbr();
				String oldName = tmp.getName();
				
				
				//处理上级单位ID
				Long parentOrgId = org.getParentOrgId();
				if(parentOrgId != null){
					//顶级，清空
					if(parentOrgId.longValue() < 0L){
						parentOrgId = null;
					}
				}
				//检查上级单位ID
				if(parentOrgId != null){
					if(isParentOrgIdInSuborgsTree(orgService, org.getId().longValue(), parentOrgId.longValue())){
						log.debug("上级单位设置不正确，产生循环嵌套。");
						throw new IllegalArgumentException("上级单位设置不正确。");
					}
				}
				
				tmp.setParentOrgId(parentOrgId);
				
				if (org.getTelNo() != null) {
					tmp.setTelNo(org.getTelNo());
				}
				if (org.getFaxNo() != null) {
					tmp.setFaxNo(org.getFaxNo());
				}
				if (org.getLegalAddr() != null) {
					tmp.setLegalAddr(org.getLegalAddr());
				}
				if (org.getWorkAddr() != null) {
					tmp.setWorkAddr(org.getWorkAddr());
				}
				if (Short.MAX_VALUE >= org.getParents() && 0 <= org.getParents()) {
					tmp.setParents(org.getParents());
				}
				if (Short.MAX_VALUE >= org.getChildren() && 0<= org.getChildren()) {
					tmp.setChildren(org.getChildren());
				}
				if (org.getModifier() != null) {
					tmp.setModifier(org.getModifier());
				}
				if (org.getAbbr() != null) {
					tmp.setAbbr(org.getAbbr());
				}
				if (org.getName() != null) {
					tmp.setName(org.getName());
				}
				if (org.getLicense() != null) {
					tmp.setLicense(org.getLicense());
				}
				if (org.getAuthorizer() != null) {
					tmp.setAuthorizer(org.getAuthorizer());
				}
				if (org.getHolder() != null) {
					tmp.setHolder(org.getHolder());
				}
				if (org.getManager() != null) {
					tmp.setManager(org.getManager());
				}
				
				if(org.getCode() != null){
					tmp.setCode(org.getCode());
				}
				
//				if(org.getParentOrgId() != null){
//					if(org.getParentOrgId().longValue() < 0L){
//						tmp.setParentOrgId(null);
//					}else{
//						tmp.setParentOrgId(org.getParentOrgId());
//					}
//				}else{
//					tmp.setParentOrgId(null);
//				}
				
				if(org.getDistrictCode() != null){
					tmp.setDistrictCode(org.getDistrictCode());
					if(org.getDistrictName() != null){
						tmp.setDistrictName(org.getDistrictName());
					}
				}
				
				tmp.setModificationTime(new Date());
				tmp.setDisplayOrder(org.getDisplayOrder());
				//tmp = orgService.updateOrg(tmp);
				
				if(!EqualsUtils.equals(oldName, org.getName())){
					if(log.isDebugEnabled()){
						log.debug("发送OrgNameChangeEvent: " + org.getName());
					}
					eventDispatcher.dispatchEvent(new OrgNameChangeEvent(tmp, oldName, org.getName()));
				}
				
				if(!EqualsUtils.equals(oldAbbr, org.getAbbr())){
					if(log.isDebugEnabled()){
						log.debug("发送OrgAbbrChangeEvent: " + org.getAbbr());
					}
					eventDispatcher.dispatchEvent(new OrgAbbrChangeEvent(tmp, oldAbbr, org.getAbbr()));
				}
				
//				if (org.getCategoryId() != null) {
//					Long categoryId = org.getCategoryId();
//					OrgCategory category = orgCategoryDao.get(categoryId);
//					org.setCategoryName(category.getName());
//				}
				
				Long oldCategoryId = tmp.getCategoryId();//.longValue();
				
				Long newCategoryId = org.getCategoryId();//.longValue();
				
				boolean needAdd = false;
				boolean needDelete = false;
				
				if(EqualsUtils.equals(oldCategoryId, newCategoryId)/*oldCategoryId == newCategoryId*/){
					//DO NOTHING
				}else if(oldCategoryId == null && newCategoryId != null){
					// 添加 
					needAdd = true;
				}else if(oldCategoryId != null && newCategoryId == null){
					needDelete = true;
					//删除
				}else if(oldCategoryId != null && newCategoryId != null && !EqualsUtils.equals(oldCategoryId, newCategoryId)){
					//删除
					needDelete = true;
					//添加
					needAdd = true;
					
					tmp.setCategoryId(newCategoryId);
					OrgCategory category = orgCategoryDao.get(newCategoryId);
					org.setCategoryName(category.getName());
				}
				
				if(needDelete){
					deleteOrgRole(tmp.getId());
					deleteOrgToGroup(tmp.getId());
				}
				
				if(needAdd){
					addOrgRole(tmp.getId(), newCategoryId);
					addOrgToGroup(tmp.getId(), newCategoryId);
				}
				
				tmp = orgService.updateOrg(tmp);

				String result = "修改单位信息成功!";
				Model model = new Model(true, result, null);
				model.setData(tmp);
				return model;
			} else {
				log.warn("doSaveOrUpdateOrg 时获取 org 为空! ");
				// result = "修改单位信息失败!";
				throw new SchemeException("被修改单位为空，修改失败。");
			}
		} else {
			if (org != null) {
				
				if (org.getCategoryId() != null) {
					Long categoryId = org.getCategoryId();
					OrgCategory category = orgCategoryDao.get(categoryId);
					org.setCategoryName(category.getName());
				}
				
				Org saved = orgService.saveOrg(org);
				//保存组信息
				if(groupId != null){
					//entityGroupService.addOrgToGroup(groupId, saved.getId());
					orgGroupService.addOrgToGroup(saved.getId(), groupId);
				}
				
				if(org.getCategoryId() != null){
					
					Long categoryId = org.getCategoryId();
					
					addOrgRole(saved.getId(), categoryId);
					addOrgToGroup(saved.getId(), categoryId);
				}

				String result = "新增单位信息成功!";
				Model model = new Model(true, result, null);
				model.setData(saved);
				return model;
			} else {
				// result = "添加单位信息失败!";
				throw new SchemeException("新增单位为空，新增失败。");
			}
		}
		//return result;
	}
	
	
	private void addOrgRole(Long orgId,Long categoryId){
		// 把角色插入关联关系表
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(Restrictions.eq("orgCategoryId", categoryId));
		List<OrgCategoryToRole> listRole = orgCategoryToRoleDao.find(filter);
		
		if(listRole != null && !listRole.isEmpty()){
			for (OrgCategoryToRole orgCategoryToRole : listRole) {
				orgService.addOrgRole(orgId, orgCategoryToRole.getRoleId());
			}
		}
	}
	
	private void addOrgToGroup(Long orgId,Long categoryId){
		// 把分组加入关联关系表
		ResultFilter filter2 = ResultFilter.createEmptyResultFilter();
		filter2.setCriterion(Restrictions.eq("orgCategoryId", categoryId));
		List<OrgCategoryToOrgGroup> listGroup = orgCategoryToOrgGroupDao.find(filter2);
		if(listGroup != null && !listGroup.isEmpty()){
			for (OrgCategoryToOrgGroup g : listGroup) {
				orgGroupService.addOrgToGroup(orgId, g.getOrgGroupId());
			}
		}
	}
	
	private void deleteOrgRole(Long orgId){
		orgService.removeOrgRole(orgId);
	}
	
	private void deleteOrgToGroup(Long orgId){
		orgGroupService.removeOrgGroup(orgId);
	}
	
	private static boolean isParentOrgIdInSuborgsTree(OrgService orgService, long orgId, long parentOrgId){
		if(parentOrgId == orgId){
			return true;
		}
		List<Long> list = orgService.findSuborgIds(orgId);
		for(long id: list){
			if(isParentOrgIdInSuborgsTree(orgService, id, parentOrgId)){
				return true;
			}
		}
		return false;
	}
	
	
	public List<Org> viewFindOrgLabel() {
		return orgService.findOrgs();
	}
	
	public Object doDelete() throws SchemeException {
		Assert.notNull(org, "被删除的单位对象不能为空。");
		int row = orgService.deleteOrg(org);
		return "删除了  " + row + " 个单位。";
	}

	public Object doDeleteById() throws SchemeException {
		Assert.notNull(id, "被删除的单位ID不能为空。");
		
		int count = orgService.getSuborgsCount(id);
		if(count > 0){
			return new Model(false, "删除失败，该单位包含下级单位，不能直接删除。", null);
		}
		
		Org deleting = orgService.getOrg(id);
		int orgs = orgService.deleteOrg(deleting);
//		int orgs = orgService.deleteOrgs(Arrays.asList(id));
		
		String result = "删除了  " + orgs + " 个单位。";
		
		//删除之后，前台UI定位到上级单位
		if(orgs == 1){
			Long parentOrgId = deleting.getParentOrgId();
			Org org = new Org();
			org.setId(-1L);
			if(parentOrgId != null){
				org.setId(parentOrgId);
			}
			Model model = new Model(true, result, null);
			model.setData(org);
			return model;
		}else{
			log.error("删除了0个单位，删除有问题。");
			return new Model(false, "删除失败", null);
		}
	}
	
	
	public Object doDeleteList() throws SchemeException {
		Assert.notNull(ids, "被删除的单位ID集合不能为空。");
		int orgs = orgService.deleteOrgs(ids);
		return "删除了  " + orgs + " 个单位。";
	}
	



	public Object doScheme() throws SchemeException {
		return null;
		//return this.doSaveOrUpdateOrg();
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// 以下5个方法用户管理用户的角色。
	// 开发者可使用  doAddRole 和 doRemoveRole 方法来一次添加一个或者删除一个角色，也
	// 可以使用 doSetOrgRoles 一次保存多个角色。
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * 查询当前单位已拥有的角色。
	 * 必须指定单位的id。
	 * 可使用参数 roleOrderByName 来确定查询结果的排序。
	 * 
	 * @return
	 * @throws SchemeException
	 * @see OrgService#findRolesByOrgId(Long)
	 */
	public Object viewFindRoles() throws SchemeException{
		Assert.notNull(id, "单位的 id 不能为空。");
		List<Group> list = orgService.findRolesByOrgId(id);
		Collections.sort(list, getRoleComparator());
		return list;
	}
	
	
	/**
	 * 查找单位可分配的角色。
	 * 根据登录用户角色是否包含系统角色。
	 * 该方法必须在登录后调用。
	 * 必须指定单位的id。
	 * 可使用参数 roleOrderByName 来确定查询结果的排序。
	 * 
	 * @return
	 * @throws SchemeException
	 * @see OrgService#findRolesByOrgId(Long)
	 */
	public Object viewFindCandidateRoles() throws SchemeException{
		Assert.notNull(id, "单位的 id 不能为空。");
		
		User user = UserHolder.getUser();
		boolean isAdmin = SecurityUtils.isGranted(user, "ROLE_ADMIN");
		List<Group> list = orgService.findRolesByOrgId(id);
		List<Long> gids = new ArrayList<Long>();
		for(Group g: list){
			gids.add(g.getId());
		}
		
		GroupManager gm = (GroupManager) Application.getContext().getUserManager();
		List<Group> groups = gm.findGroups();
		List<Group> results = new ArrayList<Group>();
		for(Group role: groups){
			if(gids.contains(role.getId())){
				continue;
			}
			if(!isAdmin && AuthUtils.isSystemGroup(role)){
//			if(role.getId().longValue() < 100L && !isAdmin){
				continue;
			}
			results.add(role);
		}
		Collections.sort(results, getRoleComparator());
		return results;
	}
	
	/**
	 * 向指定单位添加指定角色。
	 * 必须指定参数 id（单位id） 和 roleId（角色id）。
	 * 
	 * @return
	 * @throws SchemeException
	 * @see OrgService#addOrgRole(Long, Long)
	 */
	public Object doAddRole() throws SchemeException{
		Assert.notNull(id, "单位的 id 不能为空。");
		Assert.notNull(roleId, "要添加的角色的 id 不能为空。");
		
		orgService.addOrgRole(id, roleId);
		return "角色添加成功。";
	}
	
	/**
	 * 删除指定单位的指定角色。
	 * 必须指定参数 id（单位id） 和 roleId（角色id）。
	 * 
	 * 
	 * @return
	 * @throws SchemeException
	 * @see OrgService#removeOrgRole(Long, Long)
	 */
	public Object doRemoveRole() throws SchemeException{
		Assert.notNull(id, "单位的 id 不能为空。");
		Assert.notNull(roleId, "要添加的角色的 id 不能为空。");
		
		orgService.removeOrgRole(id, roleId);
		return "角色删除成功。";
	}
	
	
	/**
	 * 删除指定单位的指定角色集合。
	 * 必须指定参数 id（单位id） 和 ids（角色id集合）
	 * 
	 * 将删除多个角色。
	 * @return
	 * @throws SchemeException
	 * @see OrgService#removeOrgRole(Long, Long)
	 */
	public Object doRemoveRoles() throws SchemeException{
		Assert.notNull(id, "单位的 id 不能为空。");
		Assert.notEmpty(ids, "要删除的角色集合不能为空。");
		
		
		int n = 0;
		for(Long roleId: ids){
			orgService.removeOrgRole(id, roleId);
			n++;
		}
		
		return "单位角色删除成功，共删除了 " + n + " 个角色。";
	}
	
	/**
	 * 向指定单位的添加多个角色。
	 * 必须指定参数 id（单位id） 和 ids（角色id集合）
	 * 
	 * @return
	 * @throws SchemeException
	 * @see OrgService#addOrgRoles(Long, List)
	 */
	public Object doAddRoles() throws SchemeException{
		Assert.notNull(id, "单位的 id 不能为空。");
		Assert.notEmpty(ids, "要添加的角色集合不能为空。");
		
		orgService.addOrgRoles(id, ids);
		return "单位角色添加成功，共添加了 " + ids.size() + " 个角色。";
	}
	
	
	/**
	 * 保存指定单位的角色集合。
	 * 必须指定参数 id（单位id） 和 ids（角色id集合）
	 * 
	 * 
	 * 该方法将删除单位原来的角色，以当前参数中指定的角色集合重新创建单位的角色。
	 * 
	 * @return
	 * @throws SchemeException
	 * @see OrgService#setOrgRoles(Long, List)
	 */
	public Object doSaveRoles() throws SchemeException{
		Assert.notNull(id, "单位的 id 不能为空。");
		Assert.notEmpty(ids, "要保存的角色集合不能为空。");
		
		orgService.setOrgRoles(id, ids);
		return "单位角色保存成功。";
	}
	
	
	
	
	private Comparator<Group> getRoleComparator(){
		return roleOrderByName ? ROLE_NAME_COMPARATOR : ROLE_ID_COMPARATOR;
	}
	
	public static final Comparator<Group> ROLE_ID_COMPARATOR = new Comparator<Group>() {
		public int compare(Group o1, Group o2) {
			return o1.getId().compareTo(o2.getId());
		}
	};
	
	public static final Comparator<Group> ROLE_NAME_COMPARATOR = new Comparator<Group>() {
		public int compare(Group o1, Group o2) {
			return o1.getName().compareTo(o2.getName());
		}
	};
	
	////////////////////////
	///
	/////////////////////////////////
	
	public static final Comparator<OrgGroup> ORG_GROUP_COMPARATOR = new Comparator<OrgGroup>(){
		public int compare(OrgGroup o1, OrgGroup o2) {
			return o1.getName().compareTo(o2.getName());
		}
	};
	
	
	/**
	 * 查询单位列表
	 * @return
	 */
	@Deprecated
	@Secured({"ROLE_ADMIN", "ROLE_ADMINISTRATOR"})
	public Object viewFindOrgs(){
		List<Org> orgs = orgService.findOrgs(new ResultFilter(null, Order.asc("displayOrder")));
		if(AuthUtils.isAdministrator()){
			List<Org> list = Lists.newArrayList();
			for(Org org: orgs){
				if(org.getId().longValue() > Org.MAX_SYS_ID){
					list.add(org);
				}else{
					//去掉系统部分
				}
			}
			orgs = list;
		}
		return orgs;
	}
	
	/**
	 * 
	 * @return
	 */
	public Object viewFindGroups(){
		Assert.notNull(id, "单位 ID 不能为空。");

		List<OrgGroup> list = orgGroupService.findGroupsOfOrg(id);//entityGroupService.findExistGroupByOrgId(id);
		Collections.sort(list, ORG_GROUP_COMPARATOR);
		return list;
	}
	
	public Object viewFindCandidateGroups(){
		Assert.notNull(id, "单位 ID 不能为空。");
		List<OrgGroup> list = orgGroupService.findGorupNotOfOrg(id);//entityGroupService.findNotExistGroupByOrgId(id);
		Collections.sort(list, ORG_GROUP_COMPARATOR);
		return list;
	}
	
	
	public Object doAddGroup(){
		Assert.notNull(id, "单位的 id 不能为空。");
		Assert.notNull(groupId, "要添加的分组的 id 不能为空。");
		
		orgGroupService.addOrgToGroup(id, groupId);
		return "单位所属分组添加成功。";
	}
	
	
	public Object doAddGroups(){
		Assert.notNull(id, "单位的 id 不能为空。");
		Assert.notEmpty(ids, "要添加的分组集合不能为空。");
		
		orgGroupService.addOrgToGroups(id, ids);
		return "单位所属分组添加成功，共添加了 " + ids.size() + " 个分组。";
	}
	
	public Object doRemoveGroup(){
		Assert.notNull(id, "单位的 id 不能为空。");
		Assert.notNull(groupId, "要添加的分组的 id 不能为空。");
		
		orgGroupService.removeOrgFromGroup(id, groupId);
		return "单位所属分组删除成功。";
	}
	
	public Object doRemoveGroups(){
		Assert.notNull(id, "单位的 id 不能为空。");
		Assert.notEmpty(ids, "要删除的分组集合不能为空。");
		
		
		int n = orgGroupService.removeOrgFromGroups(id, ids);
//		for(Long groupId: ids){
//			orgGroupService.deleteOrgOfGroup(id, groupId);
//			n++;
//		}
		
		return "单位所属分组删除成功，共删除了 " + n + " 个分组。";
	}
	
	
	public Object viewFindCandidateGroups2(){
		Assert.notNull(id, "单位 ID 不能为空。");
		List<OrgGroup> list = orgCategoryToOrgGroupDao.findGroupsNotOfOrgCategory(id);
		return list;
	}
	
	public Object viewFindCandidateRoles2(){
		Assert.notNull(id, "单位的 id 不能为空。");
		List<Group> list = orgCategoryToRoleDao.findRolesNotOfOrgCategory(id);
		return list;
	}
}
