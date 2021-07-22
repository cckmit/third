/*
 * $Id: DatumAttachment.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.bean.core.Attachment;

/**
 * 包含文件信息的资料对象。
 * datum.content == attachment.id
 */
public class DatumAttachment extends Datum {
	private static final org.apache.commons.logging.Log log = LogFactory.getLog(DatumAttachment.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6151355007937692445L;
	//private Datum datum = new Datum();
	private Attachment attachment = new Attachment();;
	public DatumAttachment(Datum datum, Attachment attachment){
		//if(datum != null) this.datum = datum;
		try {
			if(datum != null){
				PropertyUtils.copyProperties(this, datum);
			}
		} catch (Exception e) {
			log.error(e);
		}
		if(attachment != null) this.attachment = attachment;
	}

	
	public String getFileName(){
		return attachment.getFileName();
	}
	
	
	public void setFileName(String fileName){
		attachment.setFileName(fileName);
	}
	
	
	public String getFileType(){
		return attachment.getFileType();
	}
	
	public void setFileType(String fileType){
		attachment.setFileType(fileType);
	}
	
	public String getContentType(){
		return attachment.getContentType();
	}
	
	public void setContentType(String contentType){
		attachment.setContentType(contentType);
	}
	
	public String getProtectedFormat(){
		return attachment.getProtectedFormat();
	}
	
	public String getReadableFormat(){
		return attachment.getReadableFormat();
	}
}
