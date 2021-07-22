/*
 * $Id: ServiceInfoImpl.java 6034 2012-09-21 03:56:45Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.services.api.support;

import cn.redflagsoft.base.services.api.ServiceInfo;
import cn.redflagsoft.base.services.api.ServiceState;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ServiceInfoImpl implements ServiceInfo{
	private String name;
	private String displayName;
	private String description;
	private String serviceType;
	private boolean canStop;
	private boolean canPause;
	private String status;
	private ServiceState state = ServiceState.Stopped;
	
	
	/**
	 * @param name
	 * @param displayName
	 * @param description
	 * @param serviceType
	 * @param canStop
	 * @param canPause
	 * @param status
	 * @param state
	 */
	public ServiceInfoImpl(String name, String displayName, String description,
			String serviceType, boolean canStop, boolean canPause,
			String status, ServiceState state) {
		super();
		this.name = name;
		this.displayName = displayName;
		this.description = description;
		this.serviceType = serviceType;
		this.canStop = canStop;
		this.canPause = canPause;
		this.status = status;
		this.state = state;
	}
	/**
	 * 
	 * @param name
	 * @param displayName
	 * @param description
	 * @param serviceType
	 * @param canStop
	 * @param canPause
	 */
	public ServiceInfoImpl(String name, String displayName, String description,
			String serviceType, boolean canStop, boolean canPause) {
		super();
		this.name = name;
		this.displayName = displayName;
		this.description = description;
		this.serviceType = serviceType;
		this.canStop = canStop;
		this.canPause = canPause;
	}
	
	/**
	 * 
	 */
	public ServiceInfoImpl() {
		super();
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}
	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	/**
	 * @param canStop the canStop to set
	 */
	public void setCanStop(boolean canStop) {
		this.canStop = canStop;
	}
	/**
	 * @param canPause the canPause to set
	 */
	public void setCanPause(boolean canPause) {
		this.canPause = canPause;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the state
	 */
	public ServiceState getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(ServiceState state) {
		this.state = state;
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.services.api.ServiceInfo#canPause()
	 */
	public boolean canPause() {
		return canPause;
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.services.api.ServiceInfo#canStop()
	 */
	public boolean canStop() {
		return canStop;
	}
	
	/**
	 * @return the canStop
	 */
	public boolean isCanStop() {
		return canStop;
	}
	/**
	 * @return the canPause
	 */
	public boolean isCanPause() {
		return canPause;
	}
}
