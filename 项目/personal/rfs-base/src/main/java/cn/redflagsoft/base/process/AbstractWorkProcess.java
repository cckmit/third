/*
 * $Id: AbstractWorkProcess.java 4031 2010-11-02 01:38:45Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.process;

import java.util.Map;


/**
 * @author Alex Lin
 *
 */
public abstract class AbstractWorkProcess implements WorkProcess {
	
	/**
	 * 执行具体事务。默认记录日志。
	 * 
	 * @param parameters 参数。
	 * @return 返回对象。
	 */
	@SuppressWarnings("unchecked")
	public Object execute(Map parameters) {
		return execute(parameters, true);
	}
}
