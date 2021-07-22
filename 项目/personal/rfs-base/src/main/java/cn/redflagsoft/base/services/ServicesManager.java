/*
 * $Id: ServicesManager.java 6015 2012-09-18 09:59:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.services;

import java.util.List;

import cn.redflagsoft.base.NotFoundException;
import cn.redflagsoft.base.services.api.Service;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface ServicesManager {
	
	List<Service> getServices();
	
	Service getService(String name) throws NotFoundException;
}
