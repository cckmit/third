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
 * ��HQL����ѯ��ص��ࡣ
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
     * ���ݲ�ѯ���������������ݡ�
     *
     * @param baseSql String ��ѯ���
     * @param c Criterion ��ѯ����
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
     * ��ѯ���ݡ�
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
     * ͨ��SQL����ѯ���ݡ�
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
     * ͨ��SQL����ѯ���ݡ�
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
     * ��ҳ��ѯ��
     * 
     * @param baseSelectSql String
     * @param baseCountSql String
     * @param resultFilter ResultFilter
     * @return PageableList
     */
    public <T> PageableList<T> find(String baseSelectSql, String baseCountSql, ResultFilter resultFilter) {
    	Assert.isTrue(resultFilter.isPageable(), "���������ҳ����");
        List<T> l = find(baseSelectSql, resultFilter);
        int count = getInt(baseCountSql, resultFilter.getCriterion());
        return new PageableList<T>(l, resultFilter.getFirstResult(), resultFilter
                                .getMaxResults(), count);
    }
    
    /**
     * ͨ��SQL��ҳ��ѯ��
     * 
     * @param baseSelectSql
     * @param baseCountSql
     * @param resultFilter
     * @return
     */
    public <T> PageableList<T> findBySQL(String baseSelectSql, String baseCountSql, ResultFilter resultFilter) {
    	Assert.isTrue(resultFilter.isPageable(), "���������ҳ����");
        List<T> l = findBySQL(baseSelectSql, resultFilter);
        int count = getIntBySQL(baseCountSql, resultFilter.getCriterion());
        return new PageableList<T>(l, resultFilter.getFirstResult(), resultFilter.getMaxResults(), count);
    }
    
    /**
     * ��ѯ��¼����
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
     * ͨ��SQL��ѯ��¼����
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
     * ��ѯ���ݡ�
     * @param queryString String
     * @return List
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> find(String queryString) {
        return template.find(queryString);
    }
    /**
     * ��ѯ���ݡ�
     * @param queryString String
     * @param value Object
     * @return List
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> find(String queryString, Object value) {
        return template.find(queryString, value);
    }
    /**
     * ��ѯ���ݡ�
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
     * �������ݡ�
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
     * �������ݡ�
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
     * �������ݡ�
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
     * ��ѯ���ݡ�
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
     * ��ѯ���ݡ�
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
     * ��ѯ���ݡ�
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
     * �������ݡ�
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
     * �������ݡ�
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
     * �������ݡ�
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
