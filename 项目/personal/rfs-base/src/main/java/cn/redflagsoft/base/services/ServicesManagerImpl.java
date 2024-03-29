/*
 * $Id: ServicesManagerImpl.java 6015 2012-09-18 09:59:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.services;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.google.common.collect.Lists;

import cn.redflagsoft.base.NotFoundException;
import cn.redflagsoft.base.services.api.Service;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ServicesManagerImpl implements ServicesManager, InitializingBean, ApplicationContextAware {
	private ApplicationContext context;
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.services.ServicesManager#getServices()
	 */
	public List<Service> getServices() {
		String[] names = context.getBeanNamesForType(Service.class);
		List<Service> services = Lists.newArrayList();
		for(String name: names){
			Service bean = (Service) context.getBean(name);
			services.add(bean);
		}
		return services;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.services.ServicesManager#getService(java.lang.String)
	 */
	public Service getService(String name) throws NotFoundException {
		List<Service> services = getServices();
		for (Service service : services) {
			String name2 = service.getInfo().getName();
			if(name.equals(name2)){
				return service;
			}
		}
		throw new NotFoundException("Service not found for name '" + name + "'.");
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
