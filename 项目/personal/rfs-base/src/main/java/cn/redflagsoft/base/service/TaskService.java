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
	 * ���� Task
	 * 
	 * @param task
	 * @return Task
	 */
	Task createTask(Task task);
	
	/**
	 * ���� Task
	 * 
	 * @param task
	 * @param matterIds
	 * @param objectId
	 * @return
	 * @deprecated
	 */
	Task createTask(Task task, Long[] matterIds, Long objectId, short dutyerType, Long dutyerID);
	
	/**
	 * ���� Task����TaskWorkHandlerForWorkSchemeInterceptor�е��á�
	 * 
	 * @param task
	 * @param matterIds
	 * @param object
	 * @param dutyerType ������������
	 * @param dutyerID ��������ID��ĿǰΪnull��since 2.1.0
	 * @return
	 */
	Task createTask(Task task, Long[] matterIds, RFSEntityObject object, short dutyerType, Long dutyerID);
	
	/**
	 * ���� Task
	 * 
	 * @param type
	 * @param clerkId
	 * @return Task
	 * @deprecated
	 */
	Task createTask(Long clerkId, int type );
	
	
	/**
	 * ���� Task
	 * 
	 * @param type
	 * @param clerkId
	 * @param matterId
	 * @return Task
	 * @deprecated
	 */
	Task createTask(Long clerkId,int type,  Long matterId);
	
	/**
	 * ���� Task
	 * 
	 * @param type
	 * @param clerkId
	 * @param matterIds
	 * @return Task
	 * @deprecated
	 */
	Task createTask( Long clerkId,int type, Long[] matterIds);
	
	/**
	 * ���� Task
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
	 * ���Ѿ�������sn������task��
	 * 
	 * @param clerkId
	 * @param type
	 * @param matterIds
	 * @param objectId
	 * @param sn �Ѿ�ָ����sn
	 * @return
	 * @deprecated
	 */
	Task createTask(Long clerkId,int type,  Long[] matterIds,Long objectId, Long sn, short dutyerType, Long dutyerID);
	
	/**
	 * ���� Task
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
	 * ���� Task
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
	 * �޸�hangTimes
	 * 
	 * @param taskSn
	 */
	void hangTask(Task t, Date hangTime); 
	
	void hangTask(Long taskSn);
	/**
	 * �޸�timeHang
	 * 
	 * @param taskSn
	 */
	void wakeTask(Task t, Date wakeTime);
	
	void wakeTask(Long taskSn);
	
	/**
	 * �޸�timeUsed,status,result������,������ֹ
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
	 * ����objectID��taskType�Ϳɼ��ԣ�0����100����ѯTask���ϣ���������beginTime˳������
	 */
	List<BizVO> findTasksByObjectId(Long objectId, Integer taskType);
	
	/**
	 * ����objectID�Ϳɼ��ԣ�0����100����ѯTask���ϣ���������beginTime˳������
	 * @param objectId
	 * @return
	 */
	List<BizVO> findTasksByObjectId(Long objectId);
	
	/* end add ymq */
	/**
	 * �޸�Task
	 * 
	 * @param task
	 * @return Task
	 */
	Task updateTask(Task task);
	
	/**
	 * ɾ��Task
	 * 
	 * @param task
	 * @return  int
	 */
	int deleteTask(Task task);
	
	/**
	 * ����ָ������ָ�����͵�task���ϡ�
	 * 
	 * @param objectID
	 * @param taskType
	 * @return
	 * @deprecated using {@link #findTasksByObjectId(Long, Integer)}
	 */
	List<Task> getTask(Long objectID, int taskType);
	
	/**
	 * ����������ѯTask
	 * @param sn
	 * @return
	 */
	Task getTask(Long sn);
	
	boolean setTimeLimits(Task task, Long matter);
	
	Long createTaskTrack(Long taskSn, int taskType, Long... matterIds);
	
	/**
	 * �����µ�Task��ID��
	 * @return
	 */
	Long generateId();
	

	/**
	 * ��ȡ������ʾ-������ʾ��Ϣ
	 * @param orgID
	 * @param clerkID
	 * @param index
	 * @return
	 */
	List<BizVO> findTaskForTodayHint(Long orgID,Long clerkID,int index);
	
	/**
	 * ��ѯTaskҵ��ſ�
	 * 
	 * @param taskId
	 * @return BizVO
	 */
	BizVO getTaskBusinessSurvey(Long taskId);
	
	/**
	 * ��ѯTaskҵ����־
	 * 
	 * @param taskId
	 * @return List<BizVO>
	 */
	List<BizVO> findTaskBusinessLog(Long taskId);
	
	/**
	 * ��ѯobjectIdҵ����־��
	 * ����objectID�Ϳɼ��ԣ�0����100����ѯTask���ϣ�������beginTime˳������
	 * 
	 * @param objectId
	 * @return List<Task>
	 */
	List<Task> findTaskByObjectId(Long objectId);
	
    /**
     * ��ѯָ��objectId��ص�task���ϣ������˿ɼ��ԣ�������beginTime˳������
     * 
     * @param objectId
     * @return List<Task>
     */
    List<Task> findTaskAllVisibilityByObjectID(Long objectId);
    
	/***
	 * ��ȡTask���ռ����Ϣ
	 * @param taskId
	 * @return MonitorVO
	 * @deprecated
	 */
	MonitorVO getTaskMonitorRiskInfo(Long taskId);
	
	
	void updateTimeLimit(Long sn,short timeLimit,boolean autoTuneScale);
	
    void updateTimeLimit2(Long sn,short timeLimit);

    /**
     * ��������task��clerkID,��δ����task��clerkName��
     * @param ids
     * @param clerkID
     * @deprecated ���²����ף����Ƽ�ʹ��
     */
	void updateTaskClerkIdByTaskIds(final List<Long> ids,final Long clerkID);
	/**
	 * ȡ��
	 * @param t
	 */
	void cancelTask(Task t, Date endTime);
	
	void cancelTask(Task t);
	/**
	 * ��ֹ
	 * @param t
	 */
	void stopTask(Task t, Date endTime);
	
	void stopTask(Task t);
	/**
	 * ����
	 * @param t
	 */
	void terminateTask(Task t, Date endTime);
	
	void terminateTask(Task t);
	
	/**
	 * ����objectID�Ϳɼ��ԣ�0����100����ָ���ɼ��ԣ���ѯTask���ϣ���������beginTime˳������
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
	 * ����Task
	 */
	Task makeUpTask(Task task, Long[] matterIds, Long objectId, short dutyerType, Long dutyerID);
	/*
	 * �޸�Task��������
	 */
	void updateTaskDutyerIDByTaskId(Long sn,short dutyerType,Long dutyerID);
	
	void updateTaskBeginTimeByTaskId(Long sn,Date beginTime);
	
	void updateBusiTimeByTaskSn(Long sn,Date startTime,Date endTime);
	
	void delayTask(Long taskSn, short delayTime);
	
	void delayTask(Task task, short delayTime);
	
	void updateTimeLimitWithScale235(Long sn,short timeLimit,short scaleValue2,short scaleValue3,short scaleValue5);


	/**
	 * ��ѯ������������ Task �б�
	 * 
	 * @param filter
	 * @return
	 */
	List<Task> findTaskWithAttachmentCount(ResultFilter filter);
	
	/**
	 * ���ݹ���������ѯtask���ϡ�
	 * 
	 * @param filter
	 * @return
	 */
	List<Task> findTasks(ResultFilter filter);

	/**
	 * ���
	 * @param task
	 */
	void avoidTask(Task task, Date endTime);
	
	void avoidTask(Task task);
	
	/**
	 * ��ȡָ����������µ�һ��task���������task�Ŀɼ������ԡ�
	 * 
	 * @param objectId ����ID
	 * @param objectType ��������
	 * @param taskType task����
	 * @return task����
	 * @since 2.0.1
	 */
	Task getLastTask(Long objectId, int objectType, int taskType);
	
	/**
	 * ��ȡָ�����������һ��task���������task�Ŀɼ������ԡ�
	 * ����ѯObjectTask��
	 * 
	 * @param objectId ����ID
	 * @param objectType ��������
	 * @param taskType task����
	 * @param status task״̬
	 * @return task����
	 * @since 2.0.1
	 */
	Task getLastTask(Long objectId, int objectType, int taskType, byte status);
	
	/**
	 * ��ѯ��ҵ�������ص�Task�б�������ʱ�䵹��
	 * ����ѯObjectTask��
	 * 
	 * @param object �����󣬲���Ϊ��
	 * @param type Task���ͣ�NULLʱ���Ը�����
	 * @param visibility �ɼ��ԣ�NULLʱ���Ը�����
	 * @param status ״̬��NULLʱ���Ը�����
	 * @return ����������task����
	 * @since 2.0.2
	 */
	List<Task> findTasks(RFSObjectable object, Integer type, Integer visibility, Byte status);
	
	/**
	 * ��ѯҵ�������ص�Task�б�������ʱ�䵹��
	 * ����ѯObjectTask��
	 *  
	 * @param object ҵ����󣬲���Ϊ��
	 * @param type Task���ͣ�NULLʱ���Ը�����
	 * @param visibility �ɼ��ԣ�NULLʱ���Ը�����
	 * @param status ״̬��NULLʱ���Ը�����
	 * @return ����������task����
	 * @since 2.0.2
	 */
	List<Task> findTasks(RFSItemable object, Integer type, Integer visibility, Byte status);

	/**
	 * 
	 * ����Task������ҵ������ϵ��
	 * ������RFSEntityObjectToTask�У����ⲿ͸����
	 * 
	 * @param task
	 * @param objects
	 */
	Task updateTaskEntityObjects(Task task, RFSEntityObject... objects);

	/**
	 * ����
	 * @param task
	 * @param happenTime
	 */
	void withdrawTask(Task task, Date endTime);

	/**
	 * ����
	 * @param task
	 * @param happenTime
	 */
	void rejectTask(Task task, Date endTime);

	/**
	 * ����
	 * @param task
	 * @param happenTime
	 */
	void undoTask(Task task, Date endTime);
	
	/**
	 * ת��
	 * @param task
	 * @param endTime
	 */
	void transferTask(Task task, Date endTime);
	
	/**
	 * ��������ѯ
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
	 * ��ָ��ʱ�����task��timeUsedֵ����������¡�
	 * 
	 * @param task
	 * @param calculateTime
	 * @return ������timeUsed������ֵ������ʱΪ0
	 */
	int calculateTaskTimeUsed(Task task, Date calculateTime);
	
	/**
	 * ��ָ��ʱ����������������е�task��timeUsedֵ����������¡�
	 * 
	 * @param calculateTime
	 * @return һ�������task����
	 */
	int calculateAllRunningTasksTimeUsed(Date calculateTime);
	
	/**
	 * ��ָ��ʱ����������������е�task��timeUsedֵ����������¡�
	 * 
	 * @param calculateTime
	 * @return һ�������task����
	 */
	int calculateAllRunningTasksTimeUsedInThreads(Date calculateTime);
	
	/**
	 * ��ȡ���һ�μ���task��timeUsedֵ��ʱ�䡣
	 * 
	 * @return ���һ�μ���task��timeUsed��ʱ��
	 * @see #calculateAllRunningTasksTimeUsed(Date)
	 */
	Date getLastCalculateAllRunningTasksTimeUsedTime();
	
	/**
	 * �������һ�μ���ʱ��Ϊ null���Ա����ٴμ��㡣
	 * �������һ�μ����������ͬһ�첻��μ��㣬Ϊ����ͬһ����μ��㣬����Ҫ������
	 * ����������ʱ���ÿա�
	 */
	void resetLastCalculateAllRunningTasksTimeUsedTime();
	
	/****
	 *  ���� task �����ˣ������˵�λ���������ܣ��ֹ��쵼
	 * @param taskSN 
	 * @param dutyClerk 		 ������
	 * @param dutyerLeader1 	 ��������
	 * @param dutyerLeader2		 �ֹ��쵼
	 * @deprecated using {@link #updateTaskDutyersInfo(long, Clerk, Clerk, Clerk)}
	 */
	void updateTaskDutyClerk(Long taskSN, Clerk dutyClerk, Clerk dutyerLeader1, Clerk dutyerLeader2);
	
	
	/****
	 *  ���� task �����ˣ������˵�λ���������ܣ��ֹ��쵼
	 * @param task 
	 * @param dutyClerk 		 ������
	 * @param dutyerLeader1 	 ��������
	 * @param dutyerLeader2		 �ֹ��쵼
	 * @deprecated using {@link #updateTaskDutyersInfo(Task, Clerk, Clerk, Clerk)}
	 */
	void updateTaskDutyClerk(Task task, Clerk dutyClerk, Clerk dutyerLeader1, Clerk dutyerLeader2);
	
	/**
	 * ���� task �����ˣ������˵�λ���������ܣ��ֹ��쵼
	 * @param taskSN
	 * @param dutyersInfo
	 * @return
	 */
	Task updateTaskDutyersInfo(long taskSN, DutyersInfo dutyersInfo);
	
	/**
	 * ���� task �����ˣ������˵�λ���������ܣ��ֹ��쵼
	 * @param task
	 * @param dutyersInfo
	 * @return
	 */
	Task updateTaskDutyersInfo(Task task, DutyersInfo dutyersInfo);
	
	/**
	 * ���� task �����ˣ������˵�λ���������ܣ��ֹ��쵼
	 * @param taskSN
	 * @param dutyer
	 * @param dutyerLeader1
	 * @param dutyerLeader2
	 * @return
	 */
	Task updateTaskDutyersInfo(long taskSN, Clerk dutyer, Clerk dutyerLeader1, Clerk dutyerLeader2);
	
	/**
	 * ���� task �����ˣ������˵�λ���������ܣ��ֹ��쵼
	 * @param task
	 * @param dutyer
	 * @param dutyerLeader1
	 * @param dutyerLeader2
	 * @return
	 */
	Task updateTaskDutyersInfo(Task task, Clerk dutyer, Clerk dutyerLeader1, Clerk dutyerLeader2);
	
	/**
	 * ���ݶ��������˵ı仯����Ҫ����task����������Ϣ�������ر����µ�task��������
	 * ���������˱��ʱӦ�õ�����������������˼��������
	 * @param object
	 * @return �����µ�ҵ������
	 */
	int updateTasksDutyersInfoByRefObject(RFSObject object);
	
	/**
	 * @see #taskDelete(Task)
	 * @param taskSN
	 */
	void taskDelete(Long taskSN);
	
	/**
	 * ɾ��task����������
	 * @param task
	 */
	void taskDelete(Task task);
	
	
	void taskInvalid(RFSObject rfsObject);
}
