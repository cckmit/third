package org.opoo.ndao.hibernate3;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.opoo.ndao.Dao;
import org.opoo.ndao.Domain;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.domain.Versionable;
import org.opoo.ndao.support.Aggregation;
import org.opoo.ndao.support.PageLoader;
import org.opoo.ndao.support.PageUtils;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Hibernate 数据访问类。
 * 
 * @author Alex Lin
 *
 * @param <T>
 * @param <K>
 */
public abstract class HibernateDao<T extends Domain<K>, K extends Serializable> 
		extends HibernateDaoSupport implements Dao<T, K>, PageLoader<T> {
	private final Log log = LogFactory.getLog(getClass());
	
	private Class<T> entityClass;
	private Class<K> idClass;
	private String idPropertyName = "id";
	@SuppressWarnings("unchecked")
	public HibernateDao() {
        super();
        
        Type genType = getClass().getGenericSuperclass();
        if(genType != null && genType instanceof ParameterizedType){
        	Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        	if(params.length >= 2){
		        entityClass = params[0] instanceof Class ? (Class<T>) params[0] : null;
		        idClass = params[1] instanceof Class ? (Class<K>) params[1] : null;
        	}
        }
    }
    
    public T save(T entity){
    	if(entity instanceof Versionable && ((Versionable) entity).getCreationTime() == null){
    		((Versionable) entity).setCreationTime(new Date());
    	}
    	
    	getHibernateTemplate().save(entity);
    	return entity;
    }

    /**
     * 
     */
    public T update(T entity) {
    	if(entity instanceof Versionable){
    		((Versionable) entity).setModificationTime(new Date());
    	}
    	
    	//Update 导致一个错误，改为merge
    	//getHibernateTemplate().update(entity);
        getHibernateTemplate().merge(entity);
    	return entity;
    }

    public T saveOrUpdate(T entity) {
        getHibernateTemplate().saveOrUpdate(entity);
        return entity;
    }

    public int delete(T entity) {
    	getHibernateTemplate().delete(entity);
        return 1;
    }

    public int remove(final K id)  {
        final String sql = "delete from " + getEntityName() + " where "
                           + getIdProperty() + "=?";
        return ((Integer)getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException,
                    HibernateException {
                return Integer.valueOf(session.createQuery(sql).setParameter(0,
                        id).executeUpdate());
            }
        })).intValue();
    }

    public int remove(K[] ids) {
		if(ids.length == 1){
		    return remove(ids[0]);
		}
		String sql = "delete from " + getEntityName() + " where " + getIdProperty() + " in (:ids)";
	    return getQuerySupport().executeUpdate(sql, "ids", ids);
    }

    public int remove(final Criterion c)  {
        String sql = "delete from " + getEntityName();
        if (c != null) {
	    return getQuerySupport().executeUpdate(sql, c);
        } else {
            return getQuerySupport().executeUpdate(sql);
        }
    }

    @SuppressWarnings("unchecked")
	public T get(K id) {
        return (T)getHibernateTemplate().get(getEntityClass(), id);
    }

    @SuppressWarnings("unchecked")
	public List<T> find() {
        return getHibernateTemplate().loadAll(getEntityClass());
    }

	public List<T> find(ResultFilter resultFilter) {
        return getQuerySupport().find("from " + getEntityName(), resultFilter);
    }

	public PageableList<T> findPageableList(ResultFilter resultFilter){
    	return PageUtils.findPageableList(this, resultFilter);
    }

    public int getCount() {
        return getCount(ResultFilter.createEmptyResultFilter());
    }

    public int getCount(ResultFilter resultFilter) {
        return getQuerySupport().getInt("select count(*) from " + getEntityName(), resultFilter.getCriterion());
    }


    /**
     * 实体名称。通常是指实体类的类名。
     * <p>
     * 当数据查询使用了视图而数据更新仍然使用数据表时，
     * 实体名称有特别的含义，专指 Hibernate 数据查询时
     * 使用的实体名。
     * 
     * JDK1.5+ required.
     * 
     * @return String 实体名
     * 
     */
    protected String getEntityName() {
    	return getEntityClass().getSimpleName();
    }
    
    /**
     * 实体类类型。
     * 
     * <p>当数据查询使用了视图而数据更新仍然使用数据表时，
     * 实体类类型有特别的含义，专指用户数据更新的实体类.
     * @return
     */
    protected Class<T> getEntityClass() {
    	/*        
    	if (entityClass == null) {
            entityClass = (Class<T>) ((ParameterizedType) getClass()
                                      .getGenericSuperclass()).
                    getActualTypeArguments()[0];
            logger.debug("T class = " + entityClass.getName());
        }*/
        return entityClass;
    }
    
	public Class<K> getIdClass(){
    	return idClass;
    }
    

    protected String getIdProperty() {
        return idPropertyName;
    }
    
    /**
     * 
     * @return
     */
    public String getIdPropertyName(){
    	return idPropertyName;
    }
    
    



	/* (non-Javadoc)
	 * @see org.opoo.ndao.Dao#get(org.opoo.ndao.criterion.Criterion)
	 */
	public T get(Criterion criterion) {
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		rf.setCriterion(criterion);
		List<T> list = find(rf);
		if(list != null && !list.isEmpty()){
			if(list.size() > 1){
				log.warn("查询结果不唯一，只返回第一条数据。");
			}
			return list.get(0);
		}
		return null;
	}
    
	
	


	/* (non-Javadoc)
	 * @see org.springframework.dao.support.DaoSupport#initDao()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void initDao() throws Exception {
		super.initDao();
		ClassMetadata data = getSessionFactory().getClassMetadata(getEntityClass());
		idClass = data.getIdentifierType().getReturnedClass();
		idPropertyName = data.getIdentifierPropertyName();
	}

	public List<Map<String, Object>> find(ResultFilter resultFilter, Aggregation aggregation) {
		if(aggregation == null){
			throw new IllegalArgumentException("aggregation is required.");
		}
		Criterion c = resultFilter != null ? resultFilter.getCriterion() : null;
		Order o = resultFilter != null ? resultFilter.getOrder() : null;
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
		ql.append("select new map(").append(aggregation.toString()).append(")").append(" from ").append(getEntityName());
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
	
	public static void main(String[] args){
		int ii = 1;
		Integer i = (Integer) ii;
		System.out.println(Integer.class.isAssignableFrom(i.getClass()));
		System.out.println(Long.class.isAssignableFrom(i.getClass()));
		System.out.println(Number.class.isAssignableFrom(i.getClass()));

		//ObjectHibernateDao dao = new ObjectHibernateDao();
		//System.out.println(HibernateDao.class.getGenericSuperclass());
		
		//find2(null, new GroupInfo("count(*) as cnt, sum(a) as bb, sum(c) as cc", "id", "name"));
		
//		String string = QueryHelper.buildQueryString("select a,b,c, count(*) as cnt from Project" +
//				" Group by a, b, c", 
//				Restrictions.eq("id", 999), 
//				Order.asc("a").add(Order.asc("b")));
//		System.out.println(string);
		
	}
}
