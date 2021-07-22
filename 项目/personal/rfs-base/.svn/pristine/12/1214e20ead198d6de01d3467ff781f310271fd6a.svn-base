package cn.redflagsoft.base.scheme.schemes.bizdef;

import java.util.List;

import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.BizDutyersConfig;
import cn.redflagsoft.base.bean.TaskDefinition;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.service.BizDutyersConfigService;
import cn.redflagsoft.base.service.TaskDefinitionService;

/**
 * 
 * @author ZF
 * 三级责任人处理Scheme
 *
 */
public class BizDutyersConfigScheme extends AbstractScheme {
	private BizDutyersConfigService bizDutyersConfigService;
	private TaskDefinitionService taskDefinitionService;
	
	private BizDutyersConfig bizDutyersConfig;
	private List<Long> ids;
	private Long id;
	private int taskType;
	private TaskDefinition taskDef;
	
	
	public BizDutyersConfigService getBizDutyersConfigService() {
		return bizDutyersConfigService;
	}

	public void setBizDutyersConfigService(
			BizDutyersConfigService bizDutyersConfigService) {
		this.bizDutyersConfigService = bizDutyersConfigService;
	}

	public BizDutyersConfig getBizDutyersConfig() {
		return bizDutyersConfig;
	}

	public void setBizDutyersConfig(BizDutyersConfig bizDutyersConfig) {
		this.bizDutyersConfig = bizDutyersConfig;
	}
	

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public TaskDefinitionService getTaskDefinitionService() {
		return taskDefinitionService;
	}

	public void setTaskDefinitionService(TaskDefinitionService taskDefinitionService) {
		this.taskDefinitionService = taskDefinitionService;
	}

	public TaskDefinition getTaskDef() {
		return taskDef;
	}

	public void setTaskDef(TaskDefinition taskDef) {
		this.taskDef = taskDef;
	}
	
	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	/**
	 * 新增三级责任人规则
	 */
	public Object doScheme(){
		Assert.notNull(bizDutyersConfig, "BizDutyersConfig is required.");
		
		bizDutyersConfigService.saveBizDutyersConfig(bizDutyersConfig);
		
		return "业务办理成功！";
	}
	
	/**
	 * 删除三级责任人规则
	 * @return
	 */
	public Object doDeteleBizDutyersConfig(){
		Assert.notNull(ids, "Ids is required.");
		bizDutyersConfigService.deteleBizDutyersConfig(ids);
		
		return "业务办理成功！";
	}
	
	public Object doUpdateBizDutyerConfig(){
		Assert.notNull(bizDutyersConfig,  "BizDutyersConfig is required.");
		Assert.notNull(id,"id is required.");
		
		BizDutyersConfig dutyerConfig = bizDutyersConfigService.getBizDutyersConfig(id);
		Assert.notNull(dutyerConfig, "DutyersConfig is required.");
		
		dutyerConfig.setDutyEntityID(bizDutyersConfig.getDutyEntityID());
		dutyerConfig.setDutyEntityName(bizDutyersConfig.getDutyEntityName());
		dutyerConfig.setDutyerID(bizDutyersConfig.getDutyerID());
		dutyerConfig.setDutyerName(bizDutyersConfig.getDutyerName());
		dutyerConfig.setDutyerLeader1Id(bizDutyersConfig.getDutyerLeader1Id());
		dutyerConfig.setDutyerLeader1Name(bizDutyersConfig.getDutyerLeader1Name());
		dutyerConfig.setDutyerLeader2Id(bizDutyersConfig.getDutyerLeader2Id());
		dutyerConfig.setDutyerLeader2Name(bizDutyersConfig.getDutyerLeader2Name());
		dutyerConfig.setDisplayOrder(bizDutyersConfig.getDisplayOrder());
		dutyerConfig.setRemark(bizDutyersConfig.getRemark());
		
		
		
		bizDutyersConfigService.updateBizDutyersConfig(dutyerConfig);
		
		return "业务办理成功！";
	}
	
	/**
	 * TaskDef修改
	 * @return
	 */
	public Object doUpdateTaskDef(){
		Assert.notNull(taskDef, "taskDef is required.");
		Assert.notNull(taskType, "taskType is required.");
		
		TaskDefinition definition = taskDefinitionService.getTaskDefinition(taskType);
		definition.setName(taskDef.getName());
		definition.setDutyerType(taskDef.getDutyerType());
		definition.setSummaryTemplate(taskDef.getSummaryTemplate());
		definition.setRemark(taskDef.getRemark());
		
		taskDefinitionService.updateTaskDefinition(definition);
		
		return "业务办理成功！";
	}
}
