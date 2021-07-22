package org.opoo.ndao.hibernate3;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.Domain;
import org.opoo.ndao.Selector;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.Aggregation;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.util.Assert;


/**
 * 使用 Hibernate 实现的通用查询器。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class HibernateSelector extends HibernateDaoSupport implements Selector {
	private static final Log log = LogFactory.getLog(HibernateSelector.class);

	@SuppressWarnings("unchecked")
	public <T extends Domain<K>, K extends Serializable> List<T> find(Class<T> clazz) {
		//return getHibernateTemplate().find("from " + clazz.getName());
		return getHibernateTemplate().loadAll(clazz);
	}

	public <T extends Domain<K>, K extends Serializable> List<T> find(Class<T> clazz, ResultFilter filter) {
		return getQuerySupport().find("from " + clazz.getName(), filter);
	}

	public <T extends Domain<K>, K extends Serializable> List<Map<String, Object>> find(Class<T> clazz,
			ResultFilter filter, Aggregation aggregation) {
		if(aggregation == null){
			throw new IllegalArgumentException("aggregation is required.");
		}
		Criterion c = filter != null ? filter.getCriterion() : null;
		Order o = filter != null ? filter.getOrder() : null;
		String g = aggregation.getGroupFieldsString();
		
		if(!ArrayUtils.isEmpty(aggregation.getGroupFields())){
			Logic logic = null;//Restrictions.logic(c);
			if(c != null){
				logic = Restrictions.logic(c);
			}
//			if(c == null){
//				c = Restrictions.sql("1=1");
//			}
			
			for(String f: aggregation.getGroupFields()){
				if(logic == null){
					logic = Restrictions.logic(Restrictions.isNotNull(f));
				}else{
					logic.and(Restrictions.isNotNull(f));
				}
			}
			c = logic;
		}
		
		
		final StringBuffer ql = new StringBuffer();
		ql.append("select new map(").append(aggregation.toString()).append(")")
			.append(" from ").append(clazz.getName());
		if(c != null){
			ql.append(" where ").append(c.toString());
		}
		if(g != null){
			ql.append(" group by ").append(g);
		}
		if(o != null){
			ql.append(" order by ").append(o.toString());
		}else if(g != null){
			ql.append(" order by ").append(g);
		}
		
		if(c != null && c.getValues() != null && c.getValues().length > 0){
			return getQuerySupport().find(ql.toString(), c.getValues());
		}else{
			return getQuerySupport().find(ql.toString());
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends Domain<K>, K extends Serializable> List<K> findIds(Class<T> clazz, String idPropertyName, ResultFilter filter) {
		if(idPropertyName == null || "".equals(idPropertyName.trim())){
			idPropertyName = "id";
		}
		String hql = "select " + idPropertyName + " from " + clazz.getName();
		if(log.isDebugEnabled()){
			log.debug("查询主键集合：" + hql);
		}
		if(filter != null){
			return getQuerySupport().find(hql, filter);
		}else{
			return getHibernateTemplate().find(hql);
		}
	}

	public <T extends Domain<K>, K extends Serializable> PageableList<T> findPageableList(Class<T> clazz,
			ResultFilter filter) {
		Assert.isTrue(filter.isPageable(), "必须包含分页参数");
		List<T> list = find(clazz, filter);
		int count = getCount(clazz, filter);
		return new PageableList<T>(list, filter.getFirstResult(), filter.getMaxResults(), count);
	}

	@SuppressWarnings("unchecked")
	public <T extends Domain<K>, K extends Serializable> T get(Class<T> clazz, K id) {
		return (T) getHibernateTemplate().get(clazz, id);
	}

	public <T extends Domain<K>, K extends Serializable> T get(Class<T> clazz, Criterion criterion) {
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		rf.setCriterion(criterion);
		List<T> list = find(clazz, rf);
		if(list != null && !list.isEmpty()){
			if(list.size() > 1){
				log.warn("查询结果不唯一，只返回第一条数据。");
			}
			return list.get(0);
		}
		return null;
	}

	public <T extends Domain<K>, K extends Serializable> int getCount(Class<T> clazz) {
		return getCount(clazz, ResultFilter.createEmptyResultFilter());
	}

	public <T extends Domain<K>, K extends Serializable> int getCount(Class<T> clazz, ResultFilter filter) {
		Criterion c = filter != null ? filter.getCriterion() : null;
		return getQuerySupport().getInt("select count(*) from " + clazz.getName(), c);
	}
}
