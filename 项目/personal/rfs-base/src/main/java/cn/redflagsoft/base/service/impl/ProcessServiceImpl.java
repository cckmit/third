/*
 * $Id: ProcessServiceImpl.java 5240 2011-12-21 09:27:55Z lcj $
 * ProessServiceImpl.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service.impl;

import java.util.Date;
import java.util.List;

import cn.redflagsoft.base.bean.Process;
import cn.redflagsoft.base.codegenerator.CodeGeneratorProvider;
import cn.redflagsoft.base.dao.ProcessDao;
import cn.redflagsoft.base.service.ProcessService;

/**
 * @author mwx
 *
 */
public class ProcessServiceImpl implements ProcessService{
//	/private static final byte PROCESS_CATEGORY = 4;
	private ProcessDao processDao;
	private CodeGeneratorProvider codeGeneratorProvider;
	
	public void setCodeGeneratorProvider(CodeGeneratorProvider codeGeneratorProvider) {
		this.codeGeneratorProvider = codeGeneratorProvider;
	}

	/**
	 * @param processDao the processDao to set
	 */
	public void setProcessDao(ProcessDao processDao) {
		this.processDao = processDao;
	}

	public Process createProcess(Long clerkId, Long taskSn, Long workSn,
			int type, String note) {
		Process process = new Process();
//		String code = codeGeneratorProvider.generateCode(Process.class, PROCESS_CATEGORY, type);
		process.setType(type);
//		process.setCode(code);
		process.setTaskSN(taskSn);
		process.setWorkSN(workSn);
		process.setProcessTime(new Date());
		process.setStatus((byte)0);
		process.setNote(note);
		
		String code = codeGeneratorProvider.generateCode(process);
		process.setCode(code);
		return processDao.save(process);
	}

	public void rollprocess(Long processSn, Long clerkId) {
		Process process = processDao.get(processSn);
		process.setRollBack((byte)1);
		process.setRollTime(new Date());
		process.setRollClerkID(clerkId);
		//process.rollclerkid=clerkid;
		processDao.update(process);
	
	}

	public List<Process> getProcessByTaskSNAndType(Long taskSN, int type) {
		return processDao.getProcessByTaskSNAndType(taskSN, type);
	}



}
