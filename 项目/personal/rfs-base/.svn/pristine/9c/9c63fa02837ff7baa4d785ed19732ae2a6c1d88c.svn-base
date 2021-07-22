package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.bean.ExCfgDatafilterRisk;
import cn.redflagsoft.base.dao.ExCfgDatafilterRiskDao;




public class ExCfgDatafilterRiskHibernateDao extends AbstractBaseHibernateDao<ExCfgDatafilterRisk,Long> implements ExCfgDatafilterRiskDao{
	private Log log=LogFactory.getLog(ExCfgDatafilterRiskHibernateDao.class);
	
	
	@SuppressWarnings("unchecked")
	public List<ExCfgDatafilterRisk> findExCfgDatafilterRiskByRuleIDAndEvent(
			Long riskRuleID, String event) {
		if(riskRuleID==null||event==null){
			log.warn("ExCfgDatafilterRiskHibernateDao²ÎÊýriskRuleID¡¢eventÎªnull!");
			return null;
		}
		
		String qs = "select a from ExCfgDatafilterRisk a,RiskRule b where a.riskRuleID=b.id and a.riskRuleID=? and a.event=? and a.status=1 and b.status=1";
		return getHibernateTemplate().find(qs, new Object[]{riskRuleID,event});
	}

}
