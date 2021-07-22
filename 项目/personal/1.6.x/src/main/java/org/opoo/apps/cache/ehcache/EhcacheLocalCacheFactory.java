package org.opoo.apps.cache.ehcache;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.cache.AbstractClusterTask;
import org.opoo.apps.cache.CacheStrategy;
import org.opoo.cache.Cache;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.ehcache.EhCacheFactoryBean;


/**
 * Ehcache实现的缓存策略。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class EhcacheLocalCacheFactory implements CacheStrategy, InitializingBean {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private CacheManager cacheManager;

	public void afterPropertiesSet() throws Exception {
		// If no CacheManager given, fetch the default.
		if (this.cacheManager == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Using default EHCache CacheManager for cache region");
			}
			this.cacheManager = CacheManager.getInstance();
		}
	}
	
	/*
	private Ehcache createEhcache(String name){
		// Fetch cache region: If none with the given name exists,
		// create one on the fly.
		Ehcache rawCache = null;
		if (this.cacheManager.cacheExists(name)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Using existing EHCache cache region '" + name + "'");
			}
			rawCache = this.cacheManager.getEhcache(name);
		}
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("Creating new EHCache cache region '" + name + "'");
			}
			rawCache = createNewCache(name);
			this.cacheManager.addCache(rawCache);
		}
		return rawCache;
	}
	*/

	
	/**
	 * Create a raw Cache object based on the configuration of this FactoryBean.
	 */
	private Ehcache createNewCache(String name) {
		EhCacheFactoryBean bean = new EhCacheFactoryBean();
		bean.setCacheManager(cacheManager);
		bean.setCacheName(name);
		try {
			bean.afterPropertiesSet();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
		return (Ehcache) bean.getObject();
	}
	
	
	public <K, V> Cache<K, V> createCache(String name) {
		Ehcache ec = createNewCache(name);
		return new EhcacheBasedCache<K, V>(ec);
	}

	public <K, V> void destroyCache(Cache<K, V> cache) {
		cache.clear();
		
		//是否要清理
//		cacheManager.removeCache(cache.getName());
	}

	public void doClusterTask(AbstractClusterTask abstractclustertask) {
		// TODO Auto-generated method stub
	}

	public Collection<?> doSynchronousClusterTask(AbstractClusterTask abstractclustertask, boolean flag) {
		return Collections.emptyList();
	}

	public boolean isSeniorClusterMember() {
		// TODO Auto-generated method stub
		return true;
	}

	public void lockKey(Object obj, long l) {
		// TODO Auto-generated method stub
	}

	public boolean startCluster() {
		// TODO Auto-generated method stub
		return false;
	}

	public void stopCluster() {
		// TODO Auto-generated method stub

	}

	public void unlockKey(Object obj) {
		// TODO Auto-generated method stub

	}

	public void updateCacheStats(Map<String, Cache<?, ?>> map) {
		// TODO Auto-generated method stub
	}

	/**
	 * @param cacheManager the cacheManager to set
	 */
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
}
