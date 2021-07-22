/*
 * $Id: MessageServiceImpl.java 5798 2012-05-29 03:33:41Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.annotation.Queryable;
import org.opoo.apps.bean.core.Attachment;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Msg;
import cn.redflagsoft.base.bean.MsgAttachments;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.SendingMsg;
import cn.redflagsoft.base.dao.MsgAttachmentsDao;
import cn.redflagsoft.base.dao.MsgDao;
import cn.redflagsoft.base.dao.OrgDao;
import cn.redflagsoft.base.dao.SendingMsgDao;
import cn.redflagsoft.base.event.MsgSendEvent;
import cn.redflagsoft.base.event.MsgSendEvent.Type;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.EventMsgService;
import cn.redflagsoft.base.service.MessageSender;
import cn.redflagsoft.base.service.MessageService;

/**
 * @author Alex Lin
 * @deprecated
 */
public class MessageServiceImpl implements MessageService, ApplicationContextAware, ApplicationListener {
	public static final Log log = LogFactory.getLog(MessageServiceImpl.class);
	private MsgDao msgDao;
	private SendingMsgDao sendingMsgDao;
	private ClerkService clerkService;
	private OrgDao orgDao;
	private MsgAttachmentsDao msgAttachmentsDao;
	private List<MessageSender> senders;	
	private EventMsgService eventMsgService;
	
	
	public EventMsgService getEventMsgService() {
		return eventMsgService;
	}

	public void setEventMsgService(EventMsgService eventMsgService) {
		this.eventMsgService = eventMsgService;
	}

	public MsgDao getMsgDao() {
		return msgDao;
	}

	public void setMsgDao(MsgDao msgDao) {
		this.msgDao = msgDao;
	}

	public SendingMsgDao getSendingMsgDao() {
		return sendingMsgDao;
	}

	public void setSendingMsgDao(SendingMsgDao sendingMsgDao) {
		this.sendingMsgDao = sendingMsgDao;
	}

