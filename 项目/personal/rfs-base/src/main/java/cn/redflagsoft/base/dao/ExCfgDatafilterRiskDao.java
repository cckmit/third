package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;
import cn.redflagsoft.base.bean.ExCfgDatafilterRisk;



public interface ExCfgDatafilterRiskDao  extends Dao<ExCfgDatafilterRisk,Long> {
	List<ExCfgDatafilterRisk> findExCfgDatafilterRiskByRuleIDAndEvent(Long riskRuleID,String event);
}
