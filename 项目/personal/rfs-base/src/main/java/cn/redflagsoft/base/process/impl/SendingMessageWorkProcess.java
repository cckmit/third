package cn.redflagsoft.base.process.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.opoo.apps.Model;
import org.opoo.apps.bean.core.Attachment;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.SendingMsg;
import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.WorkProcessException;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.MessageService;
import cn.redflagsoft.base.vo.SendingMsgVO;


/**
 * 查询我发送的消息相关信息。
 * 
 *  
 * @author Alex Lin
 *
 */
@ProcessType(SendingMessageWorkProcess.TYPE)
public class SendingMessageWorkProcess extends AbstractWorkProcess {
	public static final int TYPE = 6016;
	private MessageService messageService;
	private ClerkService clerkService;
	private Long fromId;
	private Long sendingMsgId;
	private Byte status;

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public Long getSendingMsgId() {
		return sendingMsgId;
	}

	public void setSendingMsgId(Long sendingMsgId) {
		this.sendingMsgId = sendingMsgId;
	}

	public ClerkService getClerkService() {
		return clerkService;
	}

	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}

	public Object execute(Map parameters, boolean needLog) throws WorkProcessException {
		if (sendingMsgId != null) {
			SendingMsg sm =  messageService.getSendingMsg(sendingMsgId);
			String receiver=sm.getReceivers();
			List<Clerk> receiverList=new ArrayList<Clerk>();
			 while(true){
				  int l=receiver.length();
				  int begin=receiver.indexOf(":")+1;
				  int end=receiver.indexOf(";");
				  if(end==-1){
					  end=l;
				  }
				  if(!"".equals(receiver.substring(begin,end))){
					  Long clerkID=Long.valueOf(receiver.substring(begin,end));
					  if(clerkID!=null){
						  Clerk c=clerkService.getClerk(clerkID);
						  receiverList.add(c);
					  }
				  }
				  if(end==l){
					  break;
				  }else{
					  receiver=receiver.substring(end+1, l);
					  }
			  }
			SendingMsgVO smVo=new SendingMsgVO(sm,receiverList);
			Model model = new Model();
			model.setData(smVo);
			if(sm.isAttached()){
				List<Attachment> attachments = messageService.findAttachments(sendingMsgId);
				model.setRows(attachments);
			}
			return model;
			
		} else{
			Clerk clerk = UserClerkHolder.getClerk();
			if(status == null){
				return messageService.findSendingMsgs(clerk.getId());
			}else{
				return messageService.findSendingMsgs(clerk.getId(), status);
			}
		}
		

//		if (fromId != null) {
//			return sendingMsgService.findSendMessage(fromId);
//		} else else {
//			return null;
//		}
	}

	/**
	 * @return the messageService
	 */
	public MessageService getMessageService() {
		return messageService;
	}

	/**
	 * @param messageService the messageService to set
	 */
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
}
