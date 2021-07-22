package org.opoo.apps.cache.coherence;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.cache.Cache;

import com.tangosol.net.BackingMapManager;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.DefaultConfigurableCacheFactory;
import com.tangosol.net.MemberListener;
import com.tangosol.net.NamedCache;
import com.tangosol.net.cache.NearCache;
import com.tangosol.net.cache.ReadWriteBackingMap;
import com.tangosol.util.Filter;
import com.tangosol.util.InvocableMap;
import com.tangosol.util.QueryMap;
import com.tangosol.util.ValueExtractor;


/**
 * 支持集群的缓存。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
@SuppressWarnings("unchecked")
public class ClusteredCache implements Cache, QueryMap, InvocableMap {
	private static final Log log = LogFactory.getLog(ClusteredCache.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	protected NamedCache map;
	private Cache backingCache;

	protected ClusteredCache(String name) {

		NamedCache cache = CacheFactory.getCache(name);
		init(name, cache);
	}

	protected ClusteredCache(String name, NamedCache cache) {
		init(name, cache);
	}

	private void init(String name, NamedCache cache) {
		map = cache;
		BackingMapManager backingManager = cache.getCacheService().getBackingMapManager();
		Map mapBacking = null;
		if (backingManager instanceof DefaultConfigurableCacheFactory.Manager) {
			DefaultConfigurableCacheFactory.Manager actualManager = (DefaultConfigurableCacheFactory.Manager) backingManager;
			int count = 0;
			for (mapBacking = actualManager.getBackingMap(name); mapBacking == null && count < 5; mapBacking = actualManager
					.getBackingMap(name)) {
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
				}
				count++;
			}

			if (mapBacking instanceof ReadWriteBackingMap) {
				ReadWriteBackingMap readWriteMap = (ReadWriteBackingMap) mapBacking;
				Map realBackingMap = readWriteMap.getInternalCache();
				if (realBackingMap instanceof Cache)
					backingCache = (Cache) realBackingMap;
			} else if (mapBacking instanceof Cache)
				backingCache = (Cache) mapBacking;
		}
		if (backingCache == null)
			throw new IllegalStateException((new StringBuilder()).append("Unable to access backing cache for ").append(
					name).append(". BackingMapManager is a ").append(backingManager.getClass().getName()).append(
					" and backing map is ").append(mapBacking == null ? "null" : mapBacking.getClass().getName())
					.toString());
		backingCache.setName(name);
		int size = backingCache.getMaxCacheSize();
		long lifetime = backingCache.getMaxLifetime();
		int overrideSize = org.opoo.apps.cache.CacheFactory.getMaxSizeFromProperty(name, size);
		long overrideLifetime = org.opoo.apps.cache.CacheFactory
				.getMaxLifetimeFromProperty(name, lifetime);
		if (overrideSize != size)
			backingCache.setMaxCacheSize(overrideSize);
		if (overrideLifetime != lifetime)
			backingCache.setMaxLifetime(overrideLifetime);
	}

	public void addMemberListener(MemberListener listener) {
		map.getCacheService().addMemberListener(listener);
	}

	public void removeMemberListener(MemberListener listener) {
		map.getCacheService().removeMemberListener(listener);
	}

	public String getName() {
		return backingCache.getName();
	}

	public void setName(String name) {
		backingCache.setName(name);
	}

	public Object put(Object key, Object object) {
		if(IS_DEBUG_ENABLED){
			log.debug(getName() + " Cache put: " + key);
		}
		return map.put(key, object);
	}

	public Object get(Object key) {
		if(IS_DEBUG_ENABLED){
			log.debug(getName() + " Cache hit: ; key: " + key);
		}
		return map.get(key);
	}

	public Object remove(Object key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public int size() {
		return backingCache.size();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public Set entrySet() {
		return map.entrySet();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set keySet() {
		return map.keySet();
	}

	public void putAll(Map entries) {
		map.putAll(entries);
	}

	public Collection values() {
		return map.values();
	}

	public long getCacheHits() {
		if (map instanceof NearCache)
			return ((NearCache) map).getCacheStatistics().getCacheHits();
		if (backingCache != null)
			return backingCache.getCacheHits();
		else
			return -1L;
	}

	public long getCacheMisses() {
		if (map instanceof NearCache)
			return ((NearCache) map).getCacheStatistics().getCacheMisses();
		if (backingCache != null)
			return backingCache.getCacheMisses();
		else
			return -1L;
	}

	public int getCacheSize() {
		return backingCache.getCacheSize();
	}

	public int getMaxCacheSize() {
		return backingCache.getMaxCacheSize();
	}

	public void setMaxCacheSize(int maxSize) {
		backingCache.setMaxCacheSize(maxSize);
	}

	public long getMaxLifetime() {
		return backingCache.getMaxLifetime();
	}

	public void setMaxLifetime(long maxLifetime) {
		backingCache.setMaxLifetime(maxLifetime);
	}

	public void destroy() {
		map.destroy();
	}

	public Object invoke(Object object, com.tangosol.util.InvocableMap.EntryProcessor entryProcessor) {
		return map.invoke(object, entryProcessor);
	}

	public Map invokeAll(Collection collection, com.tangosol.util.InvocableMap.EntryProcessor entryProcessor) {
		return map.invokeAll(collection, entryProcessor);
	}

	public Map invokeAll(Filter filter, com.tangosol.util.InvocableMap.EntryProcessor entryProcessor) {
		return map.invokeAll(filter, entryProcessor);
	}

	public Object aggregate(Collection collection, com.tangosol.util.InvocableMap.EntryAggregator entryAggregator) {
		return map.aggregate(collection, entryAggregator);
	}

	public Object aggregate(Filter filter, com.tangosol.util.InvocableMap.EntryAggregator entryAggregator) {
		return map.aggregate(filter, entryAggregator);
	}

	public Set keySet(Filter filter) {
		return map.keySet(filter);
	}

	public Set entrySet(Filter filter) {
		return map.entrySet(filter);
	}

	public Set entrySet(Filter filter, Comparator comparator) {
		return map.entrySet(filter, comparator);
	}

	public void addIndex(ValueExtractor valueExtractor, boolean sorted, Comparator comparator) {
		map.addIndex(valueExtractor, sorted, comparator);
	}

	public void removeIndex(ValueExtractor valueExtractor) {
		map.removeIndex(valueExtractor);
	}
}
