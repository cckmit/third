/*
 * $Id: EntityClassDiscover.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.support.hibernate3;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.EntityMode;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import cn.redflagsoft.base.support.TypedObjectClassDiscover;
import cn.redflagsoft.base.util.ObjectTypeUtils;

/**
 * 从实体类配置中根据objectType查询实体类。
 * 
 * @author Alex Lin
 *
 */
public class EntityClassDiscover implements TypedObjectClassDiscover, InitializingBean {
	private static Map<Integer, ClassMetadata> classMetadataMap = new HashMap<Integer, ClassMetadata>();
	
	//private Collection<ClassMetadata> classMetadataCollection;
	
	public EntityClassDiscover(){}
	
//	public EntityClassDiscover(Collection<ClassMetadata> classMetadataCollection){
//		this.classMetadataCollection = classMetadataCollection;
//	}

	public EntityClassDiscover(Map<Integer, ClassMetadata> classMetadataMap){
		this.classMetadataMap = classMetadataMap;
	}	
	
	public ClassMetadata findClassMetadata(int objectType){
		return classMetadataMap.get(objectType);
	}


	public Class<?> findClass(int objectType) {
		if(classMetadataMap.containsKey(objectType)) {
			return classMetadataMap.get(objectType).getMappedClass(EntityMode.POJO);
		}
		return null;
		
		
//		for(ClassMetadata meta: classMetadataCollection){
//			Class<?> clazz = meta.getMappedClass(EntityMode.POJO);
//			Short actualObjectType = ObjectTypeUtils.getObjectType(clazz);
//			if(actualObjectType != null && actualObjectType.shortValue() == objectType){
//				return clazz;
//			}
//		}
//		return null;
	}


	public String findName(int objectType) {
		if(classMetadataMap.containsKey(objectType)) {
			return classMetadataMap.get(objectType).getEntityName();
		}
		return null;
		
		
//		for(ClassMetadata meta: classMetadataCollection){
//			Class<?> clazz = meta.getMappedClass(EntityMode.POJO);
//			Short actualObjectType = ObjectTypeUtils.getObjectType(clazz);
//			if(actualObjectType != null && actualObjectType.shortValue() == objectType){
//				return meta.getEntityName();
//			}
//		}
//		return null;
	}


	public void afterPropertiesSet() throws Exception {
//		Assert.notNull(classMetadataCollection);		
		Assert.notNull(classMetadataMap);
	}

	public void setClassMetadataCollection(Collection<ClassMetadata> classMetadataCollection) {
		for(ClassMetadata meta: classMetadataCollection){
			Class<?> clazz = meta.getMappedClass(EntityMode.POJO);
			Integer actualObjectType = ObjectTypeUtils.getObjectType(clazz);
			if(actualObjectType != null){
				classMetadataMap.put(actualObjectType, meta);
			}
		}
		
//		this.classMetadataCollection = classMetadataCollection;
	}
	
	/**
	 * 从Hibernate的sessionFactory中取得所有的ClassMetadata。
	 * 
	 * @param factory
	 */
	@SuppressWarnings("unchecked")
	public void setSessionFactory(SessionFactory factory){
		this.setClassMetadataCollection(factory.getAllClassMetadata().values());
	}
}
