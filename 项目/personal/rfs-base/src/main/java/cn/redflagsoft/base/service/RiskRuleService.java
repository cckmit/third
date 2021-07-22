package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.RiskRule;

/**
 * ���չ������
 *
 */
public interface RiskRuleService {
	/**
	 * ����ָ���������õķ��չ���
	 * 
	 * @param objectType ��������
	 * @return ���չ��򼯺�
	 */
	List<RiskRule> findRiskRules(int objectType);
	
	/**
	 * ���ҷ��չ���
	 * 
	 * @param objectType ��������
	 * @param objectAttr ����ص�����
	 * @param refType �������
	 * @return ���չ��򼯺�
	 */
	List<RiskRule> findRiskRules(int objectType, String objectAttr, int refType);
	
	/**
	 * ���ҷ��չ���
	 * @param ruleId
	 * @return
	 */
	RiskRule getRiskRule(Long ruleId);
	
	/**
	 * �޸ķ��չ���
	 * @param riskRule
	 * @return
	 */
	RiskRule updateRiskRule(RiskRule riskRule);
}
