package cn.redflagsoft.base.service.impl;

import cn.redflagsoft.base.bean.Msg;
import cn.redflagsoft.base.dao.MsgDao;
import cn.redflagsoft.base.service.MessageSender;

@Deprecated
public class NoticeMessageSenderImpl implements MessageSender {
	private MsgDao msgDao;

	public MsgDao getMsgDao() {
		return msgDao;
	}

	public void setMsgDao(MsgDao msgDao) {
		this.msgDao = msgDao;
	}

	public void send(Msg msg) throws Exception {
		//msgDao.save(msg);
	}

	public boolean supports(Msg msg) {
		return false;
	}

	public int getOrder() {
		return 5;
	}
}
