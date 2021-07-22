/*
 * $Id: BusinessExceptionManagerImpl.java 6299 2013-08-13 01:56:24Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import cn.redflagsoft.base.BusinessException;
import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.bean.EventMsg;
import cn.redflagsoft.base.service.BusinessExceptionManager;
import cn.redflagsoft.base.service.EventMsgService;
import cn.redflagsoft.base.service.SmsgTemplateService;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class BusinessExceptionManagerImpl implements BusinessExceptionManager {
	public static final int BUSINESS_EXCEPTION_OBJECT_TYPE = ObjectTypes.BUSINESS_EXCEPTION;
	public static final long BUSINESS_EXCEPTION_EVENT_TYPE = 0;
	
	private EventMsgService eventMsgService;
	private SmsgTemplateService msgTemplateService;

	/**
	 * @return the msgTemplateService
	 */
	public SmsgTemplateService getMsgTemplateService() {
		return msgTemplateService;
	}

	/**
	 * @param msgTemplateService the msgTemplateService to set
	 */
	public void setMsgTemplateService(SmsgTemplateService msgTemplateService) {
		this.msgTemplateService = msgTemplateService;
	}

	/**
	 * @return the eventMsgService
	 */
	public EventMsgService getEventMsgService() {
		return eventMsgService;
	}

	/**
	 * @param eventMsgService the eventMsgService to set
	 */
	public void setEventMsgService(EventMsgService eventMsgService) {
		this.eventMsgService = eventMsgService;
	}
	
	public EventMsg getEventMsg(long msgId){
		List<EventMsg> list = eventMsgService.findEventMsgCfg(BUSINESS_EXCEPTION_OBJECT_TYPE, msgId, BUSINESS_EXCEPTION_EVENT_TYPE);
		if(list != null && !list.isEmpty()){
			return list.iterator().next();
		}
		return null;
	}
	
	public String getMsgTemplate(long msgId){
		EventMsg msg = getEventMsg(msgId);
		if(msg != null){
			return msg.getTemplet();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.BusinessExceptionManager#throwBusinessException(long, java.lang.String)
	 */
	public void throwBusinessException(long msgId, String defaultMessage) throws BusinessException {
		String template = getMsgTemplate(msgId);
		if(template == null){
			template = defaultMessage;
		}
//		Assert.notBlank(template, "业务异常消息不能为空。");
		throw new BusinessException(template);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.BusinessExceptionManager#throwBusinessException(long, java.lang.String, java.lang.Throwable)
	 */
	public void throwBusinessException(long msgId, String defaultMessage, Throwable e) throws BusinessException {
		String template = getMsgTemplate(msgId);
		if(template == null){
			template = defaultMessage;
		}
		if(template == null && e != null){
			template = e.getMessage();
		}
		if(e != null){
			throw new BusinessException(template, e);  
		}else{
			throw new BusinessException(template);
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.BusinessExceptionManager#throwBusinessException(long, java.lang.Throwable)
	 */
	public void throwBusinessException(long msgId, Throwable e)
			throws BusinessException {
		String template = getMsgTemplate(msgId);
		if(template == null && e != null){
			template = e.getMessage();
		}
		if(e != null){
			throw new BusinessException(template, e);  
		}else{
			throw new BusinessException(template);
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.BusinessExceptionManager#throwBusinessException(long, java.lang.String, java.lang.Throwable, java.lang.Object[])
	 */
	public void throwBusinessException(long msgId, String defaultMessage,
			Throwable e, Object... args) throws BusinessException {
		String template = getMsgTemplate(msgId);
		if(template == null){
			template = defaultMessage;
		}
		if(template == null && e != null){
			template = e.getMessage();
		}
		if(args != null && args.length > 0){
			//template = String.format(template, args);
			//MessageFormat 功能更强大
			template = MessageFormat.format(template, args);
		}
		if(e != null){
			throw new BusinessException(template, e);  
		}else{
			throw new BusinessException(template);
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.BusinessExceptionManager#throwBusinessException(long, java.lang.String, java.lang.Throwable, java.util.Map)
	 */
	public void throwBusinessException(long msgId, String defaultMessage,
			Throwable e, Map<String, Object> context) throws BusinessException {
		String template = getMsgTemplate(msgId);
		if(template == null){
			template = defaultMessage;
		}
		if(template == null && e != null){
			template = e.getMessage();
		}
		if(context != null && !context.isEmpty()){
			template = msgTemplateService.processTemplate(template, context);
		}
		if(e != null){
			throw new BusinessException(template, e);  
		}else{
			throw new BusinessException(template);
		}
	}
	
	
	
	public static void main(String[] args){
		String format = MessageFormat.format("This is [{0}] sakdjfd f[{2}] sad s d[{2}]", "a", "b", "c");
		System.out.println(format);
		String message = "单位【{0}】已经被引用，不能删除。参考代码：{2}.{3}";
		String format2 = MessageFormat.format(message, "发改局", "" + 1000L, "Project", "dutyEntityID");
		System.out.println(format2);
	}
}
