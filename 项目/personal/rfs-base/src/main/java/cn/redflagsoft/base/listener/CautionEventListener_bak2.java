/*
 * $Id: CautionEventListener_bak2.java 5995 2012-08-20 07:16:29Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
 * ����ʾ��Ϣ��
 * 
 * ��ʾ�ڴ���ʱ�������������������Ӧ����Ϣ��SMSG����
 * ���� ��caution.smsg.auto-generate�� ��������ܵĿ��أ�Ĭ��Ϊtrue���������ù��ܡ�
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
	 * ����ʾ����ʱ���������ã�����Smsg��
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
	 * ������ʾ��ص���Ϣ����Ҫ�Ƕ��š�
	 * ������Ϣ��ÿ����Ϣһ�������ߡ�
	 * 
	 * @param caution
	 * @param risk
	 * @param riskRule
	 * @throws Exception 
	 */
	protected void createSmsgForCaution(final Caution caution, Risk risk, RiskRule riskRule) throws Exception{
		if(!AppsGlobals.getProperty("caution.smsg.auto-generate", true)){
			log.debug("���� 'caution.smsg.auto-generate' ����Ϊfalse�����Զ�������ʾ����Ϣ��");
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
			log.debug("����Risk(" + risk.getId() 
					+ ")��MessageConfig�����ã�û��ָ���κ���Ч����Ϣ�����ߣ������ɾ�ʾ��Ӧ����Ϣ��" + config);
			return;
		}
		
		//�Զ�Ӧ��λֵ������ʾ�Ƿ�����Ϣ�����ҿ�ʼ����Ϊ0-4λ��ʾ�����ˡ�ҵ�����ܡ��ֹ��쵼��
		//���Ա������쵼��1��ʾ�������ˡ�3��ʾ��ҵ�����ܡ�7��ʾ���ֹ��쵼��15��ʾ�����Ա��
		boolean dutyerInReceivers = (config & 1) != 0;
		boolean dutyerManagerInReceivers = (config & 2) != 0;
		boolean dutyerLeaderInReceivers = (config & 4) != 0;
		boolean supervisorInReceivers = (config & 8) != 0;
		boolean supervisorLeaderInReceivers = (config & 16) != 0;
		
		
		List<Smsg> msgs = Lists.newArrayList();
		//������
		if (dutyerInReceivers) {
			if (caution.getDutyerId() != null) {
				Smsg smsg = createSmsg(caution, risk, riskRule, caution.getDutyerId(), caution.getDutyerName(), "����");
				msgs.add(smsg);
			}else{
				log.warn("��Ȼ�����˸���ʾ�����˷��Ͷ��ţ���������IDΪ�գ��޷����ɶ��š�" 
						+ buildLogString(caution, risk, riskRule));
			}
		}else{
			log.debug("�������ã�������ʾ�����˷��Ͷ��ţ�" + caution.getId());
		}
				
		// ҵ������
		if (dutyerManagerInReceivers) {
			if (caution.getDutyerManagerId() != null) {
				Smsg smsg = createSmsg(caution, risk, riskRule, caution.getDutyerManagerId(), caution.getDutyerManagerName(), "����");
				msgs.add(smsg);
			} else {
				log.warn("��Ȼ�����˸���ʾҵ�����ܷ��Ͷ��ţ���ҵ������IDΪ�գ��޷����ɶ��š�"
						+ buildLogString(caution, risk, riskRule));
			}
		} else {
			log.debug("�������ã�������ʾҵ�����ܷ��Ͷ��ţ�" + caution.getId());
		}
		
		//�ֹ��쵼
		if(dutyerLeaderInReceivers){
			if(caution.getDutyerLeaderId() != null){
				Smsg smsg = createSmsg(caution, risk, riskRule, caution.getDutyerLeaderId(), caution.getDutyerLeaderName(), "�ֹ�");
				msgs.add(smsg);
			}else {
				log.warn("��Ȼ�����˸���ʾ�ֹ��쵼���Ͷ��ţ����ֹ��쵼IDΪ�գ��޷����ɶ��š�"
						+ buildLogString(caution, risk, riskRule));
			}
		}else {
			log.debug("�������ã�������ʾ�ֹ��쵼���Ͷ��ţ�" + caution.getId());
		}
		
		
		//���Ա
		if(supervisorInReceivers){
			//TODO
		}
		
		//����쵼
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
		params.put("smsg.bizStatus", Smsg.BIZ_STATUS_���ⶨ+"");
		params.put("smsg.frId", SupervisorUser.USER_ID + "");
		params.put("smsg.frAddr","��");
		params.put("smsg.frName", "���Ӽ��");
		params.put("smsg.frOrgId", caution.getSuperviseOrgId() != null ? caution.getSuperviseOrgId() + "" : null);
		params.put("smsg.frOrgName",caution.getSuperviseOrgAbbr());
		params.put("smsg.kind", Smsg.KIND_�ֻ�����+"");
		params.put("smsg.riskGrade", risk.getGrade()+"");
		params.put("smsg.refObjectId", caution.getId()+"");
		params.put("smsg.refObjectName", caution.getName());
		params.put("smsg.refObjectCode", caution.getCode());
		params.put("smsg.refObjectType", ObjectTypes.CAUTION+"");
		params.put("smsg.writeTime", AppsGlobals.formatDate(new Date()));
		params.put("smsg.content", content);
		params.put("smsg.title", "�����Ϣ");
		params.put("smsg.status", ArchivingStatus.STATUS_������+"");
		
		int i = 0;
		for(Long receiverId :receiverIds){
			params.put("receiverIds["+i+"]", receiverId+"");
			i++;
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try{
			Scheme scheme = schemeManager.getScheme("createSmsg");
			//����Scheme
			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(SupervisorUser.USERNAME, "***"));
			((ParametersAware)scheme).setParameters(params);
			//log.debug("caution id ======================>>>>>>>: " + caution.getId() + "\n risk id #########################>>>>>>>>>" + risk.getId());
			SchemeInvoker.invoke(scheme, null);
			return ((SmsgWorkScheme)scheme).getSmsg();
		}catch(Exception e){
			log.error("������Ϣʱ������ǰ��ʾID: " + caution.getId() + "�� ���ID��" + risk.getId());
			throw e;
		}finally{
			//�������ʱ������ǰ�û���Ϣ�ָ�
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	}
	
	
	/**
	 * ͨ����Ϣģ�������Ϣ���ݡ�
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
		map.put("date", new SimpleDateFormat("yyyy��MM��dd��").format(new Date()));
		map.put("dateTime", new SimpleDateFormat("yyyy��MM��dd��hhʱmm��ss��").format(new Date()));
		map.put("ywzr", ywzr);
		map.put("receiver", receiverName);
		map.put("receiverId", receiverId);
		
		try {
			return  StringUtils.processExpression(messageTemplate, map);
		} catch (Exception e) {
			log.error("��Ϣģ���������", e);
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
		risk.setDutyerName("����");
		risk.setObjectName("���˳��ݽ�����Ŀ");
		risk.setSuperviseOrgAbbr("��ɽ������");
		
		Map<String,Object> map = Maps.newHashMap();
		map.put("caution", new Caution());
		map.put("risk", risk);
		map.put("rule", new RiskRule());
		map.put("date", new SimpleDateFormat("yyyy��MM��dd��").format(new Date()));
		map.put("dateTime", new SimpleDateFormat("yyyy��MM��dd��hhʱmm��ss��").format(new Date()));
		
		String template = "${risk.dutyerName}ͬ־�����Ӽ����ʾ�����������${risk.objectName}ҵ���Ѿ���ʼ������25������ɣ�" +
				"�ش���ʾ��${risk.superviseOrgAbbr} $!{dateTime} $!{notExistsExpr}";
		//�ӡ�������ʾ���û��ִ��������������ÿ�
		ExpressionImpl impl = new ExpressionImpl(template);
		String value = impl.getValue(map);
		System.out.println(value);
		
//		String parseMessageContent = parseMessageContent(template, new Caution(), risk, new RiskRule());
//		System.out.println(parseMessageContent);
	}
}
