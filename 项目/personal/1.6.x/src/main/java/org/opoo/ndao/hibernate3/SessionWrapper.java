package org.opoo.ndao.hibernate3;

import java.io.Serializable;
import java.sql.Connection;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.Filter;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.NamedQueryDefinition;
import org.hibernate.stat.SessionStatistics;
import org.opoo.ndao.support.ResultFilter;

public class SessionWrapper implements Session {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5540226128143367279L;
	private final Session session;
	
	
	public SessionWrapper(Session session){
		this.session = session;
	}
	

	public Transaction beginTransaction() throws HibernateException {
		return session.beginTransaction();
	}

	public void cancelQuery() throws HibernateException {
		session.cancelQuery();
	}

	public void clear() {
		session.clear();
	}

	public Connection close() throws HibernateException {
		return session.close();
	}

	@SuppressWarnings("deprecation")
	public Connection connection() throws HibernateException {
		return session.connection();
	}

	public boolean contains(Object object) {
		return session.contains(object);
	}

	public Criteria createCriteria(Class persistentClass) {
		return session.createCriteria(persistentClass);
	}

	public Criteria createCriteria(String entityName) {
		return session.createCriteria(entityName);
	}

	public Criteria createCriteria(Class persistentClass, String alias) {
		return session.createCriteria(persistentClass, alias);
	}

	public Criteria createCriteria(String entityName, String alias) {
		return session.createCriteria(entityName, alias);
	}

	public Query createFilter(Object collection, String queryString) throws HibernateException {
		return session.createFilter(collection, queryString);
	}

	public Query createQuery(String queryString) throws HibernateException {
		return session.createQuery(queryString);
	}

	public SQLQuery createSQLQuery(String queryString) throws HibernateException {
		return session.createSQLQuery(queryString);
	}

	public void delete(Object object) throws HibernateException {
		session.delete(object);
	}

	public void delete(String entityName, Object object) throws HibernateException {
		session.delete(entityName, object);
	}

	public void disableFilter(String filterName) {
		session.disableFilter(filterName);
	}

	public Connection disconnect() throws HibernateException {
		return session.disconnect();
	}

	public Filter enableFilter(String filterName) {
		return session.enableFilter(filterName);
	}

	public void evict(Object object) throws HibernateException {
		session.evict(object);
	}

	public void flush() throws HibernateException {
		session.flush();
	}

	
	public Object get(Class clazz, Serializable id) throws HibernateException {
		return session.get(clazz, id);
	}

	public Object get(String entityName, Serializable id) throws HibernateException {
		return session.get(entityName, id);
	}

	public Object get(Class clazz, Serializable id, LockMode lockMode) throws HibernateException {
		return session.get(clazz, id, lockMode);
	}

	public Object get(String entityName, Serializable id, LockMode lockMode) throws HibernateException {
		return session.get(entityName, id, lockMode);
	}

	public CacheMode getCacheMode() {
		return session.getCacheMode();
	}

	public LockMode getCurrentLockMode(Object object) throws HibernateException {
		return session.getCurrentLockMode(object);
	}

	public Filter getEnabledFilter(String filterName) {
		return session.getEnabledFilter(filterName);
	}

	public EntityMode getEntityMode() {
		return session.getEntityMode();
	}

	public String getEntityName(Object object) throws HibernateException {
		return session.getEntityName(object);
	}

	public FlushMode getFlushMode() {
		return session.getFlushMode();
	}

	public Serializable getIdentifier(Object object) throws HibernateException {
		return session.getIdentifier(object);
	}

	@SuppressWarnings("unused")
	private void initQuery(Query query, NamedQueryDefinition nqd) {
		query.setCacheable( nqd.isCacheable() );
		query.setCacheRegion( nqd.getCacheRegion() );
		if ( nqd.getTimeout()!=null ) query.setTimeout( nqd.getTimeout().intValue() );
		if ( nqd.getFetchSize()!=null ) query.setFetchSize( nqd.getFetchSize().intValue() );
		if ( nqd.getCacheMode() != null ) query.setCacheMode( nqd.getCacheMode() );
		query.setReadOnly( nqd.isReadOnly() );
		if ( nqd.getComment() != null ) query.setComment( nqd.getComment() );
	}
	
