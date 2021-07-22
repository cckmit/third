package cn.redflagsoft.base.process.impl;

import java.util.Map;

import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.WorkProcessException;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.service.TaskService;


/**
 * 查询任务监控风险信息。
 * 
 * @author Alex Lin
 *
 */
@ProcessType(MonitorProcess.TYPE)
public class MonitorProcess extends AbstractWorkProcess {
	public static final int TYPE = 6020;
	private Long taskId;
	private TaskService taskService;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public Object execute(Map parameters, boolean needLog) throws WorkProcessException {
		return taskService.getTaskMonitorRiskInfo(taskId);
	}
}
