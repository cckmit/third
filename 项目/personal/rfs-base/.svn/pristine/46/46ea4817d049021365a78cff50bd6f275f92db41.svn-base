package cn.redflagsoft.base.dao.hibernate3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.WorkDefinition;
import cn.redflagsoft.base.dao.WorkDefinitionDao;

public class WorkDefinitionHibernateDao extends
		AbstractBaseHibernateDao<WorkDefinition, Integer> implements
		WorkDefinitionDao {

	public Map<Integer, String> findSimpleWorkDefinitionsMap(ResultFilter filter) {

		Map<Integer, String> map = new HashMap<Integer, String>();
		List<WorkDefinition> list = this.find(filter);
		if (!list.isEmpty()) {
			for (WorkDefinition workDef : list) {
				map.put(workDef.getId(), workDef.getName());
			}
		}
		return map;
	}

	public Map<Integer, WorkDefinition> findWorkDefinitionsMap(ResultFilter filter) {

		Map<Integer, WorkDefinition> map = new HashMap<Integer, WorkDefinition>();
		List<WorkDefinition> list = this.find(filter);
		if (!list.isEmpty()) {
			for (WorkDefinition workDef : list) {
				map.put(workDef.getId(), workDef);
			}
		}
		return map;
	}

}
