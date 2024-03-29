/*
 * $Id: ParametersUtils.java 5403 2012-03-06 09:01:19Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.ws.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import com.google.common.collect.Maps;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public abstract class ParametersUtils {

	/**
	 * 将 JaxRs 获得的参数格式 MultivaluedMap 转成 XWork2 的HTTP参数格式，以便于使用XWork2的OGNL引擎
	 * 来设置参数。
	 * @param map
	 * @return
	 */
	public static Map<String,?> multivalueMapToXWorkParametersMap(MultivaluedMap<String, String> map){
		Map<String, Object> result = Maps.newHashMap();
		for(Map.Entry<String, List<String>> en: map.entrySet()){
			List<String> list = en.getValue();
			if(list != null && !list.isEmpty()){
				if(list.size() > 0){
					result.put(en.getKey(), list.toArray(new String[list.size()]));
				}else{
					result.put(en.getKey(), list.iterator().next());
				}
			}
		}
		return result;
	}
}
