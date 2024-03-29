/*
 * $Id: UserLoginApplicationListener.java 6335 2013-12-11 06:30:56Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserHolder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.event.authentication.AbstractAuthenticationEvent;
import org.springframework.security.event.authentication.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.event.authentication.AuthenticationSwitchUserEvent;
import org.springframework.security.event.authentication.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.ui.WebAuthenticationDetails;
import org.springframework.security.userdetails.UserDetails;

import cn.redflagsoft.base.audit.AuditManager;
import cn.redflagsoft.base.audit.DomainIdentifier;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.security.webapp.RFSWebAuthenticationDetails;
import cn.redflagsoft.base.service.ClerkService;

/**
 * @author Alex Lin
 *
 */
public class UserLoginApplicationListener implements ApplicationListener {
	private static final Log log = LogFactory.getLog(UserLoginApplicationListener.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	private AuditManager auditManager;
	
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof AbstractAuthenticationEvent){
			log.debug("用户事件：" + event.getClass().getName());
			
			//User user = UserHolder.getUser();
			AbstractAuthenticationEvent e = (AbstractAuthenticationEvent) event;
			if(event instanceof InteractiveAuthenticationSuccessEvent){
				//User user = (User) e.getAuthentication().getPrincipal();
				UserClerk uc = UserClerkHolder.getNullableUserClerk();
				User user = null;
				if(uc == null){
					user = UserHolder.getUser();
				}

				Object details = e.getAuthentication().getDetails();
				String loginAddress = "";
				String sessionId = "";
				String clientResolution = null;
				String clientUserAgent = null;
				if(details instanceof WebAuthenticationDetails){
					WebAuthenticationDetails wad = (WebAuthenticationDetails) details;
					loginAddress = wad.getRemoteAddress();
					sessionId = wad.getSessionId();
					if(wad instanceof RFSWebAuthenticationDetails){
						RFSWebAuthenticationDetails rd = (RFSWebAuthenticationDetails)wad;
						clientResolution = rd.getParameterMap().get(RFSWebAuthenticationDetails.LOGIN_FORM_RESOLUTION_KEY);
						clientUserAgent = rd.getHeaderMap().get("User-Agent");
					}
				}
				
//				log.info("用户登录成功\n姓名："
//						+ uc.getClerk().getName() + "(" + uc.getUser().getUsername() + ")\nIP："
//						+ loginAddress + "\n登录次数：" + uc.getUser().getLoginCount()
//						+ "\n最后登录时间：" + uc.getUser().getLastLoginTime() + "\n");
				
				
				// 记录登录日志
				InetAddress node = null;
				if (StringUtils.isNotBlank(loginAddress)) {
					try {
						node = InetAddress.getByName(loginAddress);
					} catch (UnknownHostException e1) {
						log.debug(e1.getMessage());
					}
				}
				
				if(uc != null){
					DomainIdentifier identifier = new DomainIdentifier(loginAddress, sessionId);
					String detail = String.format("用户‘%s(%s)’登录。", uc.getClerk().getName(), uc.getUser().getUsername());
					String desc;
					if(clientResolution == null){
						desc =  String.format("IP:%s，浏览器:%s，使用非表单形式登录。",loginAddress, clientUserAgent);
					}else{
						desc = String.format("IP:%s，显示器分辨率:%s，浏览器:%s。",loginAddress, clientResolution, clientUserAgent);
					}
					
					auditManager.audit(uc, node, detail, desc, identifier, "login");
					
					if(IS_DEBUG_ENABLED){
						log.debug(desc + "<SID=" + sessionId + ">");
					}
				}
				//内部用户，RFSA
				if(user != null){
					String detail = String.format("用户‘%s’登录。", user.getUsername());
					String desc;
					if(clientResolution == null){
						desc =  String.format("IP:%s，浏览器:%s，使用非表单形式登录。",loginAddress, clientUserAgent);
					}else{
						desc = String.format("IP:%s，显示器分辨率:%s，浏览器:%s。",loginAddress, clientResolution, clientUserAgent);
					}
					
					//记录日志
					log.info(detail + desc);
				}
				
			} else if(event instanceof AuthenticationSwitchUserEvent){
				AuthenticationSwitchUserEvent asue = (AuthenticationSwitchUserEvent)event;
				UserDetails user = asue.getTargetUser();
				Object details = e.getAuthentication().getDetails();
				String loginAddress = "";
				String sessionId = "";
				if(details instanceof WebAuthenticationDetails){
					WebAuthenticationDetails wad = (WebAuthenticationDetails) details;
					loginAddress = wad.getRemoteAddress();
					sessionId = wad.getSessionId();
				}
				
				// 记录登录日志
				InetAddress node = null;
				if (StringUtils.isNotBlank(loginAddress)) {
					try {
						node = InetAddress.getByName(loginAddress);
					} catch (UnknownHostException e1) {
						log.debug(e1.getMessage());
					}
				}
				
				//
				Long userId = ((User)user).getUserId();
				String username = user.getUsername();
				String name = "";
				if(AuthUtils.isInternalUser(userId)){
					name = username;
				}else{
					ClerkService clerkService = Application.getContext().get("clerkService", ClerkService.class);
					Clerk clerk = clerkService.getClerk(userId);
					name = clerk.getName();
				}
				
				DomainIdentifier identifier = new DomainIdentifier(username, name);
				String detail = String.format("管理员切换到用户‘%s(%s)’。", name, username);
				String desc = String.format("管理员切换到用户‘%s(%s)’，IP:‘%s’。", 
						name, username, loginAddress);
				auditManager.audit(UserClerkHolder.getNullableUserClerk(), node, detail, desc, identifier, "switchUser");
				
				if(IS_DEBUG_ENABLED){
					log.debug(desc + "<SID=" + sessionId + ">");
				}
				
			}else if (event instanceof AuthenticationFailureBadCredentialsEvent) {
				Object o = e.getAuthentication().getPrincipal();
				if (o instanceof String) {
					String username = (String) o;
					log.warn("用户 " + username + " 尝试登陆失败！");
				}
			}
		}
	}

	public AuditManager getAuditManager() {
		return auditManager;
	}

	public void setAuditManager(AuditManager auditManager) {
		this.auditManager = auditManager;
	}
	
}
