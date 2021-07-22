package cn.redflagsoft.base.service;

import java.util.Date;
import java.util.List;

import org.opoo.apps.security.User;

import cn.redflagsoft.base.service.SystemMessage.PublishStatus;


/**
 * ϵͳ��Ϣ��������
 * 
 * @author Alex Lin(alex@opoo.org)
 */
public interface SystemMessageManager {
	
	/**
	 * ����ָ������Ϣ��
	 * @param id
	 * @return
	 */
	SystemMessage getMessage(long msgId);

	/**
	 * ��ѯȫ��ϵͳ��Ϣ��
	 * @return
	 */
	List<SystemMessage> findMessages();
	
	/**
	 * ��ȡָ��Session���û�ϵͳ��Ϣ��
	 * 
	 * @param sessionId user session id
	 * @param user User
	 * @return
	 */
	List<SystemMessage> loadMessages(String sessionId, User user);
	
	/**
	 * ����һ��ϵͳ��Ϣ��
	 * 
	 * Ĭ��״̬�ǲݸ塣
	 * @param message
	 * @return
	 */
	SystemMessage createMessage(String subject, String content, Date expirationTime, long limit, PublishStatus publishStatus);
	
	/**
	 * ����ϵͳ��Ϣ��
	 * 
	 * ��״̬��Ϊ������
	 * @param message
	 * @return
	 */
	SystemMessage publishMessage(SystemMessage message);
	
	/**
	 * �Ƴ���Ϣ
	 * @param messageId
	 */
	SystemMessage removeMessage(long messageId);
	
	/**
	 * 
	 * @param autoPublish
	 */
	void setAutoPublish(boolean autoPublish);
	
	/**
	 * �Ƿ��Զ�������
	 * @return
	 */
	boolean isAutoPublish();
}
