/*
 * $Id: DistrictImpl2.java 4615 2011-08-21 07:10:37Z lcj $
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
import cn.redflagsoft.base.bean.DistrictBean;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class DistrictImpl2 implements District{
	private static final long serialVersionUID = 6846104866992026347L;
	private final Map<String, DistrictBean> districts;
	private final Cache<String,District> cache;
	private String code;
	private String name;
	private String parentCode;
	private int displayOrder;
	private byte status;
	private int type;
	private String remark;
	
	/**
	 * @param districts
	 * @param cache 
	 * @param code
	 */
	DistrictImpl2(Map<String, DistrictBean> districts, Cache<String, District> cache, String code) {
		this(districts, cache, districts.get(code));
	}
	
	DistrictImpl2(Map<String, DistrictBean> districts, Cache<String, District> cache, DistrictBean bean){
		this.districts = districts;
		this.cache = cache;
		this.code = bean.getCode();
		this.name = bean.getName();
		this.parentCode = bean.getParentCode();
		this.status = bean.getStatus();
		this.type = bean.getType();
		this.displayOrder = bean.getDisplayOrder();
		this.remark = bean.getRemark();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.District#getSubdistricts()
	 */
	public List<District> getSubdistricts() {
		return DistrictServiceImpl2.findSubdistricts(districts, cache, code);
	}


	/* (non-Javadoc)
	 * @see org.springframework.core.Ordered#getOrder()
	 */
	public int getOrder() {
		return displayOrder;
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.District#getCode()
	 */
	public String getCode() {
		return code;
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.District#getDisplayOrder()
	 */
	public int getDisplayOrder() {
		return displayOrder;
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.District#getName()
	 */
	public String getName() {
		return name;
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.District#getParent()
	 */
	public District getParent() {
		return DistrictServiceImpl2.getDistrict(districts, cache, parentCode);
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.District#getRemark()
	 */
	public String getRemark() {
		return remark;
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.District#getStatus()
	 */
	public byte getStatus() {
		return status;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.District#getType()
	 */
	public int getType() {
		return type;
	}
}
