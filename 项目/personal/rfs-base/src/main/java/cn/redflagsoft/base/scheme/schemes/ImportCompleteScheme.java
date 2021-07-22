/*
 * $Id: ImportCompleteScheme.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.Model;
import org.opoo.apps.ModelAware;
import org.opoo.util.BeanUtils;
import org.opoo.cache.Cache;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import cn.redflagsoft.base.aop.annotation.Parameters;
import cn.redflagsoft.base.aop.interceptor.ParametersAware;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.io.ImportCompleteHandler;
import cn.redflagsoft.base.process.WorkProcess;
import cn.redflagsoft.base.process.WorkProcessManager;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.scheme.SchemeInvoker;
import cn.redflagsoft.base.scheme.SchemeManager;

/**
 * 此类主要用于在数据导入（例如EXCEL导入）后要执行的数据处理工作。
 * 此类可以调用指定的Process或者Scheme来处理。
 * 当指定的scheme或者process实现了ModelAware接口时，默认由一个scheme或者process处理
 * 整个导入的数据。
 * 当指定的scheme或者process没有实现了ModelAware接口，默认由每个scheme或者process实例
 * 处理导入数据中rows的一条记录。
 * 
 * 
 * @author Alex Lin
 *
 */
public class ImportCompleteScheme extends AbstractScheme implements InitializingBean,ImportCompleteHandler {
	private static final Log log = LogFactory.getLog(ImportCompleteScheme.class);
	
	private SchemeManager schemeManager;
	private WorkProcessManager workProcessManager;
	
	/**
	 * 保存导入数据的cache
	 */
	private Cache<Long, Model> cache;
	/**
	 * 要调用的目标Process的Type
	 */
	private Integer targetProcessType;
	/**
	 * 要调用的目标scheme
	 */
	private String targetScheme;
	/**
	 * 要调用的目标scheme的指定方法
	 */
	private String targetMethod;
	/**
	 * 此次导入处理的唯一标识
	 */
	private Long id;
	/**
	 * 调用目标process或者scheme时组装参数需要的前缀
	 */
	private String targetObjectPrefix;
	/**
	 * 在调用目标scheme时需要的objectId的值对应在各个导入对象中的属性名称
	 */
	private String targetObjectIdProperty;
	
	@Parameters
	public Object doOk() throws SchemeException{
		Model m = getModel(id);
		if(targetProcessType != null){
			//先尝试查找，prototype类，不保存
			if(workProcessManager.getProcess(targetProcessType) == null){
				throw new SchemeException("找不到WorkProcess：" + targetProcessType);
			}
				
			doWorkProcess(targetProcessType, m);
		}else if(targetScheme != null && targetMethod != null){
			//先尝试查找，prototype类，不保存
			if(schemeManager.getScheme(targetScheme) == null){
				throw new SchemeException("找不到Scheme：" + targetScheme);
			}
			
			doScheme(targetScheme, targetMethod, m);
		}else{
			throw new SchemeException("提交参数不完整。");
		}
		
		return "处理完成";
	}
	
	/**
	 * @param targetScheme2
	 * @param targetMethod2
	 * @param m
	 */
	@SuppressWarnings("unchecked")
	protected void doScheme(String targetScheme2, String targetMethod2, Model m) {
		Scheme scheme = schemeManager.getScheme(targetScheme2);
		if(scheme instanceof ModelAware){
			((ModelAware) scheme).setModel(m);
			try {
				SchemeInvoker.invoke(scheme, targetMethod2);
			} catch (Exception e) {
				throw new SchemeException(e);
			}
		}else if(scheme instanceof ParametersAware){
			for(Object object: m.getRows()){
				Map<String, String> map = buildParameters(object);
				if(map != null){
					((ParametersAware) scheme).setParameters(map);
					try {
						SchemeInvoker.invoke(scheme, targetMethod2);
					} catch (Exception e) {
						throw new SchemeException(e);
					}
					scheme = schemeManager.getScheme(targetMethod2);
				}
			}
		}else{
			throw new SchemeException("请实现自定义文件导入处理的Scheme");
		}
	}

