/*
 * $Id: WorkProcessException.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.process;

/**
 * @author Alex Lin
 *
 */
public class WorkProcessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WorkProcessException() {
		super();
	}

	public WorkProcessException(String message, Throwable cause) {
		super(message, cause);
	}

	public WorkProcessException(String message) {
		super(message);
	}

	public WorkProcessException(Throwable cause) {
		super(cause);
	}

}
