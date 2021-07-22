package org.opoo.apps.service.hibernate3;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.opoo.apps.QueryParameters;
import org.opoo.apps.exception.QueryException;
import org.opoo.apps.service.impl.QueryServiceImpl;
import org.opoo.ndao.Dao;
import org.opoo.ndao.ObjectNotFoundException;
import org.opoo.ndao.hibernate3.HibernateDao;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;

@Deprecated
public class HibernateQueryServiceImpl extends QueryServiceImpl {
	
	private HibernateDao<?,?> hibernateDao;
	
	public HibernateQueryServiceImpl(){
		super();
		setQueryParametersResolver(new HibernateQueryParametersResolver());
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

	
	public List<?> namedQueryList(final String name, QueryParameters parameters) throws QueryException {
		return (List<?>) execute(name, parameters, new QueryCallback(){
			public Object execute(ResultFilter resultFilter) throws QueryException {
				QueryString string = getQueryString(name);
				return getHibernateDao().getQuerySupport().find(string.selectQueryString, resultFilter);
			}
		});
	}
	
	public PageableList<?> namedQueryPageableList(final String name, QueryParameters parameters) throws QueryException {
		return (PageableList<?>) execute(name, parameters, new QueryCallback(){
			public Object execute(ResultFilter resultFilter) throws QueryException {
				QueryString string = getQueryString(name);
				if(StringUtils.isBlank(string.selectQueryString)){
					throw new IllegalArgumentException("分页查询的查询服务配置错误，缺少 select count(*)语句。");
				}
				return getHibernateDao().getQuerySupport().find(string.selectQueryString, string.countQueryString, resultFilter);
			}
		});
	}
	
	
	public Serializable get(String target, String id) throws QueryException {
		beforeQuery(target, null);
		
		Dao<?, Serializable> dao = lookupDao(target);
		
		if(dao instanceof HibernateDao){
			Class<?> idClass = ((HibernateDao<?, ?>)dao).getIdClass();
			Serializable value = getQueryParametersResolver().convert(id, idClass);
			Serializable o = dao.get(value);
			
			if(o != null){
				return o;
			}else{
				throw new ObjectNotFoundException("找不到对象：id=" + id);
			}	
		}else{
			throw new ObjectNotFoundException("不支持查询此类对象：target=" + target + ", id=" + id);
		}
	}
}
