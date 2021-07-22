package org.opoo.apps.security.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.security.User;
import org.opoo.cache.Cache;
import org.springframework.security.providers.dao.UserCache;
import org.springframework.security.userdetails.UserDetails;

/**
 * ”√ªßª∫¥Ê°£
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5.3
 */
public class DefaultAppsUserCache implements AppsUserCache, UserCache {
	private static final Log log = LogFactory.getLog(AppsUserCache.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	private final Cache<Object,Object> cache;
	
	public DefaultAppsUserCache(Cache<Object,Object> cache) {
		this.cache = cache;
	}
	
	public UserDetails getUserFromCache(String username) {
		return (UserDetails) cache.get(username);
	}
	
	public String getUsernameFromCache(Long userId){
		return (String) cache.get(buildUserIdCacheKey(userId));
	}
	
	public void putUserInCache(UserDetails user) {
		cache.put(user.getUsername(), user);
		if(IS_DEBUG_ENABLED){
			log.debug("cache user: " + user.getUsername());
		}
		
		if(user instanceof User){
			User u = (User) user;
			cache.put(buildUserIdCacheKey(u.getUserId()), u.getUsername());
			if(IS_DEBUG_ENABLED){
				log.debug("cache username '" + u.getUsername() + "' for userId: " + u.getUserId());
			}
		}
	}

	public void removeUserFromCache(String username) {
		Object user = cache.remove(username);
		if(IS_DEBUG_ENABLED){
			log.debug("remove user cache for " + username);
		}

		if(user instanceof User){
			User u = (User) user;
			cache.remove(buildUserIdCacheKey(u.getUserId()));
			if(IS_DEBUG_ENABLED){
				log.debug("remove username cache for userId: " + u.getUserId());
			}
		}
	}
	
	private String buildUserIdCacheKey(Long userId){
		return "UID_" + userId;
	}
}
