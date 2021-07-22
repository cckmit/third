/*
 * $Id: MatterDatumWorkProcess.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.process.impl;

import java.util.Map;

import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.service.MatterDatumService;

/**
 * 查询事项资料。
 * 
 * @author ymq
 * 有问题，应为actualProcessType
 */
@ProcessType(MatterDatumWorkProcess.TYPE)
public class MatterDatumWorkProcess extends AbstractWorkProcess {
	public static final int TYPE = 6006;
	private MatterDatumService matterDatumService;
	private int taskType;
	private int workType;
//	private int processType;
	private Long matterID;
	private int actualProcessType;
	
	public void setMatterDatumService(MatterDatumService matterDatumService) {
		this.matterDatumService = matterDatumService;
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

//	public short getProcessType() {
//		return processType;
//	}
//
//	public void setProcessType(int processType) {
//		this.processType = processType;
//	}

	public Long getMatterID() {
		return matterID;
	}

	public void setMatterID(Long matterID) {
		this.matterID = matterID;
	}

	public Object execute(Map parameters, boolean needLog) {
		return matterDatumService.findMatterDatum(taskType, workType, actualProcessType, matterID);
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
