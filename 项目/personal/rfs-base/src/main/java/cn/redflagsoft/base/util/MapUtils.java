/*
 * $Id: MapUtils.java 5949 2012-08-01 10:43:06Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.util;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

/**
 * Map工具类。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class MapUtils {

	/**
	 * 获取 String 类型的值。
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue 默认值，如没有时使用的默认值。
	 * @return
	 */
	public static String getString(Map<?,?> map, String key, String defaultValue){
		if(map == null){
			return defaultValue;
		}
		if(map instanceof MultivaluedMap){
			@SuppressWarnings("unchecked")
			MultivaluedMap<String,String> tmp = (MultivaluedMap<String,String>) map;
			String val = tmp.getFirst(key);
			return val != null ? val : defaultValue;
		}
		
		Object object = map.get(key);
		if(object != null){
			if(object instanceof String[] && ((String[])object).length > 0){
				return ((String[])object)[0];
			}else if(object instanceof String){
				return (String) object;
			}else if(object instanceof List){
				List<?> list = (List<?>) object;
				if(!list.isEmpty()){
					return list.iterator().next().toString();
				}else{
					System.err.println("参数为List，但长度为0");
				}
			}else{
				System.err.println("未知的参数类型：" + object);
			}
		}
		return defaultValue;
	}
	
	/**
	 * 获取 String 类型的值。
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static String getString(Map<?,?> map, String key){
		return MapUtils.getString(map, key, null);
	}
	
	/**
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(Map<?,?> map, String key, boolean defaultValue){
		String s = MapUtils.getString(map, key);
		if(s != null){
			try {
				return Boolean.parseBoolean(s);
			} catch (Exception e) {
				//igonre
				System.err.println(e.getMessage());
			}
		}
		return defaultValue;
	}
	
	/**
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(Map<?,?> map, String key){
		return MapUtils.getBoolean(map, key, false);
	}
	
	/**
	 * 获取Short类型的参数值。
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static Short getShort(Map<?,?> map, String key){
		return MapUtils.getShort(map, key, null);
	}
	
	/**
	 * 获取Short类型的参数值。
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue 默认值
	 * @return
	 */
	public static Short getShort(Map<?,?> map, String key, Short defaultValue){
		String s = MapUtils.getString(map, key);
		if(s != null){
			return new Short(s);
		}
		return defaultValue;
	}

	/**
	 * @param map
	 * @param string
	 * @return
	 */
	public static Integer getInteger(Map<?,?> map, String key) {
		return MapUtils.getInteger(map, key, null);
	}

	/**
	 * @param map
	 * @param key
	 * @param object
	 * @return
	 */
	public static Integer getInteger(Map<?, ?> map, String key, Integer defaultValue) {
		String s = MapUtils.getString(map, key);
		if(s != null){
			return new Integer(s);
		}
		return defaultValue;
	}
}
