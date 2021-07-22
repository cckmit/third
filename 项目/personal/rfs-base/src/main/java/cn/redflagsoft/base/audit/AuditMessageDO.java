/*
 * $Id: AuditMessageDO.java 6335 2013-12-11 06:30:56Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.audit;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.jsonplugin.annotations.JSON;

import cn.redflagsoft.base.security.UserClerk;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class AuditMessageDO implements Serializable {

	private static final long serialVersionUID = -3564952300477908557L;
	
	private long id = -1;
    private long userId = -100;
    private String username;
    private String userName;
    private Date timestamp;
    private String node;
    private String details;
    private String description;
    private String operation;
	private String domainId;
	private String domainType;
	
	public AuditMessageDO() {
	}
	public AuditMessageDO(AuditMessage auditMessage){
		this.id = auditMessage.getId();
		UserClerk uc = auditMessage.getUser();
		if(uc != null){
			if(uc.getUser() != null){
				this.userId = uc.getUser().getUserId();
				this.username = uc.getUser().getUsername();
				this.userName = this.username;
			}
			if(uc.getClerk() != null){
				this.userId = uc.getClerk().getId();
				this.userName = uc.getClerk().getName();
			}
		}
		this.timestamp = auditMessage.getTimestamp();
		this.node = auditMessage.getNode() != null ? auditMessage.getNode().getHostAddress() : "";
		this.description = auditMessage.getDescription();
		this.details = auditMessage.getDetails();
		if(auditMessage instanceof OperationMessage){
			OperationMessage om = (OperationMessage) auditMessage;
			this.operation = om.getOperation();
			if(om.getDoamin() != null){
				this.domainId = om.getDoamin().getId();
				this.domainType = om.getDoamin().getType();
			}
		}
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@JSON(format="yyyy-MM-dd HH:mm")
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	public String getDomainType() {
		return domainType;
	}
	public void setDomainType(String domainType) {
		this.domainType = domainType;
	}
}
