/*
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.process.impl;

import java.util.List;
import java.util.Map;
import cn.redflagsoft.base.bean.SendingMsg;
import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.WorkProcessException;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.service.MessageService;
/**
 * 
 * 
 * @author 
 *
 */
@ProcessType(FinishConfirmSendingMsgWorkProcess.TYPE)
public class FinishConfirmSendingMsgWorkProcess extends AbstractWorkProcess {
	public static final int TYPE = 6190 ;
	private List<Long> ids;
	private MessageService messageService;
	
	
	public MessageService getMessageService() {
		return messageService;
	}



	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}



	public List<Long> getIds() {
		return ids;
	}



	public void setIds(List<Long> ids) {
		this.ids = ids;
	}



	public Object execute(Map parameters, boolean needLog) throws WorkProcessException {
		if(ids!=null){
			for(Long id:ids){
				messageService.changeSendMessageStatus(id, SendingMsg.STATUS_READY);
			}
			return "确认成功！";
		}
		return "确认失败！";
	}

	
	
}
