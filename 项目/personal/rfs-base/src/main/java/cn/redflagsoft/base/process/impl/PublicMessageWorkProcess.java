package cn.redflagsoft.base.process.impl;

import java.util.Map;

import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.WorkProcessException;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.service.MessageService;


/**
 * 查询全部公告通知。
 * 
 * @author Alex Lin
 *
 */
@ProcessType(PublicMessageWorkProcess.TYPE)
public class PublicMessageWorkProcess extends AbstractWorkProcess {
	public static final int TYPE = 6018;
	private MessageService messageService;
	private Long fromId;
	
	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}


	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public Object execute(Map parameters, boolean needLog) throws WorkProcessException {
		/*if (fromId != null) {
			return msgService.findNoticeMessage(fromId);
		} else {
			return msgService.findNoticeMessage();
		}*/
		
		return messageService.findPublicMsgs();
	}
}
