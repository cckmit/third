/*
 * $Id: OrganizationImpl.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.List;
import java.util.Map;

import org.opoo.cache.Cache;

import cn.redflagsoft.base.bean.District;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.Organization;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OrganizationImpl implements Organization {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8427581732555764181L;
	
	private final Map<Long, Org> orgs;
	private final Cache<Long, Organization> cache;
	private final Org org;
	OrganizationImpl(Map<Long, Org> orgs, Cache<Long, Organization> cache, long id) {
		this.orgs = orgs;
		this.cache = cache;
		org = orgs.get(id);
		if(org == null){
			throw new IllegalArgumentException("org not found: " + id);
		}
	}
	OrganizationImpl(Map<Long, Org> orgs, Cache<Long, Organization> cache, Org org){
		this.orgs = orgs;
		this.org = org;
		this.cache = cache;
		if(org == null){
			throw new IllegalArgumentException("org is required");
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getAbbr()
	 */
	public String getAbbr() {
		return org.getAbbr();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getAuthorizer()
	 */
	public String getAuthorizer() {
		return org.getAuthorizer();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getCode()
	 */
	public String getCode() {
		return org.getCode();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getDistrict()
	 */
	public District getDistrict() {
		if(org.getDistrictCode() != null){
			return OrganizationServiceImpl.getDistrict(org.getDistrictCode());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getEntityCode()
	 */
	public String getEntityCode() {
		return org.getEntityCode();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getFaxNo()
	 */
	public String getFaxNo() {
		return org.getFaxNo();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getHolder()
	 */
	public String getHolder() {
		return org.getHolder();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getId()
	 */
	public long getId() {
		return org.getId();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getLegalAddr()
	 */
	public String getLegalAddr() {
		return org.getLegalAddr();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getLicense()
	 */
	public String getLicense() {
		return org.getLicense();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getManager()
	 */
	public String getManager() {
		return org.getManager();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getName()
	 */
	public String getName() {
		return org.getName();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getParent()
	 */
	public Organization getParent() {
		return OrganizationServiceImpl.getOrganization(orgs, cache, org.getParentOrgId());
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getSuborgs()
	 */
	public List<Organization> getSuborgs() {
		return OrganizationServiceImpl.findSuborgs(orgs, cache, org.getId());
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getTelNo()
	 */
	public String getTelNo() {
		return org.getTelNo();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getWorkAddr()
	 */
	public String getWorkAddr() {
		return org.getWorkAddr();
	}

	/* (non-Javadoc)
	 * @see org.springframework.core.Ordered#getOrder()
	 */
	public int getOrder() {
		return org.getOrder();
	}
}
