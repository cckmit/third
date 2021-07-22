package cn.redflagsoft.base.dao.hibernate3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.TaskDefinition;
import cn.redflagsoft.base.dao.TaskDefinitionDao;

public class TaskDefinitionHibernateDao extends
		AbstractBaseHibernateDao<TaskDefinition, Integer> implements
		TaskDefinitionDao {

	public Map<Integer, String> findSimpleTaskDefinitionsMap(ResultFilter filter) {
		Map<Integer, String> map = new HashMap<Integer, String>();

		List<TaskDefinition> list = this.find(filter);

		if (!list.isEmpty()) {
			for (TaskDefinition taskDefinition : list) {
				map.put(taskDefinition.getId(), taskDefinition.getName());
			}
		}
		return map;
	}

	public Map<Integer, TaskDefinition> findTaskDefinitionsMap(ResultFilter filter) {
		Map<Integer, TaskDefinition> map = new HashMap<Integer, TaskDefinition>();

		List<TaskDefinition> list = this.find(filter);
		if (!list.isEmpty()) {
			for (TaskDefinition taskDefinition : list) {
				map.put(taskDefinition.getId(), taskDefinition);
			}
		}
		return map;
	}
}
