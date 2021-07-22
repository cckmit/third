package cn.redflagsoft.base.service.impl;

import java.util.List;
import java.util.Map;

import cn.redflagsoft.base.dao.RiskMessageDao;
import cn.redflagsoft.base.service.RiskMessageService;

public class RiskMessageServiceImpl implements RiskMessageService {
	private RiskMessageDao riskMessageDao;
	
	public RiskMessageDao getRiskMessageDao() {
		return riskMessageDao;
	}

	public void setRiskMessageDao(RiskMessageDao riskMessageDao) {
		this.riskMessageDao = riskMessageDao;
	}

	/**
	 * Map中的格式为{clerkId,name}.
	 * 
	 * <pre>
	 * select new map(a.clerkId as clerkId,b.name as name) from RiskMessage a,Clerk b where a.clerkId=b.id and a.type=?
	 * </pre>
	 */
	public List<Map<String, Object>> findAcceptSMSPersons(Byte grade) {
		return riskMessageDao.findAcceptSMSPersons(grade);
	}
}
