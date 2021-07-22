package org.opoo.apps.query.impl;

import java.util.List;

import org.opoo.apps.QueryParameters;
import org.opoo.apps.exception.QueryException;
import org.opoo.apps.query.ListQuery;
import org.opoo.apps.query.ResultFilterBuilder;
import org.opoo.ndao.hibernate3.HibernateQuerySupport;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.core.io.Resource;

public class NamedListQuery implements ListQuery, ResourceHolder {
	private final HibernateQuerySupport querySupport;
	private final String queryString;
	private final boolean isSQLQuery;
	private final String description;
	private final ResultFilterBuilder builder;
	private final Resource resource;

	public NamedListQuery(HibernateQuerySupport querySupport,
			String queryString, boolean isSQLQuery, ResultFilterBuilder builder, Resource resource) {
		super();
		this.querySupport = querySupport;
		this.queryString = queryString;
		this.isSQLQuery = isSQLQuery;
		this.builder = builder;
		this.description = (isSQLQuery ? "[SQL]" : "[HQL]") + " "
				+ (queryString.length() > 30 ? queryString.substring(0, 30) + "..." : queryString);
		this.resource = resource;
	}

	public HibernateQuerySupport getQuerySupport() {
		return querySupport;
	}

	public List<?> execute(QueryParameters parameters) throws QueryException {
		ResultFilter filter = this.builder.buildResultFilter(null, parameters);
		if(isSQLQuery){
			return getQuerySupport().findBySQL(queryString, filter);
		}else{
			return getQuerySupport().find(queryString, filter);
		}
	}
	
	public String toString(){
		return description;
	}

	public String getQueryString() {
		return queryString;
	}

	public boolean isSQLQuery() {
		return isSQLQuery;
	}

	public Resource getResource() {
		return resource;
	}
}
