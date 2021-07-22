package cn.redflagsoft.base.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.DutyersInfo;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.RFSItemable;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.RFSObjectable;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.vo.BizVO;
import cn.redflagsoft.base.vo.MonitorVO;

public interface TaskService extends TaskChecker{
	
	/**
	 * 创建 Task
	 * 
	 * @param task
	 * @return Task
	 */
	Task createTask(Task task);
	
	/**
	 * 创建 Task
	 * 
	 * @param task
	 * @param matterIds
	 * @param objectId
	 * @return
	 * @deprecated
	 */
	Task createTask(Task task, Long[] matterIds, Long objectId, short dutyerType, Long dutyerID);
	
	/**
	 * 创建 Task，在TaskWorkHandlerForWorkSchemeInterceptor中调用。
	 * 
	 * @param task
	 * @param matterIds
	 * @param object
	 * @param dutyerType 责任主体类型
	 * @param dutyerID 责任主体ID，目前为null，since 2.1.0
	 * @return
	 */
	Task createTask(Task task, Long[] matterIds, RFSEntityObject object, short dutyerType, Long dutyerID);
	
	/**
	 * 创建 Task
	 * 
	 * @param type
	 * @param clerkId
	 * @return Task
	 * @deprecated
	 */
	Task createTask(Long clerkId, int type );
	
	
	/**
	 * 创建 Task
	 * 
	 * @param type
	 * @param clerkId
	 * @param matterId
	 * @return Task
	 * @deprecated
	 */
	Task createTask(Long clerkId,int type,  Long matterId);
	
	/**
	 * 创建 Task
	 * 
	 * @param type
	 * @param clerkId
	 * @param matterIds
	 * @return Task
	 * @deprecated
	 */
	Task createTask( Long clerkId,int type, Long[] matterIds);
	
	/**
	 * 创建 Task
	 * 
	 * @param type
	 * @param clerkId
	 * @param objectId
	 * @param matterIds
	 * @return
	 * @deprecated
	 */
	Task createTask(Long clerkId,int type,  Long[] matterIds,Long objectId);
	
	/**
	 * 以已经创建的sn来创建task。
	 * 
	 * @param clerkId
	 * @param type
	 * @param matterIds
	 * @param objectId
	 * @param sn 已经指定的sn
	 * @return
	 * @deprecated
	 */
	Task createTask(Long clerkId,int type,  Long[] matterIds,Long objectId, Long sn, short dutyerType, Long dutyerID);
	
	/**
	 * 创建 Task
	 * @param clerkId
	 * @param type
	 * @param matterIds
	 * @param objectId
	 * @param dutyerType
	 * @param dutyerID
	 * @return
	 * @deprecated
	 */
	Task createTask(Long clerkId,int type,  Long[] matterIds,Long objectId, short dutyerType, Long dutyerID);
	
	/**
	 * 创建 Task
	 * @param clerkId
	 * @param type
	 * @param matterIds
	 * @param objectId
	 * @param dutyerType
	 * @param dutyerID
	 * @param note
	 * @return
	 * @deprecated
	 */
	Task createTask(Long clerkId, int type, Long[] matterIds, Long objectId, short dutyerType, Long dutyerID,String note);
	
	/**
	 * 
	 * @param clerkId
	 * @param type
	 * @param matterIds
	 * @param objectId
	 * @param dutyerType
	 * @param dutyerID
	 * @param note
	 * @param beginTime
	 * @return
	 * @deprecated
	 */
	Task createTask(Long clerkId, int type, Long[] matterIds, Long objectId, short dutyerType, Long dutyerID,String note, Date beginTime);
	
	/**
	 * 修改hangTimes
	 * 
	 * @param taskSn
	 */
	void hangTask(Task t, Date hangTime); 
	
	void hangTask(Long taskSn);
	/**
	 * 修改timeHang
	 * 
	 * @param taskSn
	 */
	void wakeTask(Task t, Date wakeTime);
	
	void wakeTask(Long taskSn);
	
	/**
	 * 修改timeUsed,status,result的属性,工作终止
	 * 
	 * @param tasksn
	 * @param result
	 */
	void terminateTask(Task t, Byte result, Date endTime);
	
	/**
	 * 
	 * @param tasksn
	 * @param result
	 */
	void terminateTask(Long tasksn, Byte result);
	
