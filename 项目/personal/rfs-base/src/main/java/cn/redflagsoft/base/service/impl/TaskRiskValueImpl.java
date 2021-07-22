/*
 * $Id: TaskRiskValueImpl.java 5787 2012-05-28 06:46:39Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import cn.redflagsoft.base.bean.BizSummaryProvider;
import cn.redflagsoft.base.bean.Task;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
class TaskRiskValueImpl extends RiskValueImpl implements BizSummaryProvider {
	private String bizSummary;
	/**
	 * @param value
	 * @param valueGeneratedTime
	 * @param source
	 */
	public TaskRiskValueImpl(BigDecimal value, Date valueGeneratedTime,	String bizSummary, Task source) {
		super(value, valueGeneratedTime, source);
		this.bizSummary = bizSummary;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.BizSummaryProvider#getBizSummary()
	 */
	public String getBizSummary() {
		return bizSummary;
	}
}
