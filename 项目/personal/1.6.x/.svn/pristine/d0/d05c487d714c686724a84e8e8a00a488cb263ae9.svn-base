package org.opoo.ndao.hibernate3;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.support.QueryHelper;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.util.ArrayUtils;
import org.opoo.util.Assert;

/**
* Hibernate查询辅助类。
* 
* @author Alex Lin(alex@opoo.org)
* @version 1.0
*/
public abstract class HibernateQueryHelper {


	/**
	 * 创建一个 Hibernate 查询。
	 * @param session hibernate session
	 * @param baseSql 查询语句HQL
	 * @param filter 结果过滤器
	 * @return Hibernate 查询
	 */
	public static Query createQuery(Session session, String baseSql, ResultFilter filter){
		return createQuery(session, baseSql, filter, false);
	}
	/**
	 * 创建一个 Hibernate 查询。
	 * @param session hibernate session
	 * @param baseSql 查询语句HQL
	 * @param filter 结果过滤器
	 * @param sqlQuery 是否是原始的 SQL 查询
	 * @return Hibernate 查询
	 */
	public static Query createQuery(Session session, String baseSql, ResultFilter filter, boolean sqlQuery){
		if(filter == null){
			return createQuery(session, baseSql, sqlQuery);
		}else{
			Query q = createQuery(session, baseSql, filter.getCriterion(), filter.getOrder(), sqlQuery);
			if(filter.isPageable()){
				q.setFirstResult(filter.getFirstResult());
				q.setMaxResults(filter.getMaxResults());
			}
			return q;
		}
	}
	
	/**
	 * 创建 Hibernate 查询。
	 * @param session hibernate session
	 * @param baseSql 查询语句
	 * @param c 查询条件
	 * @param o 排序对象
	 * @return hibernate 查询
	 */
	public static Query createQuery(Session session,
			  String baseSql,
			  Criterion c, Order o){
		return createQuery(session, baseSql, c, o, false);
	}
	
	/**
	 * 创建 Hibernate 查询。
	 * @param session
	 * @param baseSql
	 * @param c
	 * @param o
	 * @param sqlQuery
	 * @return
	 */
	public static Query createQuery(Session session, String baseSql, Criterion c, Order o, boolean sqlQuery) {
		String qs = QueryHelper.buildQueryString(baseSql, c, o);

		Query q = createQuery(session, qs, sqlQuery);

		if (c != null) {
			Object[] values = c.getValues();
			// Type[] types = c.getTypes();
			if (!ArrayUtils.isEmpty(values)) {
				// q.setParameters(values, types);
				for (int i = 0; i < values.length; i++) {
					// q.setParameter(i, values[i], fromOqsType(types[i]));
					q.setParameter(i, values[i]);
				}
			}
		}
		return q;
	}

	/**
	 * 创建 Hibernate 查询。
	 * @param session
	 * @param baseSql
	 * @param c
	 * @return
	 */
	public static Query createQuery(Session session, String baseSql, Criterion c) {
		return createQuery(session, baseSql, c, null);
	}

	/**
	 * 创建 Hibernate 查询。
	 * @param session
	 * @param baseSql
	 * @param c
	 * @param sqlQuery
	 * @return
	 */
	public static Query createQuery(Session session, String baseSql, Criterion c, boolean sqlQuery) {
		return createQuery(session, baseSql, c, null, sqlQuery);
	}

	/**
	 * 创建 Hibernate 查询。
	 * @param session
	 * @param sql
	 * @param names
	 * @param values
	 * @return
	 */
	public static Query createQuery(Session session, String sql, String[] names, Object[] values) {
		return createQuery(session, sql, names, values, false);
	}

