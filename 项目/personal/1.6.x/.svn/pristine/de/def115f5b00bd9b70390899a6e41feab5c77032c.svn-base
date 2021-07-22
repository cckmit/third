package org.opoo.apps.cache;

import junit.framework.TestCase;
import net.sf.ehcache.CacheManager;

import org.opoo.apps.cache.ehcache.EhcacheBasedCache;
import org.opoo.cache.Cache;
import org.opoo.cache.CacheWrapper;

public class CacheFactoryTest extends TestCase {

	@SuppressWarnings("unchecked")
	public void test0(){
		CacheManager.create("E:\\java-works\\opoo-apps-1.5.0\\src\\main\\resources\\ehcache.xml");
		CacheFactory.initialize();
		Cache cache = CacheFactory.createCache("userCache");
		System.out.println(cache);
		
		cache = CacheFactory.createCache("userCache");
		System.out.println(cache);
		
		cache = CacheFactory.createCache("userCache");
		System.out.println(cache);
		
		cache.get("sss");
		
		if(cache instanceof CacheWrapper){
			Cache internal = ((CacheWrapper)cache).getWrappedCache();
			System.out.println(internal);
			if(internal instanceof EhcacheBasedCache){
				EhcacheBasedCache ec = (EhcacheBasedCache) internal;
				System.out.println(ec.getEhcache());
			}
		}
		cache.put("asdasd", "dssssssssssssssssssss");
		cache.put("asdsaasd", "dssssssssssassssssssssss");
		CacheFactory.getAllCaches();
		
		//int size = CacheFactory.calculateCacheSize(cache);
		//System.out.println(size);
		
	}
}
