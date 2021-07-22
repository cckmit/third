/*
 * $Id: RiskCalculator.java 5189 2011-12-12 04:52:47Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import java.math.BigDecimal;
import java.util.Date;

import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskValue;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface RiskCalculator {
	
	/**
	 * �ӱ���ض���ı���������ж�ȡָ��Risk��valueֵ�����risk��ֵ��Դ��
	 * ��ʱ�����������ȡ�Լ�������
	 * 
	 * ͨ���ڼ���risk��grade֮ǰ���������������ȡrisk����ֵ��
	 * 
	 * @param risk
	 * @return
	 */
	RiskValue getRiskValue(Risk risk);//�Ƿ������Ϊ objectId, objectType, objectAttr?

	
	/**
	 * ͨ��Risk�������м���Risk����ֵ��ԭ���ϸ÷������ı�risk���κ����ԣ������ʱ�䣬���ܻ�ı�LastRunTime��
	 * һ��ֵ��ʱ��������ʱ��仯ʱ��ÿ�μ�����ܶ���ȡ�µ�ֵ��
	 * 
	 * <p>�������ض����ܹ��Լ���ʱ�����Լ��ı��������ֵ����ͨ������{@link #getValue(Risk)}�����������Ǳ�
	 * ���������� Task���Զ�ʱ�����Լ���timeUsed����
	 * 
	 * <p>�÷���һ������ͨ�õ�ʱ�������Լ�죬���Ҹ���ʱ��ʱ��¼��risk�����е������
	 * 
	 * @param risk - Risk �������Risk
	 * @param calculateTime - Date ����ʱ�䣬����Ϊnull��Ϊnullʱȡ��ǰֵ
	 * @return ������ֵ
	 */
	BigDecimal calculateValue(Risk risk, Date calculateTime);
	
	/**
	 * ����Risk���¼���
	 * 
	 * @param risk �������risk������ǰ���ֵ�б仯��Ҫ������
	 * @return ������ļ�켶��
	 */
	byte calculateGrade(Risk risk);
}
