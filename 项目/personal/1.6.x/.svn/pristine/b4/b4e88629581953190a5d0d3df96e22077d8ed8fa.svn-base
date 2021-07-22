package org.opoo.ndao.support;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.Domain;

public abstract class DomainUtils {
	private static final Log log = LogFactory.getLog(DomainUtils.class);
	/**
	 * 将实体类的 List 集合构造成 Map 集合。
	 * @param <K>
	 * @param list
	 * @return
	 */
	public static <K extends Serializable,T extends Domain<K>> Map<K,T> domainListToMap(List<T> list){
		Map<K, T> map = new HashMap<K, T>();
		for (T domain : list) {
			map.put(domain.getId(), domain);
		}
		return map;
	}
	
	
	/**
	 * 将实体类的 List 集合构造成 Map 集合。
	 * @param <K>
	 * @param list
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@SuppressWarnings("unchecked")
	public static <K,T extends Domain<?>> Map<K,T> domainListToMap(List<T> list, String keyPropertyName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Map<K, T> map = new HashMap<K, T>();
		for (T domain : list) {
			K k = (K) PropertyUtils.getProperty(domain, keyPropertyName);
			if(map.containsKey(k)){
				log.warn("组装 Map 时主键重复，可能导致结果不正确：" + k);
			}
			map.put(k, domain);
		}
		return map;
	}
}
