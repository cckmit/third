/*
 * $Id: PlanMonitorDetailWorkProcess.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.process.impl;

import java.util.Map;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.PlanMonitorDetailService;

/**
 * 
 * @author ymq
 *
 */
@ProcessType(PlanMonitorDetailWorkProcess.TYPE)
public class PlanMonitorDetailWorkProcess extends AbstractWorkProcess {
	public static final int TYPE = 6008;
	private PlanMonitorDetailService planMonitorDetailService;
	
	private Integer objectType;
	private Short lifeStage;
	private Byte grade;
	private Long manager;
	private Byte managerType;
	private Byte valueType;
	private Short year;
	private int key;
	private int eps;


	public int getEps() {
		return eps;
	}

	public void setEps(int eps) {
		this.eps = eps;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Integer getObjectType() {
		return objectType;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

	public Short getLifeStage() {
		return lifeStage;
	}

	public void setLifeStage(Short lifeStage) {
		this.lifeStage = lifeStage;
	}

	public Byte getGrade() {
		return grade;
	}

	public void setGrade(Byte grade) {
		this.grade = grade;
	}

	public Long getManager() {
		return manager;
	}

	public void setManager(Long manager) {
		this.manager = manager;
	}
	


	public Byte getManagerType() {
		return managerType;
	}

	public void setManagerType(Byte managerType) {
		this.managerType = managerType;
	}	

	@SuppressWarnings("unchecked")
	public Object execute(Map parameters, boolean needLog) {
		if(eps==1){
			Clerk c=UserClerkHolder.getClerk();
			manager=c.getEntityID();
			managerType=1;
		}
		return getPlanMonitorDetailService().getPlanMonitorDetail(objectType, grade, lifeStage, managerType, manager, valueType, year,key);
	}

	/**
	 * @return the planMonitorDetailService
	 */
	public PlanMonitorDetailService getPlanMonitorDetailService() {
		return planMonitorDetailService;
	}

	/**
	 * @param planMonitorDetailService the planMonitorDetailService to set
	 */
	public void setPlanMonitorDetailService(
			PlanMonitorDetailService planMonitorDetailService) {
		this.planMonitorDetailService = planMonitorDetailService;
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

	/**
	 * @return the year
	 */
	public Short getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(Short year) {
		this.year = year;
	}
}
