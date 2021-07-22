package org.opoo.apps.security;

import java.util.Iterator;


/**
 * 在线用户管理。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface OldPresenceManager {

	int SORT_USERNAME = 0;
	int SORT_LOGIN_TIME = 1;

	boolean isPresencesEnabled();

	void setPresencesEnabled(boolean flag);

	int getOnlineUserCount();

//	int getOnlineUserCount(Group group);

	boolean isOnline(User user);

	Iterator<User> getOnlineUsers();

//	Iterator<User> getOnlineUsers(Group group);

	Iterator<User> getOnlineUsers(boolean ascending, int sortField);

//	Iterator<User> getOnlineUsers(Group group, boolean flag, int i);

	Iterator<User> getOnlineUsers(boolean ascending, int sortField, int maxResults);

	Iterator<User> getOnlineUsers(boolean ascending, int sortField, int firstResult, int maxResults);

//	Iterator<User> getOnlineUsers(Group group, boolean flag, int i, int j);

	OldPresence getPresence(User user);

	OldPresence createPresence(User user, String sessionId, String ipAddress);

	void deletePresence(User user);

	void setOffline(OldPresence presence);

	//Presence getPresence(String id);
}
