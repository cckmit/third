package cn.redflagsoft.base.process.impl;

import java.util.Map;

import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.WorkProcessException;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.service.HistoryRiskService;

@ProcessType(HistoryMonitorProcess.TYPE)
public class HistoryMonitorProcess extends AbstractWorkProcess {
	public static final int TYPE = 6021;
	private HistoryRiskService historyRiskService;
	private Long taskId;
	private Integer taskType;
	
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public HistoryRiskService getHistoryRiskService() {
		return historyRiskService;
	}

	public void setHistoryRiskService(HistoryRiskService historyRiskService) {
		this.historyRiskService = historyRiskService;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Object execute(Map parameters, boolean needLog) throws WorkProcessException {
		return historyRiskService.findHistoryRisk(taskId, taskType);
	}
}
