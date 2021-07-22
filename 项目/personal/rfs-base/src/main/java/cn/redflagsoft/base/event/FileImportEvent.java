/*
 * $Id: FileImportEvent.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.event;

import org.opoo.apps.Model;
import org.opoo.apps.file.handler.FileInfo;
import org.springframework.context.ApplicationEvent;

/**
 * @author Alex Lin
 *
 */
public class FileImportEvent extends ApplicationEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FileInfo fileInfo;
	/**
	 * @param source
	 */
	public FileImportEvent(Model source, FileInfo fileInfo) {
		super(source);
		this.fileInfo = fileInfo;
	}


	/**
	 * @return the fileInfo
	 */
	public FileInfo getFileInfo() {
		return fileInfo;
	}
	
	public Model getModel(){
		return (Model) getSource();
	}

}
