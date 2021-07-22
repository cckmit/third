package org.opoo.apps.query.stat;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StatisticsImpl implements Statistics, StatisticsImplementor {
	private static final Log log = LogFactory.getLog(StatisticsImpl.class);
	private boolean isStatisticsEnabled;
	private long startTime = System.currentTimeMillis();
	
	private long operationThreshold = 0;    // log operations that take longer than this value (in milliseconds)
    										// We don't log anything if operationThreshold == 0 

	private long queryExecutionCount;
	private long queryExecutionMaxTime;
	private String queryExecutionMaxTimeQueryKey;
	private final Map<String,QueryStatistics> queryStatistics = new HashMap<String,QueryStatistics>();

	public void queryExecuted(String queryKey, int rows, long time) {
		synchronized(this) {
	        queryExecutionCount++;
	        if (queryExecutionMaxTime < time) {
	            queryExecutionMaxTime = time;
	            queryExecutionMaxTimeQueryKey = queryKey;
	        }
	        if (queryKey != null) {
	            QueryStatistics qs = getQueryStatistics(queryKey);
	            qs.executed(rows, time);
	        }
	    }
	    if(operationThreshold > 0 && operationThreshold < time) {
	        logOperation("executeQuery", queryKey, time);
	    }
	}
	
	  private void logOperation(String operation, String entityName, long time) {
	        if(entityName != null)
	            log.info(operation+entityName + " " + time + "ms");
	        else
	            log.info(operation);  // just log that the event occurred

	    }

	public void clear() {
		queryExecutionCount = 0;
		queryExecutionMaxTime = 0;
		queryExecutionMaxTimeQueryKey = null;
		
		queryStatistics.clear();
		
		startTime = System.currentTimeMillis();
	}

	public QueryStatistics getQueryStatistics(String key) {
		QueryStatistics qs = queryStatistics.get(key);
		if(qs == null){
			qs = new QueryStatistics(key);
			queryStatistics.put(key, qs);
		}
		return qs;
	}

	public long getQueryExecutionCount() {
		return queryExecutionCount;
	}

	public long getQueryExecutionMaxTime() {
		return queryExecutionMaxTime;
	}

	public String getQueryExecutionMaxTimeQueryKey() {
		return queryExecutionMaxTimeQueryKey;
	}

	public long getStartTime() {
		return startTime;
	}

	public void logSummary() {
		log.info("Logging statistics....");
		log.info("start time: " + startTime);
		log.info("queries executed: " + queryExecutionCount);
		log.info("max query time: " + queryExecutionMaxTime + "ms");
	}

	public boolean isStatisticsEnabled() {
		return isStatisticsEnabled;
	}

	public  void setStatisticsEnabled(boolean b) {
		isStatisticsEnabled = b;
	}

	public synchronized void setOperationThreshold(long threshold) {
		operationThreshold = threshold;
	}

	public synchronized long getOperationThreshold() {
		return operationThreshold;
	}

	public String[] getQueryKeys(){
		return queryStatistics.keySet().toArray(new String[queryStatistics.size()]);
	}
}
