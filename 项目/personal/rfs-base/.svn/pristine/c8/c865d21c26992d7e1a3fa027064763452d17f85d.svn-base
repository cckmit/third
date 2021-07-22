package cn.redflagsoft.base.dao;

import java.util.List;

import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Task;

public interface TaskDao extends org.opoo.ndao.Dao<Task, Long>{
	
	/**
     * 查询指定objectId相关的task集合，不过滤可见性，并根据beginTime顺序排序
     * @param objectId
     * @return
     */
    List<Task> findTaskAllVisibilityByObjectID(Long objectId);
    
    
	/**
	 * 根据objectID和可见性（0或者100）查询Task集合，并根据beginTime顺序排序。
	 * 
	 * @param objectId
	 * @return
	 * @see #findTaskByObjectIDandVisibility(Long, int)
	 */
	List<Task> findTaskByObjectID(Long objectId);
    
	/**
	 * 根据objectID和可见性（0或者指定可见性）查询Task集合，并根据beginTime顺序排序。
	 * @param objectId
	 * @param visibility 
	 * @return
	 */
	List<Task> findTaskByObjectIDandVisibility(Long objectId,int visibility); 

	/**
	 * 根据category和BizTrackNodeInstance的nodeSn查询task集合。
	 * @param category
	 * @param nodeSn BizTrackNodeInstance的nodeSn
	 * @return
	 */
	List<Task> findTaskByNodeSn(byte category, Long nodeSn);
	
	/**
	 * 根据dutyerID和可见性（0或100），查询所有未结束（status<>9）的task集合，并按type属性排列。
	 * @param dutyerID
	 * @return
	 * @see #findTaskByDutyerIDandVisibility(Long, int)
	 * @deprecated using {@link #find(org.opoo.ndao.support.ResultFilter)}
	 */
	List<Task> findTaskByDutyerID(Long dutyerID);
	
	/**
	 * 根据dutyerID和可见性（0或指定可见性），查询所有未结束（status<>9）的task集合，并按type属性排列。
	 * 
	 * @deprecated using {@link #find(org.opoo.ndao.support.ResultFilter)}
	 * @param dutyerID
	 * @param visibility
	 * @return
	 */
	List<Task> findTaskByDutyerIDandVisibility(Long dutyerID, int visibility);
	
	/**
	 * 根据objectID、taskType和可见性（0或者100）查询Task集合，并并根据beginTime顺序排序。
	 * @param objectID
	 * @param taskType
	 * @return
	 * @see #findTaskByVisibility(Long, int, int)
	 */
	List<Task>  findTasks(Long objectID, int taskType);
	
	/**
	 * 根据objectID、taskType和可见性（0或者指定可见性）查询Task集合，并并根据beginTime顺序排序。
	 * 
	 * @param objectID
	 * @param taskType
	 * @param visibility
	 * @return
	 */
	List<Task> findTaskByVisibility(Long objectID, int taskType,int visibility);
	
	/**
	 * 更新当前task的activeWorkSN。
	 * 由于使用HQL语句更新，所以效率较高，但不能很好的处理缓存等，所以尽量不使用。
	 * @param sn
	 * @param activeWorkSN
	 * @deprecated using {@link #update(Task)}
	 */
	void updateActiveWorkSN(Long sn, Long activeWorkSN);
	
	/**
	 * 根据ClerkID和可见性（0或100）查询task集合，并根据type顺序排序。
	 * @param clerkID
	 * @return
	 * @deprecated using {@link #find(org.opoo.ndao.support.ResultFilter)}
	 */
	List<Task> findTaskByClerkID(Long clerkID);
	
	/**
	 * 根据ClerkID和可见性（0或指定可见性）查询task集合，并根据type顺序排序。
	 * @param dutyerID
	 * @param visibility
	 * @return
	 * @deprecated using {@link #find(org.opoo.ndao.support.ResultFilter)}
	 */
	List<Task> findTaskByClerkIDandVisibility(Long dutyerID,int visibility); 
	
	/**
	 * 根据dutyerID分组统计查询。
	 * 
	 * dutyerID,count(*) as countF,sum(timeused) as value,sum(timeLimit) as timeLimit
	 * @return
	 */
	List<Object[]> getBizCountGroupByDutyerID();
	
	/**
	 * 批量更新task的clerkID,但未更新task的clerkName。
	 * 
	 * @param ids task主键集合
	 * @param clerkID 要更新的clerkID
	 */
	void updateTaskClerkIdByTaskIds(final List<Long> ids,final Long clerkID);
	
	/**
	 * 根据objectID和可见性（0或者100或者指定可见性）查询Task集合，并并根据beginTime顺序排序。
	 * @param objectId
	 * @param visibility
	 * @return
	 */
	List<Task> findTaskByObjectIDandVisibilitys(Long objectId, int visibility);
	
	/**
	 * 根据ObjecID和taskType查询task集合，并根据beginTime顺序排序，不过滤可见性。
	 * 
	 * @param objectID
	 * @param taskType
	 * @return
	 */
	List<Task> findTaskAllVisibility(Long objectID, int taskType);
	
	/**
	 *  task 作废
	 * @param rfsObject
	 */
	void taskInvalid(RFSObject rfsObject);
}
