/*
 * $Id: PropertyBak.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
 * �û����Ի����û������ԣ������������ԣ�������Щ������ǰ̨ʹ�õģ�
 * ���Բ��ǻ������ԡ�
 * 
 * �����ļ���
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
				log.error("�����ļ��Ҳ������޷������û����飩����", e);
			} catch (IOException e) {
				log.error("�����û����飩�����ļ�����", e);
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
