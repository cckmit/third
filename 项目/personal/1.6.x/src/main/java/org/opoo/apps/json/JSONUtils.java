package org.opoo.apps.json;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * JSON工具类。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class JSONUtils {
    //private static Class serialNumberClass = AlphaSerialNumber.class;
	//private static final Log log = LogFactory.getLog(JSONUtils.class);
	
	/**
	 * 请求是否支持gzip。
	 * @param request
	 * @return
	 */
    public static boolean isGzipInRequest(HttpServletRequest request) {
        String header = request.getHeader("Accept-Encoding");
        return header != null && header.indexOf("gzip") >= 0;
    }

    /**
     * 当list中每条数据都相同类型时使用这个，查询中，大部分属于这种情况。
     * 使用这个函数减少了判断次数，提高效率。
     * @param list List
     * @return List
     */
    @SuppressWarnings("unchecked")
	public static <T> List<T> toJSONList(List list) {
        if (list == null) {
            return null;
        }
        if (list.size() > 0) {
            if (list.get(0).getClass().isArray()) {
                for (int i = 0; i < list.size(); i++) {
                    Object obj = list.get(i);
                    list.set(i, arrayToMap(obj));
                }
            }else if(list.get(0) instanceof List){
                for (int i = 0; i < list.size(); i++) {
                    Object obj = list.get(i);
                    list.set(i, listToMap(obj));
                }
            }
        }
        return list;
    }



    /**
     * 当list中每条数据都可能不同类型时使用这个
     * @param list List
     * @return List
     */
    @SuppressWarnings("unchecked")
	public static List toJSONList2(List list) {
        if (list == null) {
            return null;
        }
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            if (obj.getClass().isArray()) {
                list.set(i, arrayToMap(obj));
            } else if (obj instanceof List) {
                list.set(i, listToMap(obj));
            }
        }
        return list;
    }

    
    /**
     * 
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
	private static Map listToMap(Object obj){
        List list = (List) obj;
        Map map = new HashMap(list.size());
        SerialNumber sn = new AlphaSerialNumber();
        for(int i = 0 ; i < list.size() ; i++){
            //map.put("x" + i, list.get(i));
            map.put(sn.next(), list.get(i));
        }
        return map;
    }
    
    /**
     * 
     * @param array
     * @return
     */
    @SuppressWarnings("unchecked")
	private static Map arrayToMap(Object array) {
        int len = Array.getLength(array);
        Map map = new HashMap(len);
        SerialNumber sn = new AlphaSerialNumber();
        //log.debug("using SerialNumber: " + sn);
        for(int i = 0 ; i < len ; i++){
            map.put(sn.next(), Array.get(array, i));
        }
        return map;
    }
}
