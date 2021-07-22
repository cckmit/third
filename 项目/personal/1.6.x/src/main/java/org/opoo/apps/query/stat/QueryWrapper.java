package org.opoo.apps.query.stat;

import java.util.Collection;

import org.opoo.apps.QueryParameters;
import org.opoo.apps.exception.QueryException;
import org.opoo.apps.query.Query;

public class QueryWrapper implements Query<Object> {
	private final String queryKey;
	private final Query<?> query;
	private final Statistics statistics;
	private final StatisticsImplementor statisticsImplementor;
	
	public QueryWrapper(Statistics statistics, 
			StatisticsImplementor statisticsImplementor,
			String queryKey, Query<?> query) {
		super();
		this.statistics = statistics;
		this.statisticsImplementor = statisticsImplementor;
		this.queryKey = queryKey;
		this.query = query;
	}

	public Object execute(QueryParameters parameters) throws QueryException {
		if(!statistics.isStatisticsEnabled()){
			return query.execute(parameters);
		}
		
		long start = System.currentTimeMillis();
		Object t = query.execute(parameters);
		long time = System.currentTimeMillis() - start;
		
		int rows = 1;
		if(t instanceof Collection){
			rows = ((Collection<?>) t).size();
		}
		statisticsImplementor.queryExecuted(queryKey, rows, time);
		return t;
	}

	public Query<?> getWrappedQuery(){
		return query;
	}

	public String getQueryKey(){
		return queryKey;
	}
}
