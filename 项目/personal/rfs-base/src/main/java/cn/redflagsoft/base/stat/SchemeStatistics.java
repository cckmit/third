/*
 * $Id: SchemeStatistics.java 6179 2013-01-23 07:20:33Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.stat;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class SchemeStatistics extends CategorizedStatistics {
	private static final long serialVersionUID = 1L;
	Map<String, MethodStatistics> methodStatistics = Maps.newHashMap();
	
	private long executionCount;
//	private String displayName;
//	private String className;
	
	/**
	 * @param schemeName
	 */
	SchemeStatistics(String schemeName) {
		super(schemeName);
//		this.className = className;
//		this.displayName = displayName;
	}
	
//	/**
//	 * @return the displayName
//	 */
//	public String getDisplayName() {
//		return displayName;
//	}
//
//	/**
//	 * @return the className
//	 */
//	public String getClassName() {
//		return className;
//	}


	void methodExecuted(String methodName, long time){
		getMethodStatistics(methodName).executed(time);
		executionCount++;
	}
	
	public synchronized MethodStatistics getMethodStatistics(String methodName){
		MethodStatistics statistics = methodStatistics.get(methodName);
		if (statistics==null) {
			statistics = new MethodStatistics(methodName);
			methodStatistics.put(methodName, statistics);
		}
		return statistics;
	}
	
	public long getExecutionCount(){
		return executionCount;
	}
	
	public String[] getMethodNames(){
		return methodStatistics.keySet().toArray(new String[methodStatistics.size()]);
	}
	
	public String toFormatString() {
		Map<String, Long> map = Maps.newHashMap();
		for (MethodStatistics s : methodStatistics.values()) {
			map.put(s.getMethodName(), s.getExecutionCount());
		}
		return new StringBuffer().append(getCategoryName()).append(": ")
				.append(map).toString();
	}
	
	public String toString() {
		return new StringBuffer()
		    .append("SchemeStatistics")
			.append("[schemeName=").append(this.getCategoryName())
//			.append(",className=").append(this.className)
//			.append(",displayName=").append(this.displayName)
			.append(",executionCount=").append(this.executionCount)
			.append(",methodNames=").append(methodStatistics.keySet())
			.append(']')
			.toString();
	}
}
