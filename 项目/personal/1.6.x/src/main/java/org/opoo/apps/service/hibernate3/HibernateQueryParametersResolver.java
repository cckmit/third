package org.opoo.apps.service.hibernate3;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.metadata.ClassMetadata;
import org.opoo.apps.QueryParameter;
import org.opoo.apps.service.impl.DefaultQueryParametersResolver;
import org.opoo.ndao.Dao;
import org.opoo.ndao.hibernate3.HibernateDao;


public class HibernateQueryParametersResolver extends DefaultQueryParametersResolver {
	private static final Log log = LogFactory.getLog(HibernateQueryParametersResolver.class);
	
	
	@Override
	public Class<?> getType(QueryParameter param, Dao<?,?> dao){
		Class<?> result = super.getType(param, dao);
		if(result == null && dao != null){
			ClassMetadata meta = getClassMetaData(dao);
			result = getPropertyType(meta, param.getN());
			//log.debug("从Hibernate的ClassMetadata中查找属性的类型：" + param.getN());
		}
		return result;
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
	
	
	public ClassMetadata getClassMetaData(Dao<?,?> dao){
		if(dao instanceof HibernateDao){
			HibernateDao<?,?> hdao = (HibernateDao<?,?>) dao;
			Class<?> entityClass = getEntityClass(dao);
			if(entityClass != null){
				log.debug("找到dao对应的entityClass: " + entityClass);
				return hdao.getSessionFactory().getClassMetadata(entityClass);
			}else{
				log.warn("找不到dao对应的entityClass：" + dao);
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param dao
	 * @return
	 */
	public Class<?> getEntityClass(Dao<?,?> dao){
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
		
		/*
		Type genType = dao.getClass().getGenericSuperclass();
	    if(genType != null && genType instanceof ParameterizedType){
        	Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        	if(params.length >= 2){
		        return params[0] instanceof Class ? (Class) params[0] : null;
        	}
        }
	    return null;
	    */
	}
}
