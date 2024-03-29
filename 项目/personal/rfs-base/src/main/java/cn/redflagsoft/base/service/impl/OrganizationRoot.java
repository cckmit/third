/*
 * $Id: OrganizationRoot.java 4342 2011-04-22 02:17:01Z lcj $
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
public class OrganizationRoot implements Organization {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6795053779302096661L;
	
	public static final long ROOT_ID = -1L;
	
	private final Map<Long, Org> orgs;
	private final Cache<Long, Organization> cache;
	
	/**
	 * @param orgs
	 */
	OrganizationRoot(Map<Long, Org> orgs, Cache<Long, Organization> cache) {
		this.orgs = orgs;
		this.cache = cache;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getAbbr()
	 */
	public String getAbbr() {
		return "顶级单位";
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getAuthorizer()
	 */
	public String getAuthorizer() {
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getCode()
	 */
	public String getCode() {
		return "99999999";
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getDistrict()
	 */
	public District getDistrict() {
		return OrganizationServiceImpl.getRootDistrict();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getEntityCode()
	 */
	public String getEntityCode() {
		return "99999999";
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getFaxNo()
	 */
	public String getFaxNo() {
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getHolder()
	 */
	public String getHolder() {
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getId()
	 */
	public long getId() {
		return -1L;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getLegalAddr()
	 */
	public String getLegalAddr() {
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getLicense()
	 */
	public String getLicense() {
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getManager()
	 */
	public String getManager() {
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getName()
	 */
	public String getName() {
		return "顶级机构";
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getParent()
	 */
	public Organization getParent() {
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getSuborgs()
	 */
	public List<Organization> getSuborgs() {
		return OrganizationServiceImpl.findSuborgs(orgs, cache, null);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getTelNo()
	 */
	public String getTelNo() {
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.Organization#getWorkAddr()
	 */
	public String getWorkAddr() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.core.Ordered#getOrder()
	 */
	public int getOrder() {
		return 0;
	}
}
