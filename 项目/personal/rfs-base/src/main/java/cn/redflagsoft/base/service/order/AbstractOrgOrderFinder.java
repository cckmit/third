/*
 * $Id: AbstractOrgOrderFinder.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.order;

import java.util.List;

import org.opoo.apps.lifecycle.Application;

import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.service.OrderFinder;
import cn.redflagsoft.base.service.OrgService;

/**
 * 单位排序号查找类超类。
 * 
 * <p>定义了单位查找规则，并缓存单位集合。
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class AbstractOrgOrderFinder<T> implements OrderFinder<T>{
	private List<Org> orgList;
	
	/**
	 * 查找单位集合，并缓存。
	 * 
	 * @return 单位集合
	 */
	protected List<Org> getCachedOrgList(){
		if(orgList == null){
			//直接从上下文中获取 OrgService 实例
			OrgService service = Application.getContext().get("orgService", OrgService.class);
			orgList = service.findOrgs();
		}
		return orgList;
	}
}
