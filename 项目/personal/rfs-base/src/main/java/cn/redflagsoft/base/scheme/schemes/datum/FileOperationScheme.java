/*
 * $Id: FileOperationScheme.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.datum;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.bean.core.Attachment;
import org.opoo.apps.lifecycle.Application;

import cn.redflagsoft.base.audit.AuditManager;
import cn.redflagsoft.base.audit.DomainIdentifier;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.security.UserClerk;
import cn.redflagsoft.base.security.UserClerkHolder;

/**
 * 文件操作日志。
 * 
 * <p>记录每个操作用户对每个文件的阅读，下载等操作。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class FileOperationScheme extends AbstractScheme {

	private static final Log log = LogFactory.getLog(FileOperationScheme.class);
	private AuditManager auditManager;
	
	/**
	 * 文件ID
	 */
	private long fileId;

	/***
	 * 标示 该'操作'的类型 （1:查看 2:下载）
	 */
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public AuditManager getAuditManager() {
		return auditManager;
	}

	public void setAuditManager(AuditManager auditManager) {
		this.auditManager = auditManager;
	}

	public long getFileId() {
		return fileId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	public String doFileOperationLog() {
		if(fileId <= 0){
			log.debug("文件id不合法，无法记录文件操作日志：" + fileId);
			return "bad filed id: " + fileId;
		}
		
		// 获得当前登录系统用户信息
		UserClerk userClerk = UserClerkHolder.getNullableUserClerk();
		
		if(userClerk == null){
			log.debug("没有登录用户，无法记录文件操作日志。");
			return "没有登录用户";
		}
		
		// 根据fileId获取文件.
		Attachment attachment = Application.getContext().getAttachmentManager().getAttachment(getFileId());

		if (attachment != null) {
			DomainIdentifier domain = new DomainIdentifier(attachment);
			String operation = "未知操作";
			if (getType() == 1) {
				operation = "查看";
			} else {
				operation = "下载";
			}
			String details = operation + " 了 " + attachment.getFileName()
					+ " 文件！";
			String description = details;
			try {
				auditManager.audit(userClerk, InetAddress.getLocalHost(),
						details, description, domain, operation);
			} catch (UnknownHostException e) {
				log.error("Host name unresolvable!");
			}
		}
		return "";
	}
}
