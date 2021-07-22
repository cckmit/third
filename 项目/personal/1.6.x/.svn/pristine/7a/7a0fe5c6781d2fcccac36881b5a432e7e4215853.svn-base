package org.opoo.apps.security.impl;

import static java.util.Collections.sort;
import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.AppsHome;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.event.v2.EventDispatcherAware;
import org.opoo.apps.security.OnlineUser;
import org.opoo.apps.security.Presence;
import org.opoo.apps.security.PresenceManager;
import org.opoo.apps.security.User;
import org.opoo.apps.security.event.OnlineEvent;
import org.opoo.cache.Cache;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class PresenceManagerImpl implements PresenceManager, EventDispatcherAware, InitializingBean, DisposableBean{
	private static final Log log = LogFactory.getLog(PresenceManagerImpl.class);
	private Cache<String, Presence> presenceCache;
	
	private boolean enabled = true;
    private final Object lock = new Object();
    public static final String PRESENCE_ENABLED_KEY = "presence.enabled";
    private EventDispatcher dispatcher;
    //private Timer timer = new Timer();
    private ScheduledExecutorService scheduledExecutorService;

    private PresenceManagerImpl() {
    	enabled = AppsGlobals.getProperty(PRESENCE_ENABLED_KEY, true);
    }

	public Cache<String, Presence> getPresenceCache() {
		return presenceCache;
	}

	public void setPresenceCache(Cache<String, Presence> presenceCache) {
		this.presenceCache = presenceCache;
	}
	
	public ScheduledExecutorService getScheduledExecutorService() {
		return scheduledExecutorService;
	}

	public void setScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
	}

	public void destroy() {
		//timer.cancel();
//		log.debug("cancel timer.");
		
		
		//将当前节点的presences设置为离线
		List<Presence> list = getPresenceList();
		UUID nodeId = AppsHome.getNodeID();
		for (Presence np : list) {
			if(nodeId.equals(np.getNodeId())){
				setOffline(np);
			}
		}
		log.debug("将当前节点的presences设置为离线.");
    }

    public boolean isPresencesEnabled() {
        return enabled;
    }

    public void setPresencesEnabled(boolean enabled) {
        if (this.enabled == enabled) {
            return;
        }

        this.enabled = enabled;
        AppsGlobals.setProperty("presence.enabled", String.valueOf(enabled));
    } 
	
	protected void setOnline(Presence presence){
        if (!enabled) {
            return;
        }

        if (presence != null) {
            synchronized(lock) {
            	if(log.isDebugEnabled()){
        			log.debug("Set online: " + presence.getUsername() 
        					+ "@" + presence.getSessionId()
        					+ "." + presence.getNodeId());
        		}
            	
            	presenceCache.put(buildPresenceCacheKey(presence), presence);
                //dispatcher.fire(new PresenceEvent(PresenceEvent.Type.ONLINE, user));
            	dispatcher.dispatchEvent(new OnlineEvent(OnlineEvent.Type.ONLINE, presence.getUser()));
            }
        }
	}
	
	//容易死循环，因此放弃
	/*
	public OnlineUser getOnlineUser(final User user){
		if(!enabled){
			return null;
		}
		if(user != null){
			String username = user.getUsername();
			final List<Presence> list = new ArrayList<Presence>();
			Collection<Presence> values = presenceCache.values();
			for(Presence p : values){
				if(username.equals(p.getUsername())){
					list.add(p);
				}
			}
			if(!list.isEmpty()){
				return new OnlineUserImpl(user, list);
			}
		}
		return null;
	}
	*/
	
	
	public List<Presence> getPresences(User user) {
		if(!enabled){
			return null;
		}
		if(user != null){
			String username = user.getUsername();
			List<Presence> list = new ArrayList<Presence>();
			Collection<Presence> values = presenceCache.values();
			for(Presence p : values){
				if(isOnline(p) && username.equals(p.getUsername())){
					list.add(p);
				}
			}
			if(!list.isEmpty()){
				return list;
			}
		}
		return null;
	}

	
    public Presence createPresence(User user, String sessionId, String remoteAddr) {
        if (!enabled) {
            return null;
        }
        
        Presence presence = new PresenceImpl(user, sessionId, remoteAddr);
        setOnline(presence);
        return presence;
    }
    




	public static String buildPresenceCacheKey(String sessionId, UUID nodeId){
		if(nodeId == null){
			nodeId = AppsHome.getNodeID();
		}
		return sessionId + "." + nodeId.toString();
	}
	
	public static String buildPresenceCacheKey(Presence presence){
		return buildPresenceCacheKey(presence.getSessionId(), presence.getNodeId());
	}
	
	

	
	
	
	
