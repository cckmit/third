package cn.redflagsoft.base.service;

import java.util.Date;

import cn.redflagsoft.base.bean.Log;

public interface LogService {
	/**
	 * ɾ��ָ��ģ���������־(status��ѡ)
	 * 
	 * @param module
	 * @param status
	 * @return int
	 */
	int deleteLogsByModule(String module, Byte status);
	
	/**
	 * ɾ��ָ������(risk,system)��������־(status��ѡ)
	 * 
	 * @param type
	 * @param status
	 * @return int
	 */
	int deleteLogsByType(String type, Byte status);
	
	/**
	 * ɾ��ָ��ʱ��ε���־
	 * 
	 * @param start
	 * @param end
	 * @return int
	 */
	int deleteLogsByTime(Date start, Date end);
	
	/** 
	 * ɾ��ָ����־
	 * 
	 * @param id
	 * @return int
	 */
	int deleteLog(Long id);
	
	/**
	 * �����־
	 * 
	 * @param log
	 * @return Log
	 */
	Log saveLog(Log log);
}
