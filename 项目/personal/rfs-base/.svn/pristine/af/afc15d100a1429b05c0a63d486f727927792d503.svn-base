package cn.redflagsoft.base.process.impl;

import java.util.Map;

import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.WorkProcessException;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.service.TaskService;

/**
 * 查询任务的业务概况。
 * 
 * @author Alex Lin
 *
 */
@ProcessType(TaskBusinessSurveyProcess.TYPE)
public class TaskBusinessSurveyProcess extends AbstractWorkProcess {
	public static final int TYPE = 7012;
	private TaskService taskService;
	private Long taskId;

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	/**
	 * 
	 * @see BizVO
	 */
	public Object execute(Map parameters, boolean needLog) throws WorkProcessException {
		return taskService.getTaskBusinessSurvey(taskId);
	}
}
