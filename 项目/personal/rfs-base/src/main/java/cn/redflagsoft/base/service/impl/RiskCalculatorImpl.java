/*
 * $Id: RiskCalculatorImpl.java 5436 2012-03-13 01:11:40Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
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
		//暂停
		boolean isPause = (Risk.PAUSE_YES == risk.getPause());
		if(isPause){
			if(IS_DEBUG_ENABLED){
				log.debug("暂停时不计算，直接使用原值。");
			}
			return new RiskValueImpl(value, risk.getValueChangedTime(), risk);
		}
		
		//provider的优先级高
		//如果对象可以使用provider取值，则调用provider的值
		RiskValueProvider provider = lookupProvider(risk);
		if(provider != null){
			if(IS_DEBUG_ENABLED){
				log.debug("使用RiskValueProvider来获取risk的value值：" + provider);
			}
			return provider.getRiskValue(risk);
		}
		
		//时间自增长的且值类型是时间的才处理
		if (RiskRule.VALUE_SOURCE_TIMER_ADD == risk.getValueSource() && RiskRule.VALUE_TYPE_TIME == risk.getValueType()) {
			BigDecimal value2 = calculateTimeAddedValue(risk, risk.getValueCalculateTime());
			return new RiskValueImpl(value2, risk.getValueCalculateTime(), risk);
		}
		
		//外部设置value的
		//调用相应的属性，valueSource == RiskRule.VALUE_SOURCE_EXTERIOR/*0*/
		RFSEntityObject entityObject = entityObjectLoader.getEntityObject(risk.getObjectType(), risk.getObjectID());
		if(entityObject != null){
			if(entityObject instanceof RiskMonitorable){
				RiskMonitorable rm = (RiskMonitorable) entityObject;
				return new RiskValueImpl(rm.getRiskValue(risk.getObjectAttr()), null, rm);
			}else{
				//取相应的属性
				return new RiskValueImpl(RiskMonitorableUtils.getRiskValue(entityObject, risk.getObjectAttr()), null, entityObject);
			}
		}else{
			throw new ObjectNotFoundException("没有找到被监控对象");
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
		//暂停
		boolean isPause = (Risk.PAUSE_YES == risk.getPause());
		if(isPause){
			if(IS_DEBUG_ENABLED){
				log.debug("暂停时不计算，直接使用原值。");
			}
			return value;
		}
		
		byte scaleMark = risk.getScaleMark();
		BigDecimal scaleValue = risk.getScaleValue();
		
		if ((scaleMark & 64) != 0 && (scaleValue.intValue() == 0)) {//百分比监察 && scalevalue=0时，跳过计算及赋值
			if(IS_DEBUG_ENABLED){
				log.debug("[Risk.checkGrade()] object(" + risk.getObjectID() 
						+ ")的risk(" + risk.getId() +")是百分比监察，且scalevalue=0，跳过值计算。");
			}
			return value;
		}
		
		//时间自增长的且值类型是时间的才处理
		if (RiskRule.VALUE_SOURCE_TIMER_ADD == risk.getValueSource() && RiskRule.VALUE_TYPE_TIME == risk.getValueType()) {
			return calculateTimeAddedValue(risk, calculateTime);
		}
		
		//其它算法暂无
		return value;
	}
	
	/**
	 * 计算时间自增长的risk的value值。
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
		//处理最后计算时间
		Date lastCheckTime = risk.getLastRunTime();
		if(lastCheckTime == null){
			lastCheckTime = risk.getBeginTime();
			if(IS_DEBUG_ENABLED){
				log.debug("取beginTime为最后运行时间：" + lastCheckTime);
			}
		}
		if(lastCheckTime == null){
			lastCheckTime = risk.getCreationTime();
			if(IS_DEBUG_ENABLED){
				log.debug("BeginTime也为空，取ctime：" + lastCheckTime);
			}
		}
		//boolean isPause = (Risk.PAUSE_YES == risk.getPause());
		int timeUse = /*isPause ? 0 :*/ DateUtil.getTimeUsed(lastCheckTime, calculateTime, risk.getValueUnit());
		
		//值没有变化
		if(timeUse == 0){
			if(IS_DEBUG_ENABLED){
				log.debug("时间增量为0，直接使用原值。");
			}
			return value;
		}

		//值变化了
		//设置最后计算时间，计算指针向前移动timeUse个单位
		Date date = DateUtil.getDate(lastCheckTime, timeUse, risk.getValueUnit(), true);
		risk.setLastRunTime(date);
		//保存了risk的新值时应该保存lastRunTime和valueChangedTime

		BigDecimal valueAdd = new BigDecimal(timeUse * risk.getValueSign());
		if(IS_DEBUG_ENABLED){
			log.debug("时间增量：" + valueAdd);
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
			log.info("获取riskValueProviders: " + riskValueProviders);
		} catch (Exception e) {
			log.warn("获取riskValueProviders出错。", e);
		}
	}
}
