package org.opoo.ndao.hibernate3;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.QuerySupport;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.util.Assert;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;


/**
 * 与HQL语句查询相关的类。
 *
 * @author Alex
 *
 */
public class HibernateQuerySupport implements QuerySupport {
    private HibernateTemplate template;

    public HibernateQuerySupport(HibernateTemplate template) {
        this.template = template;
    }

    /**
     * 根据查询语句和条件更新数据。
     *
     * @param baseSql String 查询语句
     * @param c Criterion 查询条件
     * @return int
     */
    public int executeUpdate(final String baseSql, final Criterion c) {
        return ((Number) template.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                return Integer.valueOf(HibernateQueryHelper.createQuery(
                        session, baseSql, c).executeUpdate());
            }
        })).intValue();
    }

    /**
     * 查询数据。
     * 
     * @param baseSql String
     * @param resultFilter ResultFilter
     * @return List
     */
	@SuppressWarnings("unchecked")
	public <T> List<T> find(final String baseSql, final ResultFilter resultFilter) {
        return template.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                Query q = HibernateQueryHelper.createQuery(session, baseSql,
                        resultFilter.getCriterion(), resultFilter.getOrder());
                if (resultFilter.isPageable()) {
                    q.setFirstResult(resultFilter.getFirstResult());
                    q.setMaxResults(resultFilter.getMaxResults());
                }
                return q.list();
            }
        });
    }
    
    /**
     * 通过SQL语句查询数据。
     * 
     * @param baseSql
     * @param resultFilter
     * @return
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> findBySQL(final String baseSql, final ResultFilter resultFilter){
        return template.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException, HibernateException {
                Query q = HibernateQueryHelper.createQuery(session, baseSql, resultFilter, true);
                return q.list();
            }
        });
    }
    
    /**
     * 通过SQL语句查询数据。
     * @param baseSql
     * @param resultFilter
     * @param scalars
     * @return
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> findBySQL(final String baseSql, final ResultFilter resultFilter, final List<Scalar> scalars){
        return template.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException, HibernateException {
                SQLQuery q = (SQLQuery)HibernateQueryHelper.createQuery(session, baseSql, resultFilter, true);
                if(scalars != null){
                	for(Scalar scalar: scalars){
                		q.addScalar(scalar.getColumnAlias(), scalar.getType());
                	}
                }
                return q.list();
            }
        });
    }
    /**
     * 分页查询。
     * 
     * @param baseSelectSql String
     * @param baseCountSql String
     * @param resultFilter ResultFilter
     * @return PageableList
     */
    public <T> PageableList<T> find(String baseSelectSql, String baseCountSql, ResultFilter resultFilter) {
    	Assert.isTrue(resultFilter.isPageable(), "必须包含分页参数");
        List<T> l = find(baseSelectSql, resultFilter);
        int count = getInt(baseCountSql, resultFilter.getCriterion());
        return new PageableList<T>(l, resultFilter.getFirstResult(), resultFilter
                                .getMaxResults(), count);
    }
    
    /**
     * 通过SQL分页查询。
     * 
     * @param baseSelectSql
     * @param baseCountSql
     * @param resultFilter
     * @return
     */
    public <T> PageableList<T> findBySQL(String baseSelectSql, String baseCountSql, ResultFilter resultFilter) {
    	Assert.isTrue(resultFilter.isPageable(), "必须包含分页参数");
        List<T> l = findBySQL(baseSelectSql, resultFilter);
        int count = getIntBySQL(baseCountSql, resultFilter.getCriterion());
        return new PageableList<T>(l, resultFilter.getFirstResult(), resultFilter.getMaxResults(), count);
    }
    
    /**
     * 查询记录数。
     * 
     * @param baseSql String
     * @param c Criterion
     * @return int
     */
    public int getInt(final String baseSql, final Criterion c) {
        return ((Number) template.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                return (Number) HibernateQueryHelper.createQuery(session,
                        baseSql, c).uniqueResult();
            }
        })).intValue();
    }
    
    /**
     * 通过SQL查询记录数。
     * 
     * @param baseSql
     * @param c
     * @return
     */
    public int getIntBySQL(final String baseSql, final Criterion c) {
        return ((Number) template.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException, HibernateException {
                return (Number) HibernateQueryHelper.createQuery(session, baseSql, c, true).uniqueResult();
            }
        })).intValue();
    }
    /**
     * 查询数据。
     * @param queryString String
     * @return List
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> find(String queryString) {
        return template.find(queryString);
    }
    /**
     * 查询数据。
     * @param queryString String
     * @param value Object
     * @return List
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> find(String queryString, Object value) {
        return template.find(queryString, value);
    }
    /**
     * 查询数据。
     * 
     * @param queryString String
     * @param values Object[]
     * @return List
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> find(String queryString, Object[] values) {
        return template.find(queryString, values);
    }
    /**
     * 更新数据。
     * @param queryString String
     * @return int
     */
    public int executeUpdate(final String queryString) {
        return ((Integer) template.execute(new HibernateCallback() {
            public Object doInHibernate(Session s) throws HibernateException,
                    SQLException {
                int r2 = s.createQuery(queryString).executeUpdate();
                return new Integer(r2);
            }
        })).intValue();
    }
    /**
     * 更新数据。
     * 
     * @param queryString String
     * @param values Object[]
     * @return int
     */
    public int executeUpdate(final String queryString, final Object[] values) {
        return ((Integer) template.execute(new HibernateCallback() {
            public Object doInHibernate(Session s) throws HibernateException,
                    SQLException {
                int r2 = HibernateQueryHelper.createQuery(s, queryString,
                        values).executeUpdate();
                return new Integer(r2);
            }
        })).intValue();
    }
    
    /**
     * 更新数据。
     * @param queryString String
     * @param value Object
     * @return int
     */
    public int executeUpdate(final String queryString, final Object value) {
        return ((Integer) template.execute(new HibernateCallback() {
            public Object doInHibernate(Session s) throws HibernateException,
                    SQLException {
                int r2 = HibernateQueryHelper
                         .createQuery(s, queryString, value).executeUpdate();
                return new Integer(r2);
            }
        })).intValue();
    }
    /**
     * 查询数据。
     * 
     * @param queryString String
     * @param name String
     * @param value Object
     * @return List
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> find(final String queryString, final String name,
                     final Object value) {
        return template.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                return HibernateQueryHelper.createQuery(session, queryString,
                        name, value).list();
            }
        });
    }
    
    /**
     * 查询数据。
     * @param queryString
     * @param name
     * @param values
     * @return
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> find(final String queryString, final String name, final Object[] values) {
        return template.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                return HibernateQueryHelper.createQuery(session, queryString,
                        name, values).list();
            }
        });
    }
    /**
     * 查询数据。
     * 
     * @param queryString String
     * @param names String[]
     * @param values Object[]
     * @return List
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> find(final String queryString, final String[] names,
                     final Object[] values) {
        return template.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                return HibernateQueryHelper.createQuery(session, queryString,
                        names, values).list();
            }
        });
    }
    /**
     * 更新数据。
     * 
     * @param queryString String
     * @param names String[]
     * @param values Object[]
     * @return int
     */
    public int executeUpdate(final String queryString, final String[] names,
                             final Object[] values) {
        return ((Number) template.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                return new Integer(HibernateQueryHelper.createQuery(session,
                        queryString, names, values).executeUpdate());
            }
        })).intValue();
    }

    /**
     * 更新数据。
     * 
     * @param queryString String
     * @param name String
     * @param values Object[]
     * @return int
     */
    public int executeUpdate(final String queryString, final String name,
                             final Object[] values) {
        return ((Number) template.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                return new Integer(HibernateQueryHelper.createQuery(session,
                        queryString, name, values).executeUpdate());
            }
        })).intValue();
    }

    /**
     * 更新数据。
     * @param queryString String
     * @param name String
     * @param value Object
     * @return int
     */
    public int executeUpdate(final String queryString, final String name,
                             final Object value) {
        return ((Number) template.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                return new Integer(HibernateQueryHelper.createQuery(session,
                        queryString, name, value).executeUpdate());
            }
        })).intValue();
    }

	public HibernateTemplate getTemplate() {
		return template;
	}
}
