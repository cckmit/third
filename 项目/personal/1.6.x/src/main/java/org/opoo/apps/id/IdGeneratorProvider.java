package org.opoo.apps.id;

import java.io.Serializable;


/**
 * ID 生成器提供者。
 * 
 * 注意：系统中应该只存在一个IdGeneratorProvider的示例，特别是AbstractCachedIdGeneratorProvider，如果有多个
 * AbstractCachedIdGeneratorProvider的示例，可能产生重复的ID。
 * 
 * @author alex
 *
 * @param <T>
 */
public interface IdGeneratorProvider<T extends Serializable> {
	//<T extends Serializable> IdGenerator<T> getIdGenerator(String name, Class<T> cls);
	//<T extends Serializable> IdGenerator<T> getIdGenerator(long type, Class<T> cls);
	//<T extends Serializable, K extends Serializable> IdGenerator<T> getIdGenerator(K key);
	<K extends Serializable> IdGenerator<T> getIdGenerator(K key);
}
