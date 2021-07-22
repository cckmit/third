/*
 * $Id: SimpleWorkProcessManager.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.process;

import java.util.HashMap;
import java.util.Map;

import org.opoo.util.Assert;

/**
 * @author Alex Lin
 *
 */
public class SimpleWorkProcessManager implements WorkProcessManager {
	private Map<Integer, WorkProcess> workProcesses = new HashMap<Integer, WorkProcess>();

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.process.WorkProcessManager#getProcess(short)
	 */
	public WorkProcess getProcess(int processType) {
		WorkProcess wp = workProcesses.get(processType);
		Assert.notNull(wp, "找不到注册的WorkProcess: processType = " + processType);
		return wp;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.process.WorkProcessManager#process(short, java.util.Map, boolean)
	 */
	@SuppressWarnings("unchecked")
	public Object execute(int processType, Map parameters, boolean needLog) {
		return getProcess(processType).execute(parameters, needLog);
	}

	
	
	/**
	 * @return the workProcesses
	 */
	public Map<Integer, WorkProcess> getWorkProcesses() {
		return workProcesses;
	}

	/**
	 * @param type
	 * @param workProcesses the workProcesses to set
	 */
	public void addWorkProcess(Integer type, WorkProcess workProcess) {
		if(workProcesses.containsKey(type)){
			throw new IllegalArgumentException("WorkProcess定义重复，processType is [" + type + "], processes are [" + workProcess 
					+ ", " + workProcesses.get(type) + "]");
		}
		this.workProcesses.put(type, workProcess);
	}

	/**
	 * @param workProcesses the workProcesses to set
	 */
	public void setWorkProcesses(Map<Integer, WorkProcess> workProcesses) {
		this.workProcesses = workProcesses;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.process.WorkProcessManager#getProcess(java.lang.String)
	 */
	public WorkProcess getProcess(String name) {
		return getProcess(Integer.valueOf(name));
	}
}
