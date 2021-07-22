package org.opoo.apps.cache.hibernate;

import java.util.Map;
import java.util.Properties;

import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.Timestamper;
import org.opoo.apps.cache.CacheFactory;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
@SuppressWarnings("unchecked")
public class HibernateCache implements Cache {
	
	private static final int SIXTY_THOUSAND_MS = 60000;
	
	
	private org.opoo.cache.Cache cache;
	
	public HibernateCache(org.opoo.cache.Cache cache, Properties properties){
		this.cache = cache;
	}

	public void clear() throws CacheException {
		cache.clear();
	}

	public void destroy() throws CacheException {
		CacheFactory.destroyCache(cache.getName());
	}

	public Object get(Object key) throws CacheException {
		return cache.get(key);
	}

	public long getElementCountInMemory() {
		return cache.size();
	}

	public long getElementCountOnDisk() {
		return -1L;
	}

	public String getRegionName() {
		return cache.getName();
	}

	public long getSizeInMemory() {
		return -1L;
	}

	public int getTimeout() {
		// 60 second lock timeout
		//return Timestamper.ONE_MS * SIXTY_THOUSAND_MS;
		return SIXTY_THOUSAND_MS;
	}

	public void lock(Object key) throws CacheException {
		CacheFactory.lockKey(key, SIXTY_THOUSAND_MS);
	}

	public long nextTimestamp() {
		return Timestamper.next();
	}

	public void put(Object key, Object value) throws CacheException {
		cache.put(key, value);
	}

	public Object read(Object key) throws CacheException {
		return cache.get(key);
	}

	public void remove(Object key) throws CacheException {
		cache.remove(key);
	}

	public Map toMap() {
		return cache;
	}

	public void unlock(Object key) throws CacheException {
		CacheFactory.unlockKey(key);
	}

	public void update(Object key, Object value) throws CacheException {
		put(key, value);
	}

	
	public static void main(String[] args){
		System.out.println(Timestamper.ONE_MS * SIXTY_THOUSAND_MS);
	}
}
