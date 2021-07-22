/*
 * $Id: ClerkHibernateDao.java 6009 2012-09-11 07:05:52Z lcj $
 * ClerkHibernateDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.security.User;
import org.opoo.ndao.DataAccessException;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.util.Assert;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.dao.ClerkDao;
import cn.redflagsoft.base.security.UserClerk;
import cn.redflagsoft.base.vo.UserClerkVO;

/**
 * @author mwx
 *
 */
public class ClerkHibernateDao extends AbstractBaseHibernateDao<Clerk,Long> implements ClerkDao{
	private static final Log log = LogFactory.getLog(ClerkHibernateDao.class);
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.ClerkDao#findClerkIds(org.opoo.ndao.support.ResultFilter)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> findClerkIds(ResultFilter rf) {
		String qs = "select id from Clerk";
		if(rf != null){
			return getQuerySupport().find(qs, rf);
		}else{
			return getHibernateTemplate().find(qs);
		}
	}
	
	public PageableList<Long> findPageableClerkIds(ResultFilter rf) {
		Assert.notNull(rf, "必须指定结果过滤器");
		String qs = "select id from Clerk";
		String qs2 = "select count(*) from Clerk";
		return getQuerySupport().find(qs, qs2, rf);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Clerk> findClerkByEntityName(String entityName) {
		String hql = "select a from Clerk a where a.entityName=?";
		return getHibernateTemplate().find(hql, entityName);
	}

	public List<UserClerk> findUserClerks(ResultFilter rf){
		//String qs = "select new cn.redflagsoft.base.security.UserClerk(a, b) from User a, Clerk b where a.userId=b.id";
		//getQuerySupport().find(qs, rf);
		String qs = "select a, b from LiveUser a, Clerk b where a.userId=b.id";
		List<Object[]> list = getQuerySupport().find(qs, rf);
		List<UserClerk> result = new ArrayList<UserClerk>();
		for(Object[] arr: list){
			if(arr.length == 2){
				User user = (User) arr[0];
				Clerk clerk = (Clerk) arr[1];
				
				//系统用户不出现在列表中
				if(isSysUser(user.getUsername())){
					if(log.isDebugEnabled()){
						log.debug("系统用户，不出现在查询列表中：" + user.getUsername());
					}
					continue;
				}
				result.add(new UserClerk(user, clerk));
			}
		}
		return result;
	}
	
	
	/**
	 * 查询用户名列表。
	 * 可拼接查询条件。
	 * @param rf
	 * @return
	 */
	public List<String> findUserClerkUsernames(final ResultFilter filter){
		if(filter != null && filter.getFirstResult() >= 0 && filter.getMaxResults() > 0){
			log.debug("findUserClerkUsernames()分页查询条件: " + filter.getFirstResult() + ", " + filter.getMaxResults()
					+ " -- " + filter.getCriterion() + ", " + filter.getOrder());
		}
		String qs = "select a.username from LiveUser a, Clerk b where a.userId=b.id";
//		if(filter.getCriterion() != null){
//			qs += " and " + filter.getCriterion().toString();
//		}
//		if(filter.getOrder() != null){
//			qs += " order by " + filter.getOrder();
//		}
//		
//		log.debug("Execute HQL: " + qs);
//		
//		final String hql = qs;
//		return getHibernateTemplate().executeFind(new HibernateCallback() {
//			public Object doInHibernate(Session session) throws HibernateException,	SQLException {
//				Query query = session.createQuery(hql);
//				if(filter.isPageable()){
//					query.setFirstResult(filter.getFirstResult());
//					query.setMaxResults(filter.getMaxResults());
//				}
//				if(filter.getCriterion() != null && filter.getCriterion().getValues() != null){
//					int i = 0;
//					for(Object value: filter.getCriterion().getValues()){
//						query.setParameter(i++, value);
//					}
//				}
//				
//				return query.list();
//			}
//		});
		return getQuerySupport().find(qs, filter);
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.ClerkDao#getUserClerkUsernameCount(org.opoo.ndao.support.ResultFilter)
	 */
	public int getUserClerkUsernameCount(ResultFilter filter) {
		String qs = "select count(*) from LiveUser a, Clerk b where a.userId=b.id";
		return getQuerySupport().getInt(qs, filter != null ? filter.getCriterion() : null);
	}
	
	
	public List<UserClerkVO> findUserClerkVOs(ResultFilter rf){
		//String qs = "select new cn.redflagsoft.base.vo.UserClerkVO(a, b) from User a, Clerk b where a.userId=b.id";
		//return getQuerySupport().find(qs, rf);
		String qs = "select a, b from LiveUser a, Clerk b where a.userId=b.id";
		List<Object[]> list = getQuerySupport().find(qs, rf);
		List<UserClerkVO> result = new ArrayList<UserClerkVO>();
		for(Object[] arr: list){
			if(arr.length == 2){
				User user = (User) arr[0];
				Clerk clerk = (Clerk) arr[1];
				
				//系统用户不出现在列表中
				if(isSysUser(user.getUsername())){
					continue;
				}
				
				result.add(new UserClerkVO(user, clerk));
			}
		}
		return result;
	}
	
	private boolean isSysUser(String username){
		return "rfsa".equalsIgnoreCase(username) || "sys".equalsIgnoreCase(username);
	}


	public int updateEntityName(Long entityID, String entityName) {
		String qs = "update Clerk set entityName=? where entityID=?";
		Object[] values = new Object[]{entityName, entityID};
		return getQuerySupport().executeUpdate(qs, values);
	}

	/* (non-Javadoc)
	 * @see org.opoo.ndao.hibernate3.CachedHibernateDao#delete(org.opoo.ndao.Domain)
	 */
	@Override
	public int delete(Clerk entity) throws DataAccessException {
		checkDelete(entity.getId());
		return super.delete(entity);
	}

	/* (non-Javadoc)
	 * @see org.opoo.ndao.hibernate3.CachedHibernateDao#remove(org.opoo.ndao.criterion.Criterion)
	 */
	@Override
	public int remove(Criterion c) throws DataAccessException {
		//TODO checkDelete, how?
		return super.remove(c);
	}

	/* (non-Javadoc)
	 * @see org.opoo.ndao.hibernate3.CachedHibernateDao#remove(java.io.Serializable)
	 */
	@Override
	public int remove(Long id) throws DataAccessException {
		checkDelete(id);
		return super.remove(id);
	}

	/* (non-Javadoc)
	 * @see org.opoo.ndao.hibernate3.CachedHibernateDao#remove(K[])
	 */
	@Override
	public int remove(Long[] arg0) throws DataAccessException {
		for(Long id: arg0){
			checkDelete(id);
		}
		return super.remove(arg0);
	}
	
	private void checkDelete(Long id){
		if(id != null && id.longValue() < 100L){
			throw new DataAccessException("Clerk cannot be deleted: " + id);
		}
	}


	
}
