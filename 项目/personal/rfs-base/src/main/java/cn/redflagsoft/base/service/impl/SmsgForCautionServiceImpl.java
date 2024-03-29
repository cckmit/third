/*
 * $Id: SmsgForCautionServiceImpl.java 6279 2013-07-15 08:10:38Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.util.StringUtils;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.aop.interceptor.ParametersAware;
import cn.redflagsoft.base.bean.ArchivingStatus;
import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.Glossary;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.bean.Smsg;
import cn.redflagsoft.base.el.velocity.ExpressionImpl;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeInvoker;
import cn.redflagsoft.base.scheme.SchemeManager;
import cn.redflagsoft.base.scheme.schemes.smsg.SmsgWorkScheme;
import cn.redflagsoft.base.security.SupervisorUser;
import cn.redflagsoft.base.service.EntityObjectLoader;
import cn.redflagsoft.base.service.RiskRuleService;
import cn.redflagsoft.base.service.RiskService;
import cn.redflagsoft.base.service.SmsgForCautionService;
import cn.redflagsoft.base.service.SmsgService;
import cn.redflagsoft.base.util.CodeMapUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class SmsgForCautionServiceImpl implements SmsgForCautionService{
	public static final short YWZR_GLOSSARY_CATEGORY = Glossary.CATEGORY_CAUTION_SMSG_YWZR/*1102*/;
	public static final short CAUTION_SMSG_TITLE_GLOSSARY_CATEGORY = Glossary.CATEGORY_CAUTION_SMSG_TITLE/*1109*/;
	
	private static final Log log = LogFactory.getLog(SmsgForCautionServiceImpl.class);
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

	/**
	 * 创建警示相关的消息，主要是短信。
	 * 多条消息，每条消息一个接收者。
	 * 
	 * @param caution
	 * @param risk
	 * @param riskRule
	 * @throws Exception 
	 * @return - 
	 */
	public List<Smsg> createSmsgForCaution(final Caution caution, Risk risk, RiskRule riskRule) throws Exception{
		if(risk == null){
			risk = riskService.getRiskById(caution.getRiskId());
		}
		if(riskRule == null){
			riskRule = riskRuleService.getRiskRule(caution.getRuleId());
		}
	
		int config = caution.getMessageConfig();
		if(config <= 0){
			log.debug("根据Risk(" + risk.getId() + ")的MessageConfig的配置，没有指定任何有效的消息接收者，不生成警示对应的消息：" + config);
			return null;
		}
		
		//以对应的位值决定表示是否发送消息，从右开始依次为0-4位表示责任人、科室主管、分管领导、
		//监察员、监察领导，1表示仅责任人、3表示带科室主管、7表示带分管领导、15表示带监察员。
		boolean dutyerInReceivers = (config & 1) != 0;
		boolean dutyerManagerInReceivers = (config & 2) != 0;
		boolean dutyerLeaderInReceivers = (config & 4) != 0;
		boolean supervisorInReceivers = (config & 8) != 0;
		boolean supervisorLeaderInReceivers = (config & 16) != 0;
		
		
		List<Smsg> msgs = Lists.newArrayList();
		//责任人
		if (dutyerInReceivers) {
			if (caution.getDutyerId() != null) {
				if(isValidUserId(caution.getDutyerId())){
					Smsg smsg = createSmsg(caution, risk, riskRule, caution.getDutyerId(), caution.getDutyerName(), "负责");
					msgs.add(smsg);
				}
			}else{
				log.warn("虽然配置了给警示责任人发送短信，但责任人ID为空，无法生成短信。" 
						+ buildLogString(caution, risk, riskRule));
			}
		}else{
			log.debug("根据配置，不给警示责任人发送短信：" + caution.getId());
		}
				
		// 科室主管
		if (dutyerManagerInReceivers) {
			if (caution.getDutyerManagerId() != null) {
				if(isValidUserId(caution.getDutyerManagerId())){
					Smsg smsg = createSmsg(caution, risk, riskRule, caution.getDutyerManagerId(), caution.getDutyerManagerName(), "科室负责");
					msgs.add(smsg);
				}
			} else {
				log.warn("虽然配置了给警示科室主管发送短信，但科室主管ID为空，无法生成短信。"
						+ buildLogString(caution, risk, riskRule));
			}
		} else {
			log.debug("根据配置，不给警示科室主管发送短信：" + caution.getId());
		}
		
		//分管领导
		if(dutyerLeaderInReceivers){
			if(caution.getDutyerLeaderId() != null){
				if(isValidUserId(caution.getDutyerLeaderId())){
					Smsg smsg = createSmsg(caution, risk, riskRule, caution.getDutyerLeaderId(), caution.getDutyerLeaderName(), "分管");
					msgs.add(smsg);
				}
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
		
		return msgs;
	}
	
	private boolean isValidUserId(long userId){
		UserDetails user = Application.getContext().getUserManager().loadUserByUserId(userId);
		if(user == null){
			log.warn("消息接收用户不存在，无法接收消息：" + userId);
			return false;
		}
		if(!user.isEnabled()){
			log.warn("消息接收用户被禁用，无法接收消息：" + user.getUsername() + "[" + userId + "]");
			return false;
		}
		/*
		if(!user.isAccountNonExpired()){
			log.warn("消息接收用户账户已过期，无法接收消息：" + user.getUsername() + "[" + userId + "]");
			return false;
		}
		if(!user.isCredentialsNonExpired()){
			log.warn("消息接收用户密码已过期，无法接收消息：" + user.getUsername() + "[" + userId + "]");
			return false;
		}
		if(!user.isAccountNonLocked()){
			log.warn("消息接收用户被锁定，无法接收消息：" + user.getUsername() + "[" + userId + "]");
			return false;
		}*/
		return true;
	}
	
	
	private String buildLogString(Caution caution, Risk risk, RiskRule rule){
		return String.format("{cautionID=%s, riskGrade=%s, riskID=%s, riskRuleID=%s}", caution.getId(), 
				risk.getGradeName(), risk.getId(), rule.getId());
	}
	
	private Smsg createSmsg(Caution caution, Risk risk, RiskRule riskRule, Long receiverId, String receiverName, String ywzr) throws Exception{
		String content = parseMessageContent(caution.getMessageTemplate(), caution, risk, riskRule, receiverId, receiverName, ywzr);
		return createSmsg(caution, risk, riskRule, Lists.newArrayList(receiverId), content);
	}
	
	private Smsg createSmsg(Caution caution, Risk risk, RiskRule riskRule, List<Long> receiverIds, String content) throws Exception{
//		String content = parseMessageContent(caution.getMessageTemplate(), caution, risk, riskRule, context);
		Date writeTime = caution.getHappenTime() != null ? caution.getHappenTime() : new Date();
		
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
		params.put("smsg.writeTime", AppsGlobals.formatDate(writeTime));
		params.put("smsg.content", content);
		params.put("smsg.title", /*"监察消息"*/ buildMsgTitle(risk));
		params.put("smsg.status", ArchivingStatus.STATUS_公开档+"");
		
		//使用发生时间为Work的开始时间
		if(caution.getHappenTime() != null){
			//workBeginTime
			params.put("workBeginTime", AppsGlobals.formatDate(caution.getHappenTime()));
		}
		
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
	
	private String buildMsgTitle(Risk risk){
//	    switch (risk.getGrade()) {
//	        case 0:
//	            return "监察短信（正常）";
//	        case 1:
//	            return "监察提醒短信";
//	        case 2:
//	            return "监察预警短信";
//	        case 3:
//	            return "监察发牌短信（黄牌）";
//	        case 4:
//	            return "监察发牌短信（橙牌）";
//	        case 5:
//	            return "监察发牌短信（红牌）";
//	        case 6:
//	            return "监察发牌短信（黒牌）";
//	        default:
//	            return "监察短信";
//	        }
		String str = CodeMapUtils.getCodeName(CAUTION_SMSG_TITLE_GLOSSARY_CATEGORY, risk.getGrade());
		if(StringUtils.isBlank(str)){
			return "监察短信";
		}
		return str;
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
	public static String parseMessageContent(String messageTemplate, Caution caution,
			Risk risk, RiskRule riskRule, Long receiverId, String receiverName, String ywzr) {
		
		//从Glossary中重新读取ywzr的定义。
		int code = 0;
		if("负责".equals(ywzr)){
			code = 1;
		}else if ("科室负责".equals(ywzr)){
			code = 2;
		}else if("分管".equals(ywzr)){
			code = 4;
		}
		if(code > 0){
			ywzr = CodeMapUtils.getCodeName(YWZR_GLOSSARY_CATEGORY, code);
		}
		
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
		map.put("g_1102_1", "负责");
		map.put("rule", new RiskRule());
		map.put("date", new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
		map.put("dateTime", new SimpleDateFormat("yyyy年MM月dd日hh时mm分ss秒").format(new Date()));
		
		String template = "${risk.dutyerName}同志：电子监察提示您，您${g_1102_1}的${risk.objectName}业务已经开始，请在25日内完成，" +
				"特此提示。${risk.superviseOrgAbbr} $!{dateTime} $!{notExistsExpr}";
		//加“！”表示如果没有执行这个参数，则置空
		ExpressionImpl impl = new ExpressionImpl(template);
		String value = impl.getValue(map);
		System.out.println(value);
		
//		String parseMessageContent = parseMessageContent(template, new Caution(), risk, new RiskRule());
//		System.out.println(parseMessageContent);
	}
}
