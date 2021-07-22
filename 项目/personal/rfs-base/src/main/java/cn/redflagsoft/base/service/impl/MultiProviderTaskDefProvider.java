/*
 * $Id: MultiProviderTaskDefProvider.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
 * ����ṩ�ߡ�
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
				log.warn("�Ҳ���Task���壺" + taskType);
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