	/* add ymq */
	
	/**
	 * 根据objectID、taskType和可见性（0或者100）查询Task集合，并并根据beginTime顺序排序。
	 */
	List<BizVO> findTasksByObjectId(Long objectId, Integer taskType);
	
	/**
	 * 根据objectID和可见性（0或者100）查询Task集合，并并根据beginTime顺序排序。
	 * @param objectId
	 * @return
	 */
	List<BizVO> findTasksByObjectId(Long objectId);
	
	/* end add ymq */
	/**
	 * 修改Task
	 * 
	 * @param task
	 * @return Task
	 */
	Task updateTask(Task task);
	
	/**
	 * 删除Task
	 * 
	 * @param task
	 * @return  int
	 */
	int deleteTask(Task task);
	
	/**
	 * 查找指定对象，指定类型的task集合。
	 * 
	 * @param objectID
	 * @param taskType
	 * @return
	 * @deprecated using {@link #findTasksByObjectId(Long, Integer)}
	 */
	List<Task> getTask(Long objectID, int taskType);
	
	/**
	 * 根据主键查询Task
	 * @param sn
	 * @return
	 */
	Task getTask(Long sn);
	
	boolean setTimeLimits(Task task, Long matter);
	
	Long createTaskTrack(Long taskSn, int taskType, Long... matterIds);
	
	/**
	 * 产生新的Task的ID。
	 * @return
	 */
	Long generateId();
	

	/**
	 * 获取今日提示-工作提示信息
	 * @param orgID
	 * @param clerkID
	 * @param index
	 * @return
	 */
	List<BizVO> findTaskForTodayHint(Long orgID,Long clerkID,int index);
	
	/**
	 * 查询Task业务概况
	 * 
	 * @param taskId
	 * @return BizVO
	 */
	BizVO getTaskBusinessSurvey(Long taskId);
	
	/**
	 * 查询Task业务日志
	 * 
	 * @param taskId
	 * @return List<BizVO>
	 */
	List<BizVO> findTaskBusinessLog(Long taskId);
	
	/**
	 * 查询objectId业务日志，
	 * 根据objectID和可见性（0或者100）查询Task集合，并根据beginTime顺序排序。
	 * 
	 * @param objectId
	 * @return List<Task>
	 */
	List<Task> findTaskByObjectId(Long objectId);
	
    /**
     * 查询指定objectId相关的task集合，不过滤可见性，并根据beginTime顺序排序
     * 
     * @param objectId
     * @return List<Task>
     */
    List<Task> findTaskAllVisibilityByObjectID(Long objectId);
    
	/***
	 * 获取Task风险监管信息
	 * @param taskId
	 * @return MonitorVO
	 * @deprecated
	 */
	MonitorVO getTaskMonitorRiskInfo(Long taskId);
	
	
	void updateTimeLimit(Long sn,short timeLimit,boolean autoTuneScale);
	
    void updateTimeLimit2(Long sn,short timeLimit);

    /**
     * 批量更新task的clerkID,但未更新task的clerkName。
     * @param ids
     * @param clerkID
     * @deprecated 更新不彻底，不推荐使用
     */
	void updateTaskClerkIdByTaskIds(final List<Long> ids,final Long clerkID);
	/**
	 * 取消
	 * @param t
	 */
	void cancelTask(Task t, Date endTime);
	
	void cancelTask(Task t);
	/**
	 * 中止
	 * @param t
	 */
	void stopTask(Task t, Date endTime);
	
	void stopTask(Task t);
	/**
	 * 结束
	 * @param t
	 */
	void terminateTask(Task t, Date endTime);
	
	void terminateTask(Task t);
	
	/**
	 * 根据objectID和可见性（0或者100或者指定可见性）查询Task集合，并并根据beginTime顺序排序。
	 * @param objectId
	 * @param visibility
	 * @return
	 */
	List<Task> findTaskByObjectIDandVisibilitys(Long objectId,int visibility);
	
	Task createTaskNotWithRisk(Task task, Long objectId, short dutyerType, Long dutyerID);
	
	Task createTaskWithTrack(Task task,Long[] matterIds,Long objectId, short dutyerType, Long dutyerID);
	
