/*
 * $Id: SchemeException.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;

/**
 * WorkScheme异常。
 * 
 * @author Alex Lin
 *
 */
public class SchemeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5140170644156323172L;

	/**
	 * 
	 */
	public SchemeException() {
	}

	/**
	 * @param message
	 */
	public SchemeException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public SchemeException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SchemeException(String message, Throwable cause) {
		super(message, cause);
	}

}