	public ClerkService getClerkService() {
		return clerkService;
	}

	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}

	public OrgDao getOrgDao() {
		return orgDao;
	}

	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}

	public List<MessageSender> getSenders() {
		return senders;
	}

	public void setSenders(List<MessageSender> senders) {
		this.senders = senders;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MessageService#findEntityMsgs(long)
	 */
	public List<Msg> findEntityMsgs(long entityId) {
		Logic logic = Restrictions.logic(Restrictions.eq("toType", Msg.TOTYPE_ORG))
		.and(Restrictions.eq("toId", entityId));
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(logic);
		filter.setOrder(Order.desc("sendTime"));
		return msgDao.find(filter);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MessageService#findEntityMsgs(long, byte)
	 */
	public List<Msg> findEntityMsgs(long entityId, byte readStatus) {
		Logic logic = Restrictions.logic(Restrictions.eq("toType", Msg.TOTYPE_ORG))
		.and(Restrictions.eq("toId", entityId))
		.and(Restrictions.eq("readStatus", readStatus));
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(logic);
		filter.setOrder(Order.desc("sendTime"));
		return msgDao.find(filter);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MessageService#findPersonalMsgs(long, boolean)
	 */
	public List<Msg> findPersonalMsgs(long clerkId, boolean includeEntityMsg) {
		Logic logic = Restrictions.logic(Restrictions.eq("toType", Msg.TOTYPE_CLERK))
		.and(Restrictions.eq("toId", clerkId));
		
		if(includeEntityMsg){
			Clerk clerk = clerkService.getClerk(clerkId);
			Logic logic2 = Restrictions.logic(Restrictions.eq("toType", Msg.TOTYPE_ORG))
			.and(Restrictions.eq("toId", clerk.getEntityID()));
			
			logic = Restrictions.logic(logic).or(logic2);
		}
		logic=Restrictions.logic(logic).and(Restrictions.eq("type", Msg.TYPE_INTERNAL));//只显示内部消息
		
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(logic);
		filter.setOrder(Order.desc("sendTime"));
		return msgDao.find(filter);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MessageService#findPersonalMsgs(long, boolean, byte)
	 */
	public List<Msg> findPersonalMsgs(long clerkId, boolean includeEntityMsg, byte readStatus) {
		Logic logic = Restrictions.logic(Restrictions.eq("toType", Msg.TOTYPE_CLERK))
		.and(Restrictions.eq("toId", clerkId));
		
		if(includeEntityMsg){
			Clerk clerk = clerkService.getClerk(clerkId);
			Logic logic2 = Restrictions.logic(Restrictions.eq("toType", Msg.TOTYPE_ORG))
			.and(Restrictions.eq("toId", clerk.getEntityID()));
			
			logic = Restrictions.logic(logic).or(logic2);
		}
		//logic = Restrictions.logic(logic);
		logic=Restrictions.logic(logic).and(Restrictions.eq("type", Msg.TYPE_INTERNAL));//只显示内部消息
		logic.and(Restrictions.eq("readStatus", readStatus));
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(logic);
		filter.setOrder(Order.desc("sendTime"));
		return msgDao.find(filter);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MessageService#findPublicMsgs(short)
	 */
	public List<Msg> findPublicMsgs(Long toId) {
		Logic logic = Restrictions.logic(Restrictions.eq("toType", Msg.TOTYPE_PUBLIC))
		.and(Restrictions.eq("toId", toId));
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(logic);
		filter.setOrder(Order.desc("sendTime"));
		return msgDao.find(filter);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MessageService#findPublicMsgs()
	 */
	public List<Msg> findPublicMsgs() {
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(Restrictions.eq("toType", Msg.TOTYPE_PUBLIC));
		filter.setOrder(Order.desc("sendTime"));
		return msgDao.find(filter);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MessageService#findSendingMsgs(long)
	 */
	public List<SendingMsg> findSendingMsgs(long fromId) {
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(Restrictions.eq("fromId", fromId));
		filter.setOrder(Order.desc("creationTime").add(Order.desc("modificationTime")));
		return sendingMsgDao.find(filter);
	}
	
	public List<SendingMsg> findSendingMsgs(long fromId, byte status) {
		Logic logic = Restrictions.logic(Restrictions.eq("status", status));
		logic = logic.and(Restrictions.eq("fromId", fromId));
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(logic);
		filter.setOrder(Order.desc("modificationTime"));
		return sendingMsgDao.find(filter);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MessageService#getEntityMsgCount(long)
	 */
	public int getEntityMsgCount(long entityId) {
		Logic logic = Restrictions.logic(Restrictions.eq("toType", Msg.TOTYPE_ORG))
		.and(Restrictions.eq("toId", entityId));
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(logic);
		return msgDao.getCount(filter);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MessageService#getEntityMsgCount(long, byte)
	 */
	public int getEntityMsgCount(long entityId, byte readStatus) {
		Logic logic = Restrictions.logic(Restrictions.eq("toType", Msg.TOTYPE_ORG))
		.and(Restrictions.eq("toId", entityId))
		.and(Restrictions.eq("readStatus", readStatus));
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(logic);
		return msgDao.getCount(filter);
	}

	/**
	 * 
	 */
	public Msg getMsg(Long msgId){
		return msgDao.get(msgId);
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MessageService#getMsg(java.lang.Long)
	 */
	public Msg readMsg(Long msgId, Long readerId) {
		Msg msg = msgDao.get(msgId);

		//如果是给个人或者部门的内部消息，未读则标记为已读
		if (msg != null && msg.getType() == Msg.TYPE_INTERNAL
				&& msg.getReadStatus() == Msg.READ_STATUS_UNREAD
				&& (msg.getToType() == Msg.TOTYPE_CLERK ||
						msg.getToType() == Msg.TOTYPE_ORG)) {
			msg.setReader(readerId);
			msg.setReadStatus(Msg.READ_STATUS_READ);
			msg.setReadTime(new Date());
			msgDao.update(msg);
			
//			if (msg.getType() == Msg.TYPE_INTERNAL
//					&& msg.getToType() == Msg.TOTYPE_ORG
//					&& msg.getReader() == null
//					&& msg.getReadStatus() == Msg.READ_STATUS_UNREAD) {
//				msg.setReader(id);
//				msg.setReadStatus(Msg.READ_STATUS_READ);
//				msg.setReadTime(new Date());
//				msgDao.update(msg);
//			} else if (msg.getType() == Msg.TYPE_INTERNAL
//					&& msg.getToType() == Msg.TOTYPE_CLERK
//					&& msg.getReadStatus() == Msg.READ_STATUS_UNREAD) {
//				msg.setReadStatus(Msg.READ_STATUS_READ);
//				msg.setReadTime(new Date());
//				msgDao.update(msg);
//			}
			
		}
		
		return msg;
	}
	


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MessageService#getPersonalMsgCount(long, boolean)
	 */
	public int getPersonalMsgCount(long clerkId, boolean includeEntityMsg) {
		Logic logic = Restrictions.logic(Restrictions.eq("toType", Msg.TOTYPE_CLERK))
		.and(Restrictions.eq("toId", clerkId));
		
		if(includeEntityMsg){
			Clerk clerk = clerkService.getClerk(clerkId);
			Logic logic2 = Restrictions.logic(Restrictions.eq("toType", Msg.TOTYPE_ORG))
			.and(Restrictions.eq("toId", clerk.getEntityID()));
			
			logic = Restrictions.logic(logic).or(logic2);
		}

		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(logic);
		return msgDao.getCount(filter);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MessageService#getPersonalMsgCount(long, boolean, byte)
	 */
	public int getPersonalMsgCount(long clerkId, boolean includeEntityMsg,	byte readStatus) {
		Logic logic = Restrictions.logic(Restrictions.eq("toType", Msg.TOTYPE_CLERK))
		.and(Restrictions.eq("toId", clerkId)).and(Restrictions.eq("type",Msg.TYPE_INTERNAL));
		
		if(includeEntityMsg){
			Clerk clerk = clerkService.getClerk(clerkId);
			Logic logic2 = Restrictions.logic(Restrictions.eq("toType", Msg.TOTYPE_ORG))
			.and(Restrictions.eq("toId", clerk.getEntityID()));
			
			logic = Restrictions.logic(logic).or(logic2);
		}
		logic = Restrictions.logic(logic);
		logic.and(Restrictions.eq("readStatus", readStatus));
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(logic);
		return msgDao.getCount(filter);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MessageService#getSendingMsg(java.lang.Long)
	 */
	public SendingMsg getSendingMsg(Long id) {
		return sendingMsgDao.get(id);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MessageService#getSendingMsgCount(long)
	 */
	public int getSendingMsgCount(long fromId) {
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(Restrictions.eq("fromId", fromId));
		return sendingMsgDao.getCount(filter);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 */
	public SendingMsg saveSendingMsg(SendingMsg smsg){
		saveSendingMsg(smsg, true);
		return smsg;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MessageService#saveSendingMsg(cn.redflagsoft.base.bean.SendingMsg, boolean)
	 */
	public List<Msg> saveSendingMsg(SendingMsg smsg, boolean autoSend) {
		List<Msg> msgs = null;
		smsg = sendingMsgDao.save(smsg);
		log.debug("sendingMsg.status = " + smsg.getStatus());
		if(SendingMsg.STATUS_READY == smsg.getStatus()){
			if(log.isDebugEnabled()){
				log.debug("拆分消息：" + smsg);
			}
			msgs = convertSendingMsgToMsgs(smsg);//convertToMsgs(smsg);
			saveMsgs(msgs);
			if(autoSend){
				sendMsgs(msgs);
			}
		}
		
		
		
//		//sendingMsgDao.save(smsg);
//		List<Msg> msgs = converToMsgs(smsg);
//		
//		smsg.setStatus(SendingMsg.STATUS_SPLIT);
//		smsg.setModificationTime(new Date());
//		saveSendingMsg(smsg);
//		
//		saveMsgs(msgs);
//		
//		if(sentNow){
//			sendMsgs(msgs);
//		}
//		
//		for (Msg msg : msgs) {
//			msgDao.save(msg);
//		}
//		if (sentNow) {
//			for (Msg msg : msgs) {
//				try {
//					sendMsg(msg);
//				} catch (Exception e) {
//					log.error("sendMsg 失败,消息内容 -->" + msg.toJSONString(), e);
//				}
//			}			
//		}
		return msgs;
	}
	
	/**
	 * 
	 */
	public SendingMsg updateSendingMsg(SendingMsg smsg){
		updateSendingMsg(smsg, true);
		return smsg;
	}
	
	/**
	 * 
	 */
	public List<Msg> updateSendingMsg(SendingMsg smsg, boolean autoSend){
		List<Msg> msgs = null;
		smsg = sendingMsgDao.update(smsg);
		if(SendingMsg.STATUS_READY == smsg.getStatus()){
			if(log.isDebugEnabled()){
				log.debug("拆分消息：" + smsg);
			}
			msgs = convertSendingMsgToMsgs(smsg);//convertToMsgs(smsg);
			saveMsgs(msgs);
			if(autoSend){
				sendMsgs(msgs);
			}
		}
		return msgs;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.redflagsoft.base.service.MessageManager#sendMsg(cn.redflagsoft.base
	 * .bean.Msg)
	 */
	public Msg sendMsg(Msg msg) throws Exception {

		if (senders != null) {
			for (MessageSender sender : senders) {
				if (sender.supports(msg)) {
					if (log.isDebugEnabled()) {
						log.debug("Try to send msg " + msg + " using sender: " + sender);
					}
					sender.send(msg);
					return msg;
				}
			}
		}

		throw new Exception("消息发送失败，找不到适当的发送器：" + msg.toJSONString());
	}
	
	


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MessageManager#sendMsgs(boolean)
	 */
	public List<Msg> sendMsgs(long sendingMsgId) throws Exception {
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		rf.setCriterion(Restrictions.eq("sendingMsgId", sendingMsgId));
		List<Msg> msgs = msgDao.find(rf);
		
		sendMsgs(msgs);
//		
//		if (msgs == null || msgs.isEmpty()) {//如果有且未分拆
//			SendingMsg sendingMsg = sendingMsgDao.get(sendingMsgId);
//			if (sendingMsg != null && sendingMsg.getStatus() != SendingMsg.STATUS_SPLIT) {
//				//saveSendingMsg(sendingMsg, true);
//				sendingMsg.setStatus(SendingMsg.STATUS_SPLIT);
//				sendingMsgDao.update(sendingMsg);
//				
//				saveMsgs(msgs);
//				sendMsgs(msgs);
//			}
//		} else {
//			sendMsgs(msgs);
//			
////			for (Msg msg : msgs) {
////				try {
////					sendMsg(msg);
////				} catch (Exception e) {
////					log.error("sendMsg 失败,消息内容 -->" + msg.toJSONString(), e);
////				}
////			}
//		}
		return msgs;
	}

	
	/**
	 * 可能报错，允许异常
	 * @param receiver
	 * @return
	 */
	private List<Receiver> parseReceivers(String receiver){
		List<Receiver> receivers = new ArrayList<Receiver>();
		String[] nodes = receiver.split(";");
		for(int i = 0 ; i < nodes.length ; i++){
			Receiver r = parseReceiver(nodes[i]);
			receivers.add(r);
		}
		return receivers;
	}
	
	private Receiver parseReceiver(String receiver){
		String[] node = receiver.split(":");
		return new Receiver(Byte.valueOf(node[0]), Long.valueOf(node[1]));
	}
	
	private void copySendingMsgToMsg(SendingMsg sm, Msg msg, Receiver r){
		msg.setAttached(sm.isAttached());
		msg.setContent(sm.getContent());
		msg.setFromId(sm.getFromId());
		msg.setFromName(sm.getFromName());
		msg.setReadStatus(Msg.READ_STATUS_UNREAD);
		msg.setSendingMsgId(sm.getId());
		msg.setStatus((byte) 0);
		msg.setTitle(sm.getTitle());
		msg.setToId(r.toId);
		msg.setToType(r.toType);
		msg.setPublishTime(sm.getPublishTime());
		msg.setExpirationTime(sm.getExpirationTime());
		//msg.setType(Msg.TYPE_INTERNAL);
	}
	private Msg createMsg(SendingMsg sm, Receiver r){
		Msg msg = new Msg();
		copySendingMsgToMsg(sm, msg, r);
		return msg;
	}
	
	private void addPublicMsg(List<Msg> msgs, SendingMsg sm){
		Receiver r = parseReceiver(sm.getReceivers());
		Msg msg = createMsg(sm, r);
		msg.setType(Msg.TYPE_INTERNAL);
		msg.setSendTime(new Date());
		msgs.add(msg);
		if(log.isDebugEnabled()){
			log.debug("拆公共消息：" + msg.toJSONString());
		}
	}
	
	private void addOrgMsg(List<Msg> msgs, SendingMsg sm, Receiver r){
		Org org = orgDao.get(r.toId);
		Msg msg = createMsg(sm, r);
		msg.setType(Msg.TYPE_INTERNAL);
		msg.setSendTime(new Date());
		msg.setToName(org.getName());
		msgs.add(msg);
		if(log.isDebugEnabled()){
			log.debug("拆部门消息：" + msg.toJSONString());
		}
	}
	
	private void addClerkMsg(List<Msg> msgs, SendingMsg sm, Receiver r){
		Clerk clerk = clerkService.getClerk(r.toId);
		if(clerk == null){
			return;
		}
		//内部
		Msg msg = createMsg(sm, r);
		msg.setType(Msg.TYPE_INTERNAL);
		msg.setSendTime(new Date());
		msg.setToName(clerk.getName());
		
		msgs.add(msg);
		if(log.isDebugEnabled()){
			log.debug("拆内部消息：" + msg.toJSONString());
		}
		
		//Email
		if (sm.isSupportsEmail() && clerk.getEmailAddr() != null) {
			msg = createMsg(sm, r);
			msg.setType(Msg.TYPE_EMAIL);
			msg.setToAddr(clerk.getEmailAddr());
			msg.setToName(clerk.getName());
			msgs.add(msg);
			if(log.isDebugEnabled()){
				log.debug("拆邮件消息：" + msg.toJSONString());
			}
		}
		if (sm.isSupportsSms() && clerk.getMobNo() != null) {
			msg = createMsg(sm, r);
			msg.setType(Msg.TYPE_SMS);
			//msg.setTitle(sm.getContent());//SMS信息内容改存content字段
			msg.setToAddr(clerk.getMobNo());
			msg.setToName(clerk.getName());
			msgs.add(msg);
			if(log.isDebugEnabled()){
				log.debug("拆短信消息：" + msg.toJSONString());
			}
		}		
	}
	
	private List<Msg> convertSendingMsgToMsgs(SendingMsg sm){
		List<Msg> msgs = new ArrayList<Msg>();
		if(SendingMsg.RECEIVERS_NOTICE.equals(sm.getReceivers())
				|| SendingMsg.RECEIVERS_ANNOUNCEMENT.equals(sm.getReceivers())){
			addPublicMsg(msgs, sm);
		}else{
			List<Receiver> receivers = parseReceivers(sm.getReceivers());
			for(Receiver r: receivers){
				if(Msg.TOTYPE_ORG == r.toType){
					addOrgMsg(msgs, sm, r);
				}else if(Msg.TOTYPE_CLERK == r.toType){
					addClerkMsg(msgs, sm, r);
				}
			}
			
			
			//指定的邮件地址
			if(sm.isSupportsEmail() && StringUtils.isNotBlank(sm.getEmailTo())){
				log.warn("暂时不处理直接发给指定的邮件地址：" + sm.getEmailTo());
			}
			//指定的短信地址
			if(sm.isSupportsSms() && StringUtils.isNotBlank(sm.getSmsTo())){
				log.warn("暂时不处理直接发给指定的短信地址: " + sm.getSmsTo());
			}
		}
		return msgs;
	}
	

	
	
	
	/**
	 * 
	 * @param str
	 * @return
	 * @deprecated
	 */
	private Map<String, List<Long>> split(String str) {
		String[] parent = str.split(";");
		Map<String, List<Long>> context = new HashMap<String, List<Long>>();
		for (String string : parent) {
			String[] child = string.split(":");
			if (context.containsKey(child[0])) {
				context.get(child[0]).add(Long.valueOf(child[1]));
			} else {
				ArrayList<Long> array = new ArrayList<Long>();
				array.add(Long.valueOf(child[1]));
				context.put(child[0], array);
			}
		}
		return context;
	}
	

	
	/**
	 * 
	 * @param sm
	 * @return
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private List<Msg> convertToMsgs(SendingMsg sm) {
		List<Msg> msgs = new ArrayList<Msg>();
		if (sm.getType() == Msg.TYPE_INTERNAL) {
			Clerk clerk;
			Org org;
			Msg msg;
			Map<String, List<Long>> context = split(sm.getReceivers());
			for (Map.Entry<String, List<Long>> entry : context.entrySet()) {
				if (entry.getKey().equals(String.valueOf(Msg.TOTYPE_CLERK))) {
					for (Long value : entry.getValue()) {
						clerk = clerkService.getClerk(value);
						if (clerk != null) {
							msg = new Msg();
							msg.setTitle(sm.getTitle());
							msg.setContent(sm.getContent());
							msg.setToId(value);
							msg.setToType(Msg.TOTYPE_CLERK);
							msg.setToName(clerk.getName());
							msg.setFromId(sm.getFromId());
							msg.setSendingMsgId(sm.getId());
							msg.setFromName(sm.getFromName());
							msgs.add(msg);
							if (clerk.getEmailAddr() != null && sm.isSupportsEmail()) {
								msg = new Msg();
								msg.setType(Msg.TYPE_EMAIL);
								msg.setTitle(sm.getTitle());
								msg.setContent(sm.getContent());
								msg.setToAddr(clerk.getEmailAddr());
								msg.setToName(clerk.getName());
								msg.setFromId(sm.getFromId());
								msg.setSendingMsgId(sm.getId());
								msg.setFromName(sm.getFromName());
								msgs.add(msg);
							}
							if (clerk.getMobNo() != null && sm.isSupportsSms()) {
								msg = new Msg();
								msg.setType(Msg.TYPE_SMS);
								msg.setTitle(sm.getContent());//SMS信息内容改存content字段
								msg.setToAddr(clerk.getMobNo());
								msg.setToName(clerk.getName());
								msg.setFromId(sm.getFromId());
								msg.setSendingMsgId(sm.getId());
								msg.setFromName(sm.getFromName());
								msgs.add(msg);
							}						
						}
					}
				} else if (entry.getKey().equals(String.valueOf(Msg.TOTYPE_ORG))) {
					for (Long value : entry.getValue()) {
						org = orgDao.get(value);
						if (org != null) {
							msg = new Msg();
							msg.setTitle(sm.getTitle());
							msg.setContent(sm.getContent());
							msg.setToId(value);
							msg.setToType(Msg.TOTYPE_ORG);
							msg.setToName(org.getName());
							msg.setFromId(sm.getFromId());
							msg.setSendingMsgId(sm.getId());
							msg.setFromName(sm.getFromName());
							msgs.add(msg);
						}
					}
				}
			}
//		} else if(sm.getType() == SendingMsg.TYPE_ANNOUNCEMENT
//				|| sm.getType() == SendingMsg.TYPE_NOTICE){//101,100
//			Msg  msg = new Msg();
//			msg.setType(sm.getType());
//			msg.setTitle(sm.getTitle());
//			msg.setContent(sm.getContent());
//			msg.setToId(Msg.TOID_PUBLIC);
//			msg.setFromId(sm.getFromId());
//			msg.setSendingMsgId(sm.getId());
//			msgs.add(msg);
		}else{
			log.warn("未知类型的消息，无法分拆：" + sm.getType());
		}
		return msgs;
	}	
	
	
	/**
	 * 
	 * @param msgs
	 */
	protected void saveMsgs(List<Msg> msgs){
		for (Msg msg : msgs) {
			msgDao.save(msg);
		}
	}
	/**
	 * 
	 * @param msgs
	 */
	protected void sendMsgs(List<Msg> msgs){
		for (Msg msg : msgs) {
			try {	
				if(msg.getPublishTime()==null&&Msg.TYPE_INTERNAL != msg.getType()){	
					sendMsg(msg);
				}
				
			} catch (Exception e) {
				//内部消息不必记录。
				if(Msg.TYPE_INTERNAL != msg.getType()){
					log.error("消息发送失败：" + msg.toJSONString(), e);
				}else{
					///修改发送状态为失败
					if(msg.getTrySendCount()>=Msg.TRY_SEND_COUNT_MAX){
						msg.setSendStatus(Msg.SEND_STATUS_DISABLE);
					}else{
						msg.setSendStatus(Msg.SEND_STATUS_FAIL);
					}
					msg.setTrySendCount(msg.getTrySendCount()+1);
					msgDao.update(msg);
				}
			}
		}	
		//checkAndSendMsgs();
	}
	
	
	private static class Receiver{
		public byte toType;
		public long toId;
		
		public Receiver(byte toType, long toId){
			this.toType = toType;
			this.toId = toId;
		}
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MessageService#findAttachments(java.lang.Long)
	 */
	public List<Attachment> findAttachments(Long sendingMsgId) {
		return msgAttachmentsDao.findAttachmentsBySendingMsgId(sendingMsgId);
	}

	/**
	 * @return the msgAttachmentsDao
	 */
	public MsgAttachmentsDao getMsgAttachmentsDao() {
		return msgAttachmentsDao;
	}

	/**
	 * @param msgAttachmentsDao the msgAttachmentsDao to set
	 */
	public void setMsgAttachmentsDao(MsgAttachmentsDao msgAttachmentsDao) {
		this.msgAttachmentsDao = msgAttachmentsDao;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MessageService#removeAttachments(java.lang.Long)
	 */
	public void removeAttachments(Long sendingMsgId) {
		Criterion c = Restrictions.eq("sendingMsgId", sendingMsgId);
		msgAttachmentsDao.remove(c);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MessageService#saveAttachment(cn.redflagsoft.base.bean.SendingMsg, java.util.List)
	 */
	public List<MsgAttachments> saveAttachments(Long sendingMsgId, List<Long> fileIds) {
		List<MsgAttachments> result = new ArrayList<MsgAttachments>();
		if(fileIds != null && fileIds.size() > 0){
			for(Long fileId: fileIds){
				MsgAttachments ma = new MsgAttachments();
				ma.setFileId(fileId);
				ma.setSendingMsgId(sendingMsgId);
				ma.setType( 0);
				ma = msgAttachmentsDao.save(ma);
				result.add(ma);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@SuppressWarnings("unchecked")
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if(getSenders() == null){
			Map<String, MessageSender> map = applicationContext.getBeansOfType(MessageSender.class);
			if(!map.isEmpty()){
				List<MessageSender> beans = new ArrayList<MessageSender>(map.values());
				setSenders(beans);
			}
		}
	}
	public void checkAndSendMsgs(){
		List<Msg> msgs = msgDao.findWaitSend(Msg.TRY_SEND_COUNT_MAX);
		for(Msg m:msgs){
			try {
				sendMsg(m);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof MsgSendEvent){
			perform((MsgSendEvent)event);
		}
	}

	/**
	 * @param event
	 */
	private void perform(MsgSendEvent event) {
		//System.out.println("messageServcie接收到发送短信消息:"+event.toString());
		Msg m=(Msg)event.getSource();
		if(Type.SENT==event.getType()){
			m.setSendStatus(Msg.SEND_STATUS_SEND);
			m.setSendTime(new Date());
			msgDao.update(m);
		}else if(Type.FAIL==event.getType())
		{	if(m.getTrySendCount()>=Msg.TRY_SEND_COUNT_MAX){
				m.setSendStatus(Msg.SEND_STATUS_DISABLE);
			}else{
				m.setSendStatus(Msg.SEND_STATUS_FAIL);
			}
			
			m.setTrySendCount(m.getTrySendCount()+1);
			msgDao.update(m);
		}
		//msgDao.update(m);
		
	}
	
	public int getPublicMsgCount(){
		Logic logic = Restrictions.logic(Restrictions.eq("toType", Msg.TOTYPE_PUBLIC));
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(logic);
		return msgDao.getCount(filter);
	}
	
	@Queryable(argNames={"id"})
	public String deleteSendMessageByID(Long id){
		SendingMsg sendingMsg=sendingMsgDao.get(id);
		if(sendingMsg!=null){
			sendingMsgDao.delete(sendingMsg);
			return "删除消息成功！";
		}else{
		log.warn("删除消息失败，删除对像不能为空！");
		return "删除消息失败！";
		}
	}
	
	@Queryable(argNames={"id","status"})
	public String  changeSendMessageStatus(Long id,byte status){
		SendingMsg sendingMsg=sendingMsgDao.get(id);
		if(sendingMsg!=null){
			if(status!=sendingMsg.getStatus()){
				sendingMsg.setStatus(status);
				sendingMsgDao.update(sendingMsg);
				return "修改状态成功！";
			}else{
				return "修改状态失败，新状态与原状态相同。";
			}
		}else
		{	log.warn("修改状态失败，修改对像不能为空！");
			return "修改状态失败！";
		}
	}
	
	@Queryable(argNames={"id"})
	public String deleteMsgByID(Long id){
	    Msg msg=msgDao.get(id);
	    if(msg!=null){
	    	msgDao.delete(msg);
	    	return "删除信息成功！";
	    }else{
	    	log.warn("删除信息失败,删除对象不能为空！");
	    	return "删除信息失败！";
	    }
	}
	
	@Queryable(argNames={"id","status"})
	public String changeMsgSendStatus(Long id,byte status){
		 Msg msg=msgDao.get(id);
		 if(msg!=null){
			 if(status!=msg.getSendingMsgId()){
				 msg.setSendStatus(status);
				 msgDao.update(msg);
				 return "修改状态成功";
			 }else{
				 return "修改状态失败，新状态与原状态相同。";
			 }
			 
		 }else{
			 log.warn("修改状态无法获得msg对象！");
			 return "修改状态失败";
		 }	
	}
	
	public void createRemindSendingMsgForAdmin(){
		/*
		int count=sendingMsgDao.getConfirmSendingMsgCount();
		Map<String, String> tmp=new HashMap<String, String>();
		tmp.put("${SendingMsg_Count}",String.valueOf(count));
		if(count>0){
			List<EventMsg> eventMsgs=eventMsgService.findEventMsgCfg(1001, 0L, 201L);
			for(EventMsg em:eventMsgs){
				if(em.getIsCreateMsg()==EventMsg.IS_TRUE){
					SendingMsg sm=eventMsgService.createSmsgByEventMsg(em,0L,null,tmp);
					saveSendingMsg(sm);
				}
			}	
		}
		*/
	}
}
