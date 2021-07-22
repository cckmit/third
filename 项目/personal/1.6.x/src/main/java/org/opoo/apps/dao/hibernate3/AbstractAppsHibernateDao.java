package org.opoo.apps.dao.hibernate3;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Filter;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.opoo.apps.id.DataFieldMaxValueIncrementerIdGenerator;
import org.opoo.apps.id.IdGeneratable;
import org.opoo.apps.id.IdGenerator;
import org.opoo.apps.id.IdGeneratorProvider;
import org.opoo.apps.license.AppsLicense;
import org.opoo.apps.license.AppsLicenseHolder;
import org.opoo.apps.license.AppsLicenseLog;
import org.opoo.ndao.Domain;
import org.opoo.ndao.hibernate3.CachedHibernateDao;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * 
 * DAO的超类。
 * 
 * 可根据实际需要为DAO类设置ID生成器。
 * 
 * @param <T>
 * @param <K>
 */
public abstract class AbstractAppsHibernateDao<T extends Domain<K>, K extends java.io.Serializable>
		extends CachedHibernateDao<T, K> implements IdGeneratable<K> {
	
	
	
	private IdGenerator<K> idGenerator;
	private DataFieldMaxValueIncrementer dataFieldMaxValueIncrementer;
	private static DataAccessException ex = new DataRetrievalFailureException("data query failed.");

	/**
	 * 设置ID生成器。
	 * 
	 * @param idGenerator
	 */
	public void setIdGenerator(IdGenerator<K> idGenerator) {
		this.idGenerator = idGenerator;
	}

	/**
	 * 设置ID生成器提供者。 JDK1.5+ required.
	 * 
	 * @param idGeneratorProvider
	 */
	public void setIdGeneratorProvider(
			IdGeneratorProvider<K> idGeneratorProvider) {
		this.idGenerator = idGeneratorProvider.getIdGenerator(getEntityClass().getSimpleName());
	}

	@Override
	public T save(T entity) {
		if (entity.getId() == null && getIdGenerator() != null) {
			entity.setId(getIdGenerator().getNext());
		}
		return super.save(entity);
	}

	@SuppressWarnings("unchecked")
	/*
	 * 根据结果过滤器是否为空，自动调用相应的内部方法。
	 */
	public List<T> find(String qs, ResultFilter rf) {
		if (rf == null) {
			return getHibernateTemplate().find(qs);
		} else {
			return getQuerySupport().find(qs, rf);
		}
	}

	/**
	 * @return the idGenerator
	 */
	public IdGenerator<K> getIdGenerator() {
		if (idGenerator == null && dataFieldMaxValueIncrementer != null) {
			idGenerator = new DataFieldMaxValueIncrementerIdGenerator<K>(
					dataFieldMaxValueIncrementer);
		}
		return idGenerator;
	}

	/**
	 * @return the dataFieldMaxValueIncrementer
	 */
	public DataFieldMaxValueIncrementer getDataFieldMaxValueIncrementer() {
		return dataFieldMaxValueIncrementer;
	}

	/**
	 * @param dataFieldMaxValueIncrementer
	 *            the dataFieldMaxValueIncrementer to set
	 */
	public void setDataFieldMaxValueIncrementer(
			DataFieldMaxValueIncrementer dataFieldMaxValueIncrementer) {
		this.dataFieldMaxValueIncrementer = dataFieldMaxValueIncrementer;
	}

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.opoo.ndao.hibernate3.CachedHibernateDao#find()
//	 */
//	@Override
//	public List<T> find() throws DataAccessException {
//		Check.check();
//		return super.find();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * org.opoo.ndao.hibernate3.CachedHibernateDao#find(org.opoo.ndao.support
//	 * .ResultFilter)
//	 */
//	@Override
//	public List<T> find(ResultFilter resultFilter) throws DataAccessException {
//		Check.check();
//		return super.find(resultFilter);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * org.opoo.ndao.hibernate3.CachedHibernateDao#get(java.io.Serializable)
//	 */
//	@Override
//	public T get(K id) throws DataAccessException {
//		Check.check();
//		return super.get(id);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * org.opoo.ndao.hibernate3.CachedHibernateDao#delete(org.opoo.ndao.Domain)
//	 */
//	@Override
//	public int delete(T entity) throws DataAccessException {
//		Check.check();
//		return super.delete(entity);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * org.opoo.ndao.hibernate3.CachedHibernateDao#remove(java.io.Serializable)
//	 */
//	@Override
//	public int remove(K id) throws DataAccessException {
//		Check.check();
//		return super.remove(id);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * org.opoo.ndao.hibernate3.CachedHibernateDao#update(org.opoo.ndao.Domain)
//	 */
//	@Override
//	public T update(T entity) throws DataAccessException {
//		Check.check();
//		return super.update(entity);
//	}


	protected final HibernateTemplate createSpringHibernateTemplate(SessionFactory sessionFactory) {
		return new HibernateTemplateWrapper(new HibernateTemplate(sessionFactory));
		//return new HibernateTemplate(sessionFactory);
	}

	
	private static class LicenseChecker {
		//private static final Log log = LogFactory.getLog("License");

//		
//		
//		
//		// 1小时检查一次
//		private static long ONE_HOUR = 1000 * 60 * 60 * 1;
//		private static long time = 0L;
//		private static AppsLicenseManager licenseManager = AppsLicenseManager.getInstance();
		
		private static void check() {
			AppsLicense license = AppsLicenseHolder.getThreadAppsLicense();
			if(license == null){
				license = AppsLicenseHolder.getAppsLicense();
				AppsLicenseHolder.setThreadAppsLicense(license);
				AppsLicenseLog.LOG.debug("Checking license.");
			}else{
				AppsLicenseLog.LOG.debug("Thread has a checked license, skip checking.");
			}
			
			if(license == null){
				throw ex;
			}
			
			
//			if (time == 0L) {
//				time = System.currentTimeMillis();
//			} else if (System.currentTimeMillis() - time > ONE_HOUR) {
//				// 开发版本，访问3秒延迟
//				if (licenseManager.getAppsLicense().getDevMode() != null) {
//					throw new LicenseException("开发版本 License，不能作为正式产品运行。");
//					// Thread.sleep(3000);
//				}
//
//				licenseManager.destroy();
//				licenseManager.initialize();
//				time = System.currentTimeMillis();
//			} else {
//				AppsLicense license = licenseManager.getAppsLicense();
//				if (license.getDevMode() != null) {
//					//String.format(format, args)
//					System.out.printf("*** 这是一个开发版本，授权给 %s，请勿用于产品 ***\n", license.getClient().getName());
//				}
//			}
		}
	}
	
	
	
	
	/**
	 * 带 License 检查的 Hibernate 查询模板。
	 *
	 */
	private static class HibernateTemplateWrapper extends HibernateTemplate{
		final HibernateTemplate template;
		private HibernateTemplateWrapper(HibernateTemplate template) {
			this.template = template;
		}

		public void delete(Object entity, LockMode lockMode)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			template.delete(entity, lockMode);
		}
		
		public void delete(Object entity)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			template.delete(entity);
		}
		
		public void delete(String entityName, Object entity,
				LockMode lockMode)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			template.delete(entityName, entity, lockMode);
		}
		
		public void delete(String entityName, Object entity)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			template.delete(entityName, entity);
		}
		
		public Object execute(HibernateCallback action)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			return template.execute(action);
		}
		@SuppressWarnings("unchecked")
		public List executeFind(HibernateCallback action)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			return template.executeFind(action);
		}
		@SuppressWarnings("unchecked")
		public List find(String queryString, Object value)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			return template.find(queryString, value);
		}
		@SuppressWarnings("unchecked")
		public List find(String queryString, Object[] values)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			return template.find(queryString, values);
		}
		@SuppressWarnings("unchecked")
		public List find(String queryString)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			return template.find(queryString);
		}
		@SuppressWarnings("unchecked")
		public Object get(Class entityClass, Serializable id,
				LockMode lockMode)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			return template.get(entityClass, id, lockMode);
		}
		@SuppressWarnings("unchecked")
		public Object get(Class entityClass, Serializable id)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			return template.get(entityClass, id);
		}
		
		public Object get(String entityName, Serializable id,
				LockMode lockMode)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			return template.get(entityName, id, lockMode);
		}
		
		public Object get(String entityName, Serializable id)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			return template.get(entityName, id);
		}
		@SuppressWarnings("unchecked")
		public Object load(Class entityClass, Serializable id,
				LockMode lockMode)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			return template.load(entityClass, id, lockMode);
		}
		@SuppressWarnings("unchecked")
		public Object load(Class entityClass, Serializable id)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			return template.load(entityClass, id);
		}
		
		public void load(Object entity, Serializable id)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			template.load(entity, id);
		}
		
		public Object load(String entityName, Serializable id,
				LockMode lockMode)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			return template.load(entityName, id, lockMode);
		}
		
		public Object load(String entityName, Serializable id)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			return template.load(entityName, id);
		}
		@SuppressWarnings("unchecked")
		public List loadAll(Class entityClass)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			return template.loadAll(entityClass);
		}
		
		public Serializable save(Object entity)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			return template.save(entity);
		}
		
		public void saveOrUpdate(Object entity)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			template.saveOrUpdate(entity);
		}
		
		public void update(Object entity)
				throws org.springframework.dao.DataAccessException {
			LicenseChecker.check();
			template.update(entity);
		}


		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#bulkUpdate(java.lang.String, java.lang.Object)
		 */
		@Override
		public int bulkUpdate(String queryString, Object value)
				throws org.springframework.dao.DataAccessException {
			
			return template.bulkUpdate(queryString, value);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#bulkUpdate(java.lang.String, java.lang.Object[])
		 */
		@Override
		public int bulkUpdate(String queryString, Object[] values)
				throws org.springframework.dao.DataAccessException {
			
			return template.bulkUpdate(queryString, values);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#bulkUpdate(java.lang.String)
		 */
		@Override
		public int bulkUpdate(String queryString)
				throws org.springframework.dao.DataAccessException {
			
			return template.bulkUpdate(queryString);
		}



		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#clear()
		 */
		@Override
		public void clear() throws org.springframework.dao.DataAccessException {
			
			template.clear();
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#closeIterator(java.util.Iterator)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void closeIterator(Iterator it)
				throws org.springframework.dao.DataAccessException {
			
			template.closeIterator(it);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#contains(java.lang.Object)
		 */
		@Override
		public boolean contains(Object entity)
				throws org.springframework.dao.DataAccessException {
			
			return template.contains(entity);
		}


		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#deleteAll(java.util.Collection)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void deleteAll(Collection entities)
				throws org.springframework.dao.DataAccessException {
			
			template.deleteAll(entities);
		}



		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#enableFilter(java.lang.String)
		 */
		@Override
		public Filter enableFilter(String filterName)
				throws IllegalStateException {
			
			return template.enableFilter(filterName);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#evict(java.lang.Object)
		 */
		@Override
		public void evict(Object entity)
				throws org.springframework.dao.DataAccessException {
			
			template.evict(entity);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#execute(org.springframework.orm.hibernate3.HibernateCallback, boolean)
		 */
		@SuppressWarnings("deprecation")
		@Override
		public Object execute(HibernateCallback action,
				boolean enforceNativeSession)
				throws org.springframework.dao.DataAccessException {
			
			return template.execute(action, enforceNativeSession);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#executeWithNativeSession(org.springframework.orm.hibernate3.HibernateCallback)
		 */
		@Override
		public Object executeWithNativeSession(HibernateCallback action) {
			
			return template.executeWithNativeSession(action);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#executeWithNewSession(org.springframework.orm.hibernate3.HibernateCallback)
		 */
		@Override
		public Object executeWithNewSession(HibernateCallback action) {
			
			return template.executeWithNewSession(action);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByCriteria(org.hibernate.criterion.DetachedCriteria, int, int)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List findByCriteria(DetachedCriteria criteria, int firstResult,
				int maxResults)
				throws org.springframework.dao.DataAccessException {
			
			return template.findByCriteria(criteria, firstResult, maxResults);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByCriteria(org.hibernate.criterion.DetachedCriteria)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List findByCriteria(DetachedCriteria criteria)
				throws org.springframework.dao.DataAccessException {
			
			return template.findByCriteria(criteria);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByExample(java.lang.Object, int, int)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List findByExample(Object exampleEntity, int firstResult,
				int maxResults)
				throws org.springframework.dao.DataAccessException {
			
			return template.findByExample(exampleEntity, firstResult, maxResults);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByExample(java.lang.Object)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List findByExample(Object exampleEntity)
				throws org.springframework.dao.DataAccessException {
			
			return template.findByExample(exampleEntity);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByExample(java.lang.String, java.lang.Object, int, int)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List findByExample(String entityName, Object exampleEntity,
				int firstResult, int maxResults)
				throws org.springframework.dao.DataAccessException {
			
			return template.findByExample(entityName, exampleEntity, firstResult, maxResults);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByExample(java.lang.String, java.lang.Object)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List findByExample(String entityName, Object exampleEntity)
				throws org.springframework.dao.DataAccessException {
			
			return template.findByExample(entityName, exampleEntity);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedParam(java.lang.String, java.lang.String, java.lang.Object)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List findByNamedParam(String queryString, String paramName,
				Object value)
				throws org.springframework.dao.DataAccessException {
			
			return template.findByNamedParam(queryString, paramName, value);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List findByNamedParam(String queryString, String[] paramNames,
				Object[] values)
				throws org.springframework.dao.DataAccessException {
			
			return template.findByNamedParam(queryString, paramNames, values);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQuery(java.lang.String, java.lang.Object)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List findByNamedQuery(String queryName, Object value)
				throws org.springframework.dao.DataAccessException {
			
			return template.findByNamedQuery(queryName, value);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQuery(java.lang.String, java.lang.Object[])
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List findByNamedQuery(String queryName, Object[] values)
				throws org.springframework.dao.DataAccessException {
			
			return template.findByNamedQuery(queryName, values);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQuery(java.lang.String)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List findByNamedQuery(String queryName)
				throws org.springframework.dao.DataAccessException {
			
			return template.findByNamedQuery(queryName);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQueryAndNamedParam(java.lang.String, java.lang.String, java.lang.Object)
		 */
		@Override
		@SuppressWarnings("unchecked")
		public List findByNamedQueryAndNamedParam(String queryName,
				String paramName, Object value)
				throws org.springframework.dao.DataAccessException {
			
			return template.findByNamedQueryAndNamedParam(queryName, paramName, value);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQueryAndNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
		 */
		@Override
		@SuppressWarnings("unchecked")
		public List findByNamedQueryAndNamedParam(String queryName,
				String[] paramNames, Object[] values)
				throws org.springframework.dao.DataAccessException {
			
			return template.findByNamedQueryAndNamedParam(queryName, paramNames, values);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQueryAndValueBean(java.lang.String, java.lang.Object)
		 */
		@Override
		@SuppressWarnings("unchecked")
		public List findByNamedQueryAndValueBean(String queryName,
				Object valueBean)
				throws org.springframework.dao.DataAccessException {
			
			return template.findByNamedQueryAndValueBean(queryName, valueBean);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByValueBean(java.lang.String, java.lang.Object)
		 */
		@Override
		@SuppressWarnings("unchecked")
		public List findByValueBean(String queryString, Object valueBean)
				throws org.springframework.dao.DataAccessException {
			
			return template.findByValueBean(queryString, valueBean);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#flush()
		 */
		@Override
		public void flush() throws org.springframework.dao.DataAccessException {
			
			template.flush();
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#getFetchSize()
		 */
		@Override
		public int getFetchSize() {
			
			return template.getFetchSize();
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#getMaxResults()
		 */
		@Override
		public int getMaxResults() {
			
			return template.getMaxResults();
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#getQueryCacheRegion()
		 */
		@Override
		public String getQueryCacheRegion() {
			
			return template.getQueryCacheRegion();
		}



		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#initialize(java.lang.Object)
		 */
		@Override
		public void initialize(Object proxy)
				throws org.springframework.dao.DataAccessException {
			
			template.initialize(proxy);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#isAllowCreate()
		 */
		@Override
		public boolean isAllowCreate() {
			
			return template.isAllowCreate();
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#isAlwaysUseNewSession()
		 */
		@Override
		public boolean isAlwaysUseNewSession() {
			
			return template.isAlwaysUseNewSession();
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#isCacheQueries()
		 */
		@Override
		public boolean isCacheQueries() {
			
			return template.isCacheQueries();
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#isCheckWriteOperations()
		 */
		@Override
		public boolean isCheckWriteOperations() {
			
			return template.isCheckWriteOperations();
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#isExposeNativeSession()
		 */
		@Override
		public boolean isExposeNativeSession() {
			
			return template.isExposeNativeSession();
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#iterate(java.lang.String, java.lang.Object)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public Iterator iterate(String queryString, Object value)
				throws org.springframework.dao.DataAccessException {
			
			return template.iterate(queryString, value);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#iterate(java.lang.String, java.lang.Object[])
		 */
		@SuppressWarnings("unchecked")
		@Override
		public Iterator iterate(String queryString, Object[] values)
				throws org.springframework.dao.DataAccessException {
			
			return template.iterate(queryString, values);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#iterate(java.lang.String)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public Iterator iterate(String queryString)
				throws org.springframework.dao.DataAccessException {
			return template.iterate(queryString);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#lock(java.lang.Object, org.hibernate.LockMode)
		 */
		@Override
		public void lock(Object entity, LockMode lockMode)
				throws org.springframework.dao.DataAccessException {
			template.lock(entity, lockMode);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#lock(java.lang.String, java.lang.Object, org.hibernate.LockMode)
		 */
		@Override
		public void lock(String entityName, Object entity, LockMode lockMode)
				throws org.springframework.dao.DataAccessException {
			template.lock(entityName, entity, lockMode);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#merge(java.lang.Object)
		 */
		@Override
		public Object merge(Object entity)
				throws org.springframework.dao.DataAccessException {
			return template.merge(entity);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#merge(java.lang.String, java.lang.Object)
		 */
		@Override
		public Object merge(String entityName, Object entity)
				throws org.springframework.dao.DataAccessException {
			return template.merge(entityName, entity);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#persist(java.lang.Object)
		 */
		@Override
		public void persist(Object entity)
				throws org.springframework.dao.DataAccessException {
			template.persist(entity);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#persist(java.lang.String, java.lang.Object)
		 */
		@Override
		public void persist(String entityName, Object entity)
				throws org.springframework.dao.DataAccessException {
			template.persist(entityName, entity);
		}



		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#refresh(java.lang.Object, org.hibernate.LockMode)
		 */
		@Override
		public void refresh(Object entity, LockMode lockMode)
				throws org.springframework.dao.DataAccessException {
			template.refresh(entity, lockMode);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#refresh(java.lang.Object)
		 */
		@Override
		public void refresh(Object entity)
				throws org.springframework.dao.DataAccessException {
			template.refresh(entity);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#replicate(java.lang.Object, org.hibernate.ReplicationMode)
		 */
		@Override
		public void replicate(Object entity, ReplicationMode replicationMode)
				throws org.springframework.dao.DataAccessException {
			template.replicate(entity, replicationMode);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#replicate(java.lang.String, java.lang.Object, org.hibernate.ReplicationMode)
		 */
		@Override
		public void replicate(String entityName, Object entity,
				ReplicationMode replicationMode)
				throws org.springframework.dao.DataAccessException {
			template.replicate(entityName, entity, replicationMode);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#save(java.lang.String, java.lang.Object)
		 */
		@Override
		public Serializable save(String entityName, Object entity)
				throws org.springframework.dao.DataAccessException {
			return template.save(entityName, entity);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#saveOrUpdate(java.lang.String, java.lang.Object)
		 */
		@Override
		public void saveOrUpdate(String entityName, Object entity)
				throws org.springframework.dao.DataAccessException {
			template.saveOrUpdate(entityName, entity);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#saveOrUpdateAll(java.util.Collection)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void saveOrUpdateAll(Collection entities)
				throws org.springframework.dao.DataAccessException {
			template.saveOrUpdateAll(entities);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#setAllowCreate(boolean)
		 */
		@Override
		public void setAllowCreate(boolean allowCreate) {
			template.setAllowCreate(allowCreate);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#setAlwaysUseNewSession(boolean)
		 */
		@Override
		public void setAlwaysUseNewSession(boolean alwaysUseNewSession) {
			template.setAlwaysUseNewSession(alwaysUseNewSession);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#setCacheQueries(boolean)
		 */
		@Override
		public void setCacheQueries(boolean cacheQueries) {
			template.setCacheQueries(cacheQueries);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#setCheckWriteOperations(boolean)
		 */
		@Override
		public void setCheckWriteOperations(boolean checkWriteOperations) {
			template.setCheckWriteOperations(checkWriteOperations);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#setExposeNativeSession(boolean)
		 */
		@Override
		public void setExposeNativeSession(boolean exposeNativeSession) {
			template.setExposeNativeSession(exposeNativeSession);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#setFetchSize(int)
		 */
		@Override
		public void setFetchSize(int fetchSize) {
			template.setFetchSize(fetchSize);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#setMaxResults(int)
		 */
		@Override
		public void setMaxResults(int maxResults) {
			template.setMaxResults(maxResults);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#setQueryCacheRegion(java.lang.String)
		 */
		@Override
		public void setQueryCacheRegion(String queryCacheRegion) {
			template.setQueryCacheRegion(queryCacheRegion);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#update(java.lang.Object, org.hibernate.LockMode)
		 */
		@Override
		public void update(Object entity, LockMode lockMode)
				throws org.springframework.dao.DataAccessException {
			template.update(entity, lockMode);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#update(java.lang.String, java.lang.Object, org.hibernate.LockMode)
		 */
		@Override
		public void update(String entityName, Object entity, LockMode lockMode)
				throws org.springframework.dao.DataAccessException {
			template.update(entityName, entity, lockMode);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateTemplate#update(java.lang.String, java.lang.Object)
		 */
		@Override
		public void update(String entityName, Object entity)
				throws org.springframework.dao.DataAccessException {
			template.update(entityName, entity);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateAccessor#afterPropertiesSet()
		 */
		@Override
		public void afterPropertiesSet() {
			template.afterPropertiesSet();
		}



		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateAccessor#convertHibernateAccessException(org.hibernate.HibernateException)
		 */
		@Override
		public org.springframework.dao.DataAccessException convertHibernateAccessException(
				HibernateException ex) {
			return template.convertHibernateAccessException(ex);
		}









		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateAccessor#getEntityInterceptor()
		 */
		@Override
		public Interceptor getEntityInterceptor() throws IllegalStateException,
				BeansException {
			return template.getEntityInterceptor();
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateAccessor#getFilterNames()
		 */
		@Override
		public String[] getFilterNames() {
			return template.getFilterNames();
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateAccessor#getFlushMode()
		 */
		@Override
		public int getFlushMode() {
			return template.getFlushMode();
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateAccessor#getJdbcExceptionTranslator()
		 */
		@Override
		public SQLExceptionTranslator getJdbcExceptionTranslator() {
			return template.getJdbcExceptionTranslator();
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateAccessor#getSessionFactory()
		 */
		@Override
		public SessionFactory getSessionFactory() {
			return template.getSessionFactory();
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateAccessor#setBeanFactory(org.springframework.beans.factory.BeanFactory)
		 */
		@Override
		public void setBeanFactory(BeanFactory beanFactory) {
			
			template.setBeanFactory(beanFactory);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateAccessor#setEntityInterceptor(org.hibernate.Interceptor)
		 */
		@Override
		public void setEntityInterceptor(Interceptor entityInterceptor) {
			template.setEntityInterceptor(entityInterceptor);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateAccessor#setEntityInterceptorBeanName(java.lang.String)
		 */
		@Override
		public void setEntityInterceptorBeanName(
				String entityInterceptorBeanName) {
			template.setEntityInterceptorBeanName(entityInterceptorBeanName);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateAccessor#setFilterName(java.lang.String)
		 */
		@Override
		public void setFilterName(String filter) {
			template.setFilterName(filter);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateAccessor#setFilterNames(java.lang.String[])
		 */
		@Override
		public void setFilterNames(String[] filterNames) {
			template.setFilterNames(filterNames);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateAccessor#setFlushMode(int)
		 */
		@Override
		public void setFlushMode(int flushMode) {
			template.setFlushMode(flushMode);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateAccessor#setFlushModeName(java.lang.String)
		 */
		@Override
		public void setFlushModeName(String constantName) {
			template.setFlushModeName(constantName);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateAccessor#setJdbcExceptionTranslator(org.springframework.jdbc.support.SQLExceptionTranslator)
		 */
		@Override
		public void setJdbcExceptionTranslator(
				SQLExceptionTranslator jdbcExceptionTranslator) {
			template.setJdbcExceptionTranslator(jdbcExceptionTranslator);
		}

		/* (non-Javadoc)
		 * @see org.springframework.orm.hibernate3.HibernateAccessor#setSessionFactory(org.hibernate.SessionFactory)
		 */
		@Override
		public void setSessionFactory(SessionFactory sessionFactory) {
			template.setSessionFactory(sessionFactory);
		}
	}
	
}
