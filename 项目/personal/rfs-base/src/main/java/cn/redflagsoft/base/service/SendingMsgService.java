package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.SendingMsg;


/**
 * 
 * @deprecated
 *
 */
public interface SendingMsgService {
	/**
	 * ��¼�߷��͵���Ϣ
	 * @param id
	 * @return List<SendingMsg>
	 */
	List<SendingMsg> findSendMessage(Long id);
	
	/**
	 * ������Ϣ����
	 * @param sendingMsgId
	 * @return SendingMsg
	 */
	SendingMsg getSingleSendMessage(Long sendingMsgId);
}
