/*
 * $Id: RiskCalculatorImpl.java 5436 2012-03-13 01:11:40Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.ObjectNotFoundException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskMonitorable;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.bean.RiskValue;
import cn.redflagsoft.base.service.EntityObjectLoader;
import cn.redflagsoft.base.service.RiskCalculator;
import cn.redflagsoft.base.service.RiskValueProvider;
import cn.redflagsoft.base.util.DateUtil;
import cn.redflagsoft.base.util.RiskMonitorableUtils;

import com.google.common.collect.Lists;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class RiskCalculatorImpl implements RiskCalculator, ApplicationContextAware{
	
	public static final Log log = LogFactory.getLog(RiskCalculatorImpl.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
//	private Collection<RiskValueCalculator> riskValueCalculators = Lists.newArrayList();
	private Collection<RiskValueProvider> riskValueProviders = Lists.newArrayList();
	private EntityObjectLoader entityObjectLoader;
	
	public void setRiskValueProviders(
			Collection<RiskValueProvider> riskValueProviders) {
		this.riskValueProviders = riskValueProviders;
	}

	public void setEntityObjectLoader(EntityObjectLoader entityObjectLoader) {
		this.entityObjectLoader = entityObjectLoader;
	}
	
	public RiskValue getRiskValue(Risk risk){
		BigDecimal value = risk.getValue();
		//��ͣ
		boolean isPause = (Risk.PAUSE_YES == risk.getPause());
		if(isPause){
			if(IS_DEBUG_ENABLED){
				log.debug("��ͣʱ�����㣬ֱ��ʹ��ԭֵ��");
			}
			return new RiskValueImpl(value, risk.getValueChangedTime(), risk);
		}
		
		//provider�����ȼ���
		//����������ʹ��providerȡֵ�������provider��ֵ
		RiskValueProvider provider = lookupProvider(risk);
		if(provider != null){
			if(IS_DEBUG_ENABLED){
				log.debug("ʹ��RiskValueProvider����ȡrisk��valueֵ��" + provider);
			}
			return provider.getRiskValue(risk);
		}
		
		//ʱ������������ֵ������ʱ��ĲŴ���
		if (RiskRule.VALUE_SOURCE_TIMER_ADD == risk.getValueSource() && RiskRule.VALUE_TYPE_TIME == risk.getValueType()) {
			BigDecimal value2 = calculateTimeAddedValue(risk, risk.getValueCalculateTime());
			return new RiskValueImpl(value2, risk.getValueCalculateTime(), risk);
		}
		
		//�ⲿ����value��
		//������Ӧ�����ԣ�valueSource == RiskRule.VALUE_SOURCE_EXTERIOR/*0*/
		RFSEntityObject entityObject = entityObjectLoader.getEntityObject(risk.getObjectType(), risk.getObjectID());
		if(entityObject != null){
			if(entityObject instanceof RiskMonitorable){
				RiskMonitorable rm = (RiskMonitorable) entityObject;
				return new RiskValueImpl(rm.getRiskValue(risk.getObjectAttr()), null, rm);
			}else{
				//ȡ��Ӧ������
				return new RiskValueImpl(RiskMonitorableUtils.getRiskValue(entityObject, risk.getObjectAttr()), null, entityObject);
			}
		}else{
			throw new ObjectNotFoundException("û���ҵ�����ض���");
		}
	}
	
	private RiskValueProvider lookupProvider(Risk risk){
		for(RiskValueProvider p: riskValueProviders){
			if(p.supportsGetRiskValue(risk)){
				return p;
			}
		}
		return null;
	}
	
	public BigDecimal calculateValue(Risk risk, Date calculateTime){
		BigDecimal value = risk.getValue();
		//��ͣ
		boolean isPause = (Risk.PAUSE_YES == risk.getPause());
		if(isPause){
			if(IS_DEBUG_ENABLED){
				log.debug("��ͣʱ�����㣬ֱ��ʹ��ԭֵ��");
			}
			return value;
		}
		
		byte scaleMark = risk.getScaleMark();
		BigDecimal scaleValue = risk.getScaleValue();
		
		if ((scaleMark & 64) != 0 && (scaleValue.intValue() == 0)) {//�ٷֱȼ�� && scalevalue=0ʱ���������㼰��ֵ
			if(IS_DEBUG_ENABLED){
				log.debug("[Risk.checkGrade()] object(" + risk.getObjectID() 
						+ ")��risk(" + risk.getId() +")�ǰٷֱȼ�죬��scalevalue=0������ֵ���㡣");
			}
			return value;
		}
		
		//ʱ������������ֵ������ʱ��ĲŴ���
		if (RiskRule.VALUE_SOURCE_TIMER_ADD == risk.getValueSource() && RiskRule.VALUE_TYPE_TIME == risk.getValueType()) {
			return calculateTimeAddedValue(risk, calculateTime);
		}
		
		//�����㷨����
		return value;
	}
	
	/**
	 * ����ʱ����������risk��valueֵ��
	 * 
	 * @param risk
	 * @param calculateTime
	 * @return
	 */
	private BigDecimal calculateTimeAddedValue(Risk risk, Date calculateTime){
		BigDecimal value = risk.getValue();
		if(calculateTime == null){
			calculateTime = new Date();
		}
		//����������ʱ��
		Date lastCheckTime = risk.getLastRunTime();
		if(lastCheckTime == null){
			lastCheckTime = risk.getBeginTime();
			if(IS_DEBUG_ENABLED){
				log.debug("ȡbeginTimeΪ�������ʱ�䣺" + lastCheckTime);
			}
		}
		if(lastCheckTime == null){
			lastCheckTime = risk.getCreationTime();
			if(IS_DEBUG_ENABLED){
				log.debug("BeginTimeҲΪ�գ�ȡctime��" + lastCheckTime);
			}
		}
		//boolean isPause = (Risk.PAUSE_YES == risk.getPause());
		int timeUse = /*isPause ? 0 :*/ DateUtil.getTimeUsed(lastCheckTime, calculateTime, risk.getValueUnit());
		
		//ֵû�б仯
		if(timeUse == 0){
			if(IS_DEBUG_ENABLED){
				log.debug("ʱ������Ϊ0��ֱ��ʹ��ԭֵ��");
			}
			return value;
		}

		//ֵ�仯��
		//����������ʱ�䣬����ָ����ǰ�ƶ�timeUse����λ
		Date date = DateUtil.getDate(lastCheckTime, timeUse, risk.getValueUnit(), true);
		risk.setLastRunTime(date);
		//������risk����ֵʱӦ�ñ���lastRunTime��valueChangedTime

		BigDecimal valueAdd = new BigDecimal(timeUse * risk.getValueSign());
		if(IS_DEBUG_ENABLED){
			log.debug("ʱ��������" + valueAdd);
		}
		
		if(value == null){
			return valueAdd;
		}else{
			return value.add(valueAdd);
		} 
	}
	
	
	public byte calculateGrade(Risk risk){
		return RiskMonitorableUtils.calculateGrade(risk);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
//		this.applicationContext = applicationContext;
		try {
			@SuppressWarnings("unchecked")
			Map<String, RiskValueProvider> map = applicationContext.getBeansOfType(RiskValueProvider.class);
			riskValueProviders = map.values();
			log.info("��ȡriskValueProviders: " + riskValueProviders);
		} catch (Exception e) {
			log.warn("��ȡriskValueProviders����", e);
		}
	}
}
