package org.opoo.apps;

import java.util.HashMap;
import java.util.Map;


/**
 * 默认的对象容器。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated
 */
public class DefaultBeansHolder implements BeansHolder {
	private Map<String, Object> map = new HashMap<String, Object>();
	public Object get(String name) {
		return map.get(name);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String name, Class<T> cls) {
		return (T) map.get(name);
	}

	public void put(String name, Object object) {
		map.put(name, object);
	}
}
