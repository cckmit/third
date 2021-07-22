/*
 * $Id: CautionEventListener_bak2.java 5995 2012-08-20 07:16:29Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.event.v2.EventListener;
import org.opoo.apps.util.StringUtils;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.aop.interceptor.ParametersAware;
import cn.redflagsoft.base.bean.ArchivingStatus;
import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.bean.Smsg;
import cn.redflagsoft.base.el.velocity.ExpressionImpl;
import cn.redflagsoft.base.event2.CautionEvent;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeInvoker;
import cn.redflagsoft.base.scheme.SchemeManager;
import cn.redflagsoft.base.scheme.schemes.smsg.SmsgWorkScheme;
import cn.redflagsoft.base.security.SupervisorUser;
import cn.redflagsoft.base.service.EntityObjectLoader;
import cn.redflagsoft.base.service.RiskRuleService;
import cn.redflagsoft.base.service.RiskService;
import cn.redflagsoft.base.service.SmsgService;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 处理警示消息。
 * 
 * 警示在创建时，会根据配置来创建相应的消息（SMSG）。
 * 属性 ‘caution.smsg.auto-generate’ 是这个功能的开关，默认为true，即开启该功能。
 * 
 * @author lcj
 * @deprecated
 */
public class CautionEventListener_bak2 implements EventListener<CautionEvent> {
	private static final Log log = LogFactory.getLog(CautionEventListener_bak2.class);
	private RiskService riskService;
	private RiskRuleService riskRuleService;
	private SmsgService smsgService;
	private EntityObjectLoader entityObjectLoader;
	private SchemeManager schemeManager;
	
	/**
	 * @return the entityObjectLoader
	 */
	public EntityObjectLoader getEntityObjectLoader() {
		return entityObjectLoader;
	}

	/**
	 * @param entityObjectLoader the entityObjectLoader to set
	 */
	public void setEntityObjectLoader(EntityObjectLoader entityObjectLoader) {
		this.entityObjectLoader = entityObjectLoader;
	}

	/**
	 * @return the riskService
	 */
	public RiskService getRiskService() {
		return riskService;
	}

	/**
	 * @param riskService the riskService to set
	 */
	public void setRiskService(RiskService riskService) {
		this.riskService = riskService;
	}

	/**
	 * @return the riskRuleService
	 */
	public RiskRuleService getRiskRuleService() {
		return riskRuleService;
	}

	/**
	 * @param riskRuleService the riskRuleService to set
	 */
	public void setRiskRuleService(RiskRuleService riskRuleService) {
		this.riskRuleService = riskRuleService;
	}

	/**
	 * @return the smsgService
	 */
	public SmsgService getSmsgService() {
		return smsgService;
	}

	/**
	 * @param smsgService the smsgService to set
	 */
	public void setSmsgService(SmsgService smsgService) {
		this.smsgService = smsgService;
	}
	
	public void setSchemeManager(SchemeManager schemeManager) {
		this.schemeManager = schemeManager;
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
		
		if(risk == null){
			risk = riskService.getRiskById(caution.getRiskId());
		}
		if(riskRule == null){
			riskRule = riskRuleService.getRiskRule(caution.getRuleId());
		}
	
		int config = caution.getMessageConfig();
		if(config <= 0){
			log.debug("根据Risk(" + risk.getId() 
					+ ")的MessageConfig的配置，没有指定任何有效的消息接收者，不生成警示对应的消息：" + config);
			return;
		}
		
		//以对应的位值决定表示是否发送消息，从右开始依次为0-4位表示责任人、业务主管、分管领导、
		//监察员、监察领导，1表示仅责任人、3表示带业务主管、7表示带分管领导、15表示带监察员。
		boolean dutyerInReceivers = (config & 1) != 0;
		boolean dutyerManagerInReceivers = (config & 2) != 0;
		boolean dutyerLeaderInReceivers = (config & 4) != 0;
		boolean supervisorInReceivers = (config & 8) != 0;
		boolean supervisorLeaderInReceivers = (config & 16) != 0;
		
		
		List<Smsg> msgs = Lists.newArrayList();
		//责任人
		if (dutyerInReceivers) {
			if (caution.getDutyerId() != null) {
				Smsg smsg = createSmsg(caution, risk, riskRule, caution.getDutyerId(), caution.getDutyerName(), "负责");
				msgs.add(smsg);
			}else{
				log.warn("虽然配置了给警示责任人发送短信，但责任人ID为空，无法生成短信。" 
						+ buildLogString(caution, risk, riskRule));
			}
		}else{
			log.debug("根据配置，不给警示责任人发送短信：" + caution.getId());
		}
				
		// 业务主管
		if (dutyerManagerInReceivers) {
			if (caution.getDutyerManagerId() != null) {
				Smsg smsg = createSmsg(caution, risk, riskRule, caution.getDutyerManagerId(), caution.getDutyerManagerName(), "主管");
				msgs.add(smsg);
			} else {
				log.warn("虽然配置了给警示业务主管发送短信，但业务主管ID为空，无法生成短信。"
						+ buildLogString(caution, risk, riskRule));
			}
		} else {
			log.debug("根据配置，不给警示业务主管发送短信：" + caution.getId());
		}
		
		//分管领导
		if(dutyerLeaderInReceivers){
			if(caution.getDutyerLeaderId() != null){
				Smsg smsg = createSmsg(caution, risk, riskRule, caution.getDutyerLeaderId(), caution.getDutyerLeaderName(), "分管");
				msgs.add(smsg);
			}else {
				log.warn("虽然配置了给警示分管领导发送短信，但分管领导ID为空，无法生成短信。"
						+ buildLogString(caution, risk, riskRule));
			}
		}else {
			log.debug("根据配置，不给警示分管领导发送短信：" + caution.getId());
		}
		
		
		//监察员
		if(supervisorInReceivers){
			//TODO
		}
		
		//监察领导
		if(supervisorLeaderInReceivers){
			//TODO
		}
	}
	
	
	
