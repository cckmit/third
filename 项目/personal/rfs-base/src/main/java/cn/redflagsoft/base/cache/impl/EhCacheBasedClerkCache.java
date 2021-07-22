package cn.redflagsoft.base.cache.impl;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.cache.ClerkCache;

public class EhCacheBasedClerkCache implements ClerkCache, InitializingBean {
	private static final Log log = LogFactory.getLog(EhCacheBasedClerkCache.class);	
	private Ehcache cache;
	//private static final String CACHE_NAME = "ClerkCache";
	//private static CacheManager cacheManager;
	
	//static {
	//	cacheManager = CacheManager.create();
	//	cacheManager.addCache(CACHE_NAME);
	//}

	public Clerk getClerkFromCache(Long id) {
        Element element = null;
        try {
            element = cache.get(id);
        } catch (CacheException cacheException) {
            throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage());
        }

        if (log.isDebugEnabled()) {
            log.debug("Cache hit: " + (element != null) + "; clerkid: " + id);
        } 
        
        if (element == null) {
            return null;
        } else {
            return (Clerk) element.getValue();
        }
	}

	public void putClerkIntoCache(Clerk clerk) {
        Element element = new Element(clerk.getId(), clerk);

        if (log.isDebugEnabled()) {
            log.debug("Cache put: " + element.getKey());
        }
        
        cache.put(element);
	}
	
	private Ehcache getCache() {
		//return cacheManager.getCache(CACHE_NAME);
		return cache;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.cache.ClerkCache#clear()
	 */
	public void clear() {
		getCache().removeAll();
		if(log.isDebugEnabled()){
			log.debug("clear cache: " + cache);
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.cache.ClerkCache#removeFromCache(java.lang.Long)
	 */
	public void removeFromCache(Long id) {
		if (log.isDebugEnabled()) {
            log.debug("Cache remove: " + id);
        }
		cache.remove(id);
	}
	
	public void removeFromCache(Clerk clerk){
		removeFromCache(clerk.getId());
	}

	/**
	 * @param cache the cache to set
	 */
	public void setCache(Ehcache cache) {
		this.cache = cache;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cache, "±ÿ–Î÷∏∂®ª∫¥Êehcache");
	}
}
