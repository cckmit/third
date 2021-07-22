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
import cn.redflagsoft.base.bean.Msg;
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
@ProcessType(CancelMsgWorkProcess.TYPE)
public class CancelMsgWorkProcess extends AbstractWorkProcess {
	public static final int TYPE = 6200 ;
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
			int i=0;
			int j=0;
			for(Long id:ids){
				Msg m=messageService.getMsg(id);
				if(Msg.SEND_STATUS_UNSENT==m.getSendStatus()||Msg.SEND_STATUS_FAIL==m.getSendStatus())
				{
					messageService.changeMsgSendStatus(id, Msg.SEND_STATUS_DISABLE);
					i++;
				}else{
					j++;
				}
			}
			if(i>0){
				return "成功作废("+i+")条短信。(注：只能作废未发送成功短信)";
			}
		}
		return "作废失败。(注：已发送的短信不能作废)";
	}

	
	
}
