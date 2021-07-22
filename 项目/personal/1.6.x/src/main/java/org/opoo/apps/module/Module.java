package org.opoo.apps.module;


/**
 * 
 * 模块接口。
 * 自定义模块应该实现这个接口。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * @param <T>
 */
public interface Module<T> {

	/**
	 * 模块初始化。
	 */
	void init();
	
	/**
	 * 模块销毁。
	 */
	void destroy();
	
}
