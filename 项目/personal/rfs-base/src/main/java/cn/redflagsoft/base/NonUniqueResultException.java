/*
 * $Id: NonUniqueResultException.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class NonUniqueResultException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8579072315297035188L;

	/**
	 * 
	 */
	public NonUniqueResultException() {
	}

	/**
	 * @param message
	 */
	public NonUniqueResultException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NonUniqueResultException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NonUniqueResultException(String message, Throwable cause) {
		super(message, cause);
	}
}
