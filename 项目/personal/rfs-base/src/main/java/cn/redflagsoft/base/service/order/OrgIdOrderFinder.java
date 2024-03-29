/*
 * $Id: OrgIdOrderFinder.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.order;

import java.util.Map;

import org.opoo.ndao.support.DomainUtils;

import cn.redflagsoft.base.bean.Org;

/**
 * 根据单位的Id来查找指定单位的排序号。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OrgIdOrderFinder extends AbstractOrgOrderFinder<Long> {
	private Map<Long,Org> cache;
	private Map<Long,Org> getCachedMap(){
		if(cache == null){
			cache = DomainUtils.domainListToMap(getCachedOrgList());
		}
		return cache;
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrderFinder#getOrder(java.lang.Object)
	 */
	public int getOrder(Long t) {
		if(t == null){
			return 0;
		}
		Org org = getCachedMap().get(t);
		if(org != null){
			return org.getOrder();
		}
		return 0;
	}

}
