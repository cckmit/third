package cn.redflagsoft.base.service;

import java.util.Date;

import cn.redflagsoft.base.bean.Log;

public interface LogService {
	/**
	 * 删除指定模块的所有日志(status可选)
	 * 
	 * @param module
	 * @param status
	 * @return int
	 */
	int deleteLogsByModule(String module, Byte status);
	
	/**
	 * 删除指定类型(risk,system)的所有日志(status可选)
	 * 
	 * @param type
	 * @param status
	 * @return int
	 */
	int deleteLogsByType(String type, Byte status);
	
	/**
	 * 删除指定时间段的日志
	 * 
	 * @param start
	 * @param end
	 * @return int
	 */
	int deleteLogsByTime(Date start, Date end);
	
	/** 
	 * 删除指定日志
	 * 
	 * @param id
	 * @return int
	 */
	int deleteLog(Long id);
	
	/**
	 * 添加日志
	 * 
	 * @param log
	 * @return Log
	 */
	Log saveLog(Log log);
}
