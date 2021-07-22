/*
 * $Id: OrgScheme.java 6413 2014-07-02 06:53:56Z lf $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
 * ��λ����
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
		group.put(1L, "����λ");
		group.put(2L, "���ε�λ");
		group.put(3L, "�ල��λ");
		group.put(4L, "�쵼��λ");
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
//					result = "�޸ĵ�λ��Ϣ�ɹ�!"; 
//					} else {
//						log.warn("doSaveOrUpdateOrg ʱ��ȡ id = " + org.getId() + " �� Org ���󲻴���,�޸ĵ�λ��Ϣʧ�� ... ");
//						result = "�޸ĵ�λ����Ϣʧ��!";
//					}
//		  }	else {
//			  	orgService.saveOrg(org);
//				result = "��ӵ�λ��Ϣ�ɹ�!";
//			  
//		  }
//		}else{
//			result = "���ύʧ�ܣ�";
//		}
//		return result;
//	}
//	
	
	public Object doSaveOrUpdateOrg() throws SchemeException {
		if ("update".equalsIgnoreCase(flag)) {
			if (org != null && org.getId() != null) {
				Org tmp = orgService.getOrgByDutyEntity(org.getId());
				if(tmp == null){
					throw new IllegalArgumentException("���޸Ķ�����Ϊ�ա�");
				}
				String oldAbbr = tmp.getAbbr();
				String oldName = tmp.getName();
				
				
				//�����ϼ���λID
				Long parentOrgId = org.getParentOrgId();
				if(parentOrgId != null){
					//���������
					if(parentOrgId.longValue() < 0L){
						parentOrgId = null;
					}
				}
				//����ϼ���λID
				if(parentOrgId != null){
					if(isParentOrgIdInSuborgsTree(orgService, org.getId().longValue(), parentOrgId.longValue())){
						log.debug("�ϼ���λ���ò���ȷ������ѭ��Ƕ�ס�");
						throw new IllegalArgumentException("�ϼ���λ���ò���ȷ��");
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
						log.debug("����OrgNameChangeEvent: " + org.getName());
					}
					eventDispatcher.dispatchEvent(new OrgNameChangeEvent(tmp, oldName, org.getName()));
				}
				
				if(!EqualsUtils.equals(oldAbbr, org.getAbbr())){
					if(log.isDebugEnabled()){
						log.debug("����OrgAbbrChangeEvent: " + org.getAbbr());
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
					// ��� 
					needAdd = true;
				}else if(oldCategoryId != null && newCategoryId == null){
					needDelete = true;
					//ɾ��
				}else if(oldCategoryId != null && newCategoryId != null && !EqualsUtils.equals(oldCategoryId, newCategoryId)){
					//ɾ��
					needDelete = true;
					//���
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

				String result = "�޸ĵ�λ��Ϣ�ɹ�!";
				Model model = new Model(true, result, null);
				model.setData(tmp);
				return model;
			} else {
				log.warn("doSaveOrUpdateOrg ʱ��ȡ org Ϊ��! ");
				// result = "�޸ĵ�λ��Ϣʧ��!";
				throw new SchemeException("���޸ĵ�λΪ�գ��޸�ʧ�ܡ�");
			}
		} else {
			if (org != null) {
				
				if (org.getCategoryId() != null) {
					Long categoryId = org.getCategoryId();
					OrgCategory category = orgCategoryDao.get(categoryId);
					org.setCategoryName(category.getName());
				}
				
				Org saved = orgService.saveOrg(org);
				//��������Ϣ
				if(groupId != null){
					//entityGroupService.addOrgToGroup(groupId, saved.getId());
					orgGroupService.addOrgToGroup(saved.getId(), groupId);
				}
				
				if(org.getCategoryId() != null){
					
					Long categoryId = org.getCategoryId();
					
					addOrgRole(saved.getId(), categoryId);
					addOrgToGroup(saved.getId(), categoryId);
				}

				String result = "������λ��Ϣ�ɹ�!";
				Model model = new Model(true, result, null);
				model.setData(saved);
				return model;
			} else {
				// result = "��ӵ�λ��Ϣʧ��!";
				throw new SchemeException("������λΪ�գ�����ʧ�ܡ�");
			}
		}
		//return result;
	}
	
	
	private void addOrgRole(Long orgId,Long categoryId){
		// �ѽ�ɫ���������ϵ��
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
		// �ѷ�����������ϵ��
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
		Assert.notNull(org, "��ɾ���ĵ�λ������Ϊ�ա�");
		int row = orgService.deleteOrg(org);
		return "ɾ����  " + row + " ����λ��";
	}

	public Object doDeleteById() throws SchemeException {
		Assert.notNull(id, "��ɾ���ĵ�λID����Ϊ�ա�");
		
		int count = orgService.getSuborgsCount(id);
		if(count > 0){
			return new Model(false, "ɾ��ʧ�ܣ��õ�λ�����¼���λ������ֱ��ɾ����", null);
		}
		
		Org deleting = orgService.getOrg(id);
		int orgs = orgService.deleteOrg(deleting);
//		int orgs = orgService.deleteOrgs(Arrays.asList(id));
		
		String result = "ɾ����  " + orgs + " ����λ��";
		
		//ɾ��֮��ǰ̨UI��λ���ϼ���λ
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
			log.error("ɾ����0����λ��ɾ�������⡣");
			return new Model(false, "ɾ��ʧ��", null);
		}
	}
	
	
	public Object doDeleteList() throws SchemeException {
		Assert.notNull(ids, "��ɾ���ĵ�λID���ϲ���Ϊ�ա�");
		int orgs = orgService.deleteOrgs(ids);
		return "ɾ����  " + orgs + " ����λ��";
	}
	



	public Object doScheme() throws SchemeException {
		return null;
		//return this.doSaveOrUpdateOrg();
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// ����5�������û������û��Ľ�ɫ��
	// �����߿�ʹ��  doAddRole �� doRemoveRole ������һ�����һ������ɾ��һ����ɫ��Ҳ
	// ����ʹ�� doSetOrgRoles һ�α�������ɫ��
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * ��ѯ��ǰ��λ��ӵ�еĽ�ɫ��
	 * ����ָ����λ��id��
	 * ��ʹ�ò��� roleOrderByName ��ȷ����ѯ���������
	 * 
	 * @return
	 * @throws SchemeException
	 * @see OrgService#findRolesByOrgId(Long)
	 */
	public Object viewFindRoles() throws SchemeException{
		Assert.notNull(id, "��λ�� id ����Ϊ�ա�");
		List<Group> list = orgService.findRolesByOrgId(id);
		Collections.sort(list, getRoleComparator());
		return list;
	}
	
	
	/**
	 * ���ҵ�λ�ɷ���Ľ�ɫ��
	 * ���ݵ�¼�û���ɫ�Ƿ����ϵͳ��ɫ��
	 * �÷��������ڵ�¼����á�
	 * ����ָ����λ��id��
	 * ��ʹ�ò��� roleOrderByName ��ȷ����ѯ���������
	 * 
	 * @return
	 * @throws SchemeException
	 * @see OrgService#findRolesByOrgId(Long)
	 */
	public Object viewFindCandidateRoles() throws SchemeException{
		Assert.notNull(id, "��λ�� id ����Ϊ�ա�");
		
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
	 * ��ָ����λ���ָ����ɫ��
	 * ����ָ������ id����λid�� �� roleId����ɫid����
	 * 
	 * @return
	 * @throws SchemeException
	 * @see OrgService#addOrgRole(Long, Long)
	 */
	public Object doAddRole() throws SchemeException{
		Assert.notNull(id, "��λ�� id ����Ϊ�ա�");
		Assert.notNull(roleId, "Ҫ��ӵĽ�ɫ�� id ����Ϊ�ա�");
		
		orgService.addOrgRole(id, roleId);
		return "��ɫ��ӳɹ���";
	}
	
	/**
	 * ɾ��ָ����λ��ָ����ɫ��
	 * ����ָ������ id����λid�� �� roleId����ɫid����
	 * 
	 * 
	 * @return
	 * @throws SchemeException
	 * @see OrgService#removeOrgRole(Long, Long)
	 */
	public Object doRemoveRole() throws SchemeException{
		Assert.notNull(id, "��λ�� id ����Ϊ�ա�");
		Assert.notNull(roleId, "Ҫ��ӵĽ�ɫ�� id ����Ϊ�ա�");
		
		orgService.removeOrgRole(id, roleId);
		return "��ɫɾ���ɹ���";
	}
	
	
	/**
	 * ɾ��ָ����λ��ָ����ɫ���ϡ�
	 * ����ָ������ id����λid�� �� ids����ɫid���ϣ�
	 * 
	 * ��ɾ�������ɫ��
	 * @return
	 * @throws SchemeException
	 * @see OrgService#removeOrgRole(Long, Long)
	 */
	public Object doRemoveRoles() throws SchemeException{
		Assert.notNull(id, "��λ�� id ����Ϊ�ա�");
		Assert.notEmpty(ids, "Ҫɾ���Ľ�ɫ���ϲ���Ϊ�ա�");
		
		
		int n = 0;
		for(Long roleId: ids){
			orgService.removeOrgRole(id, roleId);
			n++;
		}
		
		return "��λ��ɫɾ���ɹ�����ɾ���� " + n + " ����ɫ��";
	}
	
	/**
	 * ��ָ����λ����Ӷ����ɫ��
	 * ����ָ������ id����λid�� �� ids����ɫid���ϣ�
	 * 
	 * @return
	 * @throws SchemeException
	 * @see OrgService#addOrgRoles(Long, List)
	 */
	public Object doAddRoles() throws SchemeException{
		Assert.notNull(id, "��λ�� id ����Ϊ�ա�");
		Assert.notEmpty(ids, "Ҫ��ӵĽ�ɫ���ϲ���Ϊ�ա�");
		
		orgService.addOrgRoles(id, ids);
		return "��λ��ɫ��ӳɹ���������� " + ids.size() + " ����ɫ��";
	}
	
	
	/**
	 * ����ָ����λ�Ľ�ɫ���ϡ�
	 * ����ָ������ id����λid�� �� ids����ɫid���ϣ�
	 * 
	 * 
	 * �÷�����ɾ����λԭ���Ľ�ɫ���Ե�ǰ������ָ���Ľ�ɫ�������´�����λ�Ľ�ɫ��
	 * 
	 * @return
	 * @throws SchemeException
	 * @see OrgService#setOrgRoles(Long, List)
	 */
	public Object doSaveRoles() throws SchemeException{
		Assert.notNull(id, "��λ�� id ����Ϊ�ա�");
		Assert.notEmpty(ids, "Ҫ����Ľ�ɫ���ϲ���Ϊ�ա�");
		
		orgService.setOrgRoles(id, ids);
		return "��λ��ɫ����ɹ���";
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
	 * ��ѯ��λ�б�
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
					//ȥ��ϵͳ����
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
		Assert.notNull(id, "��λ ID ����Ϊ�ա�");

		List<OrgGroup> list = orgGroupService.findGroupsOfOrg(id);//entityGroupService.findExistGroupByOrgId(id);
		Collections.sort(list, ORG_GROUP_COMPARATOR);
		return list;
	}
	
	public Object viewFindCandidateGroups(){
		Assert.notNull(id, "��λ ID ����Ϊ�ա�");
		List<OrgGroup> list = orgGroupService.findGorupNotOfOrg(id);//entityGroupService.findNotExistGroupByOrgId(id);
		Collections.sort(list, ORG_GROUP_COMPARATOR);
		return list;
	}
	
	
	public Object doAddGroup(){
		Assert.notNull(id, "��λ�� id ����Ϊ�ա�");
		Assert.notNull(groupId, "Ҫ��ӵķ���� id ����Ϊ�ա�");
		
		orgGroupService.addOrgToGroup(id, groupId);
		return "��λ����������ӳɹ���";
	}
	
	
	public Object doAddGroups(){
		Assert.notNull(id, "��λ�� id ����Ϊ�ա�");
		Assert.notEmpty(ids, "Ҫ��ӵķ��鼯�ϲ���Ϊ�ա�");
		
		orgGroupService.addOrgToGroups(id, ids);
		return "��λ����������ӳɹ���������� " + ids.size() + " �����顣";
	}
	
	public Object doRemoveGroup(){
		Assert.notNull(id, "��λ�� id ����Ϊ�ա�");
		Assert.notNull(groupId, "Ҫ��ӵķ���� id ����Ϊ�ա�");
		
		orgGroupService.removeOrgFromGroup(id, groupId);
		return "��λ��������ɾ���ɹ���";
	}
	
	public Object doRemoveGroups(){
		Assert.notNull(id, "��λ�� id ����Ϊ�ա�");
		Assert.notEmpty(ids, "Ҫɾ���ķ��鼯�ϲ���Ϊ�ա�");
		
		
		int n = orgGroupService.removeOrgFromGroups(id, ids);
//		for(Long groupId: ids){
//			orgGroupService.deleteOrgOfGroup(id, groupId);
//			n++;
//		}
		
		return "��λ��������ɾ���ɹ�����ɾ���� " + n + " �����顣";
	}
	
	
	public Object viewFindCandidateGroups2(){
		Assert.notNull(id, "��λ ID ����Ϊ�ա�");
		List<OrgGroup> list = orgCategoryToOrgGroupDao.findGroupsNotOfOrgCategory(id);
		return list;
	}
	
	public Object viewFindCandidateRoles2(){
		Assert.notNull(id, "��λ�� id ����Ϊ�ա�");
		List<Group> list = orgCategoryToRoleDao.findRolesNotOfOrgCategory(id);
		return list;
	}
}
