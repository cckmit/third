package org.opoo.apps.service.hibernate3;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.opoo.apps.QueryParameters;
import org.opoo.apps.exception.QueryException;
import org.opoo.apps.service.impl.QueryServiceImpl2;
import org.opoo.ndao.ObjectNotFoundException;
import org.opoo.ndao.hibernate3.HibernateDao;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.factory.InitializingBean;

public class HibernateQueryServiceImpl2 extends QueryServiceImpl2 implements InitializingBean {
	
	private HibernateDao<?,?> hibernateDao;

	@SuppressWarnings("unchecked")
	public Serializable get(String target, String id) throws QueryException {
		beforeQuery(target, null);
		
		Object bean = getBean(target);
		if(bean instanceof HibernateDao){
			HibernateDao<?,Serializable> dao = (HibernateDao<?,Serializable>) bean;
			Class<?> idClass = dao.getIdClass();			
			Serializable value = (Serializable) getParametersBuilder().convertValue(id, idClass);
			Serializable o = dao.get(value);
			
			if(o != null){
				return o;
			}else{
				log.debug("找不到对象[" + target + "]：id=" + id);
				throw new ObjectNotFoundException("找不到对象：id=" + id);
			}	
		}else{
			throw new ObjectNotFoundException("不支持查询此类对象：target=" + target + ", id=" + id);
		}
	}

	public List<?> namedQueryList(String name, QueryParameters parameters) throws QueryException {
		beforeQuery(name, parameters);
		
		ResultFilter resultFilter = buildResultFilter(null, null, parameters);
		QueryString string = getEffectiveQueryString(name);
		if(string.isSQLQuery){
			return getHibernateDao().getQuerySupport().findBySQL(string.selectQueryString, resultFilter);
		}else{
			return getHibernateDao().getQuerySupport().find(string.selectQueryString, resultFilter);
		}
	}

	public PageableList<?> namedQueryPageableList(String name, QueryParameters parameters) throws QueryException {
		beforeQuery(name, parameters);
		
		ResultFilter resultFilter = buildResultFilter(null, null, parameters);
		QueryString string = getEffectiveQueryString(name);
		if(StringUtils.isBlank(string.selectQueryString)){
			throw new IllegalArgumentException("分页查询的查询服务配置错误，缺少 select count(*)语句。");
		}
		if(string.isSQLQuery){
			return getHibernateDao().getQuerySupport().findBySQL(string.selectQueryString, string.countQueryString, resultFilter);
		}else{
			return getHibernateDao().getQuerySupport().find(string.selectQueryString, string.countQueryString, resultFilter);
		}
	}
	
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	protected QueryString getEffectiveQueryString(String name){
		try {
			return getQueryString(name);
		} catch (RuntimeException e) {
			String ssql = getHibernateDao().getNamedQuerySupport().getNamedQueryString(name);
			if(ssql != null){
				QueryString qs = new QueryString(ssql);
				String csql = getHibernateDao().getNamedQuerySupport().getNamedQueryString(name + "_count");
				if(csql != null){
					qs.countQueryString = csql;
				}
				return qs;
			}else{
				throw e;
			}
		} 
	}

	/**
	 * @return the hibernateDao
	 */
	public HibernateDao<?, ?> getHibernateDao() {
		return hibernateDao;
	}

	/**
	 * @param hibernateDao the hibernateDao to set
	 */
	public void setHibernateDao(HibernateDao<?, ?> hibernateDao) {
		this.hibernateDao = hibernateDao;
	}

	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		setParametersBuilder(new HibernateQueryParametersBuilder());		
	}
}
