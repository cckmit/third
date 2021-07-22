package org.opoo.apps.query.impl;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.metadata.ClassMetadata;
import org.opoo.ndao.hibernate3.HibernateDao;

import com.google.common.collect.Maps;

public class HibernateParametersBuilder extends AbstractParametersBuilder {
	private static final Log log = LogFactory.getLog(HibernateParametersBuilder.class);
	private Map<String, Class<?>> map = Maps.newHashMap();
	
	@Override
	protected Class<?> getPropertyType(Object bean, String n, String v,	Class<?> typeClass) {
		Class<?> result = super.getPropertyType(bean, n, v, typeClass);
		if(result == null && bean != null && bean instanceof HibernateDao){
			result = getPropertyType((HibernateDao<?, ?>) bean, n);
		}
		return result;
	}
	
	private Class<?> getPropertyType(HibernateDao<?,?> dao, String n){
		String key = dao.getClass().getName() + "#" + n;
		Class<?> clazz = map.get(key);
		if(clazz == null){
			ClassMetadata meta = getClassMetaData(dao);
			clazz = getPropertyType(meta, n);
			if(clazz != null){
				map.put(key, clazz);
			}
		}
		if(log.isDebugEnabled()){
			log.debug("获取 " + key + " 的类型：" + clazz);
		}
		return clazz;
	}
	
	private ClassMetadata getClassMetaData(HibernateDao<?,?> dao){
		HibernateDao<?,?> hdao = (HibernateDao<?,?>) dao;
		Class<?> entityClass = getEntityClass(dao);
		if(entityClass != null){
			log.debug("找到dao对应的entityClass: " + entityClass);
			return hdao.getSessionFactory().getClassMetadata(entityClass);
		}else{
			log.warn("找不到dao对应的entityClass：" + dao);
		}
		return null;
	}
	
	/**
	 * 
	 * @param dao
	 * @return
	 */
	private Class<?> getEntityClass(HibernateDao<?,?> dao){
		try {
			//通过调用dao的protected方法来获的当前dao的entityClass。
			Method m = HibernateDao.class.getDeclaredMethod("getEntityClass");
			m.setAccessible(true);
			return (Class<?>) m.invoke(dao);
			//System.out.println(m.invoke(dao));//getEntityClass
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
		} 
		return null;
	}
	
	private Class<?> getPropertyType(ClassMetadata meta, String propertyName){
		if(meta == null) 
			return null;
		try {
			org.hibernate.type.Type type = meta.getPropertyType(propertyName);
			return type.getReturnedClass();
		} catch (Exception e) {
			log.warn( e);
		}
		return null;
	}
}
