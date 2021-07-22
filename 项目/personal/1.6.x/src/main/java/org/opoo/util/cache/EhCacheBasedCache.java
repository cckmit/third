package org.opoo.util.cache;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.cache.ehcache.EhcacheBasedCache;
import org.opoo.util.Cache;
import org.springframework.dao.DataRetrievalFailureException;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated use EhcacheBasedCache
 * @param <K>
 * @param <V>
 */
@SuppressWarnings("unchecked")
public class EhCacheBasedCache<K, V> implements Cache<K, V> {
	private static final Log log = LogFactory.getLog(EhCacheBasedCache.class);	
	private Ehcache cache;
	
	public EhCacheBasedCache() {
		super();
		log.warn("This class is deprecated, please use '" 
				+ EhcacheBasedCache.class.getName()
				+ "' for replacement.");
	}
	
	public long getCacheHits() {
		return cache.getStatistics().getCacheHits();
	}

	public long getCacheMisses() {
		return cache.getStatistics().getCacheMisses();
	}

	public int getCacheSize() {
		return cache.getSize();
	}

	public int getMaxCacheSize() {
		return cache.getMaxElementsInMemory();
	}

	public long getMaxLifetime() {
		return cache.getTimeToLiveSeconds() * 1000;
	}

	public String getName() {
		return cache.getName();
	}

	public void setMaxCacheSize(int arg0) {
	}

	public void setMaxLifetime(long arg0) {

	}

	public void clear() {
		cache.removeAll();
		if(log.isDebugEnabled()){
			log.debug("clear cache: " + cache);
		}
	}

	public boolean containsKey(Object key) {
		return cache.get(key) != null;
	}

	public boolean containsValue(Object value) {
		return cache.isValueInCache(value);
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		final List keys = cache.getKeys();
		return new AbstractSet<java.util.Map.Entry<K, V>>(){

			@Override
			public Iterator<java.util.Map.Entry<K, V>> iterator() {
				final Iterator it = keys.iterator();
				return new Iterator<java.util.Map.Entry<K, V>>(){
					public boolean hasNext() {
						return it.hasNext();
					}

					public java.util.Map.Entry<K, V> next() {
						final Object key = it.next();
						if(key == null){
							return null;
						}
						
						return new java.util.Map.Entry<K, V>(){
							public K getKey() {
								return (K) key;
							}

							public V getValue() {
								return get(key);
							}

							public V setValue(V value) {
								return put((K) key, value);
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
				return keys.size();
			}};
	}

	public V get(Object key) {
        Element element = null;
        try {
            element = cache.get(key);
        } catch (CacheException cacheException) {
            throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage());
        }

        if (log.isDebugEnabled()) {
            log.debug("Cache hit: " + (element != null) + "; key: " + key);
        } 
		//Element element = cache.get(key);
		if(element != null){
			return (V) element.getValue();
		}
		return null;
	}

	public boolean isEmpty() {
		return cache.getSize() == 0;
	}

	public Set<K> keySet() {
		return new LinkedHashSet<K>(cache.getKeys());
	}

	public V put(K key, V value) {
		Element e = new Element(key, value);
		
        if (log.isDebugEnabled()) {
            log.debug("Cache put: " + e.getKey());
        }
        
		cache.put(e);
		return value;
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		for(Map.Entry<? extends K, ? extends V> en:m.entrySet()){
			put(en.getKey(), en.getValue());
		}
	}

	public V remove(Object key) {
		if (log.isDebugEnabled()) {
            log.debug("Cache remove: " + key);
        }
		cache.remove(key);
		return null;
	}

	public int size() {
		return cache.getSize();
	}

	public Collection<V> values() {
		return null;
	}

	/**
	 * @return the cache
	 */
	public Ehcache getCache() {
		return cache;
	}

	/**
	 * @param cache the cache to set
	 */
	public void setCache(Ehcache cache) {
		this.cache = cache;
	}
}
