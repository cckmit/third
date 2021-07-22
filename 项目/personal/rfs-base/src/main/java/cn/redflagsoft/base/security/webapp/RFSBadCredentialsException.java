/*
 * $Id: RFSBadCredentialsException.java 5030 2011-11-07 09:41:43Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security.webapp;

import org.springframework.security.BadCredentialsException;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class RFSBadCredentialsException extends BadCredentialsException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4574369981970142247L;

	/**
	 * @param msg
	 */
	public RFSBadCredentialsException(String msg) {
		super(msg);
	}

	/**
	 * @param msg
	 * @param extraInformation
	 */
	public RFSBadCredentialsException(String msg, Object extraInformation) {
		super(msg, extraInformation);
	}

	/**
	 * @param msg
	 * @param t
	 */
	public RFSBadCredentialsException(String msg, Throwable t) {
		super(msg, t);
	}

}
