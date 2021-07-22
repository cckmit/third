/*
 * $Id: MessageService.java 5277 2011-12-26 09:07:11Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
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
	 * 保存并发送消息。
	 * 
	 * @param smsg
	 * @param autoSend 是否根据状态自动发送。
	 * @return
	 */
	List<Msg> saveSendingMsg(SendingMsg smsg, boolean autoSend);
	
	/**
	 * 保存待发送消息。
	 * 
	 * @param smsg
	 * @return
	 */
	SendingMsg saveSendingMsg(SendingMsg smsg);
	
	
	/**
	 * 是否根据状态自动发送。
	 * 
	 * @param smsg
	 * @param autoSend
	 * @return
	 */
	List<Msg> updateSendingMsg(SendingMsg smsg, boolean autoSend);
	/**
	 * 更新待发送消息。
	 * 
	 * @param smsg
	 * @return
	 */
	SendingMsg updateSendingMsg(SendingMsg smsg);
	
	
	/**
	 * 读取指定消息。
	 * 
	 * @param msgId 消息id
	 * @param readerId 阅读者id，一般是clerkId
	 * @return
	 */
	Msg readMsg(Long msgId, Long readerId);
	
	/**
	 * 获取指定消息。
	 * 
	 * @param msgId
	 * @return
	 */
	Msg getMsg(Long msgId);
	
	/**
	 * 获取待发送消息。
	 * 
	 * @param id
	 * @return
	 */
	SendingMsg getSendingMsg(Long id);
	
	/**
	 * 获取公告或者通知。
	 * 
	 * @param toId 只能是0（公告），1（通知）
	 * @return
	 */
	List<Msg> findPublicMsgs(Long toId);
	
	/**
	 * 获取全部公告或者通知。
	 * 
	 * @return
	 */
	List<Msg> findPublicMsgs();
	
	
	/**
	 * 获取指定用户收到的消息。
	 * 
	 * @param clerkId 职员ID
	 * @param includeEntityMsg 是否包含部门消息
	 * @return
	 */
	List<Msg> findPersonalMsgs(long clerkId, boolean includeEntityMsg);
	
	
	/**
	 * 获取指定用户消息数量
	 * 
	 * @param clerkId 职员id
	 * @param includeEntityMsg 是否包含部门消息
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
	 * 获取某部门收到的消息。
	 * 
	 * @param entityId 部门id
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
	 * 查询我发送的消息
	 * @param fromId
	 * @return
	 */
	List<SendingMsg> findSendingMsgs(long fromId);
	
	/**
	 * 读取草稿等。
	 * 
	 * @param fromId
	 * @param status
	 * @return
	 */
	List<SendingMsg> findSendingMsgs(long fromId, byte status);
	
	/**
	 * 获取指定发送者发送的消息的数量。
	 * 
	 * @param fromId
	 * @return
	 */
	int getSendingMsgCount(long fromId);
	
	/**
	 * 查找指定消息的附件列表。
	 * 
	 * @param sendingMsgId
	 * @return
	 */
	List<Attachment> findAttachments(Long sendingMsgId);
	
	/**
	 * 删除指定消息的附件。
	 * @param sendingMsgId
	 */
	void removeAttachments(Long sendingMsgId);
	
	/**
	 * 保存消息附件。
	 * 
	 * @param sendingMsgId
	 * @param fileIds
	 * @return
	 */
	List<MsgAttachments> saveAttachments(Long sendingMsgId, List<Long> fileIds);
	

	/**
	 * 检查并发送消息。
	 * 
	 */
	void checkAndSendMsgs();

	
	/**
	 * 获取公告通知数量。
	 * 
	 * @param getPublicMsgCount
	 * @return
	 */
	int getPublicMsgCount();
	
	 /**
	 * 根据消息id删除消息
	 * @param id
	 */
	String deleteSendMessageByID(Long id);
	/**
	 * 根据消息id和status修改消息的状态
	 * @param id
	 * @param status
	 */
	String  changeSendMessageStatus(Long id,byte status);
		
	 /**
	 * 根据信息id删除信息
	 * @param id
	 */
	String deleteMsgByID(Long id);
	/**
	 * 根据信息id和status修改信息的状态
	 * @param id
	 * @param status
	 */
	String changeMsgSendStatus(Long id,byte status);
	/**
	 * 预备消息处理通知
	 */
	void createRemindSendingMsgForAdmin();

}
