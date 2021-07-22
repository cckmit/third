/*
 * $Id: PropertyBak.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.util.IOUtils;
import org.springframework.util.ResourceUtils;

/**
 * 
 * 用户属性或者用户组属性，包含基本属性，可能有些属性是前台使用的，
 * 所以不是基本属性。
 * 
 * 配置文件：
 * cn/redflagsoft/base/security/Property.properties
 * 
 * @author Alex Lin
 *
 */
public class PropertyBak implements Serializable {
	private static final Log log = LogFactory.getLog(PropertyBak.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3433591692193304009L;

	private String name;
	private String value;
	private String label;
	
	public PropertyBak(String name, String value, String label) {
		super();
		this.name = name;
		this.value = value;
		this.label = label;
	}
	
	public PropertyBak(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public PropertyBak(BaseProperty prop, String value){
		this(prop.getName(), value);
	}
	
	public PropertyBak() {
		super();
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		if(label == null && name != null){
			label = PropertyResource.getInstance().getLabel(name);
		}
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	public static Set<String> getNames(){
		return PropertyResource.getInstance().getNames();
	}
	
	
	public static class PropertyResource{
		private static PropertyResource instance;
		public static synchronized PropertyResource getInstance(){
			if(instance == null){
				instance = new PropertyResource();
			}
			return instance;
		}
		
		
		private Properties properties = new Properties();

		private PropertyResource(){
			try {
				File file = ResourceUtils.getFile("classpath:cn/redflagsoft/base/security/Property.properties");
				InputStream is = new FileInputStream(file);
				properties.load(is);
				if(is != null){
					IOUtils.close(is);
				}
			} catch (FileNotFoundException e) {
				log.error("属性文件找不到，无法加载用户（组）属性", e);
			} catch (IOException e) {
				log.error("加载用户（组）属性文件出错", e);
			}
		}
		
		public Properties getProperties(){
			return properties;
		}
		
		public String getLabel(String key){
			return properties.getProperty(key);
		}
		
		public Set<String> getNames(){
			Set<Object> set = properties.keySet();
			Set<String> names = new HashSet<String>();
			for(Object obj: set){
				names.add((String)obj);
			}
			return names;
		}
	}
	
	
	public static void main(String[] args) throws Exception{
		File file = ResourceUtils.getFile("classpath:cn/redflagsoft/base/security/Property.properties");
		System.out.println(file);
		Properties properties = new Properties();
		properties.load(new FileInputStream(file));
		System.out.println(properties.keySet());
		
		System.out.println(PropertyResource.getInstance().getNames());
	}
}
