package cn.redflagsoft.base.scheme.schemes.entity;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.util.Assert;

import cn.redflagsoft.base.bean.OrgGroup;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.service.OrgGroupService;

public class OrgGroupScheme extends AbstractScheme {
	
	public static final Log log = LogFactory.getLog(OrgGroupScheme.class);
	
	
	private OrgGroupService orgGroupService;
	private OrgGroup group;
	private Long id;
	private List<Long> ids;
	public OrgGroupService getOrgGroupService() {
		return orgGroupService;
	}
	public void setOrgGroupService(OrgGroupService orgGroupService) {
		this.orgGroupService = orgGroupService;
	}
	public OrgGroup getGroup() {
		return group;
	}
	public void setGroup(OrgGroup group) {
		this.group = group;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Long> getIds() {
		return ids;
	}
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	
	public Object doCreateGroup(){
		Assert.notNull(group, "被创建的分组不能为空。");
		Assert.notNull(group.getName(), "被创建的分组名称不能为空。");
		orgGroupService.createGroup(group);
		
		return "单位分组创建成功。";
	}
	
	public Object doDeleteGroups(){
		Assert.notNull(ids, "被删除的组ID集合不能为空。");
		int n = orgGroupService.removeGroups(ids);
		return "单位分组删除成功，共删除了 " + n + " 个分组。";
	}
	
	
	public Object doUpdateGroup(){
		Assert.notNull(group, "被修改的分组不能为空。");
		Assert.notNull(group.getName(), "被修改的分组名称不能为空。");
		Assert.notNull(group.getId(), "被修改的分组ID不能为空。");
		
		orgGroupService.updateGroup(group);
		
		return "单位分组修改成功。";
	}
	
	
	public Object doAddOrgs(){
		Assert.notNull(id, "分组的 id 不能为空。");
		Assert.notEmpty(ids, "要添加的单位集合不能为空。");
		
		orgGroupService.addOrgsToGroup(ids, id);
		return "分组中添加单位成功，共添加了 " + ids.size() + " 个单位。";
	}
	
	public Object doRemoveOrgs(){
		Assert.notNull(id, "分组的 id 不能为空。");
		Assert.notEmpty(ids, "要删除的人员集合不能为空。");
		
		int n = orgGroupService.removeOrgsFromGroup(ids, id);
		return "组中人员删除成功，共删除了 " + n + " 个。";
	}
	
	public Object viewFindOrgs(){
		Assert.notNull(id, "组 ID 不能为空。");
		return orgGroupService.findOrgsInGroup(id);
	}
	
	
	public Object viewFindCandidateOrgs(){
		Assert.notNull(id, "组 ID 不能为空。");
		return orgGroupService.findOrgsNotInGroup(id);
	}
	
	public Object doSortOrgs(){
		Assert.notNull(id, "分组的 id 不能为空。");
		Assert.notEmpty(ids, "要排序的单位集合不能为空。");
		
		int cnt = orgGroupService.sortOrgsOfGroup(id, ids);
		return "重新调整了顺序，变动记录 " + cnt + " 条。";
	}
}
