package org.opoo.ndao.hibernate3;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.DataAccessException;
import org.opoo.ndao.Domain;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.cache.Cache;
import org.opoo.cache.NullCache;

/**
 * 可缓存的 Hibernate 数据访问类。
 * @author Alex Lin(alex@opoo.org)
 *
 * @param <T>
 * @param <K>
 */
public class CachedHibernateDao<T extends Domain<K>, K extends Serializable> extends HibernateDao<T, K> {

	private static final Log log = LogFactory.getLog(CachedHibernateDao.class);
	
	/**
	 * 实体别名。
	 */
	private String entityAlias = "a";
	
	/**
	 * Demo性质的缓存，在实际运用中采取成熟的缓存产品。
	 */
	private Cache<String, T> cache = new NullCache<String, T>();
	/**
	 * 标志缓存是否可用，默认false。
	 */
	private boolean enableCache = false;
	
	/**
	 * 构建缓存的key.
	 * @param id
	 * @return
	 */
	protected String buildCacheKey(K id){
		return getEntityName() + "|" + id;
	}
	

	/**
	 * 删除指定对象。
	 */
	@Override
	public int delete(T entity) throws DataAccessException {
		if(enableCache) {
			cache.remove(buildCacheKey(entity.getId()));
		}
		return super.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opoo.ndao.hibernate3.HibernateDao#find()
	 */
	@Override
	public List<T> find() throws DataAccessException {
		if(enableCache){
//			String hql = "select " + getIdProperty() + " from " + getEntityName();
//			if(log.isDebugEnabled()){
//				log.debug("查询主键集合：" + hql);
//			}
//			final List<K> list = getHibernateTemplate().find(hql);
			final List<K> list = findIds(null);
			return Collections.unmodifiableList(new EntityList(list));
		}
		return super.find();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opoo.ndao.hibernate3.HibernateDao#find(org.opoo.ndao.support.ResultFilter
	 * )
	 */
	@Override
	public List<T> find(ResultFilter resultFilter) throws DataAccessException {
		if(resultFilter == null){
			return find();
		}
		if(enableCache){
//			String hql = "select " + getIdProperty() + " from " + getEntityName();
//			if(log.isDebugEnabled()){
//				log.debug("查询主键集合：" + hql);
//			}
//			
//			final List<K> list = getQuerySupport().find(hql, resultFilter);
			final List<K> list = findIds(resultFilter);
			return Collections.unmodifiableList(new EntityList(list));
		}
		return super.find(resultFilter);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<K> findIds(ResultFilter resultFilter){
		String hql = "select " + getIdProperty() + " from " + getEntityName();
		if(StringUtils.isNotBlank(getEntityAlias())){
			hql += " " + getEntityAlias();
		}
		if(log.isDebugEnabled()){
			log.debug("查询主键集合：" + hql);
		}
		
		if(resultFilter != null){
			return getQuerySupport().find(hql, resultFilter);
		}else{
			return getHibernateTemplate().find(hql);
		}
	}

	@Override
	public T get(K id) throws DataAccessException {
		if(enableCache){
			String key = buildCacheKey(id);
			T t = cache.get(key);
			
			if(log.isDebugEnabled()){
				log.debug("缓存中查找对象[" + key + "]: " + t);
			}
			
			if (t == null) {
				t = super.get(id);
				if (t != null) {
					cache.put(key, t);
				}
			}
			return t;
		}
		return super.get(id);
	}

	@Override
	public int remove(Criterion c) throws DataAccessException {
		if(enableCache){
			cache.clear();
		}
		return super.remove(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opoo.ndao.hibernate3.HibernateDao#remove(java.io.Serializable)
	 */
	@Override
	public int remove(K id) throws DataAccessException {
		if(enableCache){
			cache.remove(buildCacheKey(id));
		}
		return super.remove(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opoo.ndao.hibernate3.HibernateDao#remove(K[])
	 */
	@Override
	public int remove(K[] ids) throws DataAccessException {
		if(enableCache){
			for (int i = 0; i < ids.length; i++) {
				cache.remove(buildCacheKey(ids[i]));
			}
		}
		return super.remove(ids);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opoo.ndao.hibernate3.HibernateDao#save(org.opoo.ndao.Domain)
	 */
	@Override
	public T save(T entity) throws DataAccessException {
		return super.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opoo.ndao.hibernate3.HibernateDao#saveOrUpdate(org.opoo.ndao.Domain)
	 */
	@Override
	public T saveOrUpdate(T entity) throws DataAccessException {
		if(enableCache && entity.getId() != null){
			cache.remove(buildCacheKey(entity.getId()));
		}
		return super.saveOrUpdate(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opoo.ndao.hibernate3.HibernateDao#update(org.opoo.ndao.Domain)
	 */
	@Override
	public T update(T entity) throws DataAccessException {
		if(enableCache){
			cache.remove(buildCacheKey(entity.getId()));
		}
		return super.update(entity);
	}

	class EntityList extends AbstractList<T> {
		private List<K> ids;
		private int size;

		private EntityList(List<K> ids) {
			this.ids = ids;
			this.size = ids.size();
		}

		@Override
		public T get(int index) {
			K id = ids.get(index);
			return CachedHibernateDao.this.get(id);
		}

		@Override
		public int size() {
			return size;
		}
	}

	/**
	 * 获取缓存。
	 * @return the cache
	 */
	public Cache<String, T> getCache() {
		return cache;
	}

	/**
	 * 设置缓存。
	 * @param cache the cache to set
	 */
	public void setCache(Cache<String, T> cache) {
		this.cache = cache;
	}


	/**
	 * 缓存是否可用。
	 * @return the enableCache
	 */
	public boolean isEnableCache() {
		return enableCache;
	}


	/**
	 * 设置缓存是否可用。
	 * @param enableCache the enableCache to set
	 */
	public void setEnableCache(boolean enableCache) {
		this.enableCache = enableCache;
	}


	public String getEntityAlias() {
		return entityAlias;
	}


	public void setEntityAlias(String entityAlias) {
		this.entityAlias = entityAlias;
	}

	@Override
    public int getCount(ResultFilter resultFilter) {
		String hql = "select count(*) from " + getEntityName();
		if(StringUtils.isNotBlank(getEntityAlias())){
			hql += " " + getEntityAlias();
		}
        return getQuerySupport().getInt(hql, resultFilter.getCriterion());
    }
}
