package org.opoo.apps.cache.coherence;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.DefaultConfigurableCacheFactory;
import com.tangosol.net.cache.CacheStore;
import com.tangosol.util.ConcurrentMap;
import com.tangosol.util.WrapperConcurrentMap;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.cache.AbstractClusterTask;
import org.opoo.apps.cache.CacheStrategy;
import org.opoo.cache.Cache;
import org.springframework.util.ClassUtils;

/**
 * ±¾µØ»º´æ²ßÂÔ¡£
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
@SuppressWarnings("unchecked")
public class CoherenceLocalCacheFactory implements CacheStrategy {
	private static final Log log = LogFactory.getLog(CoherenceLocalCacheFactory.class);
	
	private static ConcurrentMap lockingMap = new WrapperConcurrentMap(new HashMap());

	public CoherenceLocalCacheFactory() {
	}

	public boolean startCluster() {
		return true;
	}

	public void stopCluster() {
	}

	public Cache createCache(String name) {
		DefaultConfigurableCacheFactory factory = (DefaultConfigurableCacheFactory) CacheFactory.getConfigurableCacheFactory();
		com.tangosol.net.DefaultConfigurableCacheFactory.CacheInfo info = factory.findSchemeMapping(name);
		if (info == null)
			throw new IllegalArgumentException((new StringBuilder()).append(
					"No cache defined in coherence-cache-config.xml with name ").append(name).toString());
		Map attrs = info.getAttributes();
		String maxSize = (String) attrs.get("back-size-high");
		int size = 0;
		if (maxSize != null)
			try {
				size = Integer.parseInt(maxSize);
			} catch (NumberFormatException nfe) {
				log.warn((new StringBuilder()).append("Cache ").append(name).append(
						" defined with invalid back-size-high param of ").append(maxSize).append(
						". Defaulting to unlimited size").toString(), nfe);
				size = 0;
			}
		String maxLifetime = (String) attrs.get("back-expiry");
		long lifetime = 0L;
		if (maxLifetime != null)
			try {
				lifetime = parseExpiryParameter(maxLifetime);
			} catch (NumberFormatException nfe) {
				log.warn((new StringBuilder()).append("Cache ").append(name).append(
						" defined with invalid back-expiry param of ").append(maxLifetime).append(
						" Defaulting to unlimited expiration time").toString(), nfe);
				lifetime = 0L;
			}
		lifetime = org.opoo.apps.cache.CacheFactory.getMaxLifetimeFromProperty(name, lifetime);
		size = org.opoo.apps.cache.CacheFactory.getMaxSizeFromProperty(name, size);
		String cacheStore = (String) attrs.get("cache-store-class");
		CacheStore cacheStoreInstance = null;
		if (cacheStore != null)
			try {
				log.debug((new StringBuilder()).append("Loading CacheStore implementation: ").append(cacheStore)
						.append(" for local cache: ").append(name).toString());
				cacheStoreInstance = (CacheStore) ClassUtils.forName(cacheStore).newInstance();
			} catch (Exception e) {
				log.error((new StringBuilder()).append("Unable to load CacheStore implementation class ").append(
						cacheStore).toString(), e);
			}
		CoherenceCache cache;
		// if(name.equals("Short-term Query"))
		// cache = cacheStoreInstance == null ? ((CoherenceCache) (new
		// ShortTermQueryCache(name, size, lifetime))) : ((CoherenceCache) (new
		// ShortTermQueryCache(name, size, lifetime, cacheStoreInstance)));
		// else
		cache = cacheStoreInstance == null ? new CoherenceCache(name, size, lifetime) : new CoherenceCache(name, size,
				lifetime, cacheStoreInstance);
		return cache;
	}

	public void destroyCache(Cache cache) {
		cache.clear();
	}

	public boolean isSeniorClusterMember() {
		return true;
	}

	public void doClusterTask(AbstractClusterTask abstractclustertask) {
	}

	public Collection doSynchronousClusterTask(AbstractClusterTask task, boolean includeLocalMember) {
		return Collections.emptyList();
	}

	public void updateCacheStats(Map map) {
	}

	public void lockKey(Object key, long timeout) {
		lockingMap.lock(key, timeout);
	}

	public void unlockKey(Object key) {
		lockingMap.unlock(key);
	}

	private long parseExpiryParameter(String param) {
		if (param.endsWith("H") || param.endsWith("h")) {
			String num = param.substring(0, param.length() - 1);
			long hours = Long.parseLong(num);
			return hours * 0x36ee80L;
		}
		if (param.endsWith("m") || param.endsWith("M")) {
			String num = param.substring(0, param.length() - 1);
			long minutes = Long.parseLong(num);
			return minutes * 60000L;
		}
		if (param.endsWith("s") || param.endsWith("S")) {
			String num = param.substring(0, param.length() - 1);
			long seconds = Long.parseLong(num);
			return seconds * 1000L;
		}
		if (param.endsWith("d") || param.endsWith("D")) {
			String num = param.substring(0, param.length() - 1);
			long days = Long.parseLong(num);
			return days * 0x83d600L;
		} else {
			return Long.parseLong(param);
		}
	}
}
