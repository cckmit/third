/*
 * $Id: StageTaskDetailsServiceImpl.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.StageTaskDetails;
import cn.redflagsoft.base.bean.TaskDefinition;
import cn.redflagsoft.base.dao.StageTaskDetailsDao;
import cn.redflagsoft.base.scheme.WorkScheme;
import cn.redflagsoft.base.service.SavableStageTaskDetails;
import cn.redflagsoft.base.service.StageTaskDetailsService;
import cn.redflagsoft.base.service.TaskDefinitionService;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class StageTaskDetailsServiceImpl implements StageTaskDetailsService {
	static final Log log = LogFactory.getLog(StageTaskDetailsServiceImpl.class);

	private TaskDefinitionService taskDefinitionService;
	private StageTaskDetailsDao stageTaskDetailsDao; 
	
	public StageTaskDetailsDao getStageTaskDetailsDao() {
		return stageTaskDetailsDao;
	}

	public void setStageTaskDetailsDao(StageTaskDetailsDao stageTaskDetailsDao) {
		this.stageTaskDetailsDao = stageTaskDetailsDao;
	}
	
	

	public TaskDefinitionService getTaskDefinitionService() {
		return taskDefinitionService;
	}

	public void setTaskDefinitionService(TaskDefinitionService taskDefinitionService) {
		this.taskDefinitionService = taskDefinitionService;
	}

	public StageTaskDetails getStageTaskDetail(long objectId, int objectType,
			int taskType) {
		return stageTaskDetailsDao.getStageTaskDetail(objectId, objectType, taskType);
	}

	public StageTaskDetails saveStageTaskDetails(
			StageTaskDetails stageTaskDetails) {
		stageTaskDetails.copyAllActualTimesToExpextedTimes();
		return stageTaskDetailsDao.save(stageTaskDetails);
	}

	public StageTaskDetails updateStageTaskDetails(
			StageTaskDetails stageTaskDetails) {
		stageTaskDetails.copyAllActualTimesToExpextedTimes();
		return stageTaskDetailsDao.update(stageTaskDetails);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.StageTaskDetailsService#findStageTaskDetails(long, short)
	 */
	public List<StageTaskDetails> findStageTaskDetails(long objectId, int objectType) {
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(Restrictions.logic(Restrictions.eq("objectId", objectId)).and(
				Restrictions.eq("objectType", objectType)));
		return stageTaskDetailsDao.find(filter);
	}
	
	public List<StageTaskDetails> findStageTaskDetails(ResultFilter filter){
		return stageTaskDetailsDao.find(filter);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.StageTaskDetailsService#buildStageTaskDetails(cn.redflagsoft.base.scheme.WorkScheme, short)
	 */
	public SavableStageTaskDetails buildStageTaskDetails(WorkScheme ws, int stageTaskType) {
		return buildStageTaskDetails(ws, stageTaskType, null);
	}
	
	
	public SavableStageTaskDetails buildStageTaskDetails(WorkScheme ws, int stageTaskType, String stageTaskName) {
		RFSObject object = ws.getObject();
		Long objectId = object.getId();
		int objectType = object.getObjectType();
		
		StageTaskDetails details = getStageTaskDetail(objectId, object.getObjectType(), stageTaskType);
		SavableStageTaskDetails d = new SavableStageTaskDetailsImpl(details, this);
		d.setObjectId(objectId);
		d.setObjectType(objectType);
		d.setTaskType(stageTaskType);
		d.setObjectName(object.getName());
		
		
		d.setWorkItemName(stageTaskName);
		if(StringUtils.isBlank(stageTaskName)){
			TaskDefinition tdf = taskDefinitionService.getTaskDefinition(stageTaskType);
	//		details.setTaskType(tdf.getParentTaskType());
			if(tdf == null){
				throw new IllegalStateException("阶段taskType未定义：" + stageTaskType);
			}
			d.setWorkItemName(tdf.getName());
		}
		
		String tmp = d.getTaskSNs();
		String s = StringUtils.isNotBlank(tmp) ?  tmp + ":" : "";
		s += ws.getTask().getSn();
		d.setTaskSNs(s);
		
		tmp = d.getWorkSNs();
		s = StringUtils.isNotBlank(tmp) ? tmp + ":" : "";
		s += ws.getWork().getSn();
		d.setWorkSNs(s);
		
		return d;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.StageTaskDetailsService#buildStageTaskDetails(cn.redflagsoft.base.scheme.WorkScheme)
	 */
	public SavableStageTaskDetails buildStageTaskDetails(WorkScheme ws) {
		TaskDefinition tdf = taskDefinitionService.getTaskDefinition(ws.getTaskType());
		if(tdf == null){
			throw new IllegalStateException("TaskDefinition 未定义：" + ws.getTaskType());
		}
		Integer stageTaskType = tdf.getParentTaskType();
		if(stageTaskType == null){
			//throw new IllegalStateException("对应的阶段的taskType为空，请检查配置：" + ws.getTaskType());
			log.warn("对应的阶段的 taskType 为空，将无法生成该阶段的StageTask信息，请检查配置：" + ws.getTaskType());
			return new SavableStageTaskDetailsImpl();
		}else{
			return buildStageTaskDetails(ws, stageTaskType);
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.StageTaskDetailsService#updateAllExpectedTimes()
	 */
	public int updateAllExpectedTimes() {
		return getStageTaskDetailsDao().updateAllActualTimesToExpectedTimes();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.StageTaskDetailsService#updateBzStartTimeToStartTime()
	 */
	public int updateBzStartTimeToStartTime() {
		return getStageTaskDetailsDao().updateBzStartTimeToStartTime();
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.StageTaskDetailsService#updatePfTimeToFinishTime()
	 */
	public int updatePfTimeToFinishTime() {
		return getStageTaskDetailsDao().updatePfTimeToFinishTime();
	}
}
