package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.SendingMsg;
import cn.redflagsoft.base.dao.SendingMsgDao;
import cn.redflagsoft.base.service.SendingMsgService;

/**
 * 
 * @author 
 * @deprecated replaced by MessageServiceImpl
 */
public class SendingMsgServiceImpl implements SendingMsgService {
	private SendingMsgDao sendingMsgDao;
	
	public SendingMsgDao getSendingMsgDao() {
		return sendingMsgDao;
	}

	public void setSendingMsgDao(SendingMsgDao sendingMsgDao) {
		this.sendingMsgDao = sendingMsgDao;
	}

	public SendingMsg getSingleSendMessage(Long sendingMsgId) {
		return sendingMsgDao.get(sendingMsgId);
	}

	public List<SendingMsg> findSendMessage(Long id) {
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		rf.setCriterion(Restrictions.eq("fromId", id));
		rf.setOrder(Order.desc("creationTime"));
		return sendingMsgDao.find(rf);
	}
}
