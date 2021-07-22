/*
 * $Id$
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.config;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class BeanATest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
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
	
	private String getPropertyValue(ConfigurableListableBeanFactory beanFactory, BeanDefinition def, String propertyName){
		MutablePropertyValues values = def.getPropertyValues();
		System.out.println(values);
		
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
	
	public String getPropertyValue(ConfigurableListableBeanFactory beanFactory, String beanName, String propertyName){
		BeanDefinition def = beanFactory.getBeanDefinition(beanName);
		return getPropertyValue(beanFactory, def, propertyName);
	}
	
	@Test
	public void test() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-beanA.xml");
		BeanDefinition def = context.getBeanFactory().getBeanDefinition("beanA");
		
		//递归获取值
		String age = getPropertyValue(context.getBeanFactory(), def, "age");
		String name = getPropertyValue(context.getBeanFactory(), def, "name");
		String desc = getPropertyValue(context.getBeanFactory(), def, "desc");
		String matterIds = getPropertyValue(context.getBeanFactory(), def, "matterIds");
		System.out.println(age);
		System.out.println(name);
		System.out.println(desc);
		System.out.println(matterIds);
		
		MutablePropertyValues values = def.getPropertyValues();
		System.out.println(values);
		System.out.println(values.contains("age"));
		System.out.println(values.contains("desc"));
		
		BeanDefinition od = def.getOriginatingBeanDefinition();
		System.out.println(od);
		
		Object attribute = def.getAttribute("desc");
		System.out.println(attribute);
		
		String parentName = def.getParentName();
		System.out.println(parentName);
		if(parentName != null){
			def = context.getBeanFactory().getBeanDefinition(parentName);
			values = def.getPropertyValues();
			System.out.println(values);
			System.out.println(values.contains("age"));
			System.out.println(values.contains("desc"));
		}
	}
}
