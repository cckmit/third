/*
 * $Id: QueryServiceImpl.java 5403 2012-03-06 09:01:19Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.ws.rest.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.opoo.apps.Model;
import org.opoo.apps.exception.QueryException;
import org.opoo.apps.exception.QueryNotFoundException;
import org.opoo.apps.query.Query;
import org.opoo.apps.query.QueryManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;


import cn.redflagsoft.base.aop.ParametersSetter;
import cn.redflagsoft.base.support.DummyQueryParameters;
import cn.redflagsoft.base.support.QueryParametersWrapper;
import cn.redflagsoft.base.ws.rest.ParametersUtils;
import cn.redflagsoft.base.ws.rest.QueryService;

/**
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class QueryServiceImpl implements QueryService, InitializingBean {
	private QueryManager queryManager;
	private ParametersSetter parametersSetter;

	/**
	 * @return the queryManager
	 */
	public QueryManager getQueryManager() {
		return queryManager;
	}
	/**
	 * @param queryManager the queryManager to set
	 */
	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}

	/**
	 * @return the parametersSetter
	 */
	public ParametersSetter getParametersSetter() {
		return parametersSetter;
	}

	/**
	 * @param parametersSetter the parametersSetter to set
	 */
	public void setParametersSetter(ParametersSetter parametersSetter) {
		this.parametersSetter = parametersSetter;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(queryManager, "queryManager is required.");
		if(parametersSetter == null){
			//ServerImpl s;
			parametersSetter = new ParametersSetter();
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.webservices.rest.QueryService#execute(java.lang.String, javax.ws.rs.core.UriInfo)
	 */
	public Response execute(String queryKey, UriInfo info) {
		Model model = new Model();
		try{
			//if query not found, throw exception here.
			Query<?> query = queryManager.getQuery(queryKey);
			
			//building QueryParameters
			MultivaluedMap<String,String> map = info.getQueryParameters();
			Map<String, ?> parametersMap = ParametersUtils.multivalueMapToXWorkParametersMap(map);
			
			DummyQueryParameters params = new DummyQueryParameters();
			parametersSetter.setParameters(params, parametersMap);
			params.setRawParameters(parametersMap);
			QueryParametersWrapper wrapper = new QueryParametersWrapper(params);
			
			Object result = query.execute(wrapper);
			if(result instanceof List){
				model.setRows((List<?>) result);
			}else{
				model.setData((Serializable) result);
			}
		}catch(QueryNotFoundException e){
			model.setMessage(false, "查询不存在：" + queryKey, null);
		}catch(QueryException e){
			model.setMessage(false, "查询不存在：" + e.getMessage(), null);
		}catch(Exception e){
			model.setException(e);
		}
		return Response.ok().entity(model).build();
	}
}
