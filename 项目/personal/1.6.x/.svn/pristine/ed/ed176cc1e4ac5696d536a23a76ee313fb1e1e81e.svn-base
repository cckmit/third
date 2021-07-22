package org.opoo.apps.cache.hibernate;

import java.util.Properties;

import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CacheProvider;
import org.hibernate.cache.Timestamper;
import org.opoo.apps.cache.CacheFactory;


@SuppressWarnings("unchecked")
public class HibernateCacheProvider implements CacheProvider {

	
	public Cache buildCache(String regionName, Properties properties) throws CacheException {
		org.opoo.cache.Cache cache = CacheFactory.createCache(regionName);
		return new HibernateCache(cache, properties);
	}

	public boolean isMinimalPutsEnabledByDefault() {
		return true;
	}

	public long nextTimestamp() {
		return Timestamper.next();
	}

	public void start(Properties properties) throws CacheException {
		//CacheFactory.startup();
	}

	public void stop() {

	}

}
