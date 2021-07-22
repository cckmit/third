package cn.redflagsoft.base.service;

import cn.redflagsoft.base.vo.statistics.PlanMonitorResult;

public interface PlanMonitorService {
	
	PlanMonitorResult getPlanMonitorResult(Integer objectType, Byte riskValueType);
	

	PlanMonitorResult getObjectRiskSummaryByManager(Integer objectType, Integer riskType, Short objectStatus, Short year,int key,Long entityID);

}
