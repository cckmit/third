/*
 * $Id: MessageService.java 5277 2011-12-26 09:07:11Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.apps.bean.core.Attachment;

import cn.redflagsoft.base.bean.Msg;
import cn.redflagsoft.base.bean.MsgAttachments;
import cn.redflagsoft.base.bean.SendingMsg;

/**
 * @author Alex Lin
 * @deprecated using {@link SmsgService}
 */
public interface MessageService extends MessageManager{

	/**
	 * ���沢������Ϣ��
	 * 
	 * @param smsg
	 * @param autoSend �Ƿ����״̬�Զ����͡�
	 * @return
	 */
	List<Msg> saveSendingMsg(SendingMsg smsg, boolean autoSend);
	
	/**
	 * �����������Ϣ��
	 * 
	 * @param smsg
	 * @return
	 */
	SendingMsg saveSendingMsg(SendingMsg smsg);
	
	
	/**
	 * �Ƿ����״̬�Զ����͡�
	 * 
	 * @param smsg
	 * @param autoSend
	 * @return
	 */
	List<Msg> updateSendingMsg(SendingMsg smsg, boolean autoSend);
	/**
	 * ���´�������Ϣ��
	 * 
	 * @param smsg
	 * @return
	 */
	SendingMsg updateSendingMsg(SendingMsg smsg);
	
	
	/**
	 * ��ȡָ����Ϣ��
	 * 
	 * @param msgId ��Ϣid
	 * @param readerId �Ķ���id��һ����clerkId
	 * @return
	 */
	Msg readMsg(Long msgId, Long readerId);
	
	/**
	 * ��ȡָ����Ϣ��
	 * 
	 * @param msgId
	 * @return
	 */
	Msg getMsg(Long msgId);
	
	/**
	 * ��ȡ��������Ϣ��
	 * 
	 * @param id
	 * @return
	 */
	SendingMsg getSendingMsg(Long id);
	
	/**
	 * ��ȡ�������֪ͨ��
	 * 
	 * @param toId ֻ����0�����棩��1��֪ͨ��
	 * @return
	 */
	List<Msg> findPublicMsgs(Long toId);
	
	/**
	 * ��ȡȫ���������֪ͨ��
	 * 
	 * @return
	 */
	List<Msg> findPublicMsgs();
	
	
	/**
	 * ��ȡָ���û��յ�����Ϣ��
	 * 
	 * @param clerkId ְԱID
	 * @param includeEntityMsg �Ƿ����������Ϣ
	 * @return
	 */
	List<Msg> findPersonalMsgs(long clerkId, boolean includeEntityMsg);
	
	
	/**
	 * ��ȡָ���û���Ϣ����
	 * 
	 * @param clerkId ְԱid
	 * @param includeEntityMsg �Ƿ����������Ϣ
	 * @return
	 */
	int getPersonalMsgCount(long clerkId, boolean includeEntityMsg);
	
	/**
	 * 
	 * @param clerkId
	 * @param includeEntityMsg
	 * @param readStatus
	 * @return
	 */
	List<Msg> findPersonalMsgs(long clerkId, boolean includeEntityMsg, byte readStatus);
	
	/**
	 * 
	 * @param clerkId
	 * @param includeEntityMsg
	 * @param readStatus
	 * @return
	 */
	int getPersonalMsgCount(long clerkId, boolean includeEntityMsg, byte readStatus);
	
	/**
	 * ��ȡĳ�����յ�����Ϣ��
	 * 
	 * @param entityId ����id
	 * @return
	 */
	List<Msg> findEntityMsgs(long entityId);
	
	/**
	 * 
	 * @param entityId
	 * @return
	 */
	int getEntityMsgCount(long entityId);
	
	/**
	 * 
	 * @param entityId
	 * @param readStatus
	 * @return
	 */
	List<Msg> findEntityMsgs(long entityId, byte readStatus);
	
	/**
	 * 
	 * @param entityId
	 * @param readStatus
	 * @return
	 */
	int getEntityMsgCount(long entityId, byte readStatus);
	
	
	
	/**
	 * ��ѯ�ҷ��͵���Ϣ
	 * @param fromId
	 * @return
	 */
	List<SendingMsg> findSendingMsgs(long fromId);
	
	/**
	 * ��ȡ�ݸ�ȡ�
	 * 
	 * @param fromId
	 * @param status
	 * @return
	 */
	List<SendingMsg> findSendingMsgs(long fromId, byte status);
	
	/**
	 * ��ȡָ�������߷��͵���Ϣ��������
	 * 
	 * @param fromId
	 * @return
	 */
	int getSendingMsgCount(long fromId);
	
	/**
	 * ����ָ����Ϣ�ĸ����б�
	 * 
	 * @param sendingMsgId
	 * @return
	 */
	List<Attachment> findAttachments(Long sendingMsgId);
	
	/**
	 * ɾ��ָ����Ϣ�ĸ�����
	 * @param sendingMsgId
	 */
	void removeAttachments(Long sendingMsgId);
	
	/**
	 * ������Ϣ������
	 * 
	 * @param sendingMsgId
	 * @param fileIds
	 * @return
	 */
	List<MsgAttachments> saveAttachments(Long sendingMsgId, List<Long> fileIds);
	

	/**
	 * ��鲢������Ϣ��
	 * 
	 */
	void checkAndSendMsgs();

	
	/**
	 * ��ȡ����֪ͨ������
	 * 
	 * @param getPublicMsgCount
	 * @return
	 */
	int getPublicMsgCount();
	
	 /**
	 * ������Ϣidɾ����Ϣ
	 * @param id
	 */
	String deleteSendMessageByID(Long id);
	/**
	 * ������Ϣid��status�޸���Ϣ��״̬
	 * @param id
	 * @param status
	 */
	String  changeSendMessageStatus(Long id,byte status);
		
	 /**
	 * ������Ϣidɾ����Ϣ
	 * @param id
	 */
	String deleteMsgByID(Long id);
	/**
	 * ������Ϣid��status�޸���Ϣ��״̬
	 * @param id
	 * @param status
	 */
	String changeMsgSendStatus(Long id,byte status);
	/**
	 * Ԥ����Ϣ����֪ͨ
	 */
	void createRemindSendingMsgForAdmin();

}
