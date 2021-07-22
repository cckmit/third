/*
 * $Id: EntityObjectLoaderImpl.java 5951 2012-08-02 06:22:41Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.Dao;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.hibernate3.HibernateDao;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import cn.redflagsoft.base.NotFoundException;
import cn.redflagsoft.base.bean.RFSEntityDescriptor;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.dao.hibernate3.OrgHibernateDao;
import cn.redflagsoft.base.service.EntityObjectFactory;
import cn.redflagsoft.base.service.EntityObjectLoader;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class EntityObjectLoaderImpl implements EntityObjectLoader, BeanPostProcessor {
	private static final Log log = LogFactory.getLog(EntityObjectLoaderImpl.class);
	private Map<Integer, EntityObjectFactory<? extends RFSEntityObject>> objectFactories = new HashMap<Integer, EntityObjectFactory<? extends RFSEntityObject>>();
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.EntityObjectLoader#isKnownType(short)
	 */
	public boolean isKnownType(int type) {
		return objectFactories.containsKey(type);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.EntityObjectLoader#getEntityObject(cn.redflagsoft.base.bean.RFSEntityDescriptor)
	 */
	public <T extends RFSEntityObject> T getEntityObject(RFSEntityDescriptor entity) {
		return this.<T>getEntityObject(entity.getObjectType(), entity.getId());
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.EntityObjectLoader#getEntityObject(short, long)
	 */
	@SuppressWarnings("unchecked")
	public <T extends RFSEntityObject> T getEntityObject(int objectType, long objectId) {
		if(isKnownType(objectType)){
			return (T) objectFactories.get(objectType).loadObject(objectId);
		}
		log.debug("Object loader does not know how to load type " + objectType + ".");
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.EntityObjectLoader#findEntityObjects(short, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public <T extends RFSEntityObject> List<T> findEntityObjects(int objectType, List<Long> objectIds) {
		if(isKnownType(objectType)){
			return  (List<T>) objectFactories.get(objectType).loadObjects(objectIds);
		}
		log.debug("Object loader does not know how to load type " + objectType + ".");
		return null;
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.EntityObjectLoader#findEntityObjects(int, org.opoo.ndao.support.ResultFilter)
	 */
	@SuppressWarnings("unchecked")
	public <T extends RFSEntityObject> List<T> findEntityObjects(int objectType, ResultFilter filter) {
		if(isKnownType(objectType)){
			return  (List<T>) objectFactories.get(objectType).loadObjects(filter);
		}
		log.debug("Object loader does not know how to load type " + objectType + ".");
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
	 */
	public Object postProcessBeforeInitialization(Object bean, String beanName)	throws BeansException {
		return bean;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof EntityObjectFactory){
			EntityObjectFactory<?> f = (EntityObjectFactory<?>)bean;
			int objectType = f.getObjectType();
			if(objectType > 0){
				objectFactories.put(objectType, f);
				log.info("Registering EntityObjectFactory: " + objectType + " -> " + beanName);
			}else{
				log.warn("objectType is 0, skip register EntityObjectFactory: " + beanName);
			}
		}else if(bean instanceof HibernateDao){
			try {
				handle(bean, beanName);
			} catch (Throwable e) {
				log.warn("处理EntityObjectFactory出错，忽略注册 ‘" + beanName + "’。");
			} 
		}
		return bean;
	}

	
	@SuppressWarnings("unchecked")
	private void handle(Object bean, String beanName) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		Method method = HibernateDao.class.getDeclaredMethod("getEntityClass");
		method.setAccessible(true);
		//System.out.println(method);
		Object invoke = method.invoke(bean);
		//System.out.println(invoke);
		if(invoke instanceof Class){
			Object instance = ((Class<?>)invoke).newInstance();
			//System.out.println(instance);
			if(instance instanceof RFSEntityObject){
				RFSEntityObject eo = (RFSEntityObject) instance;
				final int objectType = eo.getObjectType();
				final Dao<?,Long> dao = (Dao<?,Long>)bean;
				if(objectType > 0){
					if(!objectFactories.containsKey(objectType)){
						DaoObjectFactory<RFSEntityObject> factory = new DaoObjectFactory<RFSEntityObject>(dao, objectType);
						objectFactories.put(objectType, factory);
						log.info("Registering EntityObjectFactory: " + objectType + " -> " + beanName);
					}else{
						log.debug("DaoObjectFactory的优先级较低，系统中已经存在相同类型的EntityObjectFactory: " + objectType);
					}
				}else{
					log.warn("objectType is 0, skip register EntityObjectFactory: " + beanName);
				}
				//log.info("Registering " +  beanName + " as an EntityObjectLoader for type '" + objectType + "'.");
			}
		}
	}
	
	private static class DaoObjectFactory<T extends RFSEntityObject> implements EntityObjectFactory<T>{
		private final Dao<?,Long> dao;
		private final int objectType;
		private final String idPropertyName;
		/**
		 * @param dao
		 * @param objectType
		 */
		@SuppressWarnings("rawtypes")
		public DaoObjectFactory(Dao<?, Long> dao, int objectType) {
			super();
			this.dao = dao;
			this.objectType = objectType;
			String idPropertyName = null;
			if(dao instanceof HibernateDao){
				idPropertyName = ((HibernateDao)dao).getIdPropertyName();
			}
			if(StringUtils.isNotBlank(idPropertyName)){
				this.idPropertyName = idPropertyName;
			}else{
				this.idPropertyName = "id";
			}
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.EntityObjectFactory#getObjectType()
		 */
		public int getObjectType() {
			return objectType;
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.EntityObjectFactory#loadObject(long)
		 */
		@SuppressWarnings("unchecked")
		public T loadObject(long id) throws NotFoundException {
			return (T) dao.get(id);
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.EntityObjectFactory#loadObjects(java.util.List)
		 */
		@SuppressWarnings("unchecked")
		public List<T> loadObjects(List<Long> objectIds) throws NotFoundException {
			return (List<T>) dao.find(new ResultFilter(Restrictions.in(idPropertyName, objectIds), null));
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.EntityObjectFactory#loadObjects(org.opoo.ndao.support.ResultFilter)
		 */
		@SuppressWarnings("unchecked")
		public List<T> loadObjects(ResultFilter filter) {
			return (List<T>) dao.find(filter);
		}
	}
	
	
	public static void main(String[] args) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException{
		EntityObjectLoaderImpl loaderImpl = new EntityObjectLoaderImpl();
		loaderImpl.postProcessAfterInitialization(new OrgHibernateDao(), "");
	}
}
