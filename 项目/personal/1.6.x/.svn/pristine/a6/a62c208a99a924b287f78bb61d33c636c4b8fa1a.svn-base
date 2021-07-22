package org.opoo.apps.query.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.QueryParameters;
import org.opoo.apps.exception.QueryException;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.query.Query;
import org.opoo.apps.query.util.QueryUtils;
import org.opoo.ndao.Dao;
import org.opoo.ndao.ObjectNotFoundException;
import org.opoo.ndao.hibernate3.HibernateDao;
import org.springframework.beans.factory.config.BeanDefinition;

public class DaoGetQuery implements Query<Object>, BeanDefinitionHolder{
	private static final Log log = LogFactory.getLog(DaoGetQuery.class);
	
	private final String name;
	private final String description;
	private final BeanDefinition beanDefinition;
	
	public DaoGetQuery(String name, BeanDefinition beanDefinition) {
		super();
		this.name = name;
		this.description = name + "#get(Serializable)";
		this.beanDefinition = beanDefinition;
	}

	public Object execute(QueryParameters parameters) throws QueryException {
		String id = QueryUtils.getStringValue(parameters.getRawParameters(), "id");
		if(id == null){
			throw new IllegalArgumentException("参数ID不能为空");
		}
		
		Dao<?, ?> bean = (Dao<?, ?>)Application.getContext().getBean(name);
		if(bean instanceof HibernateDao){
			@SuppressWarnings("unchecked")
			HibernateDao<?,Serializable> dao = (HibernateDao<?,Serializable>) bean;
			Class<?> idClass = dao.getIdClass();	
			Serializable value = (Serializable) QueryUtils.convertValue(id, idClass);
			Serializable o = dao.get(value);
			
			if(o != null){
				return o;
			}else{
				log.debug("找不到对象[" + name + "]：id=" + id);
				throw new ObjectNotFoundException("找不到对象：id=" + id);
			}	
		}else{
			throw new ObjectNotFoundException("不支持查询此类对象：target=" + name + ", id=" + id);
		}
	}
	
	public String toString(){
		return description;
	}

	public String getName() {
		return name;
	}

	public BeanDefinition getBeanDefinition() {
		return beanDefinition;
	}
}
