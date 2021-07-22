package org.opoo.apps.security.impl;

import java.util.Date;
import java.util.UUID;

import org.opoo.apps.AppsHome;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.Presence;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserManager;
import org.opoo.cache.CacheSizes;
import org.opoo.cache.Cacheable;


/**
 * 在线用户实现类。
 * 
 * @author Alex Lin(alex@opoo.org)
 */
public class PresenceImpl implements Presence, Cacheable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1564785830428208125L;
	private String username = null;
	private String sessionId = "";
	private String ipAddress;
	private long loginTime;
	private long updateTime;
	private int status;
	private UUID nodeId;
	private int loginStatus;
//	"最后活动时间"的最后同步时间。
//	private long eventDispatchTime;
	
	/**
	 * 
	 * @param user
	 * @param id
	 * @param ipAddress
	 */
	public PresenceImpl(User user, String sessionId, String ipAddress) {
		this(user, sessionId, ipAddress, null/*AppsHome.getNodeID()*/);
	}
	
	
	public PresenceImpl(User user, String sessionId, String ipAddress, UUID nodeId) {
		if (user != null) {
			username = user.getUsername();
		}
		
		this.ipAddress = ipAddress;
		this.nodeId = nodeId;
		this.sessionId = sessionId;
		
		status = Presence.STATUS_ONLINE;
		loginTime = System.currentTimeMillis();
		updateTime = loginTime;
//		eventDispatchTime = loginTime;
		
		if(this.nodeId == null){
			this.nodeId = AppsHome.getNodeID();
		}
	}


	public Date getLastUpdateTime() {
		return new Date(updateTime);
	}

	public Date getLoginTime() {
		return new Date(loginTime);
	}

	public int getStatus() {
		return status;
	}

	

	public String getUsername() {
		return username;
	}

	public void setLastUpdateTime(Date date) {
		updateTime = date.getTime();
	}

	public void setLastUpdateTime(long time) {
		if(time > updateTime){
			updateTime = time;
			
			status = Presence.STATUS_ONLINE;
			
			//超过一分钟，则发送事件
//		if(time - eventDispatchTime > 60000/* 1 * 60 * 1000 = 1min  */){
//			PresenceEvent event = new PresenceEvent(PresenceEvent.Type.LAST_UPDATE_TIME_CHANGE, this);
//			((PresenceManagerImpl)Application.getContext().getPresenceManager()).dispatchPresenceEvent(event);
//			eventDispatchTime = time;
//		}
		}
	}

	public void setStatus(int status) {
		switch (status) {
		case Presence.STATUS_AWAY:
		case Presence.STATUS_IDLE:
		case Presence.STATUS_INVISIBLE:
		case Presence.STATUS_ONLINE:
			this.status = status;
			break;

		default:
			throw new IllegalArgumentException("Status was not a valid value");
		}
	}

	public int getCachedSize() {
		int size = 0;
		size += CacheSizes.sizeOfObject();					// overhead of object
//		size += CacheSizes.sizeOfLong();					// userId
		size += CacheSizes.sizeOfString(sessionId);			// sessionId
		size += CacheSizes.sizeOfDate();					// login date
		size += CacheSizes.sizeOfDate();					// last update date
		size += CacheSizes.sizeOfInt();						//status
		size += CacheSizes.sizeOfString(username);			//username
		size += CacheSizes.sizeOfString(nodeId.toString());
		return size;
	}


	/**
	 * @return the nodeId
	 */
	public UUID getNodeId() {
		return nodeId;
	}

	public String getRemoteAddr() {
		return ipAddress;
	}

	public String getSessionId() {
		return sessionId;
	}


	public User getUser() {
		if(username == null){
			return null;
		}

		try {
			UserManager um = Application.getContext().getUserManager();
			User user = (User) um.loadUserByUsername(username);
//			Application.getContext().get("newPresenceManager", NewPresenceManager.class);
//				((PresenceManagerImpl)Application.getContext().getPresenceManager()).getOnlineUserCache().remove(userId);
//			}
			return user;
		} catch (Exception e) {
//			((PresenceManagerImpl)Application.getContext().getPresenceManager()).getOnlineUserCache().remove(userId);
		}
		return null;
	}


	public int getLoginStatus() {
		return loginStatus;
	}
	

	public void setLoginStatus(int loginStatus) {
		this.loginStatus = loginStatus;
	}
}
