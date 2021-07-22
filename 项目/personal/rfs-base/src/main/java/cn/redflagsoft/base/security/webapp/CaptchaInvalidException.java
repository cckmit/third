/*
 * $Id: CaptchaInvalidException.java 4983 2011-10-28 04:14:15Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security.webapp;

import org.springframework.security.AuthenticationException;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class CaptchaInvalidException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6392491652916886934L;

	/**
	 * @param msg
	 */
	public CaptchaInvalidException(String msg) {
		super(msg);
	}

	/**
	 * @param msg
	 * @param t
	 */
	public CaptchaInvalidException(String msg, Throwable t) {
		super(msg, t);
	}

	/**
	 * @param msg
	 * @param extraInformation
	 */
	public CaptchaInvalidException(String msg, Object extraInformation) {
		super(msg, extraInformation);
	}

}
