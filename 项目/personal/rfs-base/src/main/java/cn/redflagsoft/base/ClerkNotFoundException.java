/*
 * $Id: ClerkNotFoundException.java 5059 2011-11-14 02:29:06Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ClerkNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5483803332094671718L;

	/**
	 * 
	 */
	public ClerkNotFoundException() {
	}

	/**
	 * @param message
	 */
	public ClerkNotFoundException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ClerkNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ClerkNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
