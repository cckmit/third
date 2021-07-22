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
 * �����¼���������
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
			log.debug("���������¼���" + user.getUsername());
		}
		//FIXME: Solaris 10+Sun appserver 8.1_2 bug
		//http://wesunsolve.net/bugid/id/6490722
		/*
		try{
			dao.getQuerySupport().executeUpdate("update LiveUser set onlineStatus=? where username=?", 
				new Object[]{OnlineStatus.OFFLINE, user.getUsername()});
		}catch(Exception e){
			log.warn("�����û�״̬Ϊ����ʱ����", e);
		}*/
		userCache.removeUserFromCache(user.getUsername());
	}

	private void handleOnlineEvent(OnlineEvent event) {
		User user = event.getSource();
		if(log.isDebugEnabled()){
			log.debug("���������¼���" + user.getUsername());
		}
		//FIXME: Solaris 10+Sun appserver 8.1_2 bug
		//http://wesunsolve.net/bugid/id/6490722
		/*
		try{
			dao.getQuerySupport().executeUpdate("update LiveUser set onlineStatus=? where username=?", 
				new Object[]{OnlineStatus.ONLINE, user.getUsername()});
		}catch(Exception e){
			log.warn("�����û�״̬Ϊ����ʱ����", e);
		}
		*/
		if(user instanceof MutableUser){
			((MutableUser) user).setOnlineStatus(OnlineStatus.ONLINE);
		}else{
			userCache.removeUserFromCache(user.getUsername());
		}
	}
	
	public void afterPropertiesSet() throws Exception {
		//Assert.notNull(dao, "dao ����Ϊ��");
		Assert.notNull(userCache, "�û����治��Ϊ��");
	}
}
