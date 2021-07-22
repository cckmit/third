/*
 * $Id: DistrictImpl.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.opoo.apps.lifecycle.Application;

import cn.redflagsoft.base.bean.District;
import cn.redflagsoft.base.bean.DistrictBean;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class DistrictImpl implements District{
	private static final long serialVersionUID = -4759880250264372036L;
	private transient DistrictServiceImpl districtServiceImpl;
	
	private String code;
	private String name;
	private String parentCode;
	private int displayOrder;
	private byte status;
	private int type;
	private String remark;
	
	DistrictImpl(DistrictBean bean, DistrictServiceImpl districtServiceImpl){
		this.districtServiceImpl = districtServiceImpl;
//		Assert.notNull(bean.getCode());
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
		return getDistrictServiceImpl().findSubdistricts(getCode());
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
		return getDistrictServiceImpl().getDistrict(parentCode);
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
	
	public DistrictServiceImpl getDistrictServiceImpl() {
		if(districtServiceImpl == null){
			districtServiceImpl = Application.getContext().get("districtService", DistrictServiceImpl.class);
		}
		return districtServiceImpl;
	}
}
