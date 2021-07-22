package cn.redflagsoft.base.scheme.schemes.entity;

import java.util.List;

import org.opoo.apps.Model;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.Group;
import org.opoo.apps.security.GroupManager;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.OrgCategory;
import cn.redflagsoft.base.bean.OrgCategoryToOrgGroup;
import cn.redflagsoft.base.bean.OrgCategoryToRole;
import cn.redflagsoft.base.bean.OrgGroup;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.service.OrgCategoryService;
import cn.redflagsoft.base.service.OrgCategoryToOrgGroupService;
import cn.redflagsoft.base.service.OrgCategoryToRoleService;
import cn.redflagsoft.base.service.OrgGroupService;
import cn.redflagsoft.base.service.OrgService;

import com.google.common.collect.Lists;

public class OrgCategoryScheme extends AbstractScheme{
	
	private OrgCategory orgCategory;
	private OrgCategoryService orgCategoryService;
	private OrgCategoryToRoleService orgCategoryToRoleService;
	private OrgCategoryToOrgGroupService orgCategoryToOrgGroupService;
	private OrgService orgService;
	private OrgGroupService orgGroupService;
	private String flag;
	
	private List<Long> roleIds;
	private List<Long> groupIds;
	private Long id;
	private List<Long> ids;
	
	
	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public OrgGroupService getOrgGroupService() {
		return orgGroupService;
	}

	public void setOrgGroupService(OrgGroupService orgGroupService) {
		this.orgGroupService = orgGroupService;
	}

	public OrgService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgCategoryToRoleService getOrgCategoryToRoleService() {
		return orgCategoryToRoleService;
	}

	public void setOrgCategoryToRoleService(
			OrgCategoryToRoleService orgCategoryToRoleService) {
		this.orgCategoryToRoleService = orgCategoryToRoleService;
	}

	public OrgCategoryToOrgGroupService getOrgCategoryToOrgGroupService() {
		return orgCategoryToOrgGroupService;
	}

	public void setOrgCategoryToOrgGroupService(
			OrgCategoryToOrgGroupService orgCategoryToOrgGroupService) {
		this.orgCategoryToOrgGroupService = orgCategoryToOrgGroupService;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public List<Long> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<Long> groupIds) {
		this.groupIds = groupIds;
	}

	public OrgCategoryService getOrgCategoryService() {
		return orgCategoryService;
	}

	public void setOrgCategoryService(OrgCategoryService orgCategoryService) {
		this.orgCategoryService = orgCategoryService;
	}

	public OrgCategory getOrgCategory() {
		return orgCategory;
	}

	public void setOrgCategory(OrgCategory orgCategory) {
		this.orgCategory = orgCategory;
	}
	
	
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Object doSaveOrUpdateOrgCategory() throws SchemeException {
		if ("update".equalsIgnoreCase(flag)) {
			if (orgCategory != null && orgCategory.getId() != null) {
				OrgCategory tmp = orgCategoryService.getOrgCategoryById(orgCategory.getId());
				if(tmp == null){
					throw new IllegalArgumentException("被修改对象不能为空。");
				}
				if(!orgCategory.getName().equals(tmp.getName())){
					//TODO 同步更新 org 表中的 category_name 字段
					ResultFilter filter = ResultFilter.createEmptyResultFilter();
					SimpleExpression eq = Restrictions.eq("categoryId", tmp.getId());
					filter.setCriterion(eq);
					List<Org> list = orgService.findOrgs(filter);
					
					if(list != null && !list.isEmpty()){
						for (Org org : list) {
							org.setCategoryName(orgCategory.getName());
							orgService.updateOrg(org);
						}
					}
				}
				tmp.setCode(orgCategory.getCode());
				tmp.setSn(orgCategory.getSn());
				tmp.setName(orgCategory.getName());
				tmp.setAbbr(orgCategory.getAbbr());
				tmp.setDisplayOrder(orgCategory.getDisplayOrder());
				tmp.setRemark(orgCategory.getRemark());
				orgCategoryService.update(tmp);
				String result = "修改机构信息成功!";
				Model model = new Model(true, result, null);
				model.setData(tmp);
				return model;
			}
			return "";
		} else {
			if (orgCategory != null) {
				OrgCategory category = orgCategoryService.save(orgCategory);
				String result = "新增单位信息成功!";
				Model model = new Model(true, result, null);
				model.setData(category);
				return model;
			} else {
				// result = "添加单位信息失败!";
				throw new SchemeException("新增单位为空，新增失败。");
			}
		}
	}
	
	
	/**
	 * 删除指定机构的指定角色集合。
	 * 必须指定参数 id（机构id） 和 roleIds（角色id集合）
	 * 
	 * 将删除多个角色。
	 * @return
	 * @throws SchemeException
	 * @see 
	 */
	public Object doRemoveRoles() throws SchemeException{
		Assert.notNull(id, "机构的 id 不能为空。");
		Assert.notEmpty(roleIds, "要删除的角色集合不能为空。");
		
		
		int n = 0;
		for(Long roleId: roleIds){
			orgCategoryToRoleService.removeOrgCategoryToRole(id, roleId);
			n++;
		}
		
		return "机构角色删除成功，共删除了 " + n + " 个角色。";
	}
	
