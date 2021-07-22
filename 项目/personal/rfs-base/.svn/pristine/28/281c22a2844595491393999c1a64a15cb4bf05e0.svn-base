package cn.redflagsoft.base.process.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.opoo.apps.Model;
import org.opoo.apps.bean.core.Attachment;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Msg;
import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.WorkProcessException;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.MessageService;

/**
 * 查询我收到的消息相关信息。
 * <pre>
 * 1.	提供参数msgId，表示当前用户阅读指定消息，返回指定的消息并更新消息阅读状态。
 *      返回值{suceesss:true, data:Msg, rows:消息附件列表}
 * 2.	提供flag、readStatus参数，且flag=“org”，则表示查询指定状态的部门消息，
 *      返回值{success:true, rows:消息列表}
 * 3.	提供flag、readStatus、includeEntiryMsg参数，且flag=“clerk”，则表示查询个人消息，
 *      includeEntityMsg指定查询个人消息时是否包含个人所在部门的消息。
 *      返回值{success:true, rows:消息列表}
 * 
 * </pre>
 * 
 * @author Alex Lin
 *
 */
@ProcessType(AcceptMessageProcess.TYPE)
public class AcceptMessageProcess extends AbstractWorkProcess {
	public static final int TYPE = 6015;
	public static final String FLAG_CLERK = "clerk";
	public static final String FLAG_ORG = "org";
	
	
	private MessageService messageService;
	private Long toId;	//接收人
	private Byte readStatus;	//已读状态
	private Date time;	//发送时间
	private Long msgId;	//消息编号
	private String flag = FLAG_CLERK;	//标志,默认clerk
	private boolean includeEntityMsg = true;//是否包含部门信息，默认true


	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public Long getToId() {
		return toId;
	}

	public void setToId(Long toId) {
		this.toId = toId;
	}

	public Byte getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(Byte readStatus) {
		this.readStatus = readStatus;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public boolean isIncludeEntityMsg() {
		return includeEntityMsg;
	}

	public void setIncludeEntityMsg(boolean includeEntityMsg) {
		this.includeEntityMsg = includeEntityMsg;
	}

	@SuppressWarnings("unchecked")
	public Object execute(Map parameters, boolean needLog) throws WorkProcessException {
		Clerk clerk = UserClerkHolder.getClerk();
		if(msgId != null){
			Msg msg = messageService.readMsg(msgId, clerk.getId());
			Model model = new Model();
			model.setData(msg);
			if(msg.isAttached()){//如果包含附件
				List<Attachment> attachments = messageService.findAttachments(msg.getSendingMsgId());
				model.setRows(attachments);
			}
			return model;
		}
		
		
		
		if (FLAG_ORG.equals(flag)) {
			Long entityId = clerk.getEntityID();
//			if (msgId != null) {
//				//return messageService.readMsg(entityId, msgId);
//			} else 
			if (readStatus != null) {
				return messageService.findEntityMsgs(entityId, readStatus);
			} else {
				return messageService.findEntityMsgs(entityId);
			}
		} else if (FLAG_CLERK.equals(flag)) {
			Long clerkId = clerk.getId();
//			if (msgId != null) {
//				//return messageService.readMsg(clerkId, msgId);
//			} else 
			if (readStatus != null) {
				return messageService.findPersonalMsgs(clerkId, includeEntityMsg, readStatus);
			} else {
				return messageService.findPersonalMsgs(clerkId, includeEntityMsg);
			}
		}
		return null;
		
		/*if (toId != null && time != null) {
			return msgService.findAcceptMessage(toId, time);
		} else if (toId != null && msgId != null) {
			return msgService.modfiyMessageReader(toId, msgId);
		} else if ((toId != null && readStatus != null) || toId != null) {
			return msgService.findAcceptMessage(toId, readStatus);
		} else if (msgId != null) {
			return msgService.getAcceptMessage(msgId);
		} else {
			return msgService.getUnreadMessageCount();
		}*/
	}
}
