/*
 * $Id: DatumCategoryWithDataWorkProcess.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.process.impl;

import java.util.Map;

import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.service.DatumCategoryWithDataService;

/**
 * 查询指定对象的资料类别相关信息，如果已经上传了资料，还包含资料信息。
 * 
 * @author ymq
 *
 * 有问题，应为actualProcessType
 */
@ProcessType(DatumCategoryWithDataWorkProcess.TYPE)
public class DatumCategoryWithDataWorkProcess extends AbstractWorkProcess {
	public static final int TYPE = 6005;
	private DatumCategoryWithDataService datumCategoryWithDataService;
	private int taskType;
	private int workType;
	private int processType;
	private Long matterID;
	private Long objectID;

	public void setDatumCategoryWithDataService(
			DatumCategoryWithDataService datumCategoryWithDataService) {
		this.datumCategoryWithDataService = datumCategoryWithDataService;
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

	public int getProcessType() {
		return processType;
	}

	public void setProcessType(int processType) {
		this.processType = processType;
	}

	public Long getMatterID() {
		return matterID;
	}

	public void setMatterID(Long matterID) {
		this.matterID = matterID;
	}

	public Long getObjectID() {
		return objectID;
	}

	public void setObjectID(Long objectID) {
		this.objectID = objectID;
	}

	public Object execute(Map parameters, boolean needLog) {
		return datumCategoryWithDataService.findDatumCategoryWithData(taskType,
				workType, processType, matterID, objectID);
	}

}