	/**
	 * @param targetProcessType2
	 * @param m
	 */
	@SuppressWarnings("unchecked")
	protected void doWorkProcess(Integer targetProcessType2, Model m) {
		WorkProcess wp = workProcessManager.getProcess(targetProcessType2);
		if(wp instanceof ModelAware){
			((ModelAware) wp).setModel(m);
			wp.execute(new HashMap<Object,Object>());
		}else{
			for(Object object: m.getRows()){
				Map<String, String> map = buildParameters(object);
				if(map != null){
					wp.execute(map);
					wp = workProcessManager.getProcess(targetProcessType2);
				}
			}
			
			//throw new SchemeException("请实现自定义文件导入处理的WorkProcess");
		}
	}
	
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	private Map<String,String> buildParameters(Object object){
		@SuppressWarnings("unchecked")
		Map<String, ?> map = BeanUtils.getProperties(object);
		
		if(map == null || map.isEmpty()){
			return null;
		}
		
		
		map.remove("class");
		Map<String, String> newmap = new HashMap<String, String>();
		
		//objectId
		if(StringUtils.isNotBlank(this.targetObjectIdProperty)){
			try {
				Object idValue = PropertyUtils.getProperty(object, this.targetObjectIdProperty);
				if(idValue != null){
					newmap.put("objectId", idValue.toString());
				}
			} catch (Exception e) {
				log.warn("获取对象的objectId出错", e);
			}
		}

		//其他属性。
		boolean prefixPresent = StringUtils.isNotBlank(targetObjectPrefix);
		for(String key: map.keySet()){
			Object value = map.get(key);
			if(value != null){
				if(prefixPresent){
					key = targetObjectPrefix + "." + key;
				}
				newmap.put(key, value.toString());
			}
		}
		
		return newmap;
	}
	
	
	
	@Parameters
	public Object doCancel() throws SchemeException{
		Assert.notNull(id, "输入参数不正确");
		cache.remove(id);
		
		return "数据导入已取消";
	}
	
	
	private Model getModel(Long id) throws SchemeException{
		Assert.notNull(id);
		Model m = cache.get(id);
		if(m == null){
			throw new SchemeException("缓存数据不存在");
		}
		
		return m;
	}
	
	
	
	/**
	 * @return the cache
	 */
	public Cache<Long, Model> getCache() {
		return cache;
	}
	/**
	 * @param cache the cache to set
	 */
	public void setCache(Cache<Long, Model> cache) {
		this.cache = cache;
	}
	/**
	 * @return the targetProcessType
	 */
	public Integer getTargetProcessType() {
		return targetProcessType;
	}
	/**
	 * @param targetProcessType the targetProcessType to set
	 */
	public void setTargetProcessType(Integer targetProcessType) {
		this.targetProcessType = targetProcessType;
	}
	/**
	 * @return the targetScheme
	 */
	public String getTargetScheme() {
		return targetScheme;
	}
	/**
	 * @param targetScheme the targetScheme to set
	 */
	public void setTargetScheme(String targetScheme) {
		this.targetScheme = targetScheme;
	}
	/**
	 * @return the targetMethod
	 */
	public String getTargetMethod() {
		return targetMethod;
	}
	/**
	 * @param targetMethod the targetMethod to set
	 */
	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cache);
		
	}

	/**
	 * @return the schemeManager
	 */
	public SchemeManager getSchemeManager() {
		return schemeManager;
	}

	/**
	 * @param schemeManager the schemeManager to set
	 */
	public void setSchemeManager(SchemeManager schemeManager) {
		this.schemeManager = schemeManager;
	}

	/**
	 * @return the workProcessManager
	 */
	public WorkProcessManager getWorkProcessManager() {
		return workProcessManager;
	}

	/**
	 * @param workProcessManager the workProcessManager to set
	 */
	public void setWorkProcessManager(WorkProcessManager workProcessManager) {
		this.workProcessManager = workProcessManager;
	}

	/**
	 * @return the targetObjectPrefix
	 */
	public String getTargetObjectPrefix() {
		return targetObjectPrefix;
	}

	/**
	 * @param targetObjectPrefix the targetObjectPrefix to set
	 */
	public void setTargetObjectPrefix(String targetObjectPrefix) {
		this.targetObjectPrefix = targetObjectPrefix;
	}

	/**
	 * @return the targetObjectIdProperty
	 */
	public String getTargetObjectIdProperty() {
		return targetObjectIdProperty;
	}

	/**
	 * @param targetObjectIdProperty the targetObjectIdProperty to set
	 */
	public void setTargetObjectIdProperty(String targetObjectIdProperty) {
		this.targetObjectIdProperty = targetObjectIdProperty;
	}
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		RFSObject obj = new RFSObject();
		obj.setId(System.currentTimeMillis());
		obj.setName("abcde");
		Map<String, ?> map = BeanUtils.getProperties(obj);
		System.out.println(map);
		System.out.println(PropertyUtils.getProperty(obj, "id"));
		
		Map<String, String> newMap = new HashMap<String, String>();
		for(String key : map.keySet()){
			Object value = map.get(key);
			if(value != null){
				newMap.put("object." + key, value.toString());
			}
		}
		
		System.out.println(newMap);
	}
	
}
