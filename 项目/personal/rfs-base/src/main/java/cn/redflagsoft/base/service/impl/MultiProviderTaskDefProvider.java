/*
 * $Id: MultiProviderTaskDefProvider.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.cache.Cache;
import org.opoo.cache.MapCache;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cn.redflagsoft.base.bean.TaskDef;
import cn.redflagsoft.base.service.TaskDefProvider;


/**
 * 多个提供者。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class MultiProviderTaskDefProvider implements TaskDefProvider, InitializingBean, ApplicationContextAware/*,BeanNameAware*/ {
	private final static Log log = LogFactory.getLog(MultiProviderTaskDefProvider.class);
	
	private List<TaskDefProvider> providers;
	private ApplicationContext applicationContext;
//	private String name;
	private Cache<Integer, TaskDef> cache = new MapCache<Integer, TaskDef>();

	
	/**
	 * @param cache the cache to set
	 */
	public void setTaskDefCache(Cache<Integer, TaskDef> cache) {
		this.cache = cache;
	}

	public TaskDef getTaskDef(int taskType) {
		if(providers == null || providers.isEmpty()){
			return null;
		}
		TaskDef taskDef = cache.get(taskType);
		if(taskDef == null){
//			taskDef = new MultiProviderTaskDef(taskType);
			taskDef = getTaskDefFromProviders(providers, taskType);
			if(taskDef != null){
				cache.put(taskType, taskDef);
			}else{
				log.warn("找不到Task定义：" + taskType);
			}
		}
		return taskDef;
	}
	
	private TaskDef getTaskDefFromProviders(List<TaskDefProvider> providers, int taskType){
		for(TaskDefProvider provider: providers){
			TaskDef def = provider.getTaskDef(taskType);
			if(def != null){
				return def;
			}
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public void afterPropertiesSet() throws Exception {
		if(providers == null){
			providers = new ArrayList<TaskDefProvider>();
		}
		if(applicationContext != null){
			Map<String, TaskDefProvider> map = applicationContext.getBeansOfType(TaskDefProvider.class);
			//System.out.println(map);
			if(map != null){
				for(TaskDefProvider tdp: map.values()){
					if(tdp != this){
						providers.add(tdp);
					}
				}
			}
		}
		log.info("TaskDefProvider(s): " + providers);
	}

	public List<TaskDefProvider> getProviders() {
		return providers;
	}

	public void setProviders(List<TaskDefProvider> providers) {
		this.providers = providers;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
