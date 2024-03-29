/*
 * $Id: ServiceEventDispatcherImpl.java 6027 2012-09-20 07:36:57Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.services.api.support;

import java.util.List;

import cn.redflagsoft.base.services.api.Service;
import cn.redflagsoft.base.services.api.ServiceEvent;
import cn.redflagsoft.base.services.api.ServiceEventDispatcher;
import cn.redflagsoft.base.services.api.ServiceListener;

import com.google.common.collect.Lists;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ServiceEventDispatcherImpl implements ServiceEventDispatcher {
	private List<ServiceListener> serviceListeners = Lists.newArrayList();
	private Service service;
	
	/**
	 * @param service
	 */
	public ServiceEventDispatcherImpl(Service service) {
		super();
		this.service = service;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.services.api.Service#addListener(cn.redflagsoft.base.services.api.ServiceListener)
	 */
	public void addListener(ServiceListener listener) {
		serviceListeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.services.api.Service#removeListemer(cn.redflagsoft.base.services.api.ServiceListener)
	 */
	public void removeListemer(ServiceListener listener) {
		serviceListeners.remove(listener);
	}

	protected void fireStartingEvent(){
		ServiceEvent event = new ServiceEvent(service, ServiceEvent.SERVICE_STARTING);
		for (ServiceListener l : serviceListeners) {
			l.serviceStarting(event);
		}
	}
	
	protected void fireStartedEvent(){
		ServiceEvent event = new ServiceEvent(service, ServiceEvent.SERVICE_STARTED);
		for (ServiceListener l : serviceListeners) {
			l.serviceStarted(event);
		}
	}
	
	protected void fireStoppingEvent(){
		ServiceEvent event = new ServiceEvent(service, ServiceEvent.SERVICE_STOPPING);
		for (ServiceListener l : serviceListeners) {
			l.serviceStopping(event);
		}
	}
	
	protected void fireStoppedEvent(){
		ServiceEvent event = new ServiceEvent(service, ServiceEvent.SERVICE_STOPPED);
		for (ServiceListener l : serviceListeners) {
			l.serviceStopped(event);
		}
	}
}
