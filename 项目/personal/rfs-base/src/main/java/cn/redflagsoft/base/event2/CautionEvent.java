/*
 * $Id: CautionEvent.java 4886 2011-10-10 09:23:43Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.event2;

import org.opoo.apps.event.v2.Event;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskRule;


/**
 * ��ʾ�¼���
 * 
 * @author lcj
 *
 */
public class CautionEvent extends Event<CautionEvent.Type, Caution> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6445461337730720887L;
	private final Risk risk;
	private final RiskRule riskRule;
	
	public CautionEvent(Type eventType, Caution caution, Risk risk, RiskRule rule) {
		super(eventType, caution);
		this.risk = risk;
		this.riskRule = rule;
	}
	public CautionEvent(Type eventType, Caution caution){
		this(eventType, caution, null, null);
	}
	
	/**
	 * @return the risk
	 */
	public Risk getRisk() {
		return risk;
	}
	/**
	 * @return the riskRule
	 */
	public RiskRule getRiskRule() {
		return riskRule;
	}

	public Caution getCaution(){
		return getSource();
	}

	public static enum Type{
		CREATED, UPDATED, DELETED;
	}  
}
