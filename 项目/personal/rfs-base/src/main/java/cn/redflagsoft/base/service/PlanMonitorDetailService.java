package cn.redflagsoft.base.service;

import java.util.List;
/**
 * 
 */
public interface PlanMonitorDetailService {
	
	List<?> getPlanMonitorDetail(Integer objectType, Byte grade, Short lifeStage, Byte managerType, Long manager, Byte valueType, Short year,int key);
	//List<RFSObject> getPlanMonitorDetail(int objectType, short lifeStage, byte grade, Long manager);
}
