/*
 * $Id: ServiceListener.java 6027 2012-09-20 07:36:57Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.services.api;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface ServiceListener {

	/**
	 * Invoked when a service is starting.
	 * @param event
	 */
	void serviceStarting(ServiceEvent event);
	
	/**
	 * Invoked when a service has started.
	 * @param event
	 */
	void serviceStarted(ServiceEvent event);
	
	/**
	 * Invoked when a service is stopping.
	 * @param event
	 */
	void serviceStopping(ServiceEvent event);
	
	/**
	 * Invoked when a service has stopped.
	 * @param event
	 */
	void serviceStopped(ServiceEvent event);
}