	private String buildLogString(Caution caution, Risk risk, RiskRule rule){
		return String.format("{cautionID=%s, riskGrade=%s, riskID=%s, riskRuleID=%s}", caution.getId(), 
				risk.getGradeName(), risk.getId(), rule.getId());
	}
	
	private Smsg createSmsg(final Caution caution, Risk risk, RiskRule riskRule, Long receiverId, String receiverName, String ywzr) throws Exception{
		String content = parseMessageContent(caution.getMessageTemplate(), caution, risk, riskRule, receiverId, receiverName, ywzr);
		return createSmsg(caution, risk, riskRule, Lists.newArrayList(receiverId), content);
	}
	
	private Smsg createSmsg(Caution caution, Risk risk, RiskRule riskRule, List<Long> receiverIds, String content) throws Exception{
//		String content = parseMessageContent(caution.getMessageTemplate(), caution, risk, riskRule, context);
		
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("smsg.bizStatus", Smsg.BIZ_STATUS_已拟定+"");
		params.put("smsg.frId", SupervisorUser.USER_ID + "");
		params.put("smsg.frAddr","无");
		params.put("smsg.frName", "电子监察");
		params.put("smsg.frOrgId", caution.getSuperviseOrgId() != null ? caution.getSuperviseOrgId() + "" : null);
		params.put("smsg.frOrgName",caution.getSuperviseOrgAbbr());
		params.put("smsg.kind", Smsg.KIND_手机短信+"");
		params.put("smsg.riskGrade", risk.getGrade()+"");
		params.put("smsg.refObjectId", caution.getId()+"");
		params.put("smsg.refObjectName", caution.getName());
		params.put("smsg.refObjectCode", caution.getCode());
		params.put("smsg.refObjectType", ObjectTypes.CAUTION+"");
		params.put("smsg.writeTime", AppsGlobals.formatDate(new Date()));
		params.put("smsg.content", content);
		params.put("smsg.title", "监察消息");
		params.put("smsg.status", ArchivingStatus.STATUS_公开档+"");
		
		int i = 0;
		for(Long receiverId :receiverIds){
			params.put("receiverIds["+i+"]", receiverId+"");
			i++;
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try{
			Scheme scheme = schemeManager.getScheme("createSmsg");
			//调用Scheme
			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(SupervisorUser.USERNAME, "***"));
			((ParametersAware)scheme).setParameters(params);
			//log.debug("caution id ======================>>>>>>>: " + caution.getId() + "\n risk id #########################>>>>>>>>>" + risk.getId());
			SchemeInvoker.invoke(scheme, null);
			return ((SmsgWorkScheme)scheme).getSmsg();
		}catch(Exception e){
			log.error("创建消息时出错，当前警示ID: " + caution.getId() + "， 监察ID：" + risk.getId());
			throw e;
		}finally{
			//调用完成时，将当前用户信息恢复
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	}
	
	
	/**
	 * 通过消息模板解析消息内容。
	 * 
	 * @param messageTemplate
	 * @param caution
	 * @param risk
	 * @param riskRule
	 * @return
	 */
	private static String parseMessageContent(String messageTemplate, Caution caution,
			Risk risk, RiskRule riskRule, Long receiverId, String receiverName, String ywzr) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("caution", caution);
		map.put("risk", risk);
		map.put("rule", riskRule);
		map.put("date", new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
		map.put("dateTime", new SimpleDateFormat("yyyy年MM月dd日hh时mm分ss秒").format(new Date()));
		map.put("ywzr", ywzr);
		map.put("receiver", receiverName);
		map.put("receiverId", receiverId);
		
		try {
			return  StringUtils.processExpression(messageTemplate, map);
		} catch (Exception e) {
			log.error("消息模板解析错误", e);
		}
		
		return messageTemplate;
	}

	public static void main(String[] args){
		int x = 20;
		int i0 = x & 1;
		int i1 = x & 2;
		int i2 = x & 4;
		int i3 = x & 8;
		int i4 = x & 16;
		int i5 = x & 32;
		System.out.println(Integer.toBinaryString(x));
		System.out.println(i0);
		System.out.println(i1);
		System.out.println(i2);
		System.out.println(i3);
		System.out.println(i4);
		System.out.println(i5); 
		
		Risk risk = new Risk();
		risk.setDutyerName("张三");
		risk.setObjectName("大运场馆建设项目");
		risk.setSuperviseOrgAbbr("南山区监察局");
		
		Map<String,Object> map = Maps.newHashMap();
		map.put("caution", new Caution());
		map.put("risk", risk);
		map.put("rule", new RiskRule());
		map.put("date", new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
		map.put("dateTime", new SimpleDateFormat("yyyy年MM月dd日hh时mm分ss秒").format(new Date()));
		
		String template = "${risk.dutyerName}同志：电子监察提示您，您负责的${risk.objectName}业务已经开始，请在25日内完成，" +
				"特此提示。${risk.superviseOrgAbbr} $!{dateTime} $!{notExistsExpr}";
		//加“！”表示如果没有执行这个参数，则置空
		ExpressionImpl impl = new ExpressionImpl(template);
		String value = impl.getValue(map);
		System.out.println(value);
		
//		String parseMessageContent = parseMessageContent(template, new Caution(), risk, new RiskRule());
//		System.out.println(parseMessageContent);
	}
}
