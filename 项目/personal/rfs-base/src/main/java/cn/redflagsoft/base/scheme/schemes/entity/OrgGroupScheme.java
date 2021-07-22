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
		Assert.notNull(group, "�������ķ��鲻��Ϊ�ա�");
		Assert.notNull(group.getName(), "�������ķ������Ʋ���Ϊ�ա�");
		orgGroupService.createGroup(group);
		
		return "��λ���鴴���ɹ���";
	}
	
	public Object doDeleteGroups(){
		Assert.notNull(ids, "��ɾ������ID���ϲ���Ϊ�ա�");
		int n = orgGroupService.removeGroups(ids);
		return "��λ����ɾ���ɹ�����ɾ���� " + n + " �����顣";
	}
	
	
	public Object doUpdateGroup(){
		Assert.notNull(group, "���޸ĵķ��鲻��Ϊ�ա�");
		Assert.notNull(group.getName(), "���޸ĵķ������Ʋ���Ϊ�ա�");
		Assert.notNull(group.getId(), "���޸ĵķ���ID����Ϊ�ա�");
		
		orgGroupService.updateGroup(group);
		
		return "��λ�����޸ĳɹ���";
	}
	
	
	public Object doAddOrgs(){
		Assert.notNull(id, "����� id ����Ϊ�ա�");
		Assert.notEmpty(ids, "Ҫ��ӵĵ�λ���ϲ���Ϊ�ա�");
		
		orgGroupService.addOrgsToGroup(ids, id);
		return "��������ӵ�λ�ɹ���������� " + ids.size() + " ����λ��";
	}
	
	public Object doRemoveOrgs(){
		Assert.notNull(id, "����� id ����Ϊ�ա�");
		Assert.notEmpty(ids, "Ҫɾ������Ա���ϲ���Ϊ�ա�");
		
		int n = orgGroupService.removeOrgsFromGroup(ids, id);
		return "������Աɾ���ɹ�����ɾ���� " + n + " ����";
	}
	
	public Object viewFindOrgs(){
		Assert.notNull(id, "�� ID ����Ϊ�ա�");
		return orgGroupService.findOrgsInGroup(id);
	}
	
	
	public Object viewFindCandidateOrgs(){
		Assert.notNull(id, "�� ID ����Ϊ�ա�");
		return orgGroupService.findOrgsNotInGroup(id);
	}
	
	public Object doSortOrgs(){
		Assert.notNull(id, "����� id ����Ϊ�ա�");
		Assert.notEmpty(ids, "Ҫ����ĵ�λ���ϲ���Ϊ�ա�");
		
		int cnt = orgGroupService.sortOrgsOfGroup(id, ids);
		return "���µ�����˳�򣬱䶯��¼ " + cnt + " ����";
	}
}
