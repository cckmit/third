package org.opoo.apps.query.impl;

import org.opoo.apps.QueryParameters;
import org.opoo.apps.exception.QueryException;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.query.PageQuery;
import org.opoo.apps.query.ResultFilterBuilder;
import org.opoo.ndao.Dao;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.factory.config.BeanDefinition;

public class DaoPageQuery implements PageQuery, BeanDefinitionHolder {
	private final String name;
	private final String description;
	private final ResultFilterBuilder builder;
	private final BeanDefinition beanDefinition;

	public DaoPageQuery(String name, ResultFilterBuilder builder, BeanDefinition beanDefinition) {
		super();
		this.name = name;
		this.description = name + "#findPageableList(ResultFilter)";
		this.builder = builder;
		this.beanDefinition = beanDefinition;
	}

	public PageableList<?> execute(QueryParameters parameters) throws QueryException {
		Dao<?,?> bean = (Dao<?,?>)Application.getContext().getBean(name);
		ResultFilter filter = builder.buildResultFilter(bean, parameters);
		return bean.findPageableList(filter);
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
