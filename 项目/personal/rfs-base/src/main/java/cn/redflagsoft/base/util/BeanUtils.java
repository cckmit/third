/*
 * $Id: BeanUtils.java 5461 2012-03-21 07:43:59Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.util.SerializableUtils;

import cn.redflagsoft.base.bean.Issue;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class BeanUtils {
	
	private static final Log log = LogFactory.getLog(BeanUtils.class);
	private static final boolean IS_DEBUG_ENABLED = Boolean.getBoolean("BeanUtils.debugEnabled") && log.isDebugEnabled();

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> getPropertiesMap(Object obj){
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor descriptors[] = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor desc : descriptors) {
				//System.out.println(desc.getReadMethod() + " >> " + desc.getName());
//				System.out.println(desc.getName());
//				System.out.println(desc.getReadMethod().invoke(obj, (Object[])null));
				String name = desc.getName();
				Method method = desc.getReadMethod();
				if(method != null){
					Object value = method.invoke(obj);
					if(IS_DEBUG_ENABLED){
						System.out.println("属性 " + name + " = " + value);
					}
					if(value != null){
						if(value instanceof Date){
							value = AppsGlobals.formatDate((Date) value);
						}
						map.put(name, value);
					}
				}
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		
		return map;
	}
	
	
	
	/**
	 * 从源对象复制指定的属性到目标对象。
	 * 
	 * 通常源对象与目标对象类型一致，如果不一致，而被复制属性的名称和类型必须一致。
	 * 
	 * @param dest
	 * @param src
	 * @param propertyNames
	 */
	public static final void copyProperties(Object dest, Object src, String... propertyNames){
		if(propertyNames == null || propertyNames.length == 0){
			try {
				PropertyUtils.copyProperties(dest, src);
			} catch (Exception e) {
				throw new IllegalStateException(e);
			} 
			return;
		}
        
		if (dest == null) {
            throw new IllegalArgumentException
                    ("No destination bean specified");
        }
        if (src == null) {
            throw new IllegalArgumentException("No source bean specified");
        }
        
		try {
			Map<String, PropertyDescriptor> destMap = BeanUtils.getMappedPropertyDescriptors(dest.getClass());
			Map<String, PropertyDescriptor> srcMap = BeanUtils.getMappedPropertyDescriptors(src.getClass());
			System.out.println(destMap);
			for(String name: propertyNames){
				PropertyDescriptor destPd = destMap.get(name);
				PropertyDescriptor srcPd = srcMap.get(name);
				if(destPd != null && srcPd != null){
					Method rm = srcPd.getReadMethod();
					Method wm = destPd.getWriteMethod();
					if(rm != null && wm != null){
						Object value = rm.invoke(src);
						if(value != null){
							wm.invoke(dest, value);
						}
					}else{
						if(rm == null){
							log.warn("源对象中找不到属性的读方法：" + name);
						}
						if(wm == null){
							log.warn("目标对象中找不到属性的写方法：" + name);
						}
					}
				}else{
					if(destPd == null){
						log.warn("目标对象中找不到属性定义：" + name);
					}
					if(srcPd == null){
						log.warn("源对象中找不到属性定义：" + name);
					}
				}
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} 
	}
	
	
	/**
	 * 
	 * @param clazz
	 * @return
	 * @throws IntrospectionException
	 */
	public static Map<String, PropertyDescriptor> getMappedPropertyDescriptors(Class<?> clazz) throws IntrospectionException{
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		PropertyDescriptor descriptors[] = beanInfo.getPropertyDescriptors();
		Map<String, PropertyDescriptor> map = new HashMap<String, PropertyDescriptor>(descriptors.length);
		for(PropertyDescriptor desc: descriptors){
			System.out.println(desc.getName());
			map.put(desc.getName(), desc);
		}
		return map;
	}
	
	
	public static void main(String[] args) throws Exception{
		Issue issue = new Issue();
		issue.setSn("aaaaaaaaaaaa");
		issue.setCreationTime(new Date());
		System.out.println(BeanUtils.getPropertiesMap(issue));
		
		Issue newIssue = new Issue();
		System.out.println(BeanUtils.getPropertiesMap(newIssue));
		
		
		BeanUtils.copyProperties(newIssue, issue);
		System.out.println(BeanUtils.getPropertiesMap(newIssue));
		
		List<Issue> list = new ArrayList<Issue>();
		list.add(issue);
		list.add(issue);
		
		String string = SerializableUtils.toJSON(list.iterator());
		System.out.println(string);
		
		String s = CodeMapUtils.getCodeName((short)1, 2);
		System.out.println(s);
	}
}
