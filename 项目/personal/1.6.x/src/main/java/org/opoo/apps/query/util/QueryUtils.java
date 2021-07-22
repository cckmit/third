/**
 * 
 */
package org.opoo.apps.query.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;

import com.google.common.collect.Lists;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.XWorkConverter;

/**
 * @author lcj
 *
 */
public abstract class QueryUtils {
	private static final Log log = LogFactory.getLog(QueryUtils.class);
	
	/**
	 * 获取 String 类型的值。
	 * 
	 * @param map
	 * @param key
	 * @param defaultValue 默认值，如没有时使用的默认值。
	 * @return
	 */
	public static String getStringValue(Map<?,?> map, String key, String defaultValue){
		Object object = map.get(key);
		if(object != null){
			if(object instanceof String[] && ((String[])object).length > 0){
				return ((String[])object)[0];
			}else if(object instanceof String){
				return (String) object;
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
	public static String getStringValue(Map<?,?> map, String key){
		return getStringValue(map, key, null);
	} 
	
	/**
	 * 
	 * @param stringValue
	 * @return
	 */
	public static String[] getInputStrings(String stringValue){
		StringTokenizer st = new StringTokenizer(stringValue, ",; ");
		List<String> list = new ArrayList<String>();
		while(st.hasMoreTokens()){
			list.add(st.nextToken());
		}
		return list.toArray(new String[list.size()]);
	}
	
	public static Object convertValue(String stringValue, Class<?> clazz) {
		if(clazz != null && clazz.isArray()){
			clazz = clazz.getComponentType();
//			String[] strings = getInputStrings(stringValue);
//			List<Object> results = Lists.newArrayList();
//			for(String str: strings){
//				if(StringUtils.isNotBlank(str)){
//					results.add(convertSingleValue(str, clazz));
//				}
//			}
//			return results.toArray();
			return convertArrayValue(stringValue, clazz);
		}else{
			return convertSingleValue(stringValue, clazz);
		}
	}
	
	public static Object[] convertArrayValue(String stringValue, Class<?> clazz){
		String[] strings = getInputStrings(stringValue);
		List<Object> results = Lists.newArrayList();
		for(String str: strings){
			if(StringUtils.isNotBlank(str)){
				results.add(convertSingleValue(str, clazz));
			}
		}
		return results.toArray();
	}
	
	public static Object convertSingleValue(String stringValue, Class<?> clazz){
		//String or null
		if(clazz == null || String.class.equals(clazz)){
			return stringValue;
		}
		//Boolean
		if(Boolean.class.equals(clazz)){
			return "1".equals(stringValue) || "true".equalsIgnoreCase(stringValue);
		}
		//Date
		if(Date.class.equals(clazz)){
			try{
				return AppsGlobals.parseDate(stringValue);
			}catch(Exception e){
				try{
					return AppsGlobals.parseDateTime(stringValue);
				}catch(Exception e2){
					try{
						return AppsGlobals.parseShortDateTime(stringValue);
					}catch(Exception e3){
						try{
							return new SimpleDateFormat().parse(stringValue);
						}catch(Exception e4){
							log.error("not a date string: " + stringValue, e);
							log.error("Parse date error", e2);
							log.error("Parse date error", e3);
							log.error("Parse date error", e4);
							//return null;
							throw new IllegalArgumentException(e4);
						}
					}
				}
			}
		}
		
		//else
		return XWorkConverter.getInstance().convertValue(
				ActionContext.getContext().getContextMap(), stringValue, clazz);
	}
}
