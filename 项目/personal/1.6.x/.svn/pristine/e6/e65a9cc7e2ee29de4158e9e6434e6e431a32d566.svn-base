package org.opoo.apps.query.impl;

import org.opoo.apps.QueryParameters;
import org.opoo.apps.exception.QueryException;
import org.opoo.apps.query.PageQuery;
import org.opoo.apps.query.ResultFilterBuilder;
import org.opoo.ndao.hibernate3.HibernateQuerySupport;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.core.io.Resource;

public class NamedPageQuery implements PageQuery, ResourceHolder  {
	private final HibernateQuerySupport querySupport;
	private final String queryString;
	private final String countString;
	private final boolean isSQLQuery;
	private final String description;
	private final ResultFilterBuilder builder;
	private final Resource resource;

	public NamedPageQuery(HibernateQuerySupport querySupport,
			String queryString, String countString,
			boolean isSQLQuery, ResultFilterBuilder builder, Resource resource) {
		super();
		this.querySupport = querySupport;
		this.queryString = queryString;
		this.isSQLQuery = isSQLQuery;
		this.builder = builder;
		this.countString = countString;
		this.description = (isSQLQuery ? "[SQL]" : "[HQL]") + " "
				+ (queryString.length() > 30 ? queryString.substring(0, 30) + "..." : queryString);
		this.resource = resource;
	}

	public HibernateQuerySupport getQuerySupport() {
		return querySupport;
	}

	public PageableList<?> execute(QueryParameters parameters) throws QueryException {
		ResultFilter filter = builder.buildResultFilter(null, parameters);
		if(isSQLQuery){
			return getQuerySupport().findBySQL(queryString, countString, filter);
		}else{
			return getQuerySupport().find(queryString, countString, filter);
		}
	}
	
	public String toString(){
		return description;
		//return (isSQLQuery ? "PAGE SQL" : "PAGE HQL") + ": " + queryString + "; " + countString;
	}

	public String getQueryString() {
		return queryString;
	}

	public String getCountString() {
		return countString;
	}

	public boolean isSQLQuery() {
		return isSQLQuery;
	}

	public Resource getResource() {
		return resource;
	}
}
