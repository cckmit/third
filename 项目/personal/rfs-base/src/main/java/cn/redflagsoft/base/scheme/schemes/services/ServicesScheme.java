/*
 * $Id: ServicesScheme.java 6031 2012-09-21 03:16:55Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.services;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.Model;
import org.springframework.util.Assert;

import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.services.ServicesManager;
import cn.redflagsoft.base.services.api.Service;
import cn.redflagsoft.base.services.api.ServiceException;
import cn.redflagsoft.base.services.api.ServiceInfo;
import cn.redflagsoft.base.services.api.ServiceState;

import com.google.common.collect.Lists;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ServicesScheme extends AbstractScheme{
	
	private static final Log log = LogFactory.getLog(ServicesScheme.class);
	
	private ServicesManager servicesManager;
	
	private String name;
	
	public ServicesManager getServicesManager() {
		return servicesManager;
	}

	public void setServicesManager(ServicesManager servicesManager) {
		this.servicesManager = servicesManager;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object viewServices(){
		List<Service> services = servicesManager.getServices();
		List<ServiceBean> result = Lists.newArrayList();
		for(Service s: services){
			ServiceInfo info = s.getInfo();

			ServiceBean bean = new ServiceBean();
			bean.setName(info.getName());
			bean.setDisplayName(info.getDisplayName());
			bean.setDescription(info.getDescription());
			bean.setStatus(info.getStatus());
			bean.setCanPause(info.canPause());
			bean.setCanStop(info.canStop());

			ServiceState state = info.getState();
			bean.setState(state);
			
			if(ServiceState.Stopped == state){
				bean.setStartEnabled(true);
			}
			
			if(info.canStop() && ServiceState.Running == state){
				bean.setStopEnabled(true);	
			}
			
			//FIXME
			bean.setLastOperateTime(new Date());
			bean.setLastOperatorId(1000L);
			
			result.add(bean);
		}
		return result;
	}
	
	public Object viewServices2(){
		
		List<ServiceBean> result = Lists.newArrayList();
		
		ServiceBean bean = new ServiceBean();
		bean.setName("a");
		bean.setDisplayName("Application Experience Lookup Service");
		bean.setDescription("在应用程序启动时为应用程序处理应用程序兼容性查找请求。");
		bean.setStatus("已启动");
		bean.setStopEnabled(true);
		bean.setStartEnabled(false);
		result.add(bean);
		
		ServiceBean bean2 = new ServiceBean();
		bean2.setName("b");
		bean2.setDisplayName("Computer Browser");
		bean2.setDescription("维护网络上计算机的更新列表，并将列表提供给计算机指定浏览。如果服务停止，列表不会被更新或维护。如果服务被禁用，任何直接依");
		bean2.setStatus("已停止");
		bean2.setStopEnabled(false);
		bean2.setStartEnabled(true);
		
		ServiceBean bean3 = new ServiceBean();
		bean3.setName("c");
		bean3.setDisplayName("IBM WebSphere Application Server V8.0");
		bean3.setDescription("Controls the running of an IBM WebSphere Application Server V8.0 server named: server1");
		bean3.setStatus("已启动");
		bean3.setStopEnabled(true);
		bean3.setStartEnabled(false);
		
		ServiceBean bean4 = new ServiceBean();
		bean4.setName("d");
		bean4.setDisplayName("Office Source Engine");
		bean4.setDescription("保存用于更新和修复的安装文件，并且在下载安装程序更新和 Watson 错误报告时必须使用。");
		bean4.setStatus("已停止");
		bean4.setStopEnabled(false);
		bean4.setStartEnabled(true);
		
		ServiceBean bean5 = new ServiceBean();
		bean5.setName("e");
		bean5.setDisplayName("Plug and Play");
		bean5.setDescription("使计算机在极少或没有用户输入的情况下能识别并适应硬件的更改。终止或禁用此服务会造成系统不稳定。");
		bean5.setStatus("已停止");
		bean5.setStopEnabled(false);
		bean5.setStartEnabled(true);
		
		ServiceBean bean6 = new ServiceBean();
		bean6.setName("f");
		bean6.setDisplayName("Virtual Disk Service");
		bean6.setDescription("提供软件卷和硬件卷管理服务。");
		bean6.setStatus("已启动");
		bean6.setStopEnabled(true);
		bean6.setStartEnabled(false);
		
		result.add(bean2);
		result.add(bean3);
		result.add(bean4);
		result.add(bean5);
		result.add(bean6);
		
		return result;
	}
	
	public Object doStartService(){
		Assert.notNull(name, "服务名称不能空！");
		try {
			Service service = servicesManager.getService(name);
			service.start();
		} catch (ServiceException e) {
			log.error(e.getMessage());
			return new Model(false,e.getMessage(),null);
		}
		return "启动服务成功！";
	}
	

	public Object doStopService(){
		Assert.notNull(name, "服务名称不能空！");
		try {
			Service service = servicesManager.getService(name);
			service.stop();
		} catch (ServiceException e) {
			log.error(e.getMessage());
			return new Model(false,e.getMessage(),null);
		}
		return "服务已停止！";
	}
	
	public static class ServiceBean implements Serializable{
		private static final long serialVersionUID = -3126735429893217071L;
//		public static final Integer STATE_STOP = 1;
//		public static final Integer STATE_START = 2;
//		public static final Integer STATE_RUN = 3;
//		public static final Integer STATE_CUSTOM = 4;
		
		private String name;
		private String displayName;
		private String status;
		private String description;
		private ServiceState state;
		
		private Long lastOperatorId;
		private String lastOperatorName;
		private Date lastOperateTime;
		
		private boolean stopEnabled;
		private boolean startEnabled;
		private boolean pauseEnabled;
		private boolean canStop;
		private boolean canPause;
		
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
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
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
		 * @return the lastOperatorId
		 */
		public Long getLastOperatorId() {
			return lastOperatorId;
		}
		/**
		 * @param lastOperatorId the lastOperatorId to set
		 */
		public void setLastOperatorId(Long lastOperatorId) {
			this.lastOperatorId = lastOperatorId;
		}
		/**
		 * @return the lastOperatorName
		 */
		public String getLastOperatorName() {
			return lastOperatorName;
		}
		/**
		 * @param lastOperatorName the lastOperatorName to set
		 */
		public void setLastOperatorName(String lastOperatorName) {
			this.lastOperatorName = lastOperatorName;
		}
		/**
		 * @return the lastOperateTimel
		 */
		public Date getLastOperateTime() {
			return lastOperateTime;
		}
		/**
		 * @param lastOperateTimel the lastOperateTimel to set
		 */
		public void setLastOperateTime(Date lastOperateTime) {
			this.lastOperateTime = lastOperateTime;
		}
		/**
		 * @return the stopEnabled
		 */
		public boolean isStopEnabled() {
			return stopEnabled;
		}
		/**
		 * @param stopEnabled the stopEnabled to set
		 */
		public void setStopEnabled(boolean stopEnabled) {
			this.stopEnabled = stopEnabled;
		}
		/**
		 * @return the startEnabled
		 */
		public boolean isStartEnabled() {
			return startEnabled;
		}
		/**
		 * @param startEnabled the startEnabled to set
		 */
		public void setStartEnabled(boolean startEnabled) {
			this.startEnabled = startEnabled;
		}
		/**
		 * @return the pauseEnabled
		 */
		public boolean isPauseEnabled() {
			return pauseEnabled;
		}
		/**
		 * @param pauseEnabled the pauseEnabled to set
		 */
		public void setPauseEnabled(boolean pauseEnabled) {
			this.pauseEnabled = pauseEnabled;
		}
		/**
		 * @return the canStop
		 */
		public boolean isCanStop() {
			return canStop;
		}
		/**
		 * @param canStop the canStop to set
		 */
		public void setCanStop(boolean canStop) {
			this.canStop = canStop;
		}
		/**
		 * @return the canPause
		 */
		public boolean isCanPause() {
			return canPause;
		}
		/**
		 * @param canPause the canPause to set
		 */
		public void setCanPause(boolean canPause) {
			this.canPause = canPause;
		}
	}
}
