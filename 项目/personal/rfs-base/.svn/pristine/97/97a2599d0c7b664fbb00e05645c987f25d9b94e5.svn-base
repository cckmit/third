package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.dao.ObjectDao;
import cn.redflagsoft.base.service.PlanMonitorDetailService;

public class PlanMonitorDetailServiceImpl implements PlanMonitorDetailService {
	public static final Log log = LogFactory.getLog(PlanMonitorDetailServiceImpl.class);
//	private RiskDao riskDao;
//	
//	public void setRiskDao(RiskDao riskDao) {
//		this.riskDao = riskDao;
//	}
	
	private ObjectDao<?> objectDao;
	
	public List<?> getPlanMonitorDetail(Integer objectType, Byte grade, Short lifeStage, Byte managerType, Long manager, Byte valueType, Short year,int key) {
		//eturn riskDao.findObjectWithRisk(objectType, lifeStage, grade, manager);
		return objectDao.findObjectByRisk(objectType, grade, lifeStage, managerType, manager, valueType, year,key);
	}

	/**
	 * @return the objectDao
	 */
	public ObjectDao<?> getObjectDao() {
		return objectDao;
	}

	/**
	 * @param objectDao the objectDao to set
	 */
	public void setObjectDao(ObjectDao<?> objectDao) {
		this.objectDao = objectDao;
	}
}
