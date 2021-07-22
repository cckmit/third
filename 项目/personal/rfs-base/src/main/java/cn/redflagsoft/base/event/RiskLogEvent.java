/*
 * $Id: RiskLogEvent.java 5904 2012-06-25 08:26:44Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.event;

import org.springframework.context.ApplicationEvent;

import cn.redflagsoft.base.bean.ExDataRisk;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskLog;

/**
 * RiskLog�¼��ࡣ
 * 
 * <p>���ݽ����е�BusiType����
 * 1-��ʼ
5-��ͣ
6-����
9-����
10-ʱ�޶�����
11-�����˱��
12-�����������
19-�������
 * 
 *
 */
public class RiskLogEvent extends ApplicationEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2835413665257493552L;
	public static final int START = 1;
	public static final int HANG = 5;
	public static final int WAKE = 6;
	public static final int TERMINATE = 9;
	public static final int SCALE_CHANGE = 10;
	public static final int DUTYER_CHANGE = 11;
	public static final int RISK_GRADE_CHANGE=12;
	public static final int RISK_VALUE_CHANGE=13;
	public static final int OTHER_CHANGE = 19;
	
	private final int type;
	private final ExDataRisk exDataRisk;
	private final Risk risk;

	public RiskLogEvent(RiskLog source, int type, ExDataRisk edr) {
		this(source, type, edr, null);
	}
	public RiskLogEvent(RiskLog source, int type,ExDataRisk edr, Risk risk) {
		super(source);
		this.type = type;
		this.exDataRisk = edr;
		this.risk = risk;
	}
	
	/**
	 * @return the risk
	 */
	public Risk getRisk() {
		return risk;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	public ExDataRisk getExDataRisk() {
		return exDataRisk;
	}

}
