/*
 * $Id: CautionEventListener.java 5487 2012-04-09 04:05:21Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.event.v2.EventListener;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.event2.CautionEvent;
import cn.redflagsoft.base.service.SmsgForCautionService;

/**
 * 处理警示消息。
 * 
 * 警示在创建时，会根据配置来创建相应的消息（SMSG）。
 * 属性 ‘caution.smsg.auto-generate’ 是这个功能的开关，默认为true，即开启该功能。
 * 
 * @author lcj
 *
 */
public class CautionEventListener implements EventListener<CautionEvent> {
	private static final Log log = LogFactory.getLog(CautionEventListener.class);
	private SmsgForCautionService smsgForCautionService;
	
	/**
	 * @return the smsgForCautionService
	 */
	public SmsgForCautionService getSmsgForCautionService() {
		return smsgForCautionService;
	}

	/**
	 * @param smsgForCautionService the smsgForCautionService to set
	 */
	public void setSmsgForCautionService(SmsgForCautionService smsgForCautionService) {
		this.smsgForCautionService = smsgForCautionService;
	}

	public void handle(CautionEvent e) {
		if(e.getType() == CautionEvent.Type.CREATED){
			handleCautionCreated(e);
		}
	}

	/**
	 * 当警示创建时，根据配置，创建Smsg等
	 * @param e
	 */
	private void handleCautionCreated(CautionEvent e) {
		Caution caution = e.getCaution();
		Risk risk = e.getRisk();
		RiskRule riskRule = e.getRiskRule();
		
		try {
			createSmsgForCaution(caution, risk, riskRule);
		} catch (Exception e1) {
			if(e1 instanceof RuntimeException){
				throw (RuntimeException) e1;
			}else{
				throw new RuntimeException(e1);
			}
		}
	}

	
	/**
	 * 创建警示相关的消息，主要是短信。
	 * 多条消息，每条消息一个接收者。
	 * 
	 * @param caution
	 * @param risk
	 * @param riskRule
	 * @throws Exception 
	 */
	protected void createSmsgForCaution(final Caution caution, Risk risk, RiskRule riskRule) throws Exception{
		if(!AppsGlobals.getProperty("caution.smsg.auto-generate", true)){
			log.debug("属性 'caution.smsg.auto-generate' 设置为false，不自动产生警示的消息。");
			return;
		}
		smsgForCautionService.createSmsgForCaution(caution, risk, riskRule);
	}
}
