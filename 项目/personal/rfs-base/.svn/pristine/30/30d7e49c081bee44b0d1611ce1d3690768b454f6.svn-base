package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.RiskRule;

/**
 * 风险规则管理。
 *
 */
public interface RiskRuleService {
	/**
	 * 查找指定对象配置的风险规则。
	 * 
	 * @param objectType 对象类型
	 * @return 风险规则集合
	 */
	List<RiskRule> findRiskRules(int objectType);
	
	/**
	 * 查找风险规则。
	 * 
	 * @param objectType 对象类型
	 * @param objectAttr 被监控的属性
	 * @param refType 相关类型
	 * @return 风险规则集合
	 */
	List<RiskRule> findRiskRules(int objectType, String objectAttr, int refType);
	
	/**
	 * 查找风险规则。
	 * @param ruleId
	 * @return
	 */
	RiskRule getRiskRule(Long ruleId);
	
	/**
	 * 修改风险规则
	 * @param riskRule
	 * @return
	 */
	RiskRule updateRiskRule(RiskRule riskRule);
}
