/*
 * $Id: DatumInfoProvider.java 5383 2012-03-05 09:13:22Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.vo;

import java.util.Date;

/**
 * 资料信息提供者。
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface DatumInfoProvider {
	/**
	 * @return the datumDispatchOrgId
	 */
	Long getDatumDispatchOrgId();
	
	/**
	 * @return the datumDispatchOrgName
	 */
	String getDatumDispatchOrgName();

	/**
	 * @return the datumDispatchTime
	 */
	Date getDatumDispatchTime();
}
