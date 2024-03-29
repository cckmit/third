/*
 * $Id: ResultFilterBuilderImpl.java 5436 2012-03-13 01:11:40Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.Calendar;
import java.util.Map;

import org.opoo.apps.query.ParametersBuilder;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.factory.annotation.Required;

import cn.redflagsoft.base.aop.ParametersSetter;
import cn.redflagsoft.base.service.ResultFilterBuilder;
import cn.redflagsoft.base.support.DummyQueryParameters;
import cn.redflagsoft.base.support.QueryParametersWrapper;
import cn.redflagsoft.base.support.RFSParametersBuilder;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ResultFilterBuilderImpl implements ResultFilterBuilder {
	private ParametersSetter parametersSetter;// = new ParametersSetter();
	private ParametersBuilder parametersBuilder;// = new RFSParametersBuilder();
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ResultFilterBuilder#buildResultFilter(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public ResultFilter buildResultFilter(Map<?, ?> parameters) {
		DummyQueryParameters bean = new DummyQueryParameters();
		parametersSetter.setParameters(bean, parameters);
		bean.setRawParameters((Map<String, ?>) parameters);
		QueryParametersWrapper wrapper = new QueryParametersWrapper(bean);
		return parametersBuilder.buildResultFilter(null, wrapper);
	}
	
	public ParametersSetter getParametersSetter() {
		return parametersSetter;
	}
	@Required
	public void setParametersSetter(ParametersSetter parametersSetter) {
		this.parametersSetter = parametersSetter;
	}
	public ParametersBuilder getParametersBuilder() {
		return parametersBuilder;
	}
	@Required
	public void setParametersBuilder(ParametersBuilder parametersBuilder) {
		this.parametersBuilder = parametersBuilder;
	}
	
	
	public static void main(String[] args){
		ResultFilterBuilderImpl builder = new ResultFilterBuilderImpl();
		builder.setParametersBuilder(new RFSParametersBuilder());
		builder.setParametersSetter(new ParametersSetter());
		
		Map<String,Object> map = new java.util.HashMap<String, Object>();
		map.put("sort", "name");
		map.put("dir", "ASC");
		map.put("s[0].n", "a.name");
		map.put("s[0].v", "1021");
		//map.put("eps[0]", "b.entityID");
		
		ResultFilter filter = builder.buildResultFilter(map);
		System.out.println(filter);
		System.out.println(filter.getOrder());
		System.out.println(filter.getCriterion());
		
		Calendar a = Calendar.getInstance();
		Calendar b = Calendar.getInstance();
		a.set(2010, 10, 14);
		System.out.println(a.getTime());
		long x = b.getTimeInMillis() - a.getTimeInMillis();
		System.out.println(x / (24 * 60 * 60 * 1000));
	}
}
