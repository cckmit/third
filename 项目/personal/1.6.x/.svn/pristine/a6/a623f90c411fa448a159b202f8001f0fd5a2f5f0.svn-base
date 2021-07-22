package org.opoo.apps.security.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.AppsHome;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.event.v2.EventDispatcherAware;
import org.opoo.apps.security.OldPresence;
import org.opoo.apps.security.OldPresenceManager;
import org.opoo.apps.security.User;
import org.opoo.apps.security.event.OnlineEvent;
import org.opoo.apps.security.event.PresenceEvent;
import org.opoo.cache.Cache;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class OldPresenceManagerImpl implements OldPresenceManager, EventDispatcherAware, 
	InitializingBean, DisposableBean{
	class UsernameComparator implements Comparator<OldPresence> {
		boolean ascending = true;
		UsernameComparator(boolean asc) {
			ascending = asc;
		}

		public int compare(OldPresence presence1, OldPresence presence2) {
			if (ascending)
				return presence1.getUser().getUsername().compareTo(presence2.getUser().getUsername());
			else
				return presence2.getUser().getUsername().compareTo(presence1.getUser().getUsername());
		}
	}

	class LoginTimeComparator implements Comparator<OldPresence> {
		boolean ascending = true;

		LoginTimeComparator(boolean asc) {
			ascending = asc;
		}

		public int compare(OldPresence presence1, OldPresence presence2) {
			if (ascending)
				return presence1.getLoginTime().compareTo(presence2.getLoginTime());
			else
				return presence2.getLoginTime().compareTo(presence1.getLoginTime());
		}
	}
	
	/**
	 * 在线用户监控。
	 * @author Alex Lin(alex@opoo.org)
	 *
	 */
	class PresenceWatcher extends TimerTask{
		@Override
		public void run() {
			if(log.isDebugEnabled()){
				log.debug("执行在线用户状态整理 ");
			}
			List<OldPresence> list = getOnlineUserList();
			for(OldPresence presence: list){
				//在现用户处于当前节点
				if(presence.getNodeId().equals(AppsHome.getNodeID())){
					if(OldPresence.STATUS_ONLINE == presence.getStatus() || OldPresence.STATUS_IDLE == presence.getStatus()){
						Date time = presence.getLastUpdateTime();
						//int timeToIdleSeconds = AppsGlobals.getProperty("Presence.timeToIdleSeconds", 5 * 60);
						if(time != null){
							int timeToIdleSeconds = AppsGlobals.getProperty("Presence.timeToIdleSeconds", 5 * 60);
							if(System.currentTimeMillis() - time.getTime() > timeToIdleSeconds * 1000){
								if(log.isInfoEnabled()){
									log.info("将空闲时间超过 " + timeToIdleSeconds + " 秒的用户 '"
										+ presence.getUsername() + "' 状态设置为离线。");
								}
								setOffline(presence);
							}
						}
					}
				}
			}
		}
	}

	private static final Log log = LogFactory.getLog(OldPresenceManagerImpl.class);
	public static final String PRESENCE_ENABLED_KEY = "presence.enabled";
	private boolean enabled = true;
	private Cache<Long, OldPresence> onlineUserCache;
	// private Cache userRosterCache;
	private final Object userLock = new Object();
	private int onlineUsers = 0;
	private EventDispatcher dispatcher;
	private Timer timer = new Timer();

	private OldPresenceManagerImpl() {
		enabled = AppsGlobals.getProperty(PRESENCE_ENABLED_KEY, true);
	}

	public void setOnlineUserCache(Cache<Long, OldPresence> onlineUserCache) {
		this.onlineUserCache = onlineUserCache;
	}

	// public void setUserRosterCache(Cache userRosterCache) {
	// this.userRosterCache = userRosterCache;
	// }

	public Cache<Long, OldPresence> getOnlineUserCache() {
		return onlineUserCache;
	}

	// public Cache getUserRosterCache() {
	// return userRosterCache;
	// }

	public OldPresence createPresence(User user, String sessionId, String ipAddress) {
		if (!enabled) {
			return null;
		} else {
			OldPresence presence = new OldPresenceImpl(user, sessionId, ipAddress);
			setOnline(presence);
			return presence;
		}
	}

	public void deletePresence(User user) {
		OldPresence p = getPresence(user);
		if (p != null) {
			setOffline(p);
		}
	}

	public int getOnlineUserCount() {
		if (!enabled)
			return 0;
		else
			return onlineUsers;
	}

	private List<OldPresence> getOnlineUserList() {
		List<OldPresence> presences = new ArrayList<OldPresence>();
		synchronized (userLock) {
			Collection<OldPresence> onlineUsers = onlineUserCache.values();
			for (OldPresence pres : onlineUsers) {
				if (pres != null
						&& (pres.getStatus() == OldPresence.STATUS_ONLINE || pres.getStatus() == OldPresence.STATUS_IDLE))
					presences.add(pres);
			}
		}
		return presences;
	}

	public Iterator<User> getOnlineUsers() {
		if (!enabled) {
			return emptyIterator();
		} else {
			List<OldPresence> list = getOnlineUserList();
			final Iterator<OldPresence> it = list.iterator();
			return new Iterator<User>() {
				public boolean hasNext() {
					return it.hasNext();
				}

				public User next() {
					return it.next().getUser();
				}

				public void remove() {
				}
			};
		}
	}

	public Iterator<User> getOnlineUsers(boolean ascending, int sortField) {
		return getOnlineUsers(ascending, sortField, Integer.MAX_VALUE);
	}

	public Iterator<User> getOnlineUsers(boolean ascending, int sortField, int maxResults) {
		return getOnlineUsers(ascending, sortField, 0, maxResults);
	}

	private Iterator<User> emptyIterator() {
		List<User> list = Collections.emptyList();
		return list.iterator();
	}

	public Iterator<User> getOnlineUsers(boolean ascending, int sortField, int firstResult, int maxResults) {
		if (!enabled) {
			return emptyIterator();
		}
		List<OldPresence> presences = getOnlineUserList();
		switch (sortField) {
		case OldPresenceManager.SORT_LOGIN_TIME: // '\001'
			Collections.sort(presences, new LoginTimeComparator(ascending));
			break;

		case OldPresenceManager.SORT_USERNAME: // '\0'
			Collections.sort(presences, new UsernameComparator(ascending));
			break;
		}
		if (firstResult >= presences.size()) {
			return emptyIterator();
		}
		int end = firstResult + maxResults;
		//Integer.MAX_VALUE + i < 0
		if(end < 0 || end > presences.size()) {
			end = presences.size();
		}
		List<User> users = new ArrayList<User>(maxResults);
		for (int i = firstResult; i < end; i++) {
			OldPresence p = (OldPresence) presences.get(i);
			users.add(p.getUser());
		}

		return Collections.unmodifiableList(users).iterator();
	}

	public OldPresence getPresence(User user) {
		if (!enabled)
			return null;
		if (user == null)
			return null;
		else
			return onlineUserCache.get(user.getUserId());
	}

	public boolean isOnline(User user) {
		if (!enabled)
			return false;
		if (user == null)
			return false;
		OldPresence presence = (OldPresence) onlineUserCache.get(Long.valueOf(user.getUserId()));
		return presence != null
				&& (presence.getStatus() == OldPresence.STATUS_ONLINE || presence.getStatus() == OldPresence.STATUS_IDLE);
	}

	public boolean isPresencesEnabled() {
		return enabled;
	}

	public void setOffline(OldPresence presence) {
		if (!enabled) {
			return;
		}
		if (presence.getUser() != null) {
			Long id = presence.getUser().getUserId();
			synchronized (userLock) {
				if (onlineUserCache.remove(id) != null)
					dispatcher.dispatchEvent(new OnlineEvent(OnlineEvent.Type.OFFLINE, presence.getUser()));
					// dispatcher.fire(new
					// PresenceEvent(com.jivesoftware.base.event.PresenceEvent.Type.OFFLINE,
					// new UserTemplate(presence.getUser())));
					onlineUsers = onlineUserCache.size();
			}
		}
	}

	protected void setOnline(OldPresence presence) {
		if (!enabled)
			return;
		User user = presence.getUser();
		if (user != null) {
			synchronized (userLock) {
				onlineUserCache.put(user.getUserId(), presence);
				dispatcher.dispatchEvent(new OnlineEvent(OnlineEvent.Type.ONLINE, presence.getUser()));
				// dispatcher.fire(new
				// PresenceEvent(com.jivesoftware.base.event.PresenceEvent.Type.ONLINE,
				// new UserTemplate(user)));
				onlineUsers = onlineUserCache.size();
			}
		}
	}
	
	void dispatchPresenceEvent(PresenceEvent event){
		dispatcher.dispatchEvent(event);
	}
	

	public void setPresencesEnabled(boolean enabled) {
		if (this.enabled == enabled) {
			return;
		} else {
			this.enabled = enabled;
			String s = String.valueOf(enabled);
			AppsGlobals.setProperty(PRESENCE_ENABLED_KEY, s);
			return;
		}
	}

	public OldPresence getPresence(String presenceId) {
		if (!enabled)
			return null;
		if (presenceId == null)
			return null;
		OldPresence p[] = null;
		synchronized (userLock) {
			Collection<OldPresence> onlineUsers = onlineUserCache.values();
			p = onlineUsers.toArray(new OldPresence[onlineUsers.size()]);
		}
		for (OldPresence presence : p) {
			if (presence != null && presence.getId().equals(presenceId)) {
				return presence;
			}
		}
		return null;
	}

	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		this.dispatcher = eventDispatcher;
	}
	
	
	public void afterPropertiesSet() throws Exception {
		timer.schedule(new PresenceWatcher(), 2 * 60 * 1000, 60 * 1000);
	}

	public void destroy() throws Exception {
		timer.cancel();
	}
	
	/*
	static class MyTask extends TimerTask{

		@Override
		public void run() {
			System.out.println(new java.util.Date());
		}
	}
	
	public static void main(String[] args) throws InterruptedException{
		System.out.println(AppsHome.getNodeID().toString());
		
		Timer timer = new Timer();
		timer.schedule(new MyTask(), 1, 1000);
		Thread.sleep(4000);
		timer.cancel();
	}
	
	*/
}
