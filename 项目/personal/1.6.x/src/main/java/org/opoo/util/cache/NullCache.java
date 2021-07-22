package org.opoo.util.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.opoo.util.Cache;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated
 * @param <K>
 * @param <V>
 */
public class NullCache<K, V> implements Cache<K, V> {
	private String name;
	
	public NullCache(){
	}
	public NullCache(String name){
		this.name = name;
	}
	
	public long getCacheHits() {
		return 0;
	}

	public long getCacheMisses() {
		return 0;
	}

	public int getCacheSize() {
		return 0;
	}

	public int getMaxCacheSize() {
		return 0;
	}

	public long getMaxLifetime() {
		return 0;
	}

	public String getName() {
		return name;
	}

	public void setMaxCacheSize(int arg0) {

	}

	public void setMaxLifetime(long arg0) {

	}

	public void clear() {

	}

	public boolean containsKey(Object key) {
		return false;
	}

	public boolean containsValue(Object value) {
		return false;
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return null;
	}

	public V get(Object key) {
		return null;
	}

	public boolean isEmpty() {
		return false;
	}

	public Set<K> keySet() {
		return null;
	}

	public V put(K key, V value) {
		return null;
	}

	public void putAll(Map<? extends K, ? extends V> m) {

	}

	public V remove(Object key) {
		return null;
	}

	public int size() {
		return 0;
	}

	public Collection<V> values() {
		return null;
	}
}