//	public void deletePresence(User user) {
//        // Remove the user from the list of online users.
//		OnlineUser ou = getOnlineUser(user);
//		if(ou != null){
//			for (NewPresence p : ou.getPresences()) {
//				setOffline(p);
//			}
//		}
//	}
	
	public int getOnlineUserCount() {
        if (!enabled) {
            return 0;
        }
        else {
            //this access isn't thread safe, but some slop is probably better than lots of contention
            //for userLock
            return presenceCache.size();
            //return getOnlineUserList().size();
        }
	}
	
	public Iterator<OnlineUser> getOnlineUsers() {
		if (!enabled) {
            return Collections.<OnlineUser>emptyList().iterator();
        }
		return unmodifiableCollection(getOnlineUserList()).iterator();      
	}
	
	public Iterator<OnlineUser> getOnlineUsers(boolean ascending, int sortField) {
		return getOnlineUsers(ascending, sortField, Integer.MAX_VALUE);
	}
	
	public Iterator<OnlineUser> getOnlineUsers(boolean ascending, int sortField, int numResults) {
		return getOnlineUsers(ascending, sortField, 0, numResults);
	}
	
	public Iterator<OnlineUser> getOnlineUsers(boolean ascending, int sortField, int start, int numResults) {
		if (!enabled) {
            return Collections.<OnlineUser>emptyList().iterator();
        }

		List<OnlineUser> list = getOnlineUserList();   
//		System.out.println(list);
        switch (sortField) {
            case PresenceManager.SORT_ONLINE_TIME: {
                sort(list, new LoginTimeComparator(ascending));
                break;
            }
            case PresenceManager.SORT_USERNAME: {
                sort(list, new UsernameComparator(ascending));
                break;
            }
            default: {
                // ignore invalid sort field
            }
        }

        if (start >= list.size()) {
        	return Collections.<OnlineUser>emptyList().iterator();
        }

        if (numResults > list.size()) {
            numResults = list.size();
        }

        List<OnlineUser> users = new ArrayList<OnlineUser>(numResults);

        for (int i = start; i < numResults; i++) {
        	OnlineUser p = list.get(i);
            users.add(p);
        }

        return unmodifiableList(users).iterator();
        
        /*
        if (numResults > users.size()) {
            return unmodifiableList(users).iterator();
        }
        else {
            return unmodifiableList(users.subList(0, numResults - 1)).iterator();
        }
        */
	}
	
	
	public boolean isOnline(User user) {
        if (!enabled) {
            return false;
        }
        if (user == null) {
            return false;
        }

        List<Presence> presences = getPresences(user);

        return presences != null;
	}
	
	public void setOffline(Presence presence) {
        if (!enabled) {
            return;
        }
        
        if(presence == null){
        	return;
        }
        
        User user = presence.getUser();
        if(user != null){
        	synchronized(lock) {
            	//presence.setStatus(NewPresence.STATUS_AWAY);
        		if(log.isDebugEnabled()){
        			log.debug("Set offline: " + presence.getUsername() 
        					+ "@" + presence.getSessionId()
        					+ "." + presence.getNodeId());
        		}
        		
                if (presenceCache.remove(buildPresenceCacheKey(presence)) != null){
                	List<Presence> presences = getPresences(user);
                	if(presences == null){
                		dispatcher.dispatchEvent(new OnlineEvent(OnlineEvent.Type.OFFLINE, user));
                    //dispatcher.fire(new PresenceEvent(PresenceEvent.Type.OFFLINE, presence.getUser()));
                	}
                }
            }
        }
	}
	
	public Presence getPresence(String sessionId) {
		String key = buildPresenceCacheKey(sessionId, null);
		return presenceCache.get(key);
	}
	
	private boolean isOnline(Presence presence){
		return presence != null
		&& (presence.getStatus() == Presence.STATUS_ONLINE 
				|| presence.getStatus() == Presence.STATUS_IDLE);
	}
	
	public List<Presence> getPresenceList(){
		List<Presence> presences = new ArrayList<Presence>();
		synchronized (lock) {
			Collection<Presence> onlineUsers = presenceCache.values();
			for (Presence pres : onlineUsers) {
//				if (pres != null
//						&& (pres.getStatus() == NewPresence.STATUS_ONLINE 
//								|| pres.getStatus() == NewPresence.STATUS_IDLE))
				presences.add(pres);
			}
		}
		return presences;
	}
	
	protected List<Presence> getOnlinePresenceList(){
		List<Presence> presences = new ArrayList<Presence>();
		synchronized (lock) {
			Collection<Presence> onlineUsers = presenceCache.values();
			for (Presence pres : onlineUsers) {
				if (isOnline(pres)){
					presences.add(pres);
				}
			}
		}
		return presences;
	}
	
	protected static Map<String, List<Presence>> presenceListToMap(List<Presence> list){
		Map<String, List<Presence>> map = new HashMap<String, List<Presence>>();
		for (Presence np : list) {
			List<Presence> tmp = map.get(np.getUsername());
			if(tmp == null){
				tmp = new ArrayList<Presence>();
				map.put(np.getUsername(), tmp);
			}
			tmp.add(np);
		}
		return map;
	}
	
	
	public List<OnlineUser> getOnlineUserList(){
		List<OnlineUser> list = new ArrayList<OnlineUser>();
		Map<String, List<Presence>> map = presenceListToMap(getOnlinePresenceList());
//		System.out.println(map);
		for (Map.Entry<String, List<Presence>> en: map.entrySet()) {
			List<Presence> list2 = en.getValue();
			User user = list2.get(0).getUser();
			OnlineUser onlineUser = new OnlineUserImpl(user, en.getValue());
			list.add(onlineUser);
		}
		return list;
	}

	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		this.dispatcher = eventDispatcher;
	}

	public void afterPropertiesSet() throws Exception {
		//timer.schedule(new PresenceWatcher(), 2 * 60 * 1000, 60 * 1000);
		scheduledExecutorService.scheduleWithFixedDelay(new PresenceWatcher(), 120, 60, TimeUnit.SECONDS);
		log.debug("Start timer PresenceWatcher.");
	}
	
	
    class LoginTimeComparator implements Comparator<OnlineUser> {
        boolean ascending = true;

        LoginTimeComparator(boolean asc){
            this.ascending = asc;
        }

        public int compare(OnlineUser presence1, OnlineUser presence2) {
            if (ascending) {
                return presence1.getLastPresence().getLoginTime().compareTo(presence2.getLastPresence().getLoginTime());
            }
            else {
                return presence2.getLastPresence().getLoginTime().compareTo(presence1.getLastPresence().getLoginTime());
            }
        }
    }

    class UsernameComparator implements Comparator<OnlineUser> {
        boolean ascending = true;

        UsernameComparator(boolean asc) {
            this.ascending = asc;
        }

        public int compare(OnlineUser presence1, OnlineUser presence2) {
            if (ascending) {
                return presence1.getUsername().compareTo(presence2.getUsername());
            }
            else {
                return presence2.getUsername().compareTo(presence1.getUsername());
            }
        }
    }
	
	class PresenceWatcher extends TimerTask{
		@Override
		public void run() {
			UUID nodeId = AppsHome.getNodeID();
			long now = System.currentTimeMillis();
			int timeToIdle = AppsGlobals.getProperty("Presence.timeToIdleSeconds", 5 * 60) * 1000;
			int timeToAway = AppsGlobals.getProperty("Presence.timeToAwaySeconds", 15 * 60) * 1000;
			int timeToOffline = AppsGlobals.getProperty("Presence.timeToOfflineSeconds", 30 * 60) * 1000;
			
			List<Presence> list = getPresenceList();
			for (Presence np : list) {
				if(nodeId.equals(np.getNodeId())){
					Date time = np.getLastUpdateTime();
					if(time != null){
						long e = now - time.getTime();
						if(e > timeToIdle && e <= timeToAway){
							if(np.getStatus() == Presence.STATUS_ONLINE){
								np.setStatus(Presence.STATUS_IDLE);
								if(log.isDebugEnabled()){
									log.debug("将空闲时间超过 " + timeToIdle + " 毫秒的用户 '"
											+ np.getUsername() + "' 状态设置为空闲。");
								}
							}
						}else if(e > timeToAway && e <= timeToOffline){
							//np.setStatus(NewPresence.STATUS_AWAY);
						}else if(e > timeToOffline){
							//setOffline(np);
						}
					}
				}
			}
		}
	}
}
