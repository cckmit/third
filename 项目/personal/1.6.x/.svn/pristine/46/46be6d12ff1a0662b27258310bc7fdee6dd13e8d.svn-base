package org.opoo.apps.security.impl;

import java.util.Date;
import java.util.UUID;

import org.opoo.apps.AppsHome;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.OldPresence;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserManager;
import org.opoo.apps.security.event.PresenceEvent;
import org.opoo.cache.CacheSizes;
import org.opoo.cache.Cacheable;


/**
 * 在线用户实现类。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * 
 */
public class OldPresenceImpl implements OldPresence, Cacheable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1564785830428208125L;
	private long userId = -1L;
	private String username = null;
	private String id = "";
	private String ipAddress;
	private long loginTime;
	private long updateTime;
	private int status;
	private UUID nodeId;
	
//	"最后活动时间"的最后同步时间。
//	private long eventDispatchTime;
	
	/**
	 * 
	 * @param user
	 * @param id
	 * @param ipAddress
	 */
	public OldPresenceImpl(User user, String id, String ipAddress) {
		this(user, id, ipAddress, AppsHome.getNodeID());
	}
	
	
	public OldPresenceImpl(User user, String id, String ipAddress, UUID nodeId) {
		if (user != null) {
			userId = user.getUserId();
			username = user.getUsername();
		}
		this.id = id;
		this.ipAddress = ipAddress;
		this.nodeId = nodeId;
		
		status = OldPresence.STATUS_ONLINE;
		loginTime = System.currentTimeMillis();
		updateTime = loginTime;
//		eventDispatchTime = loginTime;
	}

	public String getIPAddress() {
		return ipAddress;
	}

	public String getId() {
		return id;
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

	public User getUser() {
		if (userId == -1L) {
			return null;
		}
		if(username == null){
			return null;
		}

		try {
			UserManager um = Application.getContext().getUserManager();
			User user = (User) um.loadUserByUsername(username);
			if (user == null) {
				((OldPresenceManagerImpl)Application.getContext().getPresenceManager()).getOnlineUserCache().remove(userId);
			}
			return user;
		} catch (Exception e) {
			((OldPresenceManagerImpl)Application.getContext().getPresenceManager()).getOnlineUserCache().remove(userId);
		}
		return null;
	}

	public String getUsername() {
		return username;
	}

	public void setLastUpdateTime(Date date) {
		updateTime = date.getTime();
	}

	public void setLastUpdateTime(long time) {
		if(time != updateTime){
			updateTime = time;

			//超过一分钟，则发送事件
//		if(time - eventDispatchTime > 60000/* 1 * 60 * 1000 = 1min  */){
			PresenceEvent event = new PresenceEvent(PresenceEvent.Type.LAST_UPDATE_TIME_CHANGE, this);
			((OldPresenceManagerImpl)Application.getContext().getPresenceManager()).dispatchPresenceEvent(event);
//			eventDispatchTime = time;
//		}
		}
	}

	public void setStatus(int status) {
		switch (status) {
		case OldPresence.STATUS_AWAY:
		case OldPresence.STATUS_IDLE:
		case OldPresence.STATUS_INVISIBLE:
		case OldPresence.STATUS_ONLINE:
			this.status = status;
			break;

		default:
			throw new IllegalArgumentException("Status was not a valid value");
		}
	}

	public int getCachedSize() {
		int size = 0;
		size += CacheSizes.sizeOfObject();
		size += CacheSizes.sizeOfLong();
		size += CacheSizes.sizeOfString(id);
		size += CacheSizes.sizeOfDate();
		size += CacheSizes.sizeOfDate();
		size += CacheSizes.sizeOfInt();
		size += CacheSizes.sizeOfString(username);
		return size;
	}


	/**
	 * @return the nodeId
	 */
	public UUID getNodeId() {
		return nodeId;
	}
	
}
