package org.opoo.apps.security.event;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.event.v2.EventListener;
import org.opoo.apps.security.OldPresence;
import org.opoo.apps.security.User.OnlineStatus;
import org.opoo.ndao.hibernate3.HibernateDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.providers.dao.UserCache;
import org.springframework.util.Assert;

/**
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated
 */
public class PresenceEventListener implements EventListener<PresenceEvent>, InitializingBean {
	private static final Log log = LogFactory.getLog(PresenceEventListener.class);
	
	@SuppressWarnings("unchecked")
	private HibernateDao dao;
	private UserCache userCache;
	
	/**
	 * @param dao the dao to set
	 */
	@SuppressWarnings("unchecked")
	public void setDao(HibernateDao dao) {
		this.dao = dao;
	}

	/**
	 * @param userCache the userCache to set
	 */
	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	public void handle(PresenceEvent event) {
		if(PresenceEvent.Type.LAST_UPDATE_TIME_CHANGE == event.getType()){
			OldPresence presence = event.getSource();
			String username = presence.getUsername();
			if(username != null){
				if(log.isDebugEnabled()){
					log.debug("更新用户最后活动时间：" + username);
				}
				
				String qs = "update LiveUser set modificationTime=? where username=?";
				dao.getQuerySupport().executeUpdate(qs, 
						new Object[]{presence.getLastUpdateTime(), username});
				userCache.removeUserFromCache(username);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(dao, "dao 不能为空");
		Assert.notNull(userCache, "用户缓存不能为空");
		
		log.info("初始化在线用户数据，重置离线状态。");
		
		
		String qs = "select username, modificationTime, onlineStatus from LiveUser where enabled=?";
		List<Object[]> list = dao.getHibernateTemplate().find(qs, true);
		for(Object[] user: list){
			checkLastUpdateTime(user);
		}
	}
	
	private void checkLastUpdateTime(Object[] user){
		String username = (String) user[0];
		Date modificationTime = (Date) user[1];
		OnlineStatus onlineStatus = (OnlineStatus) user[2];
		if(username != null && modificationTime != null && onlineStatus != OnlineStatus.OFFLINE){
			long time = modificationTime.getTime();
			int timeToIdleSeconds = AppsGlobals.getProperty("Presence.timeToIdleSeconds", 5 * 60);
			if(System.currentTimeMillis() - time > timeToIdleSeconds * 1000){
				if(log.isInfoEnabled()){
					log.info("将数据库中空闲时间超过 " + timeToIdleSeconds + " 秒的用户 '"
						+ username + "' 状态设置为离线。");
				}
				dao.getQuerySupport().executeUpdate("update LiveUser set onlineStatus=? where username=?",
						new Object[]{OnlineStatus.OFFLINE, username});
				userCache.removeUserFromCache(username);
			}
		}
	}
	
}
