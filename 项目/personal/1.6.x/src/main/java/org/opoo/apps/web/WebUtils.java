package org.opoo.apps.web;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;

import org.opoo.apps.AppsGlobals;
import org.opoo.apps.AppsHome;

/**
 * HttpServletRequest取参数相关的工具类。
 * @author alex
 *
 */
public abstract class WebUtils {
	
	public static int getInt(HttpServletRequest request, String name, int defaultValue) {
		try {
			return Integer.parseInt(request.getParameter(name));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static int getInt(HttpServletRequest request, String name) {
		return getInt(request, name, 0);
	}
	
	public static long getLong(HttpServletRequest request, String name,
			long defaultValue){
		try {
			return Long.parseLong(request.getParameter(name));
		} catch (Exception e) {
			return defaultValue;
		}
	}
	public static long getLong(HttpServletRequest request, String name){
		return getLong(request, name, 0L);
	}

	public static Date getDate(HttpServletRequest request, String name,
			Date defaultValue) {
		try {
			return AppsGlobals.parseDate(request.getParameter(name));
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static Date getDate(HttpServletRequest request, String name) {
		return getDate(request, name, (Date)null);
	}

	public static Date getDateTime(HttpServletRequest request, String name,
			Date defaultValue) {
		try {
			return AppsGlobals.parseDateTime(request.getParameter(name));
		} catch (Exception e) {
			return defaultValue;
		}
	}
	public static Date getDateTime(HttpServletRequest request, String name) {
		return getDateTime(request, name, null);
	}
	
	public static Date getDate(HttpServletRequest request, String name,
			Date defaultValue, String style){
		SimpleDateFormat f = new SimpleDateFormat(style);
		try {
			return f.parse(request.getParameter(name));
		} catch (ParseException e) {
			return defaultValue;
		}
	}
	
	public static Date getDate(HttpServletRequest request, String name, String style){
		return getDate(request, name, null, style);
	}
	
	
	
	/**
	 * 将 FilterConfig 中的 InitParameter 组装成Map。
	 * 
	 * @param filterConfig
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> initParametersToMap(FilterConfig filterConfig){
		Enumeration<String> names = filterConfig.getInitParameterNames();
		Map<String,String> map = new HashMap<String,String>();
		while(names.hasMoreElements()){
			String name = names.nextElement();
			String value = filterConfig.getInitParameter(name);
			map.put(name, value);
		}
		return map;
	}
	
	
	
	
	
	
    protected static final String canonicalURI(String s) {
        if (s == null) return null;
        StringBuffer result = new StringBuffer();
        final int len = s.length();
        int pos = 0;
        while (pos < len) {
            char c = s.charAt(pos);
            if ( isPathSeparator(c) ) {
                /*
                 * multiple path separators.
                 * 'foo///bar' -> 'foo/bar'
                 */
                while (pos+1 < len && isPathSeparator(s.charAt(pos+1))) {
                    ++pos;
                }

                if (pos+1 < len && s.charAt(pos+1) == '.') {
                    /*
                     * a single dot at the end of the path - we are done.
                     */
                    if (pos+2 >= len) break;

                    switch (s.charAt(pos+2)) {
                        /*
                         * self directory in path
                         * foo/./bar -> foo/bar
                         */
                    case '/':
                    case '\\':
                        pos += 2;
                        continue;

                        /*
                         * two dots in a path: go back one hierarchy.
                         * foo/bar/../baz -> foo/baz
                         */
                    case '.':
                        // only if we have exactly _two_ dots.
                        if (pos+3 < len && isPathSeparator(s.charAt(pos+3))) {
                            pos += 3;
                            int separatorPos = result.length()-1;
                            while (separatorPos >= 0 && 
                                   ! isPathSeparator(result
                                                     .charAt(separatorPos))) {
                                --separatorPos;
                            }
                            if (separatorPos >= 0)
                                result.setLength(separatorPos);
                            continue;
                        }
                    }
                }
            }
            result.append(c);
            ++pos;
        }
        return result.toString();
     }
    
    protected static final boolean isPathSeparator(char c) {
        return (c == '/' || c == '\\');
     }
	
	
	public static void main(String[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, MalformedURLException{
		Method m = WebUtils.class.getDeclaredMethod("test", String.class);
		System.out.println(m);
		m.setAccessible(true);
//		m.invoke(new WebUtils(), "sadsd");
		
		AppsHome.initialize();
		
		System.out.println(new URL("file:///c:/apps"));
		
		
		System.out.println(canonicalURI("c:\\asdas\\asdasd.txt"));
	}
}
