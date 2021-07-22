package cn.redflagsoft.base.dao;

import java.util.Map;

import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.WorkDefinition;
import cn.redflagsoft.base.test.BaseTests;

public class WorkDefinitionDaoTest extends BaseTests {

	protected WorkDefinitionDao workDefinitionDao;

	public void testFind() {
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		SimpleExpression eq = Restrictions.eq("taskType", ((short) 425));
		filter.setCriterion(eq);
		filter.setOrder(Order.desc("creationTime"));

		Map<Integer, WorkDefinition> temp = workDefinitionDao.findWorkDefinitionsMap(filter);

		for (Map.Entry<?, ?> en : temp.entrySet()) {
			System.out.println(en.getKey());
		}
		assertSame(temp, null);
	}

	public void testFind2() {
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		SimpleExpression eq = Restrictions.eq("taskType", ((short) 425));
		filter.setCriterion(eq);
		filter.setOrder(Order.desc("creationTime"));

		Map<Integer, String> map = workDefinitionDao.findSimpleWorkDefinitionsMap(filter);

		for (Map.Entry<?, ?> en : map.entrySet()) {
			System.out.println(en.getKey() + "----------" + en.getValue());
		}
		assertSame(map, null);
	}
}
