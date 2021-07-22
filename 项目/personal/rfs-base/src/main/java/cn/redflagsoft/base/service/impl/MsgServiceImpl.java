package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Msg;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.SendingMsg;
import cn.redflagsoft.base.dao.ClerkDao;
import cn.redflagsoft.base.dao.MsgDao;
import cn.redflagsoft.base.dao.OrgDao;
import cn.redflagsoft.base.dao.SendingMsgDao;
import cn.redflagsoft.base.event.MsgEvent;
import cn.redflagsoft.base.event.MsgSendListener;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.MessageSender;
import cn.redflagsoft.base.service.MsgService;

/**
 * 
 * @deprecated
 */
public class MsgServiceImpl implements MsgService, MsgSendListener {
	public static final Log log = LogFactory.getLog(MsgServiceImpl.class);
	private SendingMsgDao sendingMsgDao;
	private MsgDao msgDao;
	private ClerkDao clerkDao;
	private OrgDao orgDao;
	private List<MessageSender> senders;

	public ClerkDao getClerkDao() {
		return clerkDao;
	}

	public void setClerkDao(ClerkDao clerkDao) {
		this.clerkDao = clerkDao;
	}

	public SendingMsgDao getSendingMsgDao() {
		return sendingMsgDao;
	}

	public void setSendingMsgDao(SendingMsgDao sendingMsgDao) {
		this.sendingMsgDao = sendingMsgDao;
	}

	public MsgDao getMsgDao() {
		return msgDao;
	}

	public void setMsgDao(MsgDao msgDao) {
		this.msgDao = msgDao;
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
	
	private List<Msg> converToMsgs(SendingMsg sm) {
		List<Msg> msgs = new ArrayList<Msg>();
		Clerk clerk;
		Org org;
		Msg msg;
		if (sm.isSupportsEmail() && sm.getEmailTo() != null) {
			
		}
		if (sm.isSupportsSms() && sm.getSmsTo() != null) {
			
		}
		Map<String, List<Long>> context = split(sm.getReceivers());
		for (Map.Entry<String, List<Long>> entry : context.entrySet()) {
			if (entry.getKey().equals(String.valueOf(Msg.TOTYPE_CLERK))) {
				for (Long value : entry.getValue()) {
					clerk = clerkDao.get(value);
					if (clerk != null) {
						msg = new Msg();
						msg.setTitle(sm.getTitle());
						msg.setContent(sm.getContent());
						msg.setToId(value);
						msg.setToType(Msg.TOTYPE_CLERK);
						msg.setToName(clerk.getName());
						msg.setFromId(sm.getFromId());
						msg.setSendTime(new Date());
						msg.setSendingMsgId(sm.getId());
						msgs.add(msg);
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
						msg.setSendTime(new Date());
						msg.setSendingMsgId(sm.getId());
						msgs.add(msg);
					}
				}
			}
		}		
		return msgs;
	}
	
	private void sendMsg(Msg msg) throws Exception{
//		if (msgSender instanceof EventDispatcher) {
//
//		}
		for (MessageSender sender : senders) {
			if (sender.supports(msg)) {
				sender.send(msg);
			}
		}
	}

	public SendingMsg saveAndSendMsg(SendingMsg sm, boolean sendingNow) {
		if (sm == null) {
			throw new NullPointerException("sm is null");
		}
		if (sm.getCreationTime() == null) {
			sm.setCreationTime(new Date());
		}
		SendingMsg tmp = sendingMsgDao.save(sm);
		List<Msg> msgs = converToMsgs(tmp);
		for (Msg msg : msgs) {
			try {
				sendMsg(msg);
			} catch(Exception ex) {
				log.error("sendMsg Ê§°Ü,ÏûÏ¢ÄÚÈÝ -->" + msg.toJSONString(), ex);
			}
		}
		return tmp;
	}

	public List<Msg> findAcceptMessage(Long id, Byte readStatus) {
		if (readStatus == null) {
			readStatus = 0;
		}
		Clerk clerk = clerkDao.get(id);
		return msgDao.findAcceptMessage(id, clerk != null ? clerk.getEntityID() : null, readStatus);
	}
	
	public List<Msg> findAcceptMessage(Long id, Date time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date start = cal.getTime();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		Date end = cal.getTime();
		return msgDao.findAcceptMessageBySendTime(id, start, end);
	}	

	public Msg getAcceptMessage(Long msgId) {
		return msgDao.get(msgId);
	}

	public boolean modfiyMessageReader(Long id, Long msgId) {
		Msg msg = msgDao.get(Restrictions.logic(
				Restrictions.eq("toType", Msg.TOTYPE_ORG)).and(
				Restrictions.eq("id", msgId))
				.and(Restrictions.isNull("reader")));
		if (msg == null) {
			return false;
		} else {
			msg.setReader(id);
			msgDao.update(msg);
			return true;
		}
	}
	
	public int getUnreadMessageCount() {
		Clerk clerk = UserClerkHolder.getClerk();
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		rf.setCriterion(Restrictions.logic(
				Restrictions.eq("toId", clerk.getId())).and(
				Restrictions.eq("toType", Msg.TOTYPE_CLERK)).and(
				Restrictions.eq("readStatus", Msg.READ_STATUS_UNREAD)));
		return msgDao.getCount(rf);
	}

	public List<Msg> findNoticeMessage() {
		return findNoticeMessage(null);
	}

	public List<Msg> findNoticeMessage(Long fromId) {
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		Logic logic = Restrictions.logic(Restrictions.eq("type", Msg.NOTICE)).and(Restrictions.eq("toId", Long.parseLong("0")));
		if (fromId != null) {
			logic.and(Restrictions.eq("fromId", fromId));
		}
		rf.setOrder(Order.desc("sendTime"));
		return msgDao.find(rf);
	}

	public List<Msg> findUnsentMessage(Long fromId) {
		/*ResultFilter rf = ResultFilter.createEmptyResultFilter();
		Logic logic = Restrictions.logic(Restrictions.eq("sendStatus", Msg.SEND_STATUS_UNSENT)).or(Restrictions.eq("sendStatus", Msg.SEND_STATUS_FAIL));
		if (fromId != null) {
			logic.and(Restrictions.eq("fromId", fromId));
		}
		rf.setCriterion(logic);
		rf.setOrder(Order.desc("sendTime"));*/

		return msgDao.findUnsentMessage(fromId);
	}

	public List<Msg> findUnsentMessage() {
		return findUnsentMessage(null);
	}
	
	public void perform(MsgEvent event) {
		if (event.getEventType() == MsgEvent.SENDING) {
			if (event.getSource() instanceof Msg) {
				event.getObject().setSendStatus(Msg.SEND_STATUS_SENDING);
				msgDao.update(event.getObject());
			}
		} else if (event.getEventType() == MsgEvent.SENT) {
			if (event.getSource() instanceof Msg) {
				event.getObject().setSendStatus(Msg.SEND_STATUS_SEND);
				msgDao.update(event.getObject());
			}
		}
	}
}
