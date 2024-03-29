/*
 * $Id: SpringContextSchemeManager.java 5316 2012-02-02 06:44:48Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Alex Lin
 *
 */
public class SpringContextSchemeManager implements SchemeManager, ApplicationContextAware, BeanFactoryPostProcessor {
	private static final Log log = LogFactory.getLog(SpringContextSchemeManager.class);
	private ApplicationContext context;

	private Map<String, String> workSchemeNames = new HashMap<String, String>();
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.SchemeManager#getScheme(java.lang.String)
	 */
	public Scheme getScheme(String name) throws SchemeException {
		Object bean = null;
		try {
			bean = context.getBean(name);
		} catch (BeansException e) {
			log.error(e);
			throw new NoSuchSchemeException(name);
		}
		if(bean != null && bean instanceof Scheme){
			return (Scheme) bean;
		}
		throw new NoSuchSchemeException(name);
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
		this.context = applicationContext;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.SchemeManager#getWorkScheme(short, short)
	 */
	public WorkScheme getWorkScheme(int taskType, int workType)	throws SchemeException {
		String name = workSchemeNames.get(buildKey(taskType, workType));
		if(name != null){
			return (WorkScheme) getScheme(name);
		}
		throw new NoSuchSchemeException("taskType:" + taskType + ", workType:" + workType);
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
	 */
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		//System.out.println("-------------------postProcessBeanFactory: ");
		String[] names = beanFactory.getBeanNamesForType(WorkScheme.class);
		for(String name: names){
			BeanDefinition d = beanFactory.getBeanDefinition(name);
			if(!d.isAbstract()){
				/* 下面的方法不能从父级配置中读取属性
				MutablePropertyValues values = d.getPropertyValues();
				String taskType = getStringValue(values, "taskType");
				String workType = getStringValue(values, "workType");
				*/
				
				//从当前配置及父级配置中读取属性
				String taskType = getPropertyValue(beanFactory, d, "taskType");
				String workType = getPropertyValue(beanFactory, d, "workType");
				//System.out.println("发现WorkScheme：" + name + " t=" + taskType + ", w=" + workType);
				if(StringUtils.isNotBlank(taskType) && StringUtils.isNotBlank(workType)){
					try{
						//log.debug("注册 WorkScheme  name = " + name +", taskType=" + taskType + ", workType = " + workType);
						registerWorkScheme(Integer.parseInt(taskType), Integer.parseInt(workType), name);
					}catch(Exception e){
						log.debug("注册 WorkScheme 错误.", e);
					}
				}else{
					log.debug(name + " taskType or workType empty.");
				}
			}else{
				log.debug(name + " is abstract.");
			}
		}
	}
	
	private String getPropertyValue(ConfigurableListableBeanFactory beanFactory, BeanDefinition def, String propertyName){
		MutablePropertyValues values = def.getPropertyValues();
		//System.out.println(values);
		
		if(values.contains(propertyName)){
			return getStringValue(values, propertyName);
		}
		
		String parentName = def.getParentName();
		if(parentName != null){
			def = beanFactory.getBeanDefinition(parentName);
			return getPropertyValue(beanFactory, def, propertyName);
		}
		
		return null;
	}
	
	private String getStringValue(MutablePropertyValues values, String propertyName){
		PropertyValue value = values.getPropertyValue(propertyName);
		if(value != null){
			Object object = value.getValue();
			if(object instanceof TypedStringValue){
				return ((TypedStringValue) object).getValue();
			}else if(object instanceof List){
				List<String> list = toStringList((List<?>)object);
				if(list != null){
					return list.toString();
				}
			}
		}
		return null;
	}
	
	private List<String> toStringList(List<?> list){
		List<String> result = new ArrayList<String>();
		for(Object o: list){
			if(o instanceof TypedStringValue){
				String value = ((TypedStringValue) o).getValue();
				result.add(value);
			}
		}
		if(result.isEmpty()){
			return null;
		}
		return result;
	}
	
	/**
	 * 注册一个WorkScheme
	 * @param taskType
	 * @param workType
	 * @param workSchemeName beandefinition name
	 */
	public void registerWorkScheme(int taskType, int workType, String workSchemeName){
		workSchemeNames.put(buildKey(taskType, workType), workSchemeName);
		String msg = String.format("注册 WorkScheme: '%s'(taskType '%s', workType '%s')。", workSchemeName, taskType, workType);
		log.debug(msg);
	}
	
	private static String buildKey(int i, int j){
		return String.format("_t%s_w%s", i, j);
	}
}
