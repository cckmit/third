/*
 * $Id: AbstractObjectAdminWorkScheme.java 5863 2012-06-08 06:59:00Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.commons;

import cn.redflagsoft.base.scheme.AbstractWorkScheme;
import cn.redflagsoft.base.service.ObjectAdminService;
import cn.redflagsoft.base.service.RiskService;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public abstract class AbstractObjectAdminWorkScheme extends AbstractWorkScheme {
	private ObjectAdminService objectAdminService;
	private RiskService riskService;
	
	public RiskService getRiskService() {
		return riskService;
	}

	public void setRiskService(RiskService riskService) {
		this.riskService = riskService;
	}

	/**
	 * @return the objectAdminService
	 */
	public ObjectAdminService getObjectAdminService() {
		return objectAdminService;
	}

	/**
	 * @param objectAdminService the objectAdminService to set
	 */
	public void setObjectAdminService(ObjectAdminService objectAdminService) {
		this.objectAdminService = objectAdminService;
	}
}
