/*
 * $Id: QueryServiceImpl.java 5340 2012-02-23 09:30:28Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.QueryParameters;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserHolder;
import org.opoo.apps.service.hibernate3.HibernateQueryServiceImpl;
import org.opoo.ndao.Dao;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.SelectResult;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.QueryService;
import cn.redflagsoft.base.support.RFSQueryParameters;

/**
 * @author Alex Lin
 * @deprecated
 */
public class QueryServiceImpl extends HibernateQueryServiceImpl implements QueryService {
	private static final Log log = LogFactory.getLog(QueryServiceImpl.class);
	
	
	/* (non-Javadoc)
	 * @see org.opoo.apps.service.impl.QueryServiceImpl#parametersToResultFilter(org.opoo.apps.QueryParameters, org.opoo.ndao.Dao)
	 */
	@Override
	protected ResultFilter parametersToResultFilter(QueryParameters parameters,	Dao<?, ?> dao) {
		ResultFilter rf = super.parametersToResultFilter(parameters, dao);
		if(parameters instanceof RFSQueryParameters){
			RFSQueryParameters params = (RFSQueryParameters) parameters;
			List<String> eps = params.getEps();
			List<String> ups = params.getUps();
			
			Logic logic = null;
			if(eps != null && eps.size() > 0){
				Clerk clerk = UserClerkHolder.getClerk();
				for(int i = 0 ; i < eps.size() ; i++){
					Criterion tmp = Restrictions.eq(eps.get(i), clerk.getEntityID());
					if(logic == null){
						logic = Restrictions.logic(tmp);
					}else{
						logic.and(tmp);
					}
				}
			}
			if(ups != null && ups.size() > 0){
				User user = UserHolder.getUser();
				for(int i = 0 ; i < ups.size() ; i++){
					Criterion tmp = Restrictions.eq(ups.get(i), user.getUserId());
					if(logic == null){
						logic = Restrictions.logic(tmp);
					}else{
						logic.and(tmp);
					}
				}
			}
			
			Criterion c = rf.getCriterion();
			if(logic != null){
				if(c != null){
					c = logic.and(c);
				}else{
					c = logic;
				}
			}
			rf.setCriterion(c);
		}
		
		return rf;
	}


	/* 
	 * 
	 */
	public List<?> find(String queryId, ResultFilter resultFilter) {
		QueryString queryString = getQueryString(queryId);
		return getHibernateDao().getQuerySupport().find(queryString.selectQueryString, resultFilter);
	}


	/**
	 * 
	 */
	public PageableList<?> findPageableList(String queryId, ResultFilter resultFilter) {
		QueryString queryString = getQueryString(queryId);
		if(StringUtils.isBlank(queryString.countQueryString)){
			log.error("分页查询的查询服务配置错误，缺少 select count(*)语句。");
			throw new IllegalArgumentException("分页查询的查询服务配置错误，缺少 select count(*)语句。");
		}
		return getHibernateDao().getQuerySupport().find(queryString.selectQueryString, queryString.countQueryString, resultFilter);
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.QueryService#select(java.lang.String, org.opoo.apps.QueryParameters)
	 */
	public SelectResult select(String selectId, QueryParameters parameters) {
		throw new UnsupportedOperationException("select(java.lang.String, org.opoo.apps.QueryParameters)");
	}








//	/**
//	 * @return the hibernateDao
//	 */
//	public HibernateDao getHibernateDao() {
//		return hibernateDao;
//	}
//
//	/**
//	 * @param hibernateDao the hibernateDao to set
//	 */
//	public void setHibernateDao(HibernateDao hibernateDao) {
//		this.hibernateDao = hibernateDao;
//	}
//
//	/**
//	 * @return the queries
//	 */
//	public Map<String, String[]> getQueries() {
//		return queries;
//	}
//
//	/**
//	 * @param queries the queries to set
//	 */
//	public void setQueries(Map<String, String> queries) {
//		//this.queries = queries;
//		for(Map.Entry<String, String> en: queries.entrySet()){
//			String key = en.getKey();
//			String value = en.getValue();
//			Assert.hasText(value, "查询服务配置不正确。");
//			String[] array = value.split(";");
//			for(int i = 0 ; i < array.length ; i++){
//				String s = array[i];
//				s = s.trim();
//				Assert.hasText(s, "查询配置的查询语句不能为空。");
//				array[i] = s;
//				//System.out.println(s);
//			}
//			log.debug("查询[" + key + "]: " + value);
//			this.queries.put(key, array);
//		}
//	}
//
//	/* (non-Javadoc)
//	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
//	 */
//	public void setApplicationContext(ApplicationContext context) throws BeansException {
//		try {
//			Map<String, String> map = (Map<String, String>) context.getBean("queries");
//			if(map != null){
//				this.setQueries(map);
//			}
//		} catch (Exception e) {
//			log.warn(e.getMessage());
//		}
//		
//		try {
//			Properties props = (Properties) context.getBean("queryProps");
//			if(props != null){
//				Map map = new HashMap(props);
//				this.setQueries(map);
//			}
//		} catch (Exception e) {
//			log.warn(e.getMessage());
//		}
//	}

}
