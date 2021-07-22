/*
 * $Id: SmsgNotSendableException.java 5277 2011-12-26 09:07:11Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

/**
 * 消息无法发送的异常。
 * @author Alex Lin
 *
 */
public class SmsgNotSendableException extends SmsgException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2442160015006352496L;

	/**
	 * 
	 */
	public SmsgNotSendableException() {
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SmsgNotSendableException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public SmsgNotSendableException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SmsgNotSendableException(Throwable cause) {
		super(cause);
	}
}
