package cn.redflagsoft.base.service.impl;

import java.util.List;

import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.dao.RiskRuleDao;
import cn.redflagsoft.base.service.RiskRuleService;

public class RiskRuleServiceImpl implements RiskRuleService {
	private RiskRuleDao riskRuleDao;
	
	public RiskRuleDao getRiskRuleDao() {
		return riskRuleDao;
	}

	public void setRiskRuleDao(RiskRuleDao riskRuleDao) {
		this.riskRuleDao = riskRuleDao;
	}

	public List<RiskRule> findRiskRules(int objectType) {
		return riskRuleDao.findRiskRules(objectType);
	}

	public List<RiskRule> findRiskRules(int objectType, String objectAttr, int refType) {
		return riskRuleDao.findRiskRules(objectType, objectAttr, refType);
	}
	
	public RiskRule getRiskRule(Long ruleId){
		return riskRuleDao.get(ruleId);
	}

	public RiskRule updateRiskRule(RiskRule riskRule) {
		return riskRuleDao.update(riskRule);
	}
}
