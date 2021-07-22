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
 * 风险等级变更时保存caution对象。
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
		//只有监察才有发牌信息
		if (RiskRule.RULE_TYPE_监察 != risk.getRuleType()) {
			log.debug("不是监察，不处理");
			return;
		}
		
//		if(risk.getGrade() < oldGrade){
//			String msg = String.format("监察级别降级（%s -> %s）时不处理警示。", oldGrade, risk.getGrade());
//			log.warn(msg);
//			return;
//		}
		if(risk.getGrade() <= Risk.GRADE_NORMAL){
			String msg = String.format("监察级别（%s -> %s）时不处理警示。", oldGrade, risk.getGrade());
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
//		//TODO 查询clerk，找上级领导及手机号码
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
//		// 查RiskRule表
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
//		// 调用Service保存 Caution对象
//		cautionService.saveCaution(c);
		
		
		
		/*
		Clerk clerk2 = UserClerkHolder.getClerk(SupervisorUser.USER_ID);
		cautionService.createCaution(risk, clerk2);
		*/
		//改调用WorkScheme
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
			log.error("调用警示登记时出错", e);
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
