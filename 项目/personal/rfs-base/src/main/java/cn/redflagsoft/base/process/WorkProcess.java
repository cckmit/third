/*
 * $Id: WorkProcess.java 4599 2011-08-18 07:14:12Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.process;

import java.util.Map;


/**
 * 事务，最小的业务单位。不可再分拆的简单业务逻辑。
 * 如果在WorkProcess上增加拦截器，则需要将WorkProcess设置成prototype的。
 * 
 * 
 * @author Alex Lin
 *
 */
public interface WorkProcess {
	/**
	 * 执行具体事务。
	 * 
	 * @param parameters 参数。
	 * @return 返回对象。
	 */
	Object execute(Map<?,?> parameters) throws WorkProcessException;
	/**
	 * 执行具体事务。
	 * 
	 * @param parameters 参数。
	 * @param needLog 是否需要记录事务日志。
	 * @return
	 */
	Object execute(Map<?,?> parameters, boolean needLog) throws WorkProcessException;
}
