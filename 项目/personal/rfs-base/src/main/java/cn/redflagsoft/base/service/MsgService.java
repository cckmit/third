package cn.redflagsoft.base.service;

import java.util.Date;
import java.util.List;

import cn.redflagsoft.base.bean.Msg;
import cn.redflagsoft.base.bean.SendingMsg;

/**
 * 
 * @author 
 * @deprecated
 */
public interface MsgService {
	/**
	 * 保持并发送消息，主要数据来源用户界面提交的数据
	 * @param sm
	 * @param sendingNow
	 * @return SendingMsg
	 */
	SendingMsg saveAndSendMsg(SendingMsg sm, boolean sendingNow);
	
	/**
	 * 登录者收到的消息
	 * @param id
	 * @return List<Msg>
	 */
	List<Msg> findAcceptMessage(Long id, Byte readStatus);
	
	/**
	 * 登录者，或者登录者所属部门接收的消息
	 * @param id
	 * @return List<Msg>
	 */
	List<Msg> findAcceptMessage(Long id, Date time);
	
	/**
	 * 消息详细信息
	 * @param msgId
	 * @return Msg
	 */
	Msg getAcceptMessage(Long msgId);
	
	/**
	 * 修改部门消息，记录第一个阅读消息的人ID
	 * @param id
	 * @param msgId
	 * @return boolean
	 */
	boolean modfiyMessageReader(Long id, Long msgId);
	
	/***
	 * 查询用户未读内部消息记录总数
	 * @return int
	 */
	int getUnreadMessageCount();
	
	/**
	 * 查询公告,通知信息
	 * @return List<Msg>
	 */
	List<Msg> findNoticeMessage();
	
	/**
	 * 查询公告,通知信息(指定发布者)
	 * @return List<Msg>
	 */	
	List<Msg> findNoticeMessage(Long fromId);
	
	/**
	 * 查询未发送，或发送生败信息
	 * @return List<Msg>
	 */
	List<Msg> findUnsentMessage();
	
	/**
	 * 查询未发送，或发送生败信息(指定发布者)
	 * @return List<Msg>
	 */	
	List<Msg> findUnsentMessage(Long fromId);
}
