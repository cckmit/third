package org.opoo.ndao.hibernate3;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Hibernate Dao支持类。
 * 
 * @author Alex
 *
 */
public class HibernateDaoSupport extends org.springframework.orm.hibernate3.support.HibernateDaoSupport {
    private HibernateQuerySupport querySupport;
    private HibernateSimpleQuerySupport simpleQuerySupport;
    private HibernateNamedQuerySupport namedQuerySupport;

    /**
     * 创建 Hibernate 查询模板。
     * @param sessionFactory SessionFactory
     * @return HibernateTemplate
     */
    protected final HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
        HibernateTemplate template = createSpringHibernateTemplate(sessionFactory);
        querySupport = new HibernateQuerySupport(template);
        simpleQuerySupport = new HibernateSimpleQuerySupport(querySupport);
        namedQuerySupport = new HibernateNamedQuerySupport(querySupport);
        return template;
    }
    
    /**
     * 创建 Hibernate 查询模板。
     * @param sessionFactory
     * @return
     */
    protected HibernateTemplate createSpringHibernateTemplate(SessionFactory sessionFactory){
    	return super.createHibernateTemplate(sessionFactory);
    }

    /**
     * 获取查询支持对象。
     * @return HibernateQuerySupport
     */
    public final HibernateQuerySupport getQuerySupport() {
        return querySupport;
    }
    
    /**
     * 获取简单查询支持对象。
     * @return HibernateSimpleQuerySupport
     */
    public final HibernateSimpleQuerySupport getSimpleQuerySupport(){
    	return simpleQuerySupport;
    }
    
    public final HibernateNamedQuerySupport getNamedQuerySupport(){
    	return namedQuerySupport;
    }
}