	/**
	 * @deprecated using {@link #findTasks(ResultFilter)}
	 * @param objectID
	 * @param taskType
	 * @return
	 */
	List<Task> getTaskAllVisibility(Long objectID, int taskType);
	/*
	 * 补填Task
	 */
	Task makeUpTask(Task task, Long[] matterIds, Long objectId, short dutyerType, Long dutyerID);
	/*
	 * 修改Task责任主体
	 */
	void updateTaskDutyerIDByTaskId(Long sn,short dutyerType,Long dutyerID);
	
	void updateTaskBeginTimeByTaskId(Long sn,Date beginTime);
	
	void updateBusiTimeByTaskSn(Long sn,Date startTime,Date endTime);
	
	void delayTask(Long taskSn, short delayTime);
	
	void delayTask(Task task, short delayTime);
	
	void updateTimeLimitWithScale235(Long sn,short timeLimit,short scaleValue2,short scaleValue3,short scaleValue5);


	/**
	 * 查询带附件数量的 Task 列表。
	 * 
	 * @param filter
	 * @return
	 */
	List<Task> findTaskWithAttachmentCount(ResultFilter filter);
	
	/**
	 * 根据过滤条件查询task集合。
	 * 
	 * @param filter
	 * @return
	 */
	List<Task> findTasks(ResultFilter filter);

	/**
	 * 免办
	 * @param task
	 */
	void avoidTask(Task task, Date endTime);
	
	void avoidTask(Task task);
	
	/**
	 * 获取指定对象的最新的一个task，这里忽略task的可见性属性。
	 * 
	 * @param objectId 对象ID
	 * @param objectType 对象类型
	 * @param taskType task类型
	 * @return task对象
	 * @since 2.0.1
	 */
	Task getLastTask(Long objectId, int objectType, int taskType);
	
	/**
	 * 获取指定对象的最新一个task，这里忽略task的可见性属性。
	 * 不查询ObjectTask。
	 * 
	 * @param objectId 对象ID
	 * @param objectType 对象类型
	 * @param taskType task类型
	 * @param status task状态
	 * @return task对象
	 * @since 2.0.1
	 */
	Task getLastTask(Long objectId, int objectType, int taskType, byte status);
	
	/**
	 * 查询主业务对象相关的Task列表，按创建时间倒序。
	 * 不查询ObjectTask。
	 * 
	 * @param object 主对象，不能为空
	 * @param type Task类型，NULL时忽略该条件
	 * @param visibility 可见性，NULL时忽略该条件
	 * @param status 状态，NULL时忽略该条件
	 * @return 符合条件的task集合
	 * @since 2.0.2
	 */
	List<Task> findTasks(RFSObjectable object, Integer type, Integer visibility, Byte status);
	
	/**
	 * 查询业务对象相关的Task列表，按创建时间倒序。
	 * 不查询ObjectTask。
	 *  
	 * @param object 业务对象，不能为空
	 * @param type Task类型，NULL时忽略该条件
	 * @param visibility 可见性，NULL时忽略该条件
	 * @param status 状态，NULL时忽略该条件
	 * @return 符合条件的task集合
	 * @since 2.0.2
	 */
	List<Task> findTasks(RFSItemable object, Integer type, Integer visibility, Byte status);

	/**
	 * 
	 * 更新Task关联的业务对象关系。
	 * 保存在RFSEntityObjectToTask中，对外部透明。
	 * 
	 * @param task
	 * @param objects
	 */
	Task updateTaskEntityObjects(Task task, RFSEntityObject... objects);

	/**
	 * 撤回
	 * @param task
	 * @param happenTime
	 */
	void withdrawTask(Task task, Date endTime);

	/**
	 * 驳回
	 * @param task
	 * @param happenTime
	 */
	void rejectTask(Task task, Date endTime);

	/**
	 * 撤销
	 * @param task
	 * @param happenTime
	 */
	void undoTask(Task task, Date endTime);
	
	/**
	 * 转出
	 * @param task
	 * @param endTime
	 */
	void transferTask(Task task, Date endTime);
	
	/**
	 * 监查关联查询
	 * @param filter
	 * @return
	 */
	PageableList<Map<String,Object>> findTaskMyWork(ResultFilter filter);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	int getTaskCount(ResultFilter filter);
	
