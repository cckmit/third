package org.opoo.apps.cache.ehcache;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.Statistics;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.cache.Cache;
import org.opoo.cache.CacheSizeCalculator;
import org.springframework.dao.DataRetrievalFailureException;

/**
 * EhcacheÊµÏÖµÄ»º´æ¡£
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * @param <K>
 * @param <V>
 */
public class EhcacheBasedCache<K, V> implements Cache<K, V> {
	private static final Log log = LogFactory.getLog(EhcacheBasedCache.class);	
	private Ehcache cache;
	//private Statistics statistics;
	private CacheSizeCalculator calculator = new CacheSizeCalculator();
	public EhcacheBasedCache() {
		super();
	}

	public EhcacheBasedCache(Ehcache cache) {
		super();
		//this.cache = cache;
		setEhcache(cache);
	}


	public long getCacheHits() {
		return cache.getStatistics().getCacheHits();//cache.getHitCount();
	}

	public long getCacheMisses() {
		return  cache.getStatistics().getCacheMisses();//cache.getMissCountNotFound();
	}

	public int getCacheSize() {
		int size = 0;// CacheSizes.sizeOfObject();
		List<?> keys = cache.getKeys();
		for(Object key: keys){
			size += calculator.calculateUnits(key);
			Element element = cache.get(key);
			if(element != null){
				size += calculator.calculateUnits(element.getValue());
			}
		}
		return size;
		//return cache.getSize();
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

	@SuppressWarnings("unchecked")
	public Set<Map.Entry<K, V>> entrySet() {
		final List<K> keys = cache.getKeys();
		final Iterator<K> it = keys.iterator();
		AbstractSet<Entry<K, V>> set = new AbstractSet<Map.Entry<K, V>>(){
			@Override
			public Iterator<Map.Entry<K, V>> iterator() {
				return new Iterator<Entry<K,V>>(){
					public boolean hasNext() {
						return it.hasNext();
					}

					public java.util.Map.Entry<K, V> next() {
						final K key = it.next();
						if(key == null){
							return null;
						}
						
						return new Map.Entry<K, V>(){
							public K getKey() {
								return key;
							}

							public V getValue() {
								return get(key);
							}

							public V setValue(V value) {
								return put(key, value);
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
			}
		};
		return Collections.unmodifiableSet(set);
	}

	@SuppressWarnings("unchecked")
	public V get(Object key) {
        Element element = null;
        try {
            element = cache.get(key);
        } catch (CacheException cacheException) {
            throw new DataRetrievalFailureException(getName() + " Cache failure: " + cacheException.getMessage());
        }

        if (log.isDebugEnabled()) {
            log.debug(getName() + " Cache hit: " + (element != null) + "; key: " + key);
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

	@SuppressWarnings("unchecked")
	public Set<K> keySet() {
		return new LinkedHashSet<K>(cache.getKeys());
	}

	public V put(K key, V value) {
		Element e = new Element(key, value);
		
        if (log.isDebugEnabled()) {
            log.debug(getName() + " Cache put: " + e.getKey());
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

	@SuppressWarnings("unchecked")
	public Collection<V> values() {
		List keys = cache.getKeys();
		Collection<V> list = new ArrayList<V>();
		for(Object key: keys){
			Element element = cache.get(key);
			list.add((V) element.getValue());
		}
		return list;
	}

	/**
	 * @return the cache
	 */
	public Ehcache getEhcache() {
		return cache;
	}

	/**
	 * @param cache the cache to set
	 */
	public void setEhcache(Ehcache cache) {
		this.cache = cache;
		//statistics = cache.getStatistics();
	}
	

	public void setName(String name) {
		//this.cache.setName(name);
	}

	/**
	 * @return the statistics
	 */
	public Statistics getStatistics() {
		return cache.getStatistics();
	}
}