	public Object doRemoveGroups(){
		Assert.notNull(id, "机构的 id 不能为空。");
		Assert.notEmpty(groupIds, "要删除的分组集合不能为空。");
		int n = 0; 
		for(Long groupId: groupIds){
			orgCategoryToOrgGroupService.removeOrgCategoryToOrgGroup(id, groupId);
			n++;
		}
		return "机构所属分组删除成功，共删除了 " + n + " 个分组。";
	}
	
	/**
	 * 查询当前机构已拥有的角色。
	 * 必须指定机构的id。
	 * @return
	 * @throws SchemeException
	 * 
	 */
	public Object viewFindRoles() throws SchemeException{
		Assert.notNull(id, "单位的 id 不能为空。");
		List<OrgCategoryToRole> list = orgCategoryToRoleService.findOrgCategoryToRoleByOrgCategoryId(id);
		List<Group> result = Lists.newArrayList();
		
		GroupManager gm = (GroupManager) Application.getContext().getUserManager();
		
		if(list != null && !list.isEmpty()){
			for (OrgCategoryToRole r : list) {
				Group group = gm.getGroup(r.getRoleId());
				result.add(group);
			}
		}
		return result;
	}
	
	/**
	 * 查询当前机构已拥有的分组。
	 * 必须指定机构的id。
	 * @return
	 * @throws SchemeException
	 * 
	 */
	public Object viewFindGroups() throws SchemeException{
		Assert.notNull(id, "单位的 id 不能为空。");
		List<OrgCategoryToOrgGroup> list = orgCategoryToOrgGroupService.findOrgCategoryToOrgGroupByOrgCategoryId(id);
		
		List<OrgGroup> result = Lists.newArrayList();
		if(list != null && !list.isEmpty()){
			for (OrgCategoryToOrgGroup o : list) {
				OrgGroup group = orgGroupService.getGroup(o.getOrgGroupId());
				result.add(group);
			}
		}
		return result;
	}
	
	
	public Object doAddGroups(){
		Assert.notNull(id, "单位的 id 不能为空。");
		Assert.notEmpty(groupIds, "要添加的分组集合不能为空。");
		orgCategoryToOrgGroupService.save(id,
				groupIds);
		return "单位所属分组添加成功，共添加了 " + groupIds.size() + " 个分组。";
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
		Assert.notEmpty(roleIds, "要添加的角色集合不能为空。");
		orgCategoryToRoleService.save(id, roleIds);
		return "单位角色添加成功，共添加了 " + roleIds.size() + " 个角色。";
	}
	
	public Object doDeleteList() throws SchemeException {
		Assert.notNull(ids, "被删除的机构的 id集合不能为空。");
		int cnt = orgCategoryService.deleteOrgCategory(ids);
		return "删除了  " + cnt + " 个机构。";
	}
}
