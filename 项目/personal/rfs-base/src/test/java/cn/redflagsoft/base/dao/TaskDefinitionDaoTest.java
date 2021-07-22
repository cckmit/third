package cn.redflagsoft.base.dao;

import java.util.Map;

import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.TaskDefinition;
import cn.redflagsoft.base.test.BaseTests;

public class TaskDefinitionDaoTest extends BaseTests {

	protected TaskDefinitionDao taskDefinitionDao;

	public void testFind() {
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		SimpleExpression eq = Restrictions.eq("parentTaskType", ((short) 425));
		filter.setCriterion(eq);
		filter.setOrder(Order.desc("creationTime"));

		Map<Integer, TaskDefinition> temp = taskDefinitionDao
				.findTaskDefinitionsMap(filter);

		for (Map.Entry<?, ?> en : temp.entrySet()) {
			System.out.println(en.getKey());
		}
		assertNotNull(temp);
	}

	public void testFind2() {
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		SimpleExpression eq = Restrictions.eq("parentTaskType", ((short) 425));
		filter.setCriterion(eq);
		filter.setOrder(Order.desc("creationTime"));

		Map<Integer, String> map = taskDefinitionDao
				.findSimpleTaskDefinitionsMap(filter);

		for (Map.Entry<?, ?> en : map.entrySet()) {
			System.out.println(en.getKey() + "----------" + en.getValue());
		}
		assertNotNull(map);
	}
}
