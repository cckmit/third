package org.opoo.apps.query.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.Labelable;
import org.opoo.apps.LabeledBean;
import org.opoo.apps.QueryParameters;
import org.opoo.apps.dao.LabelableDao;
import org.opoo.apps.exception.QueryException;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.query.Query;
import org.opoo.apps.query.ResultFilterBuilder;
import org.opoo.ndao.Dao;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.factory.config.BeanDefinition;

public class LabelableQuery implements Query<List<Labelable>>, BeanDefinitionHolder {
	private static final Log log = LogFactory.getLog(LabelableQuery.class);
	
	private final String name;
	private final String description;
	private final ResultFilterBuilder builder;
	private final BeanDefinition beanDefinition;

	public LabelableQuery(String name, ResultFilterBuilder builder, BeanDefinition beanDefinition) {
		super();
		this.name = name;
		this.description = name + "#labeldata(ResultFilter)";
		this.builder = builder;
		this.beanDefinition = beanDefinition;
	}
	
	public List<Labelable> execute(QueryParameters parameters) throws QueryException {
		Dao<?, ?> bean = (Dao<?, ?>)Application.getContext().getBean(name);
		ResultFilter filter = builder.buildResultFilter(bean, parameters);
		if(bean instanceof LabelableDao){
			return ((LabelableDao) bean).findLabelables(filter);
		}else{
			List<?> list = bean.find(filter);
			return listItemToLabeledDomain(list);
		}
	}
	
	private List<Labelable> listItemToLabeledDomain(List<?> list){
		List<Labelable> beans = new ArrayList<Labelable>();
		if(list.size() > 0){
			for(int i = 0 ; i < list.size() ; i++){
				Object o = list.get(i);
				if(o instanceof Labelable){
					Labelable bean = (Labelable) o;
					beans.add(new LabeledBean(bean.getLabel(), bean.getData()));
				}else{
					log.warn("对象不是有效的LabeledBean: " + o);
				}
			}
		}
		return beans;
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
