/*
 * $Id: SystemMessage.java 4400 2011-05-17 06:36:04Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.Date;
import java.util.List;

import org.opoo.apps.security.User;

/**
 * 系统消息
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface SystemMessage {
	
	long getMsgId();
	
	/**
	 * 标题
	 * @return
	 */
	String getSubject();
	
	/**
	 * 内容
	 * @return
	 */
	String getContent();

	/**
	 * 创建时间
	 * @return
	 */
	Date getCreationTime();
	
	/**
	 * 过期时间
	 * @return
	 */
	Date getExpirationTime();
	
	/**
	 * 限制。例如时间限制。
	 * @return
	 */
	long getLimit();
	
	/**
	 * 发布状态
	 * @return
	 */
	PublishStatus getPublishStatus();
	
	/**
	 * 设置发布状态
	 * @param publishStatus
	 */
	void setPublishStatus(PublishStatus publishStatus);
	
	/**
	 * 所有的消息阅读者
	 * @return
	 */
	List<Reader> getReaders();
	
	/**
	 * 用户显示了消息
	 * @param user
	 * @return
	 */
	Reader addLoadedUser(String sessionId, User user);
	
	/**
	 * 用户确认阅读了消息
	 * @param user
	 * @return
	 */
	Reader addConfirmedUser(String sessionId, User user);
	
	/**
	 * 获取消息的指定阅读者
	 * @param sessionId
	 * @return
	 */
	Reader getReader(String sessionId);
	
	/**
	 * 
	 * @param sessionId
	 * @return
	 */
	boolean containsReader(String sessionId);
	
	/**
	 * 发布状态定义。
	 * @author Alex Lin(alex@opoo.org)
	 *
	 */
	public static enum PublishStatus{
		DRAFT,NORMAL,EXPIRE
	}
	
	/**
	 * 消息的阅读者。
	 */
	public static class Reader{
		private String sessionId;
		private User user;
		private Date loadedTime;
		private Date confirmedTime;
		/**
		 * @param user
		 */
		public Reader(String sessionId, User user) {
			super();
			this.user = user;
			this.sessionId = sessionId;
		}
		/**
		 * @return the user
		 */
		public User getUser() {
			return user;
		}
		/**
		 * @param user the user to set
		 */
		public void setUser(User user) {
			this.user = user;
		}
		
		/**
		 * @return the loadedTime
		 */
		public Date getLoadedTime() {
			return loadedTime;
		}
		/**
		 * @param loadedTime the loadedTime to set
		 */
		public void setLoadedTime(Date loadedTime) {
			this.loadedTime = loadedTime;
		}
		/**
		 * @return the confirmedTime
		 */
		public Date getConfirmedTime() {
			return confirmedTime;
		}
		/**
		 * @param confirmedTime the confirmedTime to set
		 */
		public void setConfirmedTime(Date confirmedTime) {
			this.confirmedTime = confirmedTime;
		}
		public void loadNow(){
			this.loadedTime = new Date();
		}
		
		public void confirmNow(){
			this.confirmedTime = new Date();
		}
		/**
		 * @return the sessionId
		 */
		public String getSessionId() {
			return sessionId;
		}
		/**
		 * @param sessionId the sessionId to set
		 */
		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}
	}
}

