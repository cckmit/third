package cn.redflagsoft.base.dao;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.StageTaskDetails;

public interface StageTaskDetailsDao extends Dao<StageTaskDetails,Long> {
	/**
	 * 根据对象id、对象类型、阶段的taskType来查找唯一一条阶段详情信息。
	 * <p>
	 * 在使用select查询时返回的结果应该是0条或者1条，大于1条时抛出异常。
	 * @param objectId
	 * @param objectType
	 * @param taskType
	 * @return
	 */
	StageTaskDetails getStageTaskDetail(long objectId, int objectType, int taskType);
	
	/**
	 * 批量同步实际时间到预计时间。
	 * 
	 * @return 被同步的记录数。
	 */
	int updateAllActualTimesToExpectedTimes();
	
	/**
	 * 批量同步（预计）批复时间到（预计）结束时间。
	 * 
	 * @return 被同步的记录数。
	 */
	int updatePfTimeToFinishTime();
	
	/**
	 * 批量同步（预计）编制开始时间到（预计）开始时间。
	 * @return 被同步的记录数。
	 */
	int updateBzStartTimeToStartTime();
}
