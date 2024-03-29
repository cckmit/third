/*
 * $Id: StageTaskDetailsService.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.StageTaskDetails;
import cn.redflagsoft.base.scheme.WorkScheme;

public interface StageTaskDetailsService {

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
	 * 保存一条阶段详情
	 */
	StageTaskDetails saveStageTaskDetails(StageTaskDetails stageTaskDetails);
	
	/**
	 * 更新一条阶段详情
	 */
	StageTaskDetails updateStageTaskDetails(StageTaskDetails stageTaskDetails);
	
	
	/**
	 * 查找对象相关的所有StageTaskDetails。
	 * 
	 * @param objectId
	 * @param objectType
	 * @return
	 */
	List<StageTaskDetails> findStageTaskDetails(long objectId, int objectType);
	
	
	/**
	 * 为指定的 WorkScheme 构建 StageTaskDetails 信息。如果有则查询，没有则创建新的。
	 * 
	 * @param ws
	 * @param stageTaskType
	 * @param stageTaskName
	 * @return
	 */
	SavableStageTaskDetails buildStageTaskDetails(WorkScheme ws, int stageTaskType, String stageTaskName);
	/**
	 * 为指定的 WorkScheme 构建 StageTaskDetails 信息。如果有则查询，没有则创建新的。
	 * 
	 * @param ws WorkScheme
	 * @param stageTaskType 阶段的taskType
	 * @return
	 */
	SavableStageTaskDetails buildStageTaskDetails(WorkScheme ws, int stageTaskType);
	
	/**
	 * 为指定的 WorkScheme 构建 StageTaskDetails 信息。如果有则查询，没有则创建新的。
	 * 阶段的taskType根据当前的WorkScheme的的taskDefinition的parentTaskType来确定。
	 * 
	 * @param ws WorkScheme
	 * @return
	 */
	SavableStageTaskDetails buildStageTaskDetails(WorkScheme ws);
	
	/**
	 * 想找满足filter条件的对象相关所有StageTaskDetails.
	 * 
	 * @param filter
	 * @return
	 */
	List<StageTaskDetails> findStageTaskDetails(ResultFilter filter);
	
	
	/**
	 * 用实际时间同步所有对应的预计时间。
	 * 
	 * @return 被更新的对象数量
	 */
	int updateAllExpectedTimes();
	
	/**
	 * 更新（预计）编制开始时间到（预计）开始时间。
	 * @return
	 */
	int updateBzStartTimeToStartTime();
	
	/**
	 * 更新（预计）批复时间到（预计）结束时间。
	 * @return
	 */
	int updatePfTimeToFinishTime();
}
