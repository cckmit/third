/*
 * $Id: SystemMessageScheme.java 4400 2011-05-17 06:36:04Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.sysmsg;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opoo.apps.AppsGlobals;
import org.opoo.apps.proxy.GenericListProxy;
import org.opoo.apps.proxy.GenericProxyFactory;
import org.opoo.apps.security.User;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.WebAuthenticationDetails;//WebAuthenticationDetails
import org.springframework.util.Assert;

import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.service.SystemMessage;
import cn.redflagsoft.base.service.SystemMessageManager;

/**
 * 系统消息。
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class SystemMessageScheme extends AbstractScheme{
	private SystemMessageManager systemMessageManager;
	private Long msgId;
	/**
	 * @return the systemMessageManager
	 */
	public SystemMessageManager getSystemMessageManager() {
		return systemMessageManager;
	}

	/**
	 * @param systemMessageManager the systemMessageManager to set
	 */
	public void setSystemMessageManager(SystemMessageManager systemMessageManager) {
		this.systemMessageManager = systemMessageManager;
	}

	/**
	 * @return the msgId
	 */
	public Long getMsgId() {
		return msgId;
	}

	/**
	 * @param msgId the msgId to set
	 */
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public List<Map<String, Object>> viewLoad(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			Object details = auth.getDetails();
			Object principal = auth.getPrincipal();
			if(details instanceof WebAuthenticationDetails && principal instanceof User){
				String sessionId = ((WebAuthenticationDetails) details).getSessionId();
				User user = (User) principal;
				List<SystemMessage> messages = systemMessageManager.loadMessages(sessionId, user);
				return new GenericListProxy<SystemMessage, Map<String,Object>>(messages, new GenericProxyFactory<SystemMessage, Map<String,Object>>(){
					public Map<String, Object> createProxy(SystemMessage m) {
						Map<String, Object> map = new HashMap<String,Object>();
						map.put("msgId", m.getMsgId());
						map.put("subject", m.getSubject());
						map.put("content", m.getContent());
						map.put("creationTime", AppsGlobals.formatDateTime(m.getCreationTime()));
						if(m.getExpirationTime() != null){
							map.put("expirationTime", AppsGlobals.formatDateTime(m.getExpirationTime()));
						}
						return map;
					}});
			}
		}
		return Collections.emptyList();
	}
	
	public Object doConfirm(){
		Assert.notNull(msgId, "确认的消息ID不能为空");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			Object details = auth.getDetails();
			Object principal = auth.getPrincipal();
			if(details instanceof WebAuthenticationDetails && principal instanceof User){
				String sessionId = ((WebAuthenticationDetails) details).getSessionId();
				User user = (User) principal;
				SystemMessage message = systemMessageManager.getMessage(msgId);
				if(message != null){
					message.addConfirmedUser(sessionId, user);
					return "消息确认";
				}else{
					return "消息不存在";
				}
			}
		}
		return "未登录用户或者认证类型未知";
	}
}
