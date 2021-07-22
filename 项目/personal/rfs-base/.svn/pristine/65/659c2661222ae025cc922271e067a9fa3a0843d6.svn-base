package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.RiskRule;

public interface RiskRuleDao extends Dao<RiskRule, Long> {
	
	List<RiskRule> findRiskRules(int objectType);
	
	List<RiskRule> findRiskRules(int objectType, int refType);
	
	List<RiskRule> findRiskRules(int objectType, String objectAttr, int refType);
	
	List<RiskRule> findRiskRules(int objectType, String objectAttr);
}
