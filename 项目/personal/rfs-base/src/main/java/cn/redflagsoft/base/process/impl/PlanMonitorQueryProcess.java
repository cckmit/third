/*
 * $Id: PlanMonitorQueryProcess.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.process.impl;

import java.util.Map;

import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.service.PlanMonitorService;

/**
 * 
 * @author Alex Lin
 *
 */
@ProcessType(PlanMonitorQueryProcess.TYPE)
public class PlanMonitorQueryProcess extends AbstractWorkProcess {
	public static final int TYPE = 6007;
	private PlanMonitorService planMonitorService;
	private int objectType;
	private Byte valueType;

	public void setPlanMonitorService(PlanMonitorService planMonitorService) {
		this.planMonitorService = planMonitorService;
	}

	public int getObjectType() {
		return objectType;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	public Object execute(Map parameters, boolean needLog) {
		return planMonitorService.getPlanMonitorResult(objectType, valueType);
	}

	/**
	 * @return the valueType
	 */
	public Byte getValueType() {
		return valueType;
	}

	/**
	 * @param valueType the valueType to set
	 */
	public void setValueType(Byte valueType) {
		this.valueType = valueType;
	}
}
