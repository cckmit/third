/*
 * $Id: ExceptionUtils.java 6296 2013-08-12 03:01:14Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.util;

import cn.redflagsoft.base.BusinessException;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public abstract class ExceptionUtils extends org.opoo.apps.util.ExceptionUtils{
	/**
	 * 
	 * @param throwable
	 * @return
	 */
	public static Throwable fetchRootCause(Throwable throwable) {
		Throwable follower = null;
		for (; throwable != null; throwable = throwable.getCause())
			follower = throwable;
		return follower;
	}
	
	public static BusinessException findBusinessException(Throwable throwable){
		while(throwable != null){
			if(throwable instanceof BusinessException){
				return (BusinessException) throwable;
			}
			throwable = throwable.getCause();
		}
		return null;
	}
}
