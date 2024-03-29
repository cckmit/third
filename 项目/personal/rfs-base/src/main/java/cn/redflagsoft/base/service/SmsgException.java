/*
 * $Id: SmsgException.java 5277 2011-12-26 09:07:11Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

/**
 * 消息相关的异常。
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class SmsgException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 517084194589874420L;

	/**
	 * 
	 */
	public SmsgException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SmsgException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public SmsgException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SmsgException(Throwable cause) {
		super(cause);
	}

}
