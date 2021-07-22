/*
 * $Id: ProcessService.java 4615 2011-08-21 07:10:37Z lcj $
 * ProcessService.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.Process;

/**
 * @author mwx
 *
 */
public interface ProcessService {
	/**
	 * 创建process
	 * 
	 * @param clerkId
	 * @param taskSn
	 * @param workSn
	 * @param type
	 * @param note
	 * @return
	 */
	Process createProcess(Long clerkId,Long taskSn,Long workSn,int type,String note);
	
	/**
	 *  设定rollback，rollClerkID,rollTime属性
	 * 
	 * @param processSn
	 * @param clerkId
	 */
	void rollprocess (Long processSn,Long clerkId);
	
	List<Process> getProcessByTaskSNAndType(Long taskSN,int type);
}
