/*
 * $Id: Property.java 4341 2011-04-22 02:12:39Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;

import java.io.Serializable;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
public class Property implements Serializable {
	private static final Log log = LogFactory.getLog(Property.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3433591692193304009L;

	private String name;
	private String value;
	private String label;
	
	public Property(String name, String value, String label) {
		super();
		this.name = name;
		this.value = value;
		this.label = label;
	}
	
	public Property(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public Property(BaseProperty prop, String value){
		this(prop.getName(), value);
	}
	
	public Property() {
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
			label = PropertyResource.getInstance().getString(name);
		}
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * 
	 * @return
	 * @since 1.5
	 */
	public static Set<String> getNames(){
		Enumeration<String> keys = PropertyResource.getInstance().getResourceBundle().getKeys();
		Set<String> set = new HashSet<String>();
		while(keys.hasMoreElements()){
			set.add(keys.nextElement());
		}
		return set;
	}
	
	
	public static class PropertyResource{
		private static PropertyResource instance;
		public static synchronized PropertyResource getInstance(){
			if(instance == null){
				instance = new PropertyResource();
			}
			return instance;
		}
		
		
		private ResourceBundle bundle;
		private PropertyResource(){
			String bundleName = Property.class.getName();
			try {
				bundle = ResourceBundle.getBundle(bundleName);
				return;
			} catch (MissingResourceException ex) {
				// Try from the current loader ( that's the case for trusted apps )
				ClassLoader cl = Thread.currentThread().getContextClassLoader();
				if (cl != null) {
					try {
						bundle = ResourceBundle.getBundle(bundleName, Locale.getDefault(), cl);
						return;
					} catch (MissingResourceException ex2) {
					}
				}
				if (cl == null){
					cl = this.getClass().getClassLoader();
				}
					
	
				if (log.isDebugEnabled())
					log.debug("Can't find resource " + bundleName + " " + cl);
				if (cl instanceof URLClassLoader) {
					if (log.isDebugEnabled())
						log.debug(((URLClassLoader) cl).getURLs());
				}
			}
		}
		
		public ResourceBundle getResourceBundle(){
			return bundle;
		}
		
		public String getString(String key){
			if(bundle == null){
				return key;
			}
			
			try {
				return bundle.getString(key);
			} catch (Exception e) {
			}
			
			return key;
		}
	}
	
	
	public static void main(String[] args){
		Enumeration<String> en = PropertyResource.getInstance().getResourceBundle().getKeys();
		System.out.println(en);
		while(en.hasMoreElements()){
			System.out.println(en.nextElement());
		}
		
		String s = PropertyResource.getInstance().getString("AbcdEfglasjlkd");
		System.out.println(s);
		//System.out.println(PropertyResource.getInstance().getResourceBundle().keySet());
	}
}
