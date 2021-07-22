/*
 * $Id: WorkProcessManager.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.process;

import java.util.Map;

/**
 * WorkProcess管理接口。
 * 
 * @author Alex Lin
 * 
 */
public interface WorkProcessManager {
	
	WorkProcess getProcess(String name);
	
	/**
	 * 从配置中查找一个WorkProcess对象。
	 * 
	 * @param processType 要查找的WorkProcess的类型。
	 * @return WorkProcess
	 */
	WorkProcess getProcess(int processType);
	
	/**
	 * 执行一个WorkProcess.
	 * 
	 * @param processType 要执行的WorkProcess的类型。
	 * @param parameters 传递给WorkProcess的参数。
	 * @param needLog 是否需要保存process记录。 
	 * @return 执行后返回对象。
	 */
	@SuppressWarnings("unchecked")
	Object execute(int processType, Map parameters, boolean needLog);
}
