/*
 * $Id: ClerkServiceImpl.java 6375 2014-04-15 10:22:57Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.annotation.Queryable;
import org.opoo.apps.cache.CacheFactory;
import org.opoo.apps.event.v2.EventDispatcherAware;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.User;
import org.opoo.apps.security.User.OnlineStatus;
import org.opoo.apps.security.UserManager;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.CriterionBuffer;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.ndao.support.ResultFilterUtils;
import org.opoo.util.StringUtils;
import org.springframework.security.annotation.Secured;
import org.springframework.security.userdetails.UserDetails;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.cache.ClerkCache;
import cn.redflagsoft.base.cache.impl.NullClerkCache;
import cn.redflagsoft.base.dao.ClerkDao;
import cn.redflagsoft.base.dao.ClerkGroupDao;
import cn.redflagsoft.base.event2.ClerkEvent;
import cn.redflagsoft.base.event2.ClerkNameChangeEvent;
import cn.redflagsoft.base.event2.EventSource;
import cn.redflagsoft.base.event2.OrgAbbrChangeListener;
import cn.redflagsoft.base.security.AuthUtils;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.OrgService;
import cn.redflagsoft.base.util.EqualsUtils;
import cn.redflagsoft.base.vo.UserClerkVO;

import com.google.common.collect.Lists;

public class ClerkServiceImpl extends EventSource implements ClerkService, EventDispatcherAware,OrgAbbrChangeListener {
	public static final Log log = LogFactory.getLog(ClerkServiceImpl.class);
	private ClerkDao clerkDao;
	private ClerkGroupDao clerkGroupDao;
	private ClerkCache clerkCache = new NullClerkCache();
	private UserManager userManager;
	private OrgService orgService;
	
	
	public ClerkDao getClerkDao() {
		return clerkDao;
	}

	public void setClerkDao(ClerkDao clerkDao) {
		this.clerkDao = clerkDao;
	}

	public ClerkCache getClerkCache() {
		return clerkCache;
	}

	public void setClerkCache(ClerkCache clerkCache) {
		this.clerkCache = clerkCache;
	}

	public ClerkGroupDao getClerkGroupDao() {
		return clerkGroupDao;
	}

	public void setClerkGroupDao(ClerkGroupDao clerkGroupDao) {
		this.clerkGroupDao = clerkGroupDao;
	}
	
	public OrgService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}

	public Clerk getClerk(Long id) {
		if(id == null){
			return null;
		}
		if(id.longValue() <= 0L){
			return null;
		}
		
//		if(AuthUtils.isInternalUser(id)){
//			return null;
//		}
		
		Clerk clerk = clerkCache.getClerkFromCache(id);
		if(clerk == null) {
			log.debug("��ѯclerk��" + id);
			clerk = clerkDao.get(id);
			log.debug("��ѯclerk��ϣ�" + clerk);
			if(clerk != null) {
				log.debug("����clerk��" + clerk);
				clerkCache.putClerkIntoCache(clerk);
				log.debug("����clerk��ϣ�" + clerk);
			} else {
				log.debug("Clerk���󲻴��ڣ�" + id);
			}
		}else{
//			if(log.isDebugEnabled()){
//				log.debug("Using cached clerk: " + clerk);
//			}
		}
		return clerk;
	}
	
	/**
	 * ����ɾ�������˺ŵ���Ա��
	 * 
	 * @param id
	 * @return
	 * @deprecated ����ʱHIBERNATE����
	 */
	public int removeClerk(Long id){
		UserDetails user = Application.getContext().getUserManager().loadUserByUserId(id);
		if(user != null){
			throw new IllegalArgumentException("��Ա�Ѿ��������˺���Ϣ������������ɾ����" +
					"��ϵͳ����Ա���û������н���ɾ��������");
		}
		clerkCache.removeFromCache(id);
		return clerkDao.remove(id);
	}

	public int deleteClerk(Clerk clerk) {
//		if(clerk == null){
//			log.warn("deleteClerk ʱ Clerk = null, return delete line = 0 ...");
//			return 0;
//		}
		
		UserDetails user = Application.getContext().getUserManager().loadUserByUserId(clerk.getId());
		if(user != null){
			throw new IllegalArgumentException("��Ա�Ѿ��������˺���Ϣ������������ɾ����" +
					"��ϵͳ����Ա���û������н���ɾ��������");
		}
		clerkCache.removeFromCache(clerk.getId());
		clerkGroupDao.removeByClerkId(clerk.getId());
		
		dispatchEvent(new ClerkEvent(ClerkEvent.Type.DELETED, clerk));
		return clerkDao.delete(clerk);
	}
	
	public int deleteClerks(List<Long> ids) {
//		if(ids == null || ids.isEmpty()) {
//			log.warn("deleteClerk ʱ ids = null, return delete line = 0 ...");
//			return 0;
//		}
		int i=0;
		for(Long id : ids) {
			//clerkCache.removeFromCache(id);
			Clerk clerk = getClerk(id);
			i += deleteClerk(clerk);//clerkDao.delete(clerkDao.get(id));
			//i += removeClerk(id);
		}
		return i;//clerkDao.remove(ids.toArray(new Long[0]));
	}

	public Clerk updateClerk(Clerk clerk) {
		if(clerk == null){
			log.warn("updateClerk ʱ Clerk = null ...");
			return null;
		}	
		
		clerkCache.removeFromCache(clerk.getId());
		
		Clerk old = clerkDao.get(clerk.getId());
		String oldName = old.getName();
		if (log.isDebugEnabled()) {
			log.debug("Old,New,OldValue,NewValue: " + old + ", " + clerk + ", " + oldName + ", "
					+ clerk.getName());
		}
		
		clerk = clerkDao.update(clerk);
		
		if(!EqualsUtils.equals(oldName, clerk.getName())){
			dispatchEvent(new ClerkNameChangeEvent(clerk, oldName, clerk.getName()));
			//firePropertyChange(clerk, "name", oldName, clerk.getName());
		}
		dispatchEvent(new ClerkEvent(ClerkEvent.Type.UPDATED, clerk));
		return clerk;
	}
	
	@Queryable
	public List<Clerk> findClerks(ResultFilter filter){
		final List<Long> ids = clerkDao.findClerkIds(filter);
		return buildClerkList(ids, null, false);
		
//		//���ö��󣬲����ظ�ǰ̨
//		ids.remove(new Long(Clerk.CLERK_ID_ADMINISTRATOR));
//		ids.remove(new Long(Clerk.CLERK_ID_SUPERVISOR));
//		
//		if(ids.isEmpty()){
//			return Collections.<Clerk>emptyList();
//		}
//		
//		return new AbstractList<Clerk>(){
//			@Override
//			public Clerk get(int index) {
//				Long id = ids.get(index);
//				return getClerk(id);
//			}
//			@Override
//			public int size() {
//				return ids.size();
//			}
//			/* (non-Javadoc)
//			 * @see java.util.AbstractList#remove(int)
//			 */
//			@Override
//			public Clerk remove(int index) {
//				Clerk clerk = get(index);
//				ids.remove(index);
//				return clerk;
//			}
//		};
	}

	public Clerk saveClerk(Clerk clerk) {
		Clerk tmp = clerkCache.getClerkFromCache(clerk.getId());
		if(tmp != null) {
			clerkCache.removeFromCache(clerk.getId());
			tmp = null;
		}
		tmp = clerkDao.save(clerk);
		if(tmp != null){
			clerkCache.putClerkIntoCache(clerk);
		}
		
		dispatchEvent(new ClerkEvent(ClerkEvent.Type.CREATED, clerk));
		return tmp;
	}

	/**
	 * ֻ��RFSA��Admin���Է����������
	 * @param filter
	 * @return
	 */
	@Queryable
	@Secured({"ROLE_ADMIN","ROLE_ADMINISTRATOR"})
	public List<UserClerkVO> findPageableUserClerkVOs(ResultFilter filter) {
		if(filter == null || !filter.isPageable()){
			throw new IllegalArgumentException("���������ҳ����");
		}else{
			//log.debug("��ѯ��ҳ��" + filter.getFirstResult() + ", " + filter.getMaxResults());
		}
		List<UserClerkVO> list = findUserClerkVOs(filter);
		int count = clerkDao.getUserClerkUsernameCount(filter);
		return new PageableList<UserClerkVO>(list, filter.getFirstResult(), filter.getMaxResults(), count);
	}
	
	/**
	 * ֻ��RFSA��Admin���Է����������
	 * @param filter
	 * @return
	 */
	@Queryable
	@Secured({"ROLE_ADMIN","ROLE_ADMINISTRATOR"})
	public List<UserClerkVO> findPageableUserClerkVOsAdmin(ResultFilter filter) {
		if(filter == null || !filter.isPageable()){
			throw new IllegalArgumentException("���������ҳ����");
		}else{
			//log.debug("��ѯ��ҳ��" + filter.getFirstResult() + ", " + filter.getMaxResults());
		}
		List<UserClerkVO> list = findUserClerkVOs(filter);
		int count = clerkDao.getUserClerkUsernameCount(filter);
		return new PageableList<UserClerkVO>(list, filter.getFirstResult(), filter.getMaxResults(), count);
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ClerkService#findUserClerkVOs(org.opoo.ndao.support.ResultFilter)
	 */
	@Queryable
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_ADMINISTRATOR"})
	public List<UserClerkVO> findUserClerkVOs(ResultFilter filter) {
		//return clerkDao.findUserClerkVOs(filter);
		if(filter == null){
			filter = ResultFilter.createEmptyResultFilter();
		}
		OnlineStatus onlineStatus = null;
		if(filter != null){
			Criterion criterion = filter.getCriterion();
			if(criterion != null){
				String qs = criterion.toString();
				Object[] values = criterion.getValues();
				
				log.debug("��ѯ�û�ְԱ��" + qs);
				String exp = "a.onlineStatus=?";
				int position = -1;
				
				position = qs.indexOf(exp);
				if(position == -1){
					exp = "a.onlineStatus = ?";
					position = qs.indexOf(exp);
				}
				
				
				//�� OnlineStatus �Ĳ�����
				if(position != -1){
					int count = 0;
					if(position > 0){
						String base = qs.substring(0, position);
						count = StringUtils.countUnquoted(base, '?');
					}
					
					Object object = values[count];
					if(object instanceof OnlineStatus){
						onlineStatus = (OnlineStatus) object;
					}else{
						onlineStatus = OnlineStatus.valueOf(object.toString().trim());
					}
//					onlineStatus = OnlineStatus.valueOf(((String)values[count]).trim());
					
					String sql = qs.replace(exp, "a.userId<>?");
					values[count] = -1000L;
					
					if(log.isDebugEnabled()){
						log.debug("�µĲ�ѯ���Ϊ��" + sql);
						log.debug("�滻����λ�ã�" + count + "�� ԭֵ�� " + onlineStatus);
						log.debug("���¶����ѯ������");
					}
					
//					ResultFilter newfilter = new ResultFilter();
//					newfilter.setCriterion(Restrictions.sql(sql, values));
//					newfilter.setFirstResult(filter.getFirstResult());
//					newfilter.setMaxResults(filter.getMaxResults());
//					if(filter.getOrder() != null){
//						newfilter.setOrder(filter.getOrder());
//					}
//					filter = newfilter;
					
					filter.setCriterion(Restrictions.sql(sql, values));
				}
			}
		}
		
		List<String> usernames = clerkDao.findUserClerkUsernames(filter);
		
		if(log.isDebugEnabled()){
			if(filter != null && filter.getFirstResult() >= 0 && filter.getMaxResults() > 0){
				log.debug("findUserClerkVOs()��ҳ��ѯ����: " + filter.getFirstResult() + ", " + filter.getMaxResults()
						+ " -- " + filter.getCriterion());
				for(String username: usernames){
					System.out.println("[DEBUG] " + username);
				}
			}
		}
		
		List<UserClerkVO> vos = new ArrayList<UserClerkVO>();
		//boolean isAdministrator = AuthUtils.isAdministrator();
		boolean isAdmin = AuthUtils.isAdmin();
		for (String string : usernames) {
			User user = (User) getUserManager().loadUserByUsername(string);
			if(onlineStatus != null && onlineStatus != user.getOnlineStatus()){
				continue;
			}
			
			Clerk clerk = getClerk(user.getUserId());
			
			
			//ϵͳ����Ա���ܲ�ѯ��ϵͳ������ĳ�Ա
//			if(isAdministrator && clerk.getEntityID() != null 
//					&& clerk.getEntityID().longValue() <= Org.MAX_SYS_ID){
//				continue;
//			}
			
			//��RFSA�޷����������û���clerk��RFSA��ADMIN
			if(!isAdmin && AuthUtils.isSystemUser(clerk)/* clerk.getId().longValue() < 100L*/){
				continue;
			}
			
			//�ڲ��û����ɹ������ɼ�
			if(AuthUtils.isInvisible(clerk)){
				continue;
			}
			
			UserClerkVO vo = new UserClerkVO(user, clerk);
			vos.add(vo);
		}
		return vos;
	}
	
	
	/**
	 * @return the userManager
	 */
	public UserManager getUserManager() {
		return userManager;
	}

	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	
	/**
	 * ���������������ͨ�ò�ѯ
	 * @param name
	 * @return
	 * /invoke/clerkService/testQueryable1.jspa?name=
	 */
	@Queryable(argNames={"name"})
	public String testQueryable1(String s){
		return "�����name��: " + s;
	}
	
	/**
	 * ͨ�ò�ѯ����ʱӦ��ʧ�ܵķ�����û��ע��Queryable��
	 * @param filter
	 * @return
	 */
	public String testQueryable2(ResultFilter filter){
		return "ͨ�ò�ѯ����ʱӦ��ʧ�ܵķ�����";
	}
	
	
	
	public static void main(String[] args){
		String name = "��";
		String username = "r";
		OnlineStatus onlineStatus = OnlineStatus.ONLINE;
		Boolean enabled = Boolean.TRUE;
		Date lastLoginTimeFrom  = new Date();
		Date lastLoginTimeTo = new Date();
		
		CriterionBuffer buffer = new CriterionBuffer();
		if(org.apache.commons.lang.StringUtils.isNotBlank(name)){
			log.debug("��ѯ������" + name);
			buffer.and(Restrictions.like("b.name", "%" + name + "%"));
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(username)){
			log.debug("��ѯ�û�����" + username);
			buffer.and(Restrictions.like("a.username", "%" + username + "%"));
		}
		if(onlineStatus != null){
			log.debug("��ѯ����״̬��" + onlineStatus);
			buffer.and(Restrictions.eq("a.onlineStatus", onlineStatus));
		}
		if(enabled != null){
			log.debug("��ѯ��Ч״̬��" + enabled);
			buffer.and(Restrictions.eq("a.enabled", enabled));
		}
		if(lastLoginTimeFrom != null){
			log.debug("����¼ʱ�䣨��ʼ����" + lastLoginTimeFrom);
			buffer.and(Restrictions.ge("a.lastLoginTime", lastLoginTimeFrom));
		}
		if(lastLoginTimeTo != null){
			log.debug("����¼ʱ�䣨��ֹ����" + lastLoginTimeTo);
			buffer.and(Restrictions.le("a.lastLoginTime", lastLoginTimeTo));
		}
		
		System.out.println(buffer.toCriterion());
		
		///////////////////////
		ResultFilter filter = null;
		if(filter == null){
			filter = ResultFilter.createEmptyResultFilter();
		}
		filter.setCriterion(buffer.toCriterion());
		OnlineStatus onlineStatus2 = null;
		if(filter != null){
			Criterion criterion = filter.getCriterion();
			if(criterion != null){
				String qs = criterion.toString();
				Object[] values = criterion.getValues();
				
				log.debug("��ѯ�û�ְԱ��" + qs);
				String exp = "a.onlineStatus=?";
				int position = -1;
				
				position = qs.indexOf(exp);
				if(position == -1){
					exp = "a.onlineStatus = ?";
					position = qs.indexOf(exp);
				}
				
				
				//�� OnlineStatus �Ĳ�����
				if(position != -1){
					int count = 0;
					if(position > 0){
						String base = qs.substring(0, position);
						count = StringUtils.countUnquoted(base, '?');
					}
					Object object = values[count];
					if(object instanceof OnlineStatus){
						onlineStatus2 = (OnlineStatus) object;
					}else{
						onlineStatus2 = OnlineStatus.valueOf(object.toString().trim());
					}
					
//					onlineStatus2 = OnlineStatus.valueOf(((String)values[count]).trim());
					String sql = qs.replace(exp, "a.userId<>?");
					values[count] = -1000L;
					
					if(log.isDebugEnabled()){
						log.debug("�µĲ�ѯ���Ϊ��" + sql);
						log.debug("�滻����λ�ã�" + count + "�� ԭֵ�� " + onlineStatus2);
						log.debug("���¶����ѯ������");
					}
					
//					ResultFilter newfilter = new ResultFilter();
//					newfilter.setCriterion(Restrictions.sql(sql, values));
//					newfilter.setFirstResult(filter.getFirstResult());
//					newfilter.setMaxResults(filter.getMaxResults());
//					if(filter.getOrder() != null){
//						newfilter.setOrder(filter.getOrder());
//					}
//					filter = newfilter;
					
					filter.setCriterion(Restrictions.sql(sql, values));
				}
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		String qs = "select a from b where a=? and b=? and c=? and a.onlineStaus=?  and d=?";
		
		log.debug("��ѯ�û�ְԱ��" + qs);
		String exp = "a.onlineStaus=?";
		int position = -1;
		
		position = qs.indexOf(exp);
		if(position == -1){
			exp = "a.onlineStaus = ?";
			position = qs.indexOf(exp);
		}
		
		System.out.println(position);
		
		//�� OnlineStatus �Ĳ�����
		if(position != -1){
			String base = qs.substring(0, position);
			int count = StringUtils.countUnquoted(base, '?');
			System.out.println(base);
			System.out.println(count);
			
			String sql = qs.replace(exp, "a.userId<>?");
			System.out.println(sql);
			
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ClerkService#findPageableClerks(org.opoo.ndao.support.ResultFilter)
	 */
	@Queryable
	public PageableList<Clerk> findPageableClerks(ResultFilter filter) {
		final PageableList<Long> ids = clerkDao.findPageableClerkIds(filter);
//		
//		
//		
//		//���ö��󣬲����ظ�ǰ̨
//		ids.remove(new Long(Clerk.CLERK_ID_ADMINISTRATOR));
//		ids.remove(new Long(Clerk.CLERK_ID_SUPERVISOR));
//		
//		List<Clerk> list = null;
//		if(ids.isEmpty()){
//			//return new PageableList<Clerk>(Collections.<Clerk>emptyList(), 
//			//		ids.getStartIndex(), ids.getPageSize(), ids.getItemCount());
//			list = Collections.<Clerk>emptyList();
//		}else{
//			list = new AbstractList<Clerk>(){
//				@Override
//				public Clerk get(int index) {
//					Long id = ids.get(index);
//					return getClerk(id);
//				}
//				@Override
//				public int size() {
//					return ids.size();
//				}
//				/* (non-Javadoc)
//				 * @see java.util.AbstractList#remove(int)
//				 */
//				@Override
//				public Clerk remove(int index) {
//					Clerk clerk = get(index);
//					ids.remove(index);
//					return clerk;
//				}
//			};
//		}
		List<Clerk> list = buildClerkList(ids, null, false);
		return new PageableList<Clerk>(list, ids.getStartIndex(), ids.getPageSize(), ids.getItemCount());
	}

	public List<Clerk> findClerks(Long orgId, String code, String name, boolean includeSuborgClerks) {
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setOrder(Order.asc("displayOrder"));
		if(org.apache.commons.lang.StringUtils.isNotBlank(code)){
			filter = ResultFilterUtils.append(filter, Restrictions.like("code", "%" + code.trim() + "%"));
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(name)){
			filter = ResultFilterUtils.append(filter, Restrictions.like("name", "%" + name.trim() + "%"));
		}

		if(orgId != null && orgId.longValue() < 0L){
			orgId = null;
		}
		if(orgId == null) {
			if(includeSuborgClerks){
				//return findClerks(filter);
			}else{
				return Collections.emptyList();
			}
		}else{
			if(includeSuborgClerks){
				List<Long> orgIds = new ArrayList<Long>();
				addOrgAndSuborgIds(orgIds, orgId);
				filter = ResultFilterUtils.append(filter, Restrictions.in("entityID", orgIds));
				//return findClerks(filter);
			}else{
				filter = ResultFilterUtils.append(filter, Restrictions.eq("entityID", orgId));
				//return findClerks(filter);
			}
		}
		return findClerks(filter);
	}

	private void addOrgAndSuborgIds(List<Long> orgIds, Long orgId){
		//�ӱ���ID
		orgIds.add(orgId);
		List<Long> ids = orgService.findSuborgIds(orgId);
		for(Long id: ids){
			addOrgAndSuborgIds(orgIds, id);
		}
	}

	public List<Clerk> findClerks(Criterion base, Order order, Long orgId, boolean includeSuborgClerks) {
		if(order == null){
			order = Order.asc("displayOrder");
		}
		ResultFilter filter = new ResultFilter(base, order);
		
		if(orgId != null && orgId.longValue() < 0L){
			orgId = null;
		}
		if(orgId == null) {
			if(includeSuborgClerks){
				//return findClerks(filter);
			}else{
				return Collections.emptyList();
			}
		}else{
			if(includeSuborgClerks){
				List<Long> orgIds = new ArrayList<Long>();
				addOrgAndSuborgIds(orgIds, orgId);
				filter = ResultFilterUtils.append(filter, Restrictions.in("entityID", orgIds));
				//return findClerks(filter);
			}else{
				filter = ResultFilterUtils.append(filter, Restrictions.eq("entityID", orgId));
				//return findClerks(filter);
			}
		}
		return findClerks(filter);
	}
	
	public List<Clerk> findClerksInGroup(long clerkGroupId, Criterion base, Order order, Long orgId, boolean includeSuborgClerks){
		ResultFilter filter = new ResultFilter(base, order);
		if(orgId != null && orgId.longValue() < 0L){
			orgId = null;
		}
		//������λ
		if(orgId == null) {
			if(includeSuborgClerks){
				//���Ӳ��ŵĹ���
			}else{
				return Collections.emptyList();
			}
		}else{
			if(includeSuborgClerks){
				List<Long> orgIds = new ArrayList<Long>();
				addOrgAndSuborgIds(orgIds, orgId);
				filter = ResultFilterUtils.append(filter, Restrictions.in("entityID", orgIds));
			}else{
				filter = ResultFilterUtils.append(filter, Restrictions.eq("entityID", orgId));
			}
		}
		
		//���е���Աid����
		List<Long> clerkIdsInGroup = clerkGroupDao.findClerkIdsInGroup(clerkGroupId);
		if(clerkIdsInGroup.isEmpty()){
			return Collections.emptyList();
		}
		//���ϼ��ϵ�������ѯclerk����Ϊ���ܻ�������������Ҫ��ѯclerk��
		filter = ResultFilterUtils.append(filter, Restrictions.in("id", clerkIdsInGroup));
		List<Long> idsInClerk = clerkDao.findClerkIds(filter);
		if(idsInClerk.isEmpty()){
			return Collections.emptyList();
		}
		
		//return makeClerkList(clerkIdsInGroup, idsInClerk);
		return buildClerkList(idsInClerk, clerkIdsInGroup, true);
	}

//	/**
//	 * ���������е�˳�����С�
//	 * 
//	 * @param clerkIdsInGroup �����е���Աid����
//	 * @param idsInClerk
//	 * @return
//	 */
//	private List<Clerk> makeClerkList(List<Long> clerkIdsInGroup, List<Long> idsInClerk) {
//		List<Clerk> clerks = new ArrayList<Clerk>();
//		for(Long id: clerkIdsInGroup){
//			if(idsInClerk.contains(id)){
//				Clerk clerk = getClerk(id);
//				//idsInClerk�Ǵ�clerk���ѯ�����ģ����Դ˴���clerk����Ӧ�ö���Ϊnull
//				//Assert.notNull(clerk);
//				clerks.add(clerk);
//			}
//		}
//		return clerks;
//	}
	

	public List<UserClerkVO> findUserClerkVOs(Criterion base, Order order, Long orgId, boolean includeSuborgUsers) {
		if(order == null){
			order = Order.asc("b.displayOrder");
		}
		ResultFilter filter = new ResultFilter(base, order);

		if(orgId != null && orgId.longValue() < 0L){
			orgId = null;
		}
		if(orgId == null) {
			if(includeSuborgUsers){
				//return findUserClerkVOs(filter);
			}else{
				return Collections.emptyList();
			}
		}else{
			if(includeSuborgUsers){
				List<Long> orgIds = new ArrayList<Long>();
				addOrgAndSuborgIds(orgIds, orgId);
				filter = ResultFilterUtils.append(filter, Restrictions.in("b.entityID", orgIds));
				//return findUserClerkVOs(filter);
			}else{
				filter = ResultFilterUtils.append(filter, Restrictions.eq("b.entityID", orgId));
				//return findUserClerkVOs(filter);
			}
		}
		
		return findUserClerkVOs(filter);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ClerkService#buildClerkList(java.util.List, java.util.List, boolean)
	 */
	public List<Clerk> buildClerkList(List<Long> clerkIds, List<Long> orderClerkIds, boolean includeSystemClerks) {
		if(clerkIds == null){
			return null;
		}
		if(!includeSystemClerks){
			//���ö��󣬲����ظ�ǰ̨
//			clerkIds.remove(new Long(Clerk.CLERK_ID_ADMIN));
//			clerkIds.remove(new Long(Clerk.CLERK_ID_RFSA));
			removeSystemClerks(clerkIds);
		}
		if(clerkIds.isEmpty()){
			return Collections.emptyList();
		}
		
		List<Clerk> clerks = new ArrayList<Clerk>();
		boolean useOrderFromOrderClerkIds = orderClerkIds != null && !orderClerkIds.isEmpty();
		if(useOrderFromOrderClerkIds){
			for(Long id: orderClerkIds){
				if(clerkIds.contains(id)){
					addClerkToList(clerks, id, includeSystemClerks);
				}
			}
		}else{
			for(Long id: clerkIds){
				addClerkToList(clerks, id, includeSystemClerks);
			}
		}
		return clerks;
	}
	
	private void addClerkToList(List<Clerk> clerks, Long clerkId, boolean includeSystemClerks){
		Clerk clerk = getClerk(clerkId);
		//idsInClerk�Ǵ�clerk���ѯ�����ģ����Դ˴���clerk����Ӧ�ö���Ϊnull
		//Assert.notNull(clerk);
		if(clerk != null){
			if(!includeSystemClerks && clerk.getEntityID() != null && clerk.getEntityID().longValue() <= Org.MAX_SYS_ID){
				log.debug("������ϵͳ���ŵ��û���" + clerk);
			}else{
				clerks.add(clerk);
			}
		}
	}
	

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.event2.OrgAbbrChangeListener#orgAbbrChange(cn.redflagsoft.base.bean.Org, java.lang.String, java.lang.String)
	 */
	public void orgAbbrChange(Org org, String oldAbbr, String newAbbr) {
		if(log.isDebugEnabled()){
			log.debug("��λ��Ϣ�����˱仯��ͬ��������Ա�еĵ�λ��ƣ�" + oldAbbr + " --> " + newAbbr);
		}
		int rows = clerkDao.updateEntityName(org.getId(), newAbbr);
		
		//������
		if(rows > 0){
			//clerkCache.clear();
			CacheFactory.clearCaches();
			if(log.isDebugEnabled()){
				log.debug("��Ա������λ��Ϣ�����仯��������������Ⱥ�Ļ���");
				log.debug("���û�������λ(dutyEntityName) ��" + rows	+ "�� ��");
			}
		}
	}
	
	
	static void removeSystemClerks(List<Long> clerkIds){
		List<Long> systemClerkIds = Lists.newArrayList();
		for(Long clerkId: clerkIds){
			if(AuthUtils.isSystemUser(clerkId)){
				systemClerkIds.add(clerkId);
			}
		}
		clerkIds.removeAll(systemClerkIds);
		
//		clerkIds.remove(new Long(Clerk.CLERK_ID_ADMIN));
//		clerkIds.remove(new Long(Clerk.CLERK_ID_RFSA));
//		clerkIds.remove(new Long(Clerk.CLERK_ID_SCHEDULER));
//		clerkIds.remove(new Long(Clerk.CLERK_ID_SUPERVISOR));
//		clerkIds.remove(new Long(Clerk.CLERK_ID_WORKFLOW));
	}
}