	public Query getNamedQuery(String queryName) throws HibernateException {
		//TODO
		Query query = session.getNamedQuery(queryName);
		return query;
	}
	
	
	public Query getNamedQuery(String queryName, ResultFilter filter) throws HibernateException{
		
		return null;
	}


	
	public Session getSession(EntityMode entityMode) {
		return session.getSession(entityMode);
	}

	public SessionFactory getSessionFactory() {
		return session.getSessionFactory();
	}

	public SessionStatistics getStatistics() {
		return session.getStatistics();
	}

	public Transaction getTransaction() {
		return session.getTransaction();
	}

	public boolean isConnected() {
		return session.isConnected();
	}

	public boolean isDirty() throws HibernateException {
		return session.isDirty();
	}

	public boolean isOpen() {
		return session.isOpen();
	}

	public Object load(Class theClass, Serializable id) throws HibernateException {
		return session.load(theClass, id);
	}

	public Object load(String entityName, Serializable id) throws HibernateException {
		return session.load(entityName, id);
	}

	public void load(Object object, Serializable id) throws HibernateException {
		session.load(object, id);
	}

	public Object load(Class theClass, Serializable id, LockMode lockMode) throws HibernateException {
		return session.load(theClass, id, lockMode);
	}

	public Object load(String entityName, Serializable id, LockMode lockMode) throws HibernateException {
		return session.load(entityName, id, lockMode);
	}

	public void lock(Object object, LockMode lockMode) throws HibernateException {
		session.lock(object, lockMode);
	}

	public void lock(String entityName, Object object, LockMode lockMode) throws HibernateException {
		session.lock(entityName, object, lockMode);
	}

	public Object merge(Object object) throws HibernateException {
		return session.merge(object);
	}

	public Object merge(String entityName, Object object) throws HibernateException {
		return session.merge(entityName, object);
	}

	public void persist(Object object) throws HibernateException {
		session.persist(object);
	}

	public void persist(String entityName, Object object) throws HibernateException {
		session.persist(entityName, object);
	}

	@SuppressWarnings("deprecation")
	public void reconnect() throws HibernateException {
		session.reconnect();
	}

	public void reconnect(Connection connection) throws HibernateException {
		session.reconnect(connection);
	}

	public void refresh(Object object) throws HibernateException {
		session.refresh(object);
	}

	public void refresh(Object object, LockMode lockMode) throws HibernateException {
		session.refresh(object, lockMode);
	}

	public void replicate(Object object, ReplicationMode replicationMode) throws HibernateException {
		session.replicate(object, replicationMode);
	}

	public void replicate(String entityName, Object object, ReplicationMode replicationMode) throws HibernateException {
		session.replicate(entityName, object, replicationMode);
	}

	public Serializable save(Object object) throws HibernateException {
		return session.save(object);
	}

	public Serializable save(String entityName, Object object) throws HibernateException {
		return session.save(entityName, object);
	}

	public void saveOrUpdate(Object object) throws HibernateException {
		session.saveOrUpdate(object);
	}

	public void saveOrUpdate(String entityName, Object object) throws HibernateException {
		session.saveOrUpdate(entityName, object);
	}

	public void setCacheMode(CacheMode cacheMode) {
		session.setCacheMode(cacheMode);
	}

	public void setFlushMode(FlushMode flushMode) {
		session.setFlushMode(flushMode);
	}

	public void setReadOnly(Object entity, boolean readOnly) {
		session.setReadOnly(entity, readOnly);
	}

	public void update(Object object) throws HibernateException {
		session.update(object);
	}

	public void update(String entityName, Object object) throws HibernateException {
		session.update(entityName, object);
	}

}
