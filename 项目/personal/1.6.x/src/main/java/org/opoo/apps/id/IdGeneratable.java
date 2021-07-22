package org.opoo.apps.id;

import java.io.Serializable;

/**
 * 可生成id的类接口。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 * @param <T>
 */
public interface IdGeneratable<T extends Serializable> {
	/**
	 * 获取ID生成器
	 * @return
	 */
	IdGenerator<T> getIdGenerator();
}
