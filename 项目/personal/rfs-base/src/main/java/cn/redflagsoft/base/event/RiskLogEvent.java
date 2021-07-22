/*
 * $Id: RiskLogEvent.java 5904 2012-06-25 08:26:44Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.event;

import org.springframework.context.ApplicationEvent;

import cn.redflagsoft.base.bean.ExDataRisk;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskLog;

/**
 * RiskLog事件类。
 * 
 * <p>数据交换中的BusiType定义
 * 1-开始
5-暂停
6-重启
9-结束
10-时限定义变更
11-责任人变更
12-监察结果级别变更
19-其他变更
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
