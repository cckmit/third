/*
 * $Id: ClerkInOrgSelectDataHandler.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.List;
import java.util.Map;

import org.opoo.apps.exception.QueryException;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.ndao.support.ResultFilterUtils;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.SelectDataSource;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.ClerkService;

/**
 * 查询指定单位的人员。
 * 
 * <p>其中数据源配置中source的值含义如下：</br>
 * 1. 值大于0：该值就是单位的ID，查询该单位的人员；<br>
 * 2. 值等于0：取当前登录用户的单位ID最为要查询的单位ID，查询该单位的人员<br>
 * 3. 值小于0：从前台查询参数（ResultFilter）中获取orgId参数，查询该单位的的人员。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ClerkInOrgSelectDataHandler extends AbstractClerkSelectDataHandler implements SelectDataHandler<Clerk> {
	private ClerkService clerkService;
	
	
	public ClerkService getClerkService() {
		return clerkService;
	}

	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.impl.SelectDataHandler#supports(cn.redflagsoft.base.bean.SelectDataSource)
	 */
	public boolean supports(SelectDataSource dataSource) {
		return dataSource.getCat() == SelectDataSource.CAT_指定单位的人员;
	}

	protected List<Clerk> findClerks(SelectDataSource dataSource, ResultFilter filter) {
		long source = Long.parseLong(dataSource.getSource());
		Long entityID = null;
		
		if(source > 0){
			entityID = source;
		}else if(source == 0){
			entityID = UserClerkHolder.getClerk().getEntityID();
		}else{
			if(filter == null || filter.getRawParameters() == null 
					|| filter.getRawParameters().get("orgId") == null){
				throw new QueryException("必须指定查询参数：orgId");
			}
			String str = getString(filter.getRawParameters(), "orgId", null);
			if(str == null){
				throw new QueryException("必须指定查询参数：orgId");
			}
			entityID = Long.parseLong(str);
		}
		
		ResultFilter filter2 = ResultFilterUtils.append(filter, Restrictions.eq("entityID", entityID));
		filter2.setOrder(Order.asc("displayOrder"));
//		filter2.setCriterion(Restrictions.eq("entityID", entityID));
		return clerkService.findClerks(filter2);
	}
	
	static String getString(Map<?,?> map, String key, String defaultValue){
		Object object = map.get(key);
		if(object != null){
			if(object instanceof String[] && ((String[])object).length > 0){
				return ((String[])object)[0];
			}else if(object instanceof String){
				return (String) object;
			}else{
				System.err.println("未知的参数类型：" + object);
			}
		}
		return defaultValue;
	}
}
