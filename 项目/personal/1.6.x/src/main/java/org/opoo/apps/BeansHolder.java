package org.opoo.apps;

/**
 * 对象容器。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated replacement by appscontext
 */
public interface BeansHolder {
	/**
	 * 向容器中添加一个对象。
	 * 
	 * @param name 对象名称，标识
	 * @param object 对象
	 */
	void put(String name, Object object);
	
	/**
	 * 根据对象名称、标识查找一个对象。
	 * 
	 * @param name 对象名称，标识
	 * @return 对象
	 */
	Object get(String name);
	
	/**
	 * 根据对象名称、标识查找一个指定类型对象。
	 * 
	 * @param <T> 指定对象的类型参数
	 * @param name 对象名称，标识
	 * @param cls 指定对象的类型
	 * @return 对象
	 */
	<T> T get(String name, Class<T> cls);
}
