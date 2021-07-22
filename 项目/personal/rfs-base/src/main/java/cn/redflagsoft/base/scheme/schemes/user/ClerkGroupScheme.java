/*
 * $Id: ClerkGroupScheme.java 4037 2010-11-04 08:11:49Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.user;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.util.Assert;

import cn.redflagsoft.base.bean.RFSGroup;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.service.ClerkGroupService;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ClerkGroupScheme extends AbstractScheme {
	public static final Log log = LogFactory.getLog(ClerkGroupScheme.class);
	
	private ClerkGroupService clerkGroupService;
	
	private RFSGroup group;
	private Long id;
	private List<Long> ids;
	
	
	public ClerkGroupService getClerkGroupService() {
		return clerkGroupService;
	}
	public void setClerkGroupService(ClerkGroupService clerkGroupService) {
		this.clerkGroupService = clerkGroupService;
	}
	public RFSGroup getGroup() {
		return group;
	}
	public void setGroup(RFSGroup group) {
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
		
//		group.setCategory(RFSGroup.CATEGORY_人员分组);
		
		clerkGroupService.createGroup(group);
		
		return "人员分组创建成功。";
	}
	
	public Object doUpdateGroup(){
		Assert.notNull(group, "被修改的分组不能为空。");
		Assert.notNull(group.getName(), "被修改的分组名称不能为空。");
		Assert.notNull(group.getId(), "被修改的分组ID不能为空。");
		
		clerkGroupService.updateGroup(group);
		
		return "人员分组修改成功。";
	}
	
	public Object doDeleteGroups(){
		Assert.notNull(ids, "被删除的组ID集合不能为空。");
		clerkGroupService.removeGroups(ids);
		return "人员分组删除成功。";
	}
	
	
	public Object doRemoveClerks(){
		Assert.notNull(id, "分组的 id 不能为空。");
		Assert.notEmpty(ids, "要删除的人员集合不能为空。");
		
		int n = clerkGroupService.removeClerksFromGroup(ids, id);
		return "组中人员删除成功，共删除了 " + n + " 个。";
	}
	
	public Object doAddClerks(){
		Assert.notNull(id, "分组的 id 不能为空。");
		Assert.notEmpty(ids, "要添加的人员集合不能为空。");
		
		clerkGroupService.addClerksToGroup(ids, id);
		return "分组中添加人员成功，共添加了 " + ids.size() + " 个人员。";
	}
	
	
	public Object viewFindClerks(){
		Assert.notNull(id, "组 ID 不能为空。");
		return clerkGroupService.findClerksInGroup(id);
	}
	
	
	public Object viewFindCandidateClerks(){
		Assert.notNull(id, "组 ID 不能为空。");
		return clerkGroupService.findClerksNotInGroup(id);
	}
	
	
	public Object doSortClerks(){
		Assert.notNull(id, "分组的 id 不能为空。");
		Assert.notEmpty(ids, "要排序的人员集合不能为空。");
		
		int cnt = clerkGroupService.sortClerksOfGroup(id, ids);
		return "重新调整了顺序，变动记录 " + cnt + " 条。";
	}
	
}
