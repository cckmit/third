/*
 * $Id: RiskValueImpl.java 5206 2011-12-13 08:10:24Z lcj $
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

import cn.redflagsoft.base.bean.RiskValue;

/**
 * @author Alex Lin(lcql@msn.com)
 * 
 */
class RiskValueImpl implements RiskValue {
	private BigDecimal value;
	private Date valueGeneratedTime;
	private Object source;

	/**
	 * @param value
	 * @param valueGeneratedTime
	 * @param source
	 */
	public RiskValueImpl(BigDecimal value, Date valueGeneratedTime,
			Object source) {
		super();
		this.value = value;
		this.valueGeneratedTime = valueGeneratedTime;
		this.source = source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.redflagsoft.base.bean.RiskValue#getValue()
	 */
	public BigDecimal getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.redflagsoft.base.bean.RiskValue#getValueGeneratedTime()
	 */
	public Date getValueGeneratedTime() {
		return valueGeneratedTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.redflagsoft.base.bean.RiskValue#getSource()
	 */
	public Object getSource() {
		return source;
	}
}
