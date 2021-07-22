package org.opoo.apps.query.stat;



public interface Statistics{
	/**
	 * reset all statistics
	 */
	public void clear();

    /**
	 * Query statistics from query string (HQL or SQL)
	 * 
	 * @param queryString query string
	 * @return QueryStatistics
	 */
	public QueryStatistics getQueryStatistics(String queryKey);


    /**
     * Get global number of executed queries
	 * @return query execution count
	 */
	public long getQueryExecutionCount();

    /**
     * Get the time in milliseconds of the slowest query.
     */
	public long getQueryExecutionMaxTime();
	/**
	 * Get the query for the slowest query.
	 */
	public String getQueryExecutionMaxTimeQueryKey();
 
	/**
	 * @return start time in ms (JVM standards {@link System#currentTimeMillis()})
	 */
	public long getStartTime();
	/**
	 * log in info level the main statistics
	 */
	public void logSummary();
	/**
	 * Are statistics logged
	 */
	public boolean isStatisticsEnabled();
	/**
	 * Enable statistics logs (this is a dynamic parameter)
	 */
	public void setStatisticsEnabled(boolean b);

    /**
     * Set the operationThreshold to a value greater than zero to enable logging of long running operations.
     * @param threshold (milliseconds)
     */
    public void setOperationThreshold(long threshold);

    /**
     *
     * @return Operationthreshold, if greater than zero, operations that exceed the level will be logged. 
     */
    public long getOperationThreshold();


	String[] getQueryKeys();
}