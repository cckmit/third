/*
 * $Id: DistrictRoot2.java 4615 2011-08-21 07:10:37Z lcj $
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
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class DistrictRoot2 implements District {
	private static final long serialVersionUID = -2168973397202331408L;
	private final Map<String, DistrictBean> districts;
	private final Cache<String,District> cache;
	/**
	 * @param districts
	 * @param cache 
	 */
	public DistrictRoot2(Map<String, DistrictBean> districts, Cache<String,District> cache) {
		this.districts = districts;
		this.cache = cache;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.District#getCode()
	 */
	public String getCode() {
		return "null";
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.District#getDisplayOrder()
	 */
	public int getDisplayOrder() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.District#getName()
	 */
	public String getName() {
		return "行政区划";
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.District#getParent()
	 */
	public District getParent() {
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.District#getRemark()
	 */
	public String getRemark() {
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.District#getStatus()
	 */
	public byte getStatus() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.District#getSubdistricts()
	 */
	public List<District> getSubdistricts() {
		return DistrictServiceImpl2.findSubdistricts(districts, cache, null);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.District#getType()
	 */
	public int getType() {
		return -1;
	}

	/* (non-Javadoc)
	 * @see org.springframework.core.Ordered#getOrder()
	 */
	public int getOrder() {
		return 0;
	}
}
