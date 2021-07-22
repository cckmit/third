package org.opoo.apps.security.cache;

import java.util.List;

import org.opoo.apps.security.Group;
import org.opoo.cache.Cache;

public class DefaultAppsGroupCache implements AppsGroupCache {
	/**
	 * A cache for group objects. This cache is not instantiated until after
	 * this factory is initialized.
	 */
	protected Cache<Long, Group> groupCache;

	/**
	 * A cache that maps group names to ID's. This cache is not instantiated
	 * until after this factory is initialized.
	 */
	protected Cache<String, Long> groupIdCache;

	/**
	 * A cache for the list of members in each group. This cache is not
	 * instantiated until after this factory is initialized.
	 */
	protected Cache<String, List<Long>> groupMemberCache;

	/**
  * 
  */
	protected Cache<Long, List<String>> groupAuthoritiesCache;
	
	public DefaultAppsGroupCache(Cache<Long, Group> groupCache, Cache<String, Long> groupIdCache,
			Cache<String, List<Long>> groupMemberCache, Cache<Long, List<String>> groupAuthoritiesCache) {
		this.groupCache = groupCache;
		this.groupIdCache = groupIdCache;
		this.groupMemberCache = groupMemberCache;
		this.groupAuthoritiesCache = groupAuthoritiesCache;
	}

	public Group getGroupFromCache(Long groupId) {
		return groupCache.get(groupId);
	}

	public void putGroupInCache(Group group) {
		groupCache.put(group.getId(), group);
		groupIdCache.put(group.getName(), group.getId());
	}

	public void removeGroupFromCache(Long groupId) {
		groupCache.remove(groupId);
		groupIdCache.clear();
		groupMemberCache.clear();
		groupAuthoritiesCache.remove(groupId);
	}

	public Group getGroupFromCache(String groupName) {
		Long id = groupIdCache.get(groupName);
		if(id != null){
			return getGroupFromCache(id);
		}
		return null;
	}

	public void removeGroupFromCache(String groupName) {
		groupCache.clear();
		groupIdCache.remove(groupName);
		groupMemberCache.clear();
		groupAuthoritiesCache.clear();
	}

	public List<String> getGroupAuthoritiesFromCache(Long groupId) {
		return groupAuthoritiesCache.get(groupId);
	}

	public void putGroupAuthoritiesInCache(Long groupId, List<String> authorities) {
		groupAuthoritiesCache.put(groupId, authorities);
	}

	public void removeGroupAuthoritiesFromCache(Long groupId) {
		groupAuthoritiesCache.remove(groupId);
	}

	public void clearGroupAuthoritiesCache() {
		groupAuthoritiesCache.clear();
	}

	public List<Long> getGroupIdsFromCache(String username) {
		return groupMemberCache.get(username);
	}

	public void putGroupIdsInCache(String username, List<Long> groupIds) {
		groupMemberCache.put(username, groupIds);
	}

	public void removeGroupIdsFromCache(String username) {
		groupMemberCache.remove(username);
	}

	public void clearGroupIdsCache() {
		groupMemberCache.clear();
	}
}
