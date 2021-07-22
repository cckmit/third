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
	 * ִ�о�������Ĭ�ϼ�¼��־��
	 * 
	 * @param parameters ������
	 * @return ���ض���
	 */
	@SuppressWarnings("unchecked")
	public Object execute(Map parameters) {
		return execute(parameters, true);
	}
}
