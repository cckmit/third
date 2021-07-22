/*
 * $Id: PhraseWorkProcess.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.process.impl;

import java.util.Map;

import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.service.PhraseService;

/**
 * 
 * @author ymq
 *
 */
@ProcessType(PhraseWorkProcess.TYPE)
public class PhraseWorkProcess extends AbstractWorkProcess {
	public static final int TYPE = 6001;
	private PhraseService phraseService;
	private int taskType;
	private int workType;
	private int actualProcessType;
	private Long clerkID;
	
	public void setPhraseService(PhraseService phraseService) {
		this.phraseService = phraseService;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public int getWorkType() {
		return workType;
	}

	public void setWorkType(int workType) {
		this.workType = workType;
	}



	public Long getClerkID() {
		return clerkID;
	}

	public void setClerkID(Long clerkID) {
		this.clerkID = clerkID;
	}
	
	public Object execute(Map parameters, boolean needLog) {
		return phraseService.findPhraseByTypes(taskType, workType, actualProcessType, clerkID);
	}

	/**
	 * @return the actualProcessType
	 */
	public int getActualProcessType() {
		return actualProcessType;
	}

	/**
	 * @param actualProcessType the actualProcessType to set
	 */
	public void setActualProcessType(int actualProcessType) {
		this.actualProcessType = actualProcessType;
	}
}
