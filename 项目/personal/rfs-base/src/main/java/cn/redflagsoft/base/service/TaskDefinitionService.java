package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.TaskDefinition;

/**
 * Task定义服务接口。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface TaskDefinitionService extends TaskDefProvider {

	TaskDefinition getTaskDefinition(int taskType);
	
	TaskDefinition updateTaskDefinition(TaskDefinition taskDefinition);
	
	void remove(int taskType);
	
	List<TaskDefinition> findTaskDefinitions(ResultFilter filter);
}
