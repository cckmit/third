package cn.redflagsoft.base.web.filter;

import java.lang.reflect.Array;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class IllegalCharacterRequest extends HttpServletRequestWrapper implements HttpServletRequest{
	/**
	 * @param request
	 */
	public IllegalCharacterRequest(HttpServletRequest request) {
		super(request);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
	 */
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		return filter(value);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameterMap()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getParameterMap() {
		Map map = super.getParameterMap();
		if(map == null || map.isEmpty()){
			return map;
		}
		
		Map newmap = new LinkedHashMap();
		
		Set keySet = map.keySet();
		for(Object key: keySet){
			Object value = map.get(key);
			value = filter(value);
			newmap.put(key, value);
		}
		return newmap;
	}


	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
	 */
	@Override
	public String[] getParameterValues(String name) {
		String[] arr = super.getParameterValues(name);
		return filter(arr);
	}
	
	
	public static String[] filter(String[] arr){
		if(arr == null || arr.length == 0){
			return arr;
		}
		
		for(int i = 0 ; i < arr.length ; i++){
			arr[i] = filter(arr[i]);
		}
		return arr;
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static Object filter(Object value) {
		if(value == null){
			return null;
		}
		if(value instanceof String){
			return filter((String) value);
		}
		
		if(value instanceof String[]){
			return filter((String[]) value);
		}
		
		if(value.getClass().isArray()){
			int len = Array.getLength(value);
			for(int i = 0 ; i < len; i++){
				Object val = Array.get(value, i);
				if(val instanceof String){
					val = filter((String)val);
					Array.set(value, i, val);
				}else if(val instanceof String[]){
					val = filter((String[])val);
					Array.set(value, i, val);
				}
			}
		}
		return value;
	}
	
	
	public static String filter(String input){
		if(input == null || input.length() == 0){
			return input;
		}
		
		input = input.replace('<', '£¼');
		input = input.replace('>', '£¾');
		input = input.replace('/', '£¯');
		input = input.replace('\"', '£¢');
		return input;
	}
}
