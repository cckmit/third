/*
 * $Id: RiskChecker.java 6410 2014-05-29 03:47:47Z lcj $
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
 * Risk���㡢��ʱ���ȴ�������
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface RiskChecker {
	
//
//	/**
//	 * ��ȡ���ڼ����valueֵ��
//	 * ���valueֵ��ʱ�����Ҷ�ʱ�����ģ���ͨ��һ�����㷨���ܻ��value��
//	 * 
//	 * �������һ����� {@link #calculateRiskGrade(Risk, BigDecimal, boolean)} һ��ʹ�á�
//	 * 
//	 * @param risk Risk����
//	 * @param checkTime ���ʱ�䣬���ڼ���valueֵ��ʱ��
//	 * @return ����õ���valueֵ
//	 */
//	//BigDecimal calculateRiskValue(Risk risk, Date checkTime);
//	
//	/**
//	 * ���Risk��value�ı仯�����value�仯�ˣ�������risk��grade��
//	 * 
//	 * @see #calculateRiskGrade(Risk, boolean)
//	 * @param risk  Risk����
//	 * @param newValue ���ڼ����valueֵ��������Ϊ�ա���ʹ�� {@link #calculateRiskValue(Risk, Date)}�ȼ������ֵ��
//	 * @param updateIfValueChanged �Ƿ���value�仯ʱ����risk����
//	 * @return value�Ƿ�仯��
//	 */
//	//boolean calculateRiskGrade(Risk risk, BigDecimal newValue, boolean updateIfValueChanged);
//	
//	/**
//	 * �����켶�𲢸�����Ҫ���¼�������risk��value������gradeֵ��
//	 * 
//	 * @param risk Risk����
//	 * @param updateIfGradeChanged updateIfValueChanged �Ƿ���grade�仯ʱ����risk����
//	 * @return grade�Ƿ�仯��
//	 */
//	//boolean calculateRiskGrade(Risk risk, boolean updateIfGradeChanged);

	
	/**
	 * ���Risk��value�����value�仯�ˣ�������risk��grade�����gradeҲ�仯�ˣ�
	 * ���������risk��gradeֵ��
	 * 
	 * <p>��risk��valueֵ����ʱ��仯�ģ�����risk��ص�ʱ�䶼��¼��risk������ʱ��
	 * �����������������risk��vlaue��grade�ȡ�
	 * 
	 * <p><b>ע�⣬ʹ�������������ʱ������valueֵ�ͼ���gradeֵ��һ��ִ�еģ�
	 * ��risk��valueһ����Դ��risk�е�������Ի������Եļ�������</b>
	 * 
	 * @param risk - Risk ����
	 * @param calculateTime - Date �������ʱ�䣬����Ϊ�գ�Ϊ��ȡ��ǰʱ��
	 * @return value�仯���Ҹ����˷���true�����򷵻�false��
	 * @see #updateRiskValue(Risk, BigDecimal, Date)
	 * @deprecated �Ƽ�ʹ�ü���value�ͼ���grade����ķ��� {@link #calculateAndUpdateRiskValue(Risk)}
	 */
	boolean calculateAndUpdateRiskValue(Risk risk, Date calculateTime);
	
	/**
	 * �ӱ���ض����в��ұ���ص����ԣ��Դ�����ֵ��Ϊrisk��value�����value��
	 * �仯��������Ҫ����grade��
	 * 
	 * <p>�������ض���ı������������ʱ��仯�ģ�ͨ����Ҫ����ض����Լ���ʱ
	 * �����Լ��ı����ֵ����Task��
	 *
	 * <p>�������������riskֵ�ļ��㣨��Դ���������߼���
	 * 
	 * @param risk
	 * @return
	 * @see #updateRiskValue(Risk, BigDecimal, Date)
	 * @see #updateRiskValue(Risk, RiskValue)
	 */
	boolean calculateAndUpdateRiskValue(Risk risk);
	
	/**
	 * 
	 * @param risk
	 * @param value
	 * @return
	 * @see #updateRiskValue(Risk, BigDecimal, Date)
	 */
	boolean updateRiskValue(Risk risk, RiskValue value);
	
	/**
	 * ����Risk��valueֵ�����valueֵ��ԭֵ��ȣ����������������value������grade��
	 * ���gradeҲ�仯�ˣ� ���������risk��gradeֵ��
	 * 
	 * @param risk - Risk ����
	 * @param newValue - �µ�valueֵ
	 * @param valueChangedTime - Date ֵ�仯��ʱ�򣬿���Ϊ�գ�Ϊ��ȡ��ǰʱ��
	 * @return value�仯���Ҹ����˷���true�����򷵻�false��
	 * @see #calculateAndUpdateRiskGrade(Risk)
	 */
	boolean updateRiskValue(Risk risk, BigDecimal newValue, Date valueChangedTime);
	
	/**
	 * ����Risk��grade�����grade�仯�ˣ������risk��gradeֵ��
	 *@param risk - Risk ����
	 * @return grade�仯���Ҹ����˷���true�����򷵻�false��
	 * @see #updateRiskGrade(Risk, byte)
	 */
	boolean calculateAndUpdateRiskGrade(Risk risk);
	
	/**
	 * ����Risk��gradeֵ�����gradeֵ��ԭֵ��ȣ����������������grade��
	 * 
	 * @param risk - Risk ����
	 * @param newGrade - byte ��켶��
	 * @return grade�仯���Ҹ����˷���true�����򷵻�false��
	 */
	boolean updateRiskGrade(Risk risk, byte newGrade);
	
	/**
	 * ��ȡ����value�������У�û����ͣ����risk��value����
	 * ����value�����Ҫ����grade��
	 * 
	 * <p>�������һ�㹩��ʱִ�еĵ���������á�
	 * 
	 * <p>�÷����ᱣ��risk�ı仯��
	 * 
	 * @return ������µ�risk����
	 */
	int calculateAllRunningRisks();
	
	
	/**
	 * �ڶ��߳��м��㡣
	 * 
	 * @return ������µ�risk����
	 * @see RiskChecker#calculateAllRunningRisks()
	 */
	int calculateAllRunningRisksInThreads();
	
//	/**
//	 * ���¼�������risk��gradeֵ��һ������grade������
//	 * 
//	 * <p>һ����ͨ���ǳ����޸���risk��valueʱ���������ݿ�ֱ���޸ģ���
//	 * ������risk��ͣʱ��Ȼ��Ҫ����ʱ�������������
//	 * 
//	 * <p>�÷������������risk������risk�Ƿ���ͣ������risk���ں���״̬��
//	 * 
//	 * <p>�÷����ᱣ��risk�ı仯��
//	 * 
//	 * @param force - boolean �Ƿ�ǿ�Ƽ��㣬�������Ϊtrue���򲻲����Ż��㷨��ǿ�Ƽ�������risk��grade��
//	 * @return ������µ�risk����
//	 */
//	int recalculateAllRisksGrade(boolean force);
}
