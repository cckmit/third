/*
 * $Id: DummyQueryParameters.java 5403 2012-03-06 09:01:19Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.QueryParameter;
import org.opoo.apps.QueryParameters;
import org.opoo.apps.web.struts2.AbstractAppsAction;
import org.opoo.ndao.criterion.Order;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.security.UserClerkHolder;

import com.google.common.collect.Maps;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class DummyQueryParameters implements QueryParameters {
	private static final Log log = LogFactory.getLog(DummyQueryParameters.class);
	
	private static final long serialVersionUID = -520290907617403158L;
	private int start = -1;
	private int limit;
	private String sort;
	private String dir;
	private List<String> eps;//entity id params，部门参数
	private List<String> ups;//user id params，用户参数

	private List<QueryParameter> q;
	private List<QueryParameter> s;
	private Map<String, ?> rawParameters;
	
	private int maxPageSize = AbstractAppsAction.PAGE_SIZE_MAX;
	private int defaultPageSize = AbstractAppsAction.DEFAULT_PAGE_SIZE;
	
	public int getMaxResults() {
		if (limit <= 0){
			return defaultPageSize;
		}
//		if(limit > PAGE_SIZE_MAX) {
//			return PAGE_SIZE_MAX;
//		}
		if(limit > maxPageSize){
			return maxPageSize;
		}
		
		return limit;
	}
	
	/**
	 * @return the parameters
	 */
	public List<QueryParameter> getParameters() {
		List<QueryParameter> list = new ArrayList<QueryParameter>();
		if(q != null){
			list.addAll(q);
		}
		if(s != null){
			list.addAll(s);
		}
		if(hasEpsOrUps()){
			Clerk clerk = UserClerkHolder.getClerk();
			if(eps != null && !eps.isEmpty()){
				String clerkEntityID = "" + clerk.getEntityID();
				for(String ep: eps){
					list.add(new QueryParameter(ep, clerkEntityID, "=", "long"));
				}
			}
			if(ups != null && !ups.isEmpty()){
				String userId = "" + clerk.getId();
				for(String up: ups){
					list.add(new QueryParameter(up, userId, "=", "long"));
				}
			}
			
			if(log.isDebugEnabled()){
				log.debug("用户或者单位相关的查询参数合并后的查询参数为：" + list);
			}
		}
		return list;
	}
	
	public void setParameters(List<QueryParameter> parameters) {
		this.q = parameters;
	}

	public void setQ(List<QueryParameter> parameters) {
		this.q = parameters;
	}
	
	public List<QueryParameter> getQ(){
		return this.q;
	}
	
	//?s[0]
	public List<QueryParameter> getS(){
		return this.s;
	}
	
	public void setS(List<QueryParameter> s){
		this.s = s;
	}
	
	public Order getOrder() {
		if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(dir)) {
			return "ASC".equalsIgnoreCase(dir) ? Order.asc(sort) : Order
					.desc(sort);
		}
		return null;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

//	public List<String> getEps() {
//		return eps;
//	}
//
//	public void setEps(List<String> eps) {
//		this.eps = eps;
//	}
//
//	public List<String> getUps() {
//		return ups;
//	}
//
//	public void setUps(List<String> ups) {
//		this.ups = ups;
//	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	

	/**
	 * @return the eps
	 */
	public List<String> getEps() {
		return eps;
	}

	/**
	 * @param eps the eps to set
	 */
	public void setEps(List<String> eps) {
		this.eps = eps;
	}

	/**
	 * @return the ups
	 */
	public List<String> getUps() {
		return ups;
	}

	/**
	 * @param ups the ups to set
	 */
	public void setUps(List<String> ups) {
		this.ups = ups;
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.QueryParameters#getRawParameters()
	 */
	public Map<String, ?> getRawParameters() {
		Map<String, ?> map = rawParameters;
		if(hasEpsOrUps()){
			Clerk clerk = UserClerkHolder.getClerk();
			Map<String,Object> result = Maps.newHashMap();
			if(map != null){
				result.putAll(map);
			}
			if(eps != null && !eps.isEmpty()){
				String clerkEntityID = "" + clerk.getEntityID();
				for(String ep: eps){
					result.put(ep, clerkEntityID);
				}
			}
			if(ups != null && !ups.isEmpty()){
				String userId = "" + clerk.getId();
				for(String up: ups){
					result.put(up, userId);
				}
			}
			if(log.isDebugEnabled()){
				log.debug("用户或者单位相关的查询参数合并后的原始参数为：" + result);
			}
			return result;
		}else{
			return map;
		}
	}

	public void setRawParameters(Map<String, ?> rawParameters) {
		this.rawParameters = rawParameters;
	}
	
	private boolean hasEpsOrUps(){
		if(eps != null && !eps.isEmpty()){
			return true;
		}
		if(ups != null && !ups.isEmpty()){
			return true;
		}
		return false;
	}
}
