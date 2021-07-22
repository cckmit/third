package org.opoo.apps.security.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.event.v2.EventListener;
import org.opoo.apps.security.MutableUser;
import org.opoo.apps.security.User;
import org.opoo.apps.security.User.OnlineStatus;
import org.opoo.ndao.hibernate3.HibernateDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.providers.dao.UserCache;
import org.springframework.util.Assert;

/**
 * 在线事件监听器。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OnlineEventListener implements EventListener<OnlineEvent>, InitializingBean {
	private static final Log log = LogFactory.getLog(OnlineEventListener.class);
	//@SuppressWarnings("unchecked")
	//private HibernateDao dao;
	private UserCache userCache;
	
	/**
	 * @param dao the dao to set
	 */
	@SuppressWarnings("unchecked")
	public void setDao(HibernateDao dao) {
		//this.dao = dao;
	}

	/**
	 * @param userCache the userCache to set
	 */
	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}
	
	public void handle(OnlineEvent event) {
		if(OnlineEvent.Type.ONLINE == event.getType()){
			handleOnlineEvent(event);
		}else if(OnlineEvent.Type.OFFLINE == event.getType()){
			handleOfflineEvent(event);
		}
	}

	private void handleOfflineEvent(OnlineEvent event) {
		User user = event.getSource();
		if(log.isDebugEnabled()){
			log.debug("处理离线事件：" + user.getUsername());
		}
		//FIXME: Solaris 10+Sun appserver 8.1_2 bug
		//http://wesunsolve.net/bugid/id/6490722
		/*
		try{
			dao.getQuerySupport().executeUpdate("update LiveUser set onlineStatus=? where username=?", 
				new Object[]{OnlineStatus.OFFLINE, user.getUsername()});
		}catch(Exception e){
			log.warn("更新用户状态为离线时出错", e);
		}*/
		userCache.removeUserFromCache(user.getUsername());
	}

	private void handleOnlineEvent(OnlineEvent event) {
		User user = event.getSource();
		if(log.isDebugEnabled()){
			log.debug("处理在线事件：" + user.getUsername());
		}
		//FIXME: Solaris 10+Sun appserver 8.1_2 bug
		//http://wesunsolve.net/bugid/id/6490722
		/*
		try{
			dao.getQuerySupport().executeUpdate("update LiveUser set onlineStatus=? where username=?", 
				new Object[]{OnlineStatus.ONLINE, user.getUsername()});
		}catch(Exception e){
			log.warn("更新用户状态为在线时出错", e);
		}
		*/
		if(user instanceof MutableUser){
			((MutableUser) user).setOnlineStatus(OnlineStatus.ONLINE);
		}else{
			userCache.removeUserFromCache(user.getUsername());
		}
	}
	
	public void afterPropertiesSet() throws Exception {
		//Assert.notNull(dao, "dao 不能为空");
		Assert.notNull(userCache, "用户缓存不能为空");
	}
}
