package cn.redflagsoft.base.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskEntry;
import cn.redflagsoft.base.bean.RiskMonitorable;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.bean.RiskTree;
import cn.redflagsoft.base.bean.Task;

/**
 * Risk处理类。 
 *
 */
public interface RiskService extends RiskChecker{
	/**
	 * List 注意:本方法使用SQL聚合函数查询,元素中存储的是二维Object[],字段包含[ObjectId, Grade]
	 * 
	 * @param objectType
	 * @return List
	 */
	List<Object[]> findRiskByType(int objectType);
	
	
	/**
	 * 
	 * @param riskMonitorable
	 * @param dutyerId
	 * @return
	 */
	List<RiskEntry> createRisksForRiskMonitorable(RiskMonitorable riskMonitorable, Long dutyerId);
	
	/**
	 * 创建特定类风险 job 101,task 102,work 103，Task中调用这个方法产生风险。
	 * @param riskMonitorable 被监控对象
	 * @param refType 相关种类
	 * @param dutyerId 责任主体ID
	 * @param object 相关的主对象
	 * @return 产生的风险项
	 */
	List<RiskEntry> createRisk(RiskMonitorable riskMonitorable, int refType, Long dutyerId, RFSObject object);
	
	/**
	 * 创建特定类风险 job 101,task 102,work 103
	 * @param object
	 * @param refType
	 * @param objectAttr
	 */
	List<RiskEntry> createRisk(RiskMonitorable riskMonitorable, String objectAttr, int refType, Long dutyerId);
	
//	/**
//	 * 创建监察对象。此方法可以手动调用，为指定的规则创建监察对象。
//	 * 
//	 * @param riskMonitorable 被监控对象，不能为空
//	 * @param dutyerId 责任主体ID，可以为空
//	 * @param riskRule 监察规则，不能为空
//	 * @param object 相关的主对象，可以为空
//	 * @param scaleValue 标度值，可以为空
//	 * @return 风险监控项集合，一般返回给被监控对象进行保存
//	 */
//	List<RiskEntry> createRisk(RiskMonitorable riskMonitorable, Long dutyerId, RiskRule riskRule, RFSObject object, BigDecimal scaleValue);
	
	/**
	 * 
	 *  创建监察对象。此方法可以手动调用，为指定的规则创建监察对象。
	 * 
	 * @param riskMonitorable 被监控对象，不能为空
	 * @param dutyerId 责任主体ID，可以为空
	 * @param riskRule 监察规则，不能为空
	 * @param object 相关的主对象，可以为空
	 * @param scaleValue 标度值，可以为空
	 * @param code 编码，可以为空
	 * @param beginTime 开始时间，可以为空
	 * @param remark 备注，可以为空
	 * @return 风险监控项,一般返回给被监控对象进行保存
	 */
	RiskEntry createRisk(RiskMonitorable riskMonitorable, Long dutyerId, RiskRule riskRule, 
			RFSObject object, BigDecimal scaleValue, String code, Date beginTime, String remark);
	
	/**
	 * 删除被监控对象特定监控属性的RISK信息。
	 * @param riskMonitorable
	 * @param objectAttr
	 */
	void deleteRisk(RiskMonitorable riskMonitorable, String objectAttr);
	
	/**
	 * 删除被监控对象的RISK信息。
	 * 
	 * @param riskMonitorable
	 */
	void deleteRisk(RiskMonitorable riskMonitorable);
	
	/**
	 * 
	 * @param riskMonitorable
	 * @param objectAttr
	 * @param dutyerId
	 * @param scaleValue
	 * @return List<RiskEntry>
	 */
	List<RiskEntry> createRisk(RiskMonitorable riskMonitorable, String objectAttr, Long dutyerId, BigDecimal scaleValue);
	
	void updateRiskValue(Long riskId, BigDecimal value);
	
	void updateRiskScaleValue(Long riskId, BigDecimal scaleValue);
	   
