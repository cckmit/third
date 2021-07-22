package org.opoo.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


/**
 * Cache wrapper.
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * @param <K>
 * @param <V>
 */
public class CacheWrapper<K,V> implements Cache<K,V> {

	private Cache<K,V> cache;
	private boolean clearable = true;

	public CacheWrapper(Cache<K,V> cache) {
//		clearable = true;
		this.cache = cache;
	}

	public Cache<K,V> getWrappedCache() {
		return cache;
	}

	public void setWrappedCache(Cache<K,V> cache) {
		this.cache = cache;
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

	public void clear() {
		if (clearable){
			cache.clear();
		}
	}

	public boolean isEmpty() {
		return cache.isEmpty();
	}

	public boolean containsKey(Object key) {
		return cache.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return cache.containsValue(value);
	}

	public Collection<V> values() {
		return cache.values();
	}

	public void putAll(Map<? extends K, ? extends V> t) {
		cache.putAll(t);
	}

	public Set<Entry<K,V>> entrySet() {
		return cache.entrySet();
	}

	public Set<K> keySet() {
		return cache.keySet();
	}

	public V get(Object key) {
		return cache.get(key);
	}

	public V remove(Object key) {
		return cache.remove(key);
	}

	public V put(K key, V value) {
		return cache.put(key, value);
	}

	public Cache<K,V> getCache() {
		return cache;
	}

	public void setCache(Cache<K,V> cache) {
		this.cache = cache;
	}

	public boolean isClearable() {
		return clearable;
	}

	public void setClearable(boolean clearable) {
		this.clearable = clearable;
	}
}