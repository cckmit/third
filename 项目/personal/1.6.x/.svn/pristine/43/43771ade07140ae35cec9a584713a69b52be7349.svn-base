package org.opoo.cache;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.opoo.util.Assert;


/**
 * 内部通过 JDK 的  Map 来实现的缓存对象。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * @param <K>
 * @param <V>
 * @see java.util.Map
 * @see java.util.concurrent.ConcurrentHashMap
 */
public class MapCache<K, V> implements Cache<K, V>, Serializable {
	private static final long serialVersionUID = 5932391458799515579L;
	private int maxCacheSize = Integer.MAX_VALUE;
	private long maxLifetime = Long.MAX_VALUE;
	private String name;
	private int cacheHits = 0;
	private int cacheMisses = 0;
	private Map<K, CacheValue<V>> map = new ConcurrentHashMap<K, CacheValue<V>>();
	
	public MapCache(){
//		map = new HashMap<K, CacheValue<V>>();
	}
	
	public MapCache(String name){
		this();
		this.name = name;
	}
	
	
	
	public long getCacheHits() {
		return cacheHits;
	}

	public long getCacheMisses() {
		return cacheMisses;
	}

	public int getCacheSize() {
		return size();
	}

	public int getMaxCacheSize() {
		return maxCacheSize;
	}

	public long getMaxLifetime() {
		return maxLifetime;
	}

	public String getName() {
		return name;
	}

	public void setMaxCacheSize(int arg0) {
		this.maxCacheSize = arg0;
	}

	public void setMaxLifetime(long arg0) {
		this.maxLifetime = arg0;
	}


	public V get(Object key) {
		CacheValue<V> cv = map.get(key);
		if(cv == null){
			cacheMisses++;
			return null;
		}else{
			
			//过期
			if((System.currentTimeMillis() - cv.timestamp) > maxLifetime){
				map.remove(key);
				return null;
			}
			cacheHits++;
			return cv.value;
		}
		//return cv.value;
	}


	public V put(K key, V value) {
		if(size() >= maxCacheSize){
			throw new IllegalArgumentException("缓存数据已经达到最大值。");
		}
		CacheValue<V> cv = map.put(key, new CacheValue<V>(value));
		return cv != null ? cv.value : null;
	}


	public void putAll(Map<? extends K, ? extends V> m) {
		for(Map.Entry<? extends K, ? extends V> en:m.entrySet()){
			put(en.getKey(), en.getValue());
		}
	}
	


	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean containsValue(Object value) {
		if(value == null) return false;
		return map.containsValue(new CacheValue(value));
	}

	/**
	 * 
	 */
	public Set<Map.Entry<K, V>> entrySet() {
		final Set<java.util.Map.Entry<K, CacheValue<V>>> set = map.entrySet();
		return new AbstractSet<Map.Entry<K, V>>(){
			@Override
			public Iterator<Map.Entry<K, V>> iterator() {
				final Iterator<Map.Entry<K, CacheValue<V>>> it = set.iterator();
				return new Iterator<java.util.Map.Entry<K, V>>(){
					public boolean hasNext() {
						return it.hasNext();
					}
					public Map.Entry<K, V> next() {
						final Map.Entry<K, CacheValue<V>> entry = it.next();
						if(entry == null){
							return null; 
						}
						return new Map.Entry<K, V>(){
							public K getKey() {
								return entry.getKey();
							}
							public V getValue() {
								return entry.getValue().value;
							}
							public V setValue(V value) {
								CacheValue<V> cv = entry.setValue(new CacheValue<V>(value));
								return cv.value;
							}
						};
					}

					public void remove() {
						it.remove();			
					}
				};
			}

			@Override
			public int size() {
				return set.size();
			}
		};
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public V remove(Object key) {
		CacheValue<V> cv = map.remove(key);
		if(cv == null){
			return null;
		}
		return cv.value;
	}

	public int size() {
		return map.size();
	}

	public Collection<V> values() {
		final Collection<CacheValue<V>> values = map.values();
		if(values == null){
			return null;
		}
		return new AbstractCollection<V>(){
			@Override
			public Iterator<V> iterator() {
				final Iterator<CacheValue<V>> it = values.iterator();
				return new Iterator<V>(){
					public boolean hasNext() {
						return it.hasNext();
					}

					public V next() {
						CacheValue<V> cv = it.next();
						return cv != null ? cv.value : null;
					}

					public void remove() {
						it.remove();
					}
				};
			}
			@Override
			public int size() {
				return values.size();
			}
		};
	}

	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Cache值对象。
	 *
	 * @param <V>
	 */
	private static class CacheValue<V>{
		public V value;
		public long timestamp = System.currentTimeMillis();
		public CacheValue(V value){
			Assert.notNull(value);
			this.value = value;
		}
		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object o){
			if(!(o instanceof CacheValue)){
				return false;
			}
			CacheValue<V> tmp = (CacheValue<V>) o;
			return value.equals(tmp.value);
		}

		@Override
		public int hashCode() {
			if(value != null){
				return value.hashCode();
			}
			return super.hashCode();
		}
	}

}
