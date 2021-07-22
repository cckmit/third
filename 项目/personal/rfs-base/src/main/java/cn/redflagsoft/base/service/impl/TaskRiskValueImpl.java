/*
 * $Id: TaskRiskValueImpl.java 5787 2012-05-28 06:46:39Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