	/**
	 * 用指定时间计算task的timeUsed值，并保存更新。
	 * 
	 * @param task
	 * @param calculateTime
	 * @return 计算后的timeUsed的增量值，不变时为0
	 */
	int calculateTaskTimeUsed(Task task, Date calculateTime);
	
	/**
	 * 用指定时间计算所有正在运行的task的timeUsed值，并保存更新。
	 * 
	 * @param calculateTime
	 * @return 一共处理的task数量
	 */
	int calculateAllRunningTasksTimeUsed(Date calculateTime);
	
	/**
	 * 用指定时间计算所有正在运行的task的timeUsed值，并保存更新。
	 * 
	 * @param calculateTime
	 * @return 一共处理的task数量
	 */
	int calculateAllRunningTasksTimeUsedInThreads(Date calculateTime);
	
	/**
	 * 获取最后一次计算task的timeUsed值的时间。
	 * 
	 * @return 最后一次计算task的timeUsed的时间
	 * @see #calculateAllRunningTasksTimeUsed(Date)
	 */
	Date getLastCalculateAllRunningTasksTimeUsedTime();
	
	/**
	 * 重置最后一次计算时间为 null，以便于再次计算。
	 * 保留最后一次计算的作用是同一天不多次计算，为了能同一条多次计算，则需要调用这
	 * 个方法将改时间置空。
	 */
	void resetLastCalculateAllRunningTasksTimeUsedTime();
	
	/****
	 *  更新 task 责任人，责任人单位，科室主管，分管领导
	 * @param taskSN 
	 * @param dutyClerk 		 负责人
	 * @param dutyerLeader1 	 科室主管
	 * @param dutyerLeader2		 分管领导
	 * @deprecated using {@link #updateTaskDutyersInfo(long, Clerk, Clerk, Clerk)}
	 */
	void updateTaskDutyClerk(Long taskSN, Clerk dutyClerk, Clerk dutyerLeader1, Clerk dutyerLeader2);
	
	
	/****
	 *  更新 task 责任人，责任人单位，科室主管，分管领导
	 * @param task 
	 * @param dutyClerk 		 负责人
	 * @param dutyerLeader1 	 科室主管
	 * @param dutyerLeader2		 分管领导
	 * @deprecated using {@link #updateTaskDutyersInfo(Task, Clerk, Clerk, Clerk)}
	 */
	void updateTaskDutyClerk(Task task, Clerk dutyClerk, Clerk dutyerLeader1, Clerk dutyerLeader2);
	
	/**
	 * 更新 task 责任人，责任人单位，科室主管，分管领导
	 * @param taskSN
	 * @param dutyersInfo
	 * @return
	 */
	Task updateTaskDutyersInfo(long taskSN, DutyersInfo dutyersInfo);
	
	/**
	 * 更新 task 责任人，责任人单位，科室主管，分管领导
	 * @param task
	 * @param dutyersInfo
	 * @return
	 */
	Task updateTaskDutyersInfo(Task task, DutyersInfo dutyersInfo);
	
	/**
	 * 更新 task 责任人，责任人单位，科室主管，分管领导
	 * @param taskSN
	 * @param dutyer
	 * @param dutyerLeader1
	 * @param dutyerLeader2
	 * @return
	 */
	Task updateTaskDutyersInfo(long taskSN, Clerk dutyer, Clerk dutyerLeader1, Clerk dutyerLeader2);
	
	/**
	 * 更新 task 责任人，责任人单位，科室主管，分管领导
	 * @param task
	 * @param dutyer
	 * @param dutyerLeader1
	 * @param dutyerLeader2
	 * @return
	 */
	Task updateTaskDutyersInfo(Task task, Clerk dutyer, Clerk dutyerLeader1, Clerk dutyerLeader2);
	
	/**
	 * 根据对象责任人的变化按需要更新task的责任人信息，并返回被更新的task的数量。
	 * 对象责任人变更时应该调用这个来处理责任人级联情况。
	 * @param object
	 * @return 被更新的业务数量
	 */
	int updateTasksDutyersInfoByRefObject(RFSObject object);
	
	/**
	 * @see #taskDelete(Task)
	 * @param taskSN
	 */
	void taskDelete(Long taskSN);
	
	/**
	 * 删除task及关联对象。
	 * @param task
	 */
	void taskDelete(Task task);
	
	
	void taskInvalid(RFSObject rfsObject);
}
