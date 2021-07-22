/*
 * $Id: RiskValueImpl.java 5206 2011-12-13 08:10:24Z lcj $
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