    void updateRiskScaleValue(Risk risk, BigDecimal scaleValue);

    void hangRisk(Long riskId, Date hangTime);
	
	void wakeRisk(Long riskId, Date wakeTime);
	
	void terminateRisk(Long riskId, Date terminateTime);
	
	void terminateRisk(Risk risk, Task task, Date endTime);
	
	//void checkRisk(Long riskId, BigDecimal value);
	
	void updateRiskDutyer(Long riskId, Long dutyerId);

	void updateRiskScale(Long riskId,BigDecimal scaleValue, byte scaleId);

	//void updateObjectAttr(Long objectId, int objectType, String objectAttr);
	
	/**
	 * 定时执行的监察扫描。
	 * @deprecated Using {@link RiskChecker#calculateAllRunningRisks()}
	 */
	void watch();
	
	Risk getRiskById(Long id);
	
	BigDecimal getRiskScale(Long id, byte scaleId);

	List<RiskTree> findRiskTree(Long dutyerId);
	
	Map<String, List<Map<Object,Object>>> findRiskByObjectId(Long objectId, int objectType);
	
	List<Risk> findRiskByObjectIdAndType(Long objectId,int objectType);
	
	List<Risk> findTaskRiskByObjectIdAndTaskType(Long objectId, final int objectType, Integer... taskTypes);
	
	
	/**
	 * add by ymq
	 * 
	 * 查询可监控对象风险,
	 * refObjectType为taskType,jobType,workType等
	 * objectId为相应监控对象编号
	 * 
	 * @param objectId
	 * @param refObjectType
	 * @return Risk
	 */
	Risk querySingleRisk(Long objectId, int refObjectType);
	
	List<Map<String,Object>> findCollectByGroupID(Long groupId);
	
	List<Object[]> getValueAndScaleValueCountGroupByDutyerID();
	
	void deleteRiskByTaskSn(final Long taskSN);
	
	List<Risk> findRiskByObjectAndObjectType(Long objectID,Integer objectType);
	
	void sendRiskLogEvent(Risk risk,Task t,int type);
	
	//void update(Risk risk);
	
	List<Risk> findAllRisks();
	
	/**
	 * 计算单个Risk的最终方法
	 * @param risk
	 */
	void checkRiskGrade(Risk risk);
	
	void gradeChangeRemind(int days);
	
	String getGradeExplain(byte grade);

	/**
	 * 延期Task等时限监察时执行的方法。
	 * 
	 * @param riskID
	 * @param delayTime
	 */
	void delayRisk(Long riskID, short delayTime);
	
	List<Risk> findRisks(ResultFilter filter);
	
	/**
	 * 获取Risk的最后一次计算时间。
	 * @return
	 */
	Date getLastCalculateAllRunningRisksTime();
	
	/**
	 * 重设Risk的最后一次计算时间为null，以便重新计算。
	 */
	void resetLastCalculateAllRunningRisksTime();
	
	
	/****
	 *  更新 risk责任人，责任人单位，科室主管，分管领导
	 * @param riskID
	 * @param dutyClerk 	 	 负责人
	 * @param dutyerLeader1 	 科室主管
	 * @param dutyerLeader2	 	 分管领导
	 */
	void updateRiskDutyersInfo(Long riskID, Clerk dutyClerk, Clerk dutyerLeader1,
			Clerk dutyerLeader2);
	
	/****
	 *  更新 risk责任人，责任人单位，科室主管，分管领导
	 * @param risk
	 * @param dutyClerk 	 负责人
	 * @param dutyerLeader1 	 科室主管
	 * @param dutyerLeader2	 分管领导
	 */
	void updateRiskDutyersInfo(Risk risk, Clerk dutyClerk,
			Clerk dutyerLeader1, Clerk dutyerLeader2);
	
	void riskInvalid(RFSObject rfsObject);
}
