package org.opoo.apps.query.stat;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StatisticsService implements StatisticsServiceMBean {
	private static final Log log = LogFactory.getLog(StatisticsService.class);
	
	Statistics stat = new StatisticsImpl();
	
	public void setStatistics(Statistics stat){
		this.stat = stat;
	}
	
	public void clear() {
		stat.clear();
	}

	public QueryStatistics getQueryStatistics(String queryKey) {
		return stat.getQueryStatistics(queryKey);
	}

	public long getQueryExecutionCount() {
		return stat.getQueryExecutionCount();
	}

	public long getQueryExecutionMaxTime() {
		return stat.getQueryExecutionMaxTime();
	}

	public String getQueryExecutionMaxTimeQueryKey() {
		return stat.getQueryExecutionMaxTimeQueryKey();
	}

	public long getStartTime() {
		return stat.getStartTime();
	}

	public void logSummary() {
		stat.logSummary();
	}

	public boolean isStatisticsEnabled() {
		return stat.isStatisticsEnabled();
	}

	public void setStatisticsEnabled(boolean b) {
		stat.setStatisticsEnabled(b);
		log.debug("通过MBean设置StatisticsEnabled属性：" + b);
	}

	public void setOperationThreshold(long threshold) {
		stat.setOperationThreshold(threshold);
	}

	public long getOperationThreshold() {
		return stat.getOperationThreshold();
	}

	public String[] getQueryKeys(){
		return stat.getQueryKeys();
	}

	public void printSummary() {
		System.out.println("输出系统统计信息 ....");
		System.out.println("开始时间: " + new Date(stat.getStartTime()));
		System.out.println("Scheme 执行次数: " + stat.getQueryExecutionCount());
		System.out.println("max query time: " + stat.getQueryExecutionMaxTime() 
				+ "ms, key is " + stat.getQueryExecutionMaxTimeQueryKey());
	}
}
