package org.opoo.cache;


/**
 * 缓存大小计算器。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface Calculator {
	/**
	 * 计算指定对象的缓存大小。
	 * 
	 * 通常对对象所占的字节数而不是对象个数。
	 * @param object
	 * @return
	 */
	int calculateUnits(Object object);
}
