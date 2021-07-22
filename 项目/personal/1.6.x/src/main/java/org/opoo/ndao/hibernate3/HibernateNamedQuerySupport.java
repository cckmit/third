package org.opoo.ndao.hibernate3;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.engine.NamedQueryDefinition;
import org.hibernate.impl.SessionFactoryImpl;
import org.opoo.ndao.DataAccessException;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.support.NamedQuerySupport;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 2.0
 */
public class HibernateNamedQuerySupport implements NamedQuerySupport {
	private static final Log log = LogFactory.getLog(HibernateNamedQuerySupport.class);
	private HibernateQuerySupport support;
	private SessionFactoryImpl sessionFactory;

    public HibernateNamedQuerySupport(HibernateQuerySupport support) {
    	this.support = support;
        SessionFactory factory = support.getTemplate().getSessionFactory();
        if(factory instanceof SessionFactoryImpl){
        	sessionFactory = (SessionFactoryImpl) factory;
		} else {
			// warn
			log.warn(String.format("Hibernate SessionFactory '%s' is not an instance of 'SessionFactoryImpl', will not process 'NamedQuery'.",
									factory.getClass().getName()));
		}
    }
    /**
     * 
     * @param name
     * @return
     */
    public NamedQueryDefinition getNamedQuery(String name){
    	if(sessionFactory == null){
    		return null;
    	}
    	return sessionFactory.getNamedQuery(name);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public String getNamedQueryString(String name){
    	NamedQueryDefinition query = getNamedQuery(name);
    	return query != null ? query.getQueryString() : null;
    }
    
    protected String getEffectiveNamedQueryString(String name){
    	String query = getNamedQueryString(name);
    	if(query == null){
    		throw new DataAccessException("Named query not known: " + name);
    	}
    	return query;
    }
    
    
    /**
     * 
     * @param name
     * @return
     */
    public boolean hasNamedQuery(String name){
    	return getNamedQuery(name) != null;
    }
    
    
	public int executeUpdate(String baseSqlName, Criterion c) {
		String baseSql = getEffectiveNamedQueryString(baseSqlName);
		return support.executeUpdate(baseSql, c);
	}

	public <T> List<T> find(String baseSqlName, ResultFilter resultFilter) {
		String baseSql = getEffectiveNamedQueryString(baseSqlName);
		return support.find(baseSql, resultFilter);
	}

	public <T> PageableList<T> find(String baseSelectSqlName, String baseCountSqlName, ResultFilter resultFilter) {
		String baseSql = getEffectiveNamedQueryString(baseSelectSqlName);
		String countSql = getEffectiveNamedQueryString(baseCountSqlName);
		return support.find(baseSql, countSql, resultFilter);
	}

	public int getInt(String baseSqlName, Criterion c) {
		String baseSql = getEffectiveNamedQueryString(baseSqlName);
		return support.getInt(baseSql, c);
	}
	
	public HibernateQuerySupport getQuerySupport() {
		return support;
	}
}
