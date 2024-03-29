/*
 * $Id: PostSendingMsgWorkProcess.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.process.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.opoo.apps.Model;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.SendingMsg;
import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.WorkProcessException;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.MessageService;

/**
 * 通过界面保存或者发送消息的逻辑处理。
 * 
 * @author Alex Lin
 *
 */
@ProcessType(PostSendingMsgWorkProcess.TYPE)
public class PostSendingMsgWorkProcess extends AbstractWorkProcess {
	public static final int TYPE = 6017;
	
	private SendingMsg sendingMsg;
	private List<Long> fileIds;
	private String index;//标识 值为:空时 修改全部。 值为：part时只修改title和content
	


	public String getIndex() {
		return index;
	}



	public void setIndex(String index) {
		this.index = index;
	}



	private MessageService messageService;
	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.process.WorkProcess#execute(java.util.Map, boolean)
	 */
	public Object execute(Map parameters, boolean needLog) throws WorkProcessException {
		Clerk clerk = UserClerkHolder.getClerk();
		
		if(sendingMsg == null){
			return new Model(false, "操作失败，提交数据为空。", "");
		}
		
		sendingMsg.setFromId(clerk.getId());
		sendingMsg.setFromName(clerk.getName());
		sendingMsg.setAttached(fileIds != null && fileIds.size() > 0);
		
		if(sendingMsg.getId() != null){//以前的草稿
			SendingMsg old = messageService.getSendingMsg(sendingMsg.getId());
//			if(old.getStatus() == SendingMsg.STATUS_READY 
//					|| old.getStatus() == SendingMsg.STATUS_SENT){
//				return new Model(false, "消息已发出，不能修改。", "");
//			}
			
			//清理附件
			if(old.isAttached()){
				messageService.removeAttachments(old.getId());
			}
			
			old.setTitle(sendingMsg.getTitle());
			old.setContent(sendingMsg.getContent());
			old.setModificationTime(new Date());
			if(index==null){
				old.setAttached(sendingMsg.isAttached());
				old.setEmailTo(sendingMsg.getEmailTo());
				old.setFromId(sendingMsg.getFromId());
				old.setFromName(sendingMsg.getFromName());
				if(sendingMsg.getReceivers()!=null){
					old.setReceivers(sendingMsg.getReceivers());
				}
				old.setSmsTo(sendingMsg.getSmsTo());
				old.setStatus(sendingMsg.getStatus());
				old.setSupportsEmail(sendingMsg.isSupportsEmail());
				old.setSupportsSms(sendingMsg.isSupportsSms());
				old.setType(sendingMsg.getType());
				//old.setCreationTime(sendingMsg.getCreationTime());
				old.setPublishTime(sendingMsg.getPublishTime());
				old.setExpirationTime(sendingMsg.getExpirationTime());
			}
			messageService.updateSendingMsg(old);
		
		}else{//新消息
			sendingMsg.setCreationTime(new Date());
			messageService.saveSendingMsg(sendingMsg);
		}
		
		//保存附件
		if(sendingMsg.isAttached()){
			messageService.saveAttachments(sendingMsg.getId(), fileIds);
		}
		
		if(sendingMsg.getStatus() == SendingMsg.STATUS_READY){
			return "操作成功！";
		}else{
			return "保存成功！";
		}
	}

	
	
	/**
	 * @return the sendingMsg
	 */
	public SendingMsg getSendingMsg() {
		return sendingMsg;
	}

	/**
	 * @param sendingMsg the sendingMsg to set
	 */
	public void setSendingMsg(SendingMsg sendingMsg) {
		this.sendingMsg = sendingMsg;
	}



	/**
	 * @return the fileIds
	 */
	public List<Long> getFileIds() {
		return fileIds;
	}



	/**
	 * @param fileIds the fileIds to set
	 */
	public void setFileIds(List<Long> fileIds) {
		this.fileIds = fileIds;
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
