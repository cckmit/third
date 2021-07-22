/*
 * $Id: OrgInOrgGroupSelectDataHandler.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.ndao.support.ResultFilterUtils;

import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.SelectDataSource;
import cn.redflagsoft.base.dao.OrgDao;


/**
 * 从指定分组中查询单位列表。
 * 
 * <p>SelectDataSource的source表示单位分组的ID。
 * 
 * <p>如果没有明确指定排序顺序，则以单位分组中的顺序为准。
 * <p>查询条件是完全针对Org对象的。
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OrgInOrgGroupSelectDataHandler extends AbstractOrgSelectDataHandler implements SelectDataHandler<Org> {

	private OrgDao orgDao;
	
	public OrgDao getOrgDao() {
		return orgDao;
	}

	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}


	public boolean supports(SelectDataSource dataSource) {
		return dataSource.getCat() == SelectDataSource.CAT_单位分组;
	}

	protected List<Org> findOrgs(SelectDataSource dataSource, ResultFilter filter) {
		//String hql="select org from Org org, EntityGroup grp where org.id=grp.entityID and grp.groupID="+dataSource.getSource();
		String source = dataSource.getSource();
		long orgGroupId = Long.parseLong(source);
		//如果不带查询条件
		if(filter == null || (filter.getCriterion() == null && filter.getOrder() == null)){
			return getOrgGroupService().findOrgsInGroup(orgGroupId);
		}
		//否则，只查询ID
		List<Long> orgIds = getOrgGroupService().findOrgIdsInGroup(orgGroupId);
		if(orgIds.isEmpty()){
			return Collections.emptyList();
		}
		
		ResultFilter filter2 = ResultFilterUtils.append(filter, Restrictions.in("id", orgIds))
			.append(Restrictions.gt("id", Org.MAX_SYS_ID));
		boolean isOrder = filter.getOrder() != null;
		List<Org> orgs = orgDao.find(filter2);
		//如果查询条件中带排序，则以这个排序为准
		if(isOrder){
			return orgs;
		}
		
		//否则以组中的顺序为准
		List<Org> result = new ArrayList<Org>();
		for(Long id: orgIds){
			Org org = lookupOrg(orgs, id);
			if(org != null){
				result.add(org);
			}
		}
		return result;
	}
	
	private Org lookupOrg(List<Org> orgs, Long id){
		for(Org o: orgs){
			if(o.getId().longValue() == id.longValue()){
				return o;
			}
		}
		return null;
	}
}
