/*
 * $Id: RFSPropertyNamespaceHandler.java 5480 2012-04-05 09:08:29Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.Conventions;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.google.common.base.Function;


/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class RFSPropertyNamespaceHandler implements NamespaceHandler {
	private static final Log log = LogFactory.getLog(RFSPropertyNamespaceHandler.class);
	
	
	public static final String PREFIX_SYSTEM = "system";
	public static final String PREFIX_SETUP = "setup";
	public static final String PREFIX_ENV = "env";
	public static final String PREFIX_PROPS = "props";
	public static final Map<String, Function<String, String>> functions;
	static{
		Function<String, String> env = new Function<String, String>() {
			public String apply(String from) {
				log.debug("Get property '" + from + "' from env");
				return System.getenv(from);
			}
		};
		Function<String, String> setup = new Function<String,String>(){
			public String apply(String from) {
				log.debug("Get property '" + from + "' from setup properties.");
				return AppsGlobals.getSetupProperty(from);
			}
		};
		Function<String, String> props = new Function<String,String>(){
			public String apply(String from) {
				log.debug("Get property '" + from + "' from properties.");
				try{
					return AppsGlobals.getProperty(from);
				}catch(Exception e){
					log.warn(e.getMessage());
				}
				return null;
			}
		};
		Function<String, String> system = new Function<String,String>(){
			public String apply(String from) {
				log.debug("Get property '" + from + "' from system properties.");
				return System.getProperty(from);
			}
		};
		
		Map<String, Function<String, String>> m = new HashMap<String, Function<String, String>>();
		m.put(PREFIX_ENV, env);
		m.put(PREFIX_PROPS, props);
		m.put(PREFIX_SETUP, setup);
		m.put(PREFIX_SYSTEM, system);
		
		functions = Collections.unmodifiableMap(m);
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
	 */
	public void init() {

		
	}
	
	

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.NamespaceHandler#parse(org.w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext)
	 */
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		parserContext.getReaderContext().error(
				"Class [" + getClass().getName() + "] does not support custom elements.", element);
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.NamespaceHandler#decorate(org.w3c.dom.Node, org.springframework.beans.factory.config.BeanDefinitionHolder, org.springframework.beans.factory.xml.ParserContext)
	 */
	public BeanDefinitionHolder decorate(Node node, BeanDefinitionHolder definition, ParserContext parserContext) {
		if (node instanceof Attr) {
			Attr attr = (Attr) node;
			String propertyName = attr.getLocalName();
			String propertyValue = attr.getValue();
			System.out.println(propertyName + ": " + propertyValue);
			MutablePropertyValues pvs = definition.getBeanDefinition().getPropertyValues();
			System.out.println(pvs);
			
			if (!pvs.contains(propertyName)) {
				parserContext.getReaderContext().error("Property '" + propertyName + "' is not defined using <property>.", attr);
			}
			
			if(StringUtils.isBlank(propertyValue)){
				parserContext.getReaderContext().error("Property '" + propertyName + "' value is required.", attr);
			}
			
			
			String prefix = PREFIX_PROPS;
			String newPropertyName = propertyValue;
			int index = propertyValue.indexOf(':');
			if(index != -1){
				prefix = propertyValue.substring(0, index);
				newPropertyName = propertyValue.substring(index + 1);
			}
			
			Function<String, String> function = functions.get(prefix);
			if(function == null){
				parserContext.getReaderContext().error("Property prefix '" + propertyName + "' is not permit.", attr);
			}
			
			String newPropertyValue = function.apply(newPropertyName);
			if(newPropertyValue != null){
				log.debug("Set property '" + propertyName + "' new value '" + newPropertyValue + "'");
				pvs.addPropertyValue(Conventions.attributeNameToPropertyName(propertyName), newPropertyValue);
			}
				
				
//			System.out.println("Get property '" + propertyValue + "' for '" + propertyName + "'.");
//			String property = System.getProperty(propertyValue);
//			if(property != null){
//				System.out.println("Set property '" + propertyName + "' new value '" + property + "'");
//				pvs.addPropertyValue(Conventions.attributeNameToPropertyName(propertyName), property);
//			}
			
			
			
			
//			if (pvs.contains(propertyName)) {
//				parserContext.getReaderContext().error("Property '" + propertyName + "' is already defined using " +
//						"both <property> and inline syntax. Only one approach may be used per property.", attr);
//			}
//			if (propertyName.endsWith(REF_SUFFIX)) {
//				propertyName = propertyName.substring(0, propertyName.length() - REF_SUFFIX.length());
//				pvs.addPropertyValue(
//						Conventions.attributeNameToPropertyName(propertyName), new RuntimeBeanReference(propertyValue));
//			}
//			else {
//				pvs.addPropertyValue(Conventions.attributeNameToPropertyName(propertyName), propertyValue);
//			}
		}else if(node instanceof Element){
			System.out.println("===>" + node.getClass());
			Element e = (Element)node;
			String name = e.getAttribute("name");
			System.out.println(name + " --> " + e.getAttribute("prop-name"));
		}
//		System.out.println("Node: " + node);
//		System.out.println(definition);
//		System.out.println(parserContext);
		return definition;
	}

}
