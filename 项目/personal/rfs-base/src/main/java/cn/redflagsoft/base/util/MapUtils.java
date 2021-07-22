/*
 * $Id: MapUtils.java 5949 2012-08-01 10:43:06Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.util;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

/**
 * Map�����ࡣ
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class MapUtils {

	/**
	 * ��ȡ String ���͵�ֵ��
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue Ĭ��ֵ����û��ʱʹ�õ�Ĭ��ֵ��
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
					System.err.println("����ΪList��������Ϊ0");
				}
			}else{
				System.err.println("δ֪�Ĳ������ͣ�" + object);
			}
		}
		return defaultValue;
	}
	
	/**
	 * ��ȡ String ���͵�ֵ��
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
	 * ��ȡShort���͵Ĳ���ֵ��
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static Short getShort(Map<?,?> map, String key){
		return MapUtils.getShort(map, key, null);
	}
	
	/**
	 * ��ȡShort���͵Ĳ���ֵ��
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue Ĭ��ֵ
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
