/*
 * $Id: ClerkGroupScheme.java 4037 2010-11-04 08:11:49Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
		Assert.notNull(group, "�������ķ��鲻��Ϊ�ա�");
		Assert.notNull(group.getName(), "�������ķ������Ʋ���Ϊ�ա�");
		
//		group.setCategory(RFSGroup.CATEGORY_��Ա����);
		
		clerkGroupService.createGroup(group);
		
		return "��Ա���鴴���ɹ���";
	}
	
	public Object doUpdateGroup(){
		Assert.notNull(group, "���޸ĵķ��鲻��Ϊ�ա�");
		Assert.notNull(group.getName(), "���޸ĵķ������Ʋ���Ϊ�ա�");
		Assert.notNull(group.getId(), "���޸ĵķ���ID����Ϊ�ա�");
		
		clerkGroupService.updateGroup(group);
		
		return "��Ա�����޸ĳɹ���";
	}
	
	public Object doDeleteGroups(){
		Assert.notNull(ids, "��ɾ������ID���ϲ���Ϊ�ա�");
		clerkGroupService.removeGroups(ids);
		return "��Ա����ɾ���ɹ���";
	}
	
	
	public Object doRemoveClerks(){
		Assert.notNull(id, "����� id ����Ϊ�ա�");
		Assert.notEmpty(ids, "Ҫɾ������Ա���ϲ���Ϊ�ա�");
		
		int n = clerkGroupService.removeClerksFromGroup(ids, id);
		return "������Աɾ���ɹ�����ɾ���� " + n + " ����";
	}
	
	public Object doAddClerks(){
		Assert.notNull(id, "����� id ����Ϊ�ա�");
		Assert.notEmpty(ids, "Ҫ��ӵ���Ա���ϲ���Ϊ�ա�");
		
		clerkGroupService.addClerksToGroup(ids, id);
		return "�����������Ա�ɹ���������� " + ids.size() + " ����Ա��";
	}
	
	
	public Object viewFindClerks(){
		Assert.notNull(id, "�� ID ����Ϊ�ա�");
		return clerkGroupService.findClerksInGroup(id);
	}
	
	
	public Object viewFindCandidateClerks(){
		Assert.notNull(id, "�� ID ����Ϊ�ա�");
		return clerkGroupService.findClerksNotInGroup(id);
	}
	
	
	public Object doSortClerks(){
		Assert.notNull(id, "����� id ����Ϊ�ա�");
		Assert.notEmpty(ids, "Ҫ�������Ա���ϲ���Ϊ�ա�");
		
		int cnt = clerkGroupService.sortClerksOfGroup(id, ids);
		return "���µ�����˳�򣬱䶯��¼ " + cnt + " ����";
	}
	
}
