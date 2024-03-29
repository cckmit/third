/*
 * $Id: ClassMetadataDiscriminatorStrategy.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.dao.hibernate3.support;

import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.Queryable;


/**
 * @author Alex Lin
 *
 */
public class ClassMetadataDiscriminatorStrategy implements DiscriminatorStrategy {
	private Map<String, ClassMetadata> allClassMetadata;
	public ClassMetadataDiscriminatorStrategy(){
		
	}
	public ClassMetadataDiscriminatorStrategy(SessionFactory factory){
		setSessionFactory(factory);
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DiscriminatorStrategy#getEntityClassByDiscriminatorValue(java.lang.String)
	 */
	public Class<?> getEntityClassByDiscriminatorValue(String discriminatorValue) {
		if(allClassMetadata != null){
			for(ClassMetadata meta: allClassMetadata.values()){
				if(meta instanceof Queryable){
					Queryable queryable = (Queryable) meta;
					if(queryable.isInherited() && discriminatorValue.equals(queryable.getDiscriminatorSQLValue())){
						return queryable.getType().getReturnedClass();
					}
				}
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DiscriminatorStrategy#getEntityClassByDiscriminatorValue(java.lang.String, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <T> Class<? extends T> getEntityClassByDiscriminatorValue(String discriminatorValue, Class<T> superClass) {
		if(allClassMetadata != null){
			for(ClassMetadata meta: allClassMetadata.values()){
				if(meta instanceof Queryable){
					Queryable queryable = (Queryable) meta;
					if(queryable.isInherited() && discriminatorValue.equals(queryable.getDiscriminatorSQLValue())){
						Class<?> cls = queryable.getType().getReturnedClass();
						if(superClass.isAssignableFrom(cls)){
							return (Class<? extends T>) cls;
						}
					}
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public void setSessionFactory(SessionFactory factory){
		allClassMetadata = factory.getAllClassMetadata();
	}
}
