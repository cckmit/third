package cn.redflagsoft.base.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.scheme.AbstractWorkScheme;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeInvoker;
import cn.redflagsoft.base.scheme.SchemeManager;
import cn.redflagsoft.base.security.SupervisorUser;
import cn.redflagsoft.base.service.RiskGradeChangeHandler;

/**
 * ���յȼ����ʱ����caution����
 * 
 * @author lcj
 * @author lf
 */
public class RiskGradeChangeHandler4Caution implements RiskGradeChangeHandler {
	private final static Log log = LogFactory.getLog(RiskGradeChangeHandler4Caution.class);
	private SchemeManager schemeManager;
//	private CautionService cautionService;
	
//	/**
//	 * @return the cautionService
//	 */
//	public CautionService getCautionService() {
//		return cautionService;
//	}
//
//
//	/**
//	 * @param cautionService the cautionService to set
//	 */
//	public void setCautionService(CautionService cautionService) {
//		this.cautionService = cautionService;
//	}


	public void gradeChange(Risk risk, byte oldGrade) {
		//ֻ�м����з�����Ϣ
		if (RiskRule.RULE_TYPE_��� != risk.getRuleType()) {
			log.debug("���Ǽ�죬������");
			return;
		}
		
//		if(risk.getGrade() < oldGrade){
//			String msg = String.format("��켶�𽵼���%s -> %s��ʱ������ʾ��", oldGrade, risk.getGrade());
//			log.warn(msg);
//			return;
//		}
		if(risk.getGrade() <= Risk.GRADE_NORMAL){
			String msg = String.format("��켶��%s -> %s��ʱ������ʾ��", oldGrade, risk.getGrade());
			log.warn(msg);
			return;
		}
		
//		Caution c = new Caution();
//		c.setConclusion(risk.getConclusion());
//		c.setDutyerDeptId(risk.getDutyerDeptId());
//		c.setDutyerDeptName(risk.getDutyerDeptName());
//		c.setDutyerName(risk.getDutyerName());
//		c.setDutyerId(risk.getDutyerID());
//		
//		//TODO ��ѯclerk�����ϼ��쵼���ֻ�����
//		if(risk.getDutyerID() != null){
//			
////			c.setDutyerLeaderId(clerk.getId());
////			c.setDutyerLeaderMobNo(clerk.getMobNo());
////			c.setDutyerLeaderName(clerk.getName());
////			c.setDutyerManagerId(clerk.get);
////			c.setDutyerManagerMobNo(null);
////			c.setDutyerManagerName(null);
////			c.setDutyerMobNo(null);
////			c.setDutyerName(null);
//			
//		}
//		
//		c.setDutyerOrgId(risk.getDutyerOrgId());
//		c.setDutyerOrgName(risk.getDutyerOrgName());
//		c.setGrade(risk.getGrade());
//		c.setHappenTime(new Date());
//		c.setJuralLimit(risk.getJuralLimit());
//		c.setMessageConfig(risk.getMessageConfig());
//		c.setMessageTemplate(risk.getMessageTemplate());
//		c.setName(risk.getName());
//		c.setObjectAttr(risk.getObjectAttr());
//		c.setObjectAttrName(risk.getObjectAttrName());
//		c.setObjectAttrUnit(risk.getValueUnit());
//		c.setObjectAttrValue(risk.getValue());
//		c.setObjectCode(risk.getObjectCode());
//		c.setObjectId(risk.getObjectID());
//		c.setObjectName(risk.getObjectName());
//		c.setObjectType(risk.getObjectType());
//		c.setPause(risk.getPause());
//		c.setRefObjectId(risk.getRefObjectId());
//		c.setRefObjectType(risk.getRefObjectType());
//		c.setRemark(risk.getRemark());
//		c.setRiskCode(risk.getCode());
//		c.setRiskId(risk.getId());
//		c.setRiskName(risk.getName());
//		c.setRiskType(risk.getType());
//		
//		// ��RiskRule��
//		RiskRule riskRule = riskRuleDao.get(risk.getRuleID());
//		c.setRuleCode(riskRule.getCode());
//		c.setRuleId(risk.getRuleID());
//		
//		c.setScaleValue(risk.getScaleValue());
//		c.setStatus((byte)1);
////		c.setSuperviseClerkId(risk.getSuperviseOrgId());
////		c.setSuperviseClerkName(null);
//		c.setSuperviseOrgAbbr(risk.getSuperviseOrgAbbr());
//		c.setSuperviseOrgId(risk.getSuperviseOrgId());
////		c.setSystemId(SystemID.)
//		c.setType(risk.getType());
//		
//		// ����Service���� Caution����
//		cautionService.saveCaution(c);
		
		
		
		/*
		Clerk clerk2 = UserClerkHolder.getClerk(SupervisorUser.USER_ID);
		cautionService.createCaution(risk, clerk2);
		*/
		//�ĵ���WorkScheme
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		try{
			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(SupervisorUser.USERNAME, "*"));
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("riskId", risk.getId());
			params.put("clerkID", SupervisorUser.USER_ID);
			
			Scheme scheme = schemeManager.getScheme("createCaution");
			((AbstractWorkScheme) scheme).setParameters(params);
			
			SchemeInvoker.invoke(scheme, null);
		}catch(Exception e){
			log.error("���þ�ʾ�Ǽ�ʱ����", e);
		}finally{
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	}

	/**
	 * @return the schemeManager
	 */
	public SchemeManager getSchemeManager() {
		return schemeManager;
	}

	/**
	 * @param schemeManager the schemeManager to set
	 */
	public void setSchemeManager(SchemeManager schemeManager) {
		this.schemeManager = schemeManager;
	}

}
