/*
 * $Id: AbstractOrgSelectDataHandler.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.OrgGroup;
import cn.redflagsoft.base.bean.SelectDataSource;
import cn.redflagsoft.base.service.OrgGroupService;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class AbstractOrgSelectDataHandler implements SelectDataHandler<Org> {
	private OrgGroupService orgGroupService;

	public OrgGroupService getOrgGroupService() {
		return orgGroupService;
	}


	public void setOrgGroupService(OrgGroupService orgGroupService) {
		this.orgGroupService = orgGroupService;
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.impl.SelectDataHandler#findSelectData(cn.redflagsoft.base.bean.SelectDataSource, org.opoo.ndao.support.ResultFilter)
	 */
	public List<Org> findSelectData(SelectDataSource dataSource, ResultFilter filter) {
		List<Org> list = findOrgs(dataSource, filter);
		List<Long> ids = orgGroupService.findOrgIdsInGroup(OrgGroup.ID_隐藏分组);
		if(ids == null || ids.isEmpty()){
			return list;
		}else{
			List<Org> result = new ArrayList<Org>();
			for(Org org: list){
				//不在隐藏组中的
				if(ids.contains(org.getId())){
					continue;
				}
				result.add(org);
			}
			return result;
		}
	}
	
	
	protected abstract List<Org> findOrgs(SelectDataSource dataSource, ResultFilter filter);
}
