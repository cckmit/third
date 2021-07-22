package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.QueryParameters;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserHolder;
import org.opoo.apps.service.hibernate3.HibernateQueryParametersBuilder;
import org.opoo.apps.service.hibernate3.HibernateQueryServiceImpl2;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.SelectResult;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.QueryService;
import cn.redflagsoft.base.service.SelectDataSourceService;
import cn.redflagsoft.base.support.RFSQueryParameters;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class QueryServiceImpl2 extends HibernateQueryServiceImpl2 implements QueryService {
	
	private SelectDataSourceService selectDataSourceService;
	
	
	public SelectDataSourceService getSelectDataSourceService() {
		return selectDataSourceService;
	}

	public void setSelectDataSourceService(SelectDataSourceService selectDataSourceService) {
		this.selectDataSourceService = selectDataSourceService;
	}

	/* 
	 * 
	 */
	public List<?> find(String queryId, ResultFilter resultFilter) {
		QueryString queryString = getQueryString(queryId);
		if(queryString.isSQLQuery){
			return getHibernateDao().getQuerySupport().findBySQL(queryString.selectQueryString, resultFilter);
		}else{
			return getHibernateDao().getQuerySupport().find(queryString.selectQueryString, resultFilter);
		}
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
		if(queryString.isSQLQuery){
			return getHibernateDao().getQuerySupport().findBySQL(queryString.selectQueryString, queryString.countQueryString, resultFilter);
		}else{
			return getHibernateDao().getQuerySupport().find(queryString.selectQueryString, queryString.countQueryString, resultFilter);
		}
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.QueryService#select(java.lang.String, org.opoo.apps.QueryParameters)
	 */
	public SelectResult select(String selectId, QueryParameters parameters) {
		ResultFilter filter = getParametersBuilder().buildResultFilter(null, parameters);
		return getSelectDataSourceService().findSelectResult(selectId, filter);
	}
	
	/* (non-Javadoc)
	 * @see org.opoo.apps.service.hibernate3.HibernateQueryServiceImpl2#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		log.info("Setting parameters builder to '" + RFSParametersBuilder.class.getName() + "'");
		setParametersBuilder(new RFSParametersBuilder());
	}


	/**
	 * 
	 * 基本的参数构建器。
	 * 可将用户相关的查询参数自动添加进查询过滤器。
	 *
	 */
	public static class RFSParametersBuilder extends HibernateQueryParametersBuilder{
		private static final Log log = LogFactory.getLog(RFSParametersBuilder.class);

		/* (non-Javadoc)
		 * @see org.opoo.apps.service.impl.QueryParametersBuilder#buildResultFilter(java.lang.Object, org.opoo.apps.QueryParameters)
		 */
		@Override
		public ResultFilter buildResultFilter(Object action, QueryParameters parameters) {
//			if(log.isDebugEnabled()){
//				log.debug("Building ResultFilter from parameters " + parameters);
//			}
			
//			List<String> eps = null;
//			List<String> ups = null;
//			
//			if(parameters instanceof RFSQueryParameters){
//				RFSQueryParameters params = (RFSQueryParameters) parameters;
//				eps = params.getEps();
//				ups = params.getUps();
//				log.info("Finding RFSQueryParameters: " + eps  + " | " + ups);
//			}
			
			ResultFilter filter = super.buildResultFilter(action, parameters);
			
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
				
				Criterion c = filter.getCriterion();
				if(logic != null){
					if(c != null){
						c = logic.and(c);
					}else{
						c = logic;
					}
				}
				if(log.isDebugEnabled()){
					log.debug("用户或者单位相关的查询参数合并后：" + c);
				}
				filter.setCriterion(c);
			}
			return filter;
		}
	}




}
