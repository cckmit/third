/*
 * $Id: SmsgAttachment.java 5001 2011-10-28 08:30:15Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import org.opoo.ndao.Domain;


/**
 * @author py
 *
 */
public class SmsgAttachment implements Domain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id; 				//id
	private Long smsgId;			//消息ID
	private String smsgCode;			//消息编码
	private int type = 0; 				//类型
	private Long fileId;			//文件ID 
	private String fileType;			//文件类型 
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the smsgId
	 */
	public Long getSmsgId() {
		return smsgId;
	}
	/**
	 * @param smsgId the smsgId to set
	 */
	public void setSmsgId(Long smsgId) {
		this.smsgId = smsgId;
	}
	/**
	 * @return the smsgCode
	 */
	public String getSmsgCode() {
		return smsgCode;
	}
	/**
	 * @param smsgCode the smsgCode to set
	 */
	public void setSmsgCode(String smsgCode) {
		this.smsgCode = smsgCode;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the fileId
	 */
	public Long getFileId() {
		return fileId;
	}
	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}
	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}