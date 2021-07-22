/*
 * $Id: NoSuchSchemeException.java 4658 2011-09-05 02:03:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class NoSuchSchemeException extends SchemeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2770860360495270011L;

	/**
	 * @param message
	 */
	public NoSuchSchemeException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NoSuchSchemeException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoSuchSchemeException(String message, Throwable cause) {
		super(message, cause);
	}

}
