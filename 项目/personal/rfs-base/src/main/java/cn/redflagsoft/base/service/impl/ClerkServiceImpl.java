/*
 * $Id: ClerkServiceImpl.java 6375 2014-04-15 10:22:57Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
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
			log.debug("查询clerk：" + id);
			clerk = clerkDao.get(id);
			log.debug("查询clerk完毕：" + clerk);
			if(clerk != null) {
				log.debug("缓存clerk：" + clerk);
				clerkCache.putClerkIntoCache(clerk);
				log.debug("缓存clerk完毕：" + clerk);
			} else {
				log.debug("Clerk对象不存在：" + id);
			}
		}else{
//			if(log.isDebugEnabled()){
//				log.debug("Using cached clerk: " + clerk);
//			}
		}
		return clerk;
	}
	
	/**
	 * 不能删除关联账号的人员。
	 * 
	 * @param id
	 * @return
	 * @deprecated 调用时HIBERNATE出错
	 */
	public int removeClerk(Long id){
		UserDetails user = Application.getContext().getUserManager().loadUserByUserId(id);
		if(user != null){
			throw new IllegalArgumentException("人员已经关联了账号信息，不能在这里删除，" +
					"请系统管理员在用户管理中进行删除操作。");
		}
		clerkCache.removeFromCache(id);
		return clerkDao.remove(id);
	}

	public int deleteClerk(Clerk clerk) {
//		if(clerk == null){
//			log.warn("deleteClerk 时 Clerk = null, return delete line = 0 ...");
//			return 0;
//		}
		
		UserDetails user = Application.getContext().getUserManager().loadUserByUserId(clerk.getId());
		if(user != null){
			throw new IllegalArgumentException("人员已经关联了账号信息，不能在这里删除，" +
					"请系统管理员在用户管理中进行删除操作。");
		}
		clerkCache.removeFromCache(clerk.getId());
		clerkGroupDao.removeByClerkId(clerk.getId());
		
		dispatchEvent(new ClerkEvent(ClerkEvent.Type.DELETED, clerk));
		return clerkDao.delete(clerk);
	}
	
	public int deleteClerks(List<Long> ids) {
//		if(ids == null || ids.isEmpty()) {
//			log.warn("deleteClerk 时 ids = null, return delete line = 0 ...");
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
			log.warn("updateClerk 时 Clerk = null ...");
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
		
//		//内置对象，不返回给前台
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
	 * 只有RFSA和Admin可以访问这个方法
	 * @param filter
	 * @return
	 */
	@Queryable
	@Secured({"ROLE_ADMIN","ROLE_ADMINISTRATOR"})
	public List<UserClerkVO> findPageableUserClerkVOs(ResultFilter filter) {
		if(filter == null || !filter.isPageable()){
			throw new IllegalArgumentException("必须包含分页参数");
		}else{
			//log.debug("查询分页：" + filter.getFirstResult() + ", " + filter.getMaxResults());
		}
		List<UserClerkVO> list = findUserClerkVOs(filter);
		int count = clerkDao.getUserClerkUsernameCount(filter);
		return new PageableList<UserClerkVO>(list, filter.getFirstResult(), filter.getMaxResults(), count);
	}
	
	/**
	 * 只有RFSA和Admin可以访问这个方法
	 * @param filter
	 * @return
	 */
	@Queryable
	@Secured({"ROLE_ADMIN","ROLE_ADMINISTRATOR"})
	public List<UserClerkVO> findPageableUserClerkVOsAdmin(ResultFilter filter) {
		if(filter == null || !filter.isPageable()){
			throw new IllegalArgumentException("必须包含分页参数");
		}else{
			//log.debug("查询分页：" + filter.getFirstResult() + ", " + filter.getMaxResults());
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
				
				log.debug("查询用户职员：" + qs);
				String exp = "a.onlineStatus=?";
				int position = -1;
				
				position = qs.indexOf(exp);
				if(position == -1){
					exp = "a.onlineStatus = ?";
					position = qs.indexOf(exp);
				}
				
				
				//有 OnlineStatus 的参数。
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
						log.debug("新的查询语句为：" + sql);
						log.debug("替换参数位置：" + count + "， 原值： " + onlineStatus);
						log.debug("重新定义查询条件。");
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
				log.debug("findUserClerkVOs()分页查询条件: " + filter.getFirstResult() + ", " + filter.getMaxResults()
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
			
			
			//系统管理员不能查询出系统管理组的成员
//			if(isAdministrator && clerk.getEntityID() != null 
//					&& clerk.getEntityID().longValue() <= Org.MAX_SYS_ID){
//				continue;
//			}
			
			//非RFSA无法看见内置用户和clerk、RFSA、ADMIN
			if(!isAdmin && AuthUtils.isSystemUser(clerk)/* clerk.getId().longValue() < 100L*/){
				continue;
			}
			
			//内部用户不可管理，不可见
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
	 * 这个方法用来测试通用查询
	 * @param name
	 * @return
	 * /invoke/clerkService/testQueryable1.jspa?name=
	 */
	@Queryable(argNames={"name"})
	public String testQueryable1(String s){
		return "输入的name是: " + s;
	}
	
	/**
	 * 通用查询调用时应当失败的方法，没有注释Queryable。
	 * @param filter
	 * @return
	 */
	public String testQueryable2(ResultFilter filter){
		return "通用查询调用时应当失败的方法。";
	}
	
	
	
	public static void main(String[] args){
		String name = "无";
		String username = "r";
		OnlineStatus onlineStatus = OnlineStatus.ONLINE;
		Boolean enabled = Boolean.TRUE;
		Date lastLoginTimeFrom  = new Date();
		Date lastLoginTimeTo = new Date();
		
		CriterionBuffer buffer = new CriterionBuffer();
		if(org.apache.commons.lang.StringUtils.isNotBlank(name)){
			log.debug("查询姓名：" + name);
			buffer.and(Restrictions.like("b.name", "%" + name + "%"));
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(username)){
			log.debug("查询用户名：" + username);
			buffer.and(Restrictions.like("a.username", "%" + username + "%"));
		}
		if(onlineStatus != null){
			log.debug("查询在线状态：" + onlineStatus);
			buffer.and(Restrictions.eq("a.onlineStatus", onlineStatus));
		}
		if(enabled != null){
			log.debug("查询有效状态：" + enabled);
			buffer.and(Restrictions.eq("a.enabled", enabled));
		}
		if(lastLoginTimeFrom != null){
			log.debug("最后登录时间（开始）：" + lastLoginTimeFrom);
			buffer.and(Restrictions.ge("a.lastLoginTime", lastLoginTimeFrom));
		}
		if(lastLoginTimeTo != null){
			log.debug("最后登录时间（截止）：" + lastLoginTimeTo);
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
				
				log.debug("查询用户职员：" + qs);
				String exp = "a.onlineStatus=?";
				int position = -1;
				
				position = qs.indexOf(exp);
				if(position == -1){
					exp = "a.onlineStatus = ?";
					position = qs.indexOf(exp);
				}
				
				
				//有 OnlineStatus 的参数。
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
						log.debug("新的查询语句为：" + sql);
						log.debug("替换参数位置：" + count + "， 原值： " + onlineStatus2);
						log.debug("重新定义查询条件。");
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
		
		log.debug("查询用户职员：" + qs);
		String exp = "a.onlineStaus=?";
		int position = -1;
		
		position = qs.indexOf(exp);
		if(position == -1){
			exp = "a.onlineStaus = ?";
			position = qs.indexOf(exp);
		}
		
		System.out.println(position);
		
		//有 OnlineStatus 的参数。
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
//		//内置对象，不返回给前台
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
		//加本级ID
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
		//顶级单位
		if(orgId == null) {
			if(includeSuborgClerks){
				//不加部门的过滤
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
		
		//组中的人员id集合
		List<Long> clerkIdsInGroup = clerkGroupDao.findClerkIdsInGroup(clerkGroupId);
		if(clerkIdsInGroup.isEmpty()){
			return Collections.emptyList();
		}
		//加上集合的条件查询clerk表，因为可能还有其他条件需要查询clerk表
		filter = ResultFilterUtils.append(filter, Restrictions.in("id", clerkIdsInGroup));
		List<Long> idsInClerk = clerkDao.findClerkIds(filter);
		if(idsInClerk.isEmpty()){
			return Collections.emptyList();
		}
		
		//return makeClerkList(clerkIdsInGroup, idsInClerk);
		return buildClerkList(idsInClerk, clerkIdsInGroup, true);
	}

//	/**
//	 * 必须以组中的顺序排列。
//	 * 
//	 * @param clerkIdsInGroup 在组中的人员id集合
//	 * @param idsInClerk
//	 * @return
//	 */
//	private List<Clerk> makeClerkList(List<Long> clerkIdsInGroup, List<Long> idsInClerk) {
//		List<Clerk> clerks = new ArrayList<Clerk>();
//		for(Long id: clerkIdsInGroup){
//			if(idsInClerk.contains(id)){
//				Clerk clerk = getClerk(id);
//				//idsInClerk是从clerk表查询出来的，所以此处的clerk对象应该都不为null
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
			//内置对象，不返回给前台
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
		//idsInClerk是从clerk表查询出来的，所以此处的clerk对象应该都不为null
		//Assert.notNull(clerk);
		if(clerk != null){
			if(!includeSystemClerks && clerk.getEntityID() != null && clerk.getEntityID().longValue() <= Org.MAX_SYS_ID){
				log.debug("不返回系统部门的用户：" + clerk);
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
			log.debug("单位信息发生了变化，同步更新人员中的单位简称：" + oldAbbr + " --> " + newAbbr);
		}
		int rows = clerkDao.updateEntityName(org.getId(), newAbbr);
		
		//清理缓存
		if(rows > 0){
			//clerkCache.clear();
			CacheFactory.clearCaches();
			if(log.isDebugEnabled()){
				log.debug("人员工作单位信息发生变化，清理了整个集群的缓存");
				log.debug("共用户工作单位(dutyEntityName) ‘" + rows	+ "’ 个");
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