	/**
	 * 创建 Hibernate 查询。
	 * @param session
	 * @param sql
	 * @param names
	 * @param values
	 * @param sqlQuery
	 * @return
	 */
	public static Query createQuery(Session session, String sql, String[] names, Object[] values, boolean sqlQuery) {
		// Query q = session.createQuery(sql);
		Query q = createQuery(session, sql, sqlQuery);
		if (names != null) {
			Assert.isTrue(names.length == values.length);
			for (int i = 0; i < names.length; i++) {
				Object obj = values[i];
				if (obj.getClass().isArray()) {
					q.setParameterList(names[i], (Object[]) obj);
				} else if (obj instanceof Collection) {
					q.setParameterList(names[i], (Collection<?>) obj);
				} else {
					q.setParameter(names[i], obj);
				}
			}
		}
		return q;
	}

	/**
	 * 创建 Hibernate 查询。
	 * @param session
	 * @param sql
	 * @param name
	 * @param value
	 * @return
	 */
	public static Query createQuery(Session session, String sql, String name, Object value) {
		return createQuery(session, sql, new String[] { name }, new Object[] { value });
	}

	/**
	 * 创建 Hibernate 查询。
	 * @param session
	 * @param sql
	 * @param name
	 * @param value
	 * @param sqlQuery
	 * @return
	 */
	public static Query createQuery(Session session, String sql, String name, Object value, boolean sqlQuery) {
		return createQuery(session, sql, new String[] { name }, new Object[] { value }, sqlQuery);
	}
	
	/**
	 * 创建 Hibernate 查询。
	 * @param session
	 * @param sql
	 * @param name
	 * @param values
	 * @return
	 */
	public static Query createQuery(Session session, String sql, String name, Object[] values) {
		return createQuery(session, sql, name, values, false);
	}
	/**
	 * 创建 Hibernate 查询。
	 * @param session
	 * @param sql
	 * @param name
	 * @param values
	 * @param sqlQuery
	 * @return
	 */
	public static Query createQuery(Session session, String sql, String name, Object[] values, boolean sqlQuery) {
		// Query q = session.createQuery(sql);
		Query q = createQuery(session, sql, sqlQuery);
		q.setParameterList(name, values);
		return q;
	}
	/**
	 * 创建 Hibernate 查询。
	 * @param session
	 * @param sql
	 * @return
	 */
	public static Query createQuery(Session session, String sql) {
		return createQuery(session, sql, false);
	}

	/**
	 * 创建 Hibernate 查询。
	 * @param session
	 * @param sql
	 * @param sqlQuery
	 * @return
	 */
	public static Query createQuery(Session session, String sql, boolean sqlQuery) {
		// return session.createQuery(sql);
		return sqlQuery ? session.createSQLQuery(sql) : session.createQuery(sql);
	}

	/**
	 * 创建 Hibernate 查询。
	 * @param session
	 * @param sql
	 * @param value
	 * @return
	 */
	public static Query createQuery(Session session, String sql, Object value) {
		return createQuery(session, sql, new Object[] { value });
	}

	/**
	 * 创建 Hibernate 查询。
	 * @param session
	 * @param sql
	 * @param value
	 * @param sqlQuery
	 * @return
	 */
	public static Query createQuery(Session session, String sql, Object value, boolean sqlQuery) {
		return createQuery(session, sql, new Object[] { value }, sqlQuery);
	}

	/**
	 * 创建 Hibernate 查询。
	 * @param session
	 * @param sql
	 * @param values
	 * @return
	 */
	public static Query createQuery(Session session, String sql, Object[] values) {
		return createQuery(session, sql, values, false);
	}

	/**
	 * 创建 Hibernate 查询。
	 * @param session
	 * @param sql
	 * @param values
	 * @param sqlQuery
	 * @return
	 */
	public static Query createQuery(Session session, String sql, Object[] values, boolean sqlQuery) {
		Query q = createQuery(session, sql, sqlQuery);// session.createQuery(sql);
		for (int i = 0; i < values.length; i++) {
			q.setParameter(i, values[i]);
		}
		return q;
	}
}
