package org.opoo.apps.service.hibernate3;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.metadata.ClassMetadata;
import org.opoo.apps.QueryParameter;
import org.opoo.apps.service.impl.QueryParametersBuilder;
import org.opoo.ndao.hibernate3.HibernateDao;

public class HibernateQueryParametersBuilder extends QueryParametersBuilder {
	private static final Log log = LogFactory.getLog(HibernateQueryParametersBuilder.class);
	
	/* (non-Javadoc)
	 * @see org.opoo.apps.service.impl.QueryParametersBuilder#getType(java.lang.Object, org.opoo.apps.QueryParameter)
	 */
	@Override
	protected Class<?> getType(Object bean, QueryParameter parameter) {
		Class<?> result = super.getType(bean, parameter);
		if(result == null && bean != null && bean instanceof HibernateDao){
			ClassMetadata meta = getClassMetaData((HibernateDao<?, ?>) bean);
			result = getPropertyType(meta, parameter.getN());
		}
		return result;
	}
	
	
	public ClassMetadata getClassMetaData(HibernateDao<?,?> dao){
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
	public Class<?> getEntityClass(HibernateDao<?,?> dao){
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
	
	
	public Class<?> getPropertyType(ClassMetadata meta, String propertyName){
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
