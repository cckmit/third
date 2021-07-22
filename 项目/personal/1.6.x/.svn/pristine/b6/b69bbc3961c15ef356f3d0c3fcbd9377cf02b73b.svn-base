package org.opoo.apps.security.cache;

import java.util.List;

import org.opoo.apps.security.Group;

public interface AppsGroupCache {
	
	void putGroupInCache(Group group);

	Group getGroupFromCache(Long groupId);
	void removeGroupFromCache(Long groupId);
	
	Group getGroupFromCache(String groupName);
	void removeGroupFromCache(String groupName);
	
	List<String> getGroupAuthoritiesFromCache(Long groupId);
	void putGroupAuthoritiesInCache(Long groupId, List<String> authorities);
	void removeGroupAuthoritiesFromCache(Long groupId);
	void clearGroupAuthoritiesCache();
	
	List<Long> getGroupIdsFromCache(String username);
	void putGroupIdsInCache(String username, List<Long> groupIds);
	void removeGroupIdsFromCache(String username);
	void clearGroupIdsCache();
}
