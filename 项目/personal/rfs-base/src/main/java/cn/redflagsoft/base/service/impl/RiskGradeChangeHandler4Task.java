package cn.redflagsoft.base.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.service.RiskGradeChangeHandler;
import cn.redflagsoft.base.service.RiskRuleService;
import cn.redflagsoft.base.service.TaskService;

public class RiskGradeChangeHandler4Task implements RiskGradeChangeHandler {
	private static final Log log = LogFactory.getLog(RiskGradeChangeHandler4Task.class);
	private RiskRuleService riskRuleService;
	private TaskService taskService;
	

	public RiskRuleService getRiskRuleService() {
		return riskRuleService;
	}

	public void setRiskRuleService(RiskRuleService riskRuleService) {
		this.riskRuleService = riskRuleService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}


	public void gradeChange(Risk risk, byte oldGrade) {
		if (RiskRule.RULE_TYPE_监察 != risk.getRuleType()) {
			log.debug("不是监察，不处理");
			return;
		}
		
		RiskRule rule = getRiskRuleService().getRiskRule(risk.getRuleID());
		if (rule != null) {
			int ruleType = rule.getType();
			if (ruleType == RiskRule.TYPE_时限监察) {
				if (risk.getObjectType() == ObjectTypes.TASK) {
					Task task = taskService.getTask(risk.getObjectID());
					if (task != null) {
						task.setTimeLimitRiskGrade(risk.getGrade());
						taskService.updateTask(task);
					}
				}
			}
		}
	}
}
