/*
 * $Id: ResultFilter.java 13 2010-11-26 05:04:02Z alex $
 *
 * Copyright 2006-2008 Alex Lin. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opoo.ndao.support;

import java.io.Serializable;
import java.util.Map;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;

/**
 * 结果过滤器。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @version 1.1
 */
public class ResultFilter implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4242540973767604673L;
	public static final ResultFilter EMPTY_FILTER = new ResultFilter();
	public static final int INVALID_INT = -1;
	/**
	 * 默认页大小。
	 */
	public static int MAX_RESULTS = 25;
	private Criterion criterion;
	private Order order;
	private int firstResult = INVALID_INT;
	private int maxResults = MAX_RESULTS;
	private Map<String, ?> rawParameters;
	
	
	/**
	 * 构建结果过滤器实例。
	 */
	public ResultFilter() {
	}

	/**
	 * 构建结果过滤器实例。
	 * 
	 * @param c 查询条件
	 * @param order 排序条件
	 */
	public ResultFilter(Criterion c, Order order) {
		this.criterion = c;
		this.order = order;
	}

	/**
	 * 构建结果过滤器实例。
	 * @param c 查询条件
	 * @param o 排序条件
	 * @param firstResult 开始记录索引号
	 * @param maxResults 最大记录数
	 */
	public ResultFilter(Criterion c, Order o, int firstResult, int maxResults) {
		this(c, o);
		setFirstResult(firstResult);
		setMaxResults(maxResults);
	}

	/**
	 * 设置查询条件。
	 * 
	 * @param criterion 查询条件
	 */
	public void setCriterion(Criterion criterion) {
		this.criterion = criterion;
	}

	/**
	 * 设置排序条件。
	 * @param order 排序条件
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * 返回查询条件。
	 * @return 查询条件
	 */
	public Criterion getCriterion() {
		return criterion;
	}

	/**
	 * 获取排序条件。
	 * @return 排序条件
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * 该查询是否是可分页的。
	 * @return
	 */
	public boolean isPageable() {
		return firstResult != INVALID_INT;
	}

	/**
	 * 获取最大记录数（页大小）。
	 * @return
	 */
	public int getMaxResults() {
		return maxResults;
	}

	/**
	 * 获取开始记录索引号。
	 * @return
	 */
	public int getFirstResult() {
		return firstResult;
	}

	/**
	 * 设置开始记录索引号。
	 * @param firstResult
	 */
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}
	/**
	 * 设置最大记录数（页大小）。
	 * @param maxResults
	 */
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	/**
	 * 
	 */
	public String toString() {
		/*
		 * return "{pi:" + pageInfo.toString() + ",c:" + criterion.toString() +
		 * ",o:" + sortSql.toString() + "}";
		 */
		StringBuffer sb = new StringBuffer("rf{p:").append(firstResult).append(",").append(maxResults);
		if (criterion != null) {
			sb.append(criterion.toString());
		}
		if (order != null) {
			sb.append(order.toString());
		}
		return sb.toString();
	}

	/**
	 * 该对象作为某种标识性的键使用时，其字符串值。
	 * 
	 * @return
	 */
	public Serializable toKey() {
		String str = toString();
		Object[] vs = criterion.getValues();
		if (vs != null) {
			StringBuffer sb = new StringBuffer(str);
			for (int j = 0; j < vs.length; j++) {
				sb.append(".").append(vs[j]);
			}
			return sb.toString();
		}
		return str;
	}
	
	/**
	 * 原始的查询参数。
	 * @return
	 */
	public Map<String, ?> getRawParameters() {
		return rawParameters;
	}

	/**
	 * 原始的查询参数或者其他用户自定义要传递的参数。
	 * @param rawParameters
	 */
	public void setRawParameters(Map<String, ?> rawParameters) {
		this.rawParameters = rawParameters;
	}
	
	/**
	 * 复制现有对象的属性创建新的实例。
	 * @return
	 */
	public ResultFilter copy(){
		return ResultFilter.createResultFilter(this);
	}

	/**
	 * 在当前过滤器中增加一个排序条件，并返回当前过滤器本身。
	 * @param o 排序条件
	 * @return 当前过滤器本身
	 * @since 1.5.2
	 */
	public ResultFilter append(Order o){
		if(o != null){
			if(order != null){
				order = order.add(o);
			}else{
				order = o;
			}
		}
		return this;
	}
	/**
	 * 在当前过滤器中增加一个查询条件，并返回当前过滤器本身。
	 * @param c 过滤条件
	 * @return 当前过滤器本身
	 * @since 1.5.2
	 */
	public ResultFilter append(Criterion c){
		if(c != null){
			if(criterion != null){
				criterion = Restrictions.logic(criterion).and(c);
			}else{
				criterion = c;
			}
		}
		return this;
	}
	
//	/**
//	 * 在当前过滤器中增加一个查询条件，并返回当前过滤器的复制对象（新实例）。
//	 * @param c 查询条件
//	 * @return 当前过滤器的复制对象（新实例）
//	 * @since 1.5.2
//	 */
//	public ResultFilter appendToNewInstance(Criterion c){
//		ResultFilter filter = ResultFilter.createResultFilter(this);
//		return filter.append(c);
//	}
//	/**
//	 * 在当前过滤器中增加一个排序条件，并返回当前过滤器的复制对象（新实例）。
//	 * 
//	 * @param o 排序条件
//	 * @return 当前过滤器的复制对象（新实例）
//	 * @since 1.5.2
//	 */
//	public ResultFilter appendToNewInstance(Order o){
//		ResultFilter filter = ResultFilter.createResultFilter(this);
//		return filter.append(o);
//	}
	
	/**
	 * 创建空白的结果过滤器实例。
	 * 
	 * @return
	 */
	public static ResultFilter createEmptyResultFilter() {
		return new ResultFilter();
	}

	/**
	 * 创建分页的结果过滤器实例。
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public static ResultFilter createPageableResultFilter(int firstResult, int maxResults) {
		return new ResultFilter(null, null, firstResult, maxResults);
	}
	
	/**
	 * 创建ResultFilter实例，返回不为null的值。
	 * @param f
	 * @return
	 * @since 1.5.1
	 */
	public static ResultFilter createResultFilter(ResultFilter f){
		if(f != null){
			ResultFilter filter = new ResultFilter(f.getCriterion(), f.getOrder(), f.getFirstResult(), f.getMaxResults());
			filter.setRawParameters(f.getRawParameters());
			return filter;
		}
		return createEmptyResultFilter();
	}
}
