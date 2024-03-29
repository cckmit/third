/*
 * $Id: SchemeEventListenerForAuditLog.java 6351 2014-02-28 07:20:21Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.listener;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.event.v2.EventListener;

import cn.redflagsoft.base.audit.AuditManager;
import cn.redflagsoft.base.audit.DomainIdentifier;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.scheme.WorkScheme;
import cn.redflagsoft.base.scheme.event.SchemeEvent;
import cn.redflagsoft.base.security.UserClerk;
import cn.redflagsoft.base.security.UserClerkHolder;

/**
 * 该监听器用于监听 Scheme 执行事件，并记录执行日志到 AuditLog 中。
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class SchemeEventListenerForAuditLog implements EventListener<SchemeEvent> {
	private static final Log log = LogFactory.getLog(SchemeEventListenerForAuditLog.class);
	private Executor executor;
	private AuditManager auditManager;
	
	/**
	 * @param executor the executor to set
	 */
	public void setExecutor(Executor executor) {
		this.executor = executor;
	}
	
	/**
	 * @param auditManager the auditManager to set
	 */
	public void setAuditManager(AuditManager auditManager) {
		this.auditManager = auditManager;
	}

	/**
	 * 
	 */
	public SchemeEventListenerForAuditLog() {
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.event.v2.EventListener#handle(org.opoo.apps.event.v2.Event)
	 */
	public void handle(SchemeEvent e) {
		if(SchemeEvent.Type.EXECUTED == e.getType()){
			handleSchemeExecuted(e);
		}
	}

	/**
	 * @param source
	 */
	private void handleSchemeExecuted(final SchemeEvent e) {
		final UserClerk uc = UserClerkHolder.getNullableUserClerk();
		if(uc == null){
			log.debug("无登录用户或者当前登录用户为系统用户，不记录Scheme操作日志："
					+ e.getSchemeName() + "!" + e.getMethodName());
			return;
		}
		
		executor.execute(new Runnable() {
			public void run() {
				handleSchemeExecutedInternal(e, uc);
			}
		});
	}
	
	private void handleSchemeExecutedInternal(SchemeEvent e, UserClerk uc) {
		String name = e.getSchemeName();
		String method = e.getMethodName();
		String displayName = e.getSource().getDisplayName();
		//String beanName = e.getSource().getBeanName();
		Method m = e.getMethod();
		//Object result = e.getResult();
		
		if(!m.getName().startsWith("do")){
			log.debug("不记录当前scheme的操作日志：" + m);
			return;
		}
		
		// 记录登录日志
		InetAddress node = null;
		try {
			node = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			log.debug(e1.getMessage());
		}
		
		String info = name + (method != null ? "!" + method : "");
		if(displayName == null){
			displayName = info;
		}else{
			displayName = displayName + "(" + info + ")";
		}
		
		String objectInfo = "系统";
		DomainIdentifier identifier = null;
		if(e.getSource() instanceof WorkScheme){
			WorkScheme ws = (WorkScheme) e.getSource();
			RFSObject object = ws.getObject();
			int objectType = object.getObjectType();
			Long objectId = object.getId();
			String objectName = object.getName();
			
			if(objectName == null){
				objectName = "";
			}
			
			objectInfo = objectName + "(" + objectType + ":" + objectId + ")"; 
			
			identifier = new DomainIdentifier(object);
		}else{
			identifier = new DomainIdentifier(name, method);
		}
		
		String detail = String.format(uc.getClerk().getName() + " 执行 %s。", displayName);
		String description = String.format("用户 %s(%s:%s) 对 %s 执行了 %s。", 
				uc.getClerk().getName(), uc.getUser().getUsername(), uc.getUser().getUserId(), 
				objectInfo,
				displayName);
		
		auditManager.audit(uc, node, detail, description, identifier, "scheme");
	}
}
