package org.opoo.apps.util;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.el.ExpressionFactory;
import org.opoo.apps.lifecycle.Application;
import org.opoo.util.ClassUtils;

public abstract class StringUtils extends org.apache.commons.lang.StringUtils{
	public static final Log log = LogFactory.getLog(StringUtils.class);
	
	/**
	 * @deprecated
	 */
	private static final char zeroArray[] 
	        = "0000000000000000000000000000000000000000000000000000000000000000".toCharArray();
	private static ExpressionFactory expressionFactory;
	
	
	/**
	 * 将一个Long型的数字输出成指定长度的字符串，数字长度不够字符串长度时，在前面补0。
	 * @param string
	 * @param length
	 * @return
	 * @deprecated
	 */
	public static final String zeroPadString0(String string, int length) {
		if (string == null || string.length() > length) {
			return string;
		} else {
			StringBuffer buf = new StringBuffer(length);
			buf.append(zeroArray, 0, length - string.length()).append(string);
			return buf.toString();
		}
	}
	
	public static final String zeroPadString(String string, int length){
		return StringUtils.leftPad(string, length, '0');
	}
	
	
	/**
	 * 解析字符串表达式。
	 * 
	 * <p>该方法使用了apps.el包的功能。
	 * 
	 * <p>除了默认的两种自定义工厂配置方式之外，该方法可以从应用程序属性中读取实现类类名。
	 * 		属性一般添加在数据库中和配置文件中，在运行时，如果应用启动了，将调用
	 *  		AppsGlobals.getProperty 从数据库中读取，如果应用没有启动，则调用
	 *  		AppsGlobals.getSetupProperty 从主配置文件中读取。
	 *  	属性名称为“apps.el.factoryClass”。
	 * 
	 * @param expr
	 * @param context
	 * @return
	 * @see ExpressionFactory
	 */
	public static String processExpression(String expr, Object context){
		if(expressionFactory == null){
			String factoryClassPropertyName = "apps.el.factoryClass";
			String className = null;
			
			if(Application.isInitialized() && Application.isContextInitialized()){
				className = AppsGlobals.getProperty(factoryClassPropertyName);
				log.debug("Get 'apps.el.factoryClass' from database: " + className);
			}else{
				className = AppsGlobals.getSetupProperty(factoryClassPropertyName);
				log.debug("Get 'apps.el.factoryClass' from setup properties: " + className);
			}
			
			if(className != null){
				expressionFactory = (ExpressionFactory) ClassUtils.newInstance(className);
				log.info("Using ExpressionFactory by startup: " + className);
			}
			if(expressionFactory == null){
				expressionFactory = ExpressionFactory.newInstance();
				log.info("Using ExpressionFactory by serviceId provider: " + expressionFactory.getClass().getName());
			}
		}
		
		return expressionFactory.getExpression(expr).getValue(context);
	}
	
	public static boolean isValidPropertyName(String name){
		if(StringUtils.isBlank(name)){
			return false;
		}
		name = name.trim();
		for(int i = 0, n = name.length() ; i < n ; i++){
			char c = name.charAt(i);
			//System.out.println(c);
			if(i == 0){//首字必须是字母、括号起
				if(!CharUtils.isAsciiAlpha(c) && (c != '_') && (c != '(')){
					return false;
				}
			}else if(i == (n - 1)){//结尾必须是字母或者数字、括号结束
				if(!CharUtils.isAsciiAlphanumeric(c) && (c != '_') && (c != ')')){
					return false;
				}
			}else{//其它位必须是字母、数字或者点、括号、加减乘除、空格等
				if((!CharUtils.isAsciiAlphanumeric(c)) && (c != '.') && ( c != '_') && (c != '(') && (c != ')') && (c != '+') && (c != '-')
						&& (c != '*') && (c != '/') && (c != ' ') && c != ','){
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean isValidMethodName(String name){
		if(StringUtils.isBlank(name)){
			return false;
		}
		name = name.trim();
		for(int i = 0, n = name.length() ; i < n ; i++){
			char c = name.charAt(i);
			if(i == 0){//首字必须是字母
				if(!CharUtils.isAsciiAlpha(c) && (c != '_')){
					return false;
				}
			}else{//其它位必须是字母、数字或者点
				if((!CharUtils.isAsciiAlphanumeric(c)) && ( c != '_')){
					return false;
				}
			}
		}
		return true;
	}

	public static void validatePropertyName(String name){
		if(!StringUtils.isValidPropertyName(name)){
			throw new IllegalArgumentException("Invalid value of property name"); 
		}
	}

	/**
	 * @deprecated using {@link OrderUtils#isValidSortDir(String)}
	 * @param dir
	 * @return
	 */
	public static boolean isValidSortDir(String dir){
		return OrderUtils.isValidSortDir(dir);
	}
	
	/**
	 * @deprecated using {@link OrderUtils#validateSortDir(String)}
	 * @param dir
	 */
	public static void validateSortDir(String dir){
		OrderUtils.validateSortDir(dir);
	}
}
