/*
 * $Id: ClerkInOrgGroupSelectDataHandler.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.Collections;
import java.util.List;

import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.ndao.support.ResultFilterUtils;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.SelectDataSource;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.OrgGroupService;

/**
 * 从指定的单位分组中的所有单位查询人员列表。
 * 
 * <p>SelectDataSource的 source 中存储的是单位分组的ID。
 * 首先根据单位分组ID查询
 * 出分组中的所有单位，再查询出这些单位中的所有人员。
 * 如果没有明确指定排序，则以人员的排序为准。
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ClerkInOrgGroupSelectDataHandler extends AbstractClerkSelectDataHandler implements SelectDataHandler<Clerk> {
	private OrgGroupService orgGroupService;
	private ClerkService clerkService;
	
	public OrgGroupService getOrgGroupService() {
		return orgGroupService;
	}

	public void setOrgGroupService(OrgGroupService orgGroupService) {
		this.orgGroupService = orgGroupService;
	}

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
		return SelectDataSource.CAT_单位分组中的人员 == dataSource.getCat();
	}

	protected List<Clerk> findClerks(SelectDataSource dataSource, ResultFilter filter) {
		List<Long> orgIds = orgGroupService.findOrgIdsInGroup(Long.parseLong(dataSource.getSource()));
		if(orgIds == null){
			return null;
		}
		
		if(orgIds.isEmpty()){
			return Collections.emptyList();
		}
		
		ResultFilter filter2 = ResultFilterUtils.append(filter, Restrictions.in("entityID", orgIds));
		return clerkService.findClerks(filter2);
	}

}
