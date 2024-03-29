/*
 * $Id: TaskVO.java 5878 2012-06-18 05:56:39Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.vo;

import cn.redflagsoft.base.bean.Task;

/**
 * @author Alex Lin
 *
 */
public class TaskVO extends Task {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4045238505902128427L;
	
	
	private Long projectId;
	/**
	 * @return the b_sn
	 */
	public Long getB_sn() {
		return getSn();
	}
	/**
	 * @param b_sn the b_sn to set
	 */
	public void setB_sn(Long b_sn) {
		setSn(b_sn);
	}
	/**
	 * @return the a_id
	 */
	public Long getA_id() {
		return projectId;
	}
	/**
	 * @param a_id the a_id to set
	 */
	public void setA_id(Long a_id) {
		this.setProjectId(a_id);
	}
	/**
	 * @return the projectId
	 */
	public Long getProjectId() {
		return projectId;
	}
	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public int getSurplusTime(){
		return super.getSurplus();
		//return getTimeLimit()-getTimeUsed();
	}
}
