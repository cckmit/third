/*
 * $Id: TemplateProcessorManagerImpl.java 5825 2012-06-01 07:58:04Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.event.v2.EventListener;
import org.opoo.apps.event.v2.EventListenerManager;
import org.opoo.apps.event.v2.EventListenerManagerAware;
import org.opoo.apps.event.v2.core.PropertyEvent;
import org.opoo.apps.lifecycle.Application;
import org.opoo.util.ClassUtils;
import org.springframework.beans.factory.DisposableBean;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.redflagsoft.base.service.TemplateProcessor;
import cn.redflagsoft.base.service.TemplateProcessorManager;

/**
 * 模板处理器管理器。
 * 
 * <p>在系统运行时属性中通过 <code>templateProcessor.<PROCESSOR_TYPE>.className</code> 或者
 * <code>templateProcessor.<PROCESSOR_TYPE>.beanName</code>来指定对应模板处理器的类名或者
 * 在spring容器中定义的bean名称。 
 *   
 * @author Alex Lin(lcql@msn.com)
 */
public class TemplateProcessorManagerImpl implements TemplateProcessorManager, DisposableBean, EventListenerManagerAware {
	private static final String TEMPLATE_PROCESSOR_PREFIXX = "templateProcessor.";
	
	
	private Map<String, TemplateProcessor> map = Maps.newHashMap();
	private List<String> missingProcessors = Lists.newArrayList();
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.TemplateProcessorManager#getTemplateProcessor(java.lang.String)
	 */
	public TemplateProcessor getTemplateProcessor(String processorType) {
		if(missingProcessors.contains(processorType)){
			return null;
		}
		TemplateProcessor processor = map.get(processorType);
		if(processor == null){
			String beanName = AppsGlobals.getProperty(TEMPLATE_PROCESSOR_PREFIXX + processorType + ".beanName");
			if(StringUtils.isNotBlank(beanName)){
				processor = Application.getContext().get(beanName, TemplateProcessor.class);
				if(processor != null){
					map.put(processorType, processor);
					return processor;
				}
			}

			String className = AppsGlobals.getProperty(TEMPLATE_PROCESSOR_PREFIXX + processorType + ".className");
			if(StringUtils.isNotBlank(className)){
				Object instance = ClassUtils.newInstance(className);
				if(instance != null && instance instanceof TemplateProcessor){
					processor = (TemplateProcessor) instance;
					map.put(processorType, processor);
					return processor;
				}
			}
			missingProcessors.add(processorType);
		}
		return processor;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	public void destroy() throws Exception {
		clear();
	}
	
	private void clear(){
		missingProcessors.clear();
		map.clear();
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.event.v2.EventListenerManagerAware#setEventListenerManager(org.opoo.apps.event.v2.EventListenerManager)
	 */
	public void setEventListenerManager(EventListenerManager manager) {
		System.out.println("--> EventListenerManager: " + manager);
		manager.addEventListener(new TemplateProcessorEventListener(this));
	}
	
	private static class TemplateProcessorEventListener implements EventListener<PropertyEvent> {
		TemplateProcessorManagerImpl manager;
		TemplateProcessorEventListener(TemplateProcessorManagerImpl manager){
			this.manager = manager;
		}
		/* (non-Javadoc)
		 * @see org.opoo.apps.event.v2.EventListener#handle(org.opoo.apps.event.v2.Event)
		 */
		public void handle(PropertyEvent event) {
			PropertyEvent.Type type = event.getType();
			String propName = event.getName(); 
			if(propName != null && propName.startsWith(TEMPLATE_PROCESSOR_PREFIXX)){
				if(type == PropertyEvent.Type.ADDED){
					propertyAdded(event);
				}else if(type == PropertyEvent.Type.MODIFIED){
					propertyModified(event);
				}else if(type == PropertyEvent.Type.REMOVED){
					propertyRemoved(event);
				}
			}
		}

		/**
		 * @param event
		 */
		private void propertyRemoved(PropertyEvent event) {
			manager.clear();
		}

		/**
		 * @param event
		 */
		private void propertyModified(PropertyEvent event) {
			manager.clear();
		}

		/**
		 * @param event
		 */
		private void propertyAdded(PropertyEvent event) {
			//ignore
		}
	}
}
