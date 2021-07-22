/*
 * $Id: ImportCompleteScheme.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
 * ������Ҫ���������ݵ��루����EXCEL���룩��Ҫִ�е����ݴ�������
 * ������Ե���ָ����Process����Scheme������
 * ��ָ����scheme����processʵ����ModelAware�ӿ�ʱ��Ĭ����һ��scheme����process����
 * ������������ݡ�
 * ��ָ����scheme����processû��ʵ����ModelAware�ӿڣ�Ĭ����ÿ��scheme����processʵ��
 * ������������rows��һ����¼��
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
	 * ���浼�����ݵ�cache
	 */
	private Cache<Long, Model> cache;
	/**
	 * Ҫ���õ�Ŀ��Process��Type
	 */
	private Integer targetProcessType;
	/**
	 * Ҫ���õ�Ŀ��scheme
	 */
	private String targetScheme;
	/**
	 * Ҫ���õ�Ŀ��scheme��ָ������
	 */
	private String targetMethod;
	/**
	 * �˴ε��봦���Ψһ��ʶ
	 */
	private Long id;
	/**
	 * ����Ŀ��process����schemeʱ��װ������Ҫ��ǰ׺
	 */
	private String targetObjectPrefix;
	/**
	 * �ڵ���Ŀ��schemeʱ��Ҫ��objectId��ֵ��Ӧ�ڸ�����������е���������
	 */
	private String targetObjectIdProperty;
	
	@Parameters
	public Object doOk() throws SchemeException{
		Model m = getModel(id);
		if(targetProcessType != null){
			//�ȳ��Բ��ң�prototype�࣬������
			if(workProcessManager.getProcess(targetProcessType) == null){
				throw new SchemeException("�Ҳ���WorkProcess��" + targetProcessType);
			}
				
			doWorkProcess(targetProcessType, m);
		}else if(targetScheme != null && targetMethod != null){
			//�ȳ��Բ��ң�prototype�࣬������
			if(schemeManager.getScheme(targetScheme) == null){
				throw new SchemeException("�Ҳ���Scheme��" + targetScheme);
			}
			
			doScheme(targetScheme, targetMethod, m);
		}else{
			throw new SchemeException("�ύ������������");
		}
		
		return "�������";
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
			throw new SchemeException("��ʵ���Զ����ļ����봦���Scheme");
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
			
			//throw new SchemeException("��ʵ���Զ����ļ����봦���WorkProcess");
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
				log.warn("��ȡ�����objectId����", e);
			}
		}

		//�������ԡ�
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
		Assert.notNull(id, "�����������ȷ");
		cache.remove(id);
		
		return "���ݵ�����ȡ��";
	}
	
	
	private Model getModel(Long id) throws SchemeException{
		Assert.notNull(id);
		Model m = cache.get(id);
		if(m == null){
			throw new SchemeException("�������ݲ�����");
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
