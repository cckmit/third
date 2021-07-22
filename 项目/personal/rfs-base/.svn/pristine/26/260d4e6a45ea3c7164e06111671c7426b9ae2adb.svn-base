package cn.redflagsoft.base.cache.impl;

import org.opoo.cache.Cache;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.cache.ClerkCache;


public class ClerkCacheImpl implements ClerkCache {
	private final Cache<Long, Clerk> cache;
	
	public ClerkCacheImpl(Cache<Long, Clerk> cache) {
		super();
		this.cache = cache;
	}
	
	public void clear() {
		cache.clear();
	}

	public Clerk getClerkFromCache(Long id) {
		return cache.get(id);
	}

	public void putClerkIntoCache(Clerk clerk) {
		cache.put(clerk.getId(), clerk);
	}

	public void removeFromCache(Long id) {
		cache.remove(id);
	}

	public void removeFromCache(Clerk clerk) {
		cache.remove(clerk.getId());
	}
}
