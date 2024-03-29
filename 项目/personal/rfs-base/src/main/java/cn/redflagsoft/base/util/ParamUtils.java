/*
 * $Id: ParamUtils.java 3825 2010-01-26 04:20:16Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.util;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.opoo.apps.AppsGlobals;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class ParamUtils {

	
	/**
	 * 获取指定参数。
	 * 
	 * <p>通常用去获取 Struts2 从前台取得的参数，其值通常是数组。
	 * 
	 * @param parameters
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getParameter(Map parameters, String name){
		Object value = parameters.get(name);
		if(value != null){
			if(value instanceof String[]){
				String[] values = (String[]) value;
				if(values.length > 0){
					return values[0];
				}
			}else{
				return value.toString();
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param parameters
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Date getDate(Map parameters, String name){
		String parameter = ParamUtils.getParameter(parameters, name);
		if(parameter != null){
			try {
				return AppsGlobals.parseDate(parameter);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param parameters
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Long getLong(Map parameters, String name){
		String parameter = ParamUtils.getParameter(parameters, name);
		if(parameter != null){
			return Long.valueOf(parameter);
		}
		return null;
	}
}
