package org.opoo.apps.id;

import java.io.Serializable;


/**
 * ID 生成器。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 * @param <T> id的类型
 */
public interface IdGenerator<T extends Serializable> {
	/**
	 * 获取下一个ID
	 * @return
	 */
	T getNext();
	
	/**
	 * 获取当前ID
	 * @return
	 */
	T getCurrent();
}
