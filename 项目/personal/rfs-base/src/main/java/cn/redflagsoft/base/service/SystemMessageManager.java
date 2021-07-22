package cn.redflagsoft.base.service;

import java.util.Date;
import java.util.List;

import org.opoo.apps.security.User;

import cn.redflagsoft.base.service.SystemMessage.PublishStatus;


/**
 * 系统消息管理器。
 * 
 * @author Alex Lin(alex@opoo.org)
 */
public interface SystemMessageManager {
	
	/**
	 * 查找指定的消息。
	 * @param id
	 * @return
	 */
	SystemMessage getMessage(long msgId);

	/**
	 * 查询全部系统消息。
	 * @return
	 */
	List<SystemMessage> findMessages();
	
	/**
	 * 获取指定Session的用户系统消息。
	 * 
	 * @param sessionId user session id
	 * @param user User
	 * @return
	 */
	List<SystemMessage> loadMessages(String sessionId, User user);
	
	/**
	 * 创建一条系统信息。
	 * 
	 * 默认状态是草稿。
	 * @param message
	 * @return
	 */
	SystemMessage createMessage(String subject, String content, Date expirationTime, long limit, PublishStatus publishStatus);
	
	/**
	 * 发布系统信息。
	 * 
	 * 将状态改为正常。
	 * @param message
	 * @return
	 */
	SystemMessage publishMessage(SystemMessage message);
	
	/**
	 * 移除消息
	 * @param messageId
	 */
	SystemMessage removeMessage(long messageId);
	
	/**
	 * 
	 * @param autoPublish
	 */
	void setAutoPublish(boolean autoPublish);
	
	/**
	 * 是否自动发布。
	 * @return
	 */
	boolean isAutoPublish();
}
