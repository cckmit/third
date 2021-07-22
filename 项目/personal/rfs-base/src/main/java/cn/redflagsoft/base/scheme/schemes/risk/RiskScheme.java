package cn.redflagsoft.base.scheme.schemes.risk;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.util.Assert;

import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskEntry;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.service.EntityObjectLoader;
import cn.redflagsoft.base.service.RiskRuleService;
import cn.redflagsoft.base.service.RiskService;
import cn.redflagsoft.base.service.TaskService;

public class RiskScheme extends AbstractScheme{
	private static final Log log = LogFactory.getLog(RiskScheme.class);
	private RiskService riskService;
	private TaskService taskService;
	private RiskRuleService riskRuleService;
	private EntityObjectLoader entityObjectLoader;
	
	private Long taskSN;
	private Risk risk;
	private Long riskID;
	private Date time;

	
	public RiskService getRiskService() {
		return riskService;
	}
	public void setRiskService(RiskService riskService) {
		this.riskService = riskService;
	}
	
	public RiskRuleService getRiskRuleService() {
		return riskRuleService;
	}
	public void setRiskRuleService(RiskRuleService riskRuleService) {
		this.riskRuleService = riskRuleService;
	}
	public EntityObjectLoader getEntityObjectLoader() {
		return entityObjectLoader;
	}
	public void setEntityObjectLoader(EntityObjectLoader entityObjectLoader) {
		this.entityObjectLoader = entityObjectLoader;
	}
	
	public Long getTaskSN() {
		return taskSN;
	}
	public void setTaskSN(Long taskSN) {
		this.taskSN = taskSN;
	}
	public Risk getRisk() {
		return risk;
	}
	public void setRisk(Risk risk) {
		this.risk = risk;
	}
	
	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	public TaskService getTaskService() {
		return taskService;
	}
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	
	public Long getRiskID() {
		return riskID;
	}
	public void setRiskID(Long riskID) {
		this.riskID = riskID;
	}
	/**
	 * 手动登记Task对象的监察信息
	 * @return Object
	 */
	public Object doTaskScheme(){
		Assert.notNull(risk, "业务对象不能为空！");
		Assert.notNull(taskSN, "taskSN不能为空！");
		
		Task task = taskService.getTask(taskSN);
		RiskRule rule = riskRuleService.getRiskRule(risk.getRuleID());
		RFSObject object = entityObjectLoader.getEntityObject(task.getRefObjectType(), task.getRefObjectId());

		RiskEntry entrie = riskService.createRisk(task, task.getDutyerID(), rule, object, null, risk.getCode(), risk.getBeginTime(), risk.getRemark());
		if(entrie != null){
			if(task.getRiskEntries() != null){
				task.addRiskEntry(entrie);
			}else{
				List<RiskEntry> list = new ArrayList<RiskEntry>();
				list.add(entrie);
				task.setRiskEntries(list);
			}
			taskService.updateTask(task);
		}else{
			log.error("监察创建不成功");
		}

		return "监察登记成功！";
	}
	
	public Object doHangRisk(){
		riskService.hangRisk(riskID, time);
		return "监察暂停成功！";
	}
	
	public Object doWakeRisk(){
		riskService.wakeRisk(riskID, time);
		return  "监察续办成功！";
	}
	
	public Object doTerminateRisk(){
		riskService.terminateRisk(riskID, time);
		return "监察结束成功！";
	}
}
