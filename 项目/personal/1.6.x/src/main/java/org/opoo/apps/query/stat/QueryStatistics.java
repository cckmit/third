package org.opoo.apps.query.stat;

import java.io.Serializable;

public class QueryStatistics implements Serializable {
	private static final long serialVersionUID = 4118123866317062685L;
	private final String queryName;
	private long executionCount;
	private long executionRowCount;
	private long executionAvgTime;
	private long executionMaxTime;
	private long executionMinTime = Long.MAX_VALUE;

	QueryStatistics(String query) {
		this.queryName = query;
	}
	
	public String getQueryName(){
		return queryName;
	}

	/**
	 * queries executed to the DB
	 */
	public long getExecutionCount() {
		return executionCount;
	}
	
	
	/**
	 * Number of lines returned by all the executions of this query (from DB)
	 * For now, {@link org.hibernate.Query#iterate()} 
	 * and {@link org.hibernate.Query#scroll()()} do not fill this statistic
	 *
	 * @return The number of rows cumulatively returned by the given query; iterate
	 * and scroll queries do not effect this total as their number of returned rows
	 * is not known at execution time.
	 */
	public long getExecutionRowCount() {
		return executionRowCount;
	}

	/**
	 * average time in ms taken by the excution of this query onto the DB
	 */
	public long getExecutionAvgTime() {
		return executionAvgTime;
	}

	/**
	 * max time in ms taken by the excution of this query onto the DB
	 */
	public long getExecutionMaxTime() {
		return executionMaxTime;
	}
	
	/**
	 * min time in ms taken by the excution of this query onto the DB
	 */
	public long getExecutionMinTime() {
		return executionMinTime;
	}
	
	/**
	 * add statistics report of a DB query
	 * 
	 * @param rows rows count returned
	 * @param time time taken
	 */
	void executed(long rows, long time) {
		if (time < executionMinTime) executionMinTime = time;
		if (time > executionMaxTime) executionMaxTime = time;
		executionAvgTime = ( executionAvgTime * executionCount + time ) / ( executionCount + 1 );
		executionCount++;
		executionRowCount += rows;
	}

	public String toString() {
		return new StringBuffer()
				.append( "QueryStatistics" )
				.append( "[queryName=" ).append( this.queryName )
				.append( ",executionCount=" ).append( this.executionCount )
				.append( ",executionRowCount=" ).append( this.executionRowCount )
				.append( ",executionAvgTime=" ).append( this.executionAvgTime )
				.append( ",executionMaxTime=" ).append( this.executionMaxTime )
				.append( ",executionMinTime=" ).append( this.executionMinTime )
				.append( ']' )
				.toString();
	}
}