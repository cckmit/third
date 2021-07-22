package org.opoo.apps.dao.hibernate3;

import java.util.List;

import org.opoo.apps.dao.QueryDao;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.hibernate3.HibernateDaoSupport;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;

public class QueryHibernateDao extends HibernateDaoSupport implements QueryDao {

	public <T> List<T> findByEntityName(String entityName, ResultFilter rf) {
		String ql = "select a from " + entityName + " a";
		return getQuerySupport().find(ql, rf);
	}

	public <T> PageableList<T> findPageableListByEntityName(String entityName,	ResultFilter rf) {
		int count = getCountByEntityName(entityName, rf);
		List<T> list = findByEntityName(entityName, rf);
		return new PageableList<T>(list, rf.getFirstResult(), rf.getMaxResults(), count);
	}

	public int getCountByEntityName(String entityName, ResultFilter rf) {
		 return getQuerySupport().getInt("select count(*) from " + entityName, rf.getCriterion());
	}

	public int getCountByEntityName(String entityName, Criterion c) {
		return getQuerySupport().getInt("select count(*) from " + entityName, c);
	}
	
}
