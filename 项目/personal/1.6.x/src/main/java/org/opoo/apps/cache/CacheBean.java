package org.opoo.apps.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.opoo.cache.Cache;
import org.opoo.cache.CacheWrapper;


/**
 * »º´æ¡£
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * @param <K>
 * @param <V>
 */
public class CacheBean<K, V> implements Cache<K, V> {

	private Cache<K, V> cache;

	public CacheBean(String name) {
		this(name, true);
	}

	@SuppressWarnings("unchecked")
	public CacheBean(String name, boolean clearable) {
		cache = CacheFactory.createCache(name);
		if (cache instanceof CacheWrapper)
			((CacheWrapper<K, V>) cache).setClearable(clearable);
	}

	public String getName() {
		return cache.getName();
	}

	public void setName(String name) {
		cache.setName(name);
	}

	public int getMaxCacheSize() {
		return cache.getMaxCacheSize();
	}

	public void setMaxCacheSize(int maxSize) {
		cache.setMaxCacheSize(maxSize);
	}

	public long getMaxLifetime() {
		return cache.getMaxLifetime();
	}

	public void setMaxLifetime(long maxLifetime) {
		cache.setMaxLifetime(maxLifetime);
	}

	public int getCacheSize() {
		return cache.getCacheSize();
	}

	public long getCacheHits() {
		return cache.getCacheHits();
	}

	public long getCacheMisses() {
		return cache.getCacheMisses();
	}

	public int size() {
		return cache.size();
	}

	public boolean isEmpty() {
		return cache.isEmpty();
	}

	public boolean containsKey(Object o) {
		return cache.containsKey(o);
	}

	public boolean containsValue(Object o) {
		return cache.containsValue(o);
	}

	public V get(Object o) {
		return cache.get(o);
	}

	public V put(K k, V v) {
		return cache.put(k, v);
	}

	public V remove(Object o) {
		return cache.remove(o);
	}

	public void putAll(Map<? extends K, ? extends V> map) {
		cache.putAll(map);
	}

	public void clear() {
		cache.clear();
	}

	public Set<K> keySet() {
		return cache.keySet();
	}

	public Collection<V> values() {
		return cache.values();
	}

	public Set<Map.Entry<K, V>> entrySet() {
		return cache.entrySet();
	}

	public boolean equals(Object o) {
		return cache.equals(o);
	}

	public int hashCode() {
		return cache.hashCode();
	}

	public Cache<K, V> getWrappedCache() {
		return cache;
	}
}