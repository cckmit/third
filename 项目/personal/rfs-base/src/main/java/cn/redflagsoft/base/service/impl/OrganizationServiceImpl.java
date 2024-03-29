/*
 * $Id: OrganizationServiceImpl.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.lifecycle.Application;
import org.opoo.cache.Cache;
import org.opoo.cache.MapCache;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.District;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.Organization;
import cn.redflagsoft.base.dao.OrgDao;
import cn.redflagsoft.base.service.DistrictService;
import cn.redflagsoft.base.service.OrganizationService;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OrganizationServiceImpl implements OrganizationService {
	private static final Log log = LogFactory.getLog(OrganizationServiceImpl.class);
	private OrgDao orgDao;
	public OrgDao getOrgDao() {
		return orgDao;
	}

	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrganizationService#getOrganization(long)
	 */
	public Organization getOrganization(long id) {
//		Long orgId = id;
//		if(id == -1L){
//			orgId = null;
//		}
		Map<Long, Org> map = findOrgMap();
		//该缓存只对当前查询有效
		Cache<Long,Organization> cache = new MapCache<Long, Organization>();
		return getOrganization(map, cache, /*orgId*/id);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrganizationService#getRootOrganization()
	 */
	public Organization getRootOrganization() {
		return getOrganization(OrganizationRoot.ROOT_ID);
	}
	
	Map<Long,Org> findOrgMap(){
		List<Org> list = orgDao.find(new ResultFilter(null, Order.asc("displayOrder")));
		Map<Long,Org> map = new LinkedHashMap<Long, Org>();
		for (Org org : list) {
			map.put(org.getId(), org);
//			log.debug("查询单位 -> " + org.getId() + ":" + org.getAbbr() + "(" + org.getParentOrgId() + ")");
		}
		
		if(log.isDebugEnabled()){
			log.debug("批量查询单位 " + map.size() + " 个");
		}
		return map;
	}
	
	static List<Organization> findSuborgs(Map<Long,Org> orgs, Cache<Long,Organization> cache, Long id){
		List<Organization> list = new ArrayList<Organization>();
		for (Org org : orgs.values()) {
			if(equals(org.getParentOrgId(), id)){
//			if(org.getParentOrgId() == id){
				Organization o = getOrganization(orgs, cache, org.getId());
				list.add(o);
//				log.debug("org.parentOrgId=" + org.getParentOrgId() + ", id=" + id + "  is equal");
			}
//			else{
//				log.debug("org.parentOrgId=" + org.getParentOrgId() + ", id=" + id + "  is not equal");
//			}
		}
		return list;
	}
	
	static Organization getOrganization(Map<Long,Org> orgs, Cache<Long,Organization> cache, Long id){
		if(id == null){
			id = -1L;
		}
		Organization org = cache.get(id);
		if(org == null){
			if(id == -1L){
				org = new OrganizationRoot(orgs, cache);
			}else{
				org = new OrganizationImpl(orgs, cache, id);
			}
			cache.put(id, org);
		}
		return org;
	}
	
	static District getDistrict(String code){
		DistrictService districtService = Application.getContext().get("districtService", DistrictService.class);
		return districtService.getDistrict(code);
	}

	/**
	 * @return
	 */
	static District getRootDistrict() {
		DistrictService districtService = Application.getContext().get("districtService", DistrictService.class);
		return districtService.getRootDistrict();
	}
	
	public static boolean equals(Object x, Object y) {
		return x == y || (x != null && y != null && x.equals(y));
	}
}
